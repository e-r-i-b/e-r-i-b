package com.rssl.phizic.business.ermb.writedown;

import com.rssl.phizic.common.types.Period;
import com.rssl.phizic.utils.PhoneNumber;

/**
 * Результат обработки ЕРИБ ответа о списании платы
 * @author Puzikov
 * @ created 06.06.14
 * @ $Author$
 * @ $Revision$
 */

@SuppressWarnings("JavaDoc")
public class WriteDownResult
{
	private boolean successful;             //успешность списания
	private String oldServiceStatus;        //статус услуги до списания
	private String newServiceStatus;        //статус услуги после списания
	private PhoneNumber phoneNumber;        //основной телефон профиля
	private Period writeOffPeriod;          //период, за который произошло списание

	public boolean isSuccessful()
	{
		return successful;
	}

	public void setSuccessful(boolean successful)
	{
		this.successful = successful;
	}

	public String getOldServiceStatus()
	{
		return oldServiceStatus;
	}

	public void setOldServiceStatus(String oldServiceStatus)
	{
		this.oldServiceStatus = oldServiceStatus;
	}

	public String getNewServiceStatus()
	{
		return newServiceStatus;
	}

	public void setNewServiceStatus(String newServiceStatus)
	{
		this.newServiceStatus = newServiceStatus;
	}

	public PhoneNumber getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(PhoneNumber phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public Period getWriteOffPeriod()
	{
		return writeOffPeriod;
	}

	public void setWriteOffPeriod(Period writeOffPeriod)
	{
		this.writeOffPeriod = writeOffPeriod;
	}
}
