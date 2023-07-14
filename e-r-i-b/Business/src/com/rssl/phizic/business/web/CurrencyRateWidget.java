package com.rssl.phizic.business.web;

import org.apache.commons.collections.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Виджет "Курсы"
 * @author gulov
 * @ created 18.07.2012
 * @ $Authors$
 * @ $Revision$
 */
public class CurrencyRateWidget extends Widget
{
	private static final Set<String> ALL_CURRENCY_CODES = new LinkedHashSet<String>(Arrays.asList("EUR", "USD"));

	private static final Set<String> ALL_IMA_CODES = new LinkedHashSet<String>(Arrays.asList("A98","A99","A76","A33"));

	//Курсы валют, которые нужно отображать в данном виджете
	private Set<String> showCurrencyCodes = ALL_CURRENCY_CODES;

	//Курсы металлов, которые нужно отображать в данном виджете
	private Set<String> showImaCodes = ALL_IMA_CODES;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * @return коды всех валют, используемых в виджете
	 */
	public static Set<String> getAllCurrencyCodes()
	{
		return Collections.unmodifiableSet(ALL_CURRENCY_CODES);
	}

	/**
	 * @return коды всех ОМС, используемых в виджете
	 */
	public static Set<String> getAllImaCodes()
	{
		return Collections.unmodifiableSet(ALL_IMA_CODES);
	}

	public Set<String> getShowCurrencyCodes()
	{
		return Collections.unmodifiableSet(showCurrencyCodes);
	}

	public void setShowCurrencyCodes(Set<String> showCurrencyCodes)
	{
		if (CollectionUtils.isEmpty(showCurrencyCodes))
			this.showCurrencyCodes = Collections.emptySet();
		else this.showCurrencyCodes = new LinkedHashSet<String>(showCurrencyCodes);
	}

	public Set<String> getShowImaCodes()
	{
		return Collections.unmodifiableSet(showImaCodes);
	}

	public void setShowImaCodes(Set<String> showImaCodes)
	{
		if (CollectionUtils.isEmpty(showCurrencyCodes))
			this.showCurrencyCodes = Collections.emptySet();
		else this.showImaCodes = new LinkedHashSet<String>(showImaCodes);
	}

	@Override
	protected Widget clone()
	{
		CurrencyRateWidget newWidget = (CurrencyRateWidget) super.clone();
		newWidget.setShowCurrencyCodes(showCurrencyCodes);
		newWidget.setShowImaCodes(showImaCodes);
		return newWidget;
	}
}
