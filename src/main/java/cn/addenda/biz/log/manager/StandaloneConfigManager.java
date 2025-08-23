package cn.addenda.biz.log.manager;

import cn.addenda.biz.log.curd.StandaloneConfigCurder;
import cn.addenda.biz.log.entity.StandaloneConfig;
import cn.addenda.component.base.exception.ServiceException;
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
public class StandaloneConfigManager {

  @Autowired
  private StandaloneConfigCurder standaloneConfigCurder;

  @Autowired
  private PlatformTransactionHelper transactionHelperNew;

  public int insert(StandaloneConfig entity) {
    return transactionHelperNew.doTransaction(() -> {
      completeInsert(entity);
      return standaloneConfigCurder.insert(entity);
    });
  }

  public int batchInsert(List<StandaloneConfig> entityList) {
    return transactionHelperNew.doTransaction(() -> {
      for (StandaloneConfig entity : entityList) {
        completeInsert(entity);
      }
      standaloneConfigCurder.batchInsert(entityList);
      return entityList.size();
    });
  }

  private void completeInsert(StandaloneConfig entity) {
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

  public int updateById(Long id, Consumer<StandaloneConfig> propertiesUpdateConsumer) {
    StandaloneConfig standaloneConfig = standaloneConfigCurder.queryById(id);
    if (standaloneConfig == null) {
      throw new ServiceException("数据不存在，无法更新！");
    }
    return update(standaloneConfig, propertiesUpdateConsumer);
  }

  /**
   * @param entity                   待更新的数据
   * @param propertiesUpdateConsumer accept()的参数是待更新的数据
   */
  public int update(StandaloneConfig entity,
                    Consumer<StandaloneConfig> propertiesUpdateConsumer) {
    return transactionHelperNew.doTransaction(() -> {
      StandaloneConfig entityCopy = BeanUtils.copyProperties(entity, new StandaloneConfig());
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
      return standaloneConfigCurder.updateById(entity);
    });
  }

  public int batchUpdateById(List<Long> idList, Consumer<List<StandaloneConfig>> propertiesUpdateConsumer) {
    List<StandaloneConfig> standaloneConfigList = standaloneConfigCurder.queryByIdList(idList);
    return batchUpdate(standaloneConfigList, propertiesUpdateConsumer);
  }

  /**
   * @param entityList               待更新的数据
   * @param propertiesUpdateConsumer accept()的参数是待更新的数据
   */
  public int batchUpdate(List<StandaloneConfig> entityList,
                         Consumer<List<StandaloneConfig>> propertiesUpdateConsumer) {
    return transactionHelperNew.doTransaction(() -> {
      List<StandaloneConfig> entityCopyList = entityList.stream().map(entity -> {
        StandaloneConfig entityCopy = BeanUtils.copyProperties(entity, new StandaloneConfig());
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
      for (StandaloneConfig entity : entityList) {
        completeUpdate(entity);
      }
      standaloneConfigCurder.batchUpdateById(entityList);
      return entityList.size();
    });
  }

  private void completeUpdate(StandaloneConfig entity) {
    entity.setLogSnapshotId(UUID.randomUUID().toString().replace("-", ""));
  }

  public void delete(Long id) {
    StandaloneConfig entity = standaloneConfigCurder.queryById(id);
    update(entity, new Consumer<StandaloneConfig>() {
      @Override
      public void accept(StandaloneConfig entity) {
        entity.setIfLog(1);
      }
    });
  }

  public void batchDelete(List<Long> idList) {
    List<StandaloneConfig> entityList = standaloneConfigCurder.queryByIdList(idList);
    batchUpdate(entityList, new Consumer<List<StandaloneConfig>>() {
      @Override
      public void accept(List<StandaloneConfig> entityList) {
        for (StandaloneConfig entity : entityList) {
          entity.setIfLog(1);
        }
      }
    });
  }

  public StandaloneConfig queryById(long id) {
    return standaloneConfigCurder.queryById(id);
  }

}
