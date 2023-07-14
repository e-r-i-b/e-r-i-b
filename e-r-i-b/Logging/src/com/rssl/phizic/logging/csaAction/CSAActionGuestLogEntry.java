package com.rssl.phizic.logging.csaAction;

/**
 * @author tisov
 * @ created 21.07.15
 * @ $Author$
 * @ $Revision$
 * запись в лог о входе гостя
 */
public class CSAActionGuestLogEntry extends CSAActionLogEntryBase
{
	private String phoneNumber;

	/**
	 * Номер телефона
	 * @return
	 */
	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	/**
	 * Номер телефона
	 * @param phoneNumber
	 */
	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}
}
