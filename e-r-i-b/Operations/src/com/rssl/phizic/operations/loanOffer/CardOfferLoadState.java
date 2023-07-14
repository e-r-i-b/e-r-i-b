package com.rssl.phizic.operations.loanOffer;

/**
 * @author Moshenko
 * @ created 24.02.2014
 * @ $Author$
 * @ $Revision$
 */
public enum CardOfferLoadState
{
	OK("Успешная загрузка"),
	PARTLY_ERROR("Есть не загруженные записи"),
	LOAD_ERROR("Ошибка загрузки"),
	FILE_NOT_FOUND("Файл загрузки не найден"),
	BD_ERROR("Ошибка в процессе записи в БД");

	private String returnMsg;

	CardOfferLoadState(String returnMsg)
	{
		this.returnMsg = returnMsg;
	}

	public String toValue()
	{
		return returnMsg;
	}

	public String getValue()
	{
		return returnMsg;
	}
}
