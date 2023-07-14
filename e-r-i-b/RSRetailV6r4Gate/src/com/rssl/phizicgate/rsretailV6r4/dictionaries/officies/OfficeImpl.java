package com.rssl.phizicgate.rsretailV6r4.dictionaries.officies;

import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizgate.common.routable.OfficeBase;

import java.util.Map;

/**
 * @author osminin
 * @ created 24.04.2009
 * @ $Author$
 * @ $Revision$
 */

public class OfficeImpl extends OfficeBase
{
    private Code code = new CodeImpl();

	public OfficeImpl() {}

	public OfficeImpl(Office office)
	{
		this.synchKey   = office.getSynchKey();
		this.name       = office.getName();
        this.address    = office.getAddress();
		this.telephone  = office.getTelephone();
		this.BIC        = office.getBIC();
		this.code       = new CodeImpl(office.getCode());
		this.creditCardOffice = office.isCreditCardOffice();
		this.needUpdateCreditCardOffice = office.isNeedUpdateCreditCardOffice();
		this.openIMAOffice = office.isOpenIMAOffice();
	}

    public void setAddress(String address)
    {
	    //todo заплатка на случай, если имя филиала пустое
	    if( ( name == null ) || ( name.length() == 0 ) )
	    {
		    this.name = address;
	    }
	    this.address = address;
    }

    public Code getCode()
    {
        return code;
    }

	public void buildCode(Map<String,Object> codeFields)
	{
		this.code = new CodeImpl((String) codeFields.get("id"));
	}

	/**
	 * @param code код - объект, однозначно идентифицирующий офис в системе
	 */
    public void setCode(Code code)
    {
        this.code = new CodeImpl(code);
    }

	public int hashCode()
	{
		int result = (synchKey != null ? synchKey.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (address != null ? address.hashCode() : 0);
		result = 31 * result + (telephone != null ? telephone.hashCode() : 0);
		result = 31 * result + (code != null ? code.hashCode() : 0);
		result = 31 * result + (creditCardOffice ? 1 : 0);
		result = 31 * result + (openIMAOffice ? 1 : 0);

		return result;
	}

	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		OfficeImpl office = (OfficeImpl) o;

		if (address != null ? !address.equals(office.address) : office.address != null) return false;
		if (code != null ? !code.equals(office.code) : office.code != null) return false;
		if (name != null ? !name.equals(office.name) : office.name != null) return false;
		if (synchKey != null ? !synchKey.equals(office.synchKey) : office.synchKey != null) return false;
		if (telephone != null ? !telephone.equals(office.telephone) : office.telephone != null) return false;
		if (creditCardOffice != office.isCreditCardOffice()) return false;
		if (openIMAOffice != office.isOpenIMAOffice()) return false;

		return true;
	}
}