package cn.addenda.biz.log.manager;

import cn.addenda.biz.log.bo.CascadeCurrentConfigSplitBo;
import cn.addenda.biz.log.bo.CascadeParentConfigSplitBo;
import cn.addenda.biz.log.bo.CascadeSnapshotBo;
import cn.addenda.biz.log.entity.CascadeChildConfig;
import cn.addenda.biz.log.entity.CascadeCurrentConfig;
import cn.addenda.biz.log.entity.CascadeParentConfig;
import cn.addenda.component.base.collection.ArrayUtils;
import cn.addenda.component.base.collection.IterableUtils;
import cn.addenda.component.base.pojo.Binary;
import cn.addenda.component.spring.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Component
public class CascadeConfigManager {

  @Autowired
  private CascadeParentConfigManager cascadeParentConfigManager;

  @Autowired
  private CascadeCurrentConfigManager cascadeCurrentConfigManager;

  @Autowired
  private CascadeChildConfigManager cascadeChildConfigManager;

  @Autowired
  private CascadeHisStateConfigManager cascadeHisStateConfigManager;

//  public void insertParent(CascadeParentConfig cascadeParentConfig) {
//    cascadeParentConfigManager.insert(cascadeParentConfig);
//  }
//
//  public void insertCurrent(CascadeCurrentConfig cascadeCurrentConfig) {
//    cascadeCurrentConfigManager.insert(cascadeCurrentConfig);
//  }
//
//  public void insertChild(CascadeChildConfig cascadeChildConfig) {
//    cascadeChildConfigManager.insert(cascadeChildConfig);
//  }
//
//  public void updateParent(Long id, String name) {
//    CascadeParentConfig cascadeParentConfig = cascadeParentConfigManager.queryById(id);
//    cascadeParentConfigManager.update(cascadeParentConfig, new Consumer<CascadeParentConfig>() {
//      @Override
//      public void accept(CascadeParentConfig cascadeParentConfig) {
//        cascadeParentConfig.setName(name);
//      }
//    });
//
//    List<CascadeCurrentConfig> cascadeCurrentConfigList = cascadeCurrentConfigManager.queryByParentId(cascadeParentConfig.getId());
//    cascadeCurrentConfigManager.batchUpdate(cascadeCurrentConfigList, new Consumer<List<CascadeCurrentConfig>>() {
//      @Override
//      public void accept(List<CascadeCurrentConfig> cascadeCurrentConfigs) {
//        for (CascadeCurrentConfig cascadeCurrentConfig : cascadeCurrentConfigs) {
//          cascadeCurrentConfig.setParentLogSnapshotId(cascadeParentConfig.getLogSnapshotId());
//        }
//      }
//    });
//
//    Map<Long, CascadeCurrentConfig> cascadeCurrentConfigMap = IterableUtils.toMap(cascadeCurrentConfigList, CascadeCurrentConfig::getId);
//    List<Long> cascadeCurrentConfigIdList = IterableUtils.collectToList(cascadeCurrentConfigList, CascadeCurrentConfig::getId);
//    List<CascadeChildConfig> cascadeChildConfigList = cascadeChildConfigManager.queryByParentIdList(cascadeCurrentConfigIdList);
//    cascadeChildConfigManager.batchUpdate(cascadeChildConfigList, new Consumer<List<CascadeChildConfig>>() {
//      @Override
//      public void accept(List<CascadeChildConfig> cascadeChildConfigs) {
//        for (CascadeChildConfig cascadeChildConfig : cascadeChildConfigs) {
//          CascadeCurrentConfig cascadeCurrentConfig = cascadeCurrentConfigMap.get(cascadeChildConfig.getParentId());
//          cascadeChildConfig.setParentLogSnapshotId(cascadeCurrentConfig.getLogSnapshotId());
//        }
//      }
//    });
//  }
//
//  public void updateCurrent(Long id, String name) {
//    CascadeCurrentConfig cascadeCurrentConfig = cascadeCurrentConfigManager.queryById(id);
//    cascadeCurrentConfigManager.update(cascadeCurrentConfig, new Consumer<CascadeCurrentConfig>() {
//      @Override
//      public void accept(CascadeCurrentConfig cascadeCurrentConfig) {
//        cascadeCurrentConfig.setName(name);
//      }
//    });
//
//    List<CascadeChildConfig> cascadeChildConfigList = cascadeChildConfigManager.queryByParentId(id);
//    cascadeChildConfigManager.batchUpdate(cascadeChildConfigList, new Consumer<List<CascadeChildConfig>>() {
//      @Override
//      public void accept(List<CascadeChildConfig> cascadeChildConfigs) {
//        for (CascadeChildConfig cascadeChildConfig : cascadeChildConfigs) {
//          cascadeChildConfig.setParentLogSnapshotId(cascadeCurrentConfig.getLogSnapshotId());
//        }
//      }
//    });
//  }
//
//  public void updateChild(Long id, String name) {
//    CascadeChildConfig cascadeChildConfig = cascadeChildConfigManager.queryById(id);
//    cascadeChildConfigManager.update(cascadeChildConfig, new Consumer<CascadeChildConfig>() {
//      @Override
//      public void accept(CascadeChildConfig cascadeChildConfig) {
//        cascadeChildConfig.setName(name);
//      }
//    });
//  }
//
//  public void deleteParent(Long id) {
//    CascadeParentConfig cascadeParentConfig = cascadeParentConfigManager.queryById(id);
//    cascadeParentConfigManager.update(cascadeParentConfig, new Consumer<CascadeParentConfig>() {
//      @Override
//      public void accept(CascadeParentConfig cascadeParentConfig) {
//        cascadeParentConfig.setIfLog(1);
//      }
//    });
//
//    List<CascadeCurrentConfig> cascadeCurrentConfigList = cascadeCurrentConfigManager.queryByParentId(cascadeParentConfig.getId());
//    cascadeCurrentConfigManager.batchUpdate(cascadeCurrentConfigList, new Consumer<List<CascadeCurrentConfig>>() {
//      @Override
//      public void accept(List<CascadeCurrentConfig> cascadeCurrentConfigs) {
//        for (CascadeCurrentConfig cascadeCurrentConfig : cascadeCurrentConfigs) {
//          cascadeCurrentConfig.setParentLogSnapshotId(cascadeParentConfig.getLogSnapshotId());
//          cascadeCurrentConfig.setIfLog(1);
//        }
//      }
//    });
//
//    Map<Long, CascadeCurrentConfig> cascadeCurrentConfigMap = IterableUtils.toMap(cascadeCurrentConfigList, CascadeCurrentConfig::getId);
//    List<Long> cascadeCurrentConfigIdList = IterableUtils.collectToList(cascadeCurrentConfigList, CascadeCurrentConfig::getId);
//    List<CascadeChildConfig> cascadeChildConfigList = cascadeChildConfigManager.queryByParentIdList(cascadeCurrentConfigIdList);
//    cascadeChildConfigManager.batchUpdate(cascadeChildConfigList, new Consumer<List<CascadeChildConfig>>() {
//      @Override
//      public void accept(List<CascadeChildConfig> cascadeChildConfigs) {
//        for (CascadeChildConfig cascadeChildConfig : cascadeChildConfigs) {
//          CascadeCurrentConfig cascadeCurrentConfig = cascadeCurrentConfigMap.get(cascadeChildConfig.getParentId());
//          cascadeChildConfig.setParentLogSnapshotId(cascadeCurrentConfig.getLogSnapshotId());
//          cascadeChildConfig.setIfLog(1);
//        }
//      }
//    });
//  }
//
//  public void deleteCurrent(Long id) {
//    CascadeCurrentConfig cascadeCurrentConfig = cascadeCurrentConfigManager.queryById(id);
//    cascadeCurrentConfigManager.update(cascadeCurrentConfig, new Consumer<CascadeCurrentConfig>() {
//      @Override
//      public void accept(CascadeCurrentConfig cascadeCurrentConfig) {
//        cascadeCurrentConfig.setIfLog(1);
//      }
//    });
//
//    List<CascadeChildConfig> cascadeChildConfigList = cascadeChildConfigManager.queryByParentId(id);
//    cascadeChildConfigManager.batchUpdate(cascadeChildConfigList, new Consumer<List<CascadeChildConfig>>() {
//      @Override
//      public void accept(List<CascadeChildConfig> cascadeChildConfigs) {
//        for (CascadeChildConfig cascadeChildConfig : cascadeChildConfigs) {
//          cascadeChildConfig.setParentLogSnapshotId(cascadeCurrentConfig.getLogSnapshotId());
//          cascadeChildConfig.setIfLog(1);
//        }
//      }
//    });
//  }
//
//  public void deleteChild(Long id) {
//    CascadeChildConfig cascadeChildConfig = cascadeChildConfigManager.queryById(id);
//    cascadeChildConfigManager.update(cascadeChildConfig, new Consumer<CascadeChildConfig>() {
//      @Override
//      public void accept(CascadeChildConfig cascadeChildConfig) {
//        cascadeChildConfig.setIfLog(1);
//      }
//    });
//  }

