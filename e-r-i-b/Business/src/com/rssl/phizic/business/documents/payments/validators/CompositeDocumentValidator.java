package com.rssl.phizic.business.documents.payments.validators;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;

/**
 * @author Evgrafov
 * @ created 13.12.2005
 * @ $Author: khudyakov $
 * @ $Revision: 58441 $
 */

public class CompositeDocumentValidator implements DocumentValidator
{
	private DocumentValidator[] validators;

	/**
	 * @param validators массив валидаторов
	 */
	public CompositeDocumentValidator(DocumentValidator... validators)
	{
		this.validators = validators.clone();
	}

	public void validate(BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		for (int i = 0; i < validators.length; i++)
		{
			DocumentValidator validator = validators[i];
			validator.validate(document);
		}
	}
}
