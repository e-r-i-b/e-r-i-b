package com.rssl.phizic.operations.errors;

import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.errors.ErrorMessagesService;
import com.rssl.phizic.errors.ErrorMessage;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.operations.Transactional;

import com.rssl.phizic.ConfigurationCheckedException;

/**
 * @author gladishev
 * @ created 16.11.2007
 * @ $Author$
 * @ $Revision$
 */

public class RemoveErrorMessageOperation extends OperationBase implements RemoveEntityOperation
{
	private static final ErrorMessagesService errorMessagesService = new ErrorMessagesService();

	private ErrorMessage errorMessage;

	/**
	 * Инициализация
	 * @param errorMessageId ID сообщения для удаления
	 */
	public void initialize(Long errorMessageId) throws BusinessException
	{
		try
		{
			errorMessage = errorMessagesService.findById(errorMessageId);
		}
		catch(ConfigurationCheckedException ex)
		{
			throw new BusinessException(ex);
		}

		if (errorMessage == null)
			throw new BusinessException("Сообщение об ошибке не найдено, ID=" + errorMessageId);
	}

	/**
	 * @return ID удаляемого cообщения
	 */
	public Long getEntity()
	{
		return errorMessage.getId();
	}

	/**
	 * Удалить ErrorMessage
	 */
	@Transactional
	public void remove() throws BusinessException
	{
		try
		{
			errorMessagesService.remove(errorMessage);
		}
		catch(ConfigurationCheckedException ex)
		{
			throw new BusinessException(ex);
		}
	}
}
