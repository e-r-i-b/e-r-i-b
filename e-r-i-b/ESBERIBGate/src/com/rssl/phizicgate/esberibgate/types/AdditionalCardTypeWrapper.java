package com.rssl.phizicgate.esberibgate.types;

import com.rssl.phizic.gate.bankroll.AdditionalCardType;

import java.util.Map;
import java.util.HashMap;

/**
 * @author egorova
 * @ created 07.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class AdditionalCardTypeWrapper
{
	private static final Map<String, AdditionalCardType> additionalCardType = new HashMap<String, AdditionalCardType>();

	static
	{
		additionalCardType.put("Client2Client", AdditionalCardType.CLIENTTOCLIENT);
		additionalCardType.put("Client2Other", AdditionalCardType.CLIENTTOOTHER);
		additionalCardType.put("Other2Client", AdditionalCardType.OTHERTOCLIENT);
	}

	public static AdditionalCardType getAdditionalCardType(String addCardType)
	{
		return additionalCardType.get(addCardType);
	}
}