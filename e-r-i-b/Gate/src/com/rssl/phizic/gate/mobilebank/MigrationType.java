package com.rssl.phizic.gate.mobilebank;

/**
 * Тип миграции
 * @author Puzikov
 * @ created 18.02.14
 * @ $Author$
 * @ $Revision$
 */

public enum MigrationType
{
	LIST("S"),          //списковая миграция
	PILOT("V"),         //миграция из пилотного ВСП
	ON_THE_FLY("F");    //миграция на лету при фильтрации ФПП

	private String code;

	private MigrationType(String code)
	{
		this.code = code;
	}

	public String getCode()
	{
		return code;
	}
}
