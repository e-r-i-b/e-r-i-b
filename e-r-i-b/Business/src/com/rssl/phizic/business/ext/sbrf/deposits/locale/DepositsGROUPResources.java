package com.rssl.phizic.business.ext.sbrf.deposits.locale;

import com.rssl.phizic.locale.dynamic.resources.LanguageResource;

/**
 * Локалезависимые ресурсы для записи из таблицы QVB_GROUP справочника ЦАС НСИ (группы вкладных продуктов)
 * @author lepihina
 * @ created 05.06.15
 * $Author$
 * $Revision$
 */
public class DepositsGROUPResources extends LanguageResource
{
	// Наименование группы (GR_NAME)
	private String groupName;

	/**
	 * @return наименование группы
	 */
	public String getGroupName()
	{
		return groupName;
	}

	/**
	 * @param groupName наименование группы
	 */
	public void setGroupName(String groupName)
	{
		this.groupName = groupName;
	}
}
