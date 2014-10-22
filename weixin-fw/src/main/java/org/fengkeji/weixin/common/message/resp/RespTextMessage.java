package org.fengkeji.weixin.common.message.resp;

/**
 * 文本消息
 * 
 * @author Song
 */
public class RespTextMessage extends RespBaseMessage{
	
	private String Content;
	
	public void setContent(String content){
		Content = content;
	}
	
	public String getContent(){
		return Content;
	}
}
