package com.rssl.phizic.common.types.fund;

/**
 * @author osminin
 * @ created 15.09.14
 * @ $Author$
 * @ $Revision$
 *
 *  Статус обработки запроса на сбор средств.
 *  Статусы, помеченные как SYNC забираются на обработку шедулером
 */
public enum FundResponseState
{
	NOT_READ("Не прочитан", ""),    //Статус по умолчанию, не поддерживает операции
	READ("Прочитан", "SetReadStateFundOperation"),
	REJECT("Отклонен", "SetRejectStateFundOperation"),
	SUCCESS("Удовлетворен", "SetSuccessStateFundOperation");

	private String description;
	private String operationKey;

	private FundResponseState(String description, String operationKey)
	{
		this.description = description;
		this.operationKey = operationKey;
	}

	/**
	 * @return описание статуса
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @return ключ операции, которая должна выполняться при переходе в статус
	 */
	public String getOperationKey()
	{
		return operationKey;
	}
}
