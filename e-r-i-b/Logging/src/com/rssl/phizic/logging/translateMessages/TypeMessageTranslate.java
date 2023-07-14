package com.rssl.phizic.logging.translateMessages;

/**
 * @author Mescheryakova
 * @ created 27.07.2011
 * @ $Author$
 * @ $Revision$
 * Доступные типы сообщений: R - запрос, A - ответ
 */

public enum TypeMessageTranslate
{
	R("REQUEST"),   // запрос
	A("ANSWER");   //ответ

	private String value;
	TypeMessageTranslate(String value)
	{
		this.value = value;
	}

	public String toString()
	{
		return this.value;
	}

}
