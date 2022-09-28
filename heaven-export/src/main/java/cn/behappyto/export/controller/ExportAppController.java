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
import cn.behappyto.export.domain.bo.ExportAppBo;
import cn.behappyto.export.domain.vo.ExportAppVo;
import cn.behappyto.export.service.IExportAppService;
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
 * 应用信息
 *
 * @author behappyto.cn
 * @date 2022-09-22
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/export/app")
public class ExportAppController extends BaseController {

    private final IExportAppService iExportAppService;

    /**
     * 查询应用信息列表
     */
    @SaCheckPermission("system:app:list")
    @GetMapping("/list")
    public TableDataInfo<ExportAppVo> list(ExportAppBo bo, PageQuery pageQuery) {
        return iExportAppService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出应用信息列表
     */
    @SaCheckPermission("system:app:export")
    @Log(title = "应用信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ExportAppBo bo, HttpServletResponse response) {
        List<ExportAppVo> list = iExportAppService.queryList(bo);
        ExcelUtil.exportExcel(list, "应用信息", ExportAppVo.class, response);
    }

    /**
     * 获取应用信息详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("system:app:query")
    @GetMapping("/{id}")
    public R<ExportAppVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(iExportAppService.queryById(id));
    }

    /**
     * 新增应用信息
     */
    @SaCheckPermission("system:app:add")
    @Log(title = "应用信息", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ExportAppBo bo) {
        return toAjax(iExportAppService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改应用信息
     */
    @SaCheckPermission("system:app:edit")
    @Log(title = "应用信息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ExportAppBo bo) {
        return toAjax(iExportAppService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除应用信息
     *
     * @param ids 主键串
     */
    @SaCheckPermission("system:app:remove")
    @Log(title = "应用信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iExportAppService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }
}
