package com.rssl.phizicgate.esberibgate.types;

import com.rssl.phizic.common.types.LongOfferPayDay;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoPayStatusType;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoSubscription;
import com.rssl.phizic.common.types.Money;

import java.util.Calendar;

/**
 * @author: vagin
 * @ created: 19.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class AutoSubscriptionImpl extends LongOfferImpl implements AutoSubscription
{
	private AutoPayStatusType autoPayStatusType;
	private Calendar nextPayDate;
	private Calendar updateDate;
	private Money maxSumWritePerMonth;
	private String reasonDesc;
	private String documentNumber;
	private LongOfferPayDay longOfferPayDay;
	private String receiverCard;
	private String autopayNumber;
	private String receiverFirstName;
	private String receiverPatronymicName;
	private String receiverLastName;

	public AutoPayStatusType getAutoPayStatusType()
	{
		return autoPayStatusType;
	}

	public Money getMaxSumWritePerMonth()
	{
		return maxSumWritePerMonth;
	}

	public void setMaxSumWritePerMonth(Money maxSumWritePerMonth)
	{
		this.maxSumWritePerMonth = maxSumWritePerMonth;
	}

	public void setUpdateDate(Calendar updateDate)
	{
		this.updateDate = updateDate;
	}

	public Calendar getUpdatedDate()
	{
		return updateDate;
	}

	public String getReasonDesc()
	{
		return reasonDesc;
	}

	public void setReasonDesc(String reasonDesc)
	{
		this.reasonDesc = reasonDesc;
	}

	public void setAutoPayStatusType(AutoPayStatusType autoPayStatusType)
	{
		this.autoPayStatusType = autoPayStatusType;
	}

	public Calendar getNextPayDate()
	{
		return nextPayDate;
	}

	public void setNextPayDate(Calendar nextPayDate)
	{
		this.nextPayDate = nextPayDate;
	}

	public String getDocumentNumber()
	{
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber)
	{
		this.documentNumber = documentNumber;
	}

	public LongOfferPayDay getLongOfferPayDay()
	{
		return longOfferPayDay;
	}

	public void setLongOfferPayDay(LongOfferPayDay longOfferPayDay)
	{
		this.longOfferPayDay = longOfferPayDay;
	}

	public String getReceiverCard()
	{
		return receiverCard;
	}

	public void setReceiverCard(String receiverCard)
	{
		this.receiverCard = receiverCard;
	}

	public String getAutopayNumber()
	{
		return autopayNumber;
	}

	public void setAutopayNumber(String autopayNumber)
	{
		this.autopayNumber = autopayNumber;
	}

	/**
	 * @return имя получателя автоперевода
	 */
	public String getReceiverFirstName()
	{
		return receiverFirstName;
	}

	public void setReceiverFirstName(String receiverFirstName)
	{
		this.receiverFirstName = receiverFirstName;
	}

	/**
	 * @return отчество получателя перевода
	 */
	public String getReceiverPatronymicName()
	{
		return receiverPatronymicName;
	}

	public void setReceiverPatronymicName(String receiverPatronymicName)
	{
		this.receiverPatronymicName = receiverPatronymicName;
	}

	/**
	 * @return фамилия получателя перевода
	 */
	public String getReceiverLastName()
	{
		return receiverLastName;
	}

	public void setReceiverLastName(String receiverLastName)
	{
		this.receiverLastName = receiverLastName;
	}
}
