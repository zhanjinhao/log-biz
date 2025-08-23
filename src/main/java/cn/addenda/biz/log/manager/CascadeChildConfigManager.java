package cn.addenda.biz.log.manager;

import cn.addenda.biz.log.curd.CascadeChildConfigCurder;
import cn.addenda.biz.log.entity.CascadeChildConfig;
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
public class CascadeChildConfigManager {

  @Autowired
  private CascadeChildConfigCurder cascadeChildConfigCurder;

  @Autowired
  private PlatformTransactionHelper transactionHelperNew;

  public int insert(CascadeChildConfig entity) {
    return transactionHelperNew.doTransaction(() -> {
      completeInsert(entity);
      return cascadeChildConfigCurder.insert(entity);
    });
  }

  public int batchInsert(List<CascadeChildConfig> entityList) {
    return transactionHelperNew.doTransaction(() -> {
      for (CascadeChildConfig entity : entityList) {
        completeInsert(entity);
      }
      cascadeChildConfigCurder.batchInsert(entityList);
      return entityList.size();
    });
  }

  private void completeInsert(CascadeChildConfig entity) {
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
  public int update(CascadeChildConfig entity,
                    Consumer<CascadeChildConfig> propertiesUpdateConsumer) {
    return transactionHelperNew.doTransaction(() -> {
      CascadeChildConfig entityCopy = BeanUtils.copyProperties(entity, new CascadeChildConfig());
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
      return cascadeChildConfigCurder.updateById(entity);
    });
  }

  /**
   * @param entityList               待更新的数据
   * @param propertiesUpdateConsumer accept()的参数是待更新的数据
   */
  public int batchUpdate(List<CascadeChildConfig> entityList,
                         Consumer<List<CascadeChildConfig>> propertiesUpdateConsumer) {
    return transactionHelperNew.doTransaction(() -> {
      List<CascadeChildConfig> entityCopyList = entityList.stream().map(entity -> {
        CascadeChildConfig entityCopy = BeanUtils.copyProperties(entity, new CascadeChildConfig());
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
      for (CascadeChildConfig entity : entityList) {
        completeUpdate(entity);
      }
      cascadeChildConfigCurder.batchUpdateById(entityList);
      return entityList.size();
    });
  }

  private void completeUpdate(CascadeChildConfig entity) {
    entity.setLogSnapshotId(UUID.randomUUID().toString().replace("-", ""));
  }

  public void delete(Long id) {
    CascadeChildConfig entity = cascadeChildConfigCurder.queryById(id);
    update(entity, new Consumer<CascadeChildConfig>() {
      @Override
      public void accept(CascadeChildConfig entity) {
        entity.setIfLog(1);
      }
    });
  }

  public void batchDelete(List<Long> idList) {
    List<CascadeChildConfig> entityList = cascadeChildConfigCurder.queryByIdList(idList);
    batchUpdate(entityList, new Consumer<List<CascadeChildConfig>>() {
      @Override
      public void accept(List<CascadeChildConfig> entityList) {
        for (CascadeChildConfig entity : entityList) {
          entity.setIfLog(1);
        }
      }
    });
  }

  public CascadeChildConfig queryById(Long id) {
    return cascadeChildConfigCurder.queryById(id);
  }

  public List<CascadeChildConfig> queryByIdList(List<Long> idList) {
    return cascadeChildConfigCurder.queryByIdList(idList);
  }

  public List<CascadeChildConfig> queryByForeignIdList(List<Long> foreignIdList) {
    return cascadeChildConfigCurder.queryByForeignIdList(foreignIdList);
  }

  public CascadeChildConfig queryByLogSnapshotId(String logSnapshotId) {
    return cascadeChildConfigCurder.queryByLogSnapshotId(logSnapshotId);
  }

}
