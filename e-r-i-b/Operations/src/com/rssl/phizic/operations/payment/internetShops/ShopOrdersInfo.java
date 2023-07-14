package com.rssl.phizic.operations.payment.internetShops;

import java.math.BigDecimal;

/**
 *
 * @author Balovtsev
 * @since 20.03.2015.
 */
public class ShopOrdersInfo
{
	private String productName;
	private String productDescription;
	private String shopCount;
	private String shopCurrency;
	private BigDecimal shopAmount;

	public void setProductName(String productName)
	{
		this.productName = productName;
	}

	public String getProductName()
	{
		return productName;
	}

	public void setProductDescription(String productDescription)
	{
		this.productDescription = productDescription;
	}

	public String getProductDescription()
	{
		return productDescription;
	}

	public void setShopCount(String shopCount)
	{
		this.shopCount = shopCount;
	}

	public String getShopCount()
	{
		return shopCount;
	}

	public void setShopAmount(BigDecimal shopAmount)
	{
		this.shopAmount = shopAmount;
	}

	public BigDecimal getShopAmount()
	{
		return shopAmount;
	}

	public void setShopCurrency(String shopCurrency)
	{
		this.shopCurrency = shopCurrency;
	}

	public String getShopCurrency()
	{
		return shopCurrency;
	}
}
