package cn.addenda.biz.log.test.cascade;

import cn.addenda.biz.log.LogApplication;
import cn.addenda.biz.log.bo.CascadeCurrentConfigSplitBo;
import cn.addenda.biz.log.bo.CascadeParentConfigSplitBo;
import cn.addenda.biz.log.bo.CascadeSnapshotBo;
import cn.addenda.biz.log.entity.*;
import cn.addenda.biz.log.manager.CascadeChildConfigManager;
import cn.addenda.biz.log.manager.CascadeConfigManager;
import cn.addenda.biz.log.manager.CascadeHisStateConfigManager;
import cn.addenda.component.base.collection.ArrayUtils;
import cn.addenda.component.base.string.StringUtils;
import cn.addenda.mybatisbasemodel.simple.SimpleBaseModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest(classes = LogApplication.class)
class CascadeConfigManagerTest {

  @Autowired
  private CascadeConfigManager cascadeConfigManager;

  @Autowired
  private CascadeChildConfigManager cascadeChildConfigManager;

  @Autowired
  private CascadeHisStateConfigManager cascadeHisStateConfigManager;

  @BeforeEach
  void beforeEach() {
    SimpleBaseModel.pushUser("addenda");
  }

  @Test
  void testSingle() {
    CascadeParentConfig parentConfig = CascadeParentConfig.ofParam();
    parentConfig.setCode("1");
    parentConfig.setName("11");
    cascadeConfigManager.insertParent(parentConfig);
    Assertions.assertEquals("11", queryHisState(parentConfig.getId()));
    Assertions.assertEquals("11", queryHisState2(parentConfig.getId()));

    CascadeCurrentConfig currentConfig1 = CascadeCurrentConfig.ofParam();
    currentConfig1.setCode("2");
    currentConfig1.setName("21");
    currentConfig1.setForeignId(parentConfig.getId());
    currentConfig1.setForeignLogSnapshotId(parentConfig.getLogSnapshotId());
    cascadeConfigManager.insertCurrent(currentConfig1);
    Assertions.assertEquals("11-21", queryHisState(parentConfig.getId()));
    Assertions.assertEquals("11-21", queryHisState2(parentConfig.getId()));

    CascadeCurrentConfig currentConfig2 = CascadeCurrentConfig.ofParam();
    currentConfig2.setCode("3");
    currentConfig2.setName("31");
    currentConfig2.setForeignId(parentConfig.getId());
    currentConfig2.setForeignLogSnapshotId(parentConfig.getLogSnapshotId());
    cascadeConfigManager.insertCurrent(currentConfig2);
    Assertions.assertEquals("11-21-31", queryHisState(parentConfig.getId()));
    Assertions.assertEquals("11-21-31", queryHisState2(parentConfig.getId()));

    CascadeChildConfig childConfig1 = CascadeChildConfig.ofParam();
    childConfig1.setCode("4");
    childConfig1.setName("41");
    childConfig1.setForeignId(currentConfig1.getId());
    childConfig1.setForeignLogSnapshotId(currentConfig1.getLogSnapshotId());
    cascadeConfigManager.insertChild(childConfig1);
    Assertions.assertEquals("11-21-31-41", queryHisState(parentConfig.getId()));
    Assertions.assertEquals("11-21-31-41", queryHisState2(parentConfig.getId()));

    CascadeSnapshotBo cascadeSnapshotBo_1 = cascadeConfigManager.querySnapshot(childConfig1.getLogSnapshotId());
    Assertions.assertEquals("11-21-41", cascadeSnapshotBo_1.cascadedName());

    CascadeChildConfig childConfig2 = CascadeChildConfig.ofParam();
    childConfig2.setCode("5");
    childConfig2.setName("51");
    childConfig2.setForeignId(currentConfig1.getId());
    childConfig2.setForeignLogSnapshotId(currentConfig1.getLogSnapshotId());
    cascadeConfigManager.insertChild(childConfig2);
    Assertions.assertEquals("11-21-31-41-51", queryHisState(parentConfig.getId()));
    Assertions.assertEquals("11-21-31-41-51", queryHisState2(parentConfig.getId()));

    CascadeSnapshotBo cascadeSnapshotBo_2 = cascadeConfigManager.querySnapshot(childConfig2.getLogSnapshotId());
    Assertions.assertEquals("11-21-51", cascadeSnapshotBo_2.cascadedName());

    CascadeChildConfig childConfig3 = CascadeChildConfig.ofParam();
    childConfig3.setCode("6");
    childConfig3.setName("61");
    childConfig3.setForeignId(currentConfig2.getId());
    childConfig3.setForeignLogSnapshotId(currentConfig2.getLogSnapshotId());
    cascadeConfigManager.insertChild(childConfig3);
    Assertions.assertEquals("11-21-31-41-51-61", queryHisState(parentConfig.getId()));
    Assertions.assertEquals("11-21-31-41-51-61", queryHisState2(parentConfig.getId()));

    CascadeSnapshotBo cascadeSnapshotBo_3 = cascadeConfigManager.querySnapshot(childConfig3.getLogSnapshotId());
    Assertions.assertEquals("11-31-61", cascadeSnapshotBo_3.cascadedName());

    CascadeChildConfig childConfig4 = CascadeChildConfig.ofParam();
    childConfig4.setCode("7");
    childConfig4.setName("71");
    childConfig4.setForeignId(currentConfig2.getId());
    childConfig4.setForeignLogSnapshotId(currentConfig2.getLogSnapshotId());
    cascadeConfigManager.insertChild(childConfig4);
    Assertions.assertEquals("11-21-31-41-51-61-71", queryHisState(parentConfig.getId()));
    Assertions.assertEquals("11-21-31-41-51-61-71", queryHisState2(parentConfig.getId()));

    CascadeSnapshotBo cascadeSnapshotBo_4 = cascadeConfigManager.querySnapshot(childConfig4.getLogSnapshotId());
    Assertions.assertEquals("11-31-71", cascadeSnapshotBo_4.cascadedName());

    cascadeConfigManager.updateParent(parentConfig.getId(), "12");
    Assertions.assertEquals("12-21-31-41-51-61-71", queryHisState(parentConfig.getId()));
    Assertions.assertEquals("12-21-31-41-51-61-71", queryHisState2(parentConfig.getId()));

    CascadeSnapshotBo cascadeSnapshotBo_5_1 = cascadeConfigManager.querySnapshot(childConfig1.getLogSnapshotId());
    Assertions.assertEquals("11-21-41", cascadeSnapshotBo_5_1.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_6_1 = cascadeConfigManager.querySnapshot(childConfig2.getLogSnapshotId());
    Assertions.assertEquals("11-21-51", cascadeSnapshotBo_6_1.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_7_1 = cascadeConfigManager.querySnapshot(childConfig3.getLogSnapshotId());
    Assertions.assertEquals("11-31-61", cascadeSnapshotBo_7_1.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_8_1 = cascadeConfigManager.querySnapshot(childConfig4.getLogSnapshotId());
    Assertions.assertEquals("11-31-71", cascadeSnapshotBo_8_1.cascadedName());

    CascadeSnapshotBo cascadeSnapshotBo_5_2 = cascadeConfigManager.querySnapshot(cascadeChildConfigManager.queryById(childConfig1.getId()).getLogSnapshotId());
    Assertions.assertEquals("12-21-41", cascadeSnapshotBo_5_2.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_6_2 = cascadeConfigManager.querySnapshot(cascadeChildConfigManager.queryById(childConfig2.getId()).getLogSnapshotId());
    Assertions.assertEquals("12-21-51", cascadeSnapshotBo_6_2.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_7_2 = cascadeConfigManager.querySnapshot(cascadeChildConfigManager.queryById(childConfig3.getId()).getLogSnapshotId());
    Assertions.assertEquals("12-31-61", cascadeSnapshotBo_7_2.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_8_2 = cascadeConfigManager.querySnapshot(cascadeChildConfigManager.queryById(childConfig4.getId()).getLogSnapshotId());
    Assertions.assertEquals("12-31-71", cascadeSnapshotBo_8_2.cascadedName());

    cascadeConfigManager.updateCurrent(currentConfig1.getId(), "22");
    Assertions.assertEquals("12-22-31-41-51-61-71", queryHisState(parentConfig.getId()));
    Assertions.assertEquals("12-22-31-41-51-61-71", queryHisState2(parentConfig.getId()));

    CascadeSnapshotBo cascadeSnapshotBo_9_1 = cascadeConfigManager.querySnapshot(childConfig1.getLogSnapshotId());
    Assertions.assertEquals("11-21-41", cascadeSnapshotBo_9_1.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_10_1 = cascadeConfigManager.querySnapshot(childConfig2.getLogSnapshotId());
    Assertions.assertEquals("11-21-51", cascadeSnapshotBo_10_1.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_11_1 = cascadeConfigManager.querySnapshot(childConfig3.getLogSnapshotId());
    Assertions.assertEquals("11-31-61", cascadeSnapshotBo_11_1.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_12_1 = cascadeConfigManager.querySnapshot(childConfig4.getLogSnapshotId());
    Assertions.assertEquals("11-31-71", cascadeSnapshotBo_12_1.cascadedName());

    CascadeSnapshotBo cascadeSnapshotBo_9_2 = cascadeConfigManager.querySnapshot(cascadeChildConfigManager.queryById(childConfig1.getId()).getLogSnapshotId());
    Assertions.assertEquals("12-22-41", cascadeSnapshotBo_9_2.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_10_2 = cascadeConfigManager.querySnapshot(cascadeChildConfigManager.queryById(childConfig2.getId()).getLogSnapshotId());
    Assertions.assertEquals("12-22-51", cascadeSnapshotBo_10_2.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_11_2 = cascadeConfigManager.querySnapshot(cascadeChildConfigManager.queryById(childConfig3.getId()).getLogSnapshotId());
    Assertions.assertEquals("12-31-61", cascadeSnapshotBo_11_2.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_12_2 = cascadeConfigManager.querySnapshot(cascadeChildConfigManager.queryById(childConfig4.getId()).getLogSnapshotId());
    Assertions.assertEquals("12-31-71", cascadeSnapshotBo_12_2.cascadedName());

    cascadeConfigManager.updateChild(childConfig1.getId(), "42");
    Assertions.assertEquals("12-22-31-42-51-61-71", queryHisState(parentConfig.getId()));
    Assertions.assertEquals("12-22-31-42-51-61-71", queryHisState2(parentConfig.getId()));

    CascadeSnapshotBo cascadeSnapshotBo_13_1 = cascadeConfigManager.querySnapshot(childConfig1.getLogSnapshotId());
    Assertions.assertEquals("11-21-41", cascadeSnapshotBo_13_1.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_14_1 = cascadeConfigManager.querySnapshot(childConfig2.getLogSnapshotId());
    Assertions.assertEquals("11-21-51", cascadeSnapshotBo_14_1.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_15_1 = cascadeConfigManager.querySnapshot(childConfig3.getLogSnapshotId());
    Assertions.assertEquals("11-31-61", cascadeSnapshotBo_15_1.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_16_1 = cascadeConfigManager.querySnapshot(childConfig4.getLogSnapshotId());
    Assertions.assertEquals("11-31-71", cascadeSnapshotBo_16_1.cascadedName());

    CascadeSnapshotBo cascadeSnapshotBo_13_2 = cascadeConfigManager.querySnapshot(cascadeChildConfigManager.queryById(childConfig1.getId()).getLogSnapshotId());
    Assertions.assertEquals("12-22-42", cascadeSnapshotBo_13_2.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_14_2 = cascadeConfigManager.querySnapshot(cascadeChildConfigManager.queryById(childConfig2.getId()).getLogSnapshotId());
    Assertions.assertEquals("12-22-51", cascadeSnapshotBo_14_2.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_15_2 = cascadeConfigManager.querySnapshot(cascadeChildConfigManager.queryById(childConfig3.getId()).getLogSnapshotId());
    Assertions.assertEquals("12-31-61", cascadeSnapshotBo_15_2.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_16_2 = cascadeConfigManager.querySnapshot(cascadeChildConfigManager.queryById(childConfig4.getId()).getLogSnapshotId());
    Assertions.assertEquals("12-31-71", cascadeSnapshotBo_16_2.cascadedName());


    List<CascadeParentConfigSplitBo> cascadeParentConfigSplitBoList = cascadeConfigManager.split(parentConfig.getId());
    CascadeParentConfigSplitBo cascadeParentConfigSplitBo1 = cascadeParentConfigSplitBoList.get(0);
    CascadeParentConfigSplitBo cascadeParentConfigSplitBo2 = cascadeParentConfigSplitBoList.get(1);

    CascadeCurrentConfigSplitBo cascadeCurrentConfigSplitBo1 = cascadeParentConfigSplitBo1.getCascadeCurrentConfigBoList().get(0);
    CascadeCurrentConfigSplitBo cascadeCurrentConfigSplitBo2 = cascadeParentConfigSplitBo1.getCascadeCurrentConfigBoList().get(1);
    CascadeCurrentConfigSplitBo cascadeCurrentConfigSplitBo3 = cascadeParentConfigSplitBo2.getCascadeCurrentConfigBoList().get(0);
    CascadeCurrentConfigSplitBo cascadeCurrentConfigSplitBo4 = cascadeParentConfigSplitBo2.getCascadeCurrentConfigBoList().get(1);

    CascadeChildConfig cascadeChildConfigSplitBo1 = cascadeCurrentConfigSplitBo1.getCascadeChildConfigList().get(0);
    CascadeChildConfig cascadeChildConfigSplitBo2 = cascadeCurrentConfigSplitBo1.getCascadeChildConfigList().get(1);
    CascadeChildConfig cascadeChildConfigSplitBo3 = cascadeCurrentConfigSplitBo2.getCascadeChildConfigList().get(0);
    CascadeChildConfig cascadeChildConfigSplitBo4 = cascadeCurrentConfigSplitBo2.getCascadeChildConfigList().get(1);
    CascadeChildConfig cascadeChildConfigSplitBo5 = cascadeCurrentConfigSplitBo3.getCascadeChildConfigList().get(0);
    CascadeChildConfig cascadeChildConfigSplitBo6 = cascadeCurrentConfigSplitBo3.getCascadeChildConfigList().get(1);
    CascadeChildConfig cascadeChildConfigSplitBo7 = cascadeCurrentConfigSplitBo4.getCascadeChildConfigList().get(0);
    CascadeChildConfig cascadeChildConfigSplitBo8 = cascadeCurrentConfigSplitBo4.getCascadeChildConfigList().get(1);

    Assertions.assertEquals("12copy1-22copy1-42copy1", cascadeConfigManager.querySnapshot(cascadeChildConfigSplitBo1.getLogSnapshotId()).cascadedName());
    Assertions.assertEquals("12copy1-22copy1-51copy1", cascadeConfigManager.querySnapshot(cascadeChildConfigSplitBo2.getLogSnapshotId()).cascadedName());
    Assertions.assertEquals("12copy1-31copy1-61copy1", cascadeConfigManager.querySnapshot(cascadeChildConfigSplitBo3.getLogSnapshotId()).cascadedName());
    Assertions.assertEquals("12copy1-31copy1-71copy1", cascadeConfigManager.querySnapshot(cascadeChildConfigSplitBo4.getLogSnapshotId()).cascadedName());
    Assertions.assertEquals("12copy2-22copy2-42copy2", cascadeConfigManager.querySnapshot(cascadeChildConfigSplitBo5.getLogSnapshotId()).cascadedName());
    Assertions.assertEquals("12copy2-22copy2-51copy2", cascadeConfigManager.querySnapshot(cascadeChildConfigSplitBo6.getLogSnapshotId()).cascadedName());
    Assertions.assertEquals("12copy2-31copy2-61copy2", cascadeConfigManager.querySnapshot(cascadeChildConfigSplitBo7.getLogSnapshotId()).cascadedName());
    Assertions.assertEquals("12copy2-31copy2-71copy2", cascadeConfigManager.querySnapshot(cascadeChildConfigSplitBo8.getLogSnapshotId()).cascadedName());

    Assertions.assertEquals("12copy1-22copy1-31copy1-42copy1-51copy1-61copy1-71copy1", queryHisState(cascadeParentConfigSplitBo1.getId()));
    Assertions.assertEquals("12copy1-22copy1-31copy1-42copy1-51copy1-61copy1-71copy1", queryHisState2(cascadeParentConfigSplitBo1.getId()));
    Assertions.assertEquals("12copy2-22copy2-31copy2-42copy2-51copy2-61copy2-71copy2", queryHisState(cascadeParentConfigSplitBo2.getId()));
    Assertions.assertEquals("12copy2-22copy2-31copy2-42copy2-51copy2-61copy2-71copy2", queryHisState2(cascadeParentConfigSplitBo2.getId()));
  }


  @Test
  void testBatch() {
    CascadeParentConfig parentConfig1 = CascadeParentConfig.ofParam();
    parentConfig1.setCode("a");
    parentConfig1.setName("a1");
    CascadeParentConfig parentConfig2 = CascadeParentConfig.ofParam();
    parentConfig2.setCode("b");
    parentConfig2.setName("b1");
    cascadeConfigManager.batchInsertParent(ArrayUtils.asArrayList(parentConfig1, parentConfig2));
    Assertions.assertEquals("a1", queryHisState(parentConfig1.getId()));
    Assertions.assertEquals("a1", queryHisState2(parentConfig1.getId()));
    Assertions.assertEquals("b1", queryHisState(parentConfig2.getId()));
    Assertions.assertEquals("b1", queryHisState2(parentConfig2.getId()));

    CascadeCurrentConfig currentConfig1 = CascadeCurrentConfig.ofParam();
    currentConfig1.setCode("c");
    currentConfig1.setName("c1");
    currentConfig1.setForeignId(parentConfig1.getId());
    currentConfig1.setForeignLogSnapshotId(parentConfig1.getLogSnapshotId());
    CascadeCurrentConfig currentConfig2 = CascadeCurrentConfig.ofParam();
    currentConfig2.setCode("d");
    currentConfig2.setName("d1");
    currentConfig2.setForeignId(parentConfig1.getId());
    currentConfig2.setForeignLogSnapshotId(parentConfig1.getLogSnapshotId());
    CascadeCurrentConfig currentConfig3 = CascadeCurrentConfig.ofParam();
    currentConfig3.setCode("e");
    currentConfig3.setName("e1");
    currentConfig3.setForeignId(parentConfig2.getId());
    currentConfig3.setForeignLogSnapshotId(parentConfig2.getLogSnapshotId());
    CascadeCurrentConfig currentConfig4 = CascadeCurrentConfig.ofParam();
    currentConfig4.setCode("f");
    currentConfig4.setName("f1");
    currentConfig4.setForeignId(parentConfig2.getId());
    currentConfig4.setForeignLogSnapshotId(parentConfig2.getLogSnapshotId());
    cascadeConfigManager.batchInsertCurrent(ArrayUtils.asArrayList(currentConfig1, currentConfig2, currentConfig3, currentConfig4));
    Assertions.assertEquals("a1-c1-d1", queryHisState(parentConfig1.getId()));
    Assertions.assertEquals("a1-c1-d1", queryHisState2(parentConfig1.getId()));
    Assertions.assertEquals("b1-e1-f1", queryHisState(parentConfig2.getId()));
    Assertions.assertEquals("b1-e1-f1", queryHisState2(parentConfig2.getId()));

    CascadeChildConfig childConfig1 = CascadeChildConfig.ofParam();
    childConfig1.setCode("g");
    childConfig1.setName("g1");
    childConfig1.setForeignId(currentConfig1.getId());
    childConfig1.setForeignLogSnapshotId(currentConfig1.getLogSnapshotId());
    CascadeChildConfig childConfig2 = CascadeChildConfig.ofParam();
    childConfig2.setCode("h");
    childConfig2.setName("h1");
    childConfig2.setForeignId(currentConfig1.getId());
    childConfig2.setForeignLogSnapshotId(currentConfig1.getLogSnapshotId());
    CascadeChildConfig childConfig3 = CascadeChildConfig.ofParam();
    childConfig3.setCode("i");
    childConfig3.setName("i1");
    childConfig3.setForeignId(currentConfig2.getId());
    childConfig3.setForeignLogSnapshotId(currentConfig2.getLogSnapshotId());
    CascadeChildConfig childConfig4 = CascadeChildConfig.ofParam();
    childConfig4.setCode("j");
    childConfig4.setName("j1");
    childConfig4.setForeignId(currentConfig2.getId());
    childConfig4.setForeignLogSnapshotId(currentConfig2.getLogSnapshotId());
    CascadeChildConfig childConfig5 = CascadeChildConfig.ofParam();
    childConfig5.setCode("k");
    childConfig5.setName("k1");
    childConfig5.setForeignId(currentConfig3.getId());
    childConfig5.setForeignLogSnapshotId(currentConfig3.getLogSnapshotId());
    CascadeChildConfig childConfig6 = CascadeChildConfig.ofParam();
    childConfig6.setCode("l");
    childConfig6.setName("l1");
    childConfig6.setForeignId(currentConfig3.getId());
    childConfig6.setForeignLogSnapshotId(currentConfig3.getLogSnapshotId());
    CascadeChildConfig childConfig7 = CascadeChildConfig.ofParam();
    childConfig7.setCode("m");
    childConfig7.setName("m1");
    childConfig7.setForeignId(currentConfig4.getId());
    childConfig7.setForeignLogSnapshotId(currentConfig4.getLogSnapshotId());
    CascadeChildConfig childConfig8 = CascadeChildConfig.ofParam();
    childConfig8.setCode("n");
    childConfig8.setName("n1");
    childConfig8.setForeignId(currentConfig4.getId());
    childConfig8.setForeignLogSnapshotId(currentConfig4.getLogSnapshotId());
    cascadeConfigManager.batchInsertChild(
            ArrayUtils.asArrayList(
                    childConfig1, childConfig2, childConfig3, childConfig4,
                    childConfig5, childConfig6, childConfig7, childConfig8));
    Assertions.assertEquals("a1-c1-d1-g1-h1-i1-j1", queryHisState(parentConfig1.getId()));
    Assertions.assertEquals("a1-c1-d1-g1-h1-i1-j1", queryHisState2(parentConfig1.getId()));
    Assertions.assertEquals("b1-e1-f1-k1-l1-m1-n1", queryHisState(parentConfig2.getId()));
    Assertions.assertEquals("b1-e1-f1-k1-l1-m1-n1", queryHisState2(parentConfig2.getId()));


    CascadeSnapshotBo cascadeSnapshotBo_1 = cascadeConfigManager.querySnapshot(childConfig1.getLogSnapshotId());
    Assertions.assertEquals("a1-c1-g1", cascadeSnapshotBo_1.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_2 = cascadeConfigManager.querySnapshot(childConfig2.getLogSnapshotId());
    Assertions.assertEquals("a1-c1-h1", cascadeSnapshotBo_2.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_3 = cascadeConfigManager.querySnapshot(childConfig3.getLogSnapshotId());
    Assertions.assertEquals("a1-d1-i1", cascadeSnapshotBo_3.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_4 = cascadeConfigManager.querySnapshot(childConfig4.getLogSnapshotId());
    Assertions.assertEquals("a1-d1-j1", cascadeSnapshotBo_4.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_5 = cascadeConfigManager.querySnapshot(childConfig5.getLogSnapshotId());
    Assertions.assertEquals("b1-e1-k1", cascadeSnapshotBo_5.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_6 = cascadeConfigManager.querySnapshot(childConfig6.getLogSnapshotId());
    Assertions.assertEquals("b1-e1-l1", cascadeSnapshotBo_6.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_7 = cascadeConfigManager.querySnapshot(childConfig7.getLogSnapshotId());
    Assertions.assertEquals("b1-f1-m1", cascadeSnapshotBo_7.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_8 = cascadeConfigManager.querySnapshot(childConfig8.getLogSnapshotId());
    Assertions.assertEquals("b1-f1-n1", cascadeSnapshotBo_8.cascadedName());


    Map<Long, String> updateParentParam = new HashMap<>();
    updateParentParam.put(parentConfig1.getId(), "a2");
    updateParentParam.put(parentConfig2.getId(), "b2");
    cascadeConfigManager.batchUpdateParent(updateParentParam);
    Assertions.assertEquals("a2-c1-d1-g1-h1-i1-j1", queryHisState(parentConfig1.getId()));
    Assertions.assertEquals("a2-c1-d1-g1-h1-i1-j1", queryHisState2(parentConfig1.getId()));
    Assertions.assertEquals("b2-e1-f1-k1-l1-m1-n1", queryHisState(parentConfig2.getId()));
    Assertions.assertEquals("b2-e1-f1-k1-l1-m1-n1", queryHisState2(parentConfig2.getId()));


    CascadeSnapshotBo cascadeSnapshotBo_9_1 = cascadeConfigManager.querySnapshot(childConfig1.getLogSnapshotId());
    Assertions.assertEquals("a1-c1-g1", cascadeSnapshotBo_9_1.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_10_1 = cascadeConfigManager.querySnapshot(childConfig2.getLogSnapshotId());
    Assertions.assertEquals("a1-c1-h1", cascadeSnapshotBo_10_1.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_11_1 = cascadeConfigManager.querySnapshot(childConfig3.getLogSnapshotId());
    Assertions.assertEquals("a1-d1-i1", cascadeSnapshotBo_11_1.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_12_1 = cascadeConfigManager.querySnapshot(childConfig4.getLogSnapshotId());
    Assertions.assertEquals("a1-d1-j1", cascadeSnapshotBo_12_1.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_13_1 = cascadeConfigManager.querySnapshot(childConfig5.getLogSnapshotId());
    Assertions.assertEquals("b1-e1-k1", cascadeSnapshotBo_13_1.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_14_1 = cascadeConfigManager.querySnapshot(childConfig6.getLogSnapshotId());
    Assertions.assertEquals("b1-e1-l1", cascadeSnapshotBo_14_1.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_15_1 = cascadeConfigManager.querySnapshot(childConfig7.getLogSnapshotId());
    Assertions.assertEquals("b1-f1-m1", cascadeSnapshotBo_15_1.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_16_1 = cascadeConfigManager.querySnapshot(childConfig8.getLogSnapshotId());
    Assertions.assertEquals("b1-f1-n1", cascadeSnapshotBo_16_1.cascadedName());

    CascadeSnapshotBo cascadeSnapshotBo_9_2 = cascadeConfigManager.querySnapshot(cascadeChildConfigManager.queryById(childConfig1.getId()).getLogSnapshotId());
    Assertions.assertEquals("a2-c1-g1", cascadeSnapshotBo_9_2.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_10_2 = cascadeConfigManager.querySnapshot(cascadeChildConfigManager.queryById(childConfig2.getId()).getLogSnapshotId());
    Assertions.assertEquals("a2-c1-h1", cascadeSnapshotBo_10_2.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_11_2 = cascadeConfigManager.querySnapshot(cascadeChildConfigManager.queryById(childConfig3.getId()).getLogSnapshotId());
    Assertions.assertEquals("a2-d1-i1", cascadeSnapshotBo_11_2.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_12_2 = cascadeConfigManager.querySnapshot(cascadeChildConfigManager.queryById(childConfig4.getId()).getLogSnapshotId());
    Assertions.assertEquals("a2-d1-j1", cascadeSnapshotBo_12_2.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_13_2 = cascadeConfigManager.querySnapshot(cascadeChildConfigManager.queryById(childConfig5.getId()).getLogSnapshotId());
    Assertions.assertEquals("b2-e1-k1", cascadeSnapshotBo_13_2.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_14_2 = cascadeConfigManager.querySnapshot(cascadeChildConfigManager.queryById(childConfig6.getId()).getLogSnapshotId());
    Assertions.assertEquals("b2-e1-l1", cascadeSnapshotBo_14_2.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_15_2 = cascadeConfigManager.querySnapshot(cascadeChildConfigManager.queryById(childConfig7.getId()).getLogSnapshotId());
    Assertions.assertEquals("b2-f1-m1", cascadeSnapshotBo_15_2.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_16_2 = cascadeConfigManager.querySnapshot(cascadeChildConfigManager.queryById(childConfig8.getId()).getLogSnapshotId());
    Assertions.assertEquals("b2-f1-n1", cascadeSnapshotBo_16_2.cascadedName());


    Map<Long, String> updateCurrentParam = new HashMap<>();
    updateCurrentParam.put(currentConfig1.getId(), "c2");
    updateCurrentParam.put(currentConfig2.getId(), "d2");
    cascadeConfigManager.batchUpdateCurrent(updateCurrentParam);

    Assertions.assertEquals("a2-c2-d2-g1-h1-i1-j1", queryHisState(parentConfig1.getId()));
    Assertions.assertEquals("a2-c2-d2-g1-h1-i1-j1", queryHisState2(parentConfig1.getId()));
    Assertions.assertEquals("b2-e1-f1-k1-l1-m1-n1", queryHisState(parentConfig2.getId()));
    Assertions.assertEquals("b2-e1-f1-k1-l1-m1-n1", queryHisState2(parentConfig2.getId()));

    CascadeSnapshotBo cascadeSnapshotBo_17_1 = cascadeConfigManager.querySnapshot(childConfig1.getLogSnapshotId());
    Assertions.assertEquals("a1-c1-g1", cascadeSnapshotBo_17_1.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_18_1 = cascadeConfigManager.querySnapshot(childConfig2.getLogSnapshotId());
    Assertions.assertEquals("a1-c1-h1", cascadeSnapshotBo_18_1.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_19_1 = cascadeConfigManager.querySnapshot(childConfig3.getLogSnapshotId());
    Assertions.assertEquals("a1-d1-i1", cascadeSnapshotBo_19_1.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_20_1 = cascadeConfigManager.querySnapshot(childConfig4.getLogSnapshotId());
    Assertions.assertEquals("a1-d1-j1", cascadeSnapshotBo_20_1.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_21_1 = cascadeConfigManager.querySnapshot(childConfig5.getLogSnapshotId());
    Assertions.assertEquals("b1-e1-k1", cascadeSnapshotBo_21_1.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_22_1 = cascadeConfigManager.querySnapshot(childConfig6.getLogSnapshotId());
    Assertions.assertEquals("b1-e1-l1", cascadeSnapshotBo_22_1.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_23_1 = cascadeConfigManager.querySnapshot(childConfig7.getLogSnapshotId());
    Assertions.assertEquals("b1-f1-m1", cascadeSnapshotBo_23_1.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_24_1 = cascadeConfigManager.querySnapshot(childConfig8.getLogSnapshotId());
    Assertions.assertEquals("b1-f1-n1", cascadeSnapshotBo_24_1.cascadedName());

    CascadeSnapshotBo cascadeSnapshotBo_25_2 = cascadeConfigManager.querySnapshot(cascadeChildConfigManager.queryById(childConfig1.getId()).getLogSnapshotId());
    Assertions.assertEquals("a2-c2-g1", cascadeSnapshotBo_25_2.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_26_2 = cascadeConfigManager.querySnapshot(cascadeChildConfigManager.queryById(childConfig2.getId()).getLogSnapshotId());
    Assertions.assertEquals("a2-c2-h1", cascadeSnapshotBo_26_2.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_27_2 = cascadeConfigManager.querySnapshot(cascadeChildConfigManager.queryById(childConfig3.getId()).getLogSnapshotId());
    Assertions.assertEquals("a2-d2-i1", cascadeSnapshotBo_27_2.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_28_2 = cascadeConfigManager.querySnapshot(cascadeChildConfigManager.queryById(childConfig4.getId()).getLogSnapshotId());
    Assertions.assertEquals("a2-d2-j1", cascadeSnapshotBo_28_2.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_29_2 = cascadeConfigManager.querySnapshot(cascadeChildConfigManager.queryById(childConfig5.getId()).getLogSnapshotId());
    Assertions.assertEquals("b2-e1-k1", cascadeSnapshotBo_29_2.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_30_2 = cascadeConfigManager.querySnapshot(cascadeChildConfigManager.queryById(childConfig6.getId()).getLogSnapshotId());
    Assertions.assertEquals("b2-e1-l1", cascadeSnapshotBo_30_2.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_31_2 = cascadeConfigManager.querySnapshot(cascadeChildConfigManager.queryById(childConfig7.getId()).getLogSnapshotId());
    Assertions.assertEquals("b2-f1-m1", cascadeSnapshotBo_31_2.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_32_2 = cascadeConfigManager.querySnapshot(cascadeChildConfigManager.queryById(childConfig8.getId()).getLogSnapshotId());
    Assertions.assertEquals("b2-f1-n1", cascadeSnapshotBo_32_2.cascadedName());


    Map<Long, String> updateChildParam = new HashMap<>();
    updateChildParam.put(childConfig1.getId(), "g2");
    updateChildParam.put(childConfig2.getId(), "h2");
    cascadeConfigManager.batchUpdateChild(updateChildParam);

    Assertions.assertEquals("a2-c2-d2-g2-h2-i1-j1", queryHisState(parentConfig1.getId()));
    Assertions.assertEquals("a2-c2-d2-g2-h2-i1-j1", queryHisState2(parentConfig1.getId()));
    Assertions.assertEquals("b2-e1-f1-k1-l1-m1-n1", queryHisState(parentConfig2.getId()));
    Assertions.assertEquals("b2-e1-f1-k1-l1-m1-n1", queryHisState2(parentConfig2.getId()));

    CascadeSnapshotBo cascadeSnapshotBo_33_1 = cascadeConfigManager.querySnapshot(childConfig1.getLogSnapshotId());
    Assertions.assertEquals("a1-c1-g1", cascadeSnapshotBo_33_1.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_34_1 = cascadeConfigManager.querySnapshot(childConfig2.getLogSnapshotId());
    Assertions.assertEquals("a1-c1-h1", cascadeSnapshotBo_34_1.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_35_1 = cascadeConfigManager.querySnapshot(childConfig3.getLogSnapshotId());
    Assertions.assertEquals("a1-d1-i1", cascadeSnapshotBo_35_1.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_36_1 = cascadeConfigManager.querySnapshot(childConfig4.getLogSnapshotId());
    Assertions.assertEquals("a1-d1-j1", cascadeSnapshotBo_36_1.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_37_1 = cascadeConfigManager.querySnapshot(childConfig5.getLogSnapshotId());
    Assertions.assertEquals("b1-e1-k1", cascadeSnapshotBo_37_1.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_38_1 = cascadeConfigManager.querySnapshot(childConfig6.getLogSnapshotId());
    Assertions.assertEquals("b1-e1-l1", cascadeSnapshotBo_38_1.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_39_1 = cascadeConfigManager.querySnapshot(childConfig7.getLogSnapshotId());
    Assertions.assertEquals("b1-f1-m1", cascadeSnapshotBo_39_1.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_40_1 = cascadeConfigManager.querySnapshot(childConfig8.getLogSnapshotId());
    Assertions.assertEquals("b1-f1-n1", cascadeSnapshotBo_40_1.cascadedName());

    CascadeSnapshotBo cascadeSnapshotBo_41_2 = cascadeConfigManager.querySnapshot(cascadeChildConfigManager.queryById(childConfig1.getId()).getLogSnapshotId());
    Assertions.assertEquals("a2-c2-g2", cascadeSnapshotBo_41_2.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_42_2 = cascadeConfigManager.querySnapshot(cascadeChildConfigManager.queryById(childConfig2.getId()).getLogSnapshotId());
    Assertions.assertEquals("a2-c2-h2", cascadeSnapshotBo_42_2.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_43_2 = cascadeConfigManager.querySnapshot(cascadeChildConfigManager.queryById(childConfig3.getId()).getLogSnapshotId());
    Assertions.assertEquals("a2-d2-i1", cascadeSnapshotBo_43_2.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_44_2 = cascadeConfigManager.querySnapshot(cascadeChildConfigManager.queryById(childConfig4.getId()).getLogSnapshotId());
    Assertions.assertEquals("a2-d2-j1", cascadeSnapshotBo_44_2.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_45_2 = cascadeConfigManager.querySnapshot(cascadeChildConfigManager.queryById(childConfig5.getId()).getLogSnapshotId());
    Assertions.assertEquals("b2-e1-k1", cascadeSnapshotBo_45_2.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_46_2 = cascadeConfigManager.querySnapshot(cascadeChildConfigManager.queryById(childConfig6.getId()).getLogSnapshotId());
    Assertions.assertEquals("b2-e1-l1", cascadeSnapshotBo_46_2.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_47_2 = cascadeConfigManager.querySnapshot(cascadeChildConfigManager.queryById(childConfig7.getId()).getLogSnapshotId());
    Assertions.assertEquals("b2-f1-m1", cascadeSnapshotBo_47_2.cascadedName());
    CascadeSnapshotBo cascadeSnapshotBo_48_2 = cascadeConfigManager.querySnapshot(cascadeChildConfigManager.queryById(childConfig8.getId()).getLogSnapshotId());
    Assertions.assertEquals("b2-f1-n1", cascadeSnapshotBo_48_2.cascadedName());
  }

  private String queryHisState(Long parentConfigId) {
    CascadeParentConfigHisState cascadeParentConfigHisState
            = cascadeHisStateConfigManager.queryLatestOfParentConfig(parentConfigId);

    String parentLogSnapshotId = cascadeParentConfigHisState.getLogSnapshotId();
    List<CascadeCurrentConfigHisState> cascadeCurrentConfigHisStateList
            = cascadeHisStateConfigManager.queryHisStateOfCurrentConfig(cascadeParentConfigHisState.getStateId(), parentLogSnapshotId);
    cascadeCurrentConfigHisStateList.sort(Comparator.comparing(CascadeCurrentConfigHisState::getId));

    List<String> currentConfigLogSnapshotIdList = cascadeCurrentConfigHisStateList.stream().map(CascadeCurrentConfigHisState::getLogSnapshotId).collect(Collectors.toList());
    List<CascadeChildConfigHisState> cascadeChildConfigHisStateList
            = cascadeHisStateConfigManager.queryHisStateOfChildConfig(cascadeParentConfigHisState.getStateId(), currentConfigLogSnapshotIdList);
    cascadeChildConfigHisStateList.sort(Comparator.comparing(CascadeChildConfigHisState::getId));

    String result = cascadeParentConfigHisState.getName()
            + "-"
            + cascadeCurrentConfigHisStateList.stream().map(CascadeCurrentConfigHisState::getName).collect(Collectors.joining("-"))
            + "-"
            + cascadeChildConfigHisStateList.stream().map(CascadeChildConfigHisState::getName).collect(Collectors.joining("-"));
    return StringUtils.biTrimSpecifiedChar(result, '-');
  }

  private String queryHisState2(Long parentConfigId) {
    CascadeParentConfigHisState cascadeParentConfigHisState
            = cascadeHisStateConfigManager.queryLatestOfParentConfig(parentConfigId);

    Long parentId = cascadeParentConfigHisState.getCascadeParentConfigId();
    List<CascadeCurrentConfigHisState> cascadeCurrentConfigHisStateList
            = cascadeHisStateConfigManager.queryHisStateOfCurrentConfig2(cascadeParentConfigHisState.getStateId(), parentId);
    cascadeCurrentConfigHisStateList.sort(Comparator.comparing(CascadeCurrentConfigHisState::getId));

    List<Long> currentIdList = cascadeCurrentConfigHisStateList.stream().map(CascadeCurrentConfigHisState::getCascadeCurrentConfigId).collect(Collectors.toList());
    List<CascadeChildConfigHisState> cascadeChildConfigHisStateList
            = cascadeHisStateConfigManager.queryHisStateOfChildConfig2(cascadeParentConfigHisState.getStateId(), currentIdList);
    cascadeChildConfigHisStateList.sort(Comparator.comparing(CascadeChildConfigHisState::getId));

    String result = cascadeParentConfigHisState.getName()
            + "-"
            + cascadeCurrentConfigHisStateList.stream().map(CascadeCurrentConfigHisState::getName).collect(Collectors.joining("-"))
            + "-"
            + cascadeChildConfigHisStateList.stream().map(CascadeChildConfigHisState::getName).collect(Collectors.joining("-"));
    return StringUtils.biTrimSpecifiedChar(result, '-');
  }

  @AfterEach
  void afterEach() {
    SimpleBaseModel.popUser();
  }

}
