package com.rssl.phizicgate.esberibgate.types;

import com.rssl.phizic.gate.ima.IMAccountState;
import com.rssl.phizicgate.esberibgate.ws.generated.IMSStatusEnum_Type;

import java.util.HashMap;
import java.util.Map;

/**
 * Обёртка преобразования состояния ОМС из типа шины в гейтовый формат
 * @author Pankin
 * @ created 03.08.2011
 * @ $Author$
 * @ $Revision$
 */

public class IMAccountStateWrapper
{
	private static final Map<IMSStatusEnum_Type, IMAccountState> imaState = new HashMap<IMSStatusEnum_Type, IMAccountState>();

	static
	{
		imaState.put(IMSStatusEnum_Type.value1, IMAccountState.opened);
		imaState.put(IMSStatusEnum_Type.value2, IMAccountState.closed);
		imaState.put(IMSStatusEnum_Type.value3, IMAccountState.lost_passbook);
		imaState.put(IMSStatusEnum_Type.value4, IMAccountState.arrested);
	}

	public static IMAccountState getIMAccountState(IMSStatusEnum_Type imsStatus)
	{
		if (imsStatus == null)
			return null;
		IMAccountState state = imaState.get(imsStatus);
		// Страхуемся от неизвестных статусов. Считаем их закрытыми.
		return (state != null) ? state : IMAccountState.closed;
	}
}
