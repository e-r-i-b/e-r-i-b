package com.rssl.phizic.web.payments.forms;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.AbstractAccountsTransfer;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.business.documents.payments.validators.NullDocumentValidator;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.forms.ViewDocumentOperation;
import com.rssl.phizic.web.actions.payments.forms.ViewDocumentActionBase;
import com.rssl.phizic.web.actions.payments.forms.ViewDocumentForm;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author komarov
 * @ created 17.05.2013 
 * @ $Author$
 * @ $Revision$
 */

public class PrintCheckAction extends ViewDocumentActionBase
{

	protected ViewEntityOperation createViewEntityOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		ViewDocumentForm frm = (ViewDocumentForm) form;
		ExistingSource source = new ExistingSource(frm.getId(), new NullDocumentValidator());

		ViewDocumentOperation operation;
		if(checkAccess(ViewDocumentOperation.class, "ViewPaymentList"))
			operation = createOperation(ViewDocumentOperation.class, "ViewPaymentList");
		else
			operation = createOperation(ViewDocumentOperation.class, "ViewPaymentListUseClientForm");

		operation.initialize(source);
		frm.setPrintCheck(operation.checkPrintCheck());
		if (source.getDocument() instanceof AbstractAccountsTransfer)
		{
			AbstractAccountsTransfer accountsTransfer = (AbstractAccountsTransfer) source.getDocument();
			frm.setProviderName(accountsTransfer.getReceiverName());
			frm.setExternalPayment(operation.getExternalPayment());
			frm.setFnsPayment(operation.getFnsPayment());
		}
		return operation;
	}

	protected String getMode()
	{
		return "printCheck";
	}
}
