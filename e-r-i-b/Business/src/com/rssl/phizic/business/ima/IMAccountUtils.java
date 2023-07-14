package com.rssl.phizic.business.ima;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.utils.IMAConfig;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author Pankin
 * @ created 28.06.2011
 * @ $Author$
 * @ $Revision$
 */

public class IMAccountUtils
{
	/**
	 * �������� ��������������� ����� ���
	 * @param imAccountNumber ����� ���
	 * @return ��������������� ����� ���
	 */
	public static String getFormattedIMAccountNumber(String imAccountNumber)
	{
		if (StringHelper.isEmpty(imAccountNumber))
			return "";

		String mask = ConfigFactory.getConfig(IMAConfig.class).getNumberMask();
		String regexp = ConfigFactory.getConfig(IMAConfig.class).getNumberRegexp();

		return imAccountNumber.replaceAll(regexp, mask);
	}
}
