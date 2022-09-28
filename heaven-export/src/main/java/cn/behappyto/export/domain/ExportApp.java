package cn.behappyto.export.domain;

import cn.behappyto.common.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 应用信息对象 export_app
 *
 * @author behappyto.cn
 * @date 2022-09-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("export_app")
public class ExportApp extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 主鍵
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 应用编码
     */
    private String appId;
    /**
     * 应用名称
     */
    private String appName;
    /**
     * 应用密钥
     */
    private String appSecret;
    /**
     * 备注
     */
    private String remark;
    /**
     * 是否有效（0：无效  1：有效）
     */
    private Integer enabled;
    /**
     * 是否删除（0：正常 1：删除）
     */
    private Integer deleted;

}
