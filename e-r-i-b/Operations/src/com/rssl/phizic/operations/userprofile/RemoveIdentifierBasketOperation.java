package com.rssl.phizic.operations.userprofile;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscriptionService;
import com.rssl.phizic.business.userDocuments.DocumentType;
import com.rssl.phizic.business.userDocuments.UserDocument;
import com.rssl.phizic.business.userDocuments.UserDocumentService;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.operations.ConfirmableOperationBase;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.security.SecurityLogicException;

/**
 * @author lukina
 * @ created 04.06.2014
 * @ $Author$
 * @ $Revision$
 */
public class RemoveIdentifierBasketOperation extends ConfirmableOperationBase
{
	private static final InvoiceSubscriptionService invoiceService = new InvoiceSubscriptionService();
	private UserDocument userDocument;

	public void initialize(Long docId) throws BusinessException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		userDocument = UserDocumentService.get().getUserDocumentById(docId);
		if (userDocument != null && !userDocument.getLoginId().equals(personData.getLogin().getId()))
			throw new AccessException("Документ с id=" + docId + " не принадлежит пользователю с login_id=" + personData.getLogin().getId());
	}

	public void initialize(Long docId, DocumentType docType) throws BusinessException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		userDocument = UserDocumentService.get().getUserDocumentById(docId);
		if (userDocument == null || userDocument.getDocumentType() != docType)
			throw new ResourceNotFoundBusinessException("Документ с id=" + docId + " не найден.", UserDocument.class);
		if (userDocument != null && !userDocument.getLoginId().equals(personData.getLogin().getId()))
			throw new AccessException("Документ с id=" + docId + " не принадлежит пользователю с login_id=" + personData.getLogin().getId());
	}

	public UserDocument getUserDocument()
	{
		return userDocument;
	}

	public void save() throws BusinessException, BusinessLogicException, SecurityLogicException
	{
		saveConfirm();
	}

	protected void saveConfirm() throws BusinessException, BusinessLogicException, SecurityLogicException
	{
		UserDocumentService.get().remove(userDocument);
		invoiceService.removeUserDocumentsLinks(userDocument.getLoginId(), userDocument.getDocumentType().name());
	}

	public ConfirmableObject getConfirmableObject()
	{
		return userDocument;
	}
}
