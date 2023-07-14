package com.rssl.phizicgate.manager.services.selectors.way;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.config.ExternalSystemIntegrationMode;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizicgate.manager.config.AdaptersConfig;
import com.rssl.phizicgate.manager.routing.Adapter;
import com.rssl.phizicgate.manager.services.IDHelper;

/**
 * @author akrenev
 * @ created 10.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * Селектор метода интеграции для оплаты поставщика
 */

class CardPaymentSystemPaymentDocumentWaySelector extends WaySelector<CardPaymentSystemPayment>
{
	@Override
	protected ExternalSystemIntegrationMode getMode(CardPaymentSystemPayment source, String serviceName) throws GateException
	{
		ExternalSystemIntegrationMode documentMode = DocumentWaySelector.getDocumentMode(source);
		if (documentMode != null)
			return documentMode;

		if (ServiceNameWaySelector.getServiceMode(serviceName) == ExternalSystemIntegrationMode.WS)
			return ExternalSystemIntegrationMode.WS;

		return getProviderMode(source);
	}

	static ExternalSystemIntegrationMode getProviderMode(CardPaymentSystemPayment source) throws GateException
	{
		AdaptersConfig config = ConfigFactory.getConfig(AdaptersConfig.class);
		Adapter iqwAdapter = config.getCardTransfersAdapter();
		if (iqwAdapter == null)
			throw new GateException("Не задана внешняя система для карточных переводов");

		String receiverPointCode = source.getReceiverPointCode();

		if (!iqwAdapter.getUUID().equals(IDHelper.restoreRouteInfo(receiverPointCode)))
			return ExternalSystemIntegrationMode.WS;

		String providerId = IDHelper.restoreOriginalIdWithAdditionalInfo(IDHelper.restoreOriginalId(receiverPointCode));
		return getConfig().getProviderCodes().contains(providerId)? ExternalSystemIntegrationMode.MQ : ExternalSystemIntegrationMode.WS;
	}
}
