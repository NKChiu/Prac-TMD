package com.springboot.prac.tmdunit;

import com.google.gson.Gson;
import com.springboot.prac.PracApplication;
import com.springboot.prac.config.TestDataSourceConfig;
import com.springboot.prac.tmdap.dao.UnitDao;
import com.springboot.prac.tmdap.dao.UserDao;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {PracApplication.class, TestDataSourceConfig.class})
@ActiveProfiles({"h2"})
@Slf4j
/********************************************************************************************************************
 * 模擬 controller
 *******************************************************************************************************************/
public class MockUnitController {

    @Autowired
    private MockUnitService mockUnitService;

    /**
     * 模擬 controller 取得該系統使用者(除主管)的所有 單位 資訊
     * 改善
     *  使用 service 的方式叫取 並取得維 dto 非 entity
     */
    @Test
    public void mockUnitController_getAllUnitsExceptSuperRole(){
        log.info("取得該系統使用者(除主管)的所有 單位 資訊");
        List<MockUnitDto> unitDtos = mockUnitService.getAllUnitsExceptSuperRole();
        System.out.println(new Gson().toJson(unitDtos));
        // 預期結果如下
        // [{"unitId":"unitId1","unitName":"IT一科","superiorUnitId":"superiorId1"},{"unitId":"unitId2","unitName":"IT二科","superiorUnitId":"superiorId1"},{"unitId":"unitId3","unitName":"Test一科","superiorUnitId":"superiorId2"},{"unitId":"unitId4","unitName":"Test二科","superiorUnitId":"superiorId2"}]
    }

    /**
     * 模擬 controller 取得該系統使用者(除主管)的所有 上級單位 資訊
     * 改善
     *  使用 service 的方式叫取 並取得維 dto 非 entity
     */
    @Test
    public void mockUnitController_getAllUnitsSuperExceptSuperRole(){
        log.info("取得該系統使用者(除主管)的所有 上級單位 資訊");
        List<MockUnitDto> unitDtos = mockUnitService.getAllUnitsSuperExceptSuperRole();
        System.out.println(new Gson().toJson(unitDtos));
        // 預期結果如下
        //[{"unitId":"superiorId1","unitName":"IT部"},{"unitId":"superiorId2","unitName":"Test部"}]
    }


    /***************************** advance *********************************************************/


    /**
     * 模擬 controller 取得該系統使用者(除主管)的所有 單位 資訊         優化
     * 改善
     *  使用 service 的方式叫取 並取得維 dto 非 entity
     *  以 try catch 方式包起來，顯示 errormsg
     */
    @Test
    public void mockUnitController_getAllUnitsExceptSuperRole_advance(){
        log.info("取得該系統使用者(除主管)的所有 單位 資訊");
        List<MockUnitDto> unitDtos = mockUnitService.getAllUnitsExceptSuperRole_advance();
        System.out.println(new Gson().toJson(unitDtos));
        // 預期結果如下
        // [{"unitId":"unitId1","unitName":"IT一科","superiorUnitId":"superiorId1"},{"unitId":"unitId2","unitName":"IT二科","superiorUnitId":"superiorId1"},{"unitId":"unitId3","unitName":"Test一科","superiorUnitId":"superiorId2"},{"unitId":"unitId4","unitName":"Test二科","superiorUnitId":"superiorId2"}]
    }

    /**
     * 模擬 controller 取得該系統使用者(除主管)的所有 上級單位 資訊       優化
     * 改善
     *  使用 service 的方式叫取 並取得維 dto 非 entity
     *  以 try catch 方式包起來，顯示 errormsg
     */
    @Test
    public void mockUnitController_getAllUnitsSuperExceptSuperRole_advance(){
        log.info("取得該系統使用者(除主管)的所有 上級單位 資訊");
        List<MockUnitDto> unitDtos = mockUnitService.getAllUnitsSuperExceptSuperRole_advance();
        System.out.println(new Gson().toJson(unitDtos));
        // 預期結果如下
        //[{"unitId":"superiorId1","unitName":"IT部"},{"unitId":"superiorId2","unitName":"Test部"}]
    }


}


/********************************************************************************************************************
 * 模擬 dto
 *******************************************************************************************************************/
@Data
class MockUnitDto{
    private String unitId;
    private String unitName;
    private String superiorUnitId;
}


/********************************************************************************************************************
 * 模擬 單位 service
 *******************************************************************************************************************/
interface MockUnitService{
    /**
     * 取得該系統使用者(除主管)的所有 單位 資訊
     */
    List<MockUnitDto> getAllUnitsExceptSuperRole();

