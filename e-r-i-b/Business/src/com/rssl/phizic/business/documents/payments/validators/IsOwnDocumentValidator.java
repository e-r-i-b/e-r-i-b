package com.rssl.phizic.business.documents.payments.validators;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.exceptions.NotOwnDocumentException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import org.apache.commons.lang.ObjectUtils;

/**
 * @author Evgrafov
 * @ created 13.12.2005
 * @ $Author: erkin $
 * @ $Revision: 74961 $
 */

public class IsOwnDocumentValidator implements DocumentValidator
{
	public void validate(BusinessDocument document) throws BusinessException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		BusinessDocumentOwner currentDocumentOwner = personData.createDocumentOwner();

		if (!ObjectUtils.equals(document.getOwner(), currentDocumentOwner))
			throw new NotOwnDocumentException("” пользовател€ " + currentDocumentOwner + " нет прав на просмотр документа с id=" + document.getId());

		if (!DocumentHelper.checkApplicationRestriction(document))
			throw new NotOwnDocumentException("” пользовател€ " + currentDocumentOwner + " нет прав на просмотр документа с id=" + document.getId());
	}
}
