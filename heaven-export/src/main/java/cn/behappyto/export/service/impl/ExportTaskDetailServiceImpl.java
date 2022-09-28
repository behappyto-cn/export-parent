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
import cn.behappyto.export.domain.bo.ExportTaskDetailBo;
import cn.behappyto.export.domain.vo.ExportTaskDetailVo;
import cn.behappyto.export.domain.ExportTaskDetail;
import cn.behappyto.export.mapper.ExportTaskDetailMapper;
import cn.behappyto.export.service.IExportTaskDetailService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 任务明细Service业务层处理
 *
 * @author behappyto.cn
 * @date 2022-09-23
 */
@RequiredArgsConstructor
@Service
public class ExportTaskDetailServiceImpl implements IExportTaskDetailService {

    private final ExportTaskDetailMapper baseMapper;

    /**
     * 查询任务明细
     */
    @Override
    public ExportTaskDetailVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询任务明细列表
     */
    @Override
    public TableDataInfo<ExportTaskDetailVo> queryPageList(ExportTaskDetailBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<ExportTaskDetail> lqw = buildQueryWrapper(bo);
        Page<ExportTaskDetailVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询任务明细列表
     */
    @Override
    public List<ExportTaskDetailVo> queryList(ExportTaskDetailBo bo) {
        LambdaQueryWrapper<ExportTaskDetail> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ExportTaskDetail> buildQueryWrapper(ExportTaskDetailBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<ExportTaskDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getTaskId() != null, ExportTaskDetail::getTaskId, bo.getTaskId());
        lqw.eq(StringUtils.isNotBlank(bo.getMethod()), ExportTaskDetail::getMethod, bo.getMethod());
        lqw.eq(bo.getDeleted() != null, ExportTaskDetail::getDeleted, bo.getDeleted());
        return lqw;
    }

    /**
     * 新增任务明细
     */
    @Override
    public Boolean insertByBo(ExportTaskDetailBo bo) {
        ExportTaskDetail add = BeanUtil.toBean(bo, ExportTaskDetail.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改任务明细
     */
    @Override
    public Boolean updateByBo(ExportTaskDetailBo bo) {
        ExportTaskDetail update = BeanUtil.toBean(bo, ExportTaskDetail.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(ExportTaskDetail entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除任务明细
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
