package cn.behappyto.export.domain;

import cn.behappyto.common.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 导出模板对象 export_template
 *
 * @author behappyto.cn
 * @date 2022-09-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("export_template")
public class ExportTemplate extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 应用编码
     */
    private String appId;
    /**
     * 模板名称
     */
    private String name;
    /**
     * 类型
     */
    private String type;
    /**
     * 模板路径
     */
    private String url;
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
