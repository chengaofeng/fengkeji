package org.fengkeji.weixin.common.message.req;

/**
 * 图片
 * 
 * @author Song
 */
public class ReqImageMessage extends ReqBaseMessage{
	// 图片链接  
    private String PicUrl;  
  
    public String getPicUrl() {  
        return PicUrl;  
    }  
  
    public void setPicUrl(String picUrl) {  
        PicUrl = picUrl;  
    }  
}
