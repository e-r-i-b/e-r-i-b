package com.rssl.phizgate.common.routable;

import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;
import com.rssl.phizic.gate.Routable;
import com.rssl.phizicgate.manager.services.IDHelper;

/**
 * @author hudyakov
 * @ created 10.12.2009
 * @ $Author$
 * @ $Revision$
 */

public abstract class OfficeBase extends DictionaryRecordBase implements Office, Routable
{
	protected String BIC;
    protected String address;
    protected String telephone;
    protected String name;
	protected Comparable synchKey;
	protected boolean creditCardOffice;
	protected boolean needUpdateCreditCardOffice;
	protected boolean openIMAOffice;

	public void storeRouteInfo(String info)
	{
		setSynchKey(IDHelper.storeRouteInfo((String) getSynchKey(), info));

	}

	public String restoreRouteInfo()
	{
		return IDHelper.restoreRouteInfo((String) getSynchKey());
	}

	public String removeRouteInfo()
	{
		String info = IDHelper.restoreRouteInfo((String) synchKey);
		setSynchKey(IDHelper.restoreOriginalId((String) synchKey));

		return info;
	}

	public String getBIC()
	{
		return BIC;
	}

	public void setBIC(String BIC)
	{
		this.BIC = BIC;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getTelephone()
	{
		return telephone;
	}

	public void setTelephone(String telephone)
	{
		this.telephone = telephone;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Comparable getSynchKey()
	{
		return synchKey;
	}

	public void setSynchKey(Comparable synchKey)
	{
		this.synchKey = synchKey;
	}

	public String toString()
	{
		return (String) synchKey;
	}

	public boolean isCreditCardOffice()
	{
		return creditCardOffice;
	}

	public void setCreditCardOffice(boolean creditCardOffice)
	{
		this.creditCardOffice = creditCardOffice;
	}


	public boolean isNeedUpdateCreditCardOffice()
	{
		return needUpdateCreditCardOffice;
	}

	public void setNeedUpdateCreditCardOffice(boolean needUpdateCreditCardOffice)
	{
		this.needUpdateCreditCardOffice = needUpdateCreditCardOffice;
	}

	public boolean isOpenIMAOffice()
	{
		return openIMAOffice;
	}

	public void setOpenIMAOffice(boolean openIMAOffice)
	{
		this.openIMAOffice = openIMAOffice;
	}
}
