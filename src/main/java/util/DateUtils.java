package util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static ThreadLocal<SimpleDateFormat> datetimeFormat = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

   /**
   * 标准日期格式化
   * @date 2022/1/21 11:34
   * @param date 日期类
   * @return 标准时间格式
   */
    public static String format(Date date){
        return datetimeFormat.get().format(date);
    }
}
