/**
 �� ################################
 * class name is MyMessage.java
 * create in 2014-6-26
 * author is ASUS
 * version 0.1 CoyeRight by IT
 �� ################################
 */
package org.fengkeji.weixin.common.message;

import java.io.Serializable;

/**
 * @author ASUS
 *
 */
public class ServMessage implements Serializable {

   /**
     * ServMessage.java
     */
    private static final long serialVersionUID = -6715601017554982330L;
private   String msgId;

   /**
 * @param msgId the msgId to set
 */
public void setMsgId(String msgId) {
    this.msgId = msgId;
}
/**
 * @param msgTxt the msgTxt to set
 */
public void setMsgTxt(String msgTxt) {
    this.msgTxt = msgTxt;
}
private   String msgTxt;

   /**
 * @return the msgType
 */
public String getMsgType() {
    return msgType;
}
/**
 * @param msgType the msgType to set
 */
public void setMsgType(String msgType) {
    this.msgType = msgType;
}
private  String msgType;
   /**
 * @return the msgId
 */
public String getMsgId() {
    return msgId;
}
/**
 * @return the msgTxt
 */
public String getMsgTxt() {
    return msgTxt;
}
}
