package com.bossteach.job.taobao;

import java.io.InputStreamReader;
import java.io.StringWriter;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class CrawlTaoBaoInsurance {
	private Log logger = LogFactory.getLog(CrawlTaoBaoInsurance.class);
	private static final String PROXY_HOST= "10.18.8.108";
	private static final int PROXY_PORT = 8008;
	private static final String PROXY_USERNAME= "niyong";
	private static final String PROXY_PASSWORD= "nY111111";
	private static final String PROXY_WORKSTATION= "isa06";
	private static final String PROXY_DOMAIN= "ulic";

	
    public DefaultHttpClient getHttpClient(DefaultHttpClient httpClient){
        NTCredentials credentials = new NTCredentials(PROXY_USERNAME ,PROXY_PASSWORD , PROXY_WORKSTATION, PROXY_DOMAIN);	        
        httpClient.getCredentialsProvider().setCredentials(new AuthScope(PROXY_HOST, PROXY_PORT), credentials);	      
        HttpHost proxy = new HttpHost(PROXY_HOST, PROXY_PORT);
        httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
        return httpClient;
    }	
	
	/** 
	     * 根据URL获得html信息 
	     * @param url 
	     * @return 
	     */  
	    public String getHtmlByUrl(String url){
	        StringWriter sw = new StringWriter();
	        
//	        DefaultHttpClient httpClient = getHttpClient(new DefaultHttpClient());//创建httpClient对象                	   
	        DefaultHttpClient httpClient = new DefaultHttpClient();
	        //如果代理需要密码验证，这里设置用户名密码  	        
	        HttpGet httpGet = new HttpGet(url);	        
	        try {	        	
	            HttpResponse response = httpClient.execute(httpGet);//得到responce对象  
	            int resStatu = response.getStatusLine().getStatusCode();//返回 	 
	            if (resStatu == HttpStatus.SC_OK) {//200正常
	                //获得相应实体  	            	
	                HttpEntity entity = response.getEntity();
	                if (entity!=null) {	             
	                	InputStreamReader insr = new InputStreamReader(entity.getContent(), "gb2312" /*ContentType.getOrDefault(entity).getCharset()*/);
                        IOUtils.copy(insr, sw);
	                	insr.close();
	                }
	            } else {
		            System.out.println("Http Status Code:" + resStatu);
	            }
	        } catch (Exception e) {  
	        	 System.out.println("访问【"+url+"】出现异常!");
	             e.printStackTrace();  
	        } finally {	        	        	
	        	logger.info("HttpClient连接关闭.");
	            httpClient.getConnectionManager().shutdown();	            
	        }  
	        return sw.toString();  
	    }
	    
	    public void analyseHtml(){
	        String html = getHtmlByUrl("http://baoxian.taobao.com/item.htm?spm=a220m.1000858.1000725.1.fLqK6A&id=17305541936&_u=f1o5vvjed13&is_b=1&cat_id=2&q=%BA%EB%BF%B5&rn=a8a7475deb04fb9c7763e9e97876ebe4");
	  /*
	   * <ul class="tab-bar clearfix"> 
 <li class="sel">宝贝详情</li> 
 <li>成交记录(<em class="cblue"></em>3864件)</li> 
</ul> 
<div class="tab-pannel"> 
 <!-- 商品详情 --> 
 <section class="J_goods-detail"> 
  <!-- 商品属性,有一个属性多值情况 --> 
  <ul class="attributes-list clearfix"> 
   <li title=" 浮动收益">收益方式: 浮动收益</li> 
   <li title=" 保本">保本类型: 保本</li> 
   <li title=" 3年以上">期限: 3年以上</li> 
   <li title=" HONGKANG LIFE/弘康人寿">品牌: HONGKANG LIFE/弘康人寿</li> 
   <li title=" 2个工作日">赎回到帐时间: 2个工作日</li> 
   <li title=" 低">风险等级: 低</li> 
   <li title=" 3~5%">预期年化收益: 3~5%</li> 
  </ul> 
  <!-- 宝贝描述 --> 
 </section> 
 <!-- 成交记录 --> 
 <section id="deal-record" class="J_bargain-detail hide"> 
  <span class="wait-img"><img src="http://img02.taobaocdn.com/tps/i2/T16WJqXaXeXXXXXXXX-32-32.gif" /></span> 
 </section> 
</div>

	   */
	        if (html!= null && !"".equals(html)) {	        	
	            Document doc = Jsoup.parse(html);  
	            Elements dlElement = doc.select("#J_ins_desc");
	            for (Element ele: dlElement) { 
	            	System.out.println(ele.html());  
	            }  
	        } 
	    }
	    /*
	     *  String html = getHtmlByUrl("http://baoxian.taobao.com/json/PurchaseList.do?page=1&itemId=17305541936&sellerId=1128953583&callback=mycallback&sold_total_num=0&callback=mycallback");
 doc.select("table");	      
<tbody>
 <tr>
  \r\n\t\t
  <th>买家&lt;\/th&gt;\r\n\t\t</th>
  <th>宝贝名称&lt;\/th&gt;\r\n\t\t</th>
  <th>拍下价格&lt;\/th&gt;\r\n\t\t</th>
  <th>购买数量&lt;\/th&gt;\r\n\t\t</th>
  <th>成交时间&lt;\/th&gt;\r\n\t\t</th>
  <th>状态&lt;\/th&gt;\r\n\t&lt;\/tr&gt;\r\n\t\t\t\t\t</th>
 </tr>
 <tr class="\&quot;odd\&quot;">
  \r\n \t\t
  <td class="\&quot;cell-align-l\&quot;">\r\n \t\t\t独**也\r\n \t\t&lt;\/td&gt;\r\n \t\t</td>
  <td class="\&quot;cell-align-l\&quot;">\r\n \t\t\t弘康灵动一号保险理财计划\r\n \t\t&lt;\/td&gt;\r\n \t\t</td>
  <td>\r\n \t\t\t<em>500.00&lt;\/em&gt;\r\n \t\t&lt;\/td&gt;\r\n \t\t</em></td>
  <td>\r\n \t\t\t1\r\n \t\t&lt;\/td&gt;\r\n \t\t</td>
  <td>\r\n \t\t\t2013-03-15 06:43:18\r\n \t\t&lt;\/td&gt;\r\n \t\t</td>
  <td>\r\n \t\t\t成交\r\n \t\t&lt;\/td&gt;\r\n\t\t&lt;\/tr&gt;\r\n\t\t\t\t\t</td>
 </tr>
 <tr class="\&quot;\&quot;">
  \r\n \t\t
  <td class="\&quot;cell-align-l\&quot;">\r\n \t\t\tj**8\r\n \t\t&lt;\/td&gt;\r\n \t\t</td>
  <td class="\&quot;cell-align-l\&quot;">\r\n \t\t\t弘康灵动一号保险理财计划\r\n \t\t&lt;\/td&gt;\r\n \t\t</td>
  <td>\r\n \t\t\t<em>1000.00&lt;\/em&gt;\r\n \t\t&lt;\/td&gt;\r\n \t\t</em></td>
  <td>\r\n \t\t\t2\r\n \t\t&lt;\/td&gt;\r\n \t\t</td>
  <td>\r\n \t\t\t2013-03-15 01:39:28\r\n \t\t&lt;\/td&gt;\r\n \t\t</td>
  <td>\r\n \t\t\t成交\r\n \t\t&lt;\/td&gt;\r\n\t\t&lt;\/tr&gt;\r\n\t\t\t\t\t</td>
 </tr>
 <tr class="\&quot;odd\&quot;">
  \r\n \t\t
  <td class="\&quot;cell-align-l\&quot;">\r\n \t\t\tr**7\r\n \t\t&lt;\/td&gt;\r\n \t\t</td>
  <td class="\&quot;cell-align-l\&quot;">\r\n \t\t\t弘康灵动一号保险理财计划\r\n \t\t&lt;\/td&gt;\r\n \t\t</td>
  <td>\r\n \t\t\t<em>2000.00&lt;\/em&gt;\r\n \t\t&lt;\/td&gt;\r\n \t\t</em></td>
  <td>\r\n \t\t\t4\r\n \t\t&lt;\/td&gt;\r\n \t\t</td>
  <td>\r\n \t\t\t2013-03-15 00:57:07\r\n \t\t&lt;\/td&gt;\r\n \t\t</td>
  <td>\r\n \t\t\t成交\r\n \t\t&lt;\/td&gt;\r\n\t\t&lt;\/tr&gt;\r\n\t\t\t\t\t</td>
 </tr>
 <tr class="\&quot;\&quot;">
  \r\n \t\t
  <td class="\&quot;cell-align-l\&quot;">\r\n \t\t\t超**购\r\n \t\t&lt;\/td&gt;\r\n \t\t</td>
  <td class="\&quot;cell-align-l\&quot;">\r\n \t\t\t弘康灵动一号保险理财计划\r\n \t\t&lt;\/td&gt;\r\n \t\t</td>
  <td>\r\n \t\t\t<em>20000.00&lt;\/em&gt;\r\n \t\t&lt;\/td&gt;\r\n \t\t</em></td>
  <td>\r\n \t\t\t40\r\n \t\t&lt;\/td&gt;\r\n \t\t</td>
  <td>\r\n \t\t\t2013-03-14 23:58:00\r\n \t\t&lt;\/td&gt;\r\n \t\t</td>
  <td>\r\n \t\t\t成交\r\n \t\t&lt;\/td&gt;\r\n\t\t&lt;\/tr&gt;\r\n\t\t\t\t\t</td>
 </tr>
 <tr class="\&quot;odd\&quot;">
  \r\n \t\t
  <td class="\&quot;cell-align-l\&quot;">\r\n \t\t\ts**e\r\n \t\t&lt;\/td&gt;\r\n \t\t</td>
  <td class="\&quot;cell-align-l\&quot;">\r\n \t\t\t弘康灵动一号保险理财计划\r\n \t\t&lt;\/td&gt;\r\n \t\t</td>
  <td>\r\n \t\t\t<em>2000.00&lt;\/em&gt;\r\n \t\t&lt;\/td&gt;\r\n \t\t</em></td>
  <td>\r\n \t\t\t4\r\n \t\t&lt;\/td&gt;\r\n \t\t</td>
  <td>\r\n \t\t\t2013-03-14 23:57:23\r\n \t\t&lt;\/td&gt;\r\n \t\t</td>
  <td>\r\n \t\t\t成交\r\n \t\t&lt;\/td&gt;\r\n\t\t&lt;\/tr&gt;\r\n\t\t\t\t\t</td>
 </tr>
 <tr class="\&quot;\&quot;">
  \r\n \t\t
  <td class="\&quot;cell-align-l\&quot;">\r\n \t\t\tz**i\r\n \t\t&lt;\/td&gt;\r\n \t\t</td>
  <td class="\&quot;cell-align-l\&quot;">\r\n \t\t\t弘康灵动一号保险理财计划\r\n \t\t&lt;\/td&gt;\r\n \t\t</td>
  <td>\r\n \t\t\t<em>500.00&lt;\/em&gt;\r\n \t\t&lt;\/td&gt;\r\n \t\t</em></td>
  <td>\r\n \t\t\t1\r\n \t\t&lt;\/td&gt;\r\n \t\t</td>
  <td>\r\n \t\t\t2013-03-14 23:38:46\r\n \t\t&lt;\/td&gt;\r\n \t\t</td>
  <td>\r\n \t\t\t成交\r\n \t\t&lt;\/td&gt;\r\n\t\t&lt;\/tr&gt;\r\n\t\t\t\t\t</td>
 </tr>
 <tr class="\&quot;odd\&quot;">
  \r\n \t\t
  <td class="\&quot;cell-align-l\&quot;">\r\n \t\t\t天**回\r\n \t\t&lt;\/td&gt;\r\n \t\t</td>
  <td class="\&quot;cell-align-l\&quot;">\r\n \t\t\t弘康灵动一号保险理财计划\r\n \t\t&lt;\/td&gt;\r\n \t\t</td>
  <td>\r\n \t\t\t<em>500.00&lt;\/em&gt;\r\n \t\t&lt;\/td&gt;\r\n \t\t</em></td>
  <td>\r\n \t\t\t1\r\n \t\t&lt;\/td&gt;\r\n \t\t</td>
  <td>\r\n \t\t\t2013-03-14 23:36:03\r\n \t\t&lt;\/td&gt;\r\n \t\t</td>
  <td>\r\n \t\t\t成交\r\n \t\t&lt;\/td&gt;\r\n\t\t&lt;\/tr&gt;\r\n\t\t\t\t\t</td>
 </tr>
 <tr class="\&quot;\&quot;">
  \r\n \t\t
  <td class="\&quot;cell-align-l\&quot;">\r\n \t\t\tb**7\r\n \t\t&lt;\/td&gt;\r\n \t\t</td>
  <td class="\&quot;cell-align-l\&quot;">\r\n \t\t\t弘康灵动一号保险理财计划\r\n \t\t&lt;\/td&gt;\r\n \t\t</td>
  <td>\r\n \t\t\t<em>500.00&lt;\/em&gt;\r\n \t\t&lt;\/td&gt;\r\n \t\t</em></td>
  <td>\r\n \t\t\t1\r\n \t\t&lt;\/td&gt;\r\n \t\t</td>
  <td>\r\n \t\t\t2013-03-14 23:22:33\r\n \t\t&lt;\/td&gt;\r\n \t\t</td>
  <td>\r\n \t\t\t成交\r\n \t\t&lt;\/td&gt;\r\n\t\t&lt;\/tr&gt;\r\n\t\t\t\t\t</td>
 </tr>
 <tr class="\&quot;odd\&quot;">
  \r\n \t\t
  <td class="\&quot;cell-align-l\&quot;">\r\n \t\t\t梦**3\r\n \t\t&lt;\/td&gt;\r\n \t\t</td>
  <td class="\&quot;cell-align-l\&quot;">\r\n \t\t\t弘康灵动一号保险理财计划\r\n \t\t&lt;\/td&gt;\r\n \t\t</td>
  <td>\r\n \t\t\t<em>500.00&lt;\/em&gt;\r\n \t\t&lt;\/td&gt;\r\n \t\t</em></td>
  <td>\r\n \t\t\t1\r\n \t\t&lt;\/td&gt;\r\n \t\t</td>
  <td>\r\n \t\t\t2013-03-14 23:15:52\r\n \t\t&lt;\/td&gt;\r\n \t\t</td>
  <td>\r\n \t\t\t成交\r\n \t\t&lt;\/td&gt;\r\n\t\t&lt;\/tr&gt;\r\n\t\t\t\t\t</td>
 </tr>
 <tr class="\&quot;\&quot;">
  \r\n \t\t
  <td class="\&quot;cell-align-l\&quot;">\r\n \t\t\tt**4\r\n \t\t&lt;\/td&gt;\r\n \t\t</td>
  <td class="\&quot;cell-align-l\&quot;">\r\n \t\t\t弘康灵动一号保险理财计划\r\n \t\t&lt;\/td&gt;\r\n \t\t</td>
  <td>\r\n \t\t\t<em>500.00&lt;\/em&gt;\r\n \t\t&lt;\/td&gt;\r\n \t\t</em></td>
  <td>\r\n \t\t\t1\r\n \t\t&lt;\/td&gt;\r\n \t\t</td>
  <td>\r\n \t\t\t2013-03-14 23:06:22\r\n \t\t&lt;\/td&gt;\r\n \t\t</td>
  <td>\r\n \t\t\t成交\r\n \t\t&lt;\/td&gt;\r\n\t\t&lt;\/tr&gt;\r\n\t&lt;\/table&gt;\r\n
   <div class="\&quot;pagination\&quot;">
    \r\n\t
    <div class="\&quot;page-bottom\&quot;">
     \r\n\t\t\t\t\t\t\r\n\t\t \t\t\t\r\n 
     <!--{Pagination Start}-->\r\n \t \t \t \t \t \t\t \t\t \t 
     <span class="\&quot;page-start\&quot;">&lt;\/span&gt;<span class="\&quot;page-cur\&quot;">1&lt;\/span&gt;<a href="\&quot;http://baoxian.taobao.com/json/PurchaseList.do?page=2&amp;&amp;itemId=17305541936&amp;sellerId=1128953583\&quot;">2&lt;\/a&gt;</a><a class="\&quot;page-next\&quot;" href="\&quot;http://baoxian.taobao.com/json/PurchaseList.do?page=2&amp;&amp;itemId=17305541936&amp;sellerId=1128953583\&quot;">下一页&lt;\/a&gt;\r\n \t&lt;\/div&gt;\r\n&lt;\/div&gt;\r\n&quot;}) </a></span></span>
    </div>
   </div></td>
 </tr>
</tbody>

	     */
	    
	    

	    
	    public static void main(String[] args) throws Exception {
	    	CrawlTaoBaoInsurance job = new CrawlTaoBaoInsurance();
	    	job.analyseHtml();
//	    	job.poolRequest();
//	    	job.transformToJsonObject();
		}
}

