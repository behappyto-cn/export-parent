package cn.behappyto.export.service;

import cn.behappyto.export.domain.ExportTask;
import cn.behappyto.export.domain.vo.ExportTaskVo;
import cn.behappyto.export.domain.bo.ExportTaskBo;
import cn.behappyto.common.core.page.TableDataInfo;
import cn.behappyto.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 导出任务Service接口
 *
 * @author behappyto.cn
 * @date 2022-09-23
 */
public interface IExportTaskService {

    /**
     * 查询导出任务
     */
    ExportTaskVo queryById(Long id);

    /**
     * 查询导出任务列表
     */
    TableDataInfo<ExportTaskVo> queryPageList(ExportTaskBo bo, PageQuery pageQuery);

    /**
     * 查询导出任务列表
     */
    List<ExportTaskVo> queryList(ExportTaskBo bo);

    /**
     * 新增导出任务
     */
    Boolean insertByBo(ExportTaskBo bo);

    /**
     * 修改导出任务
     */
    Boolean updateByBo(ExportTaskBo bo);

    /**
     * 校验并批量删除导出任务信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 下载文件
     *
     * @param path 下载地址
     */
    Boolean download(String path);
}
