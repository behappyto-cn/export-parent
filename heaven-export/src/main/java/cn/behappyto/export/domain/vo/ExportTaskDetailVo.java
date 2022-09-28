package cn.behappyto.export.domain.vo;

import cn.behappyto.common.annotation.ExcelDictFormat;
import cn.behappyto.common.convert.ExcelDictConvert;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;


/**
 * 任务明细视图对象 export_task_detail
 *
 * @author behappyto.cn
 * @date 2022-09-23
 */
@Data
@ExcelIgnoreUnannotated
public class ExportTaskDetailVo {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 任务主键
     */
    @ExcelProperty(value = "任务主键")
    private Long taskId;

    /**
     * 名称（Excel填充前缀）
     */
    @ExcelProperty(value = "名称", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "E=xcel填充前缀")
    private String name;

    /**
     * 请求方法
     */
    @ExcelProperty(value = "请求方法")
    private String method;

    /**
     * 基本参数
     */
    @ExcelProperty(value = "基本参数")
    private String param;

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
