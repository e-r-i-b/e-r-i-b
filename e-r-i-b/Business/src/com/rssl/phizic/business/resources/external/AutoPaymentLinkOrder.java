package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.auth.CommonLogin;

/**
 * Класс для линков автоплатежей с порядком их отображения
 * @ author gorshkov
 * @ created 26.09.13
 * @ $Author$
 * @ $Revision$
 */
public class AutoPaymentLinkOrder
{
	private Long    id;
	private Long loginId;
	private String  linkId;
	private int orderInd;

	/**
	 * @return ID записи
	 */
	public Long getId()
	{
	    return id;
	}

	/**
	 * @param id ID записи
	 */
	public void setId(Long id)
	{
	    this.id = id;
	}

	/**
	 * @return внешний id автоплатежа
	 */
	public String getLinkId()
	{
	    return linkId;
	}

	/**
	 * @param linkId внешний id автоплатежа
	 */
	public void setLinkId(String linkId)
	{
	    this.linkId = linkId;
	}

	/**
	 * @return логин клиента
	 */
	public Long getLoginId()
	{
	    return loginId;
	}

	/**
	 * @param loginId id логина клиента
	 */
	public void setLoginId(Long loginId)
	{
	    this.loginId = loginId;
	}

	/**
	 * @return порядок отображения автоплатежа
	 */
	public int getOrderInd()
	{
	    return orderInd;
	}

	/**
	 * @param orderInd порядок отображения автоплатежа
	 */
	public void setOrderInd(int orderInd)
	{
	    this.orderInd = orderInd;
	}


}
