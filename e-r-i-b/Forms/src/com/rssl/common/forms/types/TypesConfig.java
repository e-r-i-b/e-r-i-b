package com.rssl.common.forms.types;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author Evgrafov
 * @ created 13.02.2006
 * @ $Author: bogdanov $
 * @ $Revision: 57189 $
 */
public abstract class TypesConfig extends Config
{
	protected TypesConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * ���������� ��� �� ��� ����� � ������� ���������� ���� ��� �� ������
	 */
	public abstract Type getType(String name);

	public abstract Type defaultType();
}
