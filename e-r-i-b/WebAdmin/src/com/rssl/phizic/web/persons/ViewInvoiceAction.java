package com.rssl.phizic.web.persons;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.basket.invoice.Invoice;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.basket.invoice.ViewInvoiceOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;

/**
 * @author muhin
 * @ created 18.06.15
 * @ $Author$
 * @ $Revision$
 *
 * Экшен просмотра детальной информации по задолженности в АРМ Сотрудника
 */
public class ViewInvoiceAction extends ViewActionBase
{
	@Override
	protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		ViewInvoiceOperation operation = createOperation("ViewInvoiceAdminOperation");
		operation.initialize(frm.getId());
		return operation;
	}

	@Override
	protected void updateFormData(ViewEntityOperation operation, EditFormBase form) throws BusinessException, BusinessLogicException
	{
		ViewInvoiceForm frm = (ViewInvoiceForm) form;
		ViewInvoiceOperation op = (ViewInvoiceOperation) operation;
		Invoice invoice = op.getEntity();

		frm.setInvoice(invoice);
		frm.setBankName(op.getBankName());
		frm.setBankAccount(op.getBankAccount());
		frm.setSubscriptionName(op.getInvoiceSubscriptionName());
		frm.setOperationAvailable(op.isAvailableOperations());
		frm.setConfirmSubscription(op.isConfirmSubscription());
	}
}
