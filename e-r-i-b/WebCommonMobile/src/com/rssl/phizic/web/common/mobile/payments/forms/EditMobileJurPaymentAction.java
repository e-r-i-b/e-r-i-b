package com.rssl.phizic.web.common.mobile.payments.forms;

import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.operations.payment.EditJurPaymentOperation;
import com.rssl.phizic.web.actions.payments.forms.EditJurPaymentAction;
import com.rssl.phizic.web.actions.payments.forms.EditJurPaymentForm;
import com.rssl.phizic.utils.NumericUtil;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Erkin
 * @ created 07.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class EditMobileJurPaymentAction extends EditJurPaymentAction
{
	protected CreationType getNewDocumentCreationType()
	{
		return CreationType.mobile;
	}

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("init", "start");
		map.put("next", "next");
		return map;
	}

    protected ActionForward forwardBillingPayment(Long providerId, EditJurPaymentOperation operation, EditJurPaymentForm frm)
	{
		EditMobileJurPaymentForm form = (EditMobileJurPaymentForm) frm;
		form.setServiceProviderList(operation.getServiceProviders());
		return getCurrentMapping().findForward(FORWARD_BILLING_PAYMENT);
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		checkInputParameters(form);
		return super.start(mapping, form, request, response);
	}

	public ActionForward next(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		checkInputParameters(form);
		return super.next(mapping, form, request, response);
	}

	private void checkInputParameters(ActionForm form)
	{
		EditJurPaymentForm frm = (EditJurPaymentForm) form;
		if (NumericUtil.isNotEmpty(frm.getId()))
			frm.setCopying(true);
	}

	protected void updateFormAdditionalData(EditJurPaymentForm frm, EditJurPaymentOperation operation) throws BusinessException, BusinessLogicException
	{
		EditMobileJurPaymentForm form = (EditMobileJurPaymentForm) frm;
		form.setFromResource(operation.getFromResourceLink());
		super.updateFormAdditionalData(form,operation);

	}
}