  public void insertParent(CascadeParentConfig cascadeParentConfig) {
    batchInsertParent(ArrayUtils.asArrayList(cascadeParentConfig));
  }

  public void insertCurrent(CascadeCurrentConfig cascadeCurrentConfig) {
    batchInsertCurrent(ArrayUtils.asArrayList(cascadeCurrentConfig));
  }

  public void insertChild(CascadeChildConfig cascadeChildConfig) {
    batchInsertChild(ArrayUtils.asArrayList(cascadeChildConfig));
  }

  public void updateParent(Long id, String name) {
    Map<Long, String> updateParam = new HashMap<>();
    updateParam.put(id, name);
    batchUpdateParent(updateParam);
  }

  public void updateCurrent(Long id, String name) {
    Map<Long, String> updateParam = new HashMap<>();
    updateParam.put(id, name);
    batchUpdateCurrent(updateParam);
  }

  public void updateChild(Long id, String name) {
    Map<Long, String> updateParam = new HashMap<>();
    updateParam.put(id, name);
    batchUpdateChild(updateParam);
  }

  public void deleteParent(Long id) {
    batchDeleteParent(ArrayUtils.asArrayList(id));
  }

  public void deleteCurrent(Long id) {
    batchDeleteCurrent(ArrayUtils.asArrayList(id));
  }

