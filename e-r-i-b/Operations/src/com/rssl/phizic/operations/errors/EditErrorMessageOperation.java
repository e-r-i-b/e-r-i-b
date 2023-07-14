package com.rssl.phizic.operations.errors;

import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.errors.ErrorMessagesService;
import com.rssl.phizic.errors.ErrorMessage;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.ConfigurationCheckedException;

/**
 * @author gladishev
 * @ created 16.11.2007
 * @ $Author$
 * @ $Revision$
 */

public class EditErrorMessageOperation extends OperationBase implements EditEntityOperation
{
	private static final ErrorMessagesService errorMessagesService = new ErrorMessagesService();
	private boolean    isNew;

	ErrorMessage errorMessage;

	public void initialize(Long id) throws BusinessException
	{
		try
		{
			errorMessage = errorMessagesService.findById(id);
		}
		catch(ConfigurationCheckedException ex)
		{
			throw new BusinessException(ex);
		}

		isNew = false;

		if(errorMessage == null)
			throw new BusinessException("Сообщение об ошибке не найдено, ID=" + id);
	}

	public void initializeNew()
	{
		errorMessage = new ErrorMessage();
		isNew = true;
	}

	public boolean isNew()
	{
		return isNew;
	}

	public ErrorMessage getEntity()
	{
		return errorMessage;
	}

	public void save() throws BusinessException
	{
		try
		{
			if(errorMessage.getId() == null)
				errorMessagesService.add(errorMessage);
			else
				errorMessagesService.update(errorMessage);
		}
		catch(ConfigurationCheckedException ex)
		{
			throw new BusinessException(ex);
		}
	}
}
