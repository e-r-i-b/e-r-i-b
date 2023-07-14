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
 * ����������� ���-�������
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
	 * ��������� ���������� (� ������ ������) ���-������ ��� ���������� ���������.
	 *
	 * 1. � ������ �������������� ���������� ������������ ������� ������������ ������ ����.
	 *
	 * 2. �� ��� ������� �������� ����� ���� �������� �����.
	 * ���� ����� �� ����� ���� �������� ��� ��������, ������� �� ����� �������������� � ����.
	 * �� ������ ������ ���������� ������ ������� ��������� ������� ���������:
	 * @see Account - �����
	 * @see Card - �����
	 * @see Loan - �������
	 *
	 * @param products - �������� ��������� (can be null, can be empty)
	 * @return ���� "������� -> �����" (can be empty)
	 */
	public Map<Object, String> computeSmsAutoAliases(Collection<Object> products)
	{
		if (CollectionUtils.isEmpty(products))
			return Collections.emptyMap();

		// ����-���������
		Map<Object, String> aliasesMap = new HashMap<Object, String>(products.size());
		// ��� ��� ����� ������������ �������
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
	 * ��������� �������������� ���-����� ��������
	 * @param product - �������
	 * @param length - ����� ������
	 * @return �������������� ���-����� �������� ��� null, ���� ��� �������� ������ ��������� �����
	 */
	private String computeSmsAutoAlias(Object product, int length)
	{
		String number = getProductNumber(product);
		return StringUtils.right(number, length);
	}
}