  public void deleteChild(Long id) {
    batchDeleteChild(ArrayUtils.asArrayList(id));
  }

  // ------------
  //    batch
  // ------------

  public void batchInsertParent(List<CascadeParentConfig> cascadeParentConfigList) {
    cascadeParentConfigManager.batchInsert(cascadeParentConfigList);

    List<Long> cascadeParentConfigIdList = IterableUtils.collectToList(cascadeParentConfigList, CascadeParentConfig::getId);
    cascadeHisStateConfigManager.recordForDmlParent(cascadeParentConfigIdList);
  }

  public void batchInsertCurrent(List<CascadeCurrentConfig> cascadeCurrentConfigList) {
    cascadeCurrentConfigManager.batchInsert(cascadeCurrentConfigList);

    List<Long> cascadeCurrentIdList = IterableUtils.collectToList(cascadeCurrentConfigList, CascadeCurrentConfig::getId);
    cascadeHisStateConfigManager.recordForDmlCurrent(cascadeCurrentIdList);
  }

  public void batchInsertChild(List<CascadeChildConfig> cascadeChildConfigList) {
    cascadeChildConfigManager.batchInsert(cascadeChildConfigList);

    List<Long> cascadeChildIdList = IterableUtils.collectToList(cascadeChildConfigList, CascadeChildConfig::getId);
    cascadeHisStateConfigManager.recordForDmlChild(cascadeChildIdList);
  }

