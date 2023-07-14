package com.rssl.phizic.business.dictionaries.tariffPlan.locale;

import com.rssl.phizic.locale.dynamic.resources.LanguageResource;

/**
 * Локалезависимые текстовки для сущности  Настройки тарифного плана клиента
 * @author komarov
 * @ created 05.06.2015
 * @ $Author$
 * @ $Revision$
 */
public class TariffPlanConfigResources extends LanguageResource
{
	private String privilegedRateMessage;


	/**
	 * @return Сообщение, отображаемое при использовании льготного курса
	 */
	public String getPrivilegedRateMessage()
	{
		return privilegedRateMessage;
	}

	/**
	 * @param privilegedRateMessage Сообщение, отображаемое при использовании льготного курса
	 */
	public void setPrivilegedRateMessage(String privilegedRateMessage)
	{
		this.privilegedRateMessage = privilegedRateMessage;
	}
}
