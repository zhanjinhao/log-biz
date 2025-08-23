package cn.addenda.biz.log.curd;

import cn.addenda.biz.log.entity.CascadeParentConfig;
import cn.addenda.biz.log.mapper.CascadeParentConfigMapper;
import cn.addenda.component.base.collection.BatchUtils;
import cn.addenda.component.mybatis.helper.BatchDmlHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * (CascadeParentConfig)业务层
 *
 * @author addenda
 * @since 2025-08-16 12:06:21
 */
@Component
public class CascadeParentConfigCurder {

  @Autowired
  private CascadeParentConfigMapper cascadeParentConfigMapper;

  @Autowired
  private BatchDmlHelper batchDmlHelper;

  public int insert(CascadeParentConfig cascadeParentConfig) {
    return cascadeParentConfigMapper.insert(cascadeParentConfig);
  }

  public int updateById(CascadeParentConfig cascadeParentConfig) {
    return cascadeParentConfigMapper.updateById(cascadeParentConfig);
  }

  public int deleteById(Long id) {
    return cascadeParentConfigMapper.deleteById(id);
  }

  public void batchInsert(List<CascadeParentConfig> cascadeParentConfigList) {
    if (CollectionUtils.isEmpty(cascadeParentConfigList)) {
      return;
    }
    cascadeParentConfigList.removeIf(Objects::isNull);
    batchDmlHelper.batch(CascadeParentConfigMapper.class, cascadeParentConfigList,
            (mapper, cascadeParentConfig) -> {
              mapper.insert(cascadeParentConfig);
            });
  }

  public void batchUpdateById(List<CascadeParentConfig> cascadeParentConfigList) {
    if (CollectionUtils.isEmpty(cascadeParentConfigList)) {
      return;
    }
    cascadeParentConfigList.removeIf(Objects::isNull);
    batchDmlHelper.batch(CascadeParentConfigMapper.class, cascadeParentConfigList,
            (mapper, cascadeParentConfig) -> {
              mapper.updateById(cascadeParentConfig);
            });
  }

  public void batchDeleteById(List<Long> idList) {
    if (CollectionUtils.isEmpty(idList)) {
      return;
    }
    idList.removeIf(Objects::isNull);
    batchDmlHelper.batch(CascadeParentConfigMapper.class, idList,
            (mapper, id) -> {
              mapper.deleteById(id);
            });
  }

  public CascadeParentConfig queryById(Long id) {
    return cascadeParentConfigMapper.queryById(id);
  }

  public List<CascadeParentConfig> queryByIdList(List<Long> idList) {
    if (CollectionUtils.isEmpty(idList)) {
      return new ArrayList<>();
    }
    return BatchUtils.applyListInBatches(idList,
            longs -> cascadeParentConfigMapper.queryByIdList(longs));
  }

  public List<CascadeParentConfig> queryByEntity(CascadeParentConfig cascadeParentConfig) {
    return cascadeParentConfigMapper.queryByEntity(cascadeParentConfig);
  }

  public CascadeParentConfig queryByLogSnapshotId(String logSnapshotId) {
    return cascadeParentConfigMapper.queryByLogSnapshotId(logSnapshotId);
  }

}
