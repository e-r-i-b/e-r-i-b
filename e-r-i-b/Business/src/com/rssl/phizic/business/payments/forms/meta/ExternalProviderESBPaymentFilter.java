package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.doc.HandlerFilterBase;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.payments.handlers.DocumentFormNameHandlerFilter;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.manager.config.AdaptersConfig;
import com.rssl.phizicgate.manager.services.IDHelper;

import java.util.Collections;

/**
 * Используется для фильтрации платежей в адрес einvoicing-поставщиков при оплате через шину
 * @author gladishev
 * @ created 24.06.2015
 * @ $Author$
 * @ $Revision$
 */

public class ExternalProviderESBPaymentFilter extends HandlerFilterBase
{
	private static final DocumentFormNameHandlerFilter DOCUMENT_FORM_NAME_HANDLER_FILTER;

	static {
		DOCUMENT_FORM_NAME_HANDLER_FILTER = new DocumentFormNameHandlerFilter();
		DOCUMENT_FORM_NAME_HANDLER_FILTER.setParameters(Collections.singletonMap("whiteList", "ExternalProviderPayment;AirlineReservationPayment"));
	}

	public boolean isEnabled(StateObject stateObject)
	{
		if (!DOCUMENT_FORM_NAME_HANDLER_FILTER.isEnabled(stateObject) || !(stateObject instanceof CardPaymentSystemPayment))
			return false;

		CardPaymentSystemPayment payment = (CardPaymentSystemPayment) stateObject;
		String receiverPointCode = payment.getReceiverPointCode();
		if (StringHelper.isEmpty(receiverPointCode))
			return false;

		return !ConfigFactory.getConfig(AdaptersConfig.class).getCardTransfersAdapter().getUUID().equals(IDHelper.restoreRouteInfo(receiverPointCode));
	}
}
