package cn.addenda.biz.log.curd;

import cn.addenda.biz.log.entity.CascadeCurrentConfigHisState;
import cn.addenda.biz.log.mapper.CascadeCurrentConfigHisStateMapper;
import cn.addenda.component.mybatis.helper.BatchDmlHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * (CascadeCurrentConfigHisState)业务层
 *
 * @author addenda
 * @since 2025-08-17 13:14:49
 */
@Component
public class CascadeCurrentConfigHisStateCurder {

  @Autowired
  private CascadeCurrentConfigHisStateMapper cascadeCurrentConfigHisStateMapper;

  @Autowired
  private BatchDmlHelper batchDmlHelper;

  public int insert(CascadeCurrentConfigHisState cascadeCurrentConfigHisState) {
    return cascadeCurrentConfigHisStateMapper.insert(cascadeCurrentConfigHisState);
  }

  public int updateById(CascadeCurrentConfigHisState cascadeCurrentConfigHisState) {
    return cascadeCurrentConfigHisStateMapper.updateById(cascadeCurrentConfigHisState);
  }

  public int deleteById(Long id) {
    return cascadeCurrentConfigHisStateMapper.deleteById(id);
  }

  public void batchInsert(List<CascadeCurrentConfigHisState> cascadeCurrentConfigHisStateList) {
    if (CollectionUtils.isEmpty(cascadeCurrentConfigHisStateList)) {
      return;
    }
    cascadeCurrentConfigHisStateList.removeIf(Objects::isNull);
    batchDmlHelper.batch(CascadeCurrentConfigHisStateMapper.class, cascadeCurrentConfigHisStateList,
            (mapper, cascadeCurrentConfigHisState) -> {
              mapper.insert(cascadeCurrentConfigHisState);
            });
  }

  public void batchUpdateById(List<CascadeCurrentConfigHisState> cascadeCurrentConfigHisStateList) {
    if (CollectionUtils.isEmpty(cascadeCurrentConfigHisStateList)) {
      return;
    }
    cascadeCurrentConfigHisStateList.removeIf(Objects::isNull);
    batchDmlHelper.batch(CascadeCurrentConfigHisStateMapper.class, cascadeCurrentConfigHisStateList,
            (mapper, cascadeCurrentConfigHisState) -> {
              mapper.updateById(cascadeCurrentConfigHisState);
            });
  }

  public void batchDeleteById(List<Long> idList) {
    if (CollectionUtils.isEmpty(idList)) {
      return;
    }
    idList.removeIf(Objects::isNull);
    batchDmlHelper.batch(CascadeCurrentConfigHisStateMapper.class, idList,
            (mapper, id) -> {
              mapper.deleteById(id);
            });
  }

  public List<CascadeCurrentConfigHisState> queryByEntity(CascadeCurrentConfigHisState cascadeCurrentConfigHisState) {
    return cascadeCurrentConfigHisStateMapper.queryByEntity(cascadeCurrentConfigHisState);
  }

}
