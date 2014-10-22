package org.fengkeji.weixin.menu;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * @author ASUS
 *
 */
public class WeixinMenu {

	/**
	 * @param args
	 */
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		String token = "N5CYvmDSlJml_qaP-tNBODQXjm9OX3GaafM3B8-JBxecVEunhYyk74UNoLqG71szJBDkyPseJzTb0hJwS64q2g";
		String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="
				+ token;
		/**
		 * 设置菜单
		 */
		String responeJsonStr = "{" + "\"button\":[" + "{"
				+ "\"name\":\"个人信息\"," + "\"sub_button\":[" + "{"
				+ "\"type\":\"click\"," + "\"name\":\"注册信息\","
				+ "\"key\":\"MY_INFO\"" + "}," + "{" + "\"type\":\"click\","
				+ "\"name\":\"消费记录\"," + "\"key\":\"CONSUME_HISTORY\"" + "},"
				+ "{" + "\"type\":\"click\"," + "\"name\":\"收藏夹\","
				+ "\"key\":\"FAVOURITE\"" + "}]" + "}," + "{"
				+ "\"name\":\"商户活动\"," + "\"sub_button\":[" + "{"
				+ "\"type\":\"click\"," + "\"name\":\"固定活动\","
				+ "\"key\":\"FIXED_EVENTS\"" + "}," + "{"
				+ "\"type\":\"click\"," + "\"name\":\"新品上架\","
				+ "\"key\":\"NEW_PRODUCTS\"" + "}," + "{"
				+ "\"type\":\"click\"," + "\"name\":\"限时折扣\","
				+ "\"key\":\"LIMIT_SALE\"" + "}," + "{" + "\"type\":\"click\","
				+ "\"name\":\"限时活动\"," + "\"key\":\"LIMIT_EVENTS\"" + "},"
				+ "{" + "\"type\":\"click\"," + "\"name\":\"小游戏赢惊喜\","
				+ "\"key\":\"LITTLE_GAME\"" + "}]" + "}," + "{"
				+ "\"name\":\"圈子社区\"," + "\"sub_button\":[" + "{"
				+ "\"type\":\"click\"," + "\"name\":\"分享赢红包\","
				+ "\"key\":\"SHARE_GET_PACKET\"" + "}," + "{"
				+ "\"type\":\"click\"," + "\"name\":\"月/周排行榜\","
				+ "\"key\":\"RANKING_LIST\"" + "}," + "{"
				+ "\"type\":\"view\"," + "\"name\":\"微社区\","
				+ "\"url\":\"http://m.wsq.qq.com/263167200\"" + "}," + "{"
				+ "\"type\":\"click\"," + "\"name\":\"粉丝信箱\","
				+ "\"key\":\"FAN_MAIL\"" + "}," + "{" + "\"type\":\"click\","
				+ "\"name\":\"推荐好友赢好礼\"," + "\"key\":\"RECOMMEND_WIN\"" + "}]"
				+ "}" + "]" + "}";

		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod(url);
		post.setRequestBody(responeJsonStr);
		post.getParams().setContentCharset("utf-8");
		// 发送http请求
		String respStr = "";
		try {
			client.executeMethod(post);
			respStr = post.getResponseBodyAsString();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(responeJsonStr);
		System.out.println(respStr);
	}

}
