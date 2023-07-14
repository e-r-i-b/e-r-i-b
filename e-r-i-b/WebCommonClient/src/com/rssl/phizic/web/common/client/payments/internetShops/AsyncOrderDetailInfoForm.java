package com.rssl.phizic.web.common.client.payments.internetShops;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * форма для составления параметров заказа.
 *
 * @author bogdanov
 * @ created 06.06.2013
 * @ $Author$
 * @ $Revision$
 */

public class AsyncOrderDetailInfoForm extends ActionFormBase
{
	/**
	 * Номер заказа.
	 */
	private String orderUuid;
	/**
	 * параметры заказа.
	 */
	private String orderInfo;

	public String getOrderInfo()
	{
		return orderInfo;
	}

	public void setOrderInfo(String orderInfo)
	{
		this.orderInfo = orderInfo;
	}

	public String getOrderId()
	{
		return orderUuid;
	}

	public void setOrderId(String orderUuid)
	{
		this.orderUuid = orderUuid;
	}
}
