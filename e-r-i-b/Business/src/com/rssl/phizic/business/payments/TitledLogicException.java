package com.rssl.phizic.business.payments;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author vagin
 * @ created 28.01.14
 * @ $Author$
 * @ $Revision$
 * Исключение отображаемое клиенту. Содержит заголовок для отображения(стандартное "Обратите внимание")
 */
public class TitledLogicException extends BusinessLogicException
{
	private final String title;

	/**
	 * @param message - текст ошибки.
	 * @param title - заголовок ошибки
	 */
	public TitledLogicException(String message, String title)
	{
		super(message);
		this.title = title;
	}

	public String getMessage()
	{
		return "<title>"+title+"</title>"+super.getMessage();
	}
}
