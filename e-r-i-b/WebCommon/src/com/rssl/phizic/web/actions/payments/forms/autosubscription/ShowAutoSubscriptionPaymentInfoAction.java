package com.rssl.phizic.web.actions.payments.forms.autosubscription;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.autosubscription.GetAutoSubscriptionPaymentInfoOperation;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.utils.http.UrlBuilder;
import com.rssl.phizic.utils.StringHelper;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author bogdanov
 * @ created 13.02.2012
 * @ $Author$
 * @ $Revision$
 */

public class ShowAutoSubscriptionPaymentInfoAction extends ShowAutoSubscriptionPaymentInfoActionBase
{
	protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		ShowAutoSubscriptionPaymentInfoForm form = (ShowAutoSubscriptionPaymentInfoForm) frm;
		GetAutoSubscriptionPaymentInfoOperation operation = createOperation(GetAutoSubscriptionPaymentInfoOperation.class, "AutoSubscriptionLinkManagment");
		operation.initialize(form.getSubscriptionId(), form.getId());
		return operation;
	}
}
