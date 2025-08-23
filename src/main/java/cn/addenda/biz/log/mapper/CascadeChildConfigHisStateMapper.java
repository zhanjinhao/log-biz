package cn.addenda.biz.log.mapper;

import cn.addenda.biz.log.entity.CascadeChildConfigHisState;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (CascadeChildConfigHisState)表数据库访问层
 *
 * @author addenda
 * @since 2025-08-17 13:14:57
 */
public interface CascadeChildConfigHisStateMapper {
  /**
   * 新增数据
   */
  int insert(CascadeChildConfigHisState cascadeChildConfigHisState);

  /**
   * 按ID更新数据
   */
  int updateById(CascadeChildConfigHisState cascadeChildConfigHisState);

  /**
   * 按ID删除数据
   */
  int deleteById(@Param("id") Long id);

  /**
   * 按实体类删除数据
   */
  int deleteByEntity(CascadeChildConfigHisState cascadeChildConfigHisState);

  /**
   * 按实体类查询数据
   */
  List<CascadeChildConfigHisState> queryByEntity(CascadeChildConfigHisState cascadeChildConfigHisState);

  /**
   * 按ID查询数据
   */
  CascadeChildConfigHisState queryById(@Param("id") Long id);

  /**
   * 按ID集合查询数据
   */
  List<CascadeChildConfigHisState> queryByIdList(@Param("idList") List<Long> idList);

  /**
   * 按实体类计数数据
   */
  Long countByEntity(CascadeChildConfigHisState cascadeChildConfigHisState);

}
