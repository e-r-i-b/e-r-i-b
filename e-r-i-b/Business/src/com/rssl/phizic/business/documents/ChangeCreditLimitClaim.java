package com.rssl.phizic.business.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.TypeOfPayment;
import com.rssl.ikfl.crediting.FeedbackType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.cards.CardsUtil;
import com.rssl.phizic.business.loanCardOffer.LoanCardOfferType;
import com.rssl.phizic.business.offers.LoanCardOffer;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.documents.GateDocument;
import org.w3c.dom.Document;

import java.util.Calendar;
import java.util.List;

/**
 * @author lukina
 * @ created 17.06.2015
 * @ $Author$
 * @ $Revision$
 */
public class ChangeCreditLimitClaim extends GateExecutableDocument implements com.rssl.phizic.gate.claims.ChangeCreditLimitClaim
{
	private static final String ATTRIBUTE_CARD_NUMBER = "card-number";
	private static final String ATTRIBUTE_CARD_NAME = "card-name";
	private static final String ATTRIBUTE_CARD_DESC = "card-desc";
	private static final String ATTRIBUTE_CARD_ID  = "card-id";
	private static final String ATTRIBUTE_CARD_CURRENCY = "card-currency";
	private static final String ATTRIBUTE_CURRENT_LIMIT = "current-limit";
	private static final String ATTRIBUTE_CURRENT_LIMIT_CURR = "current-limit-currency";
	private static final String ATTRIBUTE_NEW_LIMIT = "new-limit";
	private static final String ATTRIBUTE_NEW_LIMIT_CURR = "new-limit-currency";
	private static final String ATTRIBUTE_LOAN_OFFER_ID  = "offerId";
	private static final String ATTRIBUTE_LOAN_OFFER_EXP_DATE  = "offerExpDate";
	private static final String ATTRIBUTE_FEEDBACK  = "feedbackType";

	public Class<? extends GateDocument> getType()
	{
		return ChangeCreditLimitClaim.class;
	}

	public void initialize(Document document, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		super.initialize(document, messageCollector);
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		try
		{
			List<LoanCardOffer> loanCardOfferList = personData.getLoanCardOfferByPersonData(1, null);
			if (loanCardOfferList.isEmpty())
				throw new DocumentLogicException("” вас нет предолжений на изменение кредитного лимита");
			LoanCardOffer loanCardOffer = null;
			for(LoanCardOffer cardOffer : loanCardOfferList)
			{
				if (cardOffer.getOfferType() == LoanCardOfferType.changeLimit)
					loanCardOffer = cardOffer;
			}
			if (loanCardOffer == null)
				throw new DocumentLogicException("” вас нет предолжений на изменение кредитного лимита");
			CardLink clientCreditCard = CardsUtil.getClientCreditCard();
			if (clientCreditCard == null) {
				throw new DocumentLogicException("” вас нет кредитных карт");
			}
			setCardNumber(clientCreditCard.getNumber());
			setCardName(clientCreditCard.getName());
			setCardDesc(clientCreditCard.getDescription());
			setCardId(clientCreditCard.getId());
			setCardCurrency(clientCreditCard.getCurrency().getCode());
			setCurrentLimit(clientCreditCard.getCard().getOverdraftLimit());
			setNewLimit(loanCardOffer.getMaxLimit());
			setOfferId(loanCardOffer.getOfferId().toString());
			setOfferExpDate(loanCardOffer.getExpDate());
			setFeedbackType(FeedbackType.PRESENTATION.toString());
		}
		catch (BusinessException ex)
		{
			throw new DocumentException("” вас нет предолжений на изменение кредитного лимита", ex);
		}
	}

	public String getCardNumber()
	{
		return getNullSaveAttributeStringValue(ATTRIBUTE_CARD_NUMBER);
	}

	public void setCardNumber(String cardNumber)
	{
		setNullSaveAttributeStringValue(ATTRIBUTE_CARD_NUMBER, cardNumber);
	}

	public  String getCardName()
	{
		return getNullSaveAttributeStringValue(ATTRIBUTE_CARD_NAME);
	}

