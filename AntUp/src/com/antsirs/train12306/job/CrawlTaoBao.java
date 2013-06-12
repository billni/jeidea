package com.antsirs.train12306.job;

import java.io.InputStream;  
import java.io.StringWriter;  
import org.apache.commons.io.IOUtils;  
import org.apache.commons.lang.StringUtils;  
import org.apache.http.HttpEntity;  
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;


public class CrawlTaoBao {
    //模拟UA  
    public static String UA = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.28 (KHTML, like Gecko) Chrome/26.0.1397.2 Safari/537.28";  

    //中国的IP段，如果你想爬其他的IP段，加在这里就ok了�?
    public static int[][] ip_ = { { 1, 12, 0, 0, 1, 15, 255, 255 },
            { 1, 24, 0, 0, 1, 31, 255, 255 },  
            { 27, 8, 0, 0, 27, 31, 255, 255 },  
            { 27, 36, 0, 0, 27, 47, 255, 255 },  
            { 27, 50, 128, 0, 27, 50, 255, 255 },  
            { 27, 54, 192, 0, 27, 54, 255, 255 },  
            { 27, 128, 0, 0, 27, 129, 255, 255 },  
            { 27, 144, 0, 0, 27, 144, 255, 255 },
            { 27, 148, 0, 0, 27, 159, 255, 255 },  
            { 27, 184, 0, 0, 27, 227, 255, 255 },  
            { 58, 14, 0, 0, 58, 25, 255, 255 },  
            { 58, 30, 0, 0, 58, 63, 255, 255 },  
            { 58, 66, 0, 0, 58, 67, 255, 255 },  
            { 58, 68, 128, 0, 58, 68, 255, 255 },  
            { 58, 82, 0, 0, 58, 83, 255, 255 },  
            { 58, 87, 64, 0, 58, 87, 127, 255 },  
            { 58, 99, 128, 0, 58, 101, 255, 255 },  
            { 58, 116, 0, 0, 58, 119, 255, 255 },  
            { 58, 128, 0, 0, 58, 135, 255, 255 },  
            { 58, 144, 0, 0, 58, 144, 255, 255 },  
            { 58, 154, 0, 0, 58, 155, 255, 255 },  
            { 58, 192, 0, 0, 58, 223, 255, 255 },  
            { 58, 240, 0, 0, 58, 255, 255, 255 },  
            { 59, 32, 0, 0, 59, 83, 255, 255 },  
            { 59, 107, 0, 0, 59, 111, 255, 255 },  
            { 59, 151, 0, 0, 59, 151, 127, 255 },  
            { 59, 155, 0, 0, 59, 155, 255, 255 },  
            { 59, 172, 0, 0, 59, 175, 255, 255 },  
            { 59, 191, 0, 0, 59, 255, 255, 255 },  
            { 60, 0, 0, 0, 60, 31, 255, 255 },  
            { 60, 55, 0, 0, 60, 55, 255, 255 },  
            { 60, 63, 0, 0, 60, 63, 255, 255 },  
            { 60, 160, 0, 0, 60, 191, 255, 255 },  
            { 60, 194, 0, 0, 60, 195, 255, 255 },  
            { 60, 200, 0, 0, 60, 223, 255, 255 },  
            { 60, 232, 0, 0, 60, 233, 255, 255 },  
            { 60, 235, 0, 0, 60, 235, 255, 255 },  
            { 60, 245, 128, 0, 60, 245, 255, 255 },  
            { 60, 247, 0, 0, 60, 247, 255, 255 },  
            { 60, 252, 0, 0, 60, 252, 255, 255 },  
            { 60, 253, 128, 0, 60, 253, 255, 255 },  
            { 60, 255, 0, 0, 60, 255, 255, 255 },  
            { 61, 4, 80, 0, 61, 4, 95, 255 },  
            { 61, 4, 176, 0, 61, 4, 191, 255 },  
            { 61, 8, 160, 0, 61, 8, 175, 255 },  
            { 61, 28, 0, 0, 61, 28, 127, 255 },  
            { 61, 29, 128, 0, 61, 29, 255, 255 },  
            { 61, 45, 128, 0, 61, 45, 191, 255 },  
            { 61, 47, 128, 0, 61, 47, 191, 255 },  
            { 61, 48, 0, 0, 61, 55, 255, 255 },  
            { 61, 87, 192, 0, 61, 87, 255, 255 },  
            { 61, 128, 0, 0, 61, 191, 255, 255 },  
            { 61, 232, 0, 0, 61, 237, 255, 255 },  
            { 61, 240, 0, 0, 61, 243, 255, 255 },  
            { 110, 6, 0, 0, 110, 7, 255, 255 },  
            { 110, 16, 0, 0, 110, 19, 255, 255 },  
            { 110, 40, 0, 0, 110, 43, 255, 255 },  
            { 110, 48, 0, 0, 110, 48, 255, 255 },  
            { 110, 51, 0, 0, 110, 53, 255, 255 },  
            { 110, 56, 0, 0, 110, 65, 255, 255 },  
            { 110, 72, 0, 0, 110, 73, 255, 255 },  
            { 110, 75, 0, 0, 110, 76, 63, 255 },  
            { 110, 76, 192, 0, 110, 77, 127, 255 },  
            { 110, 80, 0, 0, 110, 91, 255, 255 },  
            { 110, 94, 0, 0, 110, 127, 255, 255 },  
            { 110, 152, 0, 0, 110, 157, 255, 255 },  
            { 110, 166, 0, 0, 110, 167, 255, 255 },  
            { 110, 172, 192, 0, 110, 173, 47, 255 },  
            { 110, 173, 64, 0, 110, 173, 127, 255 },  
            { 110, 173, 192, 0, 110, 173, 223, 255 },  
            { 110, 176, 0, 0, 110, 223, 255, 255 },  
            { 110, 228, 0, 0, 110, 231, 255, 255 },  
            { 110, 232, 32, 0, 110, 232, 63, 255 },  
            { 110, 236, 0, 0, 110, 237, 255, 255 },  
            { 110, 240, 0, 0, 110, 255, 255, 255 },  
            { 111, 0, 0, 0, 111, 63, 255, 255 },  
            { 111, 66, 0, 0, 111, 66, 255, 255 },  
            { 111, 67, 192, 0, 111, 67, 207, 255 },  
            { 111, 68, 64, 0, 111, 68, 95, 255 },  
            { 111, 72, 0, 0, 111, 79, 255, 255 },  
            { 111, 85, 0, 0, 111, 85, 255, 255 },  
            { 111, 91, 192, 0, 111, 91, 223, 255 },  
            { 111, 112, 0, 0, 111, 117, 255, 255 },  
            { 111, 119, 64, 0, 111, 119, 159, 255 },  
            { 111, 120, 0, 0, 111, 124, 255, 255 },  
            { 111, 126, 0, 0, 111, 167, 255, 255 },  
            { 111, 170, 0, 0, 111, 170, 255, 255 },  
            { 111, 172, 0, 0, 111, 183, 255, 255 },  
            { 111, 186, 0, 0, 111, 187, 255, 255 },  
            { 111, 192, 0, 0, 111, 215, 255, 255 },  
            { 111, 221, 128, 0, 111, 222, 255, 255 },  
            { 111, 223, 240, 0, 111, 223, 243, 255 },  
            { 111, 223, 248, 0, 111, 223, 251, 255 },  
            { 111, 224, 0, 0, 111, 231, 255, 255 },  
            { 111, 235, 96, 0, 111, 235, 127, 255 },  
            { 111, 235, 160, 0, 111, 235, 191, 255 },  
            { 112, 0, 0, 0, 112, 67, 255, 255 },  
            { 112, 73, 0, 0, 112, 75, 255, 255 },  
            { 112, 80, 0, 0, 112, 87, 255, 255 },  
            { 112, 109, 128, 0, 112, 109, 255, 255 }}; 
    
