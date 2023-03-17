package util;

import org.apache.commons.lang3.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HTML 工具类
 */
public class HTMLUtil {

    /**
     * 获取HTML内的文本，不包含标签
     */
    public static String getInnerTextPlus(String html) {
        if (StringUtils.isNotBlank(html)) {
            //去掉 html 的标签、多个空格合并成一个空格、去空格
            return html.replaceAll("(</?[^<>].*?>)|((&nbsp;)+)|(\\s*　*)", "");
        }
        return null;
    }

    /**
     * 获取img标签中的src路径
     */
    public static List<String> getInnerImage(String html) {
        List<String> src = new ArrayList<>();
        if (StringUtils.isNotBlank(html)) {
            String regx = "(?<=<img.{0,999}src=\").*?(?=\")";
            Pattern r = Pattern.compile(regx);
            Matcher m = r.matcher(html);
            while (m.find()) {
                String group = m.group();
                if (group != null && !group.equals("")) {
                    src.add(group);
                }
            }
        }
        return src;
    }
}
