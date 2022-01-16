package com.springboot.prac.tmdunit;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.Gson;
import com.springboot.prac.PracApplication;
import com.springboot.prac.config.TestDataSourceConfig;
import com.springboot.prac.tmdap.dao.*;
import com.springboot.prac.tmdap.entity.*;
import lombok.Data;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {PracApplication.class, TestDataSourceConfig.class})
@ActiveProfiles({"h2"})
public class TestPaginate {

    @Data
    class OutputDto{
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @JsonFormat(pattern = "yyyy-MM-dd")
        private String startDate;
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @JsonFormat(pattern = "yyyy-MM-dd")
        private String endDate;
        private String userId;
        private String userName;
        private String superiorUnitId;
        private String superiorUnitName;
        private String unitId;
        private String unitName;
        private Double holidayHour;
        private Double projectHour;
        private Double maintainHour;
        private Double dailyHour;
        private Double totalHour;
        private Double overWorkHour;
        private Date recordDate;
    }

    @Autowired
    private DailyPlanDao dailyPlanDao;
    @Autowired
    private TaskDao taskDao;
    @Autowired
    private TaskDailyPlanDao taskDailyPlanDao;
    @Autowired
    private TaskTypeDao taskTypeDao;
    @Autowired
    private TaskItemDao taskItemDao;
    @Autowired
    private TaskItemTaskTypeDao taskItemTaskTypeDao;
    @Autowired
    private UserDao userDao;

    @Test
    public void testPaginate() throws ParseException {

        // 模擬 分頁功能 參數設定
        int page = 1;
        int size = 15;

        // 1. sort 不排序
        //Sort sort = null;

        // 2. sort 排序
        Sort sort = Sort.by(Sort.Direction.DESC, "unitId"); // unitId userId

        Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2021-12-01");
        Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2021-12-03");

        // 1. unitId 不選
        String unitId = "";
        // 2. unitId 選擇
        //String unitId = "unitId1"; // unitId1 unitId2

        // 1. superiorUnitId 不選
        String superiorUnitId = "";
        // 2. superiorUnitId 選擇
        //String superiorUnitId = "superiorId1"; // superiorId1 superiorId2

        this.paginate(page, size, sort, superiorUnitId, unitId, startDate, endDate);

    }


    private Page<OutputDto> paginate(int page, int size, Sort sort, String superiorUnitId, String unitId, Date startDate, Date endDate){

        if(sort == null){
            sort = Sort.unsorted();
        }
        String sortString = sort.toString();

        /**
         * 找符合選擇條件的 日計畫
         */
        List<DailyPlan> dailyPlanList = dailyPlanDao.findAll(
                Specification.where(this.filterByUnitInfo(superiorUnitId, unitId))
                .and(this.filterByplanDate(startDate, endDate))
        );

            //System.out.println(new Gson().toJson(dailyPlanList)); // 輔助顯示

        List<OutputDto> outputDtoList = new ArrayList<>(); // output List 準備


        for(DailyPlan dailyPlan : dailyPlanList){
            OutputDto outputDto = new OutputDto();
            Double holidayHour = 0.0; // 差勤
            Double projectHour = 0.0; // 專案開發
            Double maintainHour = 0.0; // 系統維運
            Double dailyWorkHour = 0.0; // 日常行政
            Double otherHour = 0.0; // 其他
            Double totalHour = 0.0; // 總時數
            Double overWorkHour = 0.0; // 加班

            Set<Tasks> taskSet = dailyPlan.getTaskSet();
            for(Tasks t : taskSet){
                TaskItemTaskType taskItemTaskType = taskItemTaskTypeDao.findById(t.getTaskMappingId()).get();
                String taskTypeId = taskItemTaskType.getTaskTypeId();
                if("SYS_MAINTEN".equals(taskTypeId)){
                    List<TaskDailyPlan> taskDailyPlanList = taskDailyPlanDao.findByTaskIdAndDailyPlanId(t.getId(), dailyPlan.getId());
                    maintainHour += taskDailyPlanList.get(0).getWorkingHours();
                }
                else if("PROJECT_DEV".equals(taskTypeId)){
                    List<TaskDailyPlan> taskDailyPlanList = taskDailyPlanDao.findByTaskIdAndDailyPlanId(t.getId(), dailyPlan.getId());
                    projectHour += taskDailyPlanList.get(0).getWorkingHours();
                }
                else if("OFF_HOURS".equals(taskTypeId)){
                    List<TaskDailyPlan> taskDailyPlanList = taskDailyPlanDao.findByTaskIdAndDailyPlanId(t.getId(), dailyPlan.getId());
                    holidayHour += taskDailyPlanList.get(0).getWorkingHours();
                }
                else if("DAILY".equals(taskTypeId)){
                    List<TaskDailyPlan> taskDailyPlanList = taskDailyPlanDao.findByTaskIdAndDailyPlanId(t.getId(), dailyPlan.getId());
                    dailyWorkHour += taskDailyPlanList.get(0).getWorkingHours();
                }
                else{
                    List<TaskDailyPlan> taskDailyPlanList = taskDailyPlanDao.findByTaskIdAndDailyPlanId(t.getId(), dailyPlan.getId());
                    otherHour += taskDailyPlanList.get(0).getWorkingHours();
                }
            } //for end


            /**
             * 整理塞入值
             */
            outputDto.setUserId(dailyPlan.getUserId());
            User user = userDao.findById(dailyPlan.getUserId()).get();
            outputDto.setUserName(user.getUserName());
            outputDto.setUnitId(user.getUnitId());
            outputDto.setUnitName(user.getUnit().getUnitName());
            outputDto.setSuperiorUnitId(user.getUnit().getSuperiorUnitId());
            outputDto.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(startDate));
            outputDto.setEndDate(new SimpleDateFormat("yyyy-MM-dd").format(endDate));
            outputDto.setRecordDate(dailyPlan.getPlanDate());

            outputDto.setMaintainHour(maintainHour);
            outputDto.setProjectHour(projectHour);
            outputDto.setHolidayHour(holidayHour);
            outputDto.setDailyHour(dailyWorkHour);

            outputDto.setTotalHour(dailyPlan.getTotalHours());
            outputDto.setOverWorkHour(dailyPlan.getOverWorkHours());

            outputDtoList.add(outputDto);
        }

