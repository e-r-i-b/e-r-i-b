package com.rssl.phizic.business.dictionaries.tariffPlan.locale;

import com.rssl.phizic.locale.dynamic.resources.LanguageResource;

/**
 * ��������������� ��������� ��� ��������  ��������� ��������� ����� �������
 * @author komarov
 * @ created 05.06.2015
 * @ $Author$
 * @ $Revision$
 */
public class TariffPlanConfigResources extends LanguageResource
{
	private String privilegedRateMessage;


	/**
	 * @return ���������, ������������ ��� ������������� ��������� �����
	 */
	public String getPrivilegedRateMessage()
	{
		return privilegedRateMessage;
	}

	/**
	 * @param privilegedRateMessage ���������, ������������ ��� ������������� ��������� �����
	 */
	public void setPrivilegedRateMessage(String privilegedRateMessage)
	{
		this.privilegedRateMessage = privilegedRateMessage;
	}
}
