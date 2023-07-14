package com.rssl.phizic.gate.office;

import com.rssl.phizgate.common.routable.OfficeBase;
import com.rssl.phizic.gate.dictionaries.officies.Code;

import java.util.Map;

/**
 * ќфис
 *
 * @author khudyakov
 * @ created 08.05.14
 * @ $Author$
 * @ $Revision$
 */
public class ExtendedOfficeImpl extends OfficeBase
{
	private Code code;

	public ExtendedOfficeImpl()
	{}

	public ExtendedOfficeImpl(Code code)
	{
		this.code = new ExtendedCodeImpl(code);
	}

	public Code getCode()
	{
		return code;
	}

	public void setCode(Code code)
	{
		this.code = code;
	}

	public void buildCode(Map<String, Object> codeFields)
	{

	}
}
