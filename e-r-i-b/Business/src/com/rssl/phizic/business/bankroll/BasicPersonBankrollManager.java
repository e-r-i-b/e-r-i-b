package com.rssl.phizic.business.bankroll;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.bankroll.BankrollProductLink;
import com.rssl.phizic.bankroll.PersonBankrollManager;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.common.types.exceptions.UserErrorException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.person.PersonManagerBase;
import org.apache.commons.lang.ArrayUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Erkin
 * @ created 23.03.2013
 * @ $Author$
 * @ $Revision$
 */

public class BasicPersonBankrollManager extends PersonManagerBase implements PersonBankrollManager
{
	private static final Collection<Class<? extends BankrollProductLink>> KNOWN_BANKROLL_PRODUCTS;

	private static final Long NUMBER_OF_TRANSACTIONS = 10L;

	static
	{
		KNOWN_BANKROLL_PRODUCTS = new HashSet<Class<? extends BankrollProductLink>>();
		KNOWN_BANKROLL_PRODUCTS.add(CardLink.class);
		KNOWN_BANKROLL_PRODUCTS.add(AccountLink.class);
		KNOWN_BANKROLL_PRODUCTS.add(LoanLink.class);
	}

	private static final ExternalResourceService resourceService = new ExternalResourceService();

	private final Login login;

	private final ProductListUpdater productListUpdater;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param module - модуль, в котором работает менеджер (never null)
	 * @param person - клиент, с которым работает менеджер (never null)
	 */
	public BasicPersonBankrollManager(Module module, ActivePerson person)
	{
		super(module, person);
		this.login = person.getLogin();
		this.productListUpdater = new CompoundProductListUpdater(person);
	}

	/**
	 * Перезагружает продукты
	 */
	public void reloadProducts()
	{
		productListUpdater.updateProductList(true);

	}

	/**
	 * Загружает продукты, если они не загружены
	 */
	private void updateProducts()
	{
		productListUpdater.updateProductList(false);
	}

	public Collection<? extends BankrollProductLink> getAccounts()
	{
		return getProductLinks(AccountLink.class);
	}

	public Collection<? extends BankrollProductLink> getCards()
	{
		return getProductLinks(CardLink.class);
	}

	public Collection<? extends BankrollProductLink> getLoans()
	{
		return getProductLinks(LoanLink.class);
	}

	private <T extends ExternalResourceLink> Collection<T> getProductLinks(Class<T> productLinkClass)
	{
		updateProducts();
		try
		{
			return resourceService.getLinks(login, productLinkClass);
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new InternalErrorException(e);
		}
	}

	public BankrollProductLink findProductBySmsAlias(String smsAlias, Class<? extends BankrollProductLink>... searchClasses)
	{
		updateProducts();
		try
		{
			Collection<Class<? extends BankrollProductLink>> productClasses = filterUnknownProducts(searchClasses);
			for (Class<? extends BankrollProductLink> productClass : productClasses)
			{
				BankrollProductLink link = resourceService.findProductBySmsAlias(productClass, login, smsAlias);
				if (link != null)
					return link;
			}
			return null;
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException(e);
		}
	}

	private Collection<Class<? extends BankrollProductLink>> filterUnknownProducts(Class<? extends BankrollProductLink>... searchClasses)
	{
		if (ArrayUtils.isEmpty(searchClasses))
			return getKnownBankrollProducts();

		Set<Class<? extends BankrollProductLink>> classes = new HashSet<Class<? extends BankrollProductLink>>(getKnownBankrollProducts());
		classes.retainAll(Arrays.asList(searchClasses));
		return classes;
	}

	private Collection<Class<? extends BankrollProductLink>> getKnownBankrollProducts()
	{
		return KNOWN_BANKROLL_PRODUCTS;
	}

	public Currency getRURCurrency()
	{
		Currency rurCurrency;
		try
		{
			CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
			rurCurrency = currencyService.findByAlphabeticCode("RUR");
		}
		catch (GateException e)
		{
			throw new UserErrorException(e);
		}
		return rurCurrency;
	}

	public BankrollProductLink getPriorityCardForChargeOff(Money amount)
	{
		throw new UnsupportedOperationException("Операция получения приоритетной карты для списания не поддерживается");
	}

	public BankrollProductLink getPriorityCard()
	{
		throw new UnsupportedOperationException("Операция получения приоритетной карты для оплаты не поддерживается");
	}

	public Long getNumberOfTransactions()
	{
		return NUMBER_OF_TRANSACTIONS;
	}
}
