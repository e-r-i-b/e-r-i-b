package com.rssl.phizic.business.documents.decorators;

import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.CurrencyRate;

import java.util.Calendar;
import java.math.BigDecimal;

/**
 * @author krenev
 * @ created 26.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class PaymentDecoratorBase extends GateDocumentDecoratorBase
{
	private AbstractPaymentDocument original;

	public PaymentDecoratorBase(AbstractPaymentDocument original)
	{
		super(original);
		this.original = original;
	}

	public String getGround()
	{
		return original.getGround();
	}

	public String getChargeOffAccount()
	{
		return original.getChargeOffAccount();
	}

	public String getChargeOffCard()
	{
		return original.getChargeOffCard();
	}

	public Calendar getChargeOffCardExpireDate()
	{
		return original.getChargeOffCardExpireDate();
	}

	public Money getChargeOffAmount()
	{
		return original.getChargeOffAmount();
	}

	public void setChargeOffAmount(Money amount)
	{
		original.setChargeOffAmount(amount);
	}

	public Money getDestinationAmount()
	{
		return original.getDestinationAmount();
	}

	public void setDestinationAmount(Money amount)
	{
		original.setDestinationAmount(amount);
	}

	public CurrencyRate getDebetSaleRate()
	{
		return original.getDebetSaleRate();
	}

	public CurrencyRate getDebetBuyRate()
	{
		return original.getDebetBuyRate();
	}

	public CurrencyRate getCreditSaleRate()
	{
		return original.getCreditSaleRate();
	}

	public CurrencyRate getCreditBuyRate()
	{
		return original.getCreditBuyRate();
	}

	public BigDecimal getConvertionRate()
	{
		return original.getConvertionRate();
	}
}
