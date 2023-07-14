package com.rssl.phizic.business.ext.sbrf.deposits;

import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.NumericUtil;
import com.rssl.phizic.utils.StringHelper;

import java.util.Comparator;

/**
 * Компаратор для сравнения записей справочника процентных ставок по вкладам (DCF_TAR)
 * @author Pankin
 * @ created 02.03.15
 * @ $Author$
 * @ $Revision$
 */
public class DepositsDCFTARComparator implements Comparator<DepositsDCFTAR>
{
	public int compare(DepositsDCFTAR o1, DepositsDCFTAR o2)
	{
		if (o1 == null && o2 == null)
			return 0;

		if (o1 == null || o2 == null)
			return -1;

		int compareResult = NumericUtil.compare(o1.getType(), o2.getType());
		if (compareResult != 0)
			return compareResult;

		compareResult = NumericUtil.compare(o1.getSubType(), o2.getSubType());
		if (compareResult != 0)
			return compareResult;

		compareResult = NumericUtil.compare(o1.isForeignCurrency(), o2.isForeignCurrency());
		if (compareResult != 0)
			return compareResult;

		compareResult = NumericUtil.compare(o1.getClientCategory(), o2.getClientCategory());
		if (compareResult != 0)
			return compareResult;

		compareResult = NumericUtil.compare(o1.getCodeSROK(), o2.getCodeSROK());
		if (compareResult != 0)
			return compareResult;

		compareResult = DateHelper.nullSafeCompare(o1.getDateBegin(), o2.getDateBegin());
		if (compareResult != 0)
			return compareResult;

		compareResult = NumericUtil.compare(o1.getSumBegin(), o2.getSumBegin());
		if (compareResult != 0)
			return compareResult;

		compareResult = StringHelper.compare(o1.getCurrencyCode(), o2.getCurrencyCode());
		if (compareResult != 0)
			return compareResult;

		compareResult = NumericUtil.compare(o1.getSegment(), o2.getSegment());
		if (compareResult != 0)
			return compareResult;

		compareResult = NumericUtil.compare(o1.getSumEnd(), o2.getSumEnd());
		if (compareResult != 0)
			return compareResult;

		compareResult = NumericUtil.compare(o1.getBaseRate(), o2.getBaseRate());
		if (compareResult != 0)
			return compareResult;

		compareResult = NumericUtil.compare(o1.getViolationRate(), o2.getViolationRate());

		return compareResult;
	}
}
