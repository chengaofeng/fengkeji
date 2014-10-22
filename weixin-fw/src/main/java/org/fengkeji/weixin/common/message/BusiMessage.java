package org.fengkeji.weixin.common.message;

import java.io.Serializable;

public class BusiMessage implements Serializable {

    /**
     * BusiMessage.java
     */
    private static final long serialVersionUID = 8803777763779706704L;

    private String msgId = null;

    private String[] args = null;

    /**
     * @return the msgId
     */
    public String getMsgId() {
        return msgId;
    }

    /**
     * @return the args
     */
    public String[] getArgs() {
        return args;
    }

    public BusiMessage(String msgId, String[] args) {
        this.msgId = msgId;
        this.args = args;
    }
}
