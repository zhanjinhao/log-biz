package cn.addenda.biz.log.manager;

import cn.addenda.biz.log.curd.CascadeChildConfigHisStateCurder;
import cn.addenda.biz.log.curd.CascadeCurrentConfigHisStateCurder;
import cn.addenda.biz.log.curd.CascadeParentConfigHisStateCurder;
import cn.addenda.biz.log.entity.*;
import cn.addenda.component.base.collection.ArrayUtils;
import cn.addenda.component.base.collection.IterableUtils;
import cn.addenda.component.spring.util.BeanUtils;
import cn.addenda.component.transaction.PlatformTransactionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class CascadeHisStateConfigManager {

  @Autowired
  private CascadeParentConfigManager cascadeParentConfigManager;

  @Autowired
  private CascadeCurrentConfigManager cascadeCurrentConfigManager;

  @Autowired
  private CascadeChildConfigManager cascadeChildConfigManager;

  @Autowired
  private CascadeParentConfigHisStateCurder cascadeParentConfigHisStateCurder;

  @Autowired
  private CascadeCurrentConfigHisStateCurder cascadeCurrentConfigHisStateCurder;

  @Autowired
  private CascadeChildConfigHisStateCurder cascadeChildConfigHisStateCurder;

  @Autowired
  private PlatformTransactionHelper platformTransactionHelper;

  public void recordForDmlParent(Long parentConfigId) {
    recordForDmlParent(ArrayUtils.asArrayList(parentConfigId));
  }

  public void recordForDmlCurrent(Long currentConfigId) {
    recordForDmlCurrent(ArrayUtils.asArrayList(currentConfigId));
  }

  public void recordForDmlChild(Long childConfigId) {
    recordForDmlChild(ArrayUtils.asArrayList(childConfigId));
  }

  public void recordForDmlParent(List<Long> parentConfigIdList) {
    List<CascadeParentConfig> cascadeParentConfigList = cascadeParentConfigManager.queryByIdList(parentConfigIdList);

    List<Long> cascadeParentConfigIdList = IterableUtils.collectToList(cascadeParentConfigList, CascadeParentConfig::getId);
    List<CascadeCurrentConfig> cascadeCurrentConfigList = cascadeCurrentConfigManager.queryByForeignIdList(cascadeParentConfigIdList);

    List<Long> cascadeCurrentIdList = IterableUtils.collectToList(cascadeCurrentConfigList, CascadeCurrentConfig::getId);
    List<CascadeChildConfig> cascadeChildConfigList = cascadeChildConfigManager.queryByForeignIdList(cascadeCurrentIdList);

    String stateId = UUID.randomUUID().toString().replace("-", "");

    List<CascadeParentConfigHisState> cascadeParentConfigHisStateList = cascadeParentConfigList.stream()
            .map(cascadeParentConfig -> {
              CascadeParentConfigHisState hisState = BeanUtils.copyProperties(cascadeParentConfig, new CascadeParentConfigHisState());
              hisState.setId(null);
              hisState.setCascadeParentConfigId(cascadeParentConfig.getId());
              hisState.setStateId(stateId);
              return hisState;
            }).collect(Collectors.toList());

    List<CascadeCurrentConfigHisState> cascadeCurrentConfigHisStateList = cascadeCurrentConfigList.stream()
            .map(cascadeCurrentConfig -> {
              CascadeCurrentConfigHisState hisState = BeanUtils.copyProperties(cascadeCurrentConfig, new CascadeCurrentConfigHisState());
              hisState.setId(null);
              hisState.setCascadeCurrentConfigId(cascadeCurrentConfig.getId());
              hisState.setStateId(stateId);
              return hisState;
            }).collect(Collectors.toList());

    List<CascadeChildConfigHisState> cascadeChildConfigHisStateList = cascadeChildConfigList.stream()
            .map(cascadeChildConfig -> {
              CascadeChildConfigHisState hisState = BeanUtils.copyProperties(cascadeChildConfig, new CascadeChildConfigHisState());
              hisState.setId(null);
              hisState.setCascadeChildConfigId(cascadeChildConfig.getId());
              hisState.setStateId(stateId);
              return hisState;
            }).collect(Collectors.toList());

    platformTransactionHelper.doTransaction(() -> {
      cascadeParentConfigHisStateCurder.batchInsert(cascadeParentConfigHisStateList);
      cascadeCurrentConfigHisStateCurder.batchInsert(cascadeCurrentConfigHisStateList);
      cascadeChildConfigHisStateCurder.batchInsert(cascadeChildConfigHisStateList);
    });
  }

  public void recordForDmlCurrent(List<Long> currentConfigIdList) {
    List<CascadeCurrentConfig> cascadeCurrentConfigList = cascadeCurrentConfigManager.queryByIdList(currentConfigIdList);
    if (cascadeCurrentConfigList == null) {
      return;
    }
    List<Long> cascadeParentConfigIdList = cascadeCurrentConfigList.stream().map(CascadeCurrentConfig::getForeignId).distinct().collect(Collectors.toList());
    recordForDmlParent(cascadeParentConfigIdList);
  }

  public void recordForDmlChild(List<Long> childConfigIdList) {
    List<CascadeChildConfig> cascadeChildConfigList = cascadeChildConfigManager.queryByIdList(childConfigIdList);
    if (cascadeChildConfigList == null) {
      return;
    }
    List<Long> currentConfigIdList = cascadeChildConfigList.stream().map(CascadeChildConfig::getForeignId).distinct().collect(Collectors.toList());
    recordForDmlCurrent(currentConfigIdList);
  }

  public CascadeParentConfigHisState queryLatestOfParentConfig(Long parentConfigId) {
    return cascadeParentConfigHisStateCurder.queryLatestOfParentConfig(parentConfigId);
  }

  public List<CascadeCurrentConfigHisState> queryHisStateOfCurrentConfig(String stateId, String foreignLogSnapshotId) {
    CascadeCurrentConfigHisState param = CascadeCurrentConfigHisState.ofParam();
    param.setStateId(stateId);
    param.setForeignLogSnapshotId(foreignLogSnapshotId);
    return cascadeCurrentConfigHisStateCurder.queryByEntity(param);
  }

  public List<CascadeCurrentConfigHisState> queryHisStateOfCurrentConfig2(String stateId, Long foreignId) {
    CascadeCurrentConfigHisState param = CascadeCurrentConfigHisState.ofParam();
    param.setStateId(stateId);
    param.setForeignId(foreignId);
    return cascadeCurrentConfigHisStateCurder.queryByEntity(param);
  }

  public List<CascadeChildConfigHisState> queryHisStateOfChildConfig(String stateId, List<String> foreignLogSnapshotIdList) {
    CascadeChildConfigHisState param = CascadeChildConfigHisState.ofParam();
    param.setStateId(stateId);
    param.setForeignLogSnapshotIdList(foreignLogSnapshotIdList);
    return cascadeChildConfigHisStateCurder.queryByEntity(param);
  }

  public List<CascadeChildConfigHisState> queryHisStateOfChildConfig2(String stateId, List<Long> foreignIdList) {
    CascadeChildConfigHisState param = CascadeChildConfigHisState.ofParam();
    param.setStateId(stateId);
    param.setForeignIdList(foreignIdList);
    return cascadeChildConfigHisStateCurder.queryByEntity(param);
  }

}
