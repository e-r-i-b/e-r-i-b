package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.bankroll.*;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.utils.DateHelper;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.Calendar;

/**
 * User: Balovtsev
 * Date: 23.10.2012
 * Time: 10:41:59
 */
public class StoredCard extends AbstractStoredResource<Card, Void> implements Card
{
	private Long                    cardLinkId;
	private Boolean                 virtual;
	private String                  holderName;
	private String                  statusDescription;
	private String                  displayedExpireDate;
	private StatusDescExternalCode  statusDescExternalCode;
	private Calendar                issueDate;
	private Calendar                nextReportDate;
	private CardType                cardType;
	private CardState               cardState;
	private Money                   purchaseLimit;
	private Money                   availableLimit;
	private Money                   availableCashLimit;
	private CardLevel               cardLevel; //вид карты
	private CardBonusSign           cardBonusSign; //принадлежность к бонусной программе

	private static final ExtendedBlockedCardFilter extBlockedCardFilter = new ExtendedBlockedCardFilter();

	public Long getCardLinkId()
	{
		return cardLinkId;
	}

	public void setCardLinkId(Long cardLinkId)
	{
		this.cardLinkId = cardLinkId;
	}

	public void setStatusDescExternalCode(StatusDescExternalCode statusDescExternalCode)
	{
		this.statusDescExternalCode = statusDescExternalCode;
	}

	public StatusDescExternalCode getStatusDescExternalCode()
	{
		return statusDescExternalCode;
	}

	public void setIssueDate(Calendar issueDate)
	{
		this.issueDate = issueDate;
	}

	public Calendar getIssueDate()
	{
		return issueDate;
	}

	public void setDisplayedExpireDate(String displayedExpireDate)
	{
		this.displayedExpireDate = displayedExpireDate;
	}

	public String getDisplayedExpireDate()
	{
		return displayedExpireDate;
	}

	public void setCardType(CardType cardType)
	{
		this.cardType = cardType;
	}

	public CardType getCardType()
	{
		return cardType;
	}

	public void setAvailableLimit(Money availableLimit)
	{
		this.availableLimit = availableLimit;
	}

	public Money getAvailableLimit()
	{
		return availableLimit;
	}


	public AdditionalCardType getAdditionalCardType()
	{
		return ((CardLink) getResourceLink()).getAdditionalCardType();
	}

	public void setStatusDescription(String statusDescription)
	{
		this.statusDescription = statusDescription;
	}

	public String getStatusDescription()
	{
		return statusDescription;
	}

	public void setCardState(CardState cardState)
	{
		this.cardState = cardState;
	}

	public CardState getCardState()
	{
		return cardState;
	}

	public AccountState getCardAccountState()
	{
		return null;
	}

	public void setVirtual(Boolean virtual)
	{
		this.virtual = virtual;
	}

	public Boolean getVirtual()
	{
		return virtual;
	}

	public boolean isVirtual()
	{
		return getVirtual();
	}

	public void setPurchaseLimit(Money purchaseLimit)
	{
		this.purchaseLimit = purchaseLimit;
	}

	public Money getPurchaseLimit()
	{
		return purchaseLimit;
	}

	public void setAvailableCashLimit(Money availableCashLimit)
	{
		this.availableCashLimit = availableCashLimit;
	}

	public Money getAvailableCashLimit()
	{
		return availableCashLimit;
	}

	public void setHolderName(String holderName)
	{
		this.holderName = holderName;
	}

	public String getHolderName()
	{
		return holderName;
	}
	
	public String getId()
	{
		return getResourceLink().getExternalId();
	}

	public String getNumber()
	{
		return getResourceLink().getNumber();
	}

	public boolean isMain()
	{
		return ((CardLink) getResourceLink()).isMain();
	}

	public String getDescription()
	{
		return getResourceLink().getDescription();
	}

	public Calendar getExpireDate()
	{
		return ((CardLink) getResourceLink()).getExpireDate();
	}

	public Currency getCurrency()
	{
		return getResourceLink().getCurrency();
	}

	public String getMainCardNumber()
	{
		return ((CardLink) getResourceLink()).getMainCardNumber();
	}

