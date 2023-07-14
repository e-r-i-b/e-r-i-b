package com.rssl.phizic.business.ermb.registration;

import com.rssl.phizic.common.types.AbstractEntity;
import com.rssl.phizic.utils.PhoneNumber;

import java.util.Calendar;

/**
 * @author Gulov
 * @ created 09.08.13
 * @ $Author$
 * @ $Revision$
 */

/**
 * История подключений к ЕРМБ
 */
public class ErmbRegistrationEvent extends AbstractEntity
{
	/**
	 * Идентификатор профиля
	 */
	private Long profileId;

	/**
	 * Номер телефона
	 */
	private PhoneNumber phone;

	/**
	 * Дата подключения
	 */
	private Calendar connectionDate;

	public ErmbRegistrationEvent()
	{
	}

	public ErmbRegistrationEvent(Long profileId, String phone, Calendar connectionDate)
	{
		this.profileId = profileId;
		this.phone = PhoneNumber.fromString(phone);
		this.connectionDate = connectionDate;
	}

	public Long getProfileId()
	{
		return profileId;
	}

	public void setProfileId(Long profileId)
	{
		this.profileId = profileId;
	}

	public PhoneNumber getPhone()
	{
		return phone;
	}

	public void setPhone(PhoneNumber phone)
	{
		this.phone = phone;
	}

	public Calendar getConnectionDate()
	{
		return connectionDate;
	}

	public void setConnectionDate(Calendar connectionDate)
	{
		this.connectionDate = connectionDate;
	}
}
