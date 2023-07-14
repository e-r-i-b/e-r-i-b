package com.rssl.phizic.config;

import com.rssl.phizic.utils.StringHelper;

/**
 * Категории настроек
 * @author gladishev
 * @ created 25.01.14
 * @ $Author$
 * @ $Revision$
 */
public class PropertyCategory
{
	public static final PropertyCategory Phizic = new PropertyCategory("phiz");
	public static final PropertyCategory CSABack = new PropertyCategory("csa-back");
	public static final PropertyCategory CSAFront = new PropertyCategory("phiz-csa");
	public static final PropertyCategory OfflineDoc = new PropertyCategory("phiz-basket-proxy-listener");
	public static final PropertyCategory CSAAdmin = new PropertyCategory("phiz-csa-admin");
	public static final PropertyCategory CSAAdminScheduler = new PropertyCategory("phiz-csa-admin-sched");
	public static final PropertyCategory Log = new PropertyCategory("phiz-log");
	public static final PropertyCategory RSA = new PropertyCategory("phiz-rsa");

	private String value;

	public PropertyCategory(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		PropertyCategory category = (PropertyCategory) o;
		return StringHelper.equals(category.getValue(), this.getValue());
	}
}
