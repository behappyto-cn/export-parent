package cn.behappyto.export.domain.vo;

import cn.behappyto.common.annotation.ExcelDictFormat;
import cn.behappyto.common.convert.ExcelDictConvert;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;


/**
 * 导出模板视图对象 export_template
 *
 * @author behappyto.cn
 * @date 2022-09-23
 */
@Data
@ExcelIgnoreUnannotated
public class ExportTemplateVo {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 应用编码
     */
    @ExcelProperty(value = "应用编码")
    private String appId;

    /**
     * 模板名称
     */
    @ExcelProperty(value = "模板名称")
    private String name;

    /**
     * 类型
     */
    @ExcelProperty(value = "类型")
    private String type;

    /**
     * 模板路径
     */
    @ExcelProperty(value = "模板路径")
    private String url;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

    /**
     * 是否有效(0：无效  1：有效)
     */
    @ExcelProperty(value = "是否有效(0：无效  1：有效)", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_enable_status")
    private Integer enabled;

    /**
     * 是否删除(0：正常 1：删除)
     */
    @ExcelProperty(value = "是否删除(0：正常 1：删除)", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_delete_status")
    private Integer deleted;

    /**
     * 创建者
     */
    @ExcelProperty(value = "创建者")
    private String createBy;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 更新者
     */
    @ExcelProperty(value = "更新者")
    private String updateBy;

    /**
     * 更新时间
     */
    @ExcelProperty(value = "更新时间")
    private Date updateTime;


}
