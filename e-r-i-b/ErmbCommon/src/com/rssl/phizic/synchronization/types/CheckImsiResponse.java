package com.rssl.phizic.synchronization.types;

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
 * Сообщение для передачи результатов проверки IMSI
 * Маршрут сообщения: СОС -> ЕРИБ
 */

@PlainOldJavaObject
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CheckIMSIRsType", propOrder = {
    "rqVersion",
    "rqUID",
    "rqTime",
    "phone",
    "resultCode",
	"correlationID"
})
@XmlRootElement(name = "CheckIMSIRs")

public class CheckImsiResponse
{
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
	 * Результат проверки IMSI [1]
	 */
	@XmlElement(name = "resultCode", required = true)
	private String resultCode;

	/**
	 * Идентификатор запроса, в контексте обработки которого отправляется сообщение [1]
	 * Должен обязательно присутствовать в ответах на запросы.
	 */
	@XmlElement(name = "correlationID", required = true)
	@XmlJavaTypeAdapter(UUIDXmlAdapter.class)
	private UUID correlationID;

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

	public String getResultCode()
	{
		return resultCode;
	}

	public void setResultCode(String resultCode)
	{
		this.resultCode = resultCode;
	}

	public UUID getCorrelationID()
	{
		return correlationID;
	}

	public void setCorrelationID(UUID correlationID)
	{
		this.correlationID = correlationID;
	}
}
