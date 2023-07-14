package com.rssl.phizic.business.cardAmountStep;

import com.rssl.phizic.common.types.Money;

import java.util.Comparator;

/**
 * @author Mescheryakova
 * @ created 16.11.2011
 * @ $Author$
 * @ $Revision$
 *
 * Сравнение лимитов сумм для предодобренных предложений
 */

public class CardAmountStepComparator implements Comparator<CardAmountStep>
{

	public int compare(CardAmountStep cardAmountStepSrc, CardAmountStep cardAmountStepSrcDesc)
	{
	    Money moneySrc = cardAmountStepSrc.getValue();
		Money moneyDesc = cardAmountStepSrcDesc.getValue();

		return moneySrc.getDecimal().compareTo(moneyDesc.getDecimal());
	}
}
