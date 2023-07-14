package com.rssl.phizic.operations.payment;

import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.statemachine.PaymentStateMachineService;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;

/**
 * @author hudyakov
 * @ created 02.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class RemoveDocumentOperation extends OperationBase implements RemoveEntityOperation
{
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();

	private final PaymentStateMachineService stateMachineService = new PaymentStateMachineService();

	private BusinessDocument document;

	public void initialize(Long docimentId) throws BusinessException
	{
		throw new UnsupportedOperationException("Использовать void initialize(DocumentSource source)");
	}

	public void initialize(DocumentSource source)
	{
		document = source.getDocument();
	}

	public void initialize(BusinessDocument document)
	{
		this.document = document;
	}

	public BusinessDocument getEntity()
	{
		return document;
	}

	/**
	 * Здесь документ не удаляется, а переводится в состояние DELETED
	 */
	public void remove() throws BusinessException, BusinessLogicException
	{
		if (isRemoveAvailable())
		{
			StateMachineExecutor executor = new StateMachineExecutor(
					stateMachineService.getStateMachineByFormName(document.getFormName()));
			executor.initialize(document);
			executor.fireEvent(new ObjectEvent(DocumentEvent.DELETE, ObjectEvent.CLIENT_EVENT_TYPE));
			businessDocumentService.addOrUpdate(document);
		}
		else
		{
			throw new BusinessLogicException("Невозможно удалить документ №" + document.getDocumentNumber());
		}
	}

	private boolean isRemoveAvailable()
	{
		return document.getState().equals(new State("DRAFT")) ||
				document.getState().equals(new State("INITIAL")) ||
				document.getState().equals(new State("INITIAL2")) ||
				document.getState().equals(new State("INITIAL3")) ||
				document.getState().equals(new State("INITIAL4")) ||
				document.getState().equals(new State("INITIAL5")) ||
				document.getState().equals(new State("INITIAL6")) ||
				document.getState().equals(new State("INITIAL7")) ||
				document.getState().equals(new State("INITIAL8")) ||
				document.getState().equals(new State("WAIT_CONFIRM")) ||
				document.getState().equals(new State("SAVED")) ||
				document.getState().equals(new State("INITIAL_LONG_OFFER"));
	}

}
