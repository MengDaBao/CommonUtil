package util;

import java.io.File;

public class ConvertUtil {

    public static void convertMP4(String evnPath, File source, File target) {
        String sourcePath = source.getAbsolutePath();
        String targetPath = target.getAbsolutePath();
        if (target.exists()){
            target.delete();
        }
        String command = "cmd /c ffmpeg -loglevel quiet -i " + sourcePath + " -b:v 3200000 -b:a 800000 " + targetPath;
        try {
            ProcessUtil.exec(command, new File(evnPath), (long) 60000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
