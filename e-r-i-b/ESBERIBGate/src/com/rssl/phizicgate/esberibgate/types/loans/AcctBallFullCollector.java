package com.rssl.phizicgate.esberibgate.types.loans;

import com.rssl.phizicgate.esberibgate.ws.generated.AcctBal_Type;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author mihaylov
 * @ created 26.05.2011
 * @ $Author$
 * @ $Revision$
 */

// ���������, ��� �������� ������ ������������� �� �������
public class AcctBallFullCollector
{
	private AcctBal_Type firstDateAmount; //����� ������� ��� ���������� ������� ������������ �� ����� ����_1
	private AcctBal_Type secondDateAmount; //����� ������� ��� ���������� ������� ������������ �� ����� ����_2
	private AcctBal_Type amountForClose; //����� ������� ��� �������� �������
	private AcctBal_Type offeredAmount; //����� ������������� �������
	private AcctBal_Type annuitetAmount; //������ ������������ ������� �� ����� ��
	private AcctBal_Type totalAmount; // ����� ���� �������� �� ��������
	private AcctBal_Type debtAmount; // ������������ (����������) ����� ��������� ����� �� �������
	private AcctBal_Type recommendedAmount; //����� �������������� �������, ������� ��� ������������������ �������� = 104 ����, � ��� ����������� �������� �������� �������
												// ������������� (������������ + �������) � ������ ���������� ���������
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
