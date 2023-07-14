package com.rssl.phizgate.common.routable;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.Routable;
import com.rssl.phizic.gate.bankroll.*;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizicgate.manager.services.IDHelper;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.Calendar;

/**
 * @author hudyakov
 * @ created 10.12.2009
 * @ $Author$
 * @ $Revision$
 */

public abstract class CardBase implements Card, Routable
{
	protected String id;
	protected String description;
	protected String number;
	protected String type;
	protected Calendar issueDate;
	protected Calendar nextReportDate;
	protected boolean main;
	protected CardType cardType;
	protected Currency currency;
	protected Money availableLimit;
	protected Office office;
	protected Code notCorrectedTbCode;
	protected AdditionalCardType additionalCardType;
	protected String statusDescription;
	protected CardState cardState;
	protected AccountState cardAccountState;
	protected Calendar expireDate;
	protected String mainCardNumber;
	protected boolean virtual;
	protected String displayedExpireDate;
	//Номер специального карточного счета (СКС)
	protected String primaryAccountNumber;
	//Внешний идентификатор СКС
	protected String primaryAccountExternalId;
	// код описания карточного статуса
	protected StatusDescExternalCode statusDescExternalCode;
	// вид карты
	protected CardLevel cardLevel;
	// принадлежность к бонусной программе
	protected CardBonusSign cardBonusSign;

	protected Long kind;
	protected Long subkind;

	private String uniAccountType;
	private String uniCardType;
	private String clientId;
	private boolean useReportDelivery;
	private String emailAddress;
	private ReportDeliveryType reportDeliveryType;
	private ReportDeliveryLanguage reportDeliveryLanguage;
	private Money availableCashLimit;
	private Money purchaseLimit;
	private String holderName;
	private String contractNumber;
	private Money overdraftLimit;
	private Money overdraftTotalDebtSum;
	private Money overdraftMinimalPayment;
	private Calendar overdraftMinimalPaymentDate;
	private Money overdraftOwnSum;
	private Client cardClient;

	public String getUNIAccountType()
	{
		return uniAccountType;
	}

	public void setUNIAccountType(String uniAccountType)
	{
		this.uniAccountType = uniAccountType;
	}

	public void setUNICardType(String uniCardType)
	{
		this.uniCardType = uniCardType;
	}

