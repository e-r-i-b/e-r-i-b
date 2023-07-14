package com.rssl.phizic.test.ermb.newclient.accepted;

/**
 * @author Gulov
 * @ created 23.08.13
 * @ $Author$
 * @ $Revision$
 */

import com.rssl.phizic.common.types.AbstractEntity;

import java.util.Calendar;

/**
 * Принятые ОСС новые регистрации
 */
public class ErmbAcceptedRegistration extends AbstractEntity
{
	/**
	 * Номер телефона
	 */
	private String phone;

	/**
	 * Дата подключения клиента к ЕРМБ
	 */
	private Calendar connectedDate;

	public ErmbAcceptedRegistration()
	{
	}

	public ErmbAcceptedRegistration(String phone, Calendar connectedDate)
	{
		this.phone = phone;
		this.connectedDate = connectedDate;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public Calendar getConnectedDate()
	{
		return connectedDate;
	}

	public void setConnectedDate(Calendar connectedDate)
	{
		this.connectedDate = connectedDate;
	}
}
