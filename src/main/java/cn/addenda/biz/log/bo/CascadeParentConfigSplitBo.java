package cn.addenda.biz.log.bo;

import cn.addenda.biz.log.entity.CascadeParentConfig;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
public class CascadeParentConfigSplitBo extends CascadeParentConfig {

  List<CascadeCurrentConfigSplitBo> cascadeCurrentConfigBoList = new ArrayList<>();

}
