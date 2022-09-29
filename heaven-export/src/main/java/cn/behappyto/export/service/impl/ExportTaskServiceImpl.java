package cn.behappyto.export.service.impl;

import cn.behappyto.common.utils.StringUtils;
import cn.hutool.core.bean.BeanUtil;
import cn.behappyto.common.core.page.TableDataInfo;
import cn.behappyto.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import cn.behappyto.export.domain.bo.ExportTaskBo;
import cn.behappyto.export.domain.vo.ExportTaskVo;
import cn.behappyto.export.domain.ExportTask;
import cn.behappyto.export.mapper.ExportTaskMapper;
import cn.behappyto.export.service.IExportTaskService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 导出任务Service业务层处理
 *
 * @author behappyto.cn
 * @date 2022-09-23
 */
@RequiredArgsConstructor
@Service
public class ExportTaskServiceImpl implements IExportTaskService {

    private final ExportTaskMapper baseMapper;

    /**
     * 查询导出任务
     */
    @Override
    public ExportTaskVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询导出任务列表
     */
    @Override
    public TableDataInfo<ExportTaskVo> queryPageList(ExportTaskBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<ExportTask> lqw = buildQueryWrapper(bo);
        Page<ExportTaskVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询导出任务列表
     */
    @Override
    public List<ExportTaskVo> queryList(ExportTaskBo bo) {
        LambdaQueryWrapper<ExportTask> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ExportTask> buildQueryWrapper(ExportTaskBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<ExportTask> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getName()), ExportTask::getName, bo.getName());
        lqw.eq(StringUtils.isNotBlank(bo.getAppId()), ExportTask::getAppId, bo.getAppId());
        lqw.eq(bo.getStatus() != null, ExportTask::getStatus, bo.getStatus());
        lqw.eq(bo.getDeleted() != null, ExportTask::getDeleted, bo.getDeleted());
        return lqw;
    }

    /**
     * 新增导出任务
     */
    @Override
    public Boolean insertByBo(ExportTaskBo bo) {
        ExportTask add = BeanUtil.toBean(bo, ExportTask.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改导出任务
     */
    @Override
    public Boolean updateByBo(ExportTaskBo bo) {
        ExportTask update = BeanUtil.toBean(bo, ExportTask.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(ExportTask entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除导出任务
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public Boolean download(String path) {
        return null;
    }
}
