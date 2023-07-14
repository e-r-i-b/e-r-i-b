package com.rssl.phizicgate.rsretailV6r20.bankroll;

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
	private Money purchaseLimit;
	private String codeWord;
	private Money AvailableCashLimit;
	private String AgreementNumber;
	private Calendar agreementDate;
	private String holderName;

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
		return AvailableCashLimit;
	}

	public void setAvailableCashLimit(Money availableCashLimit)
	{
		AvailableCashLimit = availableCashLimit;
	}

	public String getAgreementNumber()
	{
		return AgreementNumber;
	}

	public void setAgreementNumber(String agreementNumber)
	{
		AgreementNumber = agreementNumber;
	}

	public Calendar getAgreementDate()
	{
		return agreementDate;
	}

	public void setAgreementDate(Calendar agreementDate)
	{
		this.agreementDate = agreementDate;
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
