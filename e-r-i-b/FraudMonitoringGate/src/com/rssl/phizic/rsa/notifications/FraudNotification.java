package com.rssl.phizic.rsa.notifications;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.notifications.enumeration.FraudNotificationState;
import com.rssl.phizic.rsa.senders.FraudMonitoringSender;

import java.util.Calendar;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Оповещение о фрод-транзакции
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
	 * @param sender - сендер фрод-мониторинга
	 */
	public FraudNotification(FraudMonitoringSender sender) throws ParserConfigurationException, GateLogicException, GateException
	{
		this(sender.getRequestBody());
	}

	/**
	 * @param requestBody - тело запроса
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
	 * @return идентификатор оповещения
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * @return дата создания
	 */
	public Calendar getCreationDate()
	{
		return creationDate;
	}

	/**
	 * @param id - идентификатор оповещени
	 */
	public void setId(long id)
	{
		this.id = id;
	}

	/**
	 * @param creationDate - дата создания оповещения
	 */
	public void setCreationDate(Calendar creationDate)
	{
		this.creationDate = creationDate;
	}

	/**
	 * @return запрос в xml-сериализованном виде
	 */
	public String getRequestBody()
	{
		return requestBody;
	}

	/**
	 * @param requestBody - запрос в xml-сериализованном виде
	 */
	public void setRequestBody(String requestBody)
	{
		this.requestBody = requestBody;
	}

	/**
	 * @return статус оповещения
	 */
	public FraudNotificationState getState()
	{
		return state;
	}

	/**
	 * @param state статус оповещения
	 */
	public void setState(FraudNotificationState state)
	{
		this.state = state;
	}
}
