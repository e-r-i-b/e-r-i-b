package com.rssl.phizic.common.types.fund;

/**
 * @author osminin
 * @ created 15.09.14
 * @ $Author$
 * @ $Revision$
 *
 *  Тип причины закрытия запроса на сбор средств
 */
public enum ClosedReasonType
{
	FUND_COMPLETED("Закрыт по причине сбора нужной суммы."),
	FUND_TIMEOUT("Срок жизни запроса истек."),
	FUND_CLOSED("Закрыт по инициативе инициатора запроса.");

	private String description;

	private ClosedReasonType(String description)
	{
		this.description = description;
	}

	/**
	 * @return Описание типа
	 */
	public String getDescription()
	{
		return description;
	}
}
