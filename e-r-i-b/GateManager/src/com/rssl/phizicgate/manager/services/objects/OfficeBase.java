package com.rssl.phizicgate.manager.services.objects;

import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.dictionaries.officies.Office;

import java.util.Map;

/**
 * @author Krenev
 * @ created 24.02.2010
 * @ $Author$
 * @ $Revision$
 */
public abstract class OfficeBase implements Office
{
	Office delegate;

	public OfficeBase(Office delegate)
	{
		this.delegate = delegate;
	}

	public Code getCode()
	{
		return delegate.getCode();
	}

	public void setCode(Code code)
	{
		throw new UnsupportedOperationException();
	}

	public void buildCode(Map<String, Object> codeFields)
	{
		throw new UnsupportedOperationException();
	}

	public void setSynchKey(Comparable synchKey)
	{
		throw new UnsupportedOperationException();
	}

	public String getBIC()
	{
		return delegate.getBIC();
	}

	public void setBIC(String BIC)
	{
		throw new UnsupportedOperationException();
	}

	public String getAddress()
	{
		return delegate.getAddress();
	}

	public void setAddress(String address)
	{
		throw new UnsupportedOperationException();
	}

	public String getTelephone()
	{
		return delegate.getTelephone();
	}

	public void setTelephone(String telephone)
	{
		throw new UnsupportedOperationException();
	}

	public String getName()
	{
		return delegate.getName();
	}

	public void setName(String name)
	{
		throw new UnsupportedOperationException();
	}

	public void updateFrom(DictionaryRecord that)
	{
		throw new UnsupportedOperationException();
	}

	public boolean isCreditCardOffice()
	{
		return delegate.isCreditCardOffice();
	}

	public void setCreditCardOffice(boolean creditCardOffice)
	{
		new UnsupportedOperationException();
	}

	public boolean isOpenIMAOffice()
	{
		return delegate.isOpenIMAOffice();
	}
	
	public void setOpenIMAOffice(boolean openIMAOffice)
	{
		new UnsupportedOperationException();
	}

	public boolean isNeedUpdateCreditCardOffice()
	{
		return delegate.isNeedUpdateCreditCardOffice();
	}

	public void setNeedUpdateCreditCardOffice(boolean needUpdateCreditCardOffice)
	{
		new UnsupportedOperationException();
	}


}
