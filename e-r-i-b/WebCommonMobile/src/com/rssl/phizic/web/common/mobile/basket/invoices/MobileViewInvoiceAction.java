package com.rssl.phizic.web.common.mobile.basket.invoices;

import com.rssl.common.forms.processing.CompositeFieldValuesSource;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.basket.invoice.Invoice;
import com.rssl.phizic.business.cards.CardsUtil;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.LinkHelper;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.operations.basket.invoice.CreateInvoicePaymentOperation;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.common.client.basket.invoice.AsyncProcessInvoiceAction;
import com.rssl.phizic.web.common.client.basket.invoice.ViewInvoiceForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Ёкшен просмотра, оплаты, подтверждени€, откладывани€, удалени€ счета корзины
 * @ author: Gololobov
 * @ created: 26.10.14
 * @ $Author$
 * @ $Revision$
 */
public class MobileViewInvoiceAction extends AsyncProcessInvoiceAction
{
	private static final String FORWARD_CONFIRM = "Confirm";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.put("delay", "delay");
		map.put("delete", "delete");
		map.put("payInvoice", "payInvoice");
		map.put("confirm", "confirm");
		return map;
	}

	protected boolean isAjax()
	{
		return false;
	}

	public ActionForward payInvoice(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.payInvoice(mapping, form, request, response);

		MobileViewInvoiceForm frm = (MobileViewInvoiceForm) form;
		if (frm.isNeedConfirm())
			return mapping.findForward(FORWARD_CONFIRM);
		return mapping.findForward(FORWARD_SUCCESS);
	}

	protected ConfirmStrategyType getConfirmStrategyType()
	{
		return ConfirmStrategyType.none;
	}

	protected FieldValuesSource createValueSource(ViewInvoiceForm form, HttpServletRequest request, CreateInvoicePaymentOperation operation) throws BusinessLogicException
	{
		CardLink cardLink = getFromResource((MobileViewInvoiceForm) form, operation);
		Map<String, String> fieldsMap = new HashMap<String, String>();
		fieldsMap.put("fromResource", cardLink.getCode());
		return new CompositeFieldValuesSource(new MapValuesSource(fieldsMap), new RequestValuesSource(request));
	}

	private CardLink getFromResource(MobileViewInvoiceForm form, CreateInvoicePaymentOperation operation) throws BusinessLogicException
	{
		CardLink cardLink;
		Integer cardId = form.getFromResource();
		if (cardId == null)
		{
			Invoice invoice = operation.getInvoice();
			cardLink = CardsUtil.getCardLinkWithoutVisible(invoice.getCardNumber());
			if (cardLink == null)
			{
				throw new BusinessLogicException("Ќе удалось найти источник списани€");
			}
			return cardLink;
		}

		cardLink = CardsUtil.getCardLinkOfClientById(Long.valueOf(cardId));
		if (cardLink == null || !LinkHelper.isVisibleInChannel(cardLink))
		{
			throw new BusinessLogicException("Ќе удалось найти источник списани€");
		}
		return cardLink;
	}
}
