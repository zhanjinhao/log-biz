package cn.addenda.biz.log.curd;

import cn.addenda.biz.log.entity.CascadeCurrentConfig;
import cn.addenda.biz.log.mapper.CascadeCurrentConfigMapper;
import cn.addenda.component.base.collection.BatchUtils;
import cn.addenda.component.mybatis.helper.BatchDmlHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * (CascadeCurrentConfig)业务层
 *
 * @author addenda
 * @since 2025-08-16 12:06:37
 */
@Component
public class CascadeCurrentConfigCurder {

  @Autowired
  private CascadeCurrentConfigMapper cascadeCurrentConfigMapper;

  @Autowired
  private BatchDmlHelper batchDmlHelper;

  public int insert(CascadeCurrentConfig cascadeCurrentConfig) {
    return cascadeCurrentConfigMapper.insert(cascadeCurrentConfig);
  }

  public int updateById(CascadeCurrentConfig cascadeCurrentConfig) {
    return cascadeCurrentConfigMapper.updateById(cascadeCurrentConfig);
  }

  public int deleteById(Long id) {
    return cascadeCurrentConfigMapper.deleteById(id);
  }

  public void batchInsert(List<CascadeCurrentConfig> cascadeCurrentConfigList) {
    if (CollectionUtils.isEmpty(cascadeCurrentConfigList)) {
      return;
    }
    cascadeCurrentConfigList.removeIf(Objects::isNull);
    batchDmlHelper.batch(CascadeCurrentConfigMapper.class, cascadeCurrentConfigList,
            (mapper, cascadeCurrentConfig) -> {
              mapper.insert(cascadeCurrentConfig);
            });
  }

  public void batchUpdateById(List<CascadeCurrentConfig> cascadeCurrentConfigList) {
    if (CollectionUtils.isEmpty(cascadeCurrentConfigList)) {
      return;
    }
    cascadeCurrentConfigList.removeIf(Objects::isNull);
    batchDmlHelper.batch(CascadeCurrentConfigMapper.class, cascadeCurrentConfigList,
            (mapper, cascadeCurrentConfig) -> {
              mapper.updateById(cascadeCurrentConfig);
            });
  }

  public void batchDeleteById(List<Long> idList) {
    if (CollectionUtils.isEmpty(idList)) {
      return;
    }
    idList.removeIf(Objects::isNull);
    batchDmlHelper.batch(CascadeCurrentConfigMapper.class, idList,
            (mapper, id) -> {
              mapper.deleteById(id);
            });
  }

  public CascadeCurrentConfig queryById(Long id) {
    return cascadeCurrentConfigMapper.queryById(id);
  }

  public List<CascadeCurrentConfig> queryByIdList(List<Long> idList) {
    if (CollectionUtils.isEmpty(idList)) {
      return new ArrayList<>();
    }
    return BatchUtils.applyListInBatches(idList,
            longs -> cascadeCurrentConfigMapper.queryByIdList(longs));
  }

  public List<CascadeCurrentConfig> queryByEntity(CascadeCurrentConfig param) {
    return cascadeCurrentConfigMapper.queryByEntity(param);
  }

  public List<CascadeCurrentConfig> queryByForeignIdList(List<Long> foreignIdList) {
    if (CollectionUtils.isEmpty(foreignIdList)) {
      return new ArrayList<>();
    }
    return BatchUtils.applyListInBatches(foreignIdList,
            longs -> cascadeCurrentConfigMapper.queryByForeignIdList(longs));
  }

  public CascadeCurrentConfig queryByLogSnapshotId(String logSnapshotId) {
    return cascadeCurrentConfigMapper.queryByLogSnapshotId(logSnapshotId);
  }

}
