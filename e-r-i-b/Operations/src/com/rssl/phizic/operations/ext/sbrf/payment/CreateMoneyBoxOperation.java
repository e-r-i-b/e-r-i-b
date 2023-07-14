package com.rssl.phizic.operations.ext.sbrf.payment;

import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.operations.payment.CreateFormPaymentOperation;

/**
 * @author vagin
 * @ created 03.09.14
 * @ $Author$
 * @ $Revision$
 * Операция работы с заявкой на копилку.
 */
public class CreateMoneyBoxOperation extends CreateFormPaymentOperation
{
	/**
	 * Перевод документ в статус для редактирования без изменения статуса в БД.
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public void editDraftClaim() throws BusinessLogicException, BusinessException
	{
		StateMachineExecutor executor = new StateMachineExecutor(stateMachineService.getStateMachineByFormName(document.getFormName()));
		executor.setStateMachineEvent(getStateMachineEvent());
		executor.initialize(document);
		executor.fireEvent(new ObjectEvent(DocumentEvent.EDIT, getSourceEvent()));
	}
}
