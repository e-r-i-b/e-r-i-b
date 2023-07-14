package com.rssl.phizicgate.esberibgate.types;

import com.rssl.phizic.gate.ima.IMAOperationType;
import com.rssl.phizicgate.esberibgate.ws.generated.IMAOperCurType_Type;

import java.util.HashMap;
import java.util.Map;

/** 
 * @ author: Gololobov
 * @ created: 16.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class IMAOperationTypeWrapper
{
	private static final Map<IMAOperCurType_Type, IMAOperationType> imaOperationTypeMap = new HashMap<IMAOperCurType_Type, IMAOperationType>();

	static
	{
		imaOperationTypeMap.put(IMAOperCurType_Type.CS_METAL,IMAOperationType.CS_METAL);
		imaOperationTypeMap.put(IMAOperCurType_Type.CS_MONEY,IMAOperationType.CS_MONEY);
	}

	public static IMAOperationType getIMAOperationType(IMAOperCurType_Type imaOperationType)
	{
		return imaOperationTypeMap.get(imaOperationType);
	}
}