  public void batchUpdateParent(Map<Long, String> updateParam) {
    List<Long> cascadeParentConfigIdList = new ArrayList<>(updateParam.keySet());
    List<CascadeParentConfig> cascadeParentConfigList = cascadeParentConfigManager.queryByIdList(cascadeParentConfigIdList);
    cascadeParentConfigManager.batchUpdate(cascadeParentConfigList, new Consumer<List<CascadeParentConfig>>() {
      @Override
      public void accept(List<CascadeParentConfig> cascadeParentConfigList) {
        for (CascadeParentConfig cascadeParentConfig : cascadeParentConfigList) {
          cascadeParentConfig.setName(updateParam.get(cascadeParentConfig.getId()));
        }
      }
    });

    Map<Long, CascadeParentConfig> cascadeParentConfigMap = IterableUtils.toMap(cascadeParentConfigList, CascadeParentConfig::getId);
    List<CascadeCurrentConfig> cascadeCurrentConfigList = cascadeCurrentConfigManager.queryByForeignIdList(cascadeParentConfigIdList);
    cascadeCurrentConfigManager.batchUpdate(cascadeCurrentConfigList, new Consumer<List<CascadeCurrentConfig>>() {
      @Override
      public void accept(List<CascadeCurrentConfig> cascadeCurrentConfigList) {
        for (CascadeCurrentConfig cascadeCurrentConfig : cascadeCurrentConfigList) {
          CascadeParentConfig cascadeParentConfig = cascadeParentConfigMap.get(cascadeCurrentConfig.getForeignId());
          cascadeCurrentConfig.setForeignLogSnapshotId(cascadeParentConfig.getLogSnapshotId());
        }
      }
    });

    Map<Long, CascadeCurrentConfig> cascadeCurrentConfigMap = IterableUtils.toMap(cascadeCurrentConfigList, CascadeCurrentConfig::getId);
    List<Long> cascadeCurrentConfigIdList = IterableUtils.collectToList(cascadeCurrentConfigList, CascadeCurrentConfig::getId);
    List<CascadeChildConfig> cascadeChildConfigList = cascadeChildConfigManager.queryByForeignIdList(cascadeCurrentConfigIdList);
    cascadeChildConfigManager.batchUpdate(cascadeChildConfigList, new Consumer<List<CascadeChildConfig>>() {
      @Override
      public void accept(List<CascadeChildConfig> cascadeChildConfigList) {
        for (CascadeChildConfig cascadeChildConfig : cascadeChildConfigList) {
          CascadeCurrentConfig cascadeCurrentConfig = cascadeCurrentConfigMap.get(cascadeChildConfig.getForeignId());
          cascadeChildConfig.setForeignLogSnapshotId(cascadeCurrentConfig.getLogSnapshotId());
        }
      }
    });

    cascadeHisStateConfigManager.recordForDmlParent(cascadeParentConfigIdList);
  }

  public void batchUpdateCurrent(Map<Long, String> updateParam) {
    List<Long> idList = new ArrayList<>(updateParam.keySet());
    List<CascadeCurrentConfig> cascadeCurrentConfigList = cascadeCurrentConfigManager.queryByIdList(idList);
    cascadeCurrentConfigManager.batchUpdate(cascadeCurrentConfigList, new Consumer<List<CascadeCurrentConfig>>() {
      @Override
      public void accept(List<CascadeCurrentConfig> cascadeCurrentConfigList) {
        for (CascadeCurrentConfig cascadeCurrentConfig : cascadeCurrentConfigList) {
          cascadeCurrentConfig.setName(updateParam.get(cascadeCurrentConfig.getId()));
        }
      }
    });

    Map<Long, CascadeCurrentConfig> cascadeCurrentConfigMap = IterableUtils.toMap(cascadeCurrentConfigList, CascadeCurrentConfig::getId);
    List<Long> cascadeCurrentConfigIdList = IterableUtils.collectToList(cascadeCurrentConfigList, CascadeCurrentConfig::getId);
    List<CascadeChildConfig> cascadeChildConfigList = cascadeChildConfigManager.queryByForeignIdList(cascadeCurrentConfigIdList);
    cascadeChildConfigManager.batchUpdate(cascadeChildConfigList, new Consumer<List<CascadeChildConfig>>() {
      @Override
      public void accept(List<CascadeChildConfig> cascadeChildConfigList) {
        for (CascadeChildConfig cascadeChildConfig : cascadeChildConfigList) {
          CascadeCurrentConfig cascadeCurrentConfig = cascadeCurrentConfigMap.get(cascadeChildConfig.getForeignId());
          cascadeChildConfig.setForeignLogSnapshotId(cascadeCurrentConfig.getLogSnapshotId());
        }
      }
    });

    cascadeHisStateConfigManager.recordForDmlCurrent(cascadeCurrentConfigIdList);
  }

