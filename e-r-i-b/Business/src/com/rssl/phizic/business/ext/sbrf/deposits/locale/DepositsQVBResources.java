package com.rssl.phizic.business.ext.sbrf.deposits.locale;

import com.rssl.phizic.locale.dynamic.resources.LanguageResource;

/**
 * Локалезависимые ресурсы для записи из таблицы qvb справочника ЦАС НСИ (основная информация по видам и подвидам вкладов)
 * @author lepihina
 * @ created 05.06.15
 * $Author$
 * $Revision$
 */
public class DepositsQVBResources extends LanguageResource
{
	// Наименование вклада (QDN)
	private String fullName;

	/**
	 * @return наименование вклада
	 */
	public String getFullName()
	{
		return fullName;
	}

	/**
	 * @param fullName наименование вклада
	 */
	public void setFullName(String fullName)
	{
		this.fullName = fullName;
	}
}
