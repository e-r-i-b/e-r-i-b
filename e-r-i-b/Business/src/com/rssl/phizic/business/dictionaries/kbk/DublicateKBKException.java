package com.rssl.phizic.business.dictionaries.kbk;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author akrenev
 * @ created 14.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class DublicateKBKException extends BusinessLogicException
{
	/**
	 *  Исключение, бросаемое при сохранении кбк в случае нарушения условия уникальности code
	 */
	public DublicateKBKException()
	{
		super("КБК с таким кодом уже существует");
	}
}