  public void batchUpdateChild(Map<Long, String> updateParam) {
    List<Long> childIdList = new ArrayList<>(updateParam.keySet());
    List<CascadeChildConfig> cascadeChildConfigList = cascadeChildConfigManager.queryByIdList(childIdList);
    cascadeChildConfigManager.batchUpdate(cascadeChildConfigList, new Consumer<List<CascadeChildConfig>>() {
      @Override
      public void accept(List<CascadeChildConfig> cascadeChildConfigList) {
        for (CascadeChildConfig cascadeChildConfig : cascadeChildConfigList) {
          cascadeChildConfig.setName(updateParam.get(cascadeChildConfig.getId()));
        }
      }
    });

    cascadeHisStateConfigManager.recordForDmlChild(childIdList);
  }

  public void batchDeleteParent(List<Long> cascadeParentConfigIdList) {
    List<CascadeParentConfig> cascadeParentConfigList = cascadeParentConfigManager.queryByIdList(cascadeParentConfigIdList);
    cascadeParentConfigManager.batchUpdate(cascadeParentConfigList, new Consumer<List<CascadeParentConfig>>() {
      @Override
      public void accept(List<CascadeParentConfig> cascadeParentConfigList) {
        for (CascadeParentConfig cascadeParentConfig : cascadeParentConfigList) {
          cascadeParentConfig.setIfLog(1);
        }
      }
    });

    Map<Long, CascadeParentConfig> cascadeParentConfigMap = IterableUtils.toMap(cascadeParentConfigList, CascadeParentConfig::getId);
    List<CascadeCurrentConfig> cascadeCurrentConfigList = cascadeCurrentConfigManager.queryByForeignIdList(cascadeParentConfigIdList);
    cascadeCurrentConfigManager.batchUpdate(cascadeCurrentConfigList, new Consumer<List<CascadeCurrentConfig>>() {
      @Override
      public void accept(List<CascadeCurrentConfig> cascadeCurrentConfigList) {
        for (CascadeCurrentConfig cascadeCurrentConfig : cascadeCurrentConfigList) {
          CascadeParentConfig cascadeParentConfig = cascadeParentConfigMap.get(cascadeCurrentConfig.getForeignId());
          cascadeCurrentConfig.setForeignLogSnapshotId(cascadeParentConfig.getLogSnapshotId());
          cascadeCurrentConfig.setIfLog(1);
        }
      }
    });

    Map<Long, CascadeCurrentConfig> cascadeCurrentConfigMap = IterableUtils.toMap(cascadeCurrentConfigList, CascadeCurrentConfig::getId);
    List<Long> cascadeCurrentConfigIdList = IterableUtils.collectToList(cascadeCurrentConfigList, CascadeCurrentConfig::getId);
    List<CascadeChildConfig> cascadeChildConfigList = cascadeChildConfigManager.queryByForeignIdList(cascadeCurrentConfigIdList);
    cascadeChildConfigManager.batchUpdate(cascadeChildConfigList, new Consumer<List<CascadeChildConfig>>() {
      @Override
      public void accept(List<CascadeChildConfig> cascadeChildConfigList) {
        for (CascadeChildConfig cascadeChildConfig : cascadeChildConfigList) {
          CascadeCurrentConfig cascadeCurrentConfig = cascadeCurrentConfigMap.get(cascadeChildConfig.getForeignId());
          cascadeChildConfig.setForeignLogSnapshotId(cascadeCurrentConfig.getLogSnapshotId());
          cascadeChildConfig.setIfLog(1);
        }
      }
    });
  }

