package com.rssl.phizic.business.dictionaries.asyncSearch;

import com.rssl.phizic.business.dictionaries.providers.AccountType;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderState;

/**
 *
 * Информация о поставщике, необходимая для "живого" поиска, только основное.
 *
 * @ author: Gololobov
 * @ created: 29.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class AsynchSearchServiceProvider
{
	private Long id;
	private String name;
	private String alias;
	private String legalName;
	private String account;
	private String INN;
	private String regionsList;
	private boolean mobileBankAllowed;
	private boolean autoPymentSupported;
	private AccountType accountType;
	private String externalSystemName;
	private boolean templateSupported;
	private boolean allowPayments;
	private ServiceProviderState state;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getAlias()
	{
		return alias;
	}

	public void setAlias(String alias)
	{
		this.alias = alias;
	}

	public String getLegalName()
	{
		return legalName;
	}

	public void setLegalName(String legalName)
	{
		this.legalName = legalName;
	}

	public String getAccount()
	{
		return account;
	}

	public void setAccount(String account)
	{
		this.account = account;
	}

	public String getINN()
	{
		return INN;
	}

	public void setINN(String inn)
	{
		this.INN = inn;
	}

	public String getRegionsList()
	{
		return regionsList;
	}

	public void setRegionsList(String regionsList)
	{
		this.regionsList = regionsList;
	}

	public boolean isMobileBankAllowed()
	{
		return mobileBankAllowed;
	}

	public void setMobileBankAllowed(boolean mobileBankAllowed)
	{
		this.mobileBankAllowed = mobileBankAllowed;
	}

	public boolean isAutoPymentSupported()
	{
		return autoPymentSupported;
	}

	public void setAutoPymentSupported(boolean autoPymentSupported)
	{
		this.autoPymentSupported = autoPymentSupported;
	}

	public AccountType getAccountType()
	{
		return accountType;
	}

	public void setAccountType(AccountType accountType)
	{
		this.accountType = accountType;
	}

	public String getExternalSystemName()
	{
		return externalSystemName;
	}

	public void setExternalSystemName(String externalSystemName)
	{
		this.externalSystemName = externalSystemName;
	}

	public boolean isTemplateSupported()
	{
		return templateSupported;
	}

	public void setTemplateSupported(boolean templateSupported)
	{
		this.templateSupported = templateSupported;
	}

	public boolean isAllowPayments()
	{
		return allowPayments;
	}

	public void setAllowPayments(boolean allowPayments)
	{
		this.allowPayments = allowPayments;
	}

	public ServiceProviderState getState()
	{
		return state;
	}

	public void setState(ServiceProviderState state)
	{
		this.state = state;
	}
}
