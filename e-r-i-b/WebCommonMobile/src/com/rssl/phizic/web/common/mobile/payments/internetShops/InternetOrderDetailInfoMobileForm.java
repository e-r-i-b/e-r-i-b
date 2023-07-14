package com.rssl.phizic.web.common.mobile.payments.internetShops;

import com.rssl.phizic.operations.payment.internetShops.AirlineInfo;
import com.rssl.phizic.operations.payment.internetShops.ShopOrdersInfo;
import com.rssl.phizic.web.common.client.payments.internetShops.AsyncOrderDetailInfoForm;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Детальная информация по заказу в интернет-магазине
 * @author Dorzhinov
 * @ created 13.06.2013
 * @ $Author$
 * @ $Revision$
 */
public class InternetOrderDetailInfoMobileForm extends AsyncOrderDetailInfoForm
{
	private final Set<ShopOrdersInfo> shopOrdersInfo = new HashSet<ShopOrdersInfo>();
	private final Set<AirlineInfo>    airlinesInfo   = new HashSet<AirlineInfo>();

	public Set<ShopOrdersInfo> getShopOrdersInfo()
	{
		return Collections.unmodifiableSet(shopOrdersInfo);
	}

	public Set<AirlineInfo> getAirlinesInfo()
	{
		return Collections.unmodifiableSet(airlinesInfo);
	}

	public void setShopOrdersInfo(Set<ShopOrdersInfo> orderDetailInfo)
	{
		this.shopOrdersInfo.addAll(orderDetailInfo);
	}

	public void setAirlinesInfo(Set<AirlineInfo> airlinesInfo)
	{
		this.airlinesInfo.addAll(airlinesInfo);
	}
}
