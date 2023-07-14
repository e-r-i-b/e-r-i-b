package com.rssl.phizic.business.pfp.portfolio;

import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

/**
 * @author akrenev
 * @ created 10.05.2012
 * @ $Author$
 * @ $Revision$
 *
 * ���������� ��������� ���
 * �������:
 * 0. null
 * 1. ���������� �����������.
 * 2. ����������� ��������� ��������.
 * 3. ����������� �������������� ��������:
 *      3.1. ������� + ���;
 *      3.2. ������� + ��� + ���.
 * 4. ������.
 * 5. ������ �������������� �����.
 * 6. ������������ ������������� �����.
 */
public final class PFPProductComparator implements Comparator<PortfolioProduct>
{
	private static final List<DictionaryProductType> priorityList;
	private static final PFPProductComparator INSTANCE = new PFPProductComparator();

	static
	{
		priorityList = new ArrayList<DictionaryProductType>();
		priorityList.add(null);
		priorityList.add(DictionaryProductType.INSURANCE);
		priorityList.add(DictionaryProductType.COMPLEX_INSURANCE);
		priorityList.add(DictionaryProductType.COMPLEX_INVESTMENT_FUND);
		priorityList.add(DictionaryProductType.COMPLEX_INVESTMENT_FUND_IMA);
		priorityList.add(DictionaryProductType.ACCOUNT);
		priorityList.add(DictionaryProductType.FUND);
		priorityList.add(DictionaryProductType.IMA);
	}

	private PFPProductComparator(){}

	public static PFPProductComparator getInstance()
	{
		return INSTANCE;
	}

	public int compare(PortfolioProduct firstProduct, PortfolioProduct secondProduct)
	{
		if (firstProduct == null && secondProduct == null)
			return 0;

		if (firstProduct == null)
			return -1;
		
		if (secondProduct == null)
			return 1;

		int firstProductIndex = priorityList.indexOf(firstProduct.getProductType());
		int secondProductIndex = priorityList.indexOf(secondProduct.getProductType());
		return (int) Math.signum(firstProductIndex - secondProductIndex);
	}
}
