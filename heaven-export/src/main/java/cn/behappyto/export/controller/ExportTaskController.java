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
import cn.behappyto.export.domain.bo.ExportTaskBo;
import cn.behappyto.export.domain.vo.ExportTaskVo;
import cn.behappyto.export.service.IExportTaskService;
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
 * 导出任务
 *
 * @author behappyto.cn
 * @date 2022-09-22
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/export/task")
public class ExportTaskController extends BaseController {

    private final IExportTaskService iExportTaskService;

    /**
     * 查询导出任务列表
     */
    @SaCheckPermission("system:task:list")
    @GetMapping("/list")
    public TableDataInfo<ExportTaskVo> list(ExportTaskBo bo, PageQuery pageQuery) {
        return iExportTaskService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出导出任务列表
     */
    @SaCheckPermission("system:task:export")
    @Log(title = "导出任务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ExportTaskBo bo, HttpServletResponse response) {
        List<ExportTaskVo> list = iExportTaskService.queryList(bo);
        ExcelUtil.exportExcel(list, "导出任务", ExportTaskVo.class, response);
    }

    /**
     * 获取导出任务详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("system:task:query")
    @GetMapping("/{id}")
    public R<ExportTaskVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(iExportTaskService.queryById(id));
    }

    /**
     * 新增导出任务
     */
    @SaCheckPermission("system:task:add")
    @Log(title = "导出任务", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ExportTaskBo bo) {
        return toAjax(iExportTaskService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改导出任务
     */
    @SaCheckPermission("system:task:edit")
    @Log(title = "导出任务", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ExportTaskBo bo) {
        return toAjax(iExportTaskService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除导出任务
     *
     * @param ids 主键串
     */
    @SaCheckPermission("system:task:remove")
    @Log(title = "导出任务", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iExportTaskService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }
}
