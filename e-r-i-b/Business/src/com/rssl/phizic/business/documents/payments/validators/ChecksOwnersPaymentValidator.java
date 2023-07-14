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
 * ѕроверка владельца платежа
 *
 * @author khudyakov
 * @ created 15.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class ChecksOwnersPaymentValidator implements DocumentValidator
{
	public void validate(BusinessDocument document) throws BusinessException
	{
		if (!PersonContext.isAvailable())
			throw new BusinessException("PersonContext не проинициализирован клиентом.");

		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		BusinessDocumentOwner currentDocumentOwner = personData.createDocumentOwner();

		if (!ObjectUtils.equals(document.getOwner(), currentDocumentOwner))
			throw new NotOwnDocumentException("” пользовател€ " + currentDocumentOwner + " нет прав на просмотр документа с id=" + document.getId());

		if (!DocumentHelper.checkApplicationRestriction(document))
			throw new NotOwnDocumentException("” пользовател€ " + currentDocumentOwner + " нет прав на просмотр документа с id=" + document.getId());
	}
}
