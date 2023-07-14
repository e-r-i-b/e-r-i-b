package com.rssl.phizic.web.client.basket.invoiceSubscription;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.Constants;
import com.rssl.phizic.operations.payment.EditDocumentOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.http.UrlBuilder;
import com.rssl.phizic.web.actions.payments.forms.DefaultDocumentAction;
import com.rssl.phizic.web.common.client.basket.invoiceSubscription.EditInvoiceSubscriptionPaymentAction;
import com.rssl.phizic.web.common.client.basket.invoiceSubscription.EditInvoiceSubscriptionPaymentForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Асинхронное редактирование заявки на подписку получения инвойсов(создание подписки из подтвержденного документа)
 * @author niculichev
 * @ created 01.06.14
 * @ $Author$
 * @ $Revision$
 */
public class AsyncEditInvoiceSubscriptionPaymentAction extends EditInvoiceSubscriptionPaymentAction
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.makeInvoiceSubscription", "makeInvoiceSubscription");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return mapping.findForward(FORWARD_SHOW_FORM);
	}

	/**
	 * Создание подписки на получение инвойсов из сущесвующего документа
	 * @param mapping маппинг
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return форвард
	 * @throws Exception
	 */
	public ActionForward makeInvoiceSubscription(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			EditInvoiceSubscriptionPaymentForm frm = (EditInvoiceSubscriptionPaymentForm) form;
			frm.setRedirectUrl(createRedirectUrl(frm));
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e.getMessage());
		}
		catch (BusinessException ex)
		{
			log.error(ex.getMessage(), ex);
			saveError(request, new ActionMessage(Constants.COMMON_EXCEPTION_MESSAGE, false));
		}
		return mapping.findForward(FORWARD_SHOW_FORM);
	}

	protected boolean isAjax()
	{
		return true;
	}

}
