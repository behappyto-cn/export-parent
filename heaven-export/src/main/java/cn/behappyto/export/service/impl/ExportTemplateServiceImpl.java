package cn.behappyto.export.service.impl;

import cn.behappyto.common.core.domain.PageQuery;
import cn.behappyto.common.core.page.TableDataInfo;
import cn.behappyto.common.utils.StringUtils;
import cn.behappyto.export.domain.ExportTemplate;
import cn.behappyto.export.domain.bo.ExportTemplateBo;
import cn.behappyto.export.domain.vo.ExportTemplateVo;
import cn.behappyto.export.mapper.ExportTemplateMapper;
import cn.behappyto.export.service.IExportTemplateService;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 导出模板Service业务层处理
 *
 * @author behappyto.cn
 * @date 2022-09-23
 */
@RequiredArgsConstructor
@Service
public class ExportTemplateServiceImpl implements IExportTemplateService {

    private final ExportTemplateMapper baseMapper;

    /**
     * 查询导出模板
     */
    @Override
    public ExportTemplateVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询导出模板列表
     */
    @Override
    public TableDataInfo<ExportTemplateVo> queryPageList(ExportTemplateBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<ExportTemplate> lqw = buildQueryWrapper(bo);
        Page<ExportTemplateVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询导出模板列表
     */
    @Override
    public List<ExportTemplateVo> queryList(ExportTemplateBo bo) {
        LambdaQueryWrapper<ExportTemplate> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ExportTemplate> buildQueryWrapper(ExportTemplateBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<ExportTemplate> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getAppId()), ExportTemplate::getAppId, bo.getAppId());
        lqw.like(StringUtils.isNotBlank(bo.getName()), ExportTemplate::getName, bo.getName());
        lqw.eq(bo.getDeleted() != null, ExportTemplate::getDeleted, bo.getDeleted());
        return lqw;
    }

    /**
     * 新增导出模板
     */
    @Override
    public Boolean insertByBo(ExportTemplateBo bo) {
        ExportTemplate add = BeanUtil.toBean(bo, ExportTemplate.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改导出模板
     */
    @Override
    public Boolean updateByBo(ExportTemplateBo bo) {
        ExportTemplate update = BeanUtil.toBean(bo, ExportTemplate.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(ExportTemplate entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除导出模板
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