	public void setCardName(String cardName)
	{
		setNullSaveAttributeStringValue(ATTRIBUTE_CARD_NAME, cardName);
	}

	public String getCardDesc()
	{
		return getNullSaveAttributeStringValue(ATTRIBUTE_CARD_DESC);
	}

	public void setCardDesc(String cardDesc)
	{
		setNullSaveAttributeStringValue(ATTRIBUTE_CARD_DESC, cardDesc);
	}

	public Long getCardId()
	{
		return getNullSaveAttributeLongValue(ATTRIBUTE_CARD_ID);
	}

	public void setCardId(Long cardId)
	{
		setNullSaveAttributeLongValue(ATTRIBUTE_CARD_ID, cardId);
	}

	public String  getCardCurrency()
	{
		return getNullSaveAttributeStringValue(ATTRIBUTE_CARD_CURRENCY);
	}

	public void setCardCurrency(String cardCurrency)
	{
		setNullSaveAttributeStringValue(ATTRIBUTE_CARD_CURRENCY, cardCurrency);
	}

	public Money getCurrentLimit()
	{
		String currencyText = getNullSaveAttributeStringValue(ATTRIBUTE_CURRENT_LIMIT);
		String amountText = getNullSaveAttributeStringValue(ATTRIBUTE_CURRENT_LIMIT_CURR);
		return createMoney(amountText, currencyText);
	}

	public void setCurrentLimit(Money currentLimit)
	{
		if (currentLimit != null)
		{
			setNullSaveAttributeStringValue(ATTRIBUTE_CURRENT_LIMIT_CURR, currentLimit.getCurrency().getCode());
			setNullSaveAttributeDecimalValue(ATTRIBUTE_CURRENT_LIMIT, currentLimit.getDecimal());
		}
		else
		{
			setNullSaveAttributeStringValue(ATTRIBUTE_CURRENT_LIMIT_CURR, null);
			setNullSaveAttributeDecimalValue(ATTRIBUTE_CURRENT_LIMIT, null);
		}
	}

	public Money  getNewLimit()
	{
		String currencyText = getNullSaveAttributeStringValue(ATTRIBUTE_NEW_LIMIT);
		String amountText = getNullSaveAttributeStringValue(ATTRIBUTE_NEW_LIMIT_CURR);
		return createMoney(amountText, currencyText);
	}

	public void setNewLimit(Money newLimit)
	{
		if (newLimit != null)
		{
			setNullSaveAttributeStringValue(ATTRIBUTE_NEW_LIMIT_CURR, newLimit.getCurrency().getCode());
			setNullSaveAttributeDecimalValue(ATTRIBUTE_NEW_LIMIT, newLimit.getDecimal());
		}
		else
		{
			setNullSaveAttributeStringValue(ATTRIBUTE_NEW_LIMIT_CURR, null);
			setNullSaveAttributeDecimalValue(ATTRIBUTE_NEW_LIMIT, null);
		}
	}


	public String  getFeedbackType()
	{
		return getNullSaveAttributeStringValue(ATTRIBUTE_FEEDBACK);
	}

	public void setFeedbackType(String cardNumber)
	{
		setNullSaveAttributeStringValue(ATTRIBUTE_FEEDBACK, cardNumber);
	}

	public String  getOfferId()
	{
		return getNullSaveAttributeStringValue(ATTRIBUTE_LOAN_OFFER_ID);
	}

	public Calendar getOfferExpDate()
	{
		return getNullSaveAttributeCalendarValue(ATTRIBUTE_LOAN_OFFER_EXP_DATE);
	}

	public void setOfferExpDate(Calendar expDate)
	{
		setNullSaveAttributeCalendarValue(ATTRIBUTE_LOAN_OFFER_EXP_DATE, expDate);
	}

	public void  setOfferId(String cardNumber)
	{
		setNullSaveAttributeStringValue(ATTRIBUTE_LOAN_OFFER_ID, cardNumber);
	}


	public TypeOfPayment getTypeOfPayment()
	{
		return  TypeOfPayment.NOT_PAYMENT_OPEATION;
	}
}
