package cn.addenda.biz.log.mapper;

import cn.addenda.biz.log.entity.CascadeParentConfigHisState;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (CascadeParentConfigHisState)表数据库访问层
 *
 * @author addenda
 * @since 2025-08-17 13:14:39
 */
public interface CascadeParentConfigHisStateMapper {
  /**
   * 新增数据
   */
  int insert(CascadeParentConfigHisState cascadeParentConfigHisState);

  /**
   * 按ID更新数据
   */
  int updateById(CascadeParentConfigHisState cascadeParentConfigHisState);

  /**
   * 按ID删除数据
   */
  int deleteById(@Param("id") Long id);

  /**
   * 按实体类删除数据
   */
  int deleteByEntity(CascadeParentConfigHisState cascadeParentConfigHisState);

  /**
   * 按实体类查询数据
   */
  List<CascadeParentConfigHisState> queryByEntity(CascadeParentConfigHisState cascadeParentConfigHisState);

  /**
   * 按ID查询数据
   */
  CascadeParentConfigHisState queryById(@Param("id") Long id);

  /**
   * 按ID集合查询数据
   */
  List<CascadeParentConfigHisState> queryByIdList(@Param("idList") List<Long> idList);

  /**
   * 按实体类计数数据
   */
  Long countByEntity(CascadeParentConfigHisState cascadeParentConfigHisState);

  CascadeParentConfigHisState queryLatestOfParentConfig(@Param("parentConfigId") Long parentConfigId);

}
