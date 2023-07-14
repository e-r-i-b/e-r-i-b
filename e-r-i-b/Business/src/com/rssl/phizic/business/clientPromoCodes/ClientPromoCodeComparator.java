package com.rssl.phizic.business.clientPromoCodes;

import com.rssl.phizic.business.dictionaries.promoCodesDeposit.PromoCodeDeposit;
import com.rssl.phizic.utils.DateHelper;

import java.util.Comparator;

/**
 * Сортировка промокодам по приоритету, дате начала действия, коду сегмента клиента
 *
 * @author EgorovaA
 * @ created 21.01.15
 * @ $Author$
 * @ $Revision$
 */
public class ClientPromoCodeComparator implements Comparator<ClientPromoCode>
{
	public int compare(ClientPromoCode promoCode1, ClientPromoCode promoCode2)
	{
		if (promoCode1 == null && promoCode2 == null)
			return 0;
		if (promoCode1 == null || promoCode2 == null)
			return -1;

		PromoCodeDeposit promoCodeDeposit1 = promoCode1.getPromoCodeDeposit();
		PromoCodeDeposit promoCodeDeposit2 = promoCode2.getPromoCodeDeposit();

		if (promoCodeDeposit1 == null && promoCodeDeposit2 == null)
			return 0;
		if (promoCodeDeposit1 == null || promoCodeDeposit2 == null)
			return -1;

		int compareResult = Integer.valueOf(promoCodeDeposit1.getPrior()).compareTo(Integer.valueOf(promoCodeDeposit2.getPrior()));
		if(compareResult != 0)
			return compareResult;

		compareResult = DateHelper.nullSafeCompare(promoCodeDeposit1.getDateBegin(), promoCodeDeposit2.getDateBegin());
		if(compareResult != 0)
			return compareResult;

		compareResult = promoCodeDeposit1.getCodeS().compareTo(promoCodeDeposit2.getCodeS());
		if(compareResult != 0)
			return compareResult;

		return compareResult;
	}
}
