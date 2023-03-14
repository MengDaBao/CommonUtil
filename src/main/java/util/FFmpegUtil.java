package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class FFmpegUtil {

    public final static String path = "D:\\Program Files\\ffmpeg\\bin\\ffmpeg.exe";

    /**
     * 合并视频文件
     * @param videoPathList
     */
    public static void mergeVideo(List<String> videoPathList) {
        if (videoPathList.size() > 1){
            String ffmpegPath = path;// 此处是配置地址，可自行写死如“D:\\ffmpeg\\ffmpeg.exe”
            String txtPath = "";
            try {
                String firstVideo = videoPathList.get(0);
                int index = firstVideo.lastIndexOf(".");
                String videoName = firstVideo.substring(0, index);
                String newMergePath = videoName + "new" + firstVideo.substring(index);
                txtPath = videoName + ".txt";
                FileOutputStream fos = new FileOutputStream(txtPath);
                for (String path : videoPathList){
                    fos.write(("file '" + path + "'\r\n").getBytes());
                }
                fos.close();

                StringBuffer command = new StringBuffer("");
                command.append(ffmpegPath).append(" -f concat -safe 0 -i ").append(txtPath).append(" -c copy ").append(newMergePath);
                ProcessUtil.exec(command.toString(), null, 1000L);

                // 删除产生的临时用来存放每个视频文件路径的txt文件
                File txtFile = new File(txtPath);
                txtFile.delete();

                // 删除原视频文件
//                for (String filePath : videoPathList){
//                    File file = new File(filePath);
//                    file.delete();
//                }

                // 合成的新视频文件重命名为原视频文件的第一个文件名
//                File newFile = new File(newMergePath);
//                File oldFile = new File(firstVideo);
//                newFile.renameTo(oldFile);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 视频转图片
     * @author Yby
     * @date 2023/3/6 17:33
     * @version 1.0
     */
    public void videoToImage() {
        String commond = "ffmpeg.exe -i D:/2.mp4 -r 5 -f image2 D:/mp4img/1_frame_%05d.jpg";
    }

    /**
     * 图片转视频
     * @author Yby
     * @date 2023/3/6 17:41
     * @version 1.0
     */
    public void imageToVideo() {
        String commond = "ffmpeg.exe -f image2 -framerate 25 -i D:/mp4img/1_frame_%05d.jpg -b:v 25313k D:/new.mp4";
    }

}
