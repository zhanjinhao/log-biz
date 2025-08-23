package cn.addenda.biz.log.mapper;

import cn.addenda.biz.log.entity.CascadeChildConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (CascadeChildConfig)表数据库访问层
 *
 * @author addenda
 * @since 2025-08-16 12:06:46
 */
public interface CascadeChildConfigMapper {
  /**
   * 新增数据
   */
  int insert(CascadeChildConfig cascadeChildConfig);

  /**
   * 按ID更新数据
   */
  int updateById(CascadeChildConfig cascadeChildConfig);

  /**
   * 按ID删除数据
   */
  int deleteById(@Param("id") Long id);

  /**
   * 按实体类删除数据
   */
  int deleteByEntity(CascadeChildConfig cascadeChildConfig);

  /**
   * 按实体类查询数据
   */
  List<CascadeChildConfig> queryByEntity(CascadeChildConfig cascadeChildConfig);

  /**
   * 按ID查询数据
   */
  CascadeChildConfig queryById(@Param("id") Long id);

  /**
   * 按ID集合查询数据
   */
  List<CascadeChildConfig> queryByIdList(@Param("idList") List<Long> idList);

  /**
   * 按实体类计数数据
   */
  Long countByEntity(CascadeChildConfig cascadeChildConfig);

  List<CascadeChildConfig> queryByForeignIdList(@Param("foreignIdList") List<Long> foreignIdList);

  CascadeChildConfig queryByLogSnapshotId(@Param("logSnapshotId") String logSnapshotId);
}
