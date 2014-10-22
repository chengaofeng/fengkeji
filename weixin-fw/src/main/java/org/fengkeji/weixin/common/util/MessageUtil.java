package org.fengkeji.weixin.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.fengkeji.weixin.common.message.resp.RespArticle;
import org.fengkeji.weixin.common.message.resp.RespMusicMessage;
import org.fengkeji.weixin.common.message.resp.RespNewsMessage;
import org.fengkeji.weixin.common.message.resp.RespTextMessage;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * 消息工具类
 *
 */
public class MessageUtil {


	/**
	 * 解析微信发来的请求（XML）
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseXml(HttpServletRequest request) {
		// 将解析结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>();
		request.toString();
		// 从request中取得输入流
		InputStream inputStream = null;
		try {
			inputStream = request.getInputStream();
			// 读取输入流
			SAXReader reader = new SAXReader();
			// byte[] b = IOUtils.toByteArray(inputStream);
			// Document document = reader.read(new
			// ByteArrayInputStream(b),"UTF-8");

			Document document = reader.read(inputStream);
			// 得到xml根元素
			Element root = document.getRootElement();
			// 得到根元素的所有子节点
			List<Element> elementList = root.elements();

			// 遍历所有子节点
			for (Element e : elementList)
				map.put(e.getName(), e.getText());

			// 释放资源
			inputStream.close();
			inputStream = null;
		} catch (IOException | DocumentException e1) {
			e1.printStackTrace();
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}


		return map;
	}

	/**
	 * 文本消息对象转换成xml
	 *
	 * @param textMessage 文本消息对象
	 * @return xml
	 */
	public static String textMessageToXml(RespTextMessage textMessage) {
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}

	/**
	 * 音乐消息对象转换成xml
	 *
	 * @param musicMessage 音乐消息对象
	 * @return xml
	 */
	public static String musicMessageToXml(RespMusicMessage musicMessage) {
		xstream.alias("xml", musicMessage.getClass());
		return xstream.toXML(musicMessage);
	}

	/**
	 * 图文消息对象转换成xml
	 *
	 * @param newsMessage 图文消息对象
	 * @return xml
	 */
	public static String newsMessageToXml(RespNewsMessage newsMessage) {
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new RespArticle().getClass());
		return xstream.toXML(newsMessage);
	}

	/**
	 * 扩展xstream，使其支持CDATA块
	 *
	 * @date 2013-05-19
	 */
	private static XStream xstream = new XStream(new XppDriver() {
		@Override
        public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				// 对所有xml节点的转换都增加CDATA标记
				boolean cdata = true;

				@SuppressWarnings("rawtypes")
				@Override
                public void startNode(String name, Class clazz) {
					super.startNode(name, clazz);
				}

				@Override
                protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});
}

