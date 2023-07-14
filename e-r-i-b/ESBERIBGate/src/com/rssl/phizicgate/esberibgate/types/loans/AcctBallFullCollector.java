package com.rssl.phizicgate.esberibgate.types.loans;

import com.rssl.phizicgate.esberibgate.ws.generated.AcctBal_Type;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author mihaylov
 * @ created 26.05.2011
 * @ $Author$
 * @ $Revision$
 */

// коллектор, для хранения статей задолженности по кредиту
public class AcctBallFullCollector
{
	private AcctBal_Type firstDateAmount; //сумма платежа для исполнения сегодня обязательств по сроку Дата_1
	private AcctBal_Type secondDateAmount; //сумма платежа для исполнения сегодня обязательств по сроку Дата_2
	private AcctBal_Type amountForClose; //сумма платежа для закрытия кредита
	private AcctBal_Type offeredAmount; //сумма предлагаемого платежа
	private AcctBal_Type annuitetAmount; //размер аннуитетного платежа по сроку на
	private AcctBal_Type totalAmount; // сумма всех платежей по договору
	private AcctBal_Type debtAmount; // Непогашенная (оставшаяся) сумма основного долга по кредиту
	private AcctBal_Type recommendedAmount; //сумма рекомендуемого платежа», который для дифференцированных платежей = 104 коду, а для аннуитетных платежей отражает текущую
												// задолженность (просроченную + срочную) с учетом частичного погашения
	private String currencyStr;


	public AcctBallFullCollector(AcctBal_Type[] acctBalFull)
	{
		for(AcctBal_Type bal : acctBalFull)
		{
			switch(Integer.decode(bal.getBalType()))
			{
				case 101: firstDateAmount   = bal; break;
				case 102: secondDateAmount  = bal; break;
				case 103: amountForClose    = bal; break;
				case 104: offeredAmount     = bal; break;
				case 105: annuitetAmount    = bal; break;
				case 106: totalAmount       = bal; break;
				case 107: debtAmount        = bal; break;
				case 108: recommendedAmount = bal; break;
			}
			if(StringHelper.isEmpty(currencyStr))
				currencyStr = bal.getAcctCur();
		}

	}

	public AcctBal_Type getFirstDateAmount()
	{
		return firstDateAmount;
	}

	public AcctBal_Type getSecondDateAmount()
	{
		return secondDateAmount;
	}

	public AcctBal_Type getAmountForClose()
	{
		return amountForClose;
	}

	public AcctBal_Type getOfferedAmount()
	{
		return offeredAmount;
	}

	public AcctBal_Type getAnnuitetAmount()
	{
		return annuitetAmount;
	}

	public AcctBal_Type getTotalAmount()
	{
		return totalAmount;
	}

	public AcctBal_Type getDebtAmount()
	{
		return debtAmount;
	}

	public AcctBal_Type getRecommendedAmount()
	{
		return recommendedAmount;
	}

	public String getCurrencyStr()
	{
		return currencyStr;
	}
}
