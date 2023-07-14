package com.rssl.phizic.business.limits;

/**
 * @author basharin
 * @ created 03.08.2012
 * @ $Author$
 * @ $Revision$
 * тип действия при превышении лимита
 */

public enum OperationType
{
	IMPOSSIBLE_PERFORM_OPERATION("Невозможно выполнить операцию"),              //невозможно выполнить операцию
	NEED_ADDITIONAL_CONFIRN("Подтверждать в ЕРКЦ"),                             //требуется дополнительное подтверждение
	READ_SIM("Выполнить проверку на смену SIM-карты");                          //перечитать sim-карту

	private String description;

	OperationType(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}
}
