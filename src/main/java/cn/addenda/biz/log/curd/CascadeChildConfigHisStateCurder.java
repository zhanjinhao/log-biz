package cn.addenda.biz.log.curd;

import cn.addenda.biz.log.entity.CascadeChildConfigHisState;
import cn.addenda.biz.log.mapper.CascadeChildConfigHisStateMapper;
import cn.addenda.component.mybatis.helper.BatchDmlHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * (CascadeChildConfigHisState)业务层
 *
 * @author addenda
 * @since 2025-08-17 13:14:57
 */
@Component
public class CascadeChildConfigHisStateCurder {

  @Autowired
  private CascadeChildConfigHisStateMapper cascadeChildConfigHisStateMapper;

  @Autowired
  private BatchDmlHelper batchDmlHelper;

  public int insert(CascadeChildConfigHisState cascadeChildConfigHisState) {
    return cascadeChildConfigHisStateMapper.insert(cascadeChildConfigHisState);
  }

  public int updateById(CascadeChildConfigHisState cascadeChildConfigHisState) {
    return cascadeChildConfigHisStateMapper.updateById(cascadeChildConfigHisState);
  }

  public int deleteById(Long id) {
    return cascadeChildConfigHisStateMapper.deleteById(id);
  }

  public void batchInsert(List<CascadeChildConfigHisState> cascadeChildConfigHisStateList) {
    if (CollectionUtils.isEmpty(cascadeChildConfigHisStateList)) {
      return;
    }
    cascadeChildConfigHisStateList.removeIf(Objects::isNull);
    batchDmlHelper.batch(CascadeChildConfigHisStateMapper.class, cascadeChildConfigHisStateList,
            (mapper, cascadeChildConfigHisState) -> {
              mapper.insert(cascadeChildConfigHisState);
            });
  }

  public void batchUpdateById(List<CascadeChildConfigHisState> cascadeChildConfigHisStateList) {
    if (CollectionUtils.isEmpty(cascadeChildConfigHisStateList)) {
      return;
    }
    cascadeChildConfigHisStateList.removeIf(Objects::isNull);
    batchDmlHelper.batch(CascadeChildConfigHisStateMapper.class, cascadeChildConfigHisStateList,
            (mapper, cascadeChildConfigHisState) -> {
              mapper.updateById(cascadeChildConfigHisState);
            });
  }

  public void batchDeleteById(List<Long> idList) {
    if (CollectionUtils.isEmpty(idList)) {
      return;
    }
    idList.removeIf(Objects::isNull);
    batchDmlHelper.batch(CascadeChildConfigHisStateMapper.class, idList,
            (mapper, id) -> {
              mapper.deleteById(id);
            });
  }

  public List<CascadeChildConfigHisState> queryByEntity(CascadeChildConfigHisState param) {
    return cascadeChildConfigHisStateMapper.queryByEntity(param);
  }

}
