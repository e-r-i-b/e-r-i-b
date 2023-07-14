package com.rssl.phizic.gate.einvoicing;

/**
 * Состояние возвратов.
 *
 * @author bogdanov
 * @ created 10.02.14
 * @ $Author$
 * @ $Revision$
 */

public enum RecallState
{
	CREATED ("принят"),
	ERROR ("ошибка исполнения"),
	EXECUTED ("оплачен"),
	REFUSED ("отказан");

	final String description;

	RecallState(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}
}
