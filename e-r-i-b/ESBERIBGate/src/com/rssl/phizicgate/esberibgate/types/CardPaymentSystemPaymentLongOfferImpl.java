package com.rssl.phizicgate.esberibgate.types;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.longoffer.CardPaymentSystemPaymentLongOffer;
import com.rssl.phizic.gate.payments.longoffer.ChannelType;
import com.rssl.phizic.gate.longoffer.autopayment.AlwaysAutoPayScheme;
import com.rssl.phizic.gate.longoffer.autopayment.ThresholdAutoPayScheme;
import com.rssl.phizic.gate.longoffer.autopayment.InvoiceAutoPayScheme;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoPayStatusType;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.common.types.Money;

import java.util.Calendar;

/**
 * @author basharin
 * @ created 02.02.2012
 * @ $Author$
 * @ $Revision$
 */

public class CardPaymentSystemPaymentLongOfferImpl extends PaymentSystemPaymentImpl implements CardPaymentSystemPaymentLongOffer
{
	private Money maxSumWritePerMonth;
	private Calendar updateDate;
	private Calendar createDate;
	private Calendar nextPayDate;
	private String CardNumber;
	private AutoPayStatusType autoPayStatusType;
	private String reasonDescription;
	private Office employeeOffice;
	private ChannelType channelType;
	private String groupService;
	private String codeATM;
	private String receiverCard;
	private String autopayNumber;
	private String longOfferExternalId;

	public CardPaymentSystemPaymentLongOfferImpl(LongOffer longOffer) throws GateException
	{
		super(longOffer);
	}

	public AlwaysAutoPayScheme getAlwaysAutoPayScheme()
	{
		return null;
	}

	public void setAlwaysAutoPayScheme(AlwaysAutoPayScheme scheme)
	{
	}

	public ThresholdAutoPayScheme getThresholdAutoPayScheme()
	{
		return null;
	}

	public void setThresholdAutoPayScheme(ThresholdAutoPayScheme scheme)
	{

	}

	public InvoiceAutoPayScheme getInvoiceAutoPayScheme()
	{
		return null;
	}

	public void setInvoiceAutoPayScheme(InvoiceAutoPayScheme scheme)
	{
	}

	public Boolean isExecutionNow()
	{
		return null;
	}

	public boolean isNeedConfirmation()
	{
		return false;
	}

	public ChannelType getChannelType()
	{
		return channelType;
	}

	/**
	 * Установить канал создания подписки
	 * (IB - интернет банк, VSP - ВСП, US - устройство самообслуживания)
	 */
	public void setChannelType(ChannelType channelType)
	{
		this.channelType = channelType;
	}

	public boolean isWithCommision()
	{
		return false;
	}

	public void setWithCommision(boolean withCommision)
	{
	}

	public Calendar getUpdateDate()
	{
		return updateDate;
	}

	/**
	 * @return Дата и время создания подписки
	 */
	public Calendar getCreateDate()
	{
		return createDate;
	}

	public void setCreateDate(Calendar createDate)
	{
		this.createDate = createDate; 
	}

	public void setUpdateDate(Calendar updateDate)
	{
		this.updateDate = updateDate;
	}

	public Calendar getNextPayDate()
	{
		return nextPayDate;
	}

	public String getCardNumber()
	{
		return CardNumber;
	}

	public void setCardNumber(String cardNumber)
	{
		CardNumber = cardNumber;
	}

	public void setNextPayDate(Calendar nextPayDate)
	{
		this.nextPayDate = nextPayDate;
	}

	public AutoPayStatusType getAutoPayStatusType()
	{
		return autoPayStatusType;
	}

	public void setAutoPayStatusType(AutoPayStatusType autoPayStatusType)
	{
		this.autoPayStatusType = autoPayStatusType;
	}

	public Money getMaxSumWritePerMonth()
	{
		return maxSumWritePerMonth;
	}

	public void setMaxSumWritePerMonth(Money maxSumWritePerMonth)
	{
		this.maxSumWritePerMonth = maxSumWritePerMonth;
	}

	public String getReasonDescription()
	{
		return reasonDescription;
	}

	public void setReasonDescription(String reasonDescription)
	{
		this.reasonDescription = reasonDescription;
	}

	public Office getEmployeeOffice()
	{
		return employeeOffice;
	}

	/**
	 * @return Код группы услуг
	 */
	public String getGroupService()
	{
		return groupService;  //To change body of implemented methods use File | Settings | File Templates.
	}

	/**
	 * Установить код группы услуг
	 * @param groupService код группы услуг
	 */
	public void setGroupService(String groupService)
	{
		this.groupService = groupService;
	}

	public void setEmployeeOffice(Office employeeOffice)
	{
		this.employeeOffice = employeeOffice;
	}

	public String getCodeATM()
	{
		return codeATM;
	}

	public String getMessageToRecipient()
	{
		return null;
	}

	public boolean isSameTB()
	{
		return false;
	}

	public void setCodeATM(String codeATM)
	{
		this.codeATM = codeATM;
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

	public boolean isEinvoicing()
	{
		return false;
	}

	public String getLongOfferExternalId()
	{
		return longOfferExternalId;
	}

	public void setLongOfferExternalId(String longOfferExternalId)
	{
		this.longOfferExternalId = longOfferExternalId;
	}
}
