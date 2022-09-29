package cn.behappyto.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 通用下载工具类
 *
 * @author wangmingcong
 */
@Slf4j
public class DownloadUtil {

    private static MimetypesFileTypeMap mimetypesFileTypeMap;

    /**
     * 下载http文件流
     *
     * @param urlStr   下载的文件地址
     * @param request  请求
     * @param response 响应
     * @param fileName 保存文件名
     */
    public static void downloadHttpFile(String urlStr, HttpServletRequest request, HttpServletResponse response, String fileName) {
        ServletOutputStream out = null;
        InputStream inputStream = null;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //得到输入流
            inputStream = conn.getInputStream();
            //获取自己数组
            byte[] getData = DownloadUtil.inputStreamToByte(inputStream);
            // 下载
            out = response.getOutputStream();
            long contentLength = getData.length;
            DownloadUtil.setResponse(fileName, contentLength, request, response);
            out.write(getData);
            out.flush();
        } catch (Exception e) {
            throw new RuntimeException("下载失败!");
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                throw new RuntimeException("下载失败!");
            }
        }
    }

    /**
     * 下载文件流
     *
     * @param file     文件地址
     * @param request  请求
     * @param response 响应
     * @param fileName 文件名
     */
    public static void downloadFile(File file, HttpServletRequest request, HttpServletResponse response, String fileName) {
        if (file != null && file.exists() && file.length() > 0L) {
            try {
                RandomAccessFile randomFile = new RandomAccessFile(file, "r");
                Throwable var5 = null;

                try {
                    ServletOutputStream out = response.getOutputStream();
                    Throwable var7 = null;

                    try {
                        long contentLength = randomFile.length();
                        String range = request.getHeader("Range");
                        long start = 0L;
                        long end = 0L;
                        if (range != null && range.startsWith("bytes=")) {
                            String[] values = range.split("=")[1].split("-");
                            start = Long.parseLong(values[0]);
                            if (values.length > 1) {
                                end = Long.parseLong(values[1]);
                            }
                        }

                        int requestSize;
                        if (end != 0L && end > start) {
                            requestSize = Long.valueOf(end - start + 1L).intValue();
                        } else {
                            requestSize = 2147483647;
                        }

                        DownloadUtil.setResponse(fileName, contentLength, request, response);

                        randomFile.seek(start);

                        byte[] buffer;
                        for (int needSize = requestSize; needSize > 0; needSize -= buffer.length) {
                            buffer = new byte[1024];
                            int len = randomFile.read(buffer);
                            if (needSize < buffer.length) {
                                out.write(buffer, 0, needSize);
                            } else {
                                out.write(buffer, 0, len);
                                if (len < buffer.length) {
                                    break;
                                }
                            }
                        }

                        out.flush();
                    } catch (Throwable var47) {
                        throw var47;
                    } finally {
                        if (out != null) {
                            if (var7 != null) {
                                try {
                                    out.close();
                                } catch (Throwable var46) {
                                    var7.addSuppressed(var46);
                                }
                            } else {
                                out.close();
                            }
                        }

                    }
                } catch (Throwable var49) {
                    throw var49;
                } finally {
                    if (randomFile != null) {
                        if (var5 != null) {
                            try {
                                randomFile.close();
                            } catch (Throwable var45) {
                                var5.addSuppressed(var45);
                            }
                        } else {
                            randomFile.close();
                        }
                    }

                }
            } catch (IOException var51) {
                log.debug(var51.getMessage(), var51);
                throw new RuntimeException(var51.getMessage());
            }
        } else {
            throw new RuntimeException("文件为空或不存在！");
        }
    }

    /**
     * 将磁盘的多个文件打包成压缩包并输出流下载
     *
     * @param pathList 路径
     * @param response 响应
     */
    public static void zipDirFileToFile(List<Map<String, String>> pathList, HttpServletResponse response) {
        try {
            // 设置response参数并且获取ServletOutputStream
            ZipArchiveOutputStream outputStream = getServletOutputStream(response);

            for (Map<String, String> map : pathList) {
                String fileName = map.get("name");
                File file = new File(map.get("path"));
                InputStream inputStream = new FileInputStream(file);
                setByteArrayOutputStream(fileName, inputStream, outputStream);
            }
            outputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将网络url资源文件的多个文件打包成压缩包并输出流下载
     *
     * @param pathList 地址
     * @param response 响应
     */
    public static void zipUrlToFile(List<Map<String, String>> pathList, HttpServletResponse response) {
        try {
            // 设置response参数并且获取ServletOutputStream
            ZipArchiveOutputStream outputStream = getServletOutputStream(response);

            for (Map<String, String> map : pathList) {
                String fileName = map.get("name");
                InputStream inputStream = getInputStreamFromUrl(map.get("path"));
                setByteArrayOutputStream(fileName, inputStream, outputStream);
            }
            outputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * File、FileInputStream 转换为byte数组
     *
     * @param inputStream 文件转 byte[]
     * @return 返回 byte[]
     */
    private static byte[] inputStreamToByte(InputStream inputStream) {
        try {
            byte[] buffer = new byte[1024];
            int len = 0;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            while ((len = inputStream.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            bos.close();
            return bos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("文件转换失败!");
        }
    }

    /**
     * 下载配置
     *
     * @param fileName      下载文件名
     * @param contentLength 内容长度
     * @param request       请求
     * @param response      响应
     */
    private static void setResponse(String fileName, long contentLength, HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType(DownloadUtil.getContentType("0.jpg"));
            boolean isPreview = "preview".equalsIgnoreCase(request.getParameter("source"));
            response.addHeader("Content-Disposition", MessageFormat.format("{0}filename={1}", !isPreview ? "attachment; " : "", URLEncoder.encode(fileName, "UTF-8")));
            response.setHeader("Accept-Ranges", "bytes");

            String range = request.getHeader("Range");
            if (range == null) {
                response.setHeader("Content-Length", String.valueOf(contentLength));
            } else {
                response.setStatus(206);
                long requestStart = 0L;
                long requestEnd = 0L;
                String[] ranges = range.split("=");
                if (ranges.length > 1) {
                    String[] rangeDatas = ranges[1].split("-");
                    requestStart = Long.parseLong(rangeDatas[0]);
                    if (rangeDatas.length > 1) {
                        requestEnd = Long.parseLong(rangeDatas[1]);
                    }
                }

                long length;
                if (requestEnd > 0L) {
                    length = requestEnd - requestStart + 1L;
                    response.setHeader("Content-Length", String.valueOf(length));
                    response.setHeader("Content-Range", "bytes " + requestStart + "-" + requestEnd + "/" + contentLength);
                } else {
                    length = contentLength - requestStart;
                    response.setHeader("Content-Length", String.valueOf(length));
                    response.setHeader("Content-Range", "bytes " + requestStart + "-" + (contentLength - 1L) + "/" + contentLength);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("response响应失败!");
        }
    }

    /**
     * 获取 请求 content-type
     *
     * @param fileName 文件名
     * @return 返回
     */
    private static String getContentType(String fileName) {
        if (mimetypesFileTypeMap == null) {
            mimetypesFileTypeMap = new MimetypesFileTypeMap();
        }

        return mimetypesFileTypeMap.getContentType(fileName);
    }

    /**
     * 压缩下载流
     *
     * @param response 响应
     * @return 返回 压缩下载流
     * @throws Exception 异常
     */
    private static ZipArchiveOutputStream getServletOutputStream(HttpServletResponse response) throws Exception {
        String outputFileName = new SimpleDateFormat("yyyyMMddHHmmssffff").format(new Date()) + ".zip";
        response.reset();
        response.setHeader("Content-Type", "application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(outputFileName, "UTF-8"));
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        //允许所有来源访同
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Method", "POST,GET");
        ServletOutputStream out = response.getOutputStream();

        ZipArchiveOutputStream outputStream = new ZipArchiveOutputStream(out);
        outputStream.setUseZip64(Zip64Mode.AsNeeded);

        return outputStream;
    }

    /**
     * 流转换
     *
     * @param fileName     文件名
     * @param inputStream  输入流
     * @param outputStream 输出流
     * @throws Exception 异常
     */
    private static void setByteArrayOutputStream(String fileName, InputStream inputStream, ZipArchiveOutputStream outputStream) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, len);
        }
        byteArrayOutputStream.flush();
        byte[] bytes = byteArrayOutputStream.toByteArray();

        //设置文件名
        ArchiveEntry entry = new ZipArchiveEntry(fileName);
        outputStream.putArchiveEntry(entry);
        outputStream.write(bytes);
        outputStream.closeArchiveEntry();
        byteArrayOutputStream.close();
    }

    /**
     * 通过网络地址获取文件InputStream
     *
     * @param path 地址
     * @return 返回 输入流
     */
    private static InputStream getInputStreamFromUrl(String path) {
        URL url = null;
        InputStream is = null;
        try {
            url = new URL(path);
        } catch (MalformedURLException e) {
            log.error("下载失败：", e);
        }
        assert url != null;
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.connect();
            is = conn.getInputStream();
        } catch (IOException e) {
            log.error("下载失败：", e);
        }
        return is;
    }
}
