/* ====================================================================
 * $Source:  $
 * $Author:  $
 * $Revision:  $
 * $Date:  $
 * ====================================================================
 *  File  			EventInf.java
 *  Description    	EventInf
 *  History      	2014/07/23
 *           Copyright 2002 Co. All Rights Reserved.
 * ====================================================================
 */
package org.fengkeji.weixin.entity;

import java.sql.Timestamp;

import org.fengkeji.weixin.entity.base.BaseModule;

/**
 * EventInf
 *
 * @author
 * @version    1.0
 */

public class EventInf implements BaseModule{

	/**
     * EventInf.java
     */
    private static final long serialVersionUID = 2402928845926760803L;

    /**
	 * ȱʡ�Ĺ��캯��
	 */
	public EventInf() {
	}


	/**
	 * eVENT_ID
	 */
	private final Integer eventId = null;

	/**
	 * eVENT_CONTENT
	 */
	private final String eventContent = null;

	/**
	 * eVENT_TYPE
	 */
	private final String eventType = null;

	/**
	 * eVENT_DATE
	 */
	private final Timestamp eventDate = null;

	/**
	 * ������
	 */
	private final Timestamp timeLimit = null;

	/**
     * @return the eventId
     */
    public Integer getEventId() {
        return eventId;
    }

    /**
     * @return the eventContent
     */
    public String getEventContent() {
        return eventContent;
    }

    /**
     * @return the eventType
     */
    public String getEventType() {
        return eventType;
    }

    /**
     * @return the eventDate
     */
    public Timestamp getEventDate() {
        return eventDate;
    }

    /**
     * @return the timeLimit
     */
    public Timestamp getTimeLimit() {
        return timeLimit;
    }

    /**
	 * �����õķ����� ���Խ������ֶε��������
	 *
	 */
	@Override
    public String toString() {
		StringBuffer sb = new StringBuffer();


		sb.append("eVENT_ID:		" + eventId + "\n");
		sb.append("eVENT_CONTENT:		" + eventContent + "\n");
		sb.append("eVENT_TYPE:		" + eventType + "\n");
		sb.append("eVENT_DATE:		" + eventDate + "\n");
		sb.append("tIME_LIMIT:		" + timeLimit + "\n");

		return sb.toString();
	}

}
