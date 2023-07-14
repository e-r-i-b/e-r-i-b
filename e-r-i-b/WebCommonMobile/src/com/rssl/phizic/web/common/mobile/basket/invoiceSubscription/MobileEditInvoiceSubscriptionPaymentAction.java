package com.rssl.phizic.web.common.mobile.basket.invoiceSubscription;

import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.Constants;
import com.rssl.phizic.operations.payment.EditDocumentOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.client.basket.invoiceSubscription.EditInvoiceSubscriptionPaymentAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Редактирование заявки на подписку получения инвойсов (создание подписки из подтвержденного документа) в mAPI
 * @ author: Gololobov
 * @ created: 17.10.14
 * @ $Author$
 * @ $Revision$
 */
public class MobileEditInvoiceSubscriptionPaymentAction extends EditInvoiceSubscriptionPaymentAction
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("init", "init");
		map.put("save", "save");
		return map;
	}

	/**
	 * Создание подписки на получение инвойсов из сущесвующего документа
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			MobileEditInvoiceSubscriptionPaymentForm frm = (MobileEditInvoiceSubscriptionPaymentForm) form;
			String redirectUrl = createRedirectUrl(frm);
			if (StringHelper.isNotEmpty(redirectUrl))
				return new ActionForward(redirectUrl, true);
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
		log.info("Не удалось создать заявку на подписку получения инвойсов");
		return mapping.findForward(FORWARD_SHOW_FORM);
	}

	protected CreationType getCreationType()
	{
		return CreationType.mobile;
	}

	protected String buildFormHtml(EditDocumentOperation operation, FieldValuesSource fieldValuesSource, ActionForm form) throws BusinessException
	{
		return operation.buildMobileXml(fieldValuesSource);
	}
}
