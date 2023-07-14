package com.rssl.phizic.business.cardProduct;

import com.rssl.phizic.business.ext.sbrf.dictionaries.CASNSICardProduct;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * @author gulov
 * @ created 29.09.2011
 * @ $Authors$
 * @ $Revision$
 */

/**
 * Класс-сущность "Карточный продукт"
 */
@SuppressWarnings({"JavaDoc"})
public class CardProduct
{
	private Long id;
	private String name;
	private CardProductType type = CardProductType.VIRTUAL;
	private Boolean online;
	private Calendar stopOpenDate;
	private List<CASNSICardProduct> kindOfProducts;

	/**
	 * Уникальный код продукта
	 */
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * Наименование продукта
	 */
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Тип продукта
	 */
	public CardProductType getType()
	{
		return type;
	}

	public void setType(CardProductType type)
	{
		this.type = type;
	}

	/**
	 * Признак публикации продукта
	 */
	public Boolean isOnline()
	{
		return online;
	}

	/**
	 * Признак публикации продукта
	 */
	public Boolean getOnline()
	{
		return online;
	}


	public void setOnline(Boolean online)
	{
		this.online = online;
	}

	/**
	 * Максимальная дата закрытия вида карточного продукта
	 */
	public Calendar getStopOpenDate()
	{
		return stopOpenDate;
	}

	public void setStopOpenDate(Calendar stopOpenDate)
	{
		this.stopOpenDate = stopOpenDate;
	}

	/**
	 * Список видов и подвидов продуктов
	 */
	public List<CASNSICardProduct> getKindOfProducts()
	{
		return Collections.unmodifiableList(kindOfProducts);
	}

	public void setKindOfProducts(List<CASNSICardProduct> kindOfProducts)
	{
		this.kindOfProducts = new ArrayList<CASNSICardProduct>(kindOfProducts);
	}
}
