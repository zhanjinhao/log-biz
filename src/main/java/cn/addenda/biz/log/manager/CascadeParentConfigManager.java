package cn.addenda.biz.log.manager;

import cn.addenda.biz.log.curd.CascadeParentConfigCurder;
import cn.addenda.biz.log.entity.CascadeParentConfig;
import cn.addenda.component.base.collection.IterableUtils;
import cn.addenda.component.spring.util.BeanUtils;
import cn.addenda.component.transaction.PlatformTransactionHelper;
import cn.addenda.mybatisbasemodel.core.BaseModelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Component
public class CascadeParentConfigManager {

  @Autowired
  private CascadeParentConfigCurder cascadeParentConfigCurder;

  @Autowired
  private PlatformTransactionHelper transactionHelperNew;

  public int insert(CascadeParentConfig entity) {
    return transactionHelperNew.doTransaction(() -> {
      completeInsert(entity);
      return cascadeParentConfigCurder.insert(entity);
    });
  }

  public int batchInsert(List<CascadeParentConfig> entityList) {
    return transactionHelperNew.doTransaction(() -> {
      for (CascadeParentConfig entity : entityList) {
        completeInsert(entity);
      }
      cascadeParentConfigCurder.batchInsert(entityList);
      return entityList.size();
    });
  }

  private void completeInsert(CascadeParentConfig entity) {
    if (entity.getIfLog() == null) {
      entity.setIfLog(0);
    }
    if (!StringUtils.hasText(entity.getLogGroup())) {
      entity.setLogGroup(UUID.randomUUID().toString().replace("-", ""));
    }
    if (!StringUtils.hasText(entity.getLogSnapshotId())) {
      entity.setLogSnapshotId(UUID.randomUUID().toString().replace("-", ""));
    }
  }

  /**
   * @param entity                   待更新的数据
   * @param propertiesUpdateConsumer accept()的参数是待更新的数据
   */
  public int update(CascadeParentConfig entity,
                    Consumer<CascadeParentConfig> propertiesUpdateConsumer) {
    return transactionHelperNew.doTransaction(() -> {
      CascadeParentConfig entityCopy = BeanUtils.copyProperties(entity, new CascadeParentConfig());
      entityCopy.setId(null);
      entityCopy.setIfLog(1);
      try {
        BaseModelContext.pushFillMode(BaseModelContext.FILL_MODE_SKIP);
        insert(entityCopy);
      } finally {
        BaseModelContext.popFillMode();
      }

      propertiesUpdateConsumer.accept(entity);
      completeUpdate(entity);
      return cascadeParentConfigCurder.updateById(entity);
    });
  }

  /**
   * @param entityList               待更新的数据
   * @param propertiesUpdateConsumer accept()的参数是待更新的数据
   */
  public int batchUpdate(List<CascadeParentConfig> entityList,
                         Consumer<List<CascadeParentConfig>> propertiesUpdateConsumer) {
    return transactionHelperNew.doTransaction(() -> {
      List<CascadeParentConfig> entityCopyList = entityList.stream().map(entity -> {
        CascadeParentConfig entityCopy = BeanUtils.copyProperties(entity, new CascadeParentConfig());
        entityCopy.setId(null);
        entityCopy.setIfLog(1);
        return entityCopy;
      }).collect(Collectors.toList());

      try {
        BaseModelContext.pushFillMode(BaseModelContext.FILL_MODE_SKIP);
        batchInsert(entityCopyList);
      } finally {
        BaseModelContext.popFillMode();
      }

      propertiesUpdateConsumer.accept(entityList);
      for (CascadeParentConfig entity : entityList) {
        completeUpdate(entity);
      }
      cascadeParentConfigCurder.batchUpdateById(entityList);
      return entityList.size();
    });
  }

  private void completeUpdate(CascadeParentConfig entity) {
    entity.setLogSnapshotId(UUID.randomUUID().toString().replace("-", ""));
  }

  public void delete(Long id) {
    CascadeParentConfig entity = cascadeParentConfigCurder.queryById(id);
    update(entity, new Consumer<CascadeParentConfig>() {
      @Override
      public void accept(CascadeParentConfig entity) {
        entity.setIfLog(1);
      }
    });
  }

  public void batchDelete(List<Long> idList) {
    List<CascadeParentConfig> entityList = cascadeParentConfigCurder.queryByIdList(idList);
    batchUpdate(entityList, new Consumer<List<CascadeParentConfig>>() {
      @Override
      public void accept(List<CascadeParentConfig> entityList) {
        for (CascadeParentConfig entity : entityList) {
          entity.setIfLog(1);
        }
      }
    });
  }

  public CascadeParentConfig queryById(Long id) {
    return cascadeParentConfigCurder.queryById(id);
  }

  public List<CascadeParentConfig> queryByIdList(List<Long> idList) {
    return cascadeParentConfigCurder.queryByIdList(idList);
  }

  public CascadeParentConfig queryByLogSnapshotId(String logSnapshotId) {
    return cascadeParentConfigCurder.queryByLogSnapshotId(logSnapshotId);
  }

}
