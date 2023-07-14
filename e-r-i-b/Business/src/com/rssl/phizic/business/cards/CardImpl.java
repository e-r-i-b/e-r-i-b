package com.rssl.phizic.business.cards;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.bankroll.*;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizicgate.esberibgate.bankroll.GFLCard;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.Calendar;

/**
 * Бизнесовая реализация карты
 * @author Pankin
 * @ created 20.09.2013
 * @ $Author$
 * @ $Revision$
 */

public class CardImpl implements Card
{
	private String id;
	private String description;
	private StatusDescExternalCode statusDescExternalCode;
	private String type;
	private String number;
	private Calendar issueDate;
	private Calendar nextReportDate;
	private Calendar expireDate;
	private String displayedExpireDate;
	private boolean main;
	private CardType cardType;
	private Money availableLimit;
	private Office office;
	private AdditionalCardType additionalCardType;
	private String statusDescription;
	private CardState cardState;
	private AccountState cardAccountState;
	private Currency currency;
	private String mainCardNumber;
	private boolean virtual;
	private String primaryAccountNumber;
	private String primaryAccountExternalId;
	private Long kind;
	private Long subkind;
	private String uniCardType;
	private String uniAccountType;
	private CardLevel cardLevel;
	private CardBonusSign cardBonusSign;
	private boolean isGFLCard;
	private String clientId;
	private boolean useReportDelivery;
	private String emailAddress;
	private ReportDeliveryType reportDeliveryType;
	private ReportDeliveryLanguage reportDeliveryLanguage;
	private Money availableCashLimit;
	private Money purchaseLimit;
	private String holderName;
	private String contractNumber;
	private Code notCorrectedTbCode;
	private Money overdraftLimit;
	private Money overdraftTotalDebtSum;
	private Money overdraftMinimalPayment;
	private Calendar overdraftMinimalPaymentDate;
	private Money overdraftOwnSum;
	private Client cardClient;

	/**
	 *
	 * @param card карта
	 */
	public CardImpl(Card card)
	{
		this.id = card.getId();
		this.description = card.getDescription();
		this.statusDescExternalCode = card.getStatusDescExternalCode();
		this.type = card.getType();
		this.number = card.getNumber();
		this.issueDate = card.getIssueDate();
		this.expireDate = card.getExpireDate();
		this.displayedExpireDate = card.getDisplayedExpireDate();
		this.main = card.isMain();
		this.cardType = card.getCardType();
		this.availableLimit = card.getAvailableLimit();
		this.office = card.getOffice();
		this.additionalCardType = card.getAdditionalCardType();
		this.statusDescription = card.getStatusDescription();
		this.cardState = card.getCardState();
		this.currency = card.getCurrency();
		this.mainCardNumber = card.getMainCardNumber();
		this.virtual = card.isVirtual();
		this.primaryAccountNumber = card.getPrimaryAccountNumber();
		this.primaryAccountExternalId = card.getPrimaryAccountExternalId();
		this.kind = card.getKind();
		this.subkind = card.getSubkind();
		this.uniCardType = card.getUNICardType();
		this.uniAccountType = card.getUNIAccountType();
		this.cardLevel = card.getCardLevel();
		this.cardBonusSign = card.getCardBonusSign();
		this.isGFLCard = card instanceof GFLCard;
		this.clientId = card.getClientId();
		this.useReportDelivery = card.isUseReportDelivery();
		this.emailAddress = card.getEmailAddress();
		this.reportDeliveryType = card.getReportDeliveryType();
		this.reportDeliveryLanguage = card.getReportDeliveryLanguage();
		this.availableCashLimit = card.getAvailableCashLimit();
		this.purchaseLimit = card.getPurchaseLimit();
		this.holderName = card.getHolderName();
		this.cardAccountState = card.getCardAccountState();
        this.nextReportDate = card.getNextReportDate();
		this.notCorrectedTbCode = card.getOriginalTbCode();
		this.overdraftLimit = card.getOverdraftLimit();
		this.overdraftTotalDebtSum = card.getOverdraftTotalDebtSum();
		this.overdraftMinimalPayment = card.getOverdraftMinimalPayment();
		this.overdraftMinimalPaymentDate = card.getOverdraftMinimalPaymentDate();
		this.overdraftOwnSum = card.getOverdraftOwnSum();
		this.cardClient = card.getCardClient();
	}

	public String getId()
	{
		return id;
	}

	public String getDescription()
	{
		return description;
	}

