package com.rssl.phizic.operations.payment.internetShops;

import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.shop.ShopHelper;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.einvoicing.OrderState;
import com.rssl.phizic.gate.einvoicing.ShopOrder;
import com.rssl.phizic.gate.einvoicing.ShopOrderService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.DateHelper;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Операция для отображения страницы Мои интернет заказы.
 *
 * @author bogdanov
 * @ created 30.05.2013
 * @ $Author$
 * @ $Revision$
 */

public class InternetOrderListOperation extends OperationBase implements ListEntitiesOperation
{
	private final static Map<String, OrderState[]> STATE_TO_FILTER_STATES = new HashMap<String, OrderState[]>();
	static
	{
		STATE_TO_FILTER_STATES.put(null, new OrderState[]{OrderState.CREATED, OrderState.RELATED, OrderState.PAYMENT});
		//новый
		STATE_TO_FILTER_STATES.put("NEW", new OrderState[]{OrderState.CREATED, OrderState.RELATED});
		//введен
		STATE_TO_FILTER_STATES.put("SAVED", new OrderState[]{OrderState.PAYMENT});
		//в работе
		STATE_TO_FILTER_STATES.put("DISPATCHED", new OrderState[]{OrderState.WRITE_OFF, OrderState.ERROR});
		//отменен
		STATE_TO_FILTER_STATES.put("CANCELED", new OrderState[]{OrderState.CANCELED});
		//оплачен
		STATE_TO_FILTER_STATES.put("EXECUTED", new OrderState[]{OrderState.EXECUTED, OrderState.PARTIAL_REFUND});
		//оплачен /оформлен возврат
		STATE_TO_FILTER_STATES.put("EXECUTED_RETURN", new OrderState[]{OrderState.PARTIAL_REFUND});
		//возврат
		STATE_TO_FILTER_STATES.put("RECALLED", new OrderState[]{OrderState.REFUND});
		// отложенные
		STATE_TO_FILTER_STATES.put("DELAYED", new OrderState[]{OrderState.DELAYED});
		//Все операции
		STATE_TO_FILTER_STATES.put("ALL", new OrderState[]{});
	}
	/**
	 * Список заказов клиента.
	 *
	 * @param dateFrom дата от
	 * @param dateTo дата до
	 * @param amountFrom сумма от
	 * @param amountTo сумма до
	 * @param currency валюта
	 * @param status статус заказа.
	 * @return спсиок заказов.
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public List<ShopOrder> getOrders(Calendar dateFrom, Calendar dateTo, BigDecimal amountFrom, BigDecimal amountTo, String currency, String status) throws BusinessException, BusinessLogicException
	{
		try
		{
			return  GateSingleton.getFactory().service(ShopOrderService.class).getOrdersByProfileHistory(ShopHelper.get().getProfileHistory(AuthenticationContext.getContext()),
					dateFrom, dateTo, DateHelper.getMaximumDate(), amountFrom, amountTo, currency, STATE_TO_FILTER_STATES.get(status));
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}
}
