package com.rssl.phizic.logging.settings;

import com.rssl.phizic.logging.push.PushEventType;

import java.io.Serializable;
import java.util.Calendar;

/**
 * ������ ���� �� �������� ��������� �������
 * @author tisov
 * @ created 01.11.13
 * @ $Author$
 * @ $Revision$
 */

public class UserMessageLogRecord implements Serializable
{
	private Long id;
	private Long loginId;
	private Calendar additionDate;
	private MessageType type;
	private String messageId;
	private PushEventType typeCode;

	/**
	 * ������ �������������� ������
	 * @return - ��
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * ������ �������������� ������
	 * @param id - ����� ��
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * ������ ���� �������� ������
	 * @return - ���� ��������
	 */
	public Calendar getAdditionDate()
	{
		return additionDate;
	}

	/**
	 * ������ ���� �������� ������
	 * @param additionDate - ����� ���� ��������
	 */
	public void setAdditionDate(Calendar additionDate)
	{
		this.additionDate = additionDate;
	}

	/**
	 * ������ ���� ���������
	 * @return - ��� ���������
	 */
	public MessageType getType()
	{
		return type;
	}

	/**
	 * ������ ���� ���������
	 * @param type - ����� ��� ���������
	 */
	public void setType(MessageType type)
	{
		this.type = type;
	}

	/**
	 * ������ �������������� ���������
	 * @return - �� ���������
	 */
	public String getMessageId()
	{
		return messageId;
	}

	/**
	 * ������ �������������� ���������
	 * @param messageId - ����� �� ���������
	 */
	public void setMessageId(String messageId)
	{
		this.messageId = messageId;
	}

	public Long getLoginId()
	{
		return loginId;
	}

	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}

	public PushEventType getTypeCode()
	{
		return typeCode;
	}

	/**
	 * ������ ��� ������ ���� �����������
	 * @param typeCode - ����� ���� �����������
	 */
	public void setTypeCode(PushEventType typeCode)
	{
		this.typeCode = typeCode;
	}
}
