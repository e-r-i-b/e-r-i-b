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
 * ������� � �������� �������.
 */
public class PortfolioProduct
{
	private Long id;
	private DictionaryProductType productType;
	private String name;            //�������� ��������
	private Long imageId;             //������������� ��������
	private List<BaseProduct> baseProductList; //������ ������� ��������� � ��������.

	private PortfolioProductState state; //������ �������� � �������� 
	private Long dictionaryProductId;//������ �� ������� � �����������, �� ������ �������� ��� �������� ������ �������
									//�� �������� ��������� ������ �� �����������, ��� ��� � ����������� 6(����) �������� �� ������ �������
									//����� ���� �������� ������� �������.

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
	 * @return ��������� ��������. �������������� ��� ����� ���������� ������� ��������� � ��������.
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
	 * @param productType - ��� �����������
	 * @return ���������� ������������ ���������� ��������
	 */
	public BaseProduct getBaseProduct(DictionaryProductType productType)
	{
		for(BaseProduct product: baseProductList)
			if(product.getProductType() == productType)
				return product;
		return null;
	}

	/**
	 * @return ���������� ������� ������� ��� �������� �������� ���������� � ������� ����������
	 */
	public LinkedBaseProduct getLinkedBaseProduct()
	{
		for(BaseProduct product: baseProductList)
			if(product instanceof LinkedBaseProduct)
				return (LinkedBaseProduct)product;
		return null;
	}
}
