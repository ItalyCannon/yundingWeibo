package com.yundingweibo.web.servlet;

import com.yundingweibo.domain.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "WeiboPhotoServlet", urlPatterns = "/WeiboPhotoServlet")
public class WeiboPhotoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        //1.0判断是否是mulitpart请求
        if (!ServletFileUpload.isMultipartContent(request)) {
            throw new RuntimeException("当前请求不支持文件上传");
        } else {
            try {
                //为基于磁盘item工厂
                DiskFileItemFactory factory = new DiskFileItemFactory();

                //设置使用临时文件的边界值，大于该值则使用临时文件，否则直接写入内存
                //单位 字节 自处为1M
                factory.setSizeThreshold(1024 * 1024 * 1);

                //设置临时文件
                String tempPath = this.getServletContext().getRealPath("/temp");
                File temp = new File(tempPath);
                factory.setRepository(temp);

                //创建一个新的文件上传处理程序
                ServletFileUpload upload = new ServletFileUpload(factory);
                //解析请求,获取items
                List<FileItem> items = upload.parseRequest(request);

                StringBuilder photos = new StringBuilder();
                //遍历items
                for (FileItem item : items) {
                    //处理常规表单字段
                    if (item.isFormField()) {
                        //若itme为普通项目表
                        //获取表单项名称g
                        String fieldName = item.getFieldName();
                        //获取表单项的值
                        String fileValue = item.getString("UTF-8");
                        System.out.println(fieldName + " = " + fileValue);
                    } else {
                        User sessionUser = (User) request.getSession().getAttribute("sessionUser");
                        String realPath;

                        realPath = this.getServletContext().getRealPath("/weiboImg/");

                        String path = realPath + sessionUser.getUserId() + System.currentTimeMillis();
                        System.out.println("文件保存的路径：" + path);

                        //指定文件保存的名字
                        //后缀名
                        String suffix = item.getName().split("\\.")[1];
                        String fileName = UUID.randomUUID().toString() + "." + suffix;
                        System.out.println("文件名 ：" + fileName);
                        //拼接文件保存路径
                        String fileSavePath = path + "/" + fileName;
                        System.out.println("保存文件完整路径：" + fileSavePath);

                        //创建目标文件，将来用于保存文件
                        File descFile = new File(fileSavePath);
                        if (!descFile.exists()) {
                            boolean mkdirs = descFile.getParentFile().mkdirs();
                            if (mkdirs) {
                                System.out.println("创建成功");
                            } else {
                                System.out.println("创建失败");
                            }
                        }

                        //保存
                        item.write(new File(fileSavePath));

                        int root = fileSavePath.indexOf("ROOT");
                        String substring = path.substring(root + 4);
                        String url = "http://47.102.151.60:8080";
                        String urlPath = url + substring + "/" + fileName;

                        //把urlPath写到微博的photo字段里
                        System.out.println(urlPath);
                        urlPath += ",";
                        photos.append(urlPath);
                    }
                }
                response.getWriter().write(photos.toString());
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("上传失败");
            }

        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
