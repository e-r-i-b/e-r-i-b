package com.rssl.phizgate.common.payments.offline.basket;

/**
 * @author vagin
 * @ created 29.05.14
 * @ $Author$
 * @ $Revision$
 * Информация о маршрутизации поступающих инвойсов. Маршрутизация осуществляется по внутреннему идентификатору заявки на подписку.
 */
public class BasketRouteInfo
{
	private String operUID;
	private Long blockNumber;

	public BasketRouteInfo(String operUID, Long blockNumber)
	{
		this.blockNumber = blockNumber;
		this.operUID = operUID;
	}

	public BasketRouteInfo() {}

	public String getOperUID()
	{
		return operUID;
	}

	public void setOperUID(String operUID)
	{
		this.operUID = operUID;
	}

	public Long getBlockNumber()
	{
		return blockNumber;
	}

	public void setBlockNumber(Long blockNumber)
	{
		this.blockNumber = blockNumber;
	}
}
