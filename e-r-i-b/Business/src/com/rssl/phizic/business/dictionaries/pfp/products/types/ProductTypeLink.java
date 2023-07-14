package com.rssl.phizic.business.dictionaries.pfp.products.types;

/**
 * @author akrenev
 * @ created 25.06.2013
 * @ $Author$
 * @ $Revision$
 *
 * Ссылка, позволяющая выбрать продукт позже
 */

public class ProductTypeLink
{
	private String name;
	private String hint;

	/**
	 * @return название
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * задать название
	 * @param name название
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return подсказка
	 */
	public String getHint()
	{
		return hint;
	}

	/**
	 * задать подсказку
	 * @param hint подсказка
	 */
	public void setHint(String hint)
	{
		this.hint = hint;
	}
}
