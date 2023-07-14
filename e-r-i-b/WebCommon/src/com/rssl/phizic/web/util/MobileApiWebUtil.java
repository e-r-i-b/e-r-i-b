package com.rssl.phizic.web.util;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.web.actions.UserAgentActionMapping;
import org.apache.struts.action.ActionMapping;

/**
 * ��������� ����� ��� mAPI. ������ ��� ���-������.
 * @author Rydvanskiy
 * @ created 21.01.2011
 * @ $Author$
 * @ $Revision$
 */

public class MobileApiWebUtil
{
	private static final String USER_AGENT_HEADER = "User-Agent";

	/**
	 * �������� �� ������, �������� � ���������� ���������� ��������� API
	 * Deprecated! ������������ com.rssl.phizic.util.ApplicationUtil#isMobileApi()
	 * ��������� ������ ��� ��������� 1.04. 
	 * @param mapping ������� ActionMapping
	 * @return �������� �� ������, �������� � �������������� API
	 */
	@Deprecated
	public static boolean isMobileApiRq(ActionMapping mapping)
	{
		// key-feature: ���� API
		// A. ���� ���� API v2.x, �� ����� ������ � ���������� ���������� ����������
		ApplicationConfig applicationConfig = ApplicationConfig.getIt();
		if (applicationConfig.getApplicationInfo().isMobileApi())
			return true;

		// B. ���� ������� ���������� USER-AGENT, �� ������� ��� ���� ������ � ���������� ����������
		if (mapping instanceof UserAgentActionMapping)
		{
			UserAgentActionMapping userAgentActionMapping = (UserAgentActionMapping) mapping;
			if (UserAgent.mobile.getName().equals(userAgentActionMapping.getUserAgent()))
				return true;
		}
		
		return false;
	}
}
