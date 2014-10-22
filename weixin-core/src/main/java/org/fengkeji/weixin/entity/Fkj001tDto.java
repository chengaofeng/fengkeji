/**
 *
 */
package org.fengkeji.weixin.entity;

import java.sql.Timestamp;

import org.fengkeji.weixin.entity.base.BaseModule;

/**
 * @author ASUS
 *
 */
public class Fkj001tDto implements BaseModule{
    /**
     * User.java
     */
    private static final long serialVersionUID = -3764298287177112763L;



    /**
     * 缺省的构造函数
     */
    public Fkj001tDto() {
    }


    /**
     * 用户OpenID
     */
    public String openId;

    /**
     * 用户名
     */
    public String userName;

    /**
     * 性别
     */
    public String sex;

    /**
     * 电话号码
     */
    public String telno;

    /**
     * 聊天次数
     */
    public int messageCnt;
    
    /**
     * 关注日期
     */
    public String createDate = null;

    /**
     * 注册日期	
     */
    public String registDate = null;


    /**
     * 取得用户OpenID
     *
     * @return 用户OpenID
     */
    public String getOpenId() {
        return this.openId;
    }

    /**
     * 设置用户OpenID
     *
     * @param 新的用户OpenID
     */
    public void setOpenId(String openId) {
        this.openId = openId;
    }

    /**
     * 取得用户名
     *
     * @return 用户名
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * 设置用户名
     *
     * @param 新的userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    


    public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTelno() {
		return telno;
	}

	public void setTelno(String telno) {
		this.telno = telno;
	}

	public int getMessageCnt() {
		return messageCnt;
	}

	public void setMessageCnt(int messageCnt) {
		this.messageCnt = messageCnt;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getRegistDate() {
		return registDate;
	}

	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}

	/**
     * 调试用的方法， 可以将所有字段的数据输出
     *
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();


        sb.append("用户OpenID:     " + openId + "\n");
        sb.append("用户名:       " + userName + "\n");
        sb.append("性别:       " + sex + "\n");
        sb.append("电话号码:       " + telno + "\n");
        sb.append("聊天次数:       " + String.valueOf(messageCnt) + "\n");
        sb.append("关注日期:     " + createDate + "\n");
        sb.append("注册日期:     " + registDate + "\n");

        return sb.toString();
    }
}
