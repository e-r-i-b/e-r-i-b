package com.rssl.phizic.business.dictionaries.promoCodesDeposit;

import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.NumericUtil;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.ObjectUtils;

import java.util.Comparator;

/**
 * Компаратор для справочника промо - кодов
 *
 * @ author: Gololobov
 * @ created: 29.12.14
 * @ $Author$
 * @ $Revision$
 */
public class PromoCodeDepositComparator implements Comparator<PromoCodeDeposit>
{
	public int compare(PromoCodeDeposit promoCodeDeposit1, PromoCodeDeposit promoCodeDeposit2)
	{
		if (promoCodeDeposit1 == null && promoCodeDeposit2 == null)
			return 0;

		if (promoCodeDeposit1 == null || promoCodeDeposit2 == null)
			return -1;

		int compareResult = NumericUtil.compare(promoCodeDeposit1.getCode(), promoCodeDeposit2.getCode());
		if (compareResult != 0)
			return compareResult;

		compareResult = NumericUtil.compare(promoCodeDeposit1.getCodeG(), promoCodeDeposit2.getCodeG());
		if (compareResult != 0)
			return compareResult;

		compareResult = StringHelper.compare(promoCodeDeposit1.getMask(), promoCodeDeposit2.getMask());
		if (compareResult != 0)
			return compareResult;

		compareResult = NumericUtil.compare(promoCodeDeposit1.getCodeS(), promoCodeDeposit2.getCodeS());
		if (compareResult != 0)
			return compareResult;

		compareResult = DateHelper.nullSafeCompare(promoCodeDeposit1.getDateBegin(), promoCodeDeposit2.getDateBegin());
		if (compareResult != 0)
			return compareResult;

		compareResult = DateHelper.nullSafeCompare(promoCodeDeposit1.getDateEnd(), promoCodeDeposit2.getDateEnd());
		if (compareResult != 0)
			return compareResult;

		compareResult = StringHelper.compare(promoCodeDeposit1.getSrokBegin(), promoCodeDeposit2.getSrokBegin());
		if (compareResult != 0)
			return compareResult;

        compareResult = StringHelper.compare(promoCodeDeposit1.getSrokEnd(), promoCodeDeposit2.getSrokEnd());
        if (compareResult != 0)
            return compareResult;

		compareResult = NumericUtil.compare(promoCodeDeposit1.getNumUse(), promoCodeDeposit2.getNumUse());
		if (compareResult != 0)
			return compareResult;

		compareResult = promoCodeDeposit1.getPrior() > promoCodeDeposit2.getPrior() ? 1 : promoCodeDeposit1.getPrior() < promoCodeDeposit2.getPrior() ? -1 : 0;
		if (compareResult != 0)
			return compareResult;

		compareResult = NumericUtil.compare(promoCodeDeposit1.getAbRemove(), promoCodeDeposit2.getAbRemove());
		if (compareResult != 0)
			return compareResult;

		compareResult = NumericUtil.compare(promoCodeDeposit1.getActiveCount(), promoCodeDeposit2.getActiveCount());
		if (compareResult != 0)
			return compareResult;

		compareResult = NumericUtil.compare(promoCodeDeposit1.getHistCount(), promoCodeDeposit2.getHistCount());
		if (compareResult != 0)
			return compareResult;

		compareResult = StringHelper.compare(promoCodeDeposit1.getNameAct(), promoCodeDeposit2.getNameAct());
		if (compareResult != 0)
			return compareResult;

		compareResult = StringHelper.compare(promoCodeDeposit1.getNameS(), promoCodeDeposit2.getNameS());
		if (compareResult != 0)
			return compareResult;

		compareResult = StringHelper.compare(promoCodeDeposit1.getNameF(), promoCodeDeposit2.getNameF());
		if (compareResult != 0)
			return compareResult;

		return 0;
	}
}
