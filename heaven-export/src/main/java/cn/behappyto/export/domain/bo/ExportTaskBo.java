package cn.behappyto.export.domain.bo;

import cn.behappyto.common.core.domain.BaseEntity;
import cn.behappyto.common.core.validate.AddGroup;
import cn.behappyto.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;


/**
 * 导出任务业务对象 export_task
 *
 * @author behappyto.cn
 * @date 2022-09-23
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class ExportTaskBo extends BaseEntity {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 任务名称
     */
    @NotBlank(message = "任务名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 应用编码
     */
    @NotBlank(message = "应用编码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String appId;

    /**
     * 处理状态（0：等待导出； 1：执行成功； -1：执行失败； 2：正在执行； 3：已经下载）
     */
    @NotNull(message = "处理状态（0：等待导出； 1：执行成功； -1：执行失败； 2：正在执行； 3：已经下载）不能为空", groups = { EditGroup.class })
    private Long status;

    /**
     * 模板主键
     */
    @NotNull(message = "模板主键不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long templateId;

    /**
     * 模板名称
     */
    @NotBlank(message = "模板名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String templateName;

    /**
     * 回调方式
     */
    @NotBlank(message = "回调方式不能为空", groups = { AddGroup.class, EditGroup.class })
    private String method;

    /**
     * 分片的值
     */
    @NotNull(message = "分片的值不能为空", groups = { EditGroup.class })
    private Long sliceNum;

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
