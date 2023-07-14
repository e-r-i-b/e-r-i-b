package com.rssl.phizic.web.common.client.favourite;

import com.rssl.phizic.operations.autopayments.AutoPaymentLinksOrderOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Сохранение нового порядка отображения автоплатежей
 * @ author gorshkov
 * @ created 27.09.13
 * @ $Author$
 * @ $Revision$
 */
public class AsyncAutoPaymentAction	extends OperationalActionBase
{
	public final ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AsyncAutoPaymentForm frm = (AsyncAutoPaymentForm) form;

		AutoPaymentLinksOrderOperation autoPaymentOperation = createOperation(AutoPaymentLinksOrderOperation.class);
		autoPaymentOperation.initialize();
		autoPaymentOperation.save(frm.getSortAutoPayments());

		return null;
	}

	protected boolean isAjax()
	{
		return true;
	}

}
