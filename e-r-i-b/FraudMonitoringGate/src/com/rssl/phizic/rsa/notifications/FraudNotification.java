package com.rssl.phizic.rsa.notifications;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.notifications.enumeration.FraudNotificationState;
import com.rssl.phizic.rsa.senders.FraudMonitoringSender;

import java.util.Calendar;
import javax.xml.parsers.ParserConfigurationException;

/**
 * ���������� � ����-����������
 *
 * @author khudyakov
 * @ created 16.06.15
 * @ $Author$
 * @ $Revision$
 */
public class FraudNotification
{
	private long id;
	private Calendar creationDate;
	private String requestBody;
	private FraudNotificationState state;

	/**
	 * @param sender - ������ ����-�����������
	 */
	public FraudNotification(FraudMonitoringSender sender) throws ParserConfigurationException, GateLogicException, GateException
	{
		this(sender.getRequestBody());
	}

	/**
	 * @param requestBody - ���� �������
	 */
	public FraudNotification(String requestBody)
	{
		this();
		this.requestBody = requestBody;
	}


	public FraudNotification()
	{
		this.creationDate = Calendar.getInstance();
		this.state = FraudNotificationState.NOT_SENT;
	}

	/**
	 * @return ������������� ����������
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * @return ���� ��������
	 */
	public Calendar getCreationDate()
	{
		return creationDate;
	}

	/**
	 * @param id - ������������� ���������
	 */
	public void setId(long id)
	{
		this.id = id;
	}

	/**
	 * @param creationDate - ���� �������� ����������
	 */
	public void setCreationDate(Calendar creationDate)
	{
		this.creationDate = creationDate;
	}

	/**
	 * @return ������ � xml-��������������� ����
	 */
	public String getRequestBody()
	{
		return requestBody;
	}

	/**
	 * @param requestBody - ������ � xml-��������������� ����
	 */
	public void setRequestBody(String requestBody)
	{
		this.requestBody = requestBody;
	}

	/**
	 * @return ������ ����������
	 */
	public FraudNotificationState getState()
	{
		return state;
	}

	/**
	 * @param state ������ ����������
	 */
	public void setState(FraudNotificationState state)
	{
		this.state = state;
	}
}
