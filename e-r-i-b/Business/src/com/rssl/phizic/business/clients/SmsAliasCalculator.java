package com.rssl.phizic.business.clients;

import com.rssl.phizic.business.resources.external.ErmbProductLink;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.common.types.annotation.ThreadSafe;
import com.rssl.phizic.common.types.annotation.Stateless;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * @author Erkin
 * @ created 02.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Калькулятор СМС-алиасов
 */
@ThreadSafe
@Stateless
public abstract class SmsAliasCalculator
{
	private static final int MIN_ALIAS_LENGTH = 4;

	private static final int MAX_ALIAS_LENGTH = 7;

	///////////////////////////////////////////////////////////////////////////

	protected abstract String getProductNumber(Object product);

	public static final SmsAliasCalculator PRODUCT = new SmsAliasCalculator()
	{
		@Override
		protected String getProductNumber(Object product)
		{
			if (product instanceof Account)
				return ((Account)product).getNumber();

			if (product instanceof Card)
				return ((Card)product).getNumber();

			if (product instanceof Loan)
				return ((Loan)product).getAccountNumber();

			throw new UnsupportedOperationException();
		}
	};

	public static final SmsAliasCalculator LINK = new SmsAliasCalculator()
	{
		@Override
		protected String getProductNumber(Object product)
		{
			ErmbProductLink link = (ErmbProductLink) product;
			return link.getNumber();
		}
	};

	/**
	 * Вычисляет уникальные (в рамках списка) СМС-алиасы для банковских продуктов.
	 *
	 * 1. В случае недостижимости требования уникальности алиасов возвращается пустая мапа.
	 *
	 * 2. Не для всякого продукта может быть посчитан алиас.
	 * Если алиас не может быть посчитан для продукта, продукт не будет присутствовать в мапе.
	 * На данный момент реализован расчёт алиасов следующих классов продуктов:
	 * @see Account - счета
	 * @see Card - карты
	 * @see Loan - кредиты
	 *
	 * @param products - перечень продуктов (can be null, can be empty)
	 * @return мапа "продукт -> алиас" (can be empty)
	 */
	public Map<Object, String> computeSmsAutoAliases(Collection<Object> products)
	{
		if (CollectionUtils.isEmpty(products))
			return Collections.emptyMap();

		// Мапа-результат
		Map<Object, String> aliasesMap = new HashMap<Object, String>(products.size());
		// Сет для учёта уникальности алиасов
		Set<String> aliasesSet = new HashSet<String>(products.size());

		boolean done = false;
		int unsupportedProductCount = 0;
		for (int length = MIN_ALIAS_LENGTH; !done && (length <= MAX_ALIAS_LENGTH); length++)
		{
			done = true;
			unsupportedProductCount = 0;
			for (Object product : products)
			{
				try
				{
					String alias = computeSmsAutoAlias(product, length);
					if (StringHelper.isEmpty(alias))
						continue;

					if (!aliasesSet.add(alias)) {
						aliasesMap.clear();
						aliasesSet.clear();
						done = false;
						break;
					}

					aliasesMap.put(product, alias);
				}
				catch (UnsupportedOperationException e)
				{
					unsupportedProductCount++;
				}
			}
		}

		if (aliasesMap.isEmpty() && unsupportedProductCount != products.size())
			throw new RuntimeException();
		return aliasesMap;
	}

	/**
	 * Вычисляет автоматический СМС-алиас продукта
	 * @param product - продукт
	 * @param length - длина алиаса
	 * @return автоматический СМС-алиас продукта или null, если для продукта нельзя посчитать алиас
	 */
	private String computeSmsAutoAlias(Object product, int length)
	{
		String number = getProductNumber(product);
		return StringUtils.right(number, length);
	}
}
