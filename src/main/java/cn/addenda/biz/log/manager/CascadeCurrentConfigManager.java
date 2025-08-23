package cn.addenda.biz.log.manager;

import cn.addenda.biz.log.curd.CascadeCurrentConfigCurder;
import cn.addenda.biz.log.entity.CascadeCurrentConfig;
import cn.addenda.component.base.collection.ArrayUtils;
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
public class CascadeCurrentConfigManager {

  @Autowired
  private CascadeCurrentConfigCurder cascadeCurrentConfigCurder;

  @Autowired
  private PlatformTransactionHelper transactionHelperNew;

  public int insert(CascadeCurrentConfig entity) {
    return transactionHelperNew.doTransaction(() -> {
      completeInsert(entity);
      return cascadeCurrentConfigCurder.insert(entity);
    });
  }

  public int batchInsert(List<CascadeCurrentConfig> entityList) {
    return transactionHelperNew.doTransaction(() -> {
      for (CascadeCurrentConfig entity : entityList) {
        completeInsert(entity);
      }
      cascadeCurrentConfigCurder.batchInsert(entityList);
      return entityList.size();
    });
  }

  private void completeInsert(CascadeCurrentConfig entity) {
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
  public int update(CascadeCurrentConfig entity,
                    Consumer<CascadeCurrentConfig> propertiesUpdateConsumer) {
    return transactionHelperNew.doTransaction(() -> {
      CascadeCurrentConfig entityCopy = BeanUtils.copyProperties(entity, new CascadeCurrentConfig());
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
      return cascadeCurrentConfigCurder.updateById(entity);
    });
  }

  /**
   * @param entityList               待更新的数据
   * @param propertiesUpdateConsumer accept()的参数是待更新的数据
   */
  public int batchUpdate(List<CascadeCurrentConfig> entityList,
                         Consumer<List<CascadeCurrentConfig>> propertiesUpdateConsumer) {
    return transactionHelperNew.doTransaction(() -> {
      List<CascadeCurrentConfig> entityCopyList = entityList.stream().map(entity -> {
        CascadeCurrentConfig entityCopy = BeanUtils.copyProperties(entity, new CascadeCurrentConfig());
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
      for (CascadeCurrentConfig entity : entityList) {
        completeUpdate(entity);
      }
      cascadeCurrentConfigCurder.batchUpdateById(entityList);
      return entityList.size();
    });
  }

  private void completeUpdate(CascadeCurrentConfig entity) {
    entity.setLogSnapshotId(UUID.randomUUID().toString().replace("-", ""));
  }

  public void delete(Long id) {
    CascadeCurrentConfig entity = cascadeCurrentConfigCurder.queryById(id);
    update(entity, new Consumer<CascadeCurrentConfig>() {
      @Override
      public void accept(CascadeCurrentConfig entity) {
        entity.setIfLog(1);
      }
    });
  }

  public void batchDelete(List<Long> idList) {
    List<CascadeCurrentConfig> entityList = cascadeCurrentConfigCurder.queryByIdList(idList);
    batchUpdate(entityList, new Consumer<List<CascadeCurrentConfig>>() {
      @Override
      public void accept(List<CascadeCurrentConfig> entityList) {
        for (CascadeCurrentConfig entity : entityList) {
          entity.setIfLog(1);
        }
      }
    });
  }

  public CascadeCurrentConfig queryById(Long id) {
    return cascadeCurrentConfigCurder.queryById(id);
  }

  public List<CascadeCurrentConfig> queryByIdList(List<Long> idList) {
    return cascadeCurrentConfigCurder.queryByIdList(idList);
  }

  public List<CascadeCurrentConfig> queryByForeignId(Long foreignId) {
    return queryByForeignIdList(ArrayUtils.asArrayList(foreignId));
  }

  public List<CascadeCurrentConfig> queryByForeignIdList(List<Long> foreignIdList) {
    return cascadeCurrentConfigCurder.queryByForeignIdList(foreignIdList);
  }

  public CascadeCurrentConfig queryByLogSnapshotId(String logSnapshotId) {
    return cascadeCurrentConfigCurder.queryByLogSnapshotId(logSnapshotId);
  }

}
