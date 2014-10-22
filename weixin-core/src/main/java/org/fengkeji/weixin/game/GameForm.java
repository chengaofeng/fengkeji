/**
 * 
 */
package org.fengkeji.weixin.game;

import org.fengkeji.weixin.base.AbstractWxForm;

/**
 * @author ASUS
 *
 */
public class GameForm extends AbstractWxForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getUserName() {
		return userName;
	}

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
    
    

}
