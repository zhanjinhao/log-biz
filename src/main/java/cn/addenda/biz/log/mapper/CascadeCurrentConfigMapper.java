package cn.addenda.biz.log.mapper;

import cn.addenda.biz.log.entity.CascadeCurrentConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (CascadeCurrentConfig)表数据库访问层
 *
 * @author addenda
 * @since 2025-08-16 12:06:37
 */
public interface CascadeCurrentConfigMapper {
  /**
   * 新增数据
   */
  int insert(CascadeCurrentConfig cascadeCurrentConfig);

  /**
   * 按ID更新数据
   */
  int updateById(CascadeCurrentConfig cascadeCurrentConfig);

  /**
   * 按ID删除数据
   */
  int deleteById(@Param("id") Long id);

  /**
   * 按实体类删除数据
   */
  int deleteByEntity(CascadeCurrentConfig cascadeCurrentConfig);

  /**
   * 按实体类查询数据
   */
  List<CascadeCurrentConfig> queryByEntity(CascadeCurrentConfig cascadeCurrentConfig);

  /**
   * 按ID查询数据
   */
  CascadeCurrentConfig queryById(@Param("id") Long id);

  /**
   * 按ID集合查询数据
   */
  List<CascadeCurrentConfig> queryByIdList(@Param("idList") List<Long> idList);

  /**
   * 按实体类计数数据
   */
  Long countByEntity(CascadeCurrentConfig cascadeCurrentConfig);

  /**
   * 按父ID集合查询数据
   */
  List<CascadeCurrentConfig> queryByForeignIdList(@Param("foreignIdList") List<Long> foreignIdList);

  CascadeCurrentConfig queryByLogSnapshotId(@Param("logSnapshotId") String logSnapshotId);

}
