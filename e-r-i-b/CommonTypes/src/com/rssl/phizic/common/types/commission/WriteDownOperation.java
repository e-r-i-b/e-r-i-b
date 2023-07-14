package com.rssl.phizic.common.types.commission;

import com.rssl.phizic.common.types.Money;

import java.math.BigDecimal;

/**
 * @author vagin
 * @ created 01.10.13
 * @ $Author$
 * @ $Revision$
 * ћикроопераци€ дл€ платежа(списание/причисление).
 */
public class WriteDownOperation
{
	private String operationName;  //краткое наименование
	private Money curAmount;       //сумма
	private String turnOver;       //признак причислени€/списани€(RECEIPT/CHARGE).
	private static final String ATTR_DELIMITER = "|";  //разделитель параметров в строковом представлении объекта.

	public String getOperationName()
	{
		return operationName;
	}

	public void setOperationName(String operationName)
	{
		this.operationName = operationName;
	}

	public Money getCurAmount()
	{
		return curAmount;
	}

	public void setCurAmount(Money curAmount)
	{
		this.curAmount = curAmount;
	}

	public String getTurnOver()
	{
		return turnOver;
	}

	public void setTurnOver(String turnOver)
	{
		this.turnOver = turnOver;
	}

	/**
	 * ѕолучние строкового представлени€ операции
	 * @return строка, гед параметры записаны через '|' в пор€дке: Ќазвание операции, сумма, код валюты суммы, признак списани€/причистелни€(RECEIPT/CHARGE)
	 */
	public String asString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(operationName).append(ATTR_DELIMITER).append(curAmount.getDecimal())
				.append(ATTR_DELIMITER).append(curAmount.getCurrency().getCode())
				.append(ATTR_DELIMITER).append(turnOver);
		return sb.toString();
	}
}
