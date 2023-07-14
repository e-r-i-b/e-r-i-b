package com.rssl.phizic.operations.loanOffer;

import com.rssl.ikfl.crediting.CreditingConfig;
import com.rssl.ikfl.crediting.FeedbackType;
import com.rssl.ikfl.crediting.OfferId;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.creditcards.products.CreditCardProduct;
import com.rssl.phizic.business.creditcards.products.CreditCardProductService;
import com.rssl.phizic.business.loanCardOffer.LoanCardOfferType;
import com.rssl.phizic.business.offers.LoanCardOffer;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.bankroll.CardType;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Moshenko
 * Date: 31.05.2011
 * Time: 11:59:57
 * Отображение  предложений по картам, на странице "Предложения банка"
 */
public class GetLoanCardOfferViewOperation extends GetLoanOfferViewOperationBase
{
	private static final CreditCardProductService creditCardProductService = new CreditCardProductService();

	/**
	 * @param needWaitCRM Ждать ли CRM если предложения не поступили сразу.
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initialize(boolean needWaitCRM) throws BusinessException, BusinessLogicException
	{
		super.initialize();
		Login login = personData.getLogin();
		//если логин не первый
		if (!login.isFirstLogin())
			loanCardOffers = getOneLoanCardOfferByLogin(needWaitCRM) ;
		else
			loanCardOffers = new ArrayList<LoanCardOffer>();
	}

	/**
	 * возвращаем не просмотренные предложения
	 * @return
	 */
	protected List<LoanCardOffer> getOneLoanCardOffer(boolean needWaitCRM) throws BusinessException
	{
		List<LoanCardOffer> loanCardOffers = personData.getLoanCardOfferByPersonData(1, null);
		if (!loanCardOffers.isEmpty())
			return loanCardOffers;
		else if (needWaitCRM)
		{
			// Если был запрос в СРМ то ждем.
			if (personData.isOfferReceivingInProgress())
			{
				CreditingConfig creditingConfig = ConfigFactory.getConfig(CreditingConfig.class);
				try
				{
					Thread.sleep(creditingConfig.getWaitingTime());
				}
				catch (InterruptedException e)
				{
					log.error(e.getMessage(), e);
				}
				//Повторная попытка
				loanCardOffers = personData.getLoanCardOfferByPersonData(1, null);
			}
		}
		return loanCardOffers;
	}

	private List<LoanCardOffer> getOneLoanCardOfferByLogin(boolean needWaitCRM) throws BusinessException, BusinessLogicException
	{
		//для отображения берем только одно последние предложение на получение кредитной карты, только не показанное.
		List<CardLink> cardLinks = personData.getCards();
		try
		{
			//в связи с тем что появились новый типы предложений(изменение лимита, и типа карты)
			//необходимо возвращать предложение даже если у клиента есть кредитная карта
			List<LoanCardOffer> loanCardOffers = getOneLoanCardOffer(needWaitCRM);

			if (loanCardOffers.isEmpty())
				return new ArrayList<LoanCardOffer>();

			//есть ли соответствующие предложению по кредитной карте карточные продукты
			List<CreditCardProduct> creditCardProductList = creditCardProductService.getPublicApprovedProducts(loanCardOffers.get(0).getMaxLimit().getCurrency().getCode(), loanCardOffers.get(0).getMaxLimit().getDecimal());
			if (CollectionUtils.isEmpty(creditCardProductList))
				return new ArrayList<LoanCardOffer>();

			//проверяем есть ли у клиента кр.карта
			boolean hasKreditCard = false;

			for (CardLink cardLink : cardLinks)
			{
				CardType type = cardLink.getCard().getCardType();
				if (CardType.credit == type)
				{
					hasKreditCard = true;
					break;
				}
			}

			//если это предложение на новую кредитную карту(newCard), а кредитная карта у клиента уже есть
			//то возвращаем пустой список
			final LoanCardOfferType offerType = loanCardOffers.get(0).getOfferType();
			if (LoanCardOfferType.newCard == offerType)
			{
				if (hasKreditCard)
					return new ArrayList<LoanCardOffer>();
			}
			//если тип предложений 2-changeLimit,3-changeType надо проверять есть ли у клиента кр. карта
			//если нет то возвращять пустой список
			else if ((LoanCardOfferType.changeLimit == offerType) || (LoanCardOfferType.changeType == offerType))
			{
				if (!hasKreditCard)
					return new ArrayList<LoanCardOffer>();
			}

			return loanCardOffers;
		}
		catch (Exception e)
		{
			getFailOffer(personData.getLogin(), e);
		}
		return new ArrayList<LoanCardOffer>();
	}

	public List<OfferId> getListNoInformedCardOffers() throws BusinessException
	{
		List<OfferId> offerIds = new ArrayList<OfferId>();
        if (loanCardOffers == null)
            return offerIds;
		for (LoanCardOffer offer : loanCardOffers)
			if (!personData.isFeedbackForLoanCardOfferSend(offer.getOfferId(), FeedbackType.INFORM))
				offerIds.add(offer.getOfferId());
		return offerIds;
	}

	public List<OfferId> getListNoPresentationCardOffers() throws BusinessException
	{
		List<OfferId> offerIds = new ArrayList<OfferId>();
        if (loanCardOffers == null)
            return offerIds;
		for (LoanCardOffer offer : loanCardOffers)
			if (!personData.isFeedbackForLoanCardOfferSend(offer.getOfferId(), FeedbackType.PRESENTATION))
				offerIds.add(offer.getOfferId());
		return offerIds;
	}
}
