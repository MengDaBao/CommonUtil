package util;

import enums.DataSizeUnit;
import javafx.scene.transform.Scale;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.DecimalFormat;

public class FileUtil {

    private static final char[] DIGITS_LOWER;
    private static final char[] DIGITS_UPPER;

    static {
        DIGITS_LOWER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        DIGITS_UPPER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    }

    public static void copy(InputStream inputStream, String path, boolean cover) throws Exception {
        File file = new File(path);
        if (file.exists()) {
            if (cover) {
                file.delete();
            } else {
                throw new Exception("target is exist");
            }
        }
        if (!file.getParentFile().exists()) {
            file.mkdirs();
        }
        inputStream = new BufferedInputStream(inputStream);
        BufferedOutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(file.toPath()));
        byte[] arr = new byte[8192];
        while ((inputStream.read(arr)) != -1) {
            outputStream.write(arr);
        }
        inputStream.close();
        outputStream.flush();
        outputStream.close();
    }

    public static void copy(InputStream inputStream, String path) throws Exception {
        copy(inputStream, path, true);
    }

    public static void copy(File source, String path, boolean cover) throws Exception {
        FileInputStream inputStream = new FileInputStream(source);
        copy(inputStream, path, cover);
    }

    public static void copy(File source, String path) throws Exception {
        FileInputStream inputStream = new FileInputStream(source);
        copy(inputStream, path, true);
    }

    public static void copy(String sourcePath, String path, boolean cover) throws Exception {
        File file = new File(sourcePath);
        copy(file, path, cover);
    }

    public static void copy(String sourcePath, String path) throws Exception {
        copy(sourcePath, path, true);
    }

    public static double getFileSize(File file, DataSizeUnit unit, int decimalPoint) throws Exception{
        if (!file.exists()) {
            throw new Exception("file not exists");
        }
        if (file.isDirectory()) {
            throw new Exception("need file but is directory");
        }
        BigDecimal decimal = new BigDecimal(file.length());
        BigDecimal size = new BigDecimal(0);
        switch (unit) {
            case BYTE:
                size = decimal;
                break;
            case KB:
                size = decimal.divide(new BigDecimal(1024L), decimalPoint, RoundingMode.HALF_UP);
                break;
            case MB:
                size = decimal.divide(new BigDecimal(1048576L), decimalPoint, RoundingMode.HALF_UP);
                break;
            case GB:
                size = decimal.divide(new BigDecimal(1073741824L), decimalPoint, RoundingMode.HALF_UP);
                break;
            case TB:
                size = decimal.divide(new BigDecimal(1099511627776L), decimalPoint, RoundingMode.HALF_UP);
                break;
        }
        return size.doubleValue();
    }

    public String encodeHexString(byte[] data) {
        char[] toDigits = DIGITS_UPPER;
        int l = data.length;
        char[] out = new char[l << 1];
        int i = 0;

        for(int j = 0; i < l; ++i) {
            out[j++] = toDigits[(240 & data[i]) >>> 4];
            out[j++] = toDigits[15 & data[i]];
        }

        return new String(out);
    }

}