    /**
     * 取得該系統使用者(除主管)的所有 上級單位 資訊
     */
    List<MockUnitDto> getAllUnitsSuperExceptSuperRole();

    /***************************** advance *********************************************************/

    /**
     * 取得該系統使用者(除主管)的所有 單位 資訊           優化
     */
    List<MockUnitDto> getAllUnitsExceptSuperRole_advance();

    /**
     * 取得該系統使用者(除主管)的所有 上級單位 資訊         優化
     */
    List<MockUnitDto> getAllUnitsSuperExceptSuperRole_advance();
}


/********************************************************************************************************************
 * 模擬 實作 單位 service
 *******************************************************************************************************************/
@Service
@Slf4j
class MockUnitServiceImpl implements MockUnitService{

    @Autowired
    private UserDao userDao;
    @Autowired
    private UnitDao unitDao;
    @Autowired
    private ModelMapper modelMapper;

    /**
     * 取得該系統使用者(除主管)的所有 單位 資訊
     */
    @Override
    public List<MockUnitDto> getAllUnitsExceptSuperRole() {
        // 取得該系統所有使用者 除 主管 的 單位 id
        List<String> unitIds = userDao.findUnitIdExceptSuper();
        // 單位 id 取得 所有單位資訊
        List<MockUnitDto> unitDtos = unitDao.findByUnitIdIn(unitIds).stream()
                .map(u -> modelMapper.map(u, MockUnitDto.class))
                .collect(Collectors.toList());
        return unitDtos;
    }

    /**
     * 取得該系統使用者(除主管)的所有 上級單位 資訊
     */
    @Override
    public List<MockUnitDto> getAllUnitsSuperExceptSuperRole() {
        // 取得該系統所有使用者 除 主管 的 單位 id 並取得 該單位 上級 單位 id
        List<String> superUnitIds = this.getAllUnitsExceptSuperRole().stream().map(u -> u.getSuperiorUnitId()).collect(Collectors.toList());
        // 上級單位 id 取得 單位資訊
        List<MockUnitDto> unitDtos = unitDao.findByUnitIdIn(superUnitIds).stream()
                .map(u -> modelMapper.map(u, MockUnitDto.class))
                .collect(Collectors.toList());
        return unitDtos;
    }


    /***************************** advance *********************************************************/

    /**
     * 取得該系統使用者(除主管)的所有 單位 資訊           優化
     */
    @Override
    public List<MockUnitDto> getAllUnitsExceptSuperRole_advance() {

        List<MockUnitDto> unitDtos = new ArrayList<>();
        try{
            // 取得該系統所有使用者 除 主管 的 單位 id
            List<String> unitIds = userDao.findUnitIdExceptSuper();
            // 單位 id 取得 所有單位資訊
            unitDtos = unitDao.findByUnitIdIn(unitIds).stream()
                    .map(u -> modelMapper.map(u, MockUnitDto.class))
                    .collect(Collectors.toList());
        }catch (Exception e){
            log.error("取不到單位資訊");
            throw new RuntimeException("取不到單位資訊");
        }

        return unitDtos;
    }

    /**
     * 取得該系統使用者(除主管)的所有 上級單位 資訊         優化
     */
    @Override
    public List<MockUnitDto> getAllUnitsSuperExceptSuperRole_advance() {

        List<MockUnitDto> unitDtos = new ArrayList<>();
        try{
            // 取得該系統所有使用者 除 主管 的 單位 id
            List<String> unitIds = userDao.findUnitIdExceptSuper();
            // 單位 id 取得 所有單位資訊
            unitDtos = unitDao.findByUnitIdIn(unitIds).stream()
                    .map(u -> modelMapper.map(u, MockUnitDto.class))
                    .collect(Collectors.toList());
        }catch (Exception e){
            log.error("取不到單位資訊");
            throw new RuntimeException("取不到單位資訊");
        }

        List<MockUnitDto> unitDtoList = new ArrayList<>();
        try {
            // 取得該系統所有使用者 除 主管 的 單位 id 並取得 該單位 上級 單位 id
            List<String> superUnitIds = unitDtos.stream()
                                        .map(u -> u.getSuperiorUnitId())
                                        .collect(Collectors.toList());
            // 上級單位 id 取得 單位資訊
            unitDtoList = unitDao.findByUnitIdIn(superUnitIds).stream()
                                .map(u -> modelMapper.map(u, MockUnitDto.class))
                                .collect(Collectors.toList());
        }catch (Exception e){
            log.error("取不到上級單位資訊");
            throw new RuntimeException("取不到上級單位資訊");
        }

        return unitDtoList;
    }



}