	private static final String PROXY_HOST= "10.18.8.108";
	private static final int PROXY_PORT = 8008;
	private static final String PROXY_USERNAME= "niyong";
	private static final String PROXY_PASSWORD= "nY111111";
	private static final String PROXY_WORKSTATION= "isa06";
	private static final String PROXY_DOMAIN= "ulic";
	
    public static DefaultHttpClient getHttpClient(DefaultHttpClient httpClient){
        NTCredentials credentials = new NTCredentials(PROXY_USERNAME ,PROXY_PASSWORD , PROXY_WORKSTATION, PROXY_DOMAIN);	        
        httpClient.getCredentialsProvider().setCredentials(new AuthScope(PROXY_HOST, PROXY_PORT), credentials);	      
        HttpHost proxy = new HttpHost(PROXY_HOST, PROXY_PORT);
        httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
        return httpClient;
    }
    
    public static void main(String[] args) {
        try {
            DefaultHttpClient hc = getHttpClient(new DefaultHttpClient());
            InputStream is = null;
            //循环上面的IP段数�?
            for (int m = 0; m < ip_.length; m++)
                for (int n = 0; n < ip_[0].length; n++)
                    //循环IP段开始爬去数�? 
                    for (int i = ip_[m][0]; i <= ip_[m][4]; i++)  
                        for (int ii = ip_[m][1]; ii <= ip_[m][5]; ii++)  
                            for (int iii = ip_[m][2]; iii <= ip_[m][6]; iii++)  
                                for (int iiii = ip_[m][3]; iiii <= ip_[m][7]; iiii++) {  
                                    HttpGet http_get = new HttpGet("http://ip.taobao.com/service/getIpInfo.php?ip="+ i + "." + ii + "." + iii + "." + iiii);
                                    http_get.setHeader("User-Agent", UA);
                                    IPAddr ip = new IPAddr();
                                    int count = 0;
                                    do {
                                        count++;
                                        try {
                                            HttpEntity e = hc.execute(http_get).getEntity();
                                            is = e.getContent();
                                            StringWriter sw = new StringWriter();
                                            IOUtils.copy(is, sw);
                                            is.close();
                                            String res = sw.toString();
                                            System.out.println(res);
                                            JSONObject resp = new JSONObject(res); 
                                            if (resp.has("data")) {
                                                JSONObject data = resp.getJSONObject("data");
                                                if (data.has("ip")) {
                                                    String rip = data.getString("ip");
                                                    ip.setIp(rip);
                                                }
                                                if (data.has("country")) {
                                                    ip.setCountry(data.getString("country"));
                                                }
                                                if (data.has("country_id")) {
                                                    ip.setCountry_id(data.getString("country_id"));
                                                }
                                                if (data.has("area")) {
                                                	ip.setArea(data.getString("area"));
                                                } 
                                                if (data.has("area_id")) {
                                                    ip.setArea_id(data.getString("area_id"));
                                                }  
                                                if (data.has("region")) {
                                                    ip.setRegion(data.getString("region"));  
                                                }
                                                if (data.has("region_id")) {  
                                                    ip.setRegion_id(data.getString("region_id"));  
                                                }  
                                                if (data.has("city")) {  
                                                	ip.setCity(data.getString("city"));                                                    
                                                }  
                                                if (data.has("city_id")) {
                                                    ip.setCity_id(data.getString("city_id"));
                                                }
                                                if (data.has("county")) {
                                                    ip.setCounty(data.getString("county"));
                                                }
                                                if (data.has("county_id")) {
                                                    ip.setCounty_id(data.getString("county_id"));  
                                                }  
                                                if (data.has("isp_id")) {  
                                                    ip.setIsp_id(data.getString("isp_id"));  
                                                }
                                                if (data.has("isp")) {
                                                    ip.setIsp(data.getString("isp"));  
                                                }  
                                            }  
                                        } catch (Exception e) {  
                                            e.printStackTrace();  
                                        }  
                                        //因为淘宝对每秒的访问次数有限制，这里尝试十次，如果还拿不到城市，就跳过�? 
                                    } while (StringUtils.isBlank(ip.getCity()) && count<10);
                                    //這裡的Save()就是保存在数据库�?
                                    System.out.println("IP地址: " + ip.getIp()                		+ "  ---- city   -----" + ip.getCity() + "  ---serialno---  "
                                            + ip.Save());count = 0;
                                }  
        } catch (Exception e) {
            e.printStackTrace();  
        }  
    }  
}