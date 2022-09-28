package cn.behappyto.export.controller;

import cn.behappyto.common.annotation.Log;
import cn.behappyto.common.annotation.RepeatSubmit;
import cn.behappyto.common.core.controller.BaseController;
import cn.behappyto.common.core.domain.PageQuery;
import cn.behappyto.common.core.domain.R;
import cn.behappyto.common.core.page.TableDataInfo;
import cn.behappyto.common.core.validate.AddGroup;
import cn.behappyto.common.core.validate.EditGroup;
import cn.behappyto.common.enums.BusinessType;
import cn.behappyto.common.utils.poi.ExcelUtil;
import cn.behappyto.export.domain.bo.ExportTemplateBo;
import cn.behappyto.export.domain.vo.ExportTemplateVo;
import cn.behappyto.export.service.IExportTemplateService;
import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

/**
 * 导出模板
 *
 * @author behappyto.cn
 * @date 2022-09-22
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/export/template")
public class ExportTemplateController extends BaseController {

    private final IExportTemplateService iExportTemplateService;

    /**
     * 查询导出模板列表
     */
    @SaCheckPermission("system:template:list")
    @GetMapping("/list")
    public TableDataInfo<ExportTemplateVo> list(ExportTemplateBo bo, PageQuery pageQuery) {
        return iExportTemplateService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出导出模板列表
     */
    @SaCheckPermission("system:template:export")
    @Log(title = "导出模板", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ExportTemplateBo bo, HttpServletResponse response) {
        List<ExportTemplateVo> list = iExportTemplateService.queryList(bo);
        ExcelUtil.exportExcel(list, "导出模板", ExportTemplateVo.class, response);
    }

    /**
     * 获取导出模板详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("system:template:query")
    @GetMapping("/{id}")
    public R<ExportTemplateVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(iExportTemplateService.queryById(id));
    }

    /**
     * 新增导出模板
     */
    @SaCheckPermission("system:template:add")
    @Log(title = "导出模板", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ExportTemplateBo bo) {
        return toAjax(iExportTemplateService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改导出模板
     */
    @SaCheckPermission("system:template:edit")
    @Log(title = "导出模板", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ExportTemplateBo bo) {
        return toAjax(iExportTemplateService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除导出模板
     *
     * @param ids 主键串
     */
    @SaCheckPermission("system:template:remove")
    @Log(title = "导出模板", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iExportTemplateService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }
}
