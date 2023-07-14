package com.rssl.phizicgate.rsretailV6r20.dictionaries.officies;

import com.rssl.phizgate.common.services.bankroll.ExtendedCodeGateImpl;

import java.util.Map;

/**
 * @author osminin
 * @ created 23.04.2009
 * @ $Author$
 * @ $Revision$
 */

public class ExtendedOfficeGateImpl extends com.rssl.phizgate.common.services.bankroll.ExtendedOfficeGateImpl
{
	public void buildCode(Map<String, Object> codeFields)
	{
		ExtendedCodeGateImpl gateCode = new ExtendedCodeGateImpl(
				(String) codeFields.get("region"),
				(String) codeFields.get("branch"),
				(String) codeFields.get("office")
		);
		code = gateCode;
	}
}