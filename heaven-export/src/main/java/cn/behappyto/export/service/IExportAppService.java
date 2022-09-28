package cn.behappyto.export.service;

import cn.behappyto.common.core.domain.PageQuery;
import cn.behappyto.common.core.page.TableDataInfo;
import cn.behappyto.export.domain.bo.ExportAppBo;
import cn.behappyto.export.domain.vo.ExportAppVo;

import java.util.Collection;
import java.util.List;

/**
 * 应用信息Service接口
 *
 * @author behappyto.cn
 * @date 2022-09-23
 */
public interface IExportAppService {

    /**
     * 查询应用信息
     */
    ExportAppVo queryById(Long id);

    /**
     * 查询应用信息列表
     */
    TableDataInfo<ExportAppVo> queryPageList(ExportAppBo bo, PageQuery pageQuery);

    /**
     * 查询应用信息列表
     */
    List<ExportAppVo> queryList(ExportAppBo bo);

    /**
     * 新增应用信息
     */
    Boolean insertByBo(ExportAppBo bo);

    /**
     * 修改应用信息
     */
    Boolean updateByBo(ExportAppBo bo);

    /**
     * 校验并批量删除应用信息信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
