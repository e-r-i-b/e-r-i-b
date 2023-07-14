package com.rssl.phizic.csaadmin.listeners;

import com.Ostermiller.util.Base64;
import com.rssl.phizic.csaadmin.business.access.AccessScheme;
import com.rssl.phizic.csaadmin.listeners.generated.AccessSchemeType;

/**
 * @author akrenev
 * @ created 20.05.2014
 * @ $Author$
 * @ $Revision$
 *
 * ������ ������ � ��������� �����������
 */

public class GateMessageHelper
{
	/**
	 * ������������� ����� ���� � ��������� �������������
	 * @param scheme ����� ����
	 * @return �������� ������������� ����� ����
	 */
	public static AccessSchemeType toGate(AccessScheme scheme)
	{
		if (scheme == null)
			return null;

		AccessSchemeType accessSchemeType = new AccessSchemeType();
		accessSchemeType.setExternalId(scheme.getExternalId());
		accessSchemeType.setName(scheme.getName());
		accessSchemeType.setCategory(scheme.getCategory());
		accessSchemeType.setCAAdminScheme(scheme.isCAAdminScheme());
		accessSchemeType.setVSPEmployeeScheme(scheme.isVSPEmployeeScheme());
		accessSchemeType.setMailManagement(scheme.isMailManagement());
		return accessSchemeType;
	}

	/**
	 * ������������ ������
	 * @param data ������
	 * @return �������������� ������
	 */
	public static String encodeData(String data)
	{
		return Base64.encode(data);
	}
}
