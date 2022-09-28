package cn.behappyto.export.domain.bo;

import cn.behappyto.common.core.domain.BaseEntity;
import cn.behappyto.common.core.validate.AddGroup;
import cn.behappyto.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;


/**
 * 导出模板业务对象 export_template
 *
 * @author behappyto.cn
 * @date 2022-09-23
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class ExportTemplateBo extends BaseEntity {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 应用编码
     */
    @NotBlank(message = "应用编码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String appId;

    /**
     * 模板名称
     */
    @NotBlank(message = "模板名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 类型
     */
    @NotBlank(message = "类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String type;

    /**
     * 模板路径
     */
    @NotBlank(message = "模板路径不能为空", groups = { AddGroup.class, EditGroup.class })
    private String url;

    /**
     * 备注
     */
    @NotBlank(message = "备注不能为空", groups = { AddGroup.class, EditGroup.class })
    private String remark;

    /**
     * 是否有效(0：无效  1：有效)
     */
    @NotNull(message = "是否有效(0：无效  1：有效)不能为空", groups = { EditGroup.class })
    private Integer enabled;

    /**
     * 是否删除(0：正常 1：删除)
     */
    @NotNull(message = "是否删除(0：正常 1：删除)不能为空", groups = { EditGroup.class })
    private Integer deleted;


}