	public Long getKind()
	{
		return ((CardLink) getResourceLink()).getKind();
	}

	public Long getSubkind()
	{
		return ((CardLink) getResourceLink()).getSubkind();
	}

	public String getUNICardType()
	{
		return null;
	}

	public String getUNIAccountType()
	{
		return null; 
	}

	public String getPrimaryAccountNumber()
	{
		return ((CardLink) getResourceLink()).getCardPrimaryAccount();
	}

	public void update(Card card)
	{
		this.virtual                = card.isVirtual();
		this.cardType               = card.getCardType();
		this.issueDate              = card.getIssueDate();
		this.cardState              = card.getCardState();
		this.displayedExpireDate    = card.getDisplayedExpireDate();
		this.statusDescExternalCode = card.getStatusDescExternalCode();
		this.availableLimit         = card.getAvailableLimit();
		this.cardBonusSign          = card.getCardBonusSign();
		this.cardLevel              = card.getCardLevel();
		this.holderName             = card.getHolderName();
		this.purchaseLimit          = card.getPurchaseLimit();
		this.availableCashLimit     = card.getAvailableCashLimit();
        this.nextReportDate         = card.getNextReportDate();

		updateOffice(card.getOffice());
		setEntityUpdateTime( Calendar.getInstance() );
	}

	public boolean needUpdate(Card card)
	{
		// Сравниваем не все свойства, т.к. на входе продукт обновляется безусловно
		if (cardState != card.getCardState())
			return true;

		if (statusDescExternalCode != card.getStatusDescExternalCode())
			return true;

		if (!MoneyUtil.equalsNullIgnore(availableLimit, card.getAvailableLimit()))
			return true;

		return false;
	}

	public CardLevel getCardLevel()
	{
		return cardLevel;
	}

	public void setCardLevel(CardLevel cardLevel)
	{
		this.cardLevel = cardLevel;
	}

	public CardBonusSign getCardBonusSign()
	{
		return cardBonusSign;
	}

	public String getClientId()
	{
		return null;
	}

	public boolean isUseReportDelivery()
	{
		return false;
	}

	public String getEmailAddress()
	{
		return null;
	}

	public ReportDeliveryType getReportDeliveryType()
	{
		return null;
	}

	public ReportDeliveryLanguage getReportDeliveryLanguage()
	{
		return null;
	}

	public void setCardBonusSign(CardBonusSign cardBonusSign)
	{
		this.cardBonusSign = cardBonusSign;
	}

	public String getContractNumber()
	{
		return ((CardLink) getResourceLink()).getContractNumber();
	}

	public Money getOverdraftLimit()
	{
		return null;
	}

	public Money getOverdraftTotalDebtSum()
	{
		return null;
	}

	public Money getOverdraftMinimalPayment()
	{
		return null;
	}

	public Calendar getOverdraftMinimalPaymentDate()
	{
		return null;
	}

	public Money getOverdraftOwnSum()
	{
		return null;
	}

	public Client getCardClient()
	{
		return MOCK_CLIENT;
	}

	/**
	 *
 	 * Неиспользуемые свойства
	 *
	 **/
	public String getType()                     {  return null;  }
	public String getPrimaryAccountExternalId()	{  return null;  }

    public Calendar getNextReportDate()
    {
        return nextReportDate;
    }

    public void setNextReportDate(Calendar nextReportDate)
    {
        this.nextReportDate = nextReportDate;
    }

	@Override public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.append("cardLinkId", cardLinkId)
				.append("virtual", virtual)
				.append("holderName", holderName)
				.append("statusDescription", statusDescription)
				.append("displayedExpireDate", displayedExpireDate)
				.append("statusDescExternalCode", statusDescExternalCode)
				.append("issueDate", DateHelper.formatDateToStringWithPoint(issueDate))
				.append("nextReportDate", DateHelper.formatDateToStringWithPoint(nextReportDate))
				.append("cardType", cardType)
				.append("cardState", cardState)
				.append("purchaseLimit", purchaseLimit)
				.append("availableLimit", availableLimit)
				.append("availableCashLimit", availableCashLimit)
				.append("cardLevel", cardLevel)
				.append("cardBonusSign", cardBonusSign)
				.toString();
	}
}
