package com.rssl.phizic.gate.mobilebank;

import com.rssl.phizic.utils.StringHelper;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Varchar(2) число мес€ца [01, 31]
 * @author Puzikov
 * @ created 01.07.14
 * @ $Author$
 * @ $Revision$
 */

public class MbkDayAdapter extends XmlAdapter<String, Long>
{
	@Override
	public Long unmarshal(String v) throws Exception
	{
		try
		{
			return Long.valueOf(v);
		}
		catch (NumberFormatException e)
		{
			throw new IllegalArgumentException("Ќе удалось разобрать строку: " + v + " в число", e);
		}
	}

	@Override
	public String marshal(Long v) throws Exception
	{
		return StringHelper.appendLeadingZeros(v.toString(), 2);
	}
}
