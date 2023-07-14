package com.rssl.phizic.business.dictionaries.finances;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author lepihina
 * @ created 16.09.13
 * @ $Author$
 * @ $Revision$
 */
public class ExistOperationsInCardOperationCategoryException extends BusinessLogicException
{
	private static final String MESSAGE = "¬ удал€емой категории есть операции.";

	/**
	 * ѕопытка удалить категорию, в которой есть операции
	 * @param cause - причина
	 */
	public ExistOperationsInCardOperationCategoryException(Throwable cause)
	{
		super(MESSAGE, cause);
	}
}
