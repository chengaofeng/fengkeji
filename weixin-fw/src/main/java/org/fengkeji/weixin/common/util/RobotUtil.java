/**
 * 
 */
package org.fengkeji.weixin.common.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import net.sf.json.JSONObject;

/**
 * @author ASUS
 *
 */
public final class RobotUtil {



	public static String callTuling(String reqText){
		String respText = "系统故障";

		try {
			reqText = URLEncoder.encode(reqText,"UTF-8");
			String reqUrl = "http://www.tuling123.com/openapi/api?" +
			"key=49510693dee120db79a3f9f7c906fc08&info="+reqText;

			URL url =new URL(reqUrl);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			InputStreamReader input = new InputStreamReader(conn.getInputStream(),"utf-8");
			Scanner inputStream = new Scanner(input);
			StringBuffer sb = new StringBuffer();
			while (inputStream.hasNext()) {
                sb.append(inputStream.nextLine());
			}
			JSONObject respJson = JSONObject.fromObject(sb.toString());
			respText = respJson.getString("text");

			input.close();
			inputStream.close();
			//JSONObject jsonObj = JSONObject.fromObject(sb.toString());

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return respText;
	}

}
