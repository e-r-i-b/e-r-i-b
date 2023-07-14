package com.rssl.phizic.business.shop;

/**
 * @author Mescheryakova
 * @ created 06.12.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Класс, реализующий поля для заказа из интернет-магазина
 */

public class WebShop implements OrderInfo
{
	private Order order;                 // основная часть заказа
	private Long id;
	private String backUrl;             // url редиректа

	public Order getOrder()
	{
		return order;
	}

	public void setOrder(Order order)
	{
		this.order = order;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getBackUrl()
	{
		return backUrl;
	}

	public void setBackUrl(String backUrl)
	{
		this.backUrl = backUrl;
	}
}