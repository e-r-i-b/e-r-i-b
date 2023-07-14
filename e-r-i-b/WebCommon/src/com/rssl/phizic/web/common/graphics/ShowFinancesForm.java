package com.rssl.phizic.web.common.graphics;

import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.IMAccountLink;
import com.rssl.phizic.business.resources.external.security.SecurityAccountLink;
import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.gate.security.SecurityAccount;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.*;

/**
 * @author rydvanskiy
 * @ created 11.04.2011
 * @ $Author$
 * @ $Revision$
 */

public class ShowFinancesForm extends ActionFormBase
{
	private List<AccountLink> accounts = new LinkedList<AccountLink>();
	private List<CardLink> cards = new ArrayList<CardLink>();
	private List<IMAccountLink> imAccounts = new ArrayList<IMAccountLink>();
	private List<SecurityAccountLink> securityAccountLinks = new ArrayList<SecurityAccountLink>();
	private Map<SecurityAccountLink, SecurityAccount> securityAccounts = new HashMap<SecurityAccountLink, SecurityAccount>();;

	private Map<String, CurrencyRate> currencyRateByCB     = new HashMap<String, CurrencyRate>();
	private Map<String, CurrencyRate> currencyRateByRemote = new HashMap<String, CurrencyRate>();
	private boolean fillCurrencyRate; // true - есть все курсы, false - некоторых курсов нет

	public List<AccountLink> getAccounts()
	{
		return accounts;
	}

	public void setAccounts(List<AccountLink> accounts)
	{
		this.accounts = accounts;
	}

	public List<CardLink> getCards()
	{
		return cards;
	}

	public void setCards(List<CardLink> cards)
	{
		this.cards = cards;
	}

	public List<IMAccountLink> getImAccounts()
	{
		return imAccounts;
	}

	public void setImAccounts(List<IMAccountLink> imAccounts)
	{
		this.imAccounts = imAccounts;
	}

	public List<SecurityAccountLink> getSecurityAccountLinks()
	{
		return securityAccountLinks;
	}

	public void setSecurityAccountLinks(List<SecurityAccountLink> securityAccountLinks)
	{
		this.securityAccountLinks = securityAccountLinks;
	}

	public Map<SecurityAccountLink, SecurityAccount> getSecurityAccounts()
	{
		return securityAccounts;
	}

	public void setSecurityAccounts(Map<SecurityAccountLink, SecurityAccount> securityAccounts)
	{
		this.securityAccounts = securityAccounts;
	}

	public boolean getFillCurrencyRate()
	{
		return fillCurrencyRate;
	}

	public void setFillCurrencyRate(boolean fillCurrencyRate)
	{
		this.fillCurrencyRate = fillCurrencyRate;
	}

	public boolean getIsWidget()
	{
		return false;
	}

	public void setCurrencyRateByCB(Map<String, CurrencyRate> currencyRateByCB)
	{
		this.currencyRateByCB.putAll(currencyRateByCB);
	}

	public void setCurrencyRateByRemote(Map<String, CurrencyRate> currencyRateByRemote)
	{
		this.currencyRateByRemote.putAll(currencyRateByRemote);
	}

	public Map<String, CurrencyRate> getCurrencyRateByCB()
	{
		return Collections.unmodifiableMap(currencyRateByCB);
	}

	public Map<String, CurrencyRate> getCurrencyRateByRemote()
	{
		return Collections.unmodifiableMap(currencyRateByRemote);
	}
}