            //System.out.println(new Gson().toJson(outputDtoList)); // 輔助顯示

        /**
         * 排序
         */
        outputDtoList = this.sortFilter(sortString, outputDtoList);

        /**
         * 轉換為 page
         */
        Page<OutputDto> paginate = new PageImpl<OutputDto>(outputDtoList);
            System.out.println(new Gson().toJson(paginate)); // 輔助顯示

        return paginate;
    }

    /**
     * @description 尋找符合 unitId, superiorUnitId 下的 userId
     * @param superiorUnitId
     * @param unitId
     * @return
     */
    private Specification filterByUnitInfo(String superiorUnitId, String unitId){
        return (root, query, cb) -> {
            if(!StringUtils.isEmpty(unitId) && !StringUtils.isEmpty(superiorUnitId)){
                List<String> userIds = userDao.findAll().stream()
                        .filter(user -> unitId.equals(user.getUnitId()) && superiorUnitId.equals(user.getUnit().getSuperiorUnitId()))
                        .map(User::getUserId)
                        .collect(Collectors.toList());

                return root.get("userId").in(userIds);
            } else if(!StringUtils.isEmpty(unitId) && StringUtils.isEmpty(superiorUnitId)){
                List<String> userIds = userDao.findByUnitId(unitId).stream().map(User::getUserId).collect(Collectors.toList());
                return root.get("userId").in(userIds);
            } else if( StringUtils.isEmpty(unitId) && !StringUtils.isEmpty(superiorUnitId)){
                List<String> userIds = userDao.findAll().stream()
                        .filter(user -> superiorUnitId.equals(user.getUnit().getSuperiorUnitId()))
                        .map(User::getUserId)
                        .collect(Collectors.toList());

                return root.get("userId").in(userIds);
            } else{
                return cb.and();
            }
        };
    }

    /**
     * @description 尋早符合 stratDate, endDate
     * @param startDate
     * @param endDate
     * @return
     */
    private Specification filterByplanDate(Date startDate, Date endDate){
        return (root, query, cb) -> {
            if(startDate != null && endDate != null){
                List<String> dailyPlanId = dailyPlanDao.getDailyPlansByPlanDate(startDate, endDate).stream().map(DailyPlan::getId).collect(Collectors.toList());
                return root.get("id").in(dailyPlanId);
            }else{
                return cb.and();
            }
        };
    }

    private List<OutputDto> sortFilter(String sortString, List<OutputDto> outputDtoList){

        if(!"UNSORTED".equals(sortString)){
            if("unitId: ASC".equals(sortString)){
                Collections.sort(outputDtoList, (a, b)->{
                    return a.getUnitId().compareTo(b.getUnitId());
                });
            }else if("unitId: DESC".equals(sortString)){
                Collections.sort(outputDtoList, (a, b)->{
                    return b.getUnitId().compareTo(a.getUnitId());
                });
            }
            if("userId: ASC".equals(sortString)){
                Collections.sort(outputDtoList, (a, b)->{
                    return a.getUserId().compareTo(b.getUserId());
                });
            }else if("userId: DESC".equals(sortString)){
                Collections.sort(outputDtoList, (a, b)->{
                    return b.getUserId().compareTo(a.getUserId());
                });
            }
        }else {
            outputDtoList.sort(
                    Comparator.comparing(OutputDto::getSuperiorUnitId).thenComparing(OutputDto::getUnitId)
            );
        }

        return outputDtoList;
    }
}
