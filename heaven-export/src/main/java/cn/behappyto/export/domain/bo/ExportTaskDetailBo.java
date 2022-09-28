package cn.behappyto.export.domain.bo;

import cn.behappyto.common.core.domain.BaseEntity;
import cn.behappyto.common.core.validate.AddGroup;
import cn.behappyto.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;


/**
 * 任务明细业务对象 export_task_detail
 *
 * @author behappyto.cn
 * @date 2022-09-23
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class ExportTaskDetailBo extends BaseEntity {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 任务主键
     */
    @NotNull(message = "任务主键不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long taskId;

    /**
     * 名称（Excel填充前缀）
     */
    @NotBlank(message = "名称（Excel填充前缀）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 请求方法
     */
    @NotBlank(message = "请求方法不能为空", groups = { AddGroup.class, EditGroup.class })
    private String method;

    /**
     * 基本参数
     */
    @NotBlank(message = "基本参数不能为空", groups = { AddGroup.class, EditGroup.class })
    private String param;

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
