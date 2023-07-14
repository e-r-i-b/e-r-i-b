package com.rssl.phizic.business.dictionaries.providers;

/**
 * ќператор мобильной св€зи, по которому есть возможность создани€ шаблона платежа
 * @author miklyaev
 * @ created 21.01.14
 * @ $Author$
 * @ $Revision$
 */

public class MobileProviderCode
{
	protected Long id;
	private String code;
	private String name;
	private String externalId;

	/**
	 * @return идентефикатор оператора в таблице
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * «адать идентефикатор оператора
	 * @param id - идентификатор
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return код оператора мобильной св€зи
	 */
	public String getCode()
	{
		return code;
	}

	/**
	 * «адать код оператора мобильной св€зи
	 * @param code - код
	 */
	public void setCode(String code)
	{
		this.code = code;
	}

	/**
	 * @return название оператора мобильной св€зи
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * «адать название оператора мобильной св€зи
	 * @param name - название
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return внешний идентефикатор оператора мобильной св€зи
	 */
	public String getExternalId()
	{
		return externalId;
	}

	/**
	 * «адать внешний идентефикатор оператора мобильной св€зи
	 * @param externalId - внешний идентефикатор
	 */
	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}
}
