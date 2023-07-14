package com.rssl.phizic.web.client.favourite;

/**
 * @author osminin
 * @ created 28.10.14
 * @ $Author$
 * @ $Revision$
 *
 * Форма списка активных шаблонов, пригодных для создания напоминаний
 */
public class ListTemplatesForRemindersForm extends ListTemplatesForm
{
	private String extractId;

	/**
	 * @return идентификатор дня детальной выписки календаря
	 */
	public String getExtractId()
	{
		return extractId;
	}

	/**
	 * @param extractId идентификатор дня детальной выписки календаря
	 */
	public void setExtractId(String extractId)
	{
		this.extractId = extractId;
	}
}
