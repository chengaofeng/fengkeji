/* ====================================================================
 * $Source:  $
 * $Author:  $
 * $Revision:  $
 * $Date:  $
 * ====================================================================
 *  File  			RedPacket.java
 *  Description
 *  History      	2014/07/23
 *           Copyright 2002 Co. All Rights Reserved.
 * ====================================================================
 */
package org.fengkeji.weixin.entity;
import java.sql.Timestamp;

import org.fengkeji.weixin.entity.base.BaseModule;

/**
 *
 *
 * @author
 * @version    1.0
 */

public class RedPacket implements BaseModule{

	/**
     * RedPacket.java
     */
    private static final long serialVersionUID = 7733627694076328252L;


    /**
	 * ȱʡ�Ĺ��캯��
	 */
	public RedPacket() {
	}


	/**
	 * rEDPACKETID
	 */
	private String redPacketId = null;

	/**
	 * rEDPACKETMONEY
	 */
	private Integer redPacketMoney = null;

	/**
	 * rEDPACKETNUM
	 */
	private Integer redPacketNum = null;

	/**
	 * rEDPACKETLAST
	 */
	private Integer redPacketLast = null;

	/**
	 * rEDPACKETCONTENT
	 */
	private String redPacketContent = null;

	/**
	 * rEDPACKETTYPE
	 */
	private String redPackageType = null;

	/**
	 * lIMITTIME
	 */
	private Timestamp limitTime = null;

	/**
	 * iNSERTTIME
	 */
	private Timestamp insertTime = null;


	/**
	 * ȡ��rEDPACKETID
	 *
	 * @return rEDPACKETID
	 */
	public String getRedPacketId() {
		return this.redPacketId;
	}

	/**
	 * ����rEDPACKETID
	 *
	 * @param �µ�rEDPACKETID
	 */
	public void setRedPacketId(String redPacketId) {
		this.redPacketId = redPacketId;
	}

	/**
	 * ȡ��rEDPACKETMONEY
	 *
	 * @return rEDPACKETMONEY
	 */
	public Integer getRedPacketMoney() {
		return this.redPacketMoney;
	}

	/**
	 * ����rEDPACKETMONEY
	 *
	 * @param �µ�rEDPACKETMONEY
	 */
	public void setRedPacketMoney(Integer redPacketMoney) {
		this.redPacketMoney = redPacketMoney;
	}

	/**
	 * ȡ��rEDPACKETNUM
	 *
	 * @return rEDPACKETNUM
	 */
	public Integer getRedPacketNum() {
		return this.redPacketNum;
	}

	/**
	 * ����rEDPACKETNUM
	 *
	 * @param �µ�rEDPACKETNUM
	 */
	public void setRedPacketNum(Integer redPacketNum) {
		this.redPacketNum = redPacketNum;
	}

	/**
	 * ȡ��rEDPACKETLAST
	 *
	 * @return rEDPACKETLAST
	 */
	public Integer getRedPacketLast() {
		return this.redPacketLast;
	}

	/**
	 * ����rEDPACKETLAST
	 *
	 * @param �µ�rEDPACKETLAST
	 */
	public void setRedPacketLast(Integer redPacketLast) {
		this.redPacketLast = redPacketLast;
	}

	/**
	 * ȡ��rEDPACKETCONTENT
	 *
	 * @return rEDPACKETCONTENT
	 */
	public String getRedPacketContent() {
		return this.redPacketContent;
	}

	/**
	 * ����rEDPACKETCONTENT
	 *
	 * @param �µ�rEDPACKETCONTENT
	 */
	public void setRedPacketContent(String redPacketContent) {
		this.redPacketContent = redPacketContent;
	}

	/**
	 * ȡ��rEDPACKETTYPE
	 *
	 * @return rEDPACKETTYPE
	 */
	public String getRedPacketType() {
		return this.redPackageType;
	}

	/**
	 * ����rEDPACKETTYPE
	 *
	 * @param �µ�rEDPACKETTYPE
	 */
	public void setRedPacketType(String redPackageType) {
		this.redPackageType = redPackageType;
	}

	/**
	 * ȡ��lIMITTIME
	 *
	 * @return lIMITTIME
	 */
	public Timestamp getLimitTime() {
		return this.limitTime;
	}

	/**
	 * ����lIMITTIME
	 *
	 * @param �µ�lIMITTIME
	 */
	public void setLimitTime(Timestamp limitTime) {
		this.limitTime = limitTime;
	}

	/**
	 * ȡ��iNSERTTIME
	 *
	 * @return iNSERTTIME
	 */
	public Timestamp getInsertTime() {
		return this.insertTime;
	}

	/**
	 * ����iNSERTTIME
	 *
	 * @param �µ�iNSERTTIME
	 */
	public void setinsertTime(Timestamp insertTime) {
		this.insertTime = insertTime;
	}


	/**
	 * �����õķ����� ���Խ������ֶε��������
	 *
	 */
	@Override
    public String toString() {
		StringBuffer sb = new StringBuffer();


		sb.append("rEDPACKETID:		" + redPacketId + "\n");
		sb.append("rEDPACKETMONEY:		" + redPacketMoney + "\n");
		sb.append("rEDPACKETNUM:		" + redPacketNum + "\n");
		sb.append("rEDPACKETLAST:		" + redPacketLast + "\n");
		sb.append("rEDPACKETCONTENT:		" + redPacketContent + "\n");
		sb.append("rEDPACKETTYPE:		" + redPackageType + "\n");
		sb.append("lIMITTIME:		" + limitTime + "\n");
		sb.append("iNSERTTIME:		" + insertTime + "\n");

		return sb.toString();
	}

}
