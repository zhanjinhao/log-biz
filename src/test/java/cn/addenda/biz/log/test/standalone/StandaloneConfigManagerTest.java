package cn.addenda.biz.log.test.standalone;

import cn.addenda.biz.log.LogApplication;
import cn.addenda.biz.log.entity.StandaloneConfig;
import cn.addenda.biz.log.manager.StandaloneConfigManager;
import cn.addenda.component.base.collection.ArrayUtils;
import cn.addenda.mybatisbasemodel.simple.SimpleBaseModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@SpringBootTest(classes = LogApplication.class)
class StandaloneConfigManagerTest {

  @Autowired
  private StandaloneConfigManager standaloneConfigManager;

  @BeforeEach
  void beforeEach() {
    SimpleBaseModel.pushUser("addenda");
  }

  @Test
  void testSingle() {
    StandaloneConfig param = StandaloneConfig.ofParam();
    param.setCode("w");
    param.setName("王1");
    standaloneConfigManager.insert(param);

    StandaloneConfig paramState1 = standaloneConfigManager.queryById(param.getId());
    Assertions.assertEquals("王1", paramState1.getName());

    StandaloneConfig standaloneConfig = standaloneConfigManager.queryById(param.getId());
    standaloneConfigManager.update(standaloneConfig, new Consumer<StandaloneConfig>() {
      @Override
      public void accept(StandaloneConfig standaloneConfig) {
        standaloneConfig.setName("王2");
      }
    });

    StandaloneConfig paramState2 = standaloneConfigManager.queryById(param.getId());
    Assertions.assertEquals("王2", paramState2.getName());

    standaloneConfigManager.delete(param.getId());

    StandaloneConfig paramState3 = standaloneConfigManager.queryById(param.getId());
    Assertions.assertNull(paramState3);
  }


  @Test
  void testBatch() {
    StandaloneConfig param1 = StandaloneConfig.ofParam();
    param1.setCode("l");
    param1.setName("李1");
    StandaloneConfig param2 = StandaloneConfig.ofParam();
    param2.setCode("z");
    param2.setName("赵1");
    standaloneConfigManager.batchInsert(ArrayUtils.asArrayList(param1, param2));

    StandaloneConfig param1State1 = standaloneConfigManager.queryById(param1.getId());
    StandaloneConfig param2State1 = standaloneConfigManager.queryById(param2.getId());
    Assertions.assertEquals("李1", param1State1.getName());
    Assertions.assertEquals("赵1", param2State1.getName());

    StandaloneConfig standaloneConfig1 = standaloneConfigManager.queryById(param1.getId());
    StandaloneConfig standaloneConfig2 = standaloneConfigManager.queryById(param2.getId());
    standaloneConfigManager.batchUpdate(ArrayUtils.asArrayList(standaloneConfig1, standaloneConfig2),
            new Consumer<List<StandaloneConfig>>() {
              @Override
              public void accept(List<StandaloneConfig> standaloneConfigs) {
                for (StandaloneConfig standaloneConfig : standaloneConfigs) {
                  if (Objects.equals(param1.getId(), standaloneConfig.getId())) {
                    standaloneConfig.setName("李2");
                  }
                  if (Objects.equals(param2.getId(), standaloneConfig.getId())) {
                    standaloneConfig.setName("赵2");
                  }
                }
              }
            });

    StandaloneConfig param1State2 = standaloneConfigManager.queryById(param1.getId());
    StandaloneConfig param2State2 = standaloneConfigManager.queryById(param2.getId());
    Assertions.assertEquals("李2", param1State2.getName());
    Assertions.assertEquals("赵2", param2State2.getName());

    standaloneConfigManager.batchDelete(ArrayUtils.asArrayList(param1.getId(), param2.getId()));

    StandaloneConfig param1State3 = standaloneConfigManager.queryById(param1.getId());
    StandaloneConfig param2State3 = standaloneConfigManager.queryById(param2.getId());
    Assertions.assertNull(param1State3);
    Assertions.assertNull(param2State3);
  }


  @AfterEach
  void afterEach() {
    SimpleBaseModel.popUser();
  }

}
