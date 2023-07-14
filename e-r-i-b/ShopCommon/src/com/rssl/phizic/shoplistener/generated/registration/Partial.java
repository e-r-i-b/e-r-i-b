package com.rssl.phizic.shoplistener.generated.registration;

import com.rssl.phizic.einvoicing.ShopOrderImpl;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.einvoicing.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.shoplistener.generated.DocRegRqType;
import com.rssl.phizic.utils.StringHelper;

import java.util.Calendar;

/**
 * @author gulov
 * @ created 12.01.2011
 * @ $Authors$
 * @ $Revision$
 */

/**
 * Частичная регистрация - сохраняется только код заказа в базе
 */
public class Partial extends Registration
{
	@Override
	protected ShopOrder fillOrder(DocRegRqType request)
	{
		ShopOrderImpl order = new ShopOrderImpl();
		order.setExternalId(request.getDocument().getId());
		String receiverCode = request.getSPName();
		order.setReceiverCode(receiverCode);
		order.setType(getType());
		order.setDate(Calendar.getInstance());
		order.setState(OrderState.CREATED);

		try
		{
			ShopProvider activeProvider = GateSingleton.getFactory().service(InvoiceGateBackService.class).getActiveProvider(receiverCode);
			order.setKind(activeProvider.getFormName().equals("ExternalProviderPayment") ? OrderKind.INTERNET_SHOP : OrderKind.AEROFLOT);
		}
		catch (GateException e)
		{
			throw new RuntimeException(e);
		}

		String eShopIdBySP = request.getEShopIdBySP();
		if (getActiveProvider().isFacilitator() && StringHelper.isNotEmpty(eShopIdBySP))
			order.setFacilitatorProviderCode(eShopIdBySP);

		return order;
	}

	protected TypeOrder getType()
	{
		return TypeOrder.P;
	}
}