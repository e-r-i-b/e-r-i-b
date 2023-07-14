package com.rssl.phizic.web.client.basket.invoiceSubscription;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.CreateInvoiceSubscriptionPayment;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.operations.basket.invoiceSubscription.ConfirmCreateInvoiceSubscriptionOperation;
import com.rssl.phizic.operations.payment.ConfirmFormPaymentOperation;
import com.rssl.phizic.operations.payment.RemoveDocumentOperation;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.common.client.ext.sbrf.payment.ConfirmClientDocumentAction;
import org.apache.struts.action.*;

import java.math.BigDecimal;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Подтрверждение заявки на создания подписки на инвойсы
 * @author niculichev
 * @ created 20.05.14
 * @ $Author$
 * @ $Revision$
 */
public class ConfirmInvoiceSubscriptionPaymentAction extends ConfirmClientDocumentAction
{
	private static final String BASKET_FORWARD = "basket";
	private static final String LIST_INVOICE = "ListInvoiceSubscription";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.remove("button.makeLongOffer");
		return map;
	}

	@Override
	protected ConfirmFormPaymentOperation createConfirmOperation(ExistingSource source)
			throws BusinessException, BusinessLogicException
	{
		return createOperation(ConfirmCreateInvoiceSubscriptionOperation.class, getServiceName(source));
	}

	protected ActionForward createSuccessConfirmForward(ActionMapping mapping, ConfirmFormPaymentOperation operation, HttpServletResponse response) throws BusinessException, BusinessLogicException
	{
		ActionForward forward = mapping.findForward(BASKET_FORWARD);
		CreateInvoiceSubscriptionPayment invoiceSubscription = (CreateInvoiceSubscriptionPayment) operation.getDocument();

		saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				StrutsUtils.getMessage("invoice.subscription.success.create.message", "basketBundle", invoiceSubscription.getFriendlyName()), false), forward.getPath());

		return forward;
	}

	protected String getDefaultAvailableLimitAmountMessage(BigDecimal groupRiskAvailableAmount, BigDecimal obstructionAvailableAmount)
	{
		return null;
	}

	@Override
	protected ActionForward createRemoveForward(ActionMapping mapping, ActionForm form, HttpServletRequest request, RemoveDocumentOperation operation)
	{
		return mapping.findForward(LIST_INVOICE);
	}
}
