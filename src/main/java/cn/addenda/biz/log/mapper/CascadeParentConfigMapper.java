package cn.addenda.biz.log.mapper;

import cn.addenda.biz.log.entity.CascadeParentConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (CascadeParentConfig)表数据库访问层
 *
 * @author addenda
 * @since 2025-08-16 12:06:21
 */
public interface CascadeParentConfigMapper {
  /**
   * 新增数据
   */
  int insert(CascadeParentConfig cascadeParentConfig);

  /**
   * 按ID更新数据
   */
  int updateById(CascadeParentConfig cascadeParentConfig);

  /**
   * 按ID删除数据
   */
  int deleteById(@Param("id") Long id);

  /**
   * 按实体类删除数据
   */
  int deleteByEntity(CascadeParentConfig cascadeParentConfig);

  /**
   * 按实体类查询数据
   */
  List<CascadeParentConfig> queryByEntity(CascadeParentConfig cascadeParentConfig);

  /**
   * 按ID查询数据
   */
  CascadeParentConfig queryById(@Param("id") Long id);

  /**
   * 按ID集合查询数据
   */
  List<CascadeParentConfig> queryByIdList(@Param("idList") List<Long> idList);

  /**
   * 按实体类计数数据
   */
  Long countByEntity(CascadeParentConfig cascadeParentConfig);

  CascadeParentConfig queryByLogSnapshotId(@Param("logSnapshotId") String logSnapshotId);

}
