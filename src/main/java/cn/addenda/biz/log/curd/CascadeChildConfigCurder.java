package cn.addenda.biz.log.curd;

import cn.addenda.biz.log.entity.CascadeChildConfig;
import cn.addenda.biz.log.mapper.CascadeChildConfigMapper;
import cn.addenda.component.base.collection.BatchUtils;
import cn.addenda.component.mybatis.helper.BatchDmlHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * (CascadeChildConfig)业务层
 *
 * @author addenda
 * @since 2025-08-16 12:06:46
 */
@Component
public class CascadeChildConfigCurder {

  @Autowired
  private CascadeChildConfigMapper cascadeChildConfigMapper;

  @Autowired
  private BatchDmlHelper batchDmlHelper;

  public int insert(CascadeChildConfig cascadeChildConfig) {
    return cascadeChildConfigMapper.insert(cascadeChildConfig);
  }

  public int updateById(CascadeChildConfig cascadeChildConfig) {
    return cascadeChildConfigMapper.updateById(cascadeChildConfig);
  }

  public int deleteById(Long id) {
    return cascadeChildConfigMapper.deleteById(id);
  }

  public void batchInsert(List<CascadeChildConfig> cascadeChildConfigList) {
    if (CollectionUtils.isEmpty(cascadeChildConfigList)) {
      return;
    }
    cascadeChildConfigList.removeIf(Objects::isNull);
    batchDmlHelper.batch(CascadeChildConfigMapper.class, cascadeChildConfigList,
            (mapper, cascadeChildConfig) -> {
              mapper.insert(cascadeChildConfig);
            });
  }

  public void batchUpdateById(List<CascadeChildConfig> cascadeChildConfigList) {
    if (CollectionUtils.isEmpty(cascadeChildConfigList)) {
      return;
    }
    cascadeChildConfigList.removeIf(Objects::isNull);
    batchDmlHelper.batch(CascadeChildConfigMapper.class, cascadeChildConfigList,
            (mapper, cascadeChildConfig) -> {
              mapper.updateById(cascadeChildConfig);
            });
  }

  public void batchDeleteById(List<Long> idList) {
    if (CollectionUtils.isEmpty(idList)) {
      return;
    }
    idList.removeIf(Objects::isNull);
    batchDmlHelper.batch(CascadeChildConfigMapper.class, idList,
            (mapper, id) -> {
              mapper.deleteById(id);
            });
  }

  public CascadeChildConfig queryById(Long id) {
    return cascadeChildConfigMapper.queryById(id);
  }

  public List<CascadeChildConfig> queryByIdList(List<Long> idList) {
    if (CollectionUtils.isEmpty(idList)) {
      return new ArrayList<>();
    }
    return BatchUtils.applyListInBatches(idList,
            longs -> cascadeChildConfigMapper.queryByIdList(longs));
  }

  public List<CascadeChildConfig> queryByEntity(CascadeChildConfig param) {
    return cascadeChildConfigMapper.queryByEntity(param);
  }

  public List<CascadeChildConfig> queryByForeignIdList(List<Long> foreignIdList) {
    if (CollectionUtils.isEmpty(foreignIdList)) {
      return new ArrayList<>();
    }
    return BatchUtils.applyListInBatches(foreignIdList,
            longs -> cascadeChildConfigMapper.queryByForeignIdList(longs));
  }

  public CascadeChildConfig queryByLogSnapshotId(String logSnapshotId) {
    return cascadeChildConfigMapper.queryByLogSnapshotId(logSnapshotId);
  }

}
