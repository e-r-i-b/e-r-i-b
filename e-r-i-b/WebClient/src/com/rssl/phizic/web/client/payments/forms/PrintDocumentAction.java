package com.rssl.phizic.web.client.payments.forms;

import com.rssl.phizic.operations.forms.ViewDocumentOperation;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.business.documents.payments.validators.IsOwnDocumentValidator;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.web.actions.payments.forms.ViewDocumentForm;
import com.rssl.phizic.web.actions.payments.forms.ViewDocumentAction;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author osminin
 * @ created 02.03.2009
 * @ $Author$
 * @ $Revision$
 */

public class PrintDocumentAction extends ViewDocumentAction
{
	protected ViewEntityOperation createViewEntityOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		ViewDocumentForm frm = (ViewDocumentForm) form;
		ExistingSource source = new ExistingSource(frm.getId(), new IsOwnDocumentValidator());
		ViewDocumentOperation operation = createOperation("PrintDocumentOperation", source.getMetadata().getName());
		operation.initialize(source);
		return operation;
	}
}
