package com.rssl.phizic.operations.einvoicing;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.basket.invoice.InvoiceUpdateInfo;
import com.rssl.phizic.business.shop.ShopHelper;
import com.rssl.phizic.context.ClientInvoiceCounterHelper;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.einvoicing.OrderState;
import com.rssl.phizic.gate.einvoicing.ShopOrder;
import com.rssl.phizic.gate.einvoicing.ShopOrderService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.StringHelper;

import java.util.Calendar;

/**
 * @author tisov
 * @ created 23.06.14
 * @ $Author$
 * @ $Revision$
 */

public class UpdateShopOrderOperation extends OperationBase
{
	protected static final Log log = PhizICLogFactory.getLog(LogModule.Core);
	private ShopOrder shopOrder;

	public void initialize(String orderUid) throws BusinessException
	{
		if(StringHelper.isEmpty(orderUid))
			throw new BusinessException("Идентификатор заказа не задан");

		ShopOrder temp = ShopHelper.get().getShopOrder(orderUid);
		if(temp == null)
			throw new BusinessException("Заказа с идентификатороом \"" + orderUid + "\" не найден.");

		this.shopOrder = temp;
	}

	public void delay(Calendar delayDate) throws GateLogicException, GateException, BusinessLogicException, BusinessException
	{
		ShopHelper.get().changeOrderStatus(shopOrder.getUuid(), OrderState.DELAYED, null, null, delayDate);
		ShopOrderService shopOrderService = GateSingleton.getFactory().service(ShopOrderService.class);
		//меняем признак в БД.
		shopOrderService.markViewed(shopOrder.getUuid());
		//чистим кеш по списку - необходимо отобразить актуальные данные.
		try
		{
			ClientInvoiceCounterHelper.resetCounterValue();
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}

	public ShopOrder getShopOrder() throws BusinessException
	{
		return shopOrder;
	}
}
