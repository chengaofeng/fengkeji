package org.fengkeji.weixin.common.message.req;

/** 
 * 音频消息 
 *  
 * @author Song 
 * @date 2013-05-19 
 */  
public class ReqMusicMessage extends ReqBaseMessage {  
    // 媒体ID  
    private String MediaId;  
    // 语音格式  
    private String Format;  
  
    public String getMediaId() {  
        return MediaId;  
    }  
  
    public void setMediaId(String mediaId) {  
        MediaId = mediaId;  
    }  
  
    public String getFormat() {  
        return Format;  
    }  
  
    public void setFormat(String format) {  
        Format = format;  
    }  
}  
