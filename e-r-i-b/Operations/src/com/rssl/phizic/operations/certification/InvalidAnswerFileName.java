package com.rssl.phizic.operations.certification;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author Omeliyanchuk
 * @ created 30.03.2007
 * @ $Author$
 * @ $Revision$
 */

public class InvalidAnswerFileName extends BusinessLogicException
{
	InvalidAnswerFileName()
	{
		super("Имя файла ответа на запрос не соответствует запросу");
	}
}
