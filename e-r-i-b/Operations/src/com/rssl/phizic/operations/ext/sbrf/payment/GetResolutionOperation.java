package com.rssl.phizic.operations.ext.sbrf.payment;

import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.statemachine.PaymentStateMachineService;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.rsa.integration.ws.notification.generated.GetResolutionResponseType;
import com.rssl.phizic.rsa.integration.ws.notification.generated.ResolutionTypeList;
import com.rssl.phizic.rsa.senders.GetResolutionSender;
import com.rssl.phizic.rsa.senders.initialization.GetResolutionInitializationData;

/**
 * @author tisov
 * @ created 01.07.15
 * @ $Author$
 * @ $Revision$
 */
public class GetResolutionOperation extends OperationBase
{

	private static final BusinessDocumentService documentService = new BusinessDocumentService();
	private static final PaymentStateMachineService stateMachineService = new PaymentStateMachineService();

	private BusinessDocument document;

	/**
	 * »нициализировать операцию идентификатором документа
	 * @param documentId - ид документа
	 * @throws BusinessException
	 */
	public void initialize(long documentId) throws BusinessException
	{
		this.document = documentService.findById(documentId);
	}

	/**
	 * явл€етс€ ли вердикт системы фрод-мониторинга блокирующим по данному документу в текущий момент
	 * @return
	 */
	public boolean isResolutionStatusNegative()
	{
		ResolutionTypeList resolution = null;
		GetResolutionSender sender = new GetResolutionSender();
		sender.initialize(new GetResolutionInitializationData(this.document.getClientTransactionId()));
		sender.send();

		GetResolutionResponseType response = sender.getResponse();
		if (response != null)
		{
			resolution = response.getResolution();
		}
		return resolution == ResolutionTypeList.CONFIRMED_FRAUD;
	}

	/**
	 * ќтклонить документ
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public void refuseDocument() throws BusinessLogicException, BusinessException
	{
		StateMachineExecutor executor = new StateMachineExecutor(stateMachineService.getStateMachineByFormName(this.document.getFormName()));
		executor.initialize(this.document);

		executor.fireEvent(new ObjectEvent(DocumentEvent.REFUSE, "employee"));
	}
}