  public void batchDeleteCurrent(List<Long> currentIdList) {
    List<CascadeCurrentConfig> cascadeCurrentConfigList = cascadeCurrentConfigManager.queryByIdList(currentIdList);
    cascadeCurrentConfigManager.batchUpdate(cascadeCurrentConfigList, new Consumer<List<CascadeCurrentConfig>>() {
      @Override
      public void accept(List<CascadeCurrentConfig> cascadeCurrentConfigList) {
        for (CascadeCurrentConfig cascadeCurrentConfig : cascadeCurrentConfigList) {
          cascadeCurrentConfig.setIfLog(1);
        }
      }
    });

    Map<Long, CascadeCurrentConfig> cascadeCurrentConfigMap = IterableUtils.toMap(cascadeCurrentConfigList, CascadeCurrentConfig::getId);
    List<Long> cascadeCurrentConfigIdList = IterableUtils.collectToList(cascadeCurrentConfigList, CascadeCurrentConfig::getId);
    List<CascadeChildConfig> cascadeChildConfigList = cascadeChildConfigManager.queryByForeignIdList(cascadeCurrentConfigIdList);
    cascadeChildConfigManager.batchUpdate(cascadeChildConfigList, new Consumer<List<CascadeChildConfig>>() {
      @Override
      public void accept(List<CascadeChildConfig> cascadeChildConfigList) {
        for (CascadeChildConfig cascadeChildConfig : cascadeChildConfigList) {
          CascadeCurrentConfig cascadeCurrentConfig = cascadeCurrentConfigMap.get(cascadeChildConfig.getForeignId());
          cascadeChildConfig.setForeignLogSnapshotId(cascadeCurrentConfig.getLogSnapshotId());
          cascadeChildConfig.setIfLog(1);
        }
      }
    });
  }

  public void batchDeleteChild(List<Long> childIdList) {
    List<CascadeChildConfig> cascadeChildConfigList = cascadeChildConfigManager.queryByIdList(childIdList);
    cascadeChildConfigManager.batchUpdate(cascadeChildConfigList, new Consumer<List<CascadeChildConfig>>() {
      @Override
      public void accept(List<CascadeChildConfig> cascadeChildConfigList) {
        for (CascadeChildConfig cascadeChildConfig : cascadeChildConfigList) {
          cascadeChildConfig.setIfLog(1);
        }
      }
    });
  }

  public CascadeSnapshotBo querySnapshot(String childLogSnapshotId) {
    CascadeChildConfig cascadeChildConfig = cascadeChildConfigManager.queryByLogSnapshotId(childLogSnapshotId);

    String currentLogSnapshotId = cascadeChildConfig.getForeignLogSnapshotId();
    CascadeCurrentConfig cascadeCurrentConfig = cascadeCurrentConfigManager.queryByLogSnapshotId(currentLogSnapshotId);

    String parentLogSnapshotId = cascadeCurrentConfig.getForeignLogSnapshotId();
    CascadeParentConfig cascadeParentConfig = cascadeParentConfigManager.queryByLogSnapshotId(parentLogSnapshotId);

    return CascadeSnapshotBo.of(cascadeParentConfig, cascadeCurrentConfig, cascadeChildConfig);
  }

