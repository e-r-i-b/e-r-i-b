package com.rssl.phizic.messaging.ermb;

import com.rssl.phizic.common.types.UUID;
import com.rssl.phizic.common.types.VersionNumber;
import com.rssl.phizic.common.types.annotation.PlainOldJavaObject;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.jaxb.CalendarDateTimeAdapter;
import com.rssl.phizic.utils.jaxb.MobileInternationalPhoneNumberXmlAdapter;
import com.rssl.phizic.utils.jaxb.UUIDXmlAdapter;
import com.rssl.phizic.utils.jaxb.VersionNumberXmlAdapter;

import java.util.Calendar;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author Erkin
 * @ created 24.03.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Сообщение для передачи СМС клиенту (без проверки IMSI)
 * Маршрут сообщения: ЕРИБ -> СОС
 */
@PlainOldJavaObject
@SuppressWarnings("JavaDoc")
@XmlType(name = "SendSMSRqType")
@XmlRootElement(name = "SendSMSRq")
@XmlAccessorType(XmlAccessType.NONE)
public class SendSmsRequest
{
	/**
	 * Тип сообщения
	 */
	public static final String TYPENAME = "SendSMSRq";

	/**
	 * Текущая версия сообщения
	 */
	public static final VersionNumber VERSION = new VersionNumber(1, 0);

	/**
	 * Версия сообщения [1]
	 * (возрастает в случае изменения структуры сообщения)
	 */
	@XmlElement(name = "rqVersion", required = true)
	@XmlJavaTypeAdapter(VersionNumberXmlAdapter.class)
	private VersionNumber rqVersion;
	/**
	 * Идентификатор запроса [1]
	 */
	@XmlElement(name = "rqUID", required = true)
	@XmlJavaTypeAdapter(UUIDXmlAdapter.class)
	private UUID rqUID;
	/**
	 * Дата и время запроса [1]
	 */
	@XmlElement(name = "rqTime", required = true)
	@XmlJavaTypeAdapter(CalendarDateTimeAdapter.class)
	private Calendar rqTime;

	/**
	 * Номер телефона [1]
	 */
	@XmlElement(name = "phone", required = true)
	@XmlJavaTypeAdapter(MobileInternationalPhoneNumberXmlAdapter.class)
	private PhoneNumber phone;

	/**
	 * Текст сообщения [1]
	 */
	@XmlElement(name = "text", required = true)
	private String text;

	/**
	 * Приоритет сообщения [1]
	 * (от 0 до 9, 0-самый высокий, 9 - самый низкий)
	 */
	@XmlElement(name = "priority", required = true)
	private int priority;

	/**
	 * Идентификатор сессии ЕРИБ [0-1]
	 * В данный момент никогда не используется
	 */
	@XmlElement(name = "eribSID", required = false)
	@XmlJavaTypeAdapter(UUIDXmlAdapter.class)
	private UUID eribSID;

	/**
	 * Идентификатор запроса, в контексте обработки которого отправляется сообщение [0-1]
	 * Должен обязательно присутствовать в ответах на запросы.
	 */
	@XmlElement(name = "ermbCorrelationID", required = false)
	@XmlJavaTypeAdapter(UUIDXmlAdapter.class)
	private UUID ermbCorrelationID;

	///////////////////////////////////////////////////////////////////////////

	public VersionNumber getRqVersion()
	{
		return rqVersion;
	}

	public void setRqVersion(VersionNumber rqVersion)
	{
		this.rqVersion = rqVersion;
	}

	public UUID getRqUID()
	{
		return rqUID;
	}

	public void setRqUID(UUID rqUID)
	{
		this.rqUID = rqUID;
	}

	public Calendar getRqTime()
	{
		return rqTime;
	}

	public void setRqTime(Calendar rqTime)
	{
		this.rqTime = rqTime;
	}

	public PhoneNumber getPhone()
	{
		return phone;
	}

	public void setPhone(PhoneNumber phone)
	{
		this.phone = phone;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public int getPriority()
	{
		return priority;
	}

	public void setPriority(int priority)
	{
		this.priority = priority;
	}

	@SuppressWarnings("UnusedDeclaration")
	public UUID getEribSID()
	{
		return eribSID;
	}

	@SuppressWarnings("UnusedDeclaration")
	public void setEribSID(UUID eribSID)
	{
		this.eribSID = eribSID;
	}

	public UUID getErmbCorrelationID()
	{
		return ermbCorrelationID;
	}

	public void setErmbCorrelationID(UUID ermbCorrelationID)
	{
		this.ermbCorrelationID = ermbCorrelationID;
	}

	public String getRequestType()
	{
		return TYPENAME;
	}

	public UUID getRequestUID()
	{
		return rqUID;
	}
}
