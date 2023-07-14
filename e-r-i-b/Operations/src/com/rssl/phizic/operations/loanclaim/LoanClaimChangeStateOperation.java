package com.rssl.phizic.operations.loanclaim;

import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.ikfl.crediting.CRMMessageService;
import com.rssl.ikfl.crediting.CRMStateType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.operations.payment.EditDocumentOperationBase;

/**
 * Операция изменения состояния кредитной заявки сотрудником
 *
 * @ author: Gololobov
 * @ created: 05.05.15
 * @ $Author$
 * @ $Revision$
 */
public class LoanClaimChangeStateOperation extends EditDocumentOperationBase
{
	private final CRMMessageService crmMessageService = new CRMMessageService();
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();
	/**
	 * Отправка в CRM сообщения об обновлении статуса заявки
	 * @param claim
	 * @throws Exception
	 */
	protected void sendRefuseMessageToCRM(ExtendedLoanClaim claim) throws Exception
	{
		crmMessageService.updateLoanClaimStatus(CRMStateType.FILL_REFUSE, claim);
	}

	protected void fireEventLoanClaim(ExtendedLoanClaim claim, DocumentEvent documentEvent) throws BusinessLogicException, BusinessException
	{
		executor = new StateMachineExecutor(stateMachineService.getStateMachineByFormName(claim.getFormName()));
		executor.initialize(claim);
		ObjectEvent event = new ObjectEvent(documentEvent, ObjectEvent.EMPLOYEE_EVENT_TYPE);
		executor.fireEvent(event);
		businessDocumentService.addOrUpdate(claim);
	}
}
