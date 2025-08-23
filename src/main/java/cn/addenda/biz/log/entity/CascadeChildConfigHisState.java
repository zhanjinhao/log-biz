package cn.addenda.biz.log.entity;

import cn.addenda.mybatisbasemodel.simple.SimpleBaseModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * (CascadeChildConfigHisState)实体类
 *
 * @author makejava
 * @since 2025-08-17 13:14:57
 */
@Setter
@Getter
@ToString
public class CascadeChildConfigHisState extends SimpleBaseModel implements Serializable {
  private static final long serialVersionUID = -96412562554922320L;

  private Long id;

  private String stateId;

  private Long cascadeChildConfigId;

  private String code;

  private String name;
  /**
   * 日志组
   */
  private String logGroup;
  /**
   * 业务表关联的ID
   */
  private String logSnapshotId;
  /**
   * 1日志，0非日志。同一个日志组里，0状态的数据只有一个
   */
  private Integer ifLog;
  /**
   * 外键表ID
   */
  private Long foreignId;
  /**
   * 外键表快照ID
   */
  private String foreignLogSnapshotId;

  // ---------
  //  查询参数
  // ---------

  private List<String> foreignLogSnapshotIdList;

  private List<Long> foreignIdList;

  public static CascadeChildConfigHisState ofParam() {
    return new CascadeChildConfigHisState();
  }

}

