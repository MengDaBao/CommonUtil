
import enums.DataSizeUnit;
import util.FileUtil;

import java.io.File;
import java.util.Date;

public class Test {
    public static void main(String[] args) throws Exception {
        FileUtil.copy(new File("D:\\3.mp4"), "D:\\4.mp4");
    }
}
