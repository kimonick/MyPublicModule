package com.kimonik.utilsmodule.code;

/**
 * * ===============================================================
 * name:             CreateCode
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：             2018/1/23
 * description：
 * history： <meta-data
 android:name="sak"
 android:value=";3jm$>/p-ED^cVz_j~.KV&amp;V)k9jn,UAH" />
 <meta-data
 android:name="ssk"
 android:value="DY34fdgsWET@#$%wg#@4fgd345sg" />
 <meta-data
 android:name="rak"
 android:value="54Ms5bkE6UEdyrRviJ0![OR]g+i79x]k" />
 <meta-data
 android:name="rsk"
 android:value="DYklj45T78ET@asd23" />
 * *==================================================================
 */

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateCode {
    private static String SEND_AES_KEY;
    private static String SEND_SiGN_KEY;
    private static String RECEIVE_AES_KEY;
    private static String RECEIVE_SiGN_KEY;
    private static String DIYOU_URL;

    public CreateCode() {
    }

    public static String getSEND_AES_KEY() {
        return SEND_AES_KEY;
    }

    public static String getSEND_SiGN_KEY() {
        return SEND_SiGN_KEY;
    }

    public static String getRECEIVE_AES_KEY() {
        return RECEIVE_AES_KEY;
    }

    public static String getRECEIVE_SiGN_KEY() {
        return RECEIVE_SiGN_KEY;
    }

    public static String getDIYOU_URL() {
        return DIYOU_URL;
    }

    public static void initCreatCode(Context context) {
        SEND_AES_KEY = getMetaValue(context, "sak");
        SEND_SiGN_KEY = getMetaValue(context, "ssk");
        RECEIVE_AES_KEY = getMetaValue(context, "rak");
        RECEIVE_SiGN_KEY = getMetaValue(context, "rsk");
        DIYOU_URL = getMetaValue(context, "url");
    }

    public static boolean AuthentInfo(JSONObject data) throws JSONException {
        boolean isCorrect = false;
        if(data.getString("xmdy").equals(s2pXmdy(data.getString("diyou")))) {
            isCorrect = true;
        }

        return isCorrect;
    }

    public static String p2sDiyou(String str) {
        return replaceBlank(AESencrypt.encrypt2PHP(SEND_AES_KEY, replaceBlank(GetBase64Code(str))));
    }

    public static String p2sXmdy(String str) {
        try {
            return getMD5Str(SEND_SiGN_KEY + replaceBlank(str) + SEND_SiGN_KEY);
        } catch (Exception var2) {
            var2.printStackTrace();
            return "";
        }
    }

    public static String s2pDiyou(String str) {
        try {
//            return UnicodeToString(new String(Base64.decode(AESencrypt.decrypt2PHP(RECEIVE_AES_KEY, str).getBytes(), 2)));
            return UnicodeToString(new String(Base64.decode(AESencrypt.decrypt2PHP("54Ms5bkE6UEdyrRviJ0![OR]g+i79x]k", str).getBytes(), 2)));
        } catch (UnsupportedEncodingException var2) {
            var2.printStackTrace();
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return "";
    }

    public static String s2pXmdy(String str) {
        String s = null;

        try {
            s = getMD5Str(RECEIVE_SiGN_KEY + str + RECEIVE_SiGN_KEY);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return s;
    }

    public static String GetJson(TreeMap<String, String> map) {
        String str = "{";
        List<Entry<String, String>> infoIds = new ArrayList(map.entrySet());
        Collections.sort(infoIds, new Comparator<Entry<String, String>>() {
            public int compare(Entry<String, String> o1, Entry<String, String> o2) {
                return ((String)o1.getKey()).compareTo((String)o2.getKey());
            }
        });

        for(int i = 0; i < infoIds.size(); ++i) {
            Object key = ((Entry)infoIds.get(i)).getKey();
            Object val = ((Entry)infoIds.get(i)).getValue();
            str = str + "\"" + key + "\"" + ":";
            if(i != infoIds.size() - 1) {
                str = str + "\"" + val + "\"" + ",";
            } else {
                str = str + "\"" + val + "\"";
            }
        }

        str = str + "}";
        return str;
    }

    public static String GetDeviceToken(TreeMap<String, String> map) {
        String str = "{";
        List<Entry<String, String>> infoIds = new ArrayList(map.entrySet());
        Collections.sort(infoIds, new Comparator<Entry<String, String>>() {
            public int compare(Entry<String, String> o1, Entry<String, String> o2) {
                return ((String)o1.getKey()).compareTo((String)o2.getKey());
            }
        });

        for(int i = 0; i < infoIds.size(); ++i) {
            Object key = ((Entry)infoIds.get(i)).getKey();
            Object val = ((Entry)infoIds.get(i)).getValue();
            str = str + "'" + key + "'" + ":";
            if(i != infoIds.size() - 1) {
                str = str + "'" + val + "'" + ",";
            } else {
                str = str + "'" + val + "'";
            }
        }

        str = str + "}";
        return str;
    }

    private static String GetBase64Code(String str) {
        return Base64.encodeToString(replaceBlank(str).getBytes(), 2);
    }

    public static String decrypt2HF(String input) {
        byte[] decode = Base64.decode(input, 2);
        String ot = new String(decode);

        try {
            return URLDecoder.decode(ot, "utf-8");
        } catch (UnsupportedEncodingException var4) {
            var4.printStackTrace();
            return null;
        }
    }

    private static String replaceBlank(String str) {
        String dest = "";
        if(str != null) {
            Pattern p = Pattern.compile("\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll(" ");
        }

        return dest;
    }

    public static String getUrl(TreeMap<String, String> map) {
        return replaceBlank(DIYOU_URL) + GetJson(map);
    }

    public static String unicodeToGB(String s) {
        StringBuffer sb = new StringBuffer();
        StringTokenizer st = new StringTokenizer(s, "\\u");

        while(st.hasMoreTokens()) {
            sb.append((char)Integer.parseInt(st.nextToken(), 16));
        }

        return sb.toString();
    }

    public static String toUnicodeString(String s) {
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if(c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                sb.append("\\u" + Integer.toHexString(c));
            }
        }

        return sb.toString();
    }

    public static String toUnicode(String str) {
        char[] arChar = str.toCharArray();
        boolean value = false;
//        int iValue = false;//源代码201701231422
        String uStr = "";

        for(int i = 0; i < arChar.length; ++i) {
            int iValue = str.charAt(i);
            if(iValue <= 256) {
                uStr = uStr + "\\" + Integer.toHexString(iValue);
            } else {
                uStr = uStr + "\\u" + Integer.toHexString(iValue);
            }
        }

        return uStr;
    }

    public static String getMd5Value(String sSecret) {
        try {
            MessageDigest bmd5 = MessageDigest.getInstance("MD5");
            bmd5.update(sSecret.getBytes());
            StringBuffer buf = new StringBuffer();
            byte[] b = bmd5.digest();

            for(int offset = 0; offset < b.length; ++offset) {
                int i = b[offset];
                if(i < 0) {
                    i += 256;
                }

                if(i < 16) {
                    buf.append("0");
                }

                buf.append(Integer.toHexString(i));
            }

            return buf.toString();
        } catch (NoSuchAlgorithmException var6) {
            var6.printStackTrace();
            return "";
        }
    }

    public static String getMD5Str(String str) {
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException var5) {
            System.out.println("NoSuchAlgorithmException caught!");
            System.exit(-1);
        } catch (UnsupportedEncodingException var6) {
            var6.printStackTrace();
        }

        byte[] byteArray = messageDigest.digest();
        StringBuffer md5StrBuff = new StringBuffer();

        for(int i = 0; i < byteArray.length; ++i) {
            if(Integer.toHexString(255 & byteArray[i]).length() == 1) {
                md5StrBuff.append("0").append(Integer.toHexString(255 & byteArray[i]));
            } else {
                md5StrBuff.append(Integer.toHexString(255 & byteArray[i]));
            }
        }

        return md5StrBuff.toString().toLowerCase();
    }

    private static String getMetaValue(Context context, String metaKey) {
        Bundle metaData = null;
        String apiKey = null;

        if(context != null && metaKey != null) {
            try {
                ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                        PackageManager.GET_META_DATA);//原flags128
                if(ai != null) {
                    metaData = ai.metaData;
                }

                if(metaData != null) {
                    apiKey = metaData.getString(metaKey);
                }
            } catch (NameNotFoundException var5) {
                ;
            }

            return apiKey;
        } else {
            return null;
        }
    }

    private static String UnicodeToString(String str) {
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");

        char ch;
        for(Matcher matcher = pattern.matcher(str); matcher.find(); str = str.replace(matcher.group(1), String.valueOf(ch))) {
            ch = (char)Integer.parseInt(matcher.group(2), 16);
        }

        return str;
    }
}

