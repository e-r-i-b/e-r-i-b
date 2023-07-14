package com.rssl.phizicgate.shopclient;

/**
 * @author gulov
 * @ created 05.04.2011
 * @ $Authors$
 * @ $Revision$
 */

/**
 * Бин с данными платёжного поручения ОЗОН
 */
public class WebShopPayOrder
{
	/**
	 * Идентификатор документа в системе ЕРИБ
	 */
	private final String uuId;

	/**
	 * Идентификатор документа в интернет-магазине
	 */
	private final String externalId;

	/**
	 * Текущий статус документа
	 */
	private final String state;

	private final String utrrno; // Уникальный код операции SVFE

	///////////////////////////////////////////////////////////////////////////

	public WebShopPayOrder(String uuId, String externalId, String state, String utrrno)
	{
		this.uuId = uuId;
		this.externalId = externalId;
		this.state = state;
		this.utrrno = utrrno;
	}

	public String getUuId()
	{
		return uuId;
	}

	public String getExternalId()
	{
		return externalId;
	}

	public String getState()
	{
		return state;
	}

	public String getUtrrno()
	{
		return utrrno;
	}
}
