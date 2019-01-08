package cn.com.compass.util;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo url解析工具类
 * @date 2019/1/7 22:04
 */
public class UrlUtil {

    private final static String ENCODE = "UTF-8";

    /**
     * URL 解码
     * @param url
     * @return
     */
    public static String URLDecode(String url,String coding) {
        String result = "";
        if (null == url) {
            return "";
        }
        try {
            result = java.net.URLDecoder.decode(url, StringUtils.isNotEmpty(coding)?coding:ENCODE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * URL 转码
     * @param url
     * @return
     */
    public static String URLEncode(String url,String coding) {
        String result = "";
        if (null == url) {
            return "";
        }
        try {
            result = java.net.URLEncoder.encode(url, StringUtils.isNotEmpty(coding)?coding:ENCODE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 正则替换请求路径上的参数
     * @param url
     * @param name
     * @param value
     * @return
     */
    public static String replaceUrlParameterReg(String url, String name, String value) {
        if (StringUtils.isNotBlank(url) && StringUtils.isNotBlank(value)) {
            url = url.replaceAll("(" + name + "=[^&]*)", name + "=" + value);
        }
        return url;
    }

    /**
     * 获取去掉参数后的请求路径
     * @param url
     * @return
     */
    public static String getUrlPage(String url){
        String ru = url;
        if (StringUtils.isNotEmpty(url)&&url.length() > 1) {
            String[] arrSplit = url.split("[?]");
            if (arrSplit.length > 1) {
                if (arrSplit[0] != null) {
                    ru = arrSplit[0];
                }
            }
        }
        return ru;
    }

    /**
     * 正则替换，获取参数对应的值
     * @param url
     * @param name
     * @return
     */
    public static String getUrlParameterReg(String url, String name) {
        return TruncateUrlPageMap(url).get(name);
    }

    /**
     * 去掉url中的路径,留下请求参数部分
     * @param strURL
     * @return
     */
    public static String TruncateUrlPageString(String strURL) {
        String strAllParam = null;
        if (StringUtils.isNotEmpty(strURL)&&strURL.length() > 1) {
            String[] arrSplit = strURL.split("[?]");
            if (arrSplit.length > 1) {
                if (arrSplit[1] != null) {
                    strAllParam = arrSplit[1];
                }
            }
        }
        return strAllParam;
    }

    /**
     * 去掉url中的路径,留下请求参数部分
     * @param strURL
     * @return
     */
    public static Map<String,String> TruncateUrlPageMap(String strURL) {
        Map<String, String> mapRequest = new HashMap<String, String>();
        String[] arrSplit = null;
        String strUrlParam = TruncateUrlPageString(strURL);
        if (strUrlParam == null) {
            return mapRequest;
        }
        arrSplit = strUrlParam.split("[&]");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual = null;
            arrSplitEqual = strSplit.split("[=]");
            if (arrSplitEqual.length > 1) {
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
            } else {
                if (arrSplitEqual[0] != "") {
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }



    public static void main(String[] args) throws Exception {
        String URL = "http://www.baidu.com/activePage?a=1&b=我";
        String eUrl = URLEncode(URL,null);
        String dUrl = URLDecode(eUrl,null);
        System.out.println(eUrl);
        System.out.println(dUrl);
        System.out.println(replaceUrlParameterReg(URL,"a","2"));
        System.out.println(getUrlPage(URL));
        System.out.println(getUrlParameterReg(URL,"a"));
        System.out.println(TruncateUrlPageString(URL));
        System.out.println(TruncateUrlPageMap(URL));
    }
}
