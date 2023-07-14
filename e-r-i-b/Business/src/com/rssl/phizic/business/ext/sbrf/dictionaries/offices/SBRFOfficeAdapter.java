package com.rssl.phizic.business.ext.sbrf.dictionaries.offices;

import com.rssl.phizic.gate.Routable;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizicgate.manager.services.IDHelper;

import java.util.Map;

/**
 * @author osminin
 * @ created 23.04.2009
 * @ $Author$
 * @ $Revision$
 */

/**
 * Предназначен для адаптации в бизнесе подразделений сбрф
 */
public class SBRFOfficeAdapter implements Office, Routable
{
	private Comparable synchKey;
	private String name;
	private String BIC;
	private String address;
	private String telephone;
	private String sbidnt;
	private SBRFOfficeCodeAdapter code = new SBRFOfficeCodeAdapter();
	private boolean creditCardOffice;
	private boolean needUpdateCreditCardOffice;
	private boolean openIMAOffice;


	public SBRFOfficeAdapter(){}

	public SBRFOfficeAdapter(Office office)
	{
		synchKey  = office.getSynchKey();
		name      = office.getName();
		BIC       = office.getBIC();
		address   = office.getAddress();
		telephone = office.getTelephone();
		code      = new SBRFOfficeCodeAdapter(office.getCode());
		creditCardOffice = office.isCreditCardOffice();
		needUpdateCreditCardOffice = office.isNeedUpdateCreditCardOffice();
		openIMAOffice = office.isOpenIMAOffice();
	}

	public SBRFOfficeAdapter(Code code)
	{
		this.code = new SBRFOfficeCodeAdapter(code);
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
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

	public SBRFOfficeCodeAdapter getCode()
	{
		return code;
	}

	public void setCode(Code code)
	{
		if (code == null)
			return;
		this.code = new SBRFOfficeCodeAdapter(code);
	}

	public void buildCode(Map<String, Object> codeFields)
	{
		SBRFOfficeCodeAdapter gateCode = new SBRFOfficeCodeAdapter(
					(String) codeFields.get("region"),
					(String) codeFields.get("branch"),
					(String) codeFields.get("office")
				);
		code = gateCode;
	}

	public String getSbidnt()
	{
		return sbidnt;
	}

	public void setSbidnt(String sbidnt)
	{
		this.sbidnt = sbidnt;
	}

	public Comparable getSynchKey()
	{
		return synchKey;
	}

	public void updateFrom(DictionaryRecord that)
	{
		BeanHelper.copyProperties(this, that);
	}

	public void setSynchKey(Comparable synchKey)
	{
		this.synchKey = synchKey;
	}

	public boolean isOpenIMAOffice()
	{
		return openIMAOffice;
	}

	public void setOpenIMAOffice(boolean openIMAOffice)
	{
		this.openIMAOffice = openIMAOffice;
	}

	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		SBRFOfficeAdapter that = (SBRFOfficeAdapter) o;

		if (BIC != null ? !BIC.equals(that.BIC) : that.BIC != null) return false;
		if (address != null ? !address.equals(that.address) : that.address != null) return false;
		if (code != null ? !code.equals(that.code) : that.code != null) return false;
		if (name != null ? !name.equals(that.name) : that.name != null) return false;
		if (sbidnt != null ? !sbidnt.equals(that.sbidnt) : that.sbidnt != null) return false;
		if (synchKey != null ? !synchKey.equals(that.synchKey) : that.synchKey != null) return false;
		if (telephone != null ? !telephone.equals(that.telephone) : that.telephone != null) return false;
		if (creditCardOffice != that.isCreditCardOffice()) return false;
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

	public String toString()
	{
		return (String) synchKey;
	}

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

	public boolean isCreditCardOffice()
	{
		return creditCardOffice;
	}

	public void setCreditCardOffice(Boolean creditCardOffice)
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
}