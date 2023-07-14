package com.rssl.phizic.common.types.external.systems;

/**
 * Тип внешней системы, которая может быть приостановлена в автоматическом режиме
 * @author Pankin
 * @ created 22.11.2012
 * @ $Author$
 * @ $Revision$
 */

public enum AutoStopSystemType
{
	CARD("Внешние системы для получения карт"),
	COD("Внешние системы для получения счетов"),
	LOAN("Внешние системы для получения счетов"),
	ESB("КСШ"),
	MBK("МБК");

	private String description;

	AutoStopSystemType(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}
}
