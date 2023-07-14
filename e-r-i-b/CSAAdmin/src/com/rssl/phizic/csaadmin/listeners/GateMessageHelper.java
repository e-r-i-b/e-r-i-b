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
 * Хелпер работы с гейтовыми сообщениями
 */

public class GateMessageHelper
{
	/**
	 * преобразовать схему прав к гейтовому представлению
	 * @param scheme схема прав
	 * @return гейтовое представление схемы прав
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
	 * закодировать строку
	 * @param data данные
	 * @return закодированные данные
	 */
	public static String encodeData(String data)
	{
		return Base64.encode(data);
	}
}
