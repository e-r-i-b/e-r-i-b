package com.rssl.phizicgate.esberibgate.utils;

/**
 * @author lukina
 * @ created 15.03.2013
 * @ $Author$
 * @ $Revision$
 * Объектное представление компоситного идентификатора страхового продукта
 * <entityId>^<loginId>
 */

public class InsuranceCompositeId
{
	public static final String ID_DELIMETER = "^";

	protected String entityId;
	private Long loginId;

	public InsuranceCompositeId(String id)
	{
		String[] strings = id.trim().split("\\^");
		entityId = strings.length > 0 ? strings[0] : null;
		loginId = strings.length > 1 ? Long.parseLong(strings[1]) : null;
	}

	public InsuranceCompositeId(String entityId, Long loginId)
	{
		this.entityId = entityId;
		this.loginId = loginId;
	}

	/**
	 * @return Идентифкатор продукта во внешней системе.
	 */
	public String getEntityId()
	{
		return entityId;
	}

	/**
	 * @return идентификатор владельца продукта в ИКФЛ
	 */
	public Long getLoginId()
	{
		return loginId;
	}

	public String toString()
	{
		StringBuilder compositeId = new StringBuilder();
		compositeId.append(entityId).append(ID_DELIMETER);
		compositeId.append(loginId);
		return compositeId.toString();
	}
}
