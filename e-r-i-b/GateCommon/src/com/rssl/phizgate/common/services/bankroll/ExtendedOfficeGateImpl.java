package com.rssl.phizgate.common.services.bankroll;

import com.rssl.phizgate.common.routable.OfficeBase;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.dictionaries.officies.Office;

import java.util.Map;

/**
 * @author egorova
 * @ created 07.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class ExtendedOfficeGateImpl extends OfficeBase
{
	private String sbidnt;
	protected Code code = new ExtendedCodeGateImpl();

	public ExtendedOfficeGateImpl(){}

	public ExtendedOfficeGateImpl(Office office)
	{
		synchKey  = office.getSynchKey();
		name      = office.getName();
		BIC       = office.getBIC();
		address   = office.getAddress();
		telephone = office.getTelephone();
		code      = new ExtendedCodeGateImpl(office.getCode());
		creditCardOffice = office.isCreditCardOffice();
		needUpdateCreditCardOffice = office.isNeedUpdateCreditCardOffice();
		openIMAOffice = office.isOpenIMAOffice();
	}

	public Code getCode()
	{
		return code;
	}

	public void setCode(Code code)
	{
		if (code == null)
			return;
		this.code = new ExtendedCodeGateImpl(code);
	}

	public void buildCode(Map<String, Object> codeFields)
	{
		ExtendedCodeGateImpl newCode = new ExtendedCodeGateImpl();
		if(codeFields.get("region") != null) newCode.setRegion((String) codeFields.get("region"));
		if(codeFields.get("branch") != null) newCode.setBranch((String) codeFields.get("branch"));
		if(codeFields.get("office") != null) newCode.setOffice((String) codeFields.get("office"));
		code = newCode;
	}

	public String getSbidnt()
	{
		return sbidnt;
	}

	public void setSbidnt(String sbidnt)
	{
		this.sbidnt = sbidnt;
	}

	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ExtendedOfficeGateImpl that = (ExtendedOfficeGateImpl) o;

		if (BIC != null ? !BIC.equals(that.BIC) : that.BIC != null) return false;
		if (address != null ? !address.equals(that.address) : that.address != null) return false;
		if (code != null ? !code.equals(that.code) : that.code != null) return false;
		if (name != null ? !name.equals(that.name) : that.name != null) return false;
		if (sbidnt != null ? !sbidnt.equals(that.sbidnt) : that.sbidnt != null) return false;
		if (synchKey != null ? !synchKey.equals(that.synchKey) : that.synchKey != null) return false;
		if (telephone != null ? !telephone.equals(that.telephone) : that.telephone != null) return false;
		if (creditCardOffice!= that.isCreditCardOffice()) return false;
		if (openIMAOffice != that.isOpenIMAOffice()) return false;

		return true;
	}

	public int hashCode()
	{
		int result = (synchKey != null ? synchKey.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (BIC != null ? BIC.hashCode() : 0);
		result = 31 * result + (address != null ? address.hashCode() : 0);
		result = 31 * result + (telephone != null ? telephone.hashCode() : 0);
		result = 31 * result + (sbidnt != null ? sbidnt.hashCode() : 0);
		result = 31 * result + (code != null ? code.hashCode() : 0);
		result = 31 * result + (creditCardOffice ? 1 : 0);
		result = 31 * result + (openIMAOffice ? 1 : 0);

		return result;
	}
}
