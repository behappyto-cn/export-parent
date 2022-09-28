package cn.behappyto.export.domain;

import cn.behappyto.common.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 导出任务对象 export_task
 *
 * @author behappyto.cn
 * @date 2022-09-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("export_task")
public class ExportTask extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    private Long id;
    /**
     * 任务名称
     */
    private String name;
    /**
     * 应用编码
     */
    private String appId;
    /**
     * 处理状态（0：等待导出； 1：执行成功； -1：执行失败； 2：正在执行； 3：已经下载）
     */
    private Long status;
    /**
     * 模板名称
     */
    private String templateName;
    /**
     * 用户编码
     */
    private String userId;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 回调方式
     */
    private String method;
    /**
     * 分片的值
     */
    private Long sliceNum;
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
