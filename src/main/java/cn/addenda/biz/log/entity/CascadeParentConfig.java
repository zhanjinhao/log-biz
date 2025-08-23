package cn.addenda.biz.log.entity;

import cn.addenda.mybatisbasemodel.simple.SimpleBaseModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * (CascadeParentConfig)实体类
 *
 * @author makejava
 * @since 2025-08-16 12:06:21
 */
@Setter
@Getter
@ToString
public class CascadeParentConfig extends SimpleBaseModel implements Serializable {
  private static final long serialVersionUID = -40897524283217723L;

  private Long id;

  private String code;

  private String name;
  /**
   * 日志组
   */
  private String logGroup;
  /**
   * 快照ID：业务表关联的ID
   */
  private String logSnapshotId;
  /**
   * 1日志，0非日志。同一个日志组里，0状态的数据只有一个
   */
  private Integer ifLog;
  /**
   * 父亲
   */
  private Long parentId;

  public static CascadeParentConfig ofParam() {
    return new CascadeParentConfig();
  }

}

