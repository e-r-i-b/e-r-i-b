package com.rssl.phizic.business.deposits;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * Created by IntelliJ IDEA.
 * @author mihaylov
 * created 14.08.2008
 * @ $Author$
 * @ $Revision$
 */
public class DublicateDepositProductNameException extends BusinessLogicException
{
	public DublicateDepositProductNameException()
	{
		super("ƒепозитный продукт с таким именем уже существует");
	}
}
