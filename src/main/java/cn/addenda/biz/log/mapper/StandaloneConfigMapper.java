package cn.addenda.biz.log.mapper;

import cn.addenda.biz.log.entity.StandaloneConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (StandaloneConfig)表数据库访问层
 *
 * @author addenda
 * @since 2025-08-16 11:23:24
 */
public interface StandaloneConfigMapper {
  /**
   * 新增数据
   */
  int insert(StandaloneConfig standaloneConfig);

  /**
   * 按ID更新数据
   */
  int updateById(StandaloneConfig standaloneConfig);

  /**
   * 按ID删除数据
   */
  int deleteById(@Param("id") Long id);

  /**
   * 按实体类删除数据
   */
  int deleteByEntity(StandaloneConfig standaloneConfig);

  /**
   * 按实体类查询数据
   */
  List<StandaloneConfig> queryByEntity(StandaloneConfig standaloneConfig);

  /**
   * 按ID查询数据
   */
  StandaloneConfig queryById(@Param("id") Long id);

  /**
   * 按ID集合查询数据
   */
  List<StandaloneConfig> queryByIdList(@Param("idList") List<Long> idList);

  /**
   * 按实体类计数数据
   */
  Long countByEntity(StandaloneConfig standaloneConfig);

}
