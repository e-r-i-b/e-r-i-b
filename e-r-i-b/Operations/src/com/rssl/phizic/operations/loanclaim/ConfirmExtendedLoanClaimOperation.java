package com.rssl.phizic.operations.loanclaim;

import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.payment.ConfirmFormPaymentOperation;
import com.rssl.phizic.operations.payment.support.DbDocumentTarget;

/**
 * @author usachev
 * @ created 19.05.15
 * @ $Author$
 * @ $Revision$
 * Операция предназначена для потверждения расширенной кредитной заявки
 */
public class ConfirmExtendedLoanClaimOperation extends ConfirmFormPaymentOperation
{
	/**
	 * Перевод документа на первый шаг
	 */
	public void saveAsInitial() throws BusinessException, BusinessLogicException
	{
		executor.fireEvent(new ObjectEvent(DocumentEvent.INITIAL, ObjectEvent.CLIENT_EVENT_TYPE));
		new DbDocumentTarget().save(document);
	}
}
