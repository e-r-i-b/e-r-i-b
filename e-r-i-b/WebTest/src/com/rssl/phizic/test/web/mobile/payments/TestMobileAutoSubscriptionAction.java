package com.rssl.phizic.test.web.mobile.payments;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.rssl.phizic.utils.http.UrlBuilder;

/**
 * Ёкшен дл€ теста за€вок дл€ autosubscription-ов:
 * - редактирование
 * - приостановка
 * - возобновление
 * - отмена
 * @author Dorzhinov
 * @ created 18.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class TestMobileAutoSubscriptionAction extends TestMobilePaymentActionBase
{
	private static final String FORWARD_AUTO_SUBSCRIPTION_BACK = "autoSubscriptionBack";
	private static final String PAYMENT_URL = "/private/payments/payment.do";
	private static final String AUTOSUB_BACK_URL = "/private/autosubscriptions/info.do";

	protected ActionForward submitInit(ActionMapping mapping, TestMobileDocumentForm form)
	{
		if (send(form) == 0)
		{
			String lastUrl = form.getLastUrl().toExternalForm();
			if (lastUrl.contains(PAYMENT_URL))
			{
				return mapping.findForward(FORWARD_FIRST_STEP);
			}
			else if (lastUrl.contains(AUTOSUB_BACK_URL))
			{
				UrlBuilder urlBuilder = new UrlBuilder();
				urlBuilder.setUrl(mapping.findForward(FORWARD_AUTO_SUBSCRIPTION_BACK).getPath());
				//common params
				urlBuilder.addParameter("cookie", form.getCookie());
				urlBuilder.addParameter("url", form.getUrl());
				urlBuilder.addParameter("proxyUrl", form.getProxyUrl());
				urlBuilder.addParameter("proxyPort", "" + form.getProxyPort());
				urlBuilder.addParameter("version", form.getVersion());
				//test case params
				urlBuilder.addParameter("address", AUTOSUB_BACK_URL); //mobile5.jsp option.value
				urlBuilder.addParameter("params", form.getLastUrl().getQuery());
				urlBuilder.addParameter("operation", "send");
				
				return new ActionForward(urlBuilder.toString(), true);
			}
		}
		
		return mapping.findForward(FORWARD_INIT);
	}
}
