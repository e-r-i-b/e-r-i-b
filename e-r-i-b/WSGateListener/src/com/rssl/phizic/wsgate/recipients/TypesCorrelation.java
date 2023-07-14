package com.rssl.phizic.wsgate.recipients;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

/**
 * @author khudyakov
 * @ created 20.03.2011
 * @ $Author$
 * @ $Revision$
 */
public class TypesCorrelation
{
	private static final Map<Class, Class> types = new HashMap<Class, Class>();

	static
	{
		types.put(com.rssl.phizic.gate.dictionaries.officies.Office.class, com.rssl.phizic.wsgate.recipients.generated.Office.class);
		types.put(com.rssl.phizic.gate.dictionaries.officies.Code.class, com.rssl.phizic.wsgate.recipients.generated.Code.class);
		types.put(com.rssl.phizic.gate.dictionaries.ResidentBank.class, com.rssl.phizic.wsgate.recipients.generated.ResidentBank.class);
		types.put(com.rssl.phizic.gate.payments.systems.recipients.Service.class, com.rssl.phizic.wsgate.recipients.generated.Service.class);

		types.put(com.rssl.phizic.wsgate.recipients.generated.Office.class, com.rssl.phizic.wsgate.types.OfficeImpl.class);
		types.put(com.rssl.phizic.wsgate.recipients.generated.Code.class, com.rssl.phizic.wsgate.types.CodeImpl.class);
		types.put(com.rssl.phizic.wsgate.recipients.generated.ResidentBank.class, com.rssl.phizic.gate.dictionaries.ResidentBank.class);
		types.put(com.rssl.phizic.wsgate.recipients.generated.Service.class, com.rssl.phizgate.common.payments.systems.recipients.ServiceImpl.class);
	}

	/**
	 * @return ��� ����������
	 */
	public static Map<Class, Class> getTypes()
	{
		return Collections.unmodifiableMap(types);
	}
}
