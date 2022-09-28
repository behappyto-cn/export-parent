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
import cn.behappyto.export.domain.bo.ExportTaskDetailBo;
import cn.behappyto.export.domain.vo.ExportTaskDetailVo;
import cn.behappyto.export.service.IExportTaskDetailService;
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
 * 任务明细
 *
 * @author behappyto.cn
 * @date 2022-09-22
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/export/taskDetail")
public class ExportTaskDetailController extends BaseController {

    private final IExportTaskDetailService iExportTaskDetailService;

    /**
     * 查询任务明细列表
     */
    @SaCheckPermission("system:taskDetail:list")
    @GetMapping("/list")
    public TableDataInfo<ExportTaskDetailVo> list(ExportTaskDetailBo bo, PageQuery pageQuery) {
        return iExportTaskDetailService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出任务明细列表
     */
    @SaCheckPermission("system:taskDetail:export")
    @Log(title = "任务明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ExportTaskDetailBo bo, HttpServletResponse response) {
        List<ExportTaskDetailVo> list = iExportTaskDetailService.queryList(bo);
        ExcelUtil.exportExcel(list, "任务明细", ExportTaskDetailVo.class, response);
    }

    /**
     * 获取任务明细详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("system:taskDetail:query")
    @GetMapping("/{id}")
    public R<ExportTaskDetailVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(iExportTaskDetailService.queryById(id));
    }

    /**
     * 新增任务明细
     */
    @SaCheckPermission("system:taskDetail:add")
    @Log(title = "任务明细", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ExportTaskDetailBo bo) {
        return toAjax(iExportTaskDetailService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改任务明细
     */
    @SaCheckPermission("system:taskDetail:edit")
    @Log(title = "任务明细", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ExportTaskDetailBo bo) {
        return toAjax(iExportTaskDetailService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除任务明细
     *
     * @param ids 主键串
     */
    @SaCheckPermission("system:taskDetail:remove")
    @Log(title = "任务明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iExportTaskDetailService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }
}
