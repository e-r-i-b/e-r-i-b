package com.rssl.phizic.business.creditcards;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.creditcards.products.CreditCardProduct;
import com.rssl.phizic.business.creditcards.products.CreditCardProductService;
import com.rssl.phizic.business.loanCardOffer.LoanCardOfferShortCut;
import com.rssl.phizic.business.offers.LoanCardOffer;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.CurrencyUtils;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dorzhinov
 * @ created 24.06.2011
 * @ $Author$
 * @ $Revision$
 */
public class CreditCardsUtil
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final CreditCardProductService service = new CreditCardProductService();

	/**
	 * ѕолучить список всех карточных кредитных продуктов
	 * @return список всех карточных кредитных продуктов
	 */
	public static List<CreditCardProduct> getAllCreditCardProducts()
	{
		try
		{
			return service.getAll();
		}
		catch (BusinessException e)
		{
			log.error("ќшибка получени€ списка всех карточных кредитных продуктов", e);
			return new ArrayList<CreditCardProduct>();
		}
	}

	/**
	 * ѕолучить список доступных карточных кредитных продуктов
	 * @param currency
	 * @param creditLimit
	 * @return
	 */
	public static List<CreditCardProduct> getAvailableProducts(String currency, String creditLimit, String include)
	{
		try
		{
			return service.getAvailableProducts(currency, creditLimit, include);
		}
		catch (BusinessException e)
		{
			log.error("ќшибка получени€ списка доступных карточных кредитных продуктов", e);
			return new ArrayList<CreditCardProduct>();
		}
	}

	/**
	 * ќпределить есть ли опубликованные карточные кредитные продукты
	 * @return true/false
	 */
	public static Boolean isPublishedProductsExists()
	{
		try
		{
			return service.isPublishedProductsExists();
		}
		catch (BusinessException e)
		{
			log.error("ќшибка определени€ наличи€ опубликованных карточных кредитных продуктов", e);
			return false;
		}
	}

	/**
	 * ќпределить есть ли опубликованные карточные кредитные продукты
	 * @return true/false
	 */
	public static LoanCardOfferShortCut getLoanOfferClaimType()
	{
		try
		{
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			ActivePerson person = personData.getPerson();

			if (CollectionUtils.isEmpty(person.getPersonDocuments()))
			{
				return null;
			}

			if (!service.isPublishedProductsExists())
			{
				return null;
			}

			LoanCardOffer loanCardOffer = null;
			if (!PersonHelper.isGuest() || PersonHelper.isRegisteredGuest())
			{
				loanCardOffer = personData.getNewLoanCardOffer();
			}

			if (loanCardOffer == null)
			{
				if (!PersonHelper.hasRegularPassportRF())
				{
					return null;
				}

				return new LoanCardOfferShortCut("LoanCardClaim");
			}

			if (!CurrencyUtils.isRUR(loanCardOffer.getMaxLimit().getCurrency()))
			{
				return new LoanCardOfferShortCut("LoanCardClaim");
			}

			if (!(PersonHelper.isGuest() && PersonHelper.isRegisteredGuest()) && !PersonHelper.hasRegularPassportRF())
			{
				return null;
			}

			return new LoanCardOfferShortCut(loanCardOffer);
		}
		catch (Exception e)
		{
			log.error("ќшибка определени€ наличи€ опубликованных карточных кредитных продуктов", e);
			return null;
		}
	}
}