  /**
   * 具体拆分规则没有。只是展示一下拆分的流程。
   */
  public List<CascadeParentConfigSplitBo> split(Long cascadeParentConfigId) {
    CascadeParentConfig cascadeParentConfigDb = cascadeParentConfigManager.queryById(cascadeParentConfigId);

    List<CascadeCurrentConfig> cascadeCurrentConfigDbList = cascadeCurrentConfigManager.queryByForeignId(cascadeParentConfigId);

    List<Long> cascadeCurrentConfigDbIdList = cascadeCurrentConfigDbList.stream().map(CascadeCurrentConfig::getId).collect(Collectors.toList());
    List<CascadeChildConfig> cascadeChildConfigDbList = cascadeChildConfigManager.queryByForeignIdList(cascadeCurrentConfigDbIdList);
    Map<Long, List<CascadeChildConfig>> cascadeChildConfigDbGroup = cascadeChildConfigDbList.stream().collect(Collectors.groupingBy(CascadeChildConfig::getForeignId));

    deleteParent(cascadeParentConfigId);

    List<CascadeParentConfig> cascadeParentConfigCopyList = new ArrayList<>();
    cascadeParentConfigCopyList.add(copyCascadeParentConfig(cascadeParentConfigDb, "copy1"));
    cascadeParentConfigCopyList.add(copyCascadeParentConfig(cascadeParentConfigDb, "copy2"));
    batchInsertParent(cascadeParentConfigCopyList);

    List<Binary<CascadeCurrentConfig, List<CascadeChildConfig>>> binaryList = new ArrayList<>();

    for (int i = 0; i < cascadeParentConfigCopyList.size(); i++) {
      CascadeParentConfig cascadeParentConfigCopy = cascadeParentConfigCopyList.get(i);
      String suffix = "copy" + (i + 1);
      for (CascadeCurrentConfig cascadeCurrentConfigDb : cascadeCurrentConfigDbList) {
        CascadeCurrentConfig cascadeCurrentConfigCopy = copyCascadeCurrentConfig(cascadeCurrentConfigDb, suffix);
        cascadeCurrentConfigCopy.setForeignId(cascadeParentConfigCopy.getId());
        cascadeCurrentConfigCopy.setForeignLogSnapshotId(cascadeParentConfigCopy.getLogSnapshotId());

        List<CascadeChildConfig> cascadeChildConfigDbSubList =
                cascadeChildConfigDbGroup.getOrDefault(cascadeCurrentConfigDb.getId(), new ArrayList<>());
        List<CascadeChildConfig> cascadeChildConfigCopySubList = new ArrayList<>();
        for (CascadeChildConfig cascadeChildConfigDb : cascadeChildConfigDbSubList) {
          cascadeChildConfigCopySubList.add(copyCascadeChildConfig(cascadeChildConfigDb, suffix));
        }

        binaryList.add(Binary.of(cascadeCurrentConfigCopy, cascadeChildConfigCopySubList));
      }
    }

    List<CascadeCurrentConfig> cascadeCurrentConfigCopyList =
            binaryList.stream().map(Binary::getF1).collect(Collectors.toList());
    batchInsertCurrent(cascadeCurrentConfigCopyList);

    for (Binary<CascadeCurrentConfig, List<CascadeChildConfig>> binary : binaryList) {
      CascadeCurrentConfig cascadeCurrentConfig = binary.getF1();
      List<CascadeChildConfig> cascadeChildConfigList = binary.getF2();
      for (CascadeChildConfig cascadeChildConfig : cascadeChildConfigList) {
        cascadeChildConfig.setForeignId(cascadeCurrentConfig.getId());
        cascadeChildConfig.setForeignLogSnapshotId(cascadeCurrentConfig.getLogSnapshotId());
      }
    }

    List<CascadeChildConfig> cascadeChildConfigCopyList =
            binaryList.stream().map(Binary::getF2).flatMap(Collection::stream).collect(Collectors.toList());
    batchInsertChild(cascadeChildConfigCopyList);

    Map<Long, List<Binary<CascadeCurrentConfig, List<CascadeChildConfig>>>> binaryGroup = binaryList.stream()
            .collect(Collectors.groupingBy(binary -> binary.getF1().getForeignId()));

    List<CascadeParentConfigSplitBo> cascadeParentConfigSplitBoList = new ArrayList<>();
    for (CascadeParentConfig cascadeParentConfig : cascadeParentConfigCopyList) {
      CascadeParentConfigSplitBo cascadeParentConfigSplitBo = BeanUtils.copyProperties(cascadeParentConfig, new CascadeParentConfigSplitBo());
      cascadeParentConfigSplitBoList.add(cascadeParentConfigSplitBo);

      List<Binary<CascadeCurrentConfig, List<CascadeChildConfig>>> binarySubList = binaryGroup.get(cascadeParentConfig.getId());
      List<CascadeCurrentConfigSplitBo> cascadeCurrentConfigSplitBoList = new ArrayList<>();
      for (Binary<CascadeCurrentConfig, List<CascadeChildConfig>> binary : binarySubList) {
        CascadeCurrentConfig cascadeCurrentConfig = binary.getF1();
        List<CascadeChildConfig> cascadeChildConfigList = binary.getF2();
        CascadeCurrentConfigSplitBo cascadeCurrentConfigSplitBo = BeanUtils.copyProperties(cascadeCurrentConfig, new CascadeCurrentConfigSplitBo());
        cascadeCurrentConfigSplitBoList.add(cascadeCurrentConfigSplitBo);
        cascadeCurrentConfigSplitBo.setCascadeChildConfigList(cascadeChildConfigList);
      }
      cascadeParentConfigSplitBo.setCascadeCurrentConfigBoList(cascadeCurrentConfigSplitBoList);
    }

    return cascadeParentConfigSplitBoList;
  }


