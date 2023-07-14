package com.rssl.phizic.business.loanCardClaim;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.cards.CardsUtil;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.loanCardOffer.LoanCardOfferType;
import com.rssl.phizic.business.offers.LoanCardOffer;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.loanclaim.LoanClaimConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * ’елпер дл€ за€вки на кредитную карту
 * @author Rtischeva
 * @ created 13.05.15
 * @ $Author$
 * @ $Revision$
 */
public class LoanCardClaimHelper
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private static final BusinessDocumentService service = new BusinessDocumentService();
	public static final Long CHANGE_TYPE_OFFER = -2L; // предложение на изменение типа
	public static final Long NEW_CARD_OFFER = -1L;    // предложение на создание новой карты

	/**
	 *  ƒоступно ли оформление за€вки на кредитную карту клиенту/гостю
	 * @param isMainPage - запрос с главной страницы или нет
	 * @return
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public static boolean isLoanCardClaimAvailable(boolean isMainPage) throws BusinessLogicException, BusinessException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		if(isMainPage)
			return personData.isLoanCardClaimAvailable();

		boolean loanCardClaimAvailable = service.isLoanCardClaimAvailable(personData.getLogin());
		personData.setLoanCardClaimAvailable(loanCardClaimAvailable);
		return loanCardClaimAvailable;
	}

	public static String detailedDescriptionOfCardsLink()
	{
		return ConfigFactory.getConfig(LoanClaimConfig.class).getDetailedDescriptionOfCardsLink();
	}

	public static boolean hasLoanNewCardOffer()
	{
		try
		{
			return NEW_CARD_OFFER.equals(checkLoanCardOfferClient());
		}
		catch (BusinessException e)
		{
			log.error(e);
			return false;
		}
	}

	public static Long checkLoanCardOfferClient() throws BusinessException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		if (CollectionUtils.isEmpty(personData.getPerson().getPersonDocuments()))
			return null;
		List<LoanCardOffer> loanCardOffers = personData.getLoanCardOfferByPersonData(null, null);
		//≈сли подход€щие условие  и продукт
		boolean isCardProductExists = false;
		for (LoanCardOffer cardOffer:loanCardOffers)
		{   //дл€ changeLimit считаем что условие существующим(без св€зывани€)
			if (cardOffer.getOfferType() == LoanCardOfferType.changeLimit)
				isCardProductExists = true;
		}

		if (!isCardProductExists)
		{   //≈сли не нашли changeLimit, то провер€ем по другим типам
			List<String> offerTypes = new ArrayList<String>(2){{
				add(LoanCardOfferType.changeType.toString());
				add(LoanCardOfferType.newCard.toString());
			}};

			isCardProductExists = personData.getCardOfferCompositProductCondition(offerTypes).size() > 0 ? true :false;
		}

		if (loanCardOffers == null || loanCardOffers.size() <= 0 || !isCardProductExists)
			return null;

		boolean isExistsOffers = (loanCardOffers != null && loanCardOffers.size() == 1);
		LoanCardOffer loanCardOffer = loanCardOffers.get(0);
		boolean isChangeTypeOrLimitOffer = (loanCardOffer.getOfferType() == LoanCardOfferType.changeLimit
				|| loanCardOffer.getOfferType() ==  LoanCardOfferType.changeType);

		if (isExistsOffers && isChangeTypeOrLimitOffer)
		{
			if (!isExistsClientCard())
				return null;

			if (loanCardOffer.getOfferType() == LoanCardOfferType.changeLimit)
				return loanCardOffer.getOfferId().getId();
			else if (loanCardOffer.getOfferType() ==  LoanCardOfferType.changeType)
				return CHANGE_TYPE_OFFER;
		}

		return NEW_CARD_OFFER;
	}

	/**
	 * ѕроверка существовани€ кредитной карты у клиента
	 * @return - true -существует, false - не существует
	 */
	private static boolean isExistsClientCard()
	{
		CardLink cardLink = CardsUtil.getClientCreditCard();
		// такой карты не существует, или закрыта
		if (cardLink == null)
			return false;

		return true;
	}
}
