package com.rssl.phizic.web.validators;

import com.rssl.phizic.business.BusinessException;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

/**
 * @author akrenev
 * @ created 23.01.2013
 * @ $Author$
 * @ $Revision$
 *
 * Составной валидатор файлов
 */

public class CompositeFileValidator implements FileValidator
{
	private FileValidator[] validators;

	public CompositeFileValidator(FileValidator... validators)
	{
		this.validators = validators;
	}

	public ActionMessages validate(FormFile file) throws BusinessException
	{
		for (FileValidator validator : validators)
		{
			ActionMessages actionMessages = validator.validate(file);
			if (!actionMessages.isEmpty())
				return actionMessages;
		}
		return new ActionMessages();
	}
}
