package com.ai.paas.ipaas.uac.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.ai.paas.ipaas.PaaSConstant;
import com.ai.paas.ipaas.uac.UserClientException;
import com.ai.paas.ipaas.uac.constants.UserSDKConstants;

/**
 * http请求工具类
 * 
 * @author jianhua.ma 2015-3-20 update 2015-9-25
 */
public class HttpRequestUtil {

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 * @throws Exception
	 */
	public static String sendPost(String url, String param)
			throws RuntimeException {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		HttpURLConnection conn = null;
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			conn = (HttpURLConnection) realUrl.openConnection();
			conn.setConnectTimeout(UserSDKConstants.HTTP_CONN_CONNECT_TIMEOUT);
			conn.setReadTimeout(UserSDKConstants.HTTP_CONN_READ_TIMEOUT);
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);

			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());

			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			int responseCode = conn.getResponseCode();
			if (HttpURLConnection.HTTP_OK == responseCode) {// 连接成功
				// 当正确响应时处理数据
				StringBuffer sb = new StringBuffer();
				String readLine;
				// 处理响应流，必须与服务器响应流输出的编码一致
				in = new BufferedReader(new InputStreamReader(
						conn.getInputStream(), PaaSConstant.CHARSET_UTF8));
				while ((readLine = in.readLine()) != null) {
					sb.append(readLine).append("\n");
				}
				in.close();
				result = sb.toString();
			} else {
				throw new UserClientException("HttpURLConnection   error:"+responseCode);
			}
		} catch (Exception e) {
			throw new UserClientException("Http error!", e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
		return result;
	}

	public static void main(String[] args) {

		// 发送 POST 请求
		String sr;
		try {
			sr = HttpRequestUtil
					.sendPost(
							"http://10.1.228.198:14821/iPaas-Auth/service/check",
							"password=c96009e5552c5a43&authUserName=3&serviceId=SES910");
			System.out.println(sr);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}