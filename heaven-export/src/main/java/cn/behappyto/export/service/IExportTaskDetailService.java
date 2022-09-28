package cn.behappyto.export.service;

import cn.behappyto.export.domain.ExportTaskDetail;
import cn.behappyto.export.domain.vo.ExportTaskDetailVo;
import cn.behappyto.export.domain.bo.ExportTaskDetailBo;
import cn.behappyto.common.core.page.TableDataInfo;
import cn.behappyto.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 任务明细Service接口
 *
 * @author behappyto.cn
 * @date 2022-09-23
 */
public interface IExportTaskDetailService {

    /**
     * 查询任务明细
     */
    ExportTaskDetailVo queryById(Long id);

    /**
     * 查询任务明细列表
     */
    TableDataInfo<ExportTaskDetailVo> queryPageList(ExportTaskDetailBo bo, PageQuery pageQuery);

    /**
     * 查询任务明细列表
     */
    List<ExportTaskDetailVo> queryList(ExportTaskDetailBo bo);

    /**
     * 新增任务明细
     */
    Boolean insertByBo(ExportTaskDetailBo bo);

    /**
     * 修改任务明细
     */
    Boolean updateByBo(ExportTaskDetailBo bo);

    /**
     * 校验并批量删除任务明细信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
