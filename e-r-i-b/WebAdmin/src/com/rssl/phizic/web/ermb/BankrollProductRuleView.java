package com.rssl.phizic.web.ermb;

/**
 * Сущность для просмотра правила включения видимости продуктов по умолчанию
 * @author Rtischeva
 * @ created 03.12.13
 * @ $Author$
 * @ $Revision$
 */
public class BankrollProductRuleView
{
	private Long id;
	private String name;
	private String condition;
	private String productsVisibility;
	private String productsNotification;
	private boolean status;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getCondition()
	{
		return condition;
	}

	public void setCondition(String condition)
	{
		this.condition = condition;
	}

	public String getProductsVisibility()
	{
		return productsVisibility;
	}

	public void setProductsVisibility(String productsVisibility)
	{
		this.productsVisibility = productsVisibility;
	}

	public String getProductsNotification()
	{
		return productsNotification;
	}

	public void setProductsNotification(String productsNotification)
	{
		this.productsNotification = productsNotification;
	}

	public boolean isStatus()
	{
		return status;
	}

	public void setStatus(boolean status)
	{
		this.status = status;
	}
}
