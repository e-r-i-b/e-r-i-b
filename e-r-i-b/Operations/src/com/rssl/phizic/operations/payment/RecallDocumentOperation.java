package com.rssl.phizic.operations.payment;

import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.phizic.business.statemachine.PaymentStateMachineService;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.documents.payments.validators.IsOwnDocumentValidator;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.operations.OperationBase;

/**
 @author Pankin
 @ created 16.12.2010
 @ $Author$
 @ $Revision$
 */
public class RecallDocumentOperation extends OperationBase
{
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();

	private final PaymentStateMachineService stateMachineService = new PaymentStateMachineService();

	private static final IsOwnDocumentValidator ownValidator = new IsOwnDocumentValidator();

	private BusinessDocument document;

	public void initialize(Long documentId) throws BusinessException
	{
		document = businessDocumentService.findById(documentId);
		ownValidator.validate(document);
	}

	public void initialize(DocumentSource source)
	{
		document = source.getDocument();
	}

	public State getDocumentState()
	{
		return document.getState();
	}

	@Transactional
	public void recall() throws BusinessException, BusinessLogicException
	{
		ownValidator.validate(document);
		StateMachineExecutor executor = getStateMachineExecutor();
		executor.initialize(document);
		executor.fireEvent(new ObjectEvent(DocumentEvent.RECALL, "client"));
		businessDocumentService.addOrUpdate(document);
	}

	private StateMachineExecutor getStateMachineExecutor() throws BusinessException
	{
		return new StateMachineExecutor(stateMachineService.getStateMachineByFormName(document.getFormName()));
	}
}
