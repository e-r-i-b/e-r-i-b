package com.rssl.phizic.business.persons;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author osminin
 * @ created 03.04.2009
 * @ $Author$
 * @ $Revision$
 */

public abstract class DataValidateConfig extends Config
{
	public static final String PHONE_FORMAT_REGEXP = "com.rssl.iccs.phone.format.regexp";
	public static final String PHONE_FORMAT_DESCRIPTION = "com.rssl.iccs.phone.format.description";

	protected DataValidateConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * ��������� ����������� ��������� ��� ��������� ���������� �����
	 * @return ���������� ���������
	 */

	public abstract String getPhoneFormatRegexp();

	/**
	 * ��������� ���������
	 * @return ���������
	 */

	public abstract String getPhoneFormatDescription();
}
