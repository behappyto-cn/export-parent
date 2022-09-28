package cn.behappyto.export.domain.bo;

import cn.behappyto.common.core.domain.BaseEntity;
import cn.behappyto.common.core.validate.AddGroup;
import cn.behappyto.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;


/**
 * 应用信息业务对象 export_app
 *
 * @author behappyto.cn
 * @date 2022-09-23
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class ExportAppBo extends BaseEntity {

    /**
     * 主鍵
     */
    @NotNull(message = "主鍵不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 应用编码
     */
    @NotBlank(message = "应用编码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String appId;

    /**
     * 应用名称
     */
    @NotBlank(message = "应用名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String appName;

    /**
     * 应用密钥
     */
    @NotBlank(message = "应用密钥不能为空", groups = { AddGroup.class, EditGroup.class })
    private String appSecret;

    /**
     * 备注
     */
    @NotBlank(message = "备注不能为空", groups = { AddGroup.class, EditGroup.class })
    private String remark;

    /**
     * 是否有效（0：无效  1：有效）
     */
    @NotNull(message = "是否有效（0：无效  1：有效）不能为空", groups = { EditGroup.class })
    private Integer enabled;

    /**
     * 是否删除（0：正常 1：删除）
     */
    @NotNull(message = "是否删除（0：正常 1：删除）不能为空", groups = { EditGroup.class })
    private Integer deleted;


}
