package com.rssl.phizic.web.payments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.business.documents.payments.validators.ChecksOwnersPaymentValidator;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.forms.ViewDocumentOperation;
import com.rssl.phizic.web.actions.payments.forms.ViewDocumentActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.context.PersonContext;

/**
 * @author khudyakov
 * @ created 22.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class ViewEmployeePaymentAction extends ViewDocumentActionBase
{
	protected ViewDocumentOperation createViewEntityOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		DocumentSource source = new ExistingSource(frm.getId(), new ChecksOwnersPaymentValidator());

		ViewDocumentOperation operation = createOperation(ViewDocumentOperation.class, DocumentHelper.getEmployeeServiceName(source));
		operation.initialize(source);
		return operation;
	}

	protected void updateFormData(ViewEntityOperation operation, EditFormBase form) throws BusinessException, BusinessLogicException
	{
		super.updateFormData(operation, form);

		ViewEmployeePaymentForm frm = (ViewEmployeePaymentForm) form;
		//заполненность контекста проверена при инициализации операции
		frm.setActivePerson(PersonContext.getPersonDataProvider().getPersonData().getPerson());
	}
}
