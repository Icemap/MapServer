package com.cheese.MapServer.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HttpUtils
{
	public static BufferedImage getImage(String url) throws IOException 
	{
		BufferedImage bufImg = null;
		
		URL urlObj = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();

		// 连接超时
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setConnectTimeout(25000);

		// 读取超时 --服务器响应比较慢,增大时间
		conn.setReadTimeout(25000);
		conn.setRequestMethod("GET");
		conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.104 Safari/537.36");
		conn.connect();

		bufImg = ImageIO.read(conn.getInputStream());
		
		return bufImg;
	}
	
	public static byte[] getRequest(String url, String repType)
	{
		String result = "";
		byte[] resByt = null;
		try
		{
			URL urlObj = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();

			// 连接超时
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setConnectTimeout(25000);

			// 读取超时 --服务器响应比较慢,增大时间
			conn.setReadTimeout(25000);
			conn.setRequestMethod("GET");
			conn.connect();

			PrintWriter out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"), true);

			if ("image/jpeg".equals(repType))
			{
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				BufferedImage bufImg = ImageIO.read(conn.getInputStream());
				ImageIO.write(bufImg, "jpg", outputStream);
				resByt = outputStream.toByteArray();
				outputStream.close();
			}
			else
			{
				// 取得输入流，并使用Reader读取
				BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

				System.out.println("=============================");
				System.out.println("Contents of get request");
				System.out.println("=============================");
				String lines = null;
				while ((lines = reader.readLine()) != null)
				{
					System.out.println(lines);
					result += lines;
					result += "\r";
				}
				resByt = result.getBytes();
				reader.close();
			}
			out.print(resByt);
			out.flush();
			out.close();
			// 断开连接
			conn.disconnect();
			System.out.println("=============================");
			System.out.println("Contents of get request ends");
			System.out.println("=============================");
		}
		catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resByt;
	}
	
	
	
	
	 private static Log log = LogFactory.getLog(HttpUtils.class);  
     
	    /** 
	     * 定义编码格式 UTF-8 
	     */  
	    public static final String URL_PARAM_DECODECHARSET_UTF8 = "UTF-8";  
	      
	    /** 
	     * 定义编码格式 GBK 
	     */  
	    public static final String URL_PARAM_DECODECHARSET_GBK = "GBK";  
	      
	    private static final String URL_PARAM_CONNECT_FLAG = "&";  
	      
	    private static final String EMPTY = "";  
	  
	    private static MultiThreadedHttpConnectionManager connectionManager = null;  
	  
	    private static int connectionTimeOut = 600000;  
	  
	    private static int socketTimeOut = 600000;  
	  
	    private static int maxConnectionPerHost = 20;  
	  
	    private static int maxTotalConnections = 20;  
	  
	    private static HttpClient client;  
	  
	    static{  
	        connectionManager = new MultiThreadedHttpConnectionManager();  
	        connectionManager.getParams().setConnectionTimeout(connectionTimeOut);  
	        connectionManager.getParams().setSoTimeout(socketTimeOut);  
	        connectionManager.getParams().setDefaultMaxConnectionsPerHost(maxConnectionPerHost);  
	        connectionManager.getParams().setMaxTotalConnections(maxTotalConnections);  
	        client = new HttpClient(connectionManager);  
	    }  
	      
	    /** 
	     * POST方式提交数据 
	     * @param url 
	     *          待请求的URL 
	     * @param params 
	     *          要提交的数据 
	     * @param enc 
	     *          编码 
	     * @return 
	     *          响应结果 
	     * @throws IOException 
	     *          IO异常 
	     */  
	    public static String URLPost(String url, Map<String, String> params, String enc){  
	  
	        String response = EMPTY;          
	        PostMethod postMethod = null;  
	        try {  
	            postMethod = new PostMethod(url);  
	            postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + enc);  
	            //将表单的值放入postMethod中  
	            Set<String> keySet = params.keySet();  
	            for(String key : keySet){  
	                String value = params.get(key);  
	                postMethod.addParameter(key, value);  
	            }             
	            //执行postMethod  
	            int statusCode = client.executeMethod(postMethod);  
	            if(statusCode == HttpStatus.SC_OK) {  
	                response = postMethod.getResponseBodyAsString();  
	            }else{  
	                log.error("响应状态码 = " + postMethod.getStatusCode());  
	            }  
	        }catch(HttpException e){  
	            log.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);  
	            e.printStackTrace();  
	        }catch(IOException e){  
	            log.error("发生网络异常", e);  
	            e.printStackTrace();  
	        }finally{  
	            if(postMethod != null){  
	                postMethod.releaseConnection();  
	                postMethod = null;  
	            }  
	        }  
	          
	        return response;  
	    }  
	      
	    /** 
	     * GET方式提交数据 
	     * @param url 
	     *          待请求的URL 
	     * @param params 
	     *          要提交的数据 
	     * @param enc 
	     *          编码 
	     * @return 
	     *          响应结果 
	     * @throws IOException 
	     *          IO异常 
	     */  
	    public static String URLGet(String url, Map<String, String> params, String enc){  
	  
	        String response = EMPTY;  
	        GetMethod getMethod = null;       
	        StringBuffer strtTotalURL = new StringBuffer(EMPTY);  
	          
	        if(strtTotalURL.indexOf("?") == -1) {  
	          strtTotalURL.append(url).append("?").append(getUrl(params, enc));  
	        } else {  
	            strtTotalURL.append(url).append("&").append(getUrl(params, enc));  
	        }  
	        log.debug("GET请求URL = \n" + strtTotalURL.toString());  
	          
	        try {  
	            getMethod = new GetMethod(strtTotalURL.toString());  
	            getMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + enc);  
	            //执行getMethod  
	            int statusCode = client.executeMethod(getMethod);  
	            if(statusCode == HttpStatus.SC_OK) {  
	                response = getMethod.getResponseBodyAsString();  
	            }else{  
	                log.debug("响应状态码 = " + getMethod.getStatusCode());  
	            }  
	        }catch(HttpException e){  
	            log.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);  
	            e.printStackTrace();  
	        }catch(IOException e){  
	            log.error("发生网络异常", e);  
	            e.printStackTrace();  
	        }finally{  
	            if(getMethod != null){  
	                getMethod.releaseConnection();  
	                getMethod = null;  
	            }  
	        }  
	          
	        return response;  
	    }     
	  
	    /** 
	     * 据Map生成URL字符串 
	     * @param map 
	     *          Map 
	     * @param valueEnc 
	     *          URL编码 
	     * @return 
	     *          URL 
	     */  
	    private static String getUrl(Map<String, String> map, String valueEnc) {  
	          
	        if (null == map || map.keySet().size() == 0) {  
	            return (EMPTY);  
	        }  
	        StringBuffer url = new StringBuffer();  
	        Set<String> keys = map.keySet();  
	        for (Iterator<String> it = keys.iterator(); it.hasNext();) {  
	            String key = it.next();  
	            if (map.containsKey(key)) {  
	                String val = map.get(key);  
	                String str = val != null ? val : EMPTY;  
	                try {  
	                    str = URLEncoder.encode(str, valueEnc);  
	                } catch (UnsupportedEncodingException e) {  
	                    e.printStackTrace();  
	                }  
	                url.append(key).append("=").append(str).append(URL_PARAM_CONNECT_FLAG);  
	            }  
	        }  
	        String strURL = EMPTY;  
	        strURL = url.toString();  
	        if (URL_PARAM_CONNECT_FLAG.equals(EMPTY + strURL.charAt(strURL.length() - 1))) {  
	            strURL = strURL.substring(0, strURL.length() - 1);  
	        }  
	          
	        return (strURL);  
	    }  
}
