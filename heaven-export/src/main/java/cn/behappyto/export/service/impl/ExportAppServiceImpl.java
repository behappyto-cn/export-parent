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
import cn.behappyto.export.domain.bo.ExportAppBo;
import cn.behappyto.export.domain.vo.ExportAppVo;
import cn.behappyto.export.domain.ExportApp;
import cn.behappyto.export.mapper.ExportAppMapper;
import cn.behappyto.export.service.IExportAppService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 应用信息Service业务层处理
 *
 * @author behappyto.cn
 * @date 2022-09-23
 */
@RequiredArgsConstructor
@Service
public class ExportAppServiceImpl implements IExportAppService {

    private final ExportAppMapper baseMapper;

    /**
     * 查询应用信息
     */
    @Override
    public ExportAppVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询应用信息列表
     */
    @Override
    public TableDataInfo<ExportAppVo> queryPageList(ExportAppBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<ExportApp> lqw = buildQueryWrapper(bo);
        Page<ExportAppVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询应用信息列表
     */
    @Override
    public List<ExportAppVo> queryList(ExportAppBo bo) {
        LambdaQueryWrapper<ExportApp> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ExportApp> buildQueryWrapper(ExportAppBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<ExportApp> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getAppId()), ExportApp::getAppId, bo.getAppId());
        lqw.eq(bo.getEnabled() != null, ExportApp::getEnabled, bo.getEnabled());
        lqw.eq(bo.getDeleted() != null, ExportApp::getDeleted, bo.getDeleted());
        return lqw;
    }

    /**
     * 新增应用信息
     */
    @Override
    public Boolean insertByBo(ExportAppBo bo) {
        ExportApp add = BeanUtil.toBean(bo, ExportApp.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改应用信息
     */
    @Override
    public Boolean updateByBo(ExportAppBo bo) {
        ExportApp update = BeanUtil.toBean(bo, ExportApp.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(ExportApp entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除应用信息
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
