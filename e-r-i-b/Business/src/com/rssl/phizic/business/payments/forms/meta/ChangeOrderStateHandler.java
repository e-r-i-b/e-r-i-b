package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.RefundGoodsClaim;
import com.rssl.phizic.business.documents.RollbackOrderClaim;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.PaymentAbilityERL;
import com.rssl.phizic.business.shop.ShopHelper;
import com.rssl.phizic.common.types.shop.ShopConstants;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.gate.einvoicing.OrderState;
import com.rssl.phizic.logging.LogThreadContext;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Хендлер изменения статуса заказа в EInvoicing.
 *
 * @author bogdanov
 * @ created 18.02.14
 * @ $Author$
 * @ $Revision$
 */

public class ChangeOrderStateHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		OrderState state = OrderState.valueOf(getParameter("state"));

		if (document instanceof JurPayment)
		{
			changeStateForOrder((JurPayment) document, state);
			return;
		}

		throw new DocumentException("Документ должен быть: " + JurPayment.class);
	}

	private void changeStateForOrder(JurPayment document, OrderState newState) throws DocumentException, DocumentLogicException
	{
		String paidBy;
		PaymentAbilityERL chargeOffLink = document.getChargeOffResourceLink();
		if (chargeOffLink != null && (chargeOffLink instanceof CardLink))
			paidBy = chargeOffLink.getDescription();
		else if (chargeOffLink != null)
			paidBy = ShopConstants.UNKNOWN_CHARGE_OFF_CARD_TYPE;
		else
			paidBy = null;

		boolean resetLogThreadContext = LogThreadContext.getIPAddress() == null;
		try
		{
			if (resetLogThreadContext)
				LogThreadContext.setIPAddress(ApplicationConfig.getIt().getApplicationHost().getHostAddress());
			ShopHelper.get().changeOrderStatus(document.getOrderUuid(), newState, document.getIdFromPaymentSystem(), paidBy);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new DocumentLogicException(e);
		}
		catch (UnknownHostException e)
		{
			throw new DocumentException(e);
		}
		finally
		{
			if (resetLogThreadContext)
				LogThreadContext.clear();
		}
	}
}
