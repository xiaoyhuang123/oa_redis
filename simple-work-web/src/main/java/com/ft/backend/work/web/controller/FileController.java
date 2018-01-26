package com.ft.backend.work.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ft.backend.work.baseVo.ListItemVo;
import com.ft.backend.work.client.common.ReturnResult;
import com.ft.backend.work.client.service.FileService;
import com.ft.backend.work.client.vo.FileVo;
import com.ft.backend.work.common.CommonConstant;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Wonderful on 2017/11/15.
 */
@Controller
@RequestMapping("/file")
public class FileController {
    private Logger logger = LogManager.getLogger(FileController.class);

    @Resource
    private FileService fileService;

    @RequestMapping(value = "/delete",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public JSONObject delete(HttpServletRequest request,HttpServletResponse response){
        ReturnResult<Boolean> result=null;

        String[] idList=request.getParameterValues("ids");
        List<Long> ids=new ArrayList<>();
        for(String item:idList){
            Long id=Long.parseLong(item);
            ids.add(id);
            fileService.deleteById(id);
        }
        JSONObject outData = new JSONObject();
        outData.put("successed","true");

        return outData;
    }

    @RequestMapping(value = "/download",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public ResponseEntity<byte[]> download(HttpServletRequest request, HttpServletResponse response) throws Exception{

        ResponseEntity<byte[]> responseEntity = ResponseEntity.ok(null);

        String[] idList=request.getParameterValues("ids");
        List<Long> ids=new ArrayList<>();
        for(String item:idList){
            Long id=Long.parseLong(item);
            ids.add(id);

            FileVo fileVo = fileService.selectById(id).getResult();

            String fileName = fileVo.getFileName();
            if (fileName != null) {
                String realPath = request.getSession().getServletContext().getRealPath(fileVo.getFilePath());
                File file = new File(realPath, fileName);
                if (file.exists())
                {

                    HttpHeaders headers = new HttpHeaders();
                    //下载显示的文件名，解决中文名称乱码问题
                    String downloadFielName = new String(fileName.getBytes("UTF-8"),"iso-8859-1");
                    //通知浏览器以attachment（下载方式）打开图片
                    headers.setContentDispositionFormData("attachment", downloadFielName);
                    //application/octet-stream ： 二进制流数据（最常见的文件下载）。
                    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                    responseEntity = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
                            headers, HttpStatus.CREATED);
                }
            }
        }

        return responseEntity;
    }

    @RequestMapping(value = "/downloadTaskFile",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public void downloadTaskFile(HttpServletRequest request, HttpServletResponse response) throws Exception{

        List<File> files = new ArrayList<File>();

        String[] idList=request.getParameterValues("ids");
        for(String item:idList){

            Long id=Long.parseLong(item);

            Map<String,Object> params = new HashedMap();
            params.put("taskId",id);
            params.put("fileType", CommonConstant.FILE_TYPE_TASK_FILE);
            ReturnResult<List<FileVo>> fs = fileService.selectByParameters(params);
            if(fs.isSuccessed() && fs.getResult()!=null){
                List<FileVo> fileVos = fs.getResult();

                for(FileVo fileVo: fileVos){
                    String fileName = fileVo.getFileName();
                    if (fileName != null) {
                        String realPath = request.getSession().getServletContext().getRealPath(fileVo.getFilePath());
                        File file = new File(realPath, fileName);
                        if (file.exists())
                        {
                            files.add(file);
                        }
                    }
                }
            }
        }


        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        String fileName = "参考资料"+uuid+".zip";
        fileName=java.net.URLEncoder.encode(fileName, "UTF-8");//防止ie下文件名乱码
        //在服务器端创建打包下载的临时文件
        String outFilePath=request.getSession().getServletContext().getRealPath("fileSet/");
        outFilePath +=  fileName;
        File file = new File(outFilePath);
        // 文件是否存在
        if(!file.exists()){
            file.createNewFile();
        }
        //文件输出流
        FileOutputStream outStream = new FileOutputStream(file);
        //压缩流
        ZipOutputStream toClient = new ZipOutputStream(outStream);
        //toClient.setEncoding("utf-8");
        zipFile(files, toClient);
        toClient.close();
        outStream.close();
        this.downloadFile(file, response,true);

    }


    /**********************************************************************/

    @RequestMapping(value = "/selectByParams")
    @ResponseBody
    public JSONObject selectByParams(HttpServletRequest request){

        JSONObject jsonFromBean=new JSONObject();

        String currentPage=request.getParameter("pageIndex");
        String limit = request.getParameter("limit");

        int pageIndex=1;
        int pageSize=10;

        if(StringUtils.isNotBlank(currentPage) && StringUtils.isNotBlank(limit)){
            pageIndex= Integer.parseInt(currentPage)+1;
            pageSize = Integer.parseInt(limit);
        }

        try {

            Map<String,Object> params=new HashMap<>();
            List<FileVo> result=new ArrayList<>();

            String taskIdstr = request.getParameter("taskId");

            if(!StringUtils.isBlank(taskIdstr)){
                params.put("taskId",Long.parseLong(taskIdstr));
            }

            String fileTypeStr = request.getParameter("fileType");

            if(!StringUtils.isBlank(fileTypeStr)){
                params.put("fileType",Integer.parseInt(fileTypeStr));
            }

            params.put("pageIndex",pageIndex);
            params.put("offset",pageSize*(pageIndex-1));
            params.put("pageSize",pageSize);

            Integer totals=fileService.countByParameters(params).getResult();
            logger.info("listFile - info,totals={}", totals);
            if(totals>0) {

                ReturnResult<List<FileVo>> res = fileService.selectByParameters(params);
                List<FileVo> records = new ArrayList<>();
                if (res != null) {
                    records = res.getResult();
                }
                result = res.getResult();
                logger.info("listFile - info,result={}", result);
            }

            jsonFromBean=new JSONObject();
            jsonFromBean.put("rows",result);
            jsonFromBean.put("results",totals);
        }catch (Exception e){
            logger.error("selectByParams -error:{}", e);
        }
        return jsonFromBean;
    }


    @RequestMapping(value = "/detail",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public ReturnResult<FileVo> FileDetail(HttpServletRequest request,HttpServletResponse response){
        ReturnResult<FileVo> result=null;

        String item=request.getParameter("id");

        Long id=Long.parseLong(item);

        result = fileService.selectById(id);
        return result;
    }


    @RequestMapping(value = "/select")
    @ResponseBody
    public JSONArray select(HttpServletRequest request){

        JSONArray jsonFromBean=null;

        try {
            Map<String,Object> params=new HashedMap();

            ReturnResult<List<FileVo>> resp=fileService.selectByParameters(params);

            List<FileVo> FileVoList =new ArrayList<>();
            if(resp!=null && resp.getResult()!=null){
                FileVoList=resp.getResult();
            }

            List<ListItemVo> itemVos =new ArrayList<>();
            for(FileVo workshopVo: FileVoList){
                ListItemVo itemVo=new ListItemVo();
                itemVo.setText(workshopVo.getFileName());
                itemVo.setValue(workshopVo.getId().toString());
                itemVos.add(itemVo);
            }

            String jsonstr= JSON.toJSONString(itemVos);
            logger.info("select -info, tess:{}", jsonstr);
            jsonFromBean = (JSONArray) JSONArray.parseArray(jsonstr);
        }catch (Exception e){
            logger.error("select - error:{}", e);
        }
        return jsonFromBean;
    }


    /***********************************************************************/


    /**
     * 压缩文件列表中的文件
     *
     * @param files
     * @param outputStream
     * @throws IOException
     */
    public  void zipFile(List<File> files, ZipOutputStream outputStream) throws IOException, ServletException {
        List<String>fileNames=new ArrayList<String>();
        try {
            int size = files.size();
// 压缩列表中的文件
            for (int i = 0; i < size; i++) {
                File file = (File) files.get(i);
                //outputStream.setEncoding("GBK"); //设置压缩文件内的字符编码，不然会变成乱码
                String fileName = file.getName();//替换下载文件的文件名
//判断压缩文件内文件名是否有重复
                if(!fileNames.contains(fileName)){
                    fileNames.add(fileName);
                }else{
                    /*fileName=courseResources.getResourceName()+PingXXUtil.getFixLenthString(6)+"."+fileName.getFormat();*/
                }
                zipFile(file, outputStream,fileName);
            }
        } catch (IOException e) {
            throw e;
        }
    }


    /**
     * 将文件写入到zip文件中
     *
     * @param inputFile
     * @param outputstream
     * @throws Exception
     */
    public  void zipFile(File inputFile, ZipOutputStream outputstream,String fileName) throws IOException, ServletException {
        try {
            if (inputFile.exists()) {
                if (inputFile.isFile()) {
                    FileInputStream inStream = new FileInputStream(inputFile);
                    BufferedInputStream bInStream = new BufferedInputStream(inStream);
                    ZipEntry entry = new ZipEntry(fileName);
                    outputstream.putNextEntry(entry);
                    final int MAX_BYTE = 10 * 1024 * 1024; // 最大的流为10M
                    long streamTotal = 0; // 接受流的容量
                    int streamNum = 0; // 流需要分开的数量
                    int leaveByte = 0; // 文件剩下的字符数
                    byte[] inOutbyte; // byte数组接受文件的数据
                    streamTotal = bInStream.available(); // 通过available方法取得流的最大字符数
                    streamNum = (int) Math.floor(streamTotal / MAX_BYTE); // 取得流文件需要分开的数量
                    leaveByte = (int) streamTotal % MAX_BYTE; // 分开文件之后,剩余的数量
                    if (streamNum > 0) {
                        for (int j = 0; j < streamNum; ++j) {
                            inOutbyte = new byte[MAX_BYTE];
// 读入流,保存在byte数组
                            bInStream.read(inOutbyte, 0, MAX_BYTE);
                            outputstream.write(inOutbyte, 0, MAX_BYTE); // 写出流
                        }
                    }
// 写出剩下的流数据
                    inOutbyte = new byte[leaveByte];
                    bInStream.read(inOutbyte, 0, leaveByte);
                    outputstream.write(inOutbyte);
                    outputstream.closeEntry(); // Closes the current ZIP entry
// and positions the stream for
// writing the next entry
                    bInStream.close(); // 关闭
                    inStream.close();
                }
            } else {
                throw new ServletException("文件不存在！");
            }
        } catch (IOException e) {
            throw e;
        }
    }


    /**
     * 下载文件
     *
     * @param file
     * @param response
     */
    public void downloadFile(File file, HttpServletResponse response, boolean isDelete) {
        try {
// 以流的形式下载文件。
            BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file.getPath()));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
// 清空response
            response.reset();
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition","attachment;filename=" + new String(file.getName().getBytes("UTF-8"), "ISO-8859-1"));
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
            if (isDelete) {
                file.delete(); // 是否将生成的服务器端文件删除
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
