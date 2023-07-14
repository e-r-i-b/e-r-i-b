package com.rssl.phizic.test.web.atm.payments;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.rssl.phizic.utils.http.UrlBuilder;

/**
 * Экшен для теста заявок для autopayment-ов:
 * - редактирование
 * - отмена
 * @author Dorzhinov
 * @ created 21.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class TestATMAutoPaymentAction extends TestATMPaymentActionBase
{
	private static final String FORWARD_AUTO_PAYMENT_BACK = "autoPaymentBack";
	private static final String PAYMENT_URL = "/private/payments/payment.do";
	private static final String AUTOPAYMENT_BACK_URL = "/private/autopayments/info.do";

	protected ActionForward submitInit(ActionMapping mapping, TestATMDocumentForm form)
	{
		if (send(form) == 0)
		{
			String lastUrl = form.getLastUrl().toExternalForm();
			if (lastUrl.contains(PAYMENT_URL))
			{
				return mapping.findForward(FORWARD_FIRST_STEP);
			}
			else if (lastUrl.contains(AUTOPAYMENT_BACK_URL))
			{
				UrlBuilder urlBuilder = new UrlBuilder();
				urlBuilder.setUrl(mapping.findForward(FORWARD_AUTO_PAYMENT_BACK).getPath());
				//common params
				urlBuilder.addParameter("cookie", form.getCookie());
				urlBuilder.addParameter("url", form.getUrl());
				urlBuilder.addParameter("proxyUrl", form.getProxyUrl());
				urlBuilder.addParameter("proxyPort", "" + form.getProxyPort());

				//test case params
				urlBuilder.addParameter("address", AUTOPAYMENT_BACK_URL); //mobile5.jsp option.value
				urlBuilder.addParameter("params", form.getLastUrl().getQuery());
				urlBuilder.addParameter("operation", "send");

				return new ActionForward(urlBuilder.toString(), true);
			}
		}

		return mapping.findForward(FORWARD_INIT);
	}
}