	public String getUNICardType()
	{
		return uniCardType;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public Calendar getExpireDate()
	{
		return expireDate;
	}

	public void setExpireDate(Calendar expireDate)
	{
		this.expireDate = expireDate;
	}

	public void setDisplayedExpireDate(String displayedExpireDate)
	{
		this.displayedExpireDate = displayedExpireDate;
	}

	public String getDisplayedExpireDate()
	{
		return displayedExpireDate;
	}

	public Currency getCurrency()
	{
		return currency;
	}

	public void setCurrency(Currency currency)
	{
		this.currency = currency;
	}

	public void storeRouteInfo(String info)
	{
		setId(IDHelper.storeRouteInfo(getId(), info));
	}

	public String restoreRouteInfo()
	{
		return IDHelper.restoreRouteInfo(getId());
	}

	public String removeRouteInfo()
	{
		String info = IDHelper.restoreRouteInfo(id);
		setId(IDHelper.restoreOriginalId(id));

		return info;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	public Calendar getIssueDate()
	{
		return issueDate;
	}

	public void setIssueDate(Calendar issueDate)
	{
		this.issueDate = issueDate;
	}

	public boolean isMain()
	{
		return main;
	}

	public void setMain(boolean main)
	{
		this.main = main;
	}

	public Money getAvailableLimit()
	{
		return availableLimit;
	}

	public void setAvailableLimit(Money availableLimit)
	{
		this.availableLimit = availableLimit;
	}

	public Office getOffice()
	{
		return office;
	}

	public void setOffice(Office office)
	{
		this.office = office;
	}

	public Code getOriginalTbCode()
	{
		return notCorrectedTbCode;
	}

	public void setNotCorrectedTbCode(Code notCorrectedTbCode)
	{
		this.notCorrectedTbCode = notCorrectedTbCode;
	}

	public AdditionalCardType getAdditionalCardType()
	{
		return additionalCardType;
	}

	public void setAdditionalCardType(AdditionalCardType additionalCardType)
	{
		this.additionalCardType = additionalCardType;
	}

	public void setAdditionalCardType(String additionalCardType)
	{
		this.additionalCardType = AdditionalCardType.valueOf(additionalCardType);
	}

	public String getStatusDescription()
	{
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription)
	{
		this.statusDescription = statusDescription;
	}

	public CardState getCardState()
	{
		return cardState;
	}

	public void setCardState(CardState cardState)
	{
		this.cardState = cardState;
	}

	public void setCardState(String cardState)
	{
		this.cardState = CardState.valueOf(cardState);
	}

	public CardType getCardType()
	{
		return cardType;
	}

	public void setCardType(CardType cardType)
	{
		this.cardType = cardType;
	}

	public void setCardType(String cardType)
	{
		this.cardType = CardType.valueOf(cardType);
	}

	public String getMainCardNumber()
	{
		return mainCardNumber;
	}

	public void setMainCardNumber(String mainCardNumber)
	{
		this.mainCardNumber = mainCardNumber;
	}

	public void setVirtual(boolean virtual)
	{
		this.virtual = virtual;
	}

	public boolean isVirtual()
	{
		return virtual;
	}

	public String getPrimaryAccountNumber()
	{
		return primaryAccountNumber;
	}

	public void setPrimaryAccountNumber(String primaryAccountNumber)
	{
		this.primaryAccountNumber = primaryAccountNumber;
	}

	public String getPrimaryAccountExternalId()
	{
		return primaryAccountExternalId;
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

	public Long getKind()
	{
		return kind;
	}

	public Long getSubkind()
	{
		return subkind;
	}

	public StatusDescExternalCode getStatusDescExternalCode()
	{
		return statusDescExternalCode;
	}

	public void setStatusDescExternalCode(StatusDescExternalCode statusDescExternalCode)
	{
		this.statusDescExternalCode = statusDescExternalCode;
	}

	public CardBonusSign getCardBonusSign()
	{
		return cardBonusSign;
	}

	public void setCardBonusSign(CardBonusSign cardBonusSign)
	{
		this.cardBonusSign = cardBonusSign;
	}

	public void setCardBonusSign(String cardBonusSign)
	{
		this.cardBonusSign = CardBonusSign.valueOf(cardBonusSign);
	}

	public CardLevel getCardLevel()
	{
		return cardLevel;
	}

	public void setCardLevel(CardLevel cardLevel)
	{
		this.cardLevel = cardLevel;
	}

	public void setCardLevel(String cardLevel)
	{
		this.cardLevel = CardLevel.valueOf(cardLevel);
	}

	/**
	 * задать внешний идентификатор пользователя
	 * @param clientId внешний идентификатор пользователя
	 */
	public void setClientId(String clientId)
	{
		this.clientId = clientId;
	}

	public String getClientId()
	{
		return clientId;
	}

	/**
	 * задать признак используемости отчета
	 * @param useReportDelivery признак используемости отчета
	 */
	public void setUseReportDelivery(boolean useReportDelivery)
	{
		this.useReportDelivery = useReportDelivery;
	}

	public boolean isUseReportDelivery()
	{
		return useReportDelivery;
	}

	public String getEmailAddress()
	{
		return emailAddress;
	}

	/**
	 * задать адрес
	 * @param emailAddress e-mail
	 */
	public void setEmailAddress(String emailAddress)
	{
		this.emailAddress = emailAddress;
	}

	/**
	 * задать тип данных отчета
	 * @param reportDeliveryType тип данных отчета
	 */
	public void setReportDeliveryType(ReportDeliveryType reportDeliveryType)
	{
		this.reportDeliveryType = reportDeliveryType;
	}

	public ReportDeliveryType getReportDeliveryType()
	{
		return reportDeliveryType;
	}

	/**
	 * задать язык отчета
	 * @param reportDeliveryLanguage язык отчета
	 */
	public void setReportDeliveryLanguage(ReportDeliveryLanguage reportDeliveryLanguage)
	{
		this.reportDeliveryLanguage = reportDeliveryLanguage;
	}

	public ReportDeliveryLanguage getReportDeliveryLanguage()
	{
		return reportDeliveryLanguage;
	}

	public Money getAvailableCashLimit()
	{
		return availableCashLimit;
	}

	public void setAvailableCashLimit(Money availableCashLimit)
	{
		this.availableCashLimit = availableCashLimit;
	}

	public Money getPurchaseLimit()
	{
		return purchaseLimit;
	}

	public void setPurchaseLimit(Money purchaseLimit)
	{
		this.purchaseLimit = purchaseLimit;
	}

	public String getHolderName()
	{
		return holderName;
	}

	public void setHolderName(String holderName)
	{
		this.holderName = holderName;
	}

	public String getContractNumber()
	{
		return contractNumber;
	}

	public void setContractNumber(String contractNumber)
	{
		this.contractNumber = contractNumber;
	}

	public AccountState getCardAccountState()
	{
		return cardAccountState;
	}

	public void setCardAccountState(AccountState cardAccountState)
	{
		this.cardAccountState = cardAccountState;
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
}
