package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.ConvertableToGateDocument;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.statemachine.PaymentStateMachineService;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.documents.DocumentService;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * @author Krenev
 * @ created 24.08.2007
 * @ $Author$
 * @ $Revision$
 */
public class WithdrawBusinessDocumentSender extends BusinessDocumentHandlerBase
{
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();
	private final PaymentStateMachineService stateMachineService = new PaymentStateMachineService();

	/**
	 * ���������� ��������
	 * @param document ��������
	 * @param stateMachineEvent
	 * @throws com.rssl.common.forms.DocumentLogicException ����������� �������� ��������, ����� ��������� ������
	 */
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof ConvertableToGateDocument))
			throw new DocumentException("��������� ��������� ConvertableToGateDocument");

		if (!(document instanceof WithdrawDocument))
			throw new DocumentException("��������� ��������� WithdrawDocument");

		DocumentService documentService = GateSingleton.getFactory().service(DocumentService.class);
		WithdrawDocument withdrawDocument = (WithdrawDocument) document;
		try
		{
			//������� �������� ������
			BusinessDocument parent = businessDocumentService.findById(withdrawDocument.getWithdrawInternalId());
			//�������� � �������
			documentService.send(((ConvertableToGateDocument) document).asGateDocument());

			//��������� ���������� ��������
			parent.setDateCreated(((BusinessDocument) document).getDateCreated());

			StateMachineExecutor executor = new StateMachineExecutor(stateMachineService.getStateMachineByFormName(parent.getFormName()));
			executor.initialize(parent);
			executor.fireEvent(new ObjectEvent(DocumentEvent.RECALL, "client"));

			businessDocumentService.addOrUpdate(parent);
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}
		catch (GateLogicException e)
		{
			throw new DocumentLogicException(e.getMessage(), e);
		}
		catch (BusinessException e)
		{
			throw new DocumentException("��� �������� ������ �� ������ �������� ������ id=" + withdrawDocument.getWithdrawExternalId(), e);
		}
		catch (BusinessLogicException e)
		{
			throw new DocumentLogicException(e);
		}
	}
}
