package com.rssl.phizic.logging.operations;

/**
 * @author vagin
 * @ created 29.10.2012
 * @ $Author$
 * @ $Revision$
 * Статусы операций в ЦСА
 */
public enum CSAOperationsState
{
	NEW("новая неподтвержденная"),
	CONFIRMED("подтвержденная"),
	EXECUTED("исполненная"),
	REFUSED("отказанная");

	private String description;

	CSAOperationsState(String str)
	{
		description = str;
	}

	public String getDescription()
	{
		return description;
	}
}
