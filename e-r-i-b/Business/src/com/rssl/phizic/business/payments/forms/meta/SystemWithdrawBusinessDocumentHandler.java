package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.statemachine.PaymentStateMachineService;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.gate.documents.WithdrawDocument;

/**
 * ’ендлер выполн€ющий перевод статуса отмененного документа
 * @author gladishev
 * @ created 19.06.2013
 * @ $Author$
 * @ $Revision$
 */
public class SystemWithdrawBusinessDocumentHandler extends BusinessDocumentHandlerBase
{
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();
	private final PaymentStateMachineService stateMachineService = new PaymentStateMachineService();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof WithdrawDocument))
			throw new DocumentException("ќжидаетс€ наследник WithdrawDocument");

		WithdrawDocument withdrawDocument = (WithdrawDocument) document;
		try
		{
			BusinessDocument transferPayment = (BusinessDocument) withdrawDocument.getTransferPayment();
			StateMachineExecutor executor = new StateMachineExecutor(stateMachineService.getStateMachineByFormName(transferPayment.getFormName()));
			executor.initialize(transferPayment);
			executor.fireEvent(new ObjectEvent(DocumentEvent.RECALL, "system"));

			businessDocumentService.addOrUpdate(transferPayment);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new DocumentLogicException(e);
		}
	}
}
