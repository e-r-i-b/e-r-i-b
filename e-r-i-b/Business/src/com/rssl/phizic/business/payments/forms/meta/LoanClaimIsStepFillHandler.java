package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;

/**
 * хендлер по заполнению флажков на каждом шаге
 * @author Nady
 * @ created 10.02.15
 * @ $Author$
 * @ $Revision$
 */

public class LoanClaimIsStepFillHandler extends BusinessDocumentHandlerBase
{
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof ExtendedLoanClaim))
			throw new DocumentException("Неверный тип платежа id=" + document.getId() + " (Ожидается ExtendedLoanClaim)");

		ExtendedLoanClaim extendedLoanClaim = (ExtendedLoanClaim) document;
		extendedLoanClaim.setStepFilled(true, extendedLoanClaim.getState().getCode());
		try
		{
			businessDocumentService.addOrUpdate(extendedLoanClaim);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
