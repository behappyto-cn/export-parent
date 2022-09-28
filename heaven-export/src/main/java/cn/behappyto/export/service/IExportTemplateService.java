package cn.behappyto.export.service;

import cn.behappyto.export.domain.ExportTemplate;
import cn.behappyto.export.domain.vo.ExportTemplateVo;
import cn.behappyto.export.domain.bo.ExportTemplateBo;
import cn.behappyto.common.core.page.TableDataInfo;
import cn.behappyto.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 导出模板Service接口
 *
 * @author behappyto.cn
 * @date 2022-09-23
 */
public interface IExportTemplateService {

    /**
     * 查询导出模板
     */
    ExportTemplateVo queryById(Long id);

    /**
     * 查询导出模板列表
     */
    TableDataInfo<ExportTemplateVo> queryPageList(ExportTemplateBo bo, PageQuery pageQuery);

    /**
     * 查询导出模板列表
     */
    List<ExportTemplateVo> queryList(ExportTemplateBo bo);

    /**
     * 新增导出模板
     */
    Boolean insertByBo(ExportTemplateBo bo);

    /**
     * 修改导出模板
     */
    Boolean updateByBo(ExportTemplateBo bo);

    /**
     * 校验并批量删除导出模板信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
