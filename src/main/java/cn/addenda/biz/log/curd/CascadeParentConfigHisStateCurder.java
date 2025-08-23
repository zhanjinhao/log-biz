package cn.addenda.biz.log.curd;

import cn.addenda.biz.log.entity.CascadeParentConfigHisState;
import cn.addenda.biz.log.mapper.CascadeParentConfigHisStateMapper;
import cn.addenda.component.mybatis.helper.BatchDmlHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * (CascadeParentConfigHisState)业务层
 *
 * @author addenda
 * @since 2025-08-17 13:14:39
 */
@Component
public class CascadeParentConfigHisStateCurder {

  @Autowired
  private CascadeParentConfigHisStateMapper cascadeParentConfigHisStateMapper;

  @Autowired
  private BatchDmlHelper batchDmlHelper;

  public int insert(CascadeParentConfigHisState cascadeParentConfigHisState) {
    return cascadeParentConfigHisStateMapper.insert(cascadeParentConfigHisState);
  }

  public int updateById(CascadeParentConfigHisState cascadeParentConfigHisState) {
    return cascadeParentConfigHisStateMapper.updateById(cascadeParentConfigHisState);
  }

  public int deleteById(Long id) {
    return cascadeParentConfigHisStateMapper.deleteById(id);
  }

  public void batchInsert(List<CascadeParentConfigHisState> cascadeParentConfigHisStateList) {
    if (CollectionUtils.isEmpty(cascadeParentConfigHisStateList)) {
      return;
    }
    cascadeParentConfigHisStateList.removeIf(Objects::isNull);
    batchDmlHelper.batch(CascadeParentConfigHisStateMapper.class, cascadeParentConfigHisStateList,
            (mapper, cascadeParentConfigHisState) -> {
              mapper.insert(cascadeParentConfigHisState);
            });
  }

  public void batchUpdateById(List<CascadeParentConfigHisState> cascadeParentConfigHisStateList) {
    if (CollectionUtils.isEmpty(cascadeParentConfigHisStateList)) {
      return;
    }
    cascadeParentConfigHisStateList.removeIf(Objects::isNull);
    batchDmlHelper.batch(CascadeParentConfigHisStateMapper.class, cascadeParentConfigHisStateList,
            (mapper, cascadeParentConfigHisState) -> {
              mapper.updateById(cascadeParentConfigHisState);
            });
  }

  public void batchDeleteById(List<Long> idList) {
    if (CollectionUtils.isEmpty(idList)) {
      return;
    }
    idList.removeIf(Objects::isNull);
    batchDmlHelper.batch(CascadeParentConfigHisStateMapper.class, idList,
            (mapper, id) -> {
              mapper.deleteById(id);
            });
  }

  public CascadeParentConfigHisState queryLatestOfParentConfig(Long parentConfigId) {
    return cascadeParentConfigHisStateMapper.queryLatestOfParentConfig(parentConfigId);
  }

}
