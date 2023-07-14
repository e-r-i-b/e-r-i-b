package com.rssl.phizicgate.mock.ima;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.bankroll.TransactionBase;
import com.rssl.phizic.gate.ima.IMAccountAbstract;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @ author Balovtsev
 * @ created 25.08.2010
 * @ $Author$
 * @ $Revision$
 */
public class IMAccountAbstractGenerator extends AbstractIMAGenerator
{
	private Money      openingBalance;
	private Money      closingBalance;
	private Money      openingBalanceInRub;
	private Money      closingBalanceInRub;
	private Calendar   fromDate;
	private Calendar   toDate;
	private BigDecimal rate;

	private List<TransactionBase> transtactions = new ArrayList();

	IMAccountAbstractGenerator()
	{
		super();
	}

	protected IMAccountAbstract generate()
	{
		return new IMAccountAbstractImpl(
			generateOpeningBalanceInRub(),
			generateClosingBalanceInRub(),
			generateDate(),
			generateRate(),
			generateFromDate(),
			generateToDate(),
			generateOpeningBalance(),
			generateClosingBalance(),
			transtactions
		);
	}

	private Money generateOpeningBalanceInRub()
	{
		if(openingBalanceInRub == null)
		{
		   openingBalanceInRub = generateMoney(AbstractIMAGenerator.CURRENCY_CODE_RUB);
		}
		return openingBalanceInRub;
	}

	private Money generateClosingBalanceInRub()
	{
		boolean flag = getRandom().nextBoolean();

		double opening = generateOpeningBalanceInRub().getDecimal().doubleValue();

		if(closingBalanceInRub == null)
		{
			do
			{
				closingBalanceInRub = generateMoney(AbstractIMAGenerator.CURRENCY_CODE_RUB);

				if(closingBalanceInRub.getDecimal().doubleValue() > opening)
				{
					flag = false;
				}
				else
				{
					flag = true;
				}
			}
			while(flag);
		}
		return closingBalanceInRub;
	}

	private Money generateOpeningBalance()
	{
		if(openingBalance == null)
		{
		   double inRub = generateOpeningBalanceInRub().getDecimal().doubleValue();
		   double dRate = generateRate().doubleValue();

		   openingBalance = new Money(new BigDecimal(inRub / dRate), getCurrency(AbstractIMAGenerator.CURRENCY_CODE_AUR));
		}
		return openingBalance;
	}

	private Money generateClosingBalance()
	{
		if(closingBalance == null)
		{
		   double inRub = generateOpeningBalanceInRub().getDecimal().doubleValue();
		   double dRate = generateRate().doubleValue();

		   closingBalance = new Money(new BigDecimal(inRub / dRate), getCurrency(AbstractIMAGenerator.CURRENCY_CODE_AUR));
		}
		return openingBalance;
	}

	private Calendar generateFromDate()
	{
		if(fromDate == null)
		{
		   fromDate = generateDate();
		}
		return fromDate;
	}

	private Calendar generateToDate()
	{
		if(toDate == null)
		{
			do
			{
				toDate = generateDate();
			}
			while(generateFromDate().getTimeInMillis() > toDate.getTimeInMillis());
		}
		return toDate;
	}

	private BigDecimal generateRate()
	{
		if(rate == null)
		{
			rate = new BigDecimal(getRandom().nextInt(900));
		}
		return rate;
	}

	public void setFromDate(Calendar fromDate)
	{
		this.fromDate = fromDate;
	}

	public void setToDate(Calendar toDate)
	{
		this.toDate = toDate;
	}

	protected void setTransactions(List<TransactionBase> transtactions)
	{
		this.transtactions = transtactions;
	}
}
