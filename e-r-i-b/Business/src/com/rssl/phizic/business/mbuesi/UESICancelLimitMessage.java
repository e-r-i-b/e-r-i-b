package com.rssl.phizic.business.mbuesi;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.gate.mobilebank.UESIMessage;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Сохраненное сообщение UESI
 * @author Pankin
 * @ created 21.01.15
 * @ $Author$
 * @ $Revision$
 */
public class UESICancelLimitMessage
{
	private Long id;
	private String externalId;
	private Calendar creationDate;
	private String phone;
	private Calendar eventDateTime;
	private State state;

	/**
	 * ctor
	 * @param message сообщение UESI
	 */
	public UESICancelLimitMessage(UESIMessage message) throws BusinessException
	{
		creationDate = Calendar.getInstance();
		state = State.NEW;

		try
		{
			Element messageElement = XmlHelper.parse(message.getMessageText()).getDocumentElement();
			phone = XmlHelper.getSimpleElementValue(messageElement, "phone");
			String eventTime = XmlHelper.getSimpleElementValue(messageElement, "event_time");

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			eventDateTime = Calendar.getInstance();
			eventDateTime.setTime(sdf.parse(eventTime));
		}
		catch (Exception e)
		{
			throw new BusinessException("Ошибка при разборе сообщения унифицированного интерфейса МБК");
		}
	}

	/**
	 * ctor
	 */
	public UESICancelLimitMessage()
	{}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getExternalId()
	{
		return externalId;
	}

	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}

	public Calendar getCreationDate()
	{
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate)
	{
		this.creationDate = creationDate;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public Calendar getEventDateTime()
	{
		return eventDateTime;
	}

	public void setEventDateTime(Calendar eventDateTime)
	{
		this.eventDateTime = eventDateTime;
	}

	public State getState()
	{
		return state;
	}

	public void setState(State state)
	{
		this.state = state;
	}
}
