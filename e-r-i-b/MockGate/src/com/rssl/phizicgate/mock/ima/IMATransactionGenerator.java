package com.rssl.phizicgate.mock.ima;

import com.rssl.phizic.gate.ima.IMAccountTransaction;

/**
 * @ author Balovtsev
 * @ created 27.08.2010
 * @ $Author$
 * @ $Revision$
 */
class IMATransactionGenerator extends AbstractIMAGenerator
{
	IMATransactionGenerator()
	{
		super();
	}

	protected IMAccountTransaction generate()
	{
		boolean debit = getRandom().nextBoolean();

		return new IMAccountTransactionImpl
		(
			generateNumber(2),
			getDescription(),
			generateNumber(4),
			generateNumber(4),
			generateNumber(19),

			debit ?  generateMoney(AbstractIMAGenerator.CURRENCY_CODE_AUR) : generateEmptyMoney(AbstractIMAGenerator.CURRENCY_CODE_AUR),
			!debit ? generateMoney(AbstractIMAGenerator.CURRENCY_CODE_AUR) : generateEmptyMoney(AbstractIMAGenerator.CURRENCY_CODE_AUR),

			generateMoney(AbstractIMAGenerator.CURRENCY_CODE_AUR),

		     debit ? generateMoney(AbstractIMAGenerator.CURRENCY_CODE_AUR) : generateEmptyMoney(AbstractIMAGenerator.CURRENCY_CODE_AUR),
			!debit ? generateMoney(AbstractIMAGenerator.CURRENCY_CODE_AUR) : generateEmptyMoney(AbstractIMAGenerator.CURRENCY_CODE_AUR),

			generateMoney(AbstractIMAGenerator.CURRENCY_CODE_RUB),
			generateDate()
		);
	}

	private String getDescription()
	{
		return "Там царь Кащей на златом чахнет...";
	}
}