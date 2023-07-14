package com.rssl.phizic.web.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.AutoSubscriptionPaymentBase;
import com.rssl.phizic.business.documents.payments.EditAutoSubscriptionPayment;
import com.rssl.phizic.business.documents.payments.EditP2PAutoTransferClaim;
import com.rssl.phizic.business.resources.external.AutoSubscriptionLink;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.payments.autosubscriptions.AutoSubscriptionClaim;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: vagin
 * @ created: 03.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class PrintEmployeeEditPaymentAction extends ViewEmployeePaymentAction
{
	protected void updateFormData(ViewEntityOperation operation, EditFormBase form) throws BusinessException, BusinessLogicException
	{
		super.updateFormData(operation, form);

		PrintEmployeeEditPaymentForm frm = (PrintEmployeeEditPaymentForm) form;
		AutoSubscriptionClaim payment = (AutoSubscriptionClaim) operation.getEntity();

		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		if (StringHelper.isEmpty(payment.getNumber()))
		{
			throw new BusinessException("Невозможно получить данные по редактируемому платежу.");
		}

		AutoSubscriptionLink link = personData.getAutoSubscriptionLink(Long.parseLong(payment.getNumber()));

		frm.setFormData(payment, link.getAutoSubscriptionInfo());
		form.setField("employeeDepartmentId", EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee().getDepartmentId());
	}

	protected ActionForward forwardSuccessShow(ActionMapping mapping, ViewEntityOperation operation)
	{
		return mapping.findForward(FORWARD_START);
	}
}