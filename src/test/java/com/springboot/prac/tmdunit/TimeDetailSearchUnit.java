package com.springboot.prac.tmdunit;

import com.google.gson.Gson;
import com.springboot.prac.PracApplication;
import com.springboot.prac.config.TestDataSourceConfig;
import com.springboot.prac.tmdap.dao.*;
import com.springboot.prac.tmdap.entity.*;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.criteria.Predicate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Data
class TimeDetailSearchDto{
    private Date startDate;
    private Date endDate;
    private String taskName;
    private String taskTypeId;
    private String taskItemId;
    private String unitId;
    private String assigneeId;
}


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {PracApplication.class, TestDataSourceConfig.class})
@ActiveProfiles({"h2"})
public class TimeDetailSearchUnit {

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
    public void testTimeDetailSearch() throws ParseException {
        Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2021-12-01");
        Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2021-12-03");
        String taskName = ""; // name3 name4 有
        String taskTypeId = ""; // taskTypeId001
        String unitId = "unitId1"; //unitId1 有
        String assigneeId = "test1"; // test1 有

        TimeDetailSearchDto timeDetailSearchDto = new TimeDetailSearchDto();
        timeDetailSearchDto.setStartDate(startDate);
        timeDetailSearchDto.setEndDate(endDate);
        timeDetailSearchDto.setTaskName(taskName);
        timeDetailSearchDto.setTaskTypeId(taskTypeId);
        timeDetailSearchDto.setUnitId(unitId);
        timeDetailSearchDto.setAssigneeId(assigneeId);

        List<DailyPlan> dailyPlanList = dailyPlanDao.findAll(Specification.where(filter2_1(timeDetailSearchDto))
                .and(filter4(timeDetailSearchDto))
                .and(filter5(timeDetailSearchDto))
                .and(filter6(timeDetailSearchDto))
        );

        System.out.println(new Gson().toJson(dailyPlanList));
    }

    private Specification filter1(){
        return (root, query, cb) -> cb.and();
    }

    private Specification filter2(Date startDate, Date endDate){
        return (root, query, cb) -> {
            if(startDate != null && endDate != null){
                return root.get("dailyPlanId").in(dailyPlanDao.getDailyPlansByPlanDate(startDate, endDate).stream().map(DailyPlan::getId).collect(Collectors.toList()));
            }else{
                return cb.and();
            }
        };
    }

    private Specification filter2_1(TimeDetailSearchDto dto){
        return (root, query, cb) ->{
            if(dto.getStartDate() != null && dto.getEndDate() != null){
                return root.get("id").in(dailyPlanDao.getDailyPlansByPlanDate(dto.getStartDate(), dto.getEndDate()).stream().map(DailyPlan::getId).collect(Collectors.toList()));
            }else{
                return cb.and();
            }
        };
    }

    private Specification filter2_2(TimeDetailSearchDto dto){
        return (root, query, cb) ->{
            if(dto.getStartDate() != null && dto.getEndDate() != null){
                Predicate pre1 = cb.greaterThanOrEqualTo(root.get("planDate"), dto.getStartDate());
                Predicate pre2 = cb.lessThanOrEqualTo(root.get("planDate"), dto.getEndDate());
                return cb.and(pre1, pre2);
            }else{
                return cb.and();
            }
        };
    }

    private Specification filter3(String taskName){
        return (root, query, cb) -> {
            if(!StringUtils.isEmpty(taskName)){
                return root.get("taskId").in(taskDao.queryByTaskNameNoCase(taskName).stream().map(Tasks::getId).collect(Collectors.toList()));
            }else{
                return cb.and();
            }
        };
    }

    private Specification filter4(TimeDetailSearchDto dto){
        return (root, query, cb) ->{
            if(!StringUtils.isEmpty(dto.getTaskName())){
                List<String> taskIds = taskDao.queryByTaskNameNoCase(dto.getTaskName()).stream().map(Tasks::getId).collect(Collectors.toList());
                List<String> dailyPlanIds = taskDailyPlanDao.findByTaskIdIn(taskIds).stream().map(TaskDailyPlan::getDailyPlanId).collect(Collectors.toList());
                return root.get("id").in(dailyPlanIds);
            }else{
                return cb.and();
            }
        };
    }

    private Specification filter5(TimeDetailSearchDto dto){
        return (root, query, cb) -> {
            if(!StringUtils.isEmpty(dto.getTaskTypeId())){
                List<String> taskItmeTypeMappingIds = taskItemTaskTypeDao.findByTaskTypeId(dto.getTaskTypeId()).stream().map(TaskItemTaskType::getId).collect(Collectors.toList());
                List<String> taskIds = taskDao.findByTaskMappingIdIn(taskItmeTypeMappingIds).stream().map(Tasks::getId).collect(Collectors.toList());
                List<String> dailyPlanIds = taskDailyPlanDao.findByTaskIdIn(taskIds).stream().map(TaskDailyPlan::getDailyPlanId).collect(Collectors.toList());
                return root.get("id").in(dailyPlanIds);
            }else{
                return cb.and();
            }
        };

    }

    private Specification filter6(TimeDetailSearchDto dto){
        return (root, query, cb) ->{
            if(!StringUtils.isEmpty(dto.getUnitId())){
                List<String> userIds = userDao.findByUnitId(dto.getUnitId()).stream().map(User::getUserId).collect(Collectors.toList());
                return root.get("userId").in(userIds);
            }else{
                return cb.and();
            }
        };
    }

    private Specification filter7(TimeDetailSearchDto dto){
        return (root, query, cb) ->{
            if(!StringUtils.isEmpty(dto.getAssigneeId())){
                return root.get("userId").in(dto.getAssigneeId());
            }else{
                return cb.and();
            }
        };
    }
}
