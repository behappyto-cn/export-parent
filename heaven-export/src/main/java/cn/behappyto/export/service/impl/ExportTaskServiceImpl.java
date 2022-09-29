package cn.behappyto.export.service.impl;

import cn.behappyto.common.core.domain.PageQuery;
import cn.behappyto.common.core.page.TableDataInfo;
import cn.behappyto.common.exception.ServiceException;
import cn.behappyto.common.utils.StringUtils;
import cn.behappyto.export.domain.ExportTask;
import cn.behappyto.export.domain.bo.ExportTaskBo;
import cn.behappyto.export.domain.vo.ExportTaskVo;
import cn.behappyto.export.mapper.ExportTaskMapper;
import cn.behappyto.export.service.IExportTaskService;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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

    private final HttpServletRequest request;

    private final HttpServletResponse response;

    /**
     * 查询导出任务
     */
    @Override
    public ExportTaskVo queryById(Long id) {
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
    private void validEntityBeforeSave(ExportTask entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除导出任务
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    @SneakyThrows
    public void download(Long id) {
        ExportTask exportTask = baseMapper.selectById(id);
        if (exportTask == null) {
            throw new ServiceException("下载异常");
        }
        String path = exportTask.getUrl();
        File file = new File(path);
        if (file == null || !file.exists()) {
            throw new ServiceException("下载异常");
        }
        // 读到流中 文件的存放路径
        InputStream inputStream = new FileInputStream(path);
        response.reset();
        response.setContentType("application/octet-stream");
        String filename = new File(path).getName();
        response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        byte[] b = new byte[1024];
        int len;
        //从输入流中读取一定数量的字节，并将其存储在缓冲区字节数组中，读到末尾返回-1
        while ((len = inputStream.read(b)) > 0) {
            outputStream.write(b, 0, len);
        }
        inputStream.close();
    }
}
