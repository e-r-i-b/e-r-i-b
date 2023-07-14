package com.rssl.phizic.business.ext.sbrf.technobreaks.event;

import com.rssl.phizic.events.Event;

/**
 * @author akrenev
 * @ created 20.06.2014
 * @ $Author$
 * @ $Revision$
 *
 * ������� ��������� ������ ���. ��������� � �������
 */

public class UpdateTechnoBreaksEvent implements Event
{
	private final String externalSystemCode;

	/**
	 * �����������
	 * @param externalSystemCode ��� ������� ������� ��� ������� ��������� ������ ���. ���������
	 */
	public UpdateTechnoBreaksEvent(String externalSystemCode)
	{
		this.externalSystemCode = externalSystemCode;
	}

	/**
	 * @return ��� ������� ������� ��� ������� ��������� ������ ���. ���������
	 */
	public String getExternalSystemCode()
	{
		return externalSystemCode;
	}

	public String getStringForLog()
	{
		return getClass().getSimpleName() + " ��� " + getExternalSystemCode();
	}
}
