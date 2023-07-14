package com.rssl.phizic.business.ext.sbrf.deposits.entities;

import com.rssl.phizic.business.clientPromoCodes.ClientPromoCode;
import com.rssl.phizic.business.dictionaries.promoCodesDeposit.PromoCodeDeposit;

import java.util.Comparator;

/**
 *  омпаратор дл€ сортировки списка вкладных продуктов, отображаемого клиенту
 * ¬ первую очередь отобразить промо-вклады
 *
 * @author EgorovaA
 * @ created 08.04.15
 * @ $Author$
 * @ $Revision$
 */
public class DepositEntityComparator implements Comparator<DepositProductEntity>
{
	public int compare(DepositProductEntity o1, DepositProductEntity o2)
	{
		PromoCodeDeposit promoCodeDeposit1 = o1.getPromoCodeDeposit();
		PromoCodeDeposit promoCodeDeposit2 = o2.getPromoCodeDeposit();

		if (promoCodeDeposit1 == null && promoCodeDeposit2 == null)
			return 0;
		if (promoCodeDeposit1 == null)
			return 1;
		if (promoCodeDeposit2 == null)
			return -1;

		return Integer.valueOf(promoCodeDeposit2.getPrior()).compareTo(Integer.valueOf(promoCodeDeposit1.getPrior()));
	}
}
