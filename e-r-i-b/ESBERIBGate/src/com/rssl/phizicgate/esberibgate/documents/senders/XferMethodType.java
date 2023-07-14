package com.rssl.phizicgate.esberibgate.documents.senders;

/**
 * Переводы в различные типы банков
 * (внутри тербанка, другой тербанк, другой комерческий банк)
 *
 * @author hudyakov
 * @ created 21.09.2010
 * @ $Author$
 * @ $Revision$
 */
public enum XferMethodType
{
	OUR_TERBANK("0"),         //внутри тербанка
	EXTERNAL_TERBANK("1"),    //другой тербанк
	EXTERNAL_BANK("2");       //другой ком. банк

	XferMethodType(String value)
	{
		this.value = value;
	}

	private final String value;

	public String toValue()
	{
		return value;
	}
}
