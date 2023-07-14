package com.rssl.phizicgate.wsgateclient.services.types;

import com.rssl.phizic.gate.payments.systems.recipients.BusinessRecipientInfo;
import com.rssl.phizic.gate.payments.systems.recipients.Service;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.dictionaries.officies.Office;

import java.math.BigDecimal;

/**
 * @author khudyakov
 * @ created 20.03.2011
 * @ $Author$
 * @ $Revision$
 */
public class BusinessRecipientInfoImpl implements BusinessRecipientInfo
{
	private BigDecimal maxCommissionAmount;
	private BigDecimal minCommissionAmount;
	private BigDecimal commissionRate;
	private Boolean propsOnline;
	private Boolean deptAvailable;
	private Service service;
	private String name;
	private String description;
	private Boolean main;
	private Comparable synchKey;
	private String INN;
	private String KPP;
	private String account;
	private ResidentBank bank;
	private String transitAccount;

	public BigDecimal getMaxCommissionAmount()
	{
		return maxCommissionAmount;
	}

	public void setMaxCommissionAmount(BigDecimal maxCommissionAmount)
	{
		this.maxCommissionAmount = maxCommissionAmount;
	}

	public BigDecimal getMinCommissionAmount()
	{
		return minCommissionAmount;
	}

	public void setMinCommissionAmount(BigDecimal minCommissionAmount)
	{
		this.minCommissionAmount = minCommissionAmount;
	}

	public BigDecimal getCommissionRate()
	{
		return commissionRate;
	}

	public void setCommissionRate(BigDecimal commissionRate)
	{
		this.commissionRate = commissionRate;
	}

	public Boolean isPropsOnline()
	{
		return propsOnline;
	}

	public void setPropsOnline(Boolean propsOnline)
	{
		this.propsOnline = propsOnline;
	}

	public Boolean isDeptAvailable()
	{
		return deptAvailable;
	}

	public void setDeptAvailable(Boolean deptAvailable)
	{
		this.deptAvailable = deptAvailable;
	}

	public Service getService()
	{
		return service;
	}

	public void setService(Service service)
	{
		this.service = service;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Boolean isMain()
	{
		return main;
	}

	public void setMain(Boolean main)
	{
		this.main = main;
	}

	public Comparable getSynchKey()
	{
		return synchKey;
	}

	public void setSynchKey(Comparable synchKey)
	{
		this.synchKey = synchKey;
	}

	public String getINN()
	{
		return INN;
	}

	public void setINN(String INN)
	{
		this.INN = INN;
	}

	public String getKPP()
	{
		return KPP;
	}

	public void setKPP(String KPP)
	{
		this.KPP = KPP;
	}

	public String getAccount()
	{
		return account;
	}

	public void setAccount(String account)
	{
		this.account = account;
	}

	public ResidentBank getBank()
	{
		return bank;
	}

	public void setBank(ResidentBank bank)
	{
		this.bank = bank;
	}

	public String getTransitAccount()
	{
		return transitAccount;
	}

	public void setTransitAccount(String transitAccount)
	{
		this.transitAccount = transitAccount;
	}

	public void updateFrom(DictionaryRecord that)
	{

	}
}