package com.rssl.phizic.web.common.exception;

import com.rssl.phizic.business.BusinessLogicException;
import org.apache.struts.action.ActionMessages;

/**
 * @author akrenev
 * @ created 19.03.2013
 * @ $Author$
 * @ $Revision$
 *
 * Исключение ошибки валидации полей форм-процессором
 */

public class FormProcessorException extends BusinessLogicException
{
	private final ActionMessages errors;

	/**
	 * конструктор
	 * @param errors ошибки
	 */
	public FormProcessorException(ActionMessages errors)
	{
		super("Ошибка валидации полей формы.");
		this.errors = errors;
	}

	/**
	 * @return ошибки валидации
	 */
	public ActionMessages getErrors()
	{
		return errors;
	}
}
