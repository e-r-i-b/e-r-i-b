package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.HandlerFilter;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.ext.sbrf.payments.forms.meta.DocumentOfficeESBSupportedFilter;

/**
 * Хендлер подготовки документа перевода со счета
 * @author gladishev
 * @ created 27.07.2015
 * @ $Author$
 * @ $Revision$
 */

public class AccountPaymentPrepareHandler extends PrepareDocumentHandler
{
	private static final HandlerFilter documentOfficeESBSupportedFilter = new DocumentOfficeESBSupportedFilter();

	public void process(BusinessDocument document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if ((document.getType() == com.rssl.phizic.gate.payments.ClientAccountsTransfer.class || document.getType() == com.rssl.phizic.gate.payments.AccountToCardTransfer.class)
				&& documentOfficeESBSupportedFilter.isEnabled(document))
			return;

		super.process(document, stateMachineEvent);
	}
}
