package com.rssl.phizic.common.types.fund;

/**
 * @author osminin
 * @ created 15.09.14
 * @ $Author$
 * @ $Revision$
 *
 *  Статус запроса на сбор средств
 *  Статусы, помеченные как SYNC забираются на обработку шедулером
 */
public enum FundRequestState
{
	OPEN("Открыт"),
	SYNC_OPEN("Открыт"),
	CLOSED("Закрыт"),
	SYNC_CLOSED("Закрыт");

	private String description;

	private FundRequestState(String description)
	{
		this.description = description;
	}

	/**
	 * @return Описание статуса
	 */
	public String getDescription()
	{
		return description;
	}
}
