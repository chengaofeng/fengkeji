/* ====================================================================
 * $Source:  $
 * $Author:  $
 * $Revision:  $
 * $Date:  $
 * ====================================================================
 *  File  			UserPacket.java
 *  Description
 *  History      	2014/07/29
 *           Copyright 2002 Co. All Rights Reserved.
 * ====================================================================
 */
package org.fengkeji.weixin.entity;

import org.fengkeji.weixin.entity.base.BaseModule;

/**
 *
 *
 * @author
 * @version    1.0
 */

public class UserPacket implements BaseModule{

	/**
     * UserPacket.java
     */
    private static final long serialVersionUID = -7012450939823828653L;


    /**
	 * ȱʡ�Ĺ��캯��
	 */
	public UserPacket() {
	}


	/**
	 * packetId
	 */
	private String packetId = null;

	/**
	 * openId
	 */
	private String openId = null;

	/**
	 * ecouponId
	 */
	private String ecouponId = null;

	/**
	 * ecouponType
	 */
	private String ecouponType = null;


	/**
	 * ȡ��packetId
	 *
	 * @return packetId
	 */
	public String getPacketId() {
		return this.packetId;
	}

	/**
	 * ����packetId
	 *
	 * @param �µ�packetId
	 */
	public void setPacketId(String packetId) {
		this.packetId = packetId;
	}

	/**
	 * ȡ��openId
	 *
	 * @return openId
	 */
	public String getOpenId() {
		return this.openId;
	}

	/**
	 * ����openId
	 *
	 * @param �µ�openId
	 */
	public void setOpenId(String openId) {
		this.openId = openId;
	}

	/**
	 * ȡ��ecouponId
	 *
	 * @return ecouponId
	 */
	public String getEcouponId() {
		return this.ecouponId;
	}

	/**
	 * ����ecouponId
	 *
	 * @param �µ�ecouponId
	 */
	public void setEcouponId(String ecouponId) {
		this.ecouponId = ecouponId;
	}

	/**
	 * ȡ��ecouponType
	 *
	 * @return ecouponType
	 */
	public String getEcouponType() {
		return this.ecouponType;
	}

	/**
	 * ����ecouponType
	 *
	 * @param �µ�ecouponType
	 */
	public void setEcouponType(String ecouponType) {
		this.ecouponType = ecouponType;
	}


	/**
	 * �����õķ����� ���Խ������ֶε��������
	 *
	 */
	@Override
    public String toString() {
		StringBuffer sb = new StringBuffer();


		sb.append("packetId:		" + packetId + "\n");
		sb.append("openId:		" + openId + "\n");
		sb.append("ecouponId:		" + ecouponId + "\n");
		sb.append("ecouponType:		" + ecouponType + "\n");

		return sb.toString();
	}

}
