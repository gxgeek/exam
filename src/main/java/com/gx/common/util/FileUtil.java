package com.gx.common.util;

import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

/**
 * Created by zq on 2016/11/30.
 */
public class FileUtil {

    public static void uploadFile(InputStream in, String out_path) throws Exception{
        creatPath(out_path);
        ReadableByteChannel readableChannel = Channels.newChannel(in);
        //创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //读入缓冲区
        readableChannel.read(buffer);
        FileOutputStream fout = new FileOutputStream(out_path);
        FileChannel fcout = fout.getChannel();
        while (readableChannel.read(buffer) != -1) {
            buffer.flip();// 读模式切换成写模式
            fcout.write(buffer);
            buffer.clear();  //从写切换到读
        }

        fout.close();
        fcout.close();
        readableChannel.close();
    }

    /**
     * 把图片压缩成指定 长 宽
     * @param srcPath
     * @param tarPath
     * @param w
     * @param h
     * @param keep   true：保存原图比例  false：否
     * @throws IOException
     */
    public static void smallerpic(String srcPath,String tarPath,int w,int h,boolean keep) throws IOException{
        creatPath(tarPath);
        Thumbnails.of(srcPath).size(w, h).keepAspectRatio(keep).toFile(tarPath);
    }

    /**
     * 比例不变压缩图片质量
     */
    ///输入输出路径为图片所在文件夹，不必包括图片全名
        public static void imagCompress(String srcPath,String tarPath) throws IOException{
            String[] str = {"jpg","gif","png","jpeg"};
                File dir = new File(srcPath);
                File[] files = dir.listFiles();
                for (int i = 0; i < files.length; i++) {
                    //过滤非图片
                    String fileType = files[i].getName().substring(files[i].getName().lastIndexOf('.') + 1, files[i].getName().length());
                    for (int t = 0; t < str.length; t++) {
                        if ("jpeg".equals(fileType)) {
                            System.out.println(files[i].getName());
                            String imgName = files[i].getName();
                            Thumbnails.of(files[i].getPath()).scale(1).outputQuality(0.4).toFile(tarPath + imgName);
                        }
                        if (str[t].equals(fileType.toLowerCase()) && !(fileType.toLowerCase() .equals("jpeg") )) {
                            System.out.println(files[i].getName());
                            String imgName = files[i].getName();
                            compressPic(srcPath + imgName, tarPath + imgName);
                        }

                    }
                }
            }


        public static boolean compressPic(String srcFilePath,String descFilePath)throws IOException {
            File file=null;
            BufferedImage src=null;
            FileOutputStream out=null;
            ImageWriter imgWrier;
            ImageWriteParam imgWriteParams;

            // 指定写图片的方式为 jpg
            imgWrier= ImageIO.getImageWritersByFormatName("jpg").next();
            imgWriteParams=new javax.imageio.plugins.jpeg.JPEGImageWriteParam(
                    null);
            // 要使用压缩，必须指定压缩方式为MODE_EXPLICIT
            imgWriteParams.setCompressionMode(imgWriteParams.MODE_EXPLICIT);
            // 这里指定压缩的程度，参数qality是取值0~1范围内，
            imgWriteParams.setCompressionQuality((float)0.4);
            imgWriteParams.setProgressiveMode(imgWriteParams.MODE_DISABLED);

            try{
                if(isBlank(srcFilePath)){
                    return false;
                }else{
                    file=new File(srcFilePath);System.out.println(file.length());
                    src=ImageIO.read(file);
                    out=new FileOutputStream(descFilePath);
                    imgWrier.reset();
                    // 必须先指定 out值，才能调用write方法, ImageOutputStream可以通过任何
                    // OutputStream构造
                    imgWrier.setOutput(ImageIO.createImageOutputStream(out));
                    // 调用write方法，就可以向输入流写图片
                    imgWrier.write(null,new IIOImage(src,null,null), imgWriteParams);
                    out.flush();
                    out.close();
                }
            }catch(Exception e){
                e.printStackTrace();
                return false;
            }
            return true;
        }
        public static boolean isBlank(String string){
            if(string==null||string.length()==0||string.trim().equals("")){
                return true;
            }
            return false;
        }


    /**
     * inputStream转成BufferedImage
     * @param in
     * @return
     * @throws IOException
     */
    public static BufferedImage in2Image(InputStream in) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(in);
        return bufferedImage;
    }



    /**
     *  创建保存文件路劲
     * @param outPath
     * @throws IOException
     */
    public static void creatPath(String outPath) throws IOException {
        String path = outPath.substring(0,outPath.lastIndexOf("/"));
        File f = new File(path);
        if(!f.exists()){
            f.mkdirs();
        }
    }
}
