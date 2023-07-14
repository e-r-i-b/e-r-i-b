package com.rssl.phizic.web.client.component;

import com.rssl.phizic.web.component.WidgetForm;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.IMAccountLink;
import com.rssl.phizic.common.types.CurrencyRate;

import java.util.*;

/**Form для виджета "Доступные средства"
 * @ author Rtischeva
 * @ created 19.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class MyFinancesWidgetForm extends WidgetForm
{
	private List<AccountLink> accounts = new LinkedList<AccountLink>();
	private List<CardLink> cards = new ArrayList<CardLink>();
	private List<IMAccountLink> imAccounts = new ArrayList<IMAccountLink>();

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

	public boolean getIsWidget()
	{
		return true;
	}

	public boolean isFillCurrencyRate()
	{
		return fillCurrencyRate;
	}

	public Map<String, CurrencyRate> getCurrencyRateByCB()
	{
		return Collections.unmodifiableMap(currencyRateByCB);
	}

	public void setCurrencyRateByCB(Map<String, CurrencyRate> currencyRateByCB)
	{
		this.currencyRateByCB.putAll(currencyRateByCB);
	}

	public Map<String, CurrencyRate> getCurrencyRateByRemote()
	{
		return Collections.unmodifiableMap(currencyRateByRemote);
	}

	public void setCurrencyRateByRemote(Map<String, CurrencyRate> currencyRateByRemote)
	{
		this.currencyRateByRemote.putAll(currencyRateByRemote);
	}

	public void setFillCurrencyRate(boolean fillCurrencyRate)
	{
		this.fillCurrencyRate = fillCurrencyRate;
	}
}