	public StatusDescExternalCode getStatusDescExternalCode()
	{
		return statusDescExternalCode;
	}

	public String getType()
	{
		return type;
	}

	public String getNumber()
	{
		return number;
	}

	public Calendar getIssueDate()
	{
		return issueDate;
	}

	public Calendar getExpireDate()
	{
		return expireDate;
	}

	public String getDisplayedExpireDate()
	{
		return displayedExpireDate;
	}

	public boolean isMain()
	{
		return main;
	}

	public CardType getCardType()
	{
		return cardType;
	}

	public Money getAvailableLimit()
	{
		return availableLimit;
	}

	public Office getOffice()
	{
		return office;
	}

	public AdditionalCardType getAdditionalCardType()
	{
		return additionalCardType;
	}

	public String getStatusDescription()
	{
		return statusDescription;
	}

	public CardState getCardState()
	{
		return cardState;
	}

	public Currency getCurrency()
	{
		return currency;
	}

	public String getMainCardNumber()
	{
		return mainCardNumber;
	}

	public boolean isVirtual()
	{
		return virtual;
	}

	public String getPrimaryAccountNumber()
	{
		return primaryAccountNumber;
	}

	public String getPrimaryAccountExternalId()
	{
		return primaryAccountExternalId;
	}

	public Long getKind()
	{
		return kind;
	}

	public Long getSubkind()
	{
		return subkind;
	}

	public String getUNICardType()
	{
		return uniCardType;
	}

	public String getUNIAccountType()
	{
		return uniAccountType;
	}

	public CardLevel getCardLevel()
	{
		return cardLevel;
	}

