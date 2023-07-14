package com.rssl.phizic.business.deposits;

/**
 * Сущность является частью сущности "Депозитный продукт".
 * Хранит поля необхидимые для связи депозитного продукта и вклада ПФП.
 * @author lepihina
 * @ created 22.07.2013
 * @ $Author$
 * @ $Revision$
 */
public class DepositProductPart
{
	private Long id;
	private String name;
	private Long productId;

	/**
	 * @return идентификатор
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id - идентификатор
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return название
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name - название
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return номер вида вклада из внешнего справочника
	 */
	public Long getProductId()
	{
		return productId;
	}

	/**
	 * @param productId - номер вида вклада из внешнего справочника
	 */
	public void setProductId(Long productId)
	{
		this.productId = productId;
	}
}
