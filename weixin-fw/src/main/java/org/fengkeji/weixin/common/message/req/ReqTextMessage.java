package org.fengkeji.weixin.common.message.req;

/**
 * 文本消息
 * 
 * @author Song
 */
public class ReqTextMessage extends ReqBaseMessage{
	
	// 消息内容  
    private String Content;  
  
    public String getContent() {  
        return Content;  
    }  
  
    public void setContent(String content) {  
        Content = content;  
    }
}
