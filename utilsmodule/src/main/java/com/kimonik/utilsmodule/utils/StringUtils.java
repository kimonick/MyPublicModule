package com.kimonik.utilsmodule.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kimonik.utilsmodule.bean.GlobalBean;
import com.kimonik.utilsmodule.code.CreateCode;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * * ==================================================
 * name:            StringUtils
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：            2017/8/8
 * description：   字符串辅助工具类
 * history：
 * * ==================================================
 */

public class StringUtils {
    private static final Gson gson = new Gson();

    /**
     * 返回未解密的json对象
     */
    private static GlobalBean firstParseJson(String json) {
        return gson.fromJson(json, new TypeToken<GlobalBean>() {
        }.getType());


    }

    /**
     * @param json 第一次返回的json加密字符串
     * @return MD5验证成功后返回解密后的json字符串, MD5验证失败返回null
     */
    public static String getDecodeString(String json) {
        String temp;
        GlobalBean bean = firstParseJson(json);
        //MD5验证
        if (MD5Utils.md5("DYklj45T78ET@asd23" + bean.getDiyou() + "DYklj45T78ET@asd23").equals(bean.getXmdy())) {
            temp = CreateCode.s2pDiyou(bean.getDiyou());
            return temp;
        } else {
            return null;
        }
    }

    /**
     * 将请求参数进行加密再组装
     */
    public static Map<String, String> getRequestParams(TreeMap<String, String> map) {


        Map<String, String> params = new HashMap<>(2);
        params.put("diyou", CreateCode.p2sDiyou(CreateCode.GetJson(map)));
        params.put("xmdy", CreateCode.p2sXmdy(CreateCode.p2sDiyou(CreateCode.GetJson(map))));

//        Log.e("TAG", "getRequestParams: -diyou----"+params.get("diyou"));
//        Log.e("TAG", "getRequestParams: -xmdy----"+params.get("xmdy"));

        return params;
    }

    /**
     * 验证是否是手机号码
     *
     * @param str 手机号字符串
     * @return 是否是手机号格式
     */
    public static boolean isCellphone(String str) {
        Pattern pattern = Pattern.compile("1[0-9]{10}");
        Matcher matcher = pattern.matcher(str);
        return !str.equals("") && matcher.matches();
    }

    /**
     * 获取六位随机验证码,纯数字
     */
    public static String getRandomCode() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            builder.append((int) (Math.random() * 10));
        }
        return builder.toString();
    }


    /**
     * 密码规则：必须是6-16位大小写字母及数字的组合
     * 是否包含
     *
     * @param str 检验的字符串
     * @return 符合--true,不符合--false
     */
    public static boolean conformPasswordRule(String str) {
        boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
        boolean isLetter = false;//定义一个boolean值，用来表示是否包含字母
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            } else if (Character.isLetter(str.charAt(i))) {  //用char包装类中的判断字母的方法判断每一个字符
                isLetter = true;
            }
        }
        String regex = "^[a-zA-Z0-9]+$";
        return isDigit && isLetter && str.matches(regex);
    }


    /**
     * 将字符串转化为int型数字
     *
     * @param intString 数字型字符串
     * @return 字符串数字或0
     */
    public static int string2Integer(String intString) {

        if (TextUtils.isEmpty(intString)) {
            return 0;
        }
        try {

            return Integer.parseInt(intString.trim());
        } catch (NumberFormatException e) {
            return 0;
        }

    }


    /**
     * 将字符串转化为float型数字
     *
     * @param floatString 数字型字符串
     * @return 字符串数字或0
     */
    public static float string2Float(String floatString) {
        if (floatString == null) {
            return 0;
        }
        try {
            return Float.parseFloat(floatString);
        } catch (NumberFormatException e) {
            return 0;
        }

    }


    /**
     * 将时间戳转为字符串
     *
     * @param cc_time 时间戳字符串,到秒
     * @return yyyy-MM-dd
     */
    public static String getStrTime(String cc_time) {
        if (cc_time == null) {
            return "****-**-**";
        }
        String re_StrTime;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            long lcc_time = Long.valueOf(cc_time);
            re_StrTime = sdf.format(new Date(lcc_time * 1000L));

        } catch (NumberFormatException e) {
            return "****-**-**";
        }
        return re_StrTime;
    }


    /**
     * 将时间戳转为字符串
     *
     * @param cc_time 时间戳字符串,到秒
     * @return yyyy-MM-dd
     */
    public static String getStrTimeFull(String cc_time) {
        String re_StrTime;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss", Locale.getDefault());
        try {
            long lcc_time = Long.valueOf(cc_time);
            re_StrTime = sdf.format(new Date(lcc_time * 1000L));

        } catch (NumberFormatException e) {
            return "****-**-**";
        }

        return re_StrTime;
    }


    /**
     * 将时间戳转为字符串
     *
     * @param cc_time 时间戳字符串,到秒
     * @return yyyy-MM-dd
     */
    public static String getStrTimeBias(String cc_time) {
        if (cc_time == null) {
            return "****-**-**";
        }
        String re_StrTime;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        try {
            long lcc_time = Long.valueOf(cc_time);
            re_StrTime = sdf.format(new Date(lcc_time * 1000L));

        } catch (NumberFormatException e) {
            return "****-**-**";
        }
        return re_StrTime;
    }

    /**
     * 判断字符串是否为null,或者""、{}、[]
     *
     * @param str
     * @return
     */
    public static boolean isEmpty2(String str) {
        return str == null || "".equals(str.trim()) || "{}".equals(str)
                || "[]".equals(str) || "null".equals(str);
    }

    /**
     * 将字符串转化为2位小数的字符串,不足两位的补齐两位小数
     */
    public static String getTwoDecimalsStr(String fStr) {
        if (fStr == null) {
            return "";
        }
        float f = string2Float(fStr);
        DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        decimalFormat.setRoundingMode(RoundingMode.DOWN);//舍去多余位
        return decimalFormat.format(f);//format 返回的是字符串
    }

    /**
     * 补齐两位小数
     */
    public static String getTwoDecimalsStrUD(String fStr) {
        if (fStr == null) {
            return "";
        }
        double f = string2Float(fStr);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);//舍去多余位

