package com.rssl.phizic.logging.csaAction;

/**
 * @author tisov
 * @ created 21.07.15
 * @ $Author$
 * @ $Revision$
 * ������ � ��� � ����� �����
 */
public class CSAActionGuestLogEntry extends CSAActionLogEntryBase
{
	private String phoneNumber;

	/**
	 * ����� ��������
	 * @return
	 */
	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	/**
	 * ����� ��������
	 * @param phoneNumber
	 */
	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}
}
