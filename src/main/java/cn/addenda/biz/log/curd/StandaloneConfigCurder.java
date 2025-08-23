package cn.addenda.biz.log.curd;

import cn.addenda.biz.log.entity.StandaloneConfig;
import cn.addenda.biz.log.mapper.StandaloneConfigMapper;
import cn.addenda.component.base.collection.BatchUtils;
import cn.addenda.component.mybatis.helper.BatchDmlHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * (StandaloneConfig)业务层
 *
 * @author addenda
 * @since 2025-08-16 11:23:24
 */
@Component
public class StandaloneConfigCurder {

  @Autowired
  private StandaloneConfigMapper standaloneConfigMapper;

  @Autowired
  private BatchDmlHelper batchDmlHelper;

  public int insert(StandaloneConfig standaloneConfig) {
    return standaloneConfigMapper.insert(standaloneConfig);
  }

  public int updateById(StandaloneConfig standaloneConfig) {
    return standaloneConfigMapper.updateById(standaloneConfig);
  }

  public int deleteById(Long id) {
    return standaloneConfigMapper.deleteById(id);
  }

  public void batchInsert(List<StandaloneConfig> standaloneConfigList) {
    if (CollectionUtils.isEmpty(standaloneConfigList)) {
      return;
    }
    standaloneConfigList.removeIf(Objects::isNull);
    batchDmlHelper.batch(StandaloneConfigMapper.class, standaloneConfigList,
            (mapper, standaloneConfig) -> {
              mapper.insert(standaloneConfig);
            });
  }

  public void batchUpdateById(List<StandaloneConfig> standaloneConfigList) {
    if (CollectionUtils.isEmpty(standaloneConfigList)) {
      return;
    }
    standaloneConfigList.removeIf(Objects::isNull);
    batchDmlHelper.batch(StandaloneConfigMapper.class, standaloneConfigList,
            (mapper, standaloneConfig) -> {
              mapper.updateById(standaloneConfig);
            });
  }

  public void batchDeleteById(List<Long> idList) {
    if (CollectionUtils.isEmpty(idList)) {
      return;
    }
    idList.removeIf(Objects::isNull);
    batchDmlHelper.batch(StandaloneConfigMapper.class, idList,
            (mapper, id) -> {
              mapper.deleteById(id);
            });
  }

  public StandaloneConfig queryById(Long id) {
    return standaloneConfigMapper.queryById(id);
  }

  public List<StandaloneConfig> queryByIdList(List<Long> idList) {
    if (CollectionUtils.isEmpty(idList)) {
      return new ArrayList<>();
    }
    return BatchUtils.applyListInBatches(idList,
            longs -> standaloneConfigMapper.queryByIdList(longs));
  }

}
