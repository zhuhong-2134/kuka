package com.camelot.kuka.common.utils;

import com.fehorizon.commonService.common.constants.SysConstant;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;

/**
 * <p>Description: [encode处理工具类]</p>
 * Created on 2019/9/25
 * @author <a href="mailto: hexiaobo@camelotchina.com">贺小波</a>
 * @version 1.0
 * Copyright (c) 2019 北京柯莱特科技有限公司
 */
public class EncodeUtil {

    /**
     * 编码字符集
     */
    private static final String defaultCharset = "utf-8";

    /**
     * <p>Description:[文件路径处理文件base64]</p>
     * Created on 2019/9/25
     * @param fileUrl 文件url
     * @return java.lang.String
     * @author 贺小波
     */
    public static String fileUrlBase64Encode(String fileUrl) throws IOException {
        if (StringUtils.isBlank(fileUrl)) {
            return null;
        }
        String encode;
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(fileUrl);
            byte[] data = new byte[inputStream.available()];
            inputStream.read(data);
            BASE64Encoder encoder = new BASE64Encoder();
            encode = encoder.encode(data);
        } catch (IOException e) {
            throw e;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return encode;
    }

    /**
     * <p>Description:[URLEncoder处理]</p>
     * Created on 2019/9/25
     * @param param   参数
     * @param charset 编码字符集-默认为utf-8
     * @return java.lang.String
     * @author 贺小波
     */
    public static String getURLEncoder(String param, String charset) throws UnsupportedEncodingException {
        if (StringUtils.isBlank(param)) {
            return null;
        }
        if (StringUtils.isBlank(charset)) {
            charset = defaultCharset;
        }
        String result = URLEncoder.encode(param, charset);
        return result;
    }

    /**
     * <p>Description:[base64码转字节数组]</p>
     * Created on 2019/9/27
     * @param base64str base64码
     * @return byte[]
     * @author 贺小波
     */
    public static byte[] getBase64Byte(String base64str) throws IOException {
        if (StringUtils.isBlank(base64str)) {
            return null;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] base64Bytes = decoder.decodeBuffer(base64str);
        if (base64Bytes != null) {
            for (int i = 0; i < base64Bytes.length; i++) {
                if (base64Bytes[i] < 0) {
                    base64Bytes[i] += SysConstant.BYTE_CORRECT;
                }
            }
        }
        return base64Bytes;
    }

    /**
     * <p>Description:[字节数组转base64]</p>
     * Created on 2019/9/29
     * @param bytes
     * @return java.lang.String
     * @author 贺小波
     */
    public static String getByteBase64(byte[] bytes) {
        if (bytes == null || bytes.length == SysConstant.ZERO) {
            return null;
        }
        BASE64Encoder encoder = new BASE64Encoder();
        String base64 = encoder.encode(bytes);
        return base64;
    }

    
	/**
	 * Description: [yml], 所以将特殊字符转义]
	 * @return: java.lang.String
	 **/
    public static String urlReduction(String url) {
	   url = url.replace("zdy100001", "http://");
	   url = url.replace("zdy100002", "https://");
	   url = url.replace("zdy100003", "/");
	   url = url.replace("zdy100004", "?");
	   url = url.replace("zdy100005", "=");
	   url = url.replace("zdy100006", "&");
	   url = url.replace("zdy100007", ".");
	   url = url.replace("zdy100008", "#");
	   return url;
	}
    
    public static String getContent(boolean flag,String key, Object... values) {
		String url = MessageFormat.format(key, values);
		try {
			return flag?URLEncoder.encode(url,defaultCharset):url;
		} catch (UnsupportedEncodingException e) {
			return url;
		}
	}

}