  private CascadeParentConfig copyCascadeParentConfig(CascadeParentConfig cascadeParentConfig, String suffix) {
    CascadeParentConfig cascadeParentConfigCopy = new CascadeParentConfig();
    cascadeParentConfigCopy.setId(null);
    cascadeParentConfigCopy.setCode(cascadeParentConfig.getCode() + suffix);
    cascadeParentConfigCopy.setName(cascadeParentConfig.getName() + suffix);
    cascadeParentConfigCopy.setLogGroup(null);
    cascadeParentConfigCopy.setLogSnapshotId(null);
    cascadeParentConfigCopy.setIfLog(0);
    cascadeParentConfigCopy.setParentId(cascadeParentConfig.getId());
    return cascadeParentConfigCopy;
  }

  private CascadeCurrentConfig copyCascadeCurrentConfig(CascadeCurrentConfig cascadeCurrentConfig, String suffix) {
    CascadeCurrentConfig cascadeCurrentConfigCopy = new CascadeCurrentConfig();
    cascadeCurrentConfigCopy.setId(null);
    cascadeCurrentConfigCopy.setCode(cascadeCurrentConfig.getCode() + suffix);
    cascadeCurrentConfigCopy.setName(cascadeCurrentConfig.getName() + suffix);
    cascadeCurrentConfigCopy.setLogGroup(null);
    cascadeCurrentConfigCopy.setLogSnapshotId(null);
    cascadeCurrentConfigCopy.setIfLog(null);
    cascadeCurrentConfigCopy.setForeignId(null);
    cascadeCurrentConfigCopy.setForeignLogSnapshotId(null);
    return cascadeCurrentConfigCopy;
  }

  private CascadeChildConfig copyCascadeChildConfig(CascadeChildConfig cascadeChildConfig, String suffix) {
    CascadeChildConfig cascadeChildConfigCopy = new CascadeChildConfig();
    cascadeChildConfigCopy.setId(null);
    cascadeChildConfigCopy.setCode(cascadeChildConfig.getCode() + suffix);
    cascadeChildConfigCopy.setName(cascadeChildConfig.getName() + suffix);
    cascadeChildConfigCopy.setLogGroup(null);
    cascadeChildConfigCopy.setLogSnapshotId(null);
    cascadeChildConfigCopy.setIfLog(null);
    cascadeChildConfigCopy.setForeignId(null);
    cascadeChildConfigCopy.setForeignLogSnapshotId(null);
    return cascadeChildConfigCopy;
  }

}
