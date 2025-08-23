package cn.addenda.biz.log.bo;

import cn.addenda.biz.log.entity.CascadeChildConfig;
import cn.addenda.biz.log.entity.CascadeCurrentConfig;
import cn.addenda.biz.log.entity.CascadeParentConfig;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CascadeSnapshotBo {

  private CascadeParentConfig cascadeParentConfig;

  private CascadeCurrentConfig cascadeCurrentConfig;

  private CascadeChildConfig cascadeChildConfig;

  public static CascadeSnapshotBo of(
          CascadeParentConfig cascadeParentConfig,
          CascadeCurrentConfig cascadeCurrentConfig,
          CascadeChildConfig cascadeChildConfig) {
    CascadeSnapshotBo cascadeSnapshotBo = new CascadeSnapshotBo();
    cascadeSnapshotBo.setCascadeParentConfig(cascadeParentConfig);
    cascadeSnapshotBo.setCascadeCurrentConfig(cascadeCurrentConfig);
    cascadeSnapshotBo.setCascadeChildConfig(cascadeChildConfig);
    return cascadeSnapshotBo;
  }

  public String cascadedName() {
    return cascadeParentConfig.getName() + "-" + cascadeCurrentConfig.getName() + "-" + cascadeChildConfig.getName();
  }

}
