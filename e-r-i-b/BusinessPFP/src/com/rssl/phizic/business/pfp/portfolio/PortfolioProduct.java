package com.rssl.phizic.business.pfp.portfolio;

import com.rssl.phizic.business.pfp.portfolio.product.LinkedBaseProduct;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.business.pfp.portfolio.product.BaseProduct;
import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mihaylov
 * @ created 11.04.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Продукт в портфеле клиента.
 */
public class PortfolioProduct
{
	private Long id;
	private DictionaryProductType productType;
	private String name;            //название продукта
	private Long imageId;             //идентификатор картинки
	private List<BaseProduct> baseProductList; //список базовых продуктов в продукте.

	private PortfolioProductState state; //статус продукта в портфеле 
	private Long dictionaryProductId;//ссылка на продукт в справочнике, на основе которого был добавлен данный продукт
									//не вставляю отдельный объект из справочника, так как в справочнике 6(пока) объектов на основе которых
									//может быть добавлен продукт клиенту.

	public PortfolioProduct()
	{
		state = PortfolioProductState.ADD;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public DictionaryProductType getProductType()
	{
		return productType;
	}

	public void setProductType(DictionaryProductType productType)
	{
		this.productType = productType;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Long getImageId()
	{
		return imageId;
	}

	public void setImageId(Long imageId)
	{
		this.imageId = imageId;
	}

	public List<BaseProduct> getBaseProductList()
	{
		return baseProductList;
	}

	public void setBaseProductList(List<BaseProduct> baseProductList)
	{
		this.baseProductList = baseProductList;
	}

	public void addBaseProduct(BaseProduct baseProduct)
	{
		if(CollectionUtils.isEmpty(baseProductList))
			baseProductList = new ArrayList<BaseProduct>();
		baseProductList.add(baseProduct);
	}

	public Long getDictionaryProductId()
	{
		return dictionaryProductId;
	}

	public void setDictionaryProductId(Long dictionaryProductId)
	{
		this.dictionaryProductId = dictionaryProductId;
	}

	public PortfolioProductState getState()
	{
		return state;
	}

	public void setState(PortfolioProductState state)
	{
		this.state = state;
	}

	/**
	 * @return Стоимость продукта. Рассчитывается как сумма стоимостей базовых продуктов в портфеле.
	 */
	public Money getAmount()
	{
		Money amount = null;
		for(BaseProduct baseProduct: baseProductList)
			if(amount != null)
				amount = amount.add(baseProduct.getAmount());
			else
				amount = baseProduct.getAmount();
		return amount;
	}

	/**
	 * @param productType - тип подпродукта
	 * @return Возвращает определенный подпродукт продукта
	 */
	public BaseProduct getBaseProduct(DictionaryProductType productType)
	{
		for(BaseProduct product: baseProductList)
			if(product.getProductType() == productType)
				return product;
		return null;
	}

	/**
	 * @return Возвращает базовый продукт для которого возможно связывание с другими портфелями
	 */
	public LinkedBaseProduct getLinkedBaseProduct()
	{
		for(BaseProduct product: baseProductList)
			if(product instanceof LinkedBaseProduct)
				return (LinkedBaseProduct)product;
		return null;
	}
}
