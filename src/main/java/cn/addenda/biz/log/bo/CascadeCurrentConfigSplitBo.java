package cn.addenda.biz.log.bo;

import cn.addenda.biz.log.entity.CascadeChildConfig;
import cn.addenda.biz.log.entity.CascadeCurrentConfig;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class CascadeCurrentConfigSplitBo extends CascadeCurrentConfig {

  private List<CascadeChildConfig> cascadeChildConfigList;

}
