package com.rssl.phizic.business.resources.external.comparator;

import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanHelper;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.common.types.CurrencyRateType;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyRateService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Comparator;

/**
 * @author vagin
 * @ created 18.09.14
 * @ $Author$
 * @ $Revision$
 * Компаратор для карт по убыванию суммы на карте. Если карт с одинаковым балансом несколько, то приоритет карте с наибольшим сроком действия.
 */
public class CardAmountComparator implements Comparator<CardLink>
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	protected static final PersonService personService = new PersonService();

	public int compare(CardLink link1, CardLink link2)
	{
		try
		{
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			String tarifPlanCode = personData.getProfile().getTariffPlanCode();
			Money rest1 = link1.getRest();
			Money rest2 = link2.getRest();

			CurrencyRateService service = GateSingleton.getFactory().service(CurrencyRateService.class);
			Currency link1Currency = rest1.getCurrency();
			Currency link2Currency = rest2.getCurrency();
			int compareResult = 0;
			//если сравниваем разные валюты.
			if (!link1Currency.compare(link2Currency))
			{
				BigDecimal rest1Decimal = rest1.getDecimal();
				BigDecimal rest2Decimal = rest2.getDecimal();
				Currency rubCurrency = MoneyUtil.getNationalCurrency();
				//если валюта не рубль - конвертируем к рублю согласно "курсам валют" и сравниваем.
				if (!link1Currency.compare(rubCurrency))
				{
					CurrencyRate rate = service.getRate(link1Currency, rubCurrency, CurrencyRateType.BUY_REMOTE, personData.getDepartment(), StringHelper.isNotEmpty(tarifPlanCode) ? tarifPlanCode : TariffPlanHelper.getUnknownTariffPlanCode());
					rest1Decimal = rest1Decimal.multiply(rate.getToValue().divide(rate.getFromValue(), 2, CurrencyRate.ROUNDING_MODE));
				}
				if (!link2Currency.compare(rubCurrency))
				{
					CurrencyRate rate = service.getRate(link2Currency, rubCurrency, CurrencyRateType.BUY_REMOTE, personData.getDepartment(), StringHelper.isNotEmpty(tarifPlanCode) ? tarifPlanCode : TariffPlanHelper.getUnknownTariffPlanCode());
					rest2Decimal = rest2Decimal.multiply(rate.getToValue().divide(rate.getFromValue(), 2, CurrencyRate.ROUNDING_MODE));
				}
				compareResult = rest2Decimal.compareTo(rest1Decimal);
			}
			else
			{
				compareResult = rest2.compareTo(rest1);
			}
			//если суммы остатков равны то приоритет у карты с большей датой действия.
			if (compareResult == 0)
			{
				Calendar card1ExpireDate = link1.getExpireDate();
				Calendar card2ExpireDate = link2.getExpireDate();
				if (card1ExpireDate != null && card2ExpireDate != null)
					compareResult = card2ExpireDate.compareTo(card1ExpireDate);
			}
			return compareResult;
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			return 0;
		}
	}
}