	public CardBonusSign getCardBonusSign()
	{
		return cardBonusSign;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setStatusDescExternalCode(StatusDescExternalCode statusDescExternalCode)
	{
		this.statusDescExternalCode = statusDescExternalCode;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	public void setIssueDate(Calendar issueDate)
	{
		this.issueDate = issueDate;
	}

	public void setExpireDate(Calendar expireDate)
	{
		this.expireDate = expireDate;
	}

	public void setDisplayedExpireDate(String displayedExpireDate)
	{
		this.displayedExpireDate = displayedExpireDate;
	}

	public void setMain(boolean main)
	{
		this.main = main;
	}

	public void setCardType(CardType cardType)
	{
		this.cardType = cardType;
	}

	public void setAvailableLimit(Money availableLimit)
	{
		this.availableLimit = availableLimit;
	}

	public void setOffice(Office office)
	{
		this.office = office;
	}

	public void setAdditionalCardType(AdditionalCardType additionalCardType)
	{
		this.additionalCardType = additionalCardType;
	}

	public void setStatusDescription(String statusDescription)
	{
		this.statusDescription = statusDescription;
	}

	public void setCardState(CardState cardState)
	{
		this.cardState = cardState;
	}

	public void setCurrency(Currency currency)
	{
		this.currency = currency;
	}

	public void setMainCardNumber(String mainCardNumber)
	{
		this.mainCardNumber = mainCardNumber;
	}

	public void setVirtual(boolean virtual)
	{
		this.virtual = virtual;
	}

	public void setPrimaryAccountNumber(String primaryAccountNumber)
	{
		this.primaryAccountNumber = primaryAccountNumber;
	}

	public void setPrimaryAccountExternalId(String primaryAccountExternalId)
	{
		this.primaryAccountExternalId = primaryAccountExternalId;
	}

	public void setKind(Long kind)
	{
		this.kind = kind;
	}

	public void setSubkind(Long subkind)
	{
		this.subkind = subkind;
	}

	public void setUniCardType(String uniCardType)
	{
		this.uniCardType = uniCardType;
	}

	public void setUniAccountType(String uniAccountType)
	{
		this.uniAccountType = uniAccountType;
	}

	public void setCardLevel(CardLevel cardLevel)
	{
		this.cardLevel = cardLevel;
	}

	public void setCardBonusSign(CardBonusSign cardBonusSign)
	{
		this.cardBonusSign = cardBonusSign;
	}

	public boolean isGFLCard()
	{
		return isGFLCard;
	}

	public String getClientId()
	{
		return clientId;
	}

	public boolean isUseReportDelivery()
	{
		return useReportDelivery;
	}

	public String getEmailAddress()
	{
		return emailAddress;
	}

	public ReportDeliveryType getReportDeliveryType()
	{
		return reportDeliveryType;
	}

	public ReportDeliveryLanguage getReportDeliveryLanguage()
	{
		return reportDeliveryLanguage;
	}

	public Money getPurchaseLimit()
	{
		return purchaseLimit;
	}

	public Money getAvailableCashLimit()
	{
		return availableCashLimit;
	}

	public String getHolderName()
	{
		return holderName;
	}

	public String getContractNumber()
	{
		return contractNumber;
	}

	public void setContractNumber(String contractNumber)
	{
		this.contractNumber = contractNumber;
	}

	public void setCardAccountState(AccountState cardAccountState)
	{
		this.cardAccountState = cardAccountState;
	}

	public AccountState getCardAccountState()
	{
		return cardAccountState;
	}

    public Calendar getNextReportDate()
    {
        return nextReportDate;
    }

    public void setNextReportDate(Calendar nextReportDate)
    {
        this.nextReportDate = nextReportDate;
	}

	public Money getOverdraftLimit()
	{
		return overdraftLimit;
	}

	public void setOverdraftLimit(Money overdraftLimit)
	{
		this.overdraftLimit = overdraftLimit;
	}

	public Money getOverdraftTotalDebtSum()
	{
		return overdraftTotalDebtSum;
	}

	public void setOverdraftTotalDebtSum(Money overdraftTotalDebtSum)
	{
		this.overdraftTotalDebtSum = overdraftTotalDebtSum;
	}

	public Money getOverdraftMinimalPayment()
	{
		return overdraftMinimalPayment;
	}

	public void setOverdraftMinimalPayment(Money overdraftMinimalPayment)
	{
		this.overdraftMinimalPayment = overdraftMinimalPayment;
	}

	public Calendar getOverdraftMinimalPaymentDate()
	{
		return overdraftMinimalPaymentDate;
	}

	public void setOverdraftMinimalPaymentDate(Calendar overdraftMinimalPaymentDate)
	{
		this.overdraftMinimalPaymentDate = overdraftMinimalPaymentDate;
	}

	public Money getOverdraftOwnSum()
	{
		return overdraftOwnSum;
	}

	public void setOverdraftOwnSum(Money overdraftOwnSum)
	{
		this.overdraftOwnSum = overdraftOwnSum;
	}

	public Client getCardClient()
	{
		return cardClient;
	}

	public void setCardClient(Client cardClient)
	{
		this.cardClient = cardClient;
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.append("id", id)
				.append("description", description)
				.append("statusDescExternalCode", statusDescExternalCode)
				.append("type", type)
				.append("number", number)
				.append("issueDate", DateHelper.formatDateToStringWithPoint(issueDate))
				.append("nextReportDate", DateHelper.formatDateToStringWithPoint(nextReportDate))
				.append("expireDate", DateHelper.formatDateToStringWithPoint(expireDate))
				.append("displayedExpireDate", displayedExpireDate)
				.append("main", main)
				.append("cardType", cardType)
				.append("availableLimit", availableLimit)
				.append("office", office)
				.append("additionalCardType", additionalCardType)
				.append("statusDescription", statusDescription)
				.append("cardState", cardState)
				.append("cardAccountState", cardAccountState)
				.append("currency", currency == null ? "" : currency.getCode())
				.append("mainCardNumber", mainCardNumber)
				.append("virtual", virtual)
				.append("primaryAccountNumber", primaryAccountNumber)
				.append("primaryAccountExternalId", primaryAccountExternalId)
				.append("kind", kind)
				.append("subkind", subkind)
				.append("uniCardType", uniCardType)
				.append("uniAccountType", uniAccountType)
				.append("cardLevel", cardLevel)
				.append("cardBonusSign", cardBonusSign)
				.append("isGFLCard", isGFLCard)
				.append("clientId", clientId)
				.append("useReportDelivery", useReportDelivery)
				.append("emailAddress", emailAddress)
				.append("reportDeliveryType", reportDeliveryType)
				.append("reportDeliveryLanguage", reportDeliveryLanguage)
				.append("availableCashLimit", availableCashLimit)
				.append("purchaseLimit", purchaseLimit)
				.append("holderName", holderName)
				.append("contractNumber", contractNumber)
				.toString();
	}

	public Code getOriginalTbCode()
	{
		return notCorrectedTbCode;
	}

	public void setNotCorrectedTbCode(Code notCorrectedTbCode)
	{
		this.notCorrectedTbCode = notCorrectedTbCode;
	}
}
