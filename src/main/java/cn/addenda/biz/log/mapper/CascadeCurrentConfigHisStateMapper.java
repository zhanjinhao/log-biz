package cn.addenda.biz.log.mapper;

import cn.addenda.biz.log.entity.CascadeCurrentConfigHisState;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (CascadeCurrentConfigHisState)表数据库访问层
 *
 * @author addenda
 * @since 2025-08-17 13:14:48
 */
public interface CascadeCurrentConfigHisStateMapper {
  /**
   * 新增数据
   */
  int insert(CascadeCurrentConfigHisState cascadeCurrentConfigHisState);

  /**
   * 按ID更新数据
   */
  int updateById(CascadeCurrentConfigHisState cascadeCurrentConfigHisState);

  /**
   * 按ID删除数据
   */
  int deleteById(@Param("id") Long id);

  /**
   * 按实体类删除数据
   */
  int deleteByEntity(CascadeCurrentConfigHisState cascadeCurrentConfigHisState);

  /**
   * 按实体类查询数据
   */
  List<CascadeCurrentConfigHisState> queryByEntity(CascadeCurrentConfigHisState cascadeCurrentConfigHisState);

  /**
   * 按ID查询数据
   */
  CascadeCurrentConfigHisState queryById(@Param("id") Long id);

  /**
   * 按ID集合查询数据
   */
  List<CascadeCurrentConfigHisState> queryByIdList(@Param("idList") List<Long> idList);

  /**
   * 按实体类计数数据
   */
  Long countByEntity(CascadeCurrentConfigHisState cascadeCurrentConfigHisState);

}
