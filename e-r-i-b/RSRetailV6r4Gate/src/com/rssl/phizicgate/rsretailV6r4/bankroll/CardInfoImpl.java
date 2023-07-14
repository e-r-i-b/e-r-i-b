package com.rssl.phizicgate.rsretailV6r4.bankroll;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.bankroll.CardInfo;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: Kidyaev
 * Date: 18.10.2005
 * Time: 14:48:52
 * Класс, реализующий интерфейс
 * @see com.rssl.phizic.gate.bankroll.CardInfo
 */
public class CardInfoImpl implements CardInfo
{
    private long       longId;
    private BigDecimal decimalHoldSum;
    private Calendar lastOperationDate;
    private Money  holdSum;
	private String codeWord;
	private Money availableCashLimit;
	private String agreementNumber;
	private Calendar agreementDate;
	private Money purchaseLimit;
	private String holderName;

	public Money getPurchaseLimit()
	{
		return purchaseLimit;
	}

	public void setPurchaseLimit(Money purchaseLimit)
	{
		this.purchaseLimit = purchaseLimit;
	}

	public String getCodeWord()
	{
		return codeWord;
	}

	public void setCodeWord(String codeWord)
	{
		this.codeWord = codeWord;
	}

	public Money getAvailableCashLimit()
	{
		return availableCashLimit;
	}

	public void setAvailableCashLimit(Money availableCashLimit)
	{
		this.availableCashLimit = availableCashLimit;
	}

	public String getAgreementNumber()
	{
		return agreementNumber;
	}

	public void setAgreementNumber(String agreementNumber)
	{
		this.agreementNumber = agreementNumber;
	}

	public Calendar getAgreementDate()
	{
		return agreementDate;
	}

	public void setAgreementDate(Calendar agreementDate)
	{
		this.agreementDate = agreementDate;
	}

	/**
	 * @return ID карты в ретейле
	 */
    public long getLongId()
    {
        return longId;
    }

	/**
	 * @param longId ID карты в ретейле 4hibernate
	 */
    public void setLongId(long longId)
    {
        this.longId = longId;
    }

	public Money getHoldSum()
    {
        return holdSum;
    }

	/**
	 * @param holdSum сумма блокировок на карте
	 */
	public void setHoldSum(Money holdSum)
	{
		this.holdSum = holdSum;
	}

	public Calendar getLastOperationDate()
    {
        return lastOperationDate;
    }

    void setLastOperationDate(Calendar lastOperationDate)
    {
        this.lastOperationDate = lastOperationDate;
    }

    public BigDecimal getDecimalHoldSum()
    {
        return decimalHoldSum;
    }

    public void setDecimalHoldSum(BigDecimal decimalHoldSum)
    {
        this.decimalHoldSum = decimalHoldSum;
    }

	public String getHolderName()
	{
		return holderName;
	}

	public void setHolderName(String holderName)
	{
		this.holderName = holderName;
	}
}
