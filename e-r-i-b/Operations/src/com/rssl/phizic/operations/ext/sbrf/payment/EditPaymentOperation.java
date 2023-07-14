package com.rssl.phizic.operations.ext.sbrf.payment;

import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.operations.payment.EditDocumentOperationBase;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;

/**
 * Операция редактирования документа в админке
 * @author niculichev
 * @ created 27.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditPaymentOperation extends EditDocumentOperationBase
{
	public void initialize(DocumentSource source) throws BusinessException, BusinessLogicException
	{
		document = source.getDocument();
	    metadata = source.getMetadata();

		executor = new StateMachineExecutor(stateMachineService.getStateMachineByFormName(document.getFormName()));
		executor.setStateMachineEvent(getStateMachineEvent());
		executor.initialize(document);
	}

	protected String getSourceEvent()
	{
		return  ObjectEvent.EMPLOYEE_EVENT_TYPE;
	}
}
