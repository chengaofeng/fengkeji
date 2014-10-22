package org.fengkeji.weixin.common.message.resp;

/** 
 * 音乐消息 
 */  
public class RespMusicMessage extends RespBaseMessage {  
    // 音乐  
    private RespMusic Music;  
  
    public RespMusic getMusic() {  
        return Music;  
    }  
  
    public void setMusic(RespMusic music) {  
        Music = music;  
    }  
}  
