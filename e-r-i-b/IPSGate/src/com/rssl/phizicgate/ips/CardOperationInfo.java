package com.rssl.phizicgate.ips;

import com.rssl.phizgate.mobilebank.GateCardHelper;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.utils.DateHelper;
import static com.rssl.phizic.utils.MoneyHelper.formatMoney;

import java.util.Calendar;

/**
 * @author Erkin
 * @ created 01.08.2011
 * @ $Author$
 * @ $Revision$
 */
class CardOperationInfo
{
	private String operationId;

	private long operationType;

	private Calendar operationDate;

	private Card card;

	private String description;

	private Money accountMoney;

	private Money operationMoney;

	private long mccCode;

	private String deviceId;

	private String cardId;
	private String errorMessage;
	private boolean badOperation;

	private String authCode;
	///////////////////////////////////////////////////////////////////////////

	String getOperationId()
	{
		return operationId;
	}

	void setOperationId(String operationId)
	{
		this.operationId = operationId;
	}

	long getOperationType()
	{
		return operationType;
	}

	void setOperationType(long operationType)
	{
		this.operationType = operationType;
	}

	Calendar getOperationDate()
	{
		return operationDate;
	}

	void setOperationDate(Calendar operationDate)
	{
		this.operationDate = operationDate;
	}

	Card getCard()
	{
		return card;
	}

	void setCard(Card card)
	{
		this.card = card;
	}

	String getDescription()
	{
		return description;
	}

	void setDescription(String description)
	{
		this.description = description;
	}

	Money getAccountMoney()
	{
		return accountMoney;
	}

	void setAccountMoney(Money accountMoney)
	{
		this.accountMoney = accountMoney;
	}

	Money getOperationMoney()
	{
		return operationMoney;
	}

	void setOperationMoney(Money operationMoney)
	{
		this.operationMoney = operationMoney;
	}

	long getMccCode()
	{
		return mccCode;
	}

	void setMccCode(long mccCode)
	{
		this.mccCode = mccCode;
	}

	String getDeviceId()
	{
		return deviceId;
	}

	void setDeviceId(String deviceId)
	{
		this.deviceId = deviceId;
	}

	String getCardId()
	{
		return cardId;
	}

	void setCardId(String cardId)
	{
		this.cardId = cardId;
	}

	String getErrorMessage()
	{
		return errorMessage;
	}

	void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}

	boolean isBadOperation()
	{
		return badOperation;
	}

	void setBadOperation(boolean badOperation)
	{
		this.badOperation = badOperation;
	}

	String getAuthCode()
	{
		return authCode;
	}

	void setAuthCode(String authCode)
	{
		this.authCode = authCode;
	}

	public String toString()
	{
		return "CardOperationInfo{" +
				"card="             + GateCardHelper.hideCardNumber(card) +
				", operationId="    + operationId +
				", operationType="  + operationType +
				", operationDate="  + DateHelper.toISO8601DateFormat(operationDate) +
				", description='"   + description + '\'' +
				", accountMoney="   + formatMoney(accountMoney) +
				", operationMoney=" + formatMoney(operationMoney) +
				", mccCode="        + mccCode +
				", deviceId="       + deviceId +
				'}';
	}
}
