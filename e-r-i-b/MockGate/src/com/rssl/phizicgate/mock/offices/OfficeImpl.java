package com.rssl.phizicgate.mock.offices;

import com.rssl.phizgate.common.routable.OfficeBase;
import com.rssl.phizgate.common.services.bankroll.ExtendedCodeGateImpl;
import com.rssl.phizic.gate.dictionaries.officies.Code;

import java.util.Map;

/**
 * @author mihaylov
 * @ created 29.07.2010
 * @ $Author$
 * @ $Revision$
 */

public class OfficeImpl extends OfficeBase
{
	private Code code;

	public Code getCode()
	{
		return code;
	}

	public void setCode(Code code)
	{
		this.code = code;
	}

	public void buildCode(Map<String,Object> codeFields)
	{
		ExtendedCodeGateImpl newCode = new ExtendedCodeGateImpl();
		if(codeFields.get("region") != null) newCode.setRegion((String) codeFields.get("region"));
		if(codeFields.get("branch") != null) newCode.setBranch((String) codeFields.get("branch"));
		if(codeFields.get("office") != null) newCode.setOffice((String) codeFields.get("office"));
		code = newCode;
	}
}
