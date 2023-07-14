package com.rssl.phizic.web.common.client.payments.internetShops;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * ����� ��� ����������� ���������� ������.
 *
 * @author bogdanov
 * @ created 06.06.2013
 * @ $Author$
 * @ $Revision$
 */

public class AsyncOrderDetailInfoForm extends ActionFormBase
{
	/**
	 * ����� ������.
	 */
	private String orderUuid;
	/**
	 * ��������� ������.
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
