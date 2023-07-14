/**
 * User: usachev
 * Date: 11.12.14
 */
package com.rssl.phizic.web.fund;

import com.rssl.phizic.business.fund.sender.FundSenderResponse;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.math.BigDecimal;

public class FundSenderResponseForm extends ActionFormBase
{
	private String id;
	private FundSenderResponse response;
    private BigDecimal accumulatedSum;
	/**
	 * Получение id сущности "Ответ на сбор средств"
	 * @return ID сущности
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * Установление id сущности "Ответ на сбор средств"
	 * @param id ID сущности
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * Получение сущности "Ответ на сбор средств"
	 * @return Сущность "Ответ на сбор средств"
	 */
	public FundSenderResponse getResponse()
	{
		return response;
	}

	/**
	 * Установление сущности "Ответ на сбор средств"
	 * @param response Сущность "Ответ на сбор средств"
	 */
	public void setResponse(FundSenderResponse response)
	{
		this.response = response;
	}

	/**
	 * Получение собранной суммы в рамках запроса на сбор средств
	 * @return Собранная сумма
	 */
	public BigDecimal getAccumulatedSum()
	{
		return accumulatedSum;
	}

	/**
	 * Установка значения текущей собранной суммы
	 * @param accumulatedSum Собранная сумма
	 */
	public void setAccumulatedSum(BigDecimal accumulatedSum)
	{
		this.accumulatedSum = accumulatedSum;
	}
}