//        BigDecimal bd = new BigDecimal(fStr).setScale(2, BigDecimal.ROUND_HALF_UP);
//        f = bd.doubleValue();
//        return ""+f;//format 返回的是字符串
        return decimalFormat.format(f);//format 返回的是字符串
    }


    /**
     * 每三位用逗号分隔,最终保留两位小数,不足的用0补齐
     */
    public static String getCommaDecimalsStr(String fStr) {
        boolean flag = false;

        if (fStr == null) {
            return "0.00";
        }

        if (fStr.contains("-")) {
            flag = true;
            fStr = fStr.replace("-", "");
        }

        if (fStr.contains(".")) {
            int temp = fStr.indexOf(".");
            String s = fStr.substring(0, temp);
            String se = fStr.substring(temp + 1);
            switch (se.length()) {
                case 0:
                    fStr = fStr + "00";
                    break;
                case 1:
                    fStr = fStr + "0";
                    break;
            }
            if (se.length() > 2) {
                fStr = s + "." + se.substring(0, 2);
            }
            if (flag) {
                return "-" + getCommaDecimalsStrAssist(s, fStr, 2);

            } else {
                return getCommaDecimalsStrAssist(s, fStr, 2);

            }

        } else {
            if (flag) {
                return "-"+getCommaDecimalsStrAssist(fStr, fStr + ".00", 2);

            } else {
                return getCommaDecimalsStrAssist(fStr, fStr + ".00", 2);

            }
        }
    }

    /**
     * getCommaDecimalsStr(String s)方法辅助
     */
    private static String getCommaDecimalsStrAssist(String s, String fStr, int flag) {
        if (s.length() > 3) {
            StringBuilder builder = new StringBuilder(fStr);
            int start = s.length() % 3;
            int tem = 0;
            if (start != 0) {
                builder.insert(s.length() % 3, ",");
                tem = 1;
            }
//              12,345678,910.00    -6
//              12,345,678,910.00   -10
//              12,345,678,910.00  -14
//              12,345,678,910.00
            //123,456,789  -3
            //123,456,789  -7

            int i = 1;
            while (s.length() - 3 * i >= 3) {
                if (flag == 2) {
                    builder.insert(fStr.length() + tem - 6 - (i - 1) * 4, ",");
                } else if (flag == 0) {
                    builder.insert(fStr.length() + tem - 3 - (i - 1) * 4, ",");
                }
                tem++;
//                Log.e("TAG", "getCommaDecimalsStrAssist: -----" + builder.toString() + "------------" + s.length());
                i++;
            }
//            while (s.length()+tem - 3 * i >= 3) {
//                builder.insert(s.length() - 3 * i - 3+tem, ",");
//                tem++;
//                Log.e("TAG", "getCommaDecimalsStrAssist: -----"+builder.toString()+"------------"+s.length());
//                i++;
//            }
            return builder.toString();
        } else {
            return fStr;
        }
    }

    /**
     * 每三位用逗号分隔,不保留小数
     */
    public static String getCommaDecimalsStrZeroDot(String fStr) {
        if (fStr == null) {
            return "0";
        }
        if (fStr.contains(".")) {
            int temp = fStr.indexOf(".");
            String s = fStr.substring(0, temp);
            return getCommaDecimalsStrAssist(s, s, 0);

        } else {
            return getCommaDecimalsStrAssist(fStr, fStr, 0);
        }
    }

    /**
     * 将字符串转成unicode
     *
     * @param str 待转字符串
     * @return unicode字符串
     */
    public static String convertStr2Unicode(String str) {
        str = (str == null ? "" : str);
        String tmp;
        StringBuffer sb = new StringBuffer(1000);
        char c;
        int i, j;
        sb.setLength(0);
        for (i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            sb.append("\\u");
            j = (c >>> 8); //取出高8位
            tmp = Integer.toHexString(j);
            if (tmp.length() == 1)
                sb.append("0");
            sb.append(tmp);
            j = (c & 0xFF); //取出低8位
            tmp = Integer.toHexString(j);
            if (tmp.length() == 1)
                sb.append("0");
            sb.append(tmp);

        }
        return (new String(sb));
    }

    /**
     * 将缩进的字符串,有空格的地方统一替换为8个   \u0020
     * 针对投资列表详情
     */
    public static String convertRetract(String str) {
        String temp = str.replace("\n\n", "\n").replace("\u00a0", "\u0020");
        Pattern p = Pattern.compile("\\u0020+");//匹配多于一个空格的地方,至少两个
        Matcher m = p.matcher(temp);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020");//将匹配到的字符串用指定字符串替代
        }
        m.appendTail(sb);//将未匹配到的地方添加到sb中
        return sb.toString();
    }

    /**
     * 科学计数法转换为诶数字scientific notation
     */
    public static String changeScientificNotation(String num1) {
        if (num1 != null) {
            BigDecimal bd1 = new BigDecimal(num1);
            return bd1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString();
        } else {
            return "0";
        }


    }


}
