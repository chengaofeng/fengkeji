/* ====================================================================
 * $Source:  $
 * $Author:  $
 * $Revision:  $
 * $Date:  $
 * ====================================================================
 *  File  			ScratchCard.java
 *  Description
 *  History      	2014/07/30
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

public class ScratchCard implements BaseModule{

	/**
     * ScratchCard.java
     */
    private static final long serialVersionUID = -5999544953259805109L;


    /**
	 * ȱʡ�Ĺ��캯��
	 */
	public ScratchCard() {
	}


	/**
	 * scratchCardId
	 */
	private String scratchCardId = null;

	/**
	 * scratchCardMoney
	 */
	private Integer scratchCardMoney = null;

	/**
	 * scratchCardNum
	 */
	private Integer scratchCardNum = null;

	/**
	 * scratchCardLast
	 */
	private Integer scratchCardLast = null;

	/**
	 * scratchCardContent
	 */
	private String scratchCardContent = null;

	/**
	 * scratchCardType
	 */
	private String scratchCardType = null;

	/**
	 * scratchCardProb
	 */
	private Integer scratchCardProb = null;

	/**
	 * limitTime
	 */
	private Timestamp limitTime = null;

	/**
	 * insertTime
	 */
	private Timestamp insertTime = null;


	/**
	 * ȡ��scratchCardId
	 *
	 * @return scratchCardId
	 */
	public String getScratchCardId() {
		return this.scratchCardId;
	}

	/**
	 * ����scratchCardId
	 *
	 * @param �µ�scratchCardId
	 */
	public void setScratchCardId(String scratchCardId) {
		this.scratchCardId = scratchCardId;
	}

	/**
	 * ȡ��scratchCardMoney
	 *
	 * @return scratchCardMoney
	 */
	public Integer getScratchCardMoney() {
		return this.scratchCardMoney;
	}

	/**
	 * ����scratchCardMoney
	 *
	 * @param �µ�scratchCardMoney
	 */
	public void setScratchCardMoney(Integer scratchCardMoney) {
		this.scratchCardMoney = scratchCardMoney;
	}

	/**
	 * ȡ��scratchCardNum
	 *
	 * @return scratchCardNum
	 */
	public Integer getScratchCardNum() {
		return this.scratchCardNum;
	}

	/**
	 * ����scratchCardNum
	 *
	 * @param �µ�scratchCardNum
	 */
	public void setScratchCardNum(Integer scratchCardNum) {
		this.scratchCardNum = scratchCardNum;
	}

	/**
	 * ȡ��scratchCardLast
	 *
	 * @return scratchCardLast
	 */
	public Integer getScratchCardLast() {
		return this.scratchCardLast;
	}

	/**
	 * ����scratchCardLast
	 *
	 * @param �µ�scratchCardLast
	 */
	public void setScratchCardLast(Integer scratchCardLast) {
		this.scratchCardLast = scratchCardLast;
	}

	/**
	 * ȡ��scratchCardContent
	 *
	 * @return scratchCardContent
	 */
	public String getScratchCardContent() {
		return this.scratchCardContent;
	}

	/**
	 * ����scratchCardContent
	 *
	 * @param �µ�scratchCardContent
	 */
	public void setScratchCardContent(String scratchCardContent) {
		this.scratchCardContent = scratchCardContent;
	}

	/**
	 * ȡ��scratchCardType
	 *
	 * @return scratchCardType
	 */
	public String getScratchCardType() {
		return this.scratchCardType;
	}

	/**
	 * ����scratchCardType
	 *
	 * @param �µ�scratchCardType
	 */
	public void setScratchCardType(String scratchCardType) {
		this.scratchCardType = scratchCardType;
	}

	/**
	 * ȡ��scratchCardProb
	 *
	 * @return scratchCardProb
	 */
	public Integer getScratchCardProb() {
		return this.scratchCardProb;
	}

	/**
	 * ����scratchCardProb
	 *
	 * @param �µ�scratchCardProb
	 */
	public void setScratchCardProb(Integer scratchCardProb) {
		this.scratchCardProb = scratchCardProb;
	}

	/**
	 * ȡ��limitTime
	 *
	 * @return limitTime
	 */
	public Timestamp getLimitTime() {
		return this.limitTime;
	}

	/**
	 * ����limitTime
	 *
	 * @param �µ�limitTime
	 */
	public void setLimitTime(Timestamp limitTime) {
		this.limitTime = limitTime;
	}

	/**
	 * ȡ��insertTime
	 *
	 * @return insertTime
	 */
	public Timestamp getInsertTime() {
		return this.insertTime;
	}

	/**
	 * ����insertTime
	 *
	 * @param �µ�insertTime
	 */
	public void setInsertTime(Timestamp insertTime) {
		this.insertTime = insertTime;
	}


	/**
	 * �����õķ����� ���Խ������ֶε��������
	 *
	 */
	@Override
    public String toString() {
		StringBuffer sb = new StringBuffer();


		sb.append("scratchCardId:		" + scratchCardId + "\n");
		sb.append("scratchCardMoney:		" + scratchCardMoney + "\n");
		sb.append("scratchCardNum:		" + scratchCardNum + "\n");
		sb.append("scratchCardLast:		" + scratchCardLast + "\n");
		sb.append("scratchCardContent:		" + scratchCardContent + "\n");
		sb.append("scratchCardType:		" + scratchCardType + "\n");
		sb.append("scratchCardProb:		" + scratchCardProb + "\n");
		sb.append("limitTime:		" + limitTime + "\n");
		sb.append("insertTime:		" + insertTime + "\n");

		return sb.toString();
	}

}
