package com.rssl.phizic.web.common.mobile.payments;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.mobile.MobileApiConfig;
import com.rssl.phizic.operations.payment.CreateFormPaymentOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ author: Gololobov
 * @ created: 06.10.14
 * @ $Author$
 * @ $Revision$
 * Ёкшн получени€ суммы по умолчанию дл€ оплаты мобильной св€зи
 */
public class ShowMobilePhoneDefaultSumPayAction extends OperationalActionBase
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		createOperation(CreateFormPaymentOperation.class, "RurPayJurSB");

		MobileApiConfig mobileApiConfig = ConfigFactory.getConfig(MobileApiConfig.class);
		BigDecimal defaultSum = BigDecimal.valueOf(mobileApiConfig.getMobilePhoneNumberSumDefault());
		ShowMobilePhoneDefaultSumPayForm frm = (ShowMobilePhoneDefaultSumPayForm) form;
		frm.setPhonePayDefaultSum(defaultSum);

		return mapping.findForward(FORWARD_START);
	}
}
