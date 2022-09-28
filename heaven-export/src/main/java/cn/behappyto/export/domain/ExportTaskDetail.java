package cn.behappyto.export.domain;

import cn.behappyto.common.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 任务明细对象 export_task_detail
 *
 * @author behappyto.cn
 * @date 2022-09-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("export_task_detail")
public class ExportTaskDetail extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 任务主键
     */
    private Long taskId;
    /**
     * 名称（Excel填充前缀）
     */
    private String name;
    /**
     * 请求方法
     */
    private String method;
    /**
     * 基本参数
     */
    private String param;
    /**
     * 备注
     */
    private String remark;
    /**
     * 是否有效(0：无效  1：有效)
     */
    private Integer enabled;
    /**
     * 是否删除(0：正常 1：删除)
     */
    private Integer deleted;

}
