package com.rssl.phizic.business.accounts;

import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.common.types.MockObject;

import java.util.Map;

/**
 * @author Omeliyanchuk
 * @ created 27.05.2010
 * @ $Author$
 * @ $Revision$
 */

public class MockOffice implements Office, MockObject
{
	private static String EMPTY_STRING="";
	private Code code;

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

	public void setSynchKey(Comparable synchKey)
	{

	}

	public String getBIC()
	{
		return EMPTY_STRING;
	}

	public void setBIC(String BIC)
	{

	}

	public String getAddress()
	{
		return EMPTY_STRING;
	}

	public void setAddress(String address)
	{

	}

	public String getTelephone()
	{
		return EMPTY_STRING;
	}

	public void setTelephone(String telephone)
	{

	}

	public String getName()
	{
		return EMPTY_STRING;
	}

	public void setName(String name)
	{

	}

	public boolean isCreditCardOffice()
	{
		return false;
	}

	public boolean isOpenIMAOffice()
	{
		return false;
	}

	public boolean isNeedUpdateCreditCardOffice()
	{
		return false;
	}

	public Comparable getSynchKey()
	{
		return EMPTY_STRING;
	}

	public void updateFrom(DictionaryRecord that)
	{

	}
}

