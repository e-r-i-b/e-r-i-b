package com.rssl.phizic.test.web.atm.payments;

/**
 * @author Mescheryakova
 * @ created 22.11.2011
 * @ $Author$
 * @ $Revision$
 */

public class TestATMInternalPaymentForm extends TestATMDocumentForm
{
	String fromResource;     // счет списания
	String toResource;       // счет зачисления
	String amount;           // сумма
	String buyAmount;        // сумма списания
	String sellAmount;       // сумма зачисления
	String exactAmount;      // списание или зачисление
	String operationCode;     //Код валютной операции

	public String getOperationCode()
	{
		return operationCode;
	}

	public void setOperationCode(String operationCode)
	{
		this.operationCode = operationCode;
	}

	public String getFromResource()
	{
		return fromResource;
	}

	public void setFromResource(String fromResource)
	{
		this.fromResource = fromResource;
	}

	public String getToResource()
	{
		return toResource;
	}

	public void setToResource(String toResource)
	{
		this.toResource = toResource;
	}

	public String getAmount()
	{
		return amount;
	}

	public void setAmount(String amount)
	{
		this.amount = amount;
	}

	public String getBuyAmount()
	{
		return buyAmount;
	}

	public void setBuyAmount(String buyAmount)
	{
		this.buyAmount = buyAmount;
	}

	public String getSellAmount()
	{
		return sellAmount;
	}

	public void setSellAmount(String sellAmount)
	{
		this.sellAmount = sellAmount;
	}

	public String getExactAmount()
	{
		return exactAmount;
	}

	public void setExactAmount(String exactAmount)
	{
		this.exactAmount = exactAmount;
	}
}