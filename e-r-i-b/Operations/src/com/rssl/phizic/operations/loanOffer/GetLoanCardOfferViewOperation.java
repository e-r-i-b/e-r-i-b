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
 * �����������  ����������� �� ������, �� �������� "����������� �����"
 */
public class GetLoanCardOfferViewOperation extends GetLoanOfferViewOperationBase
{
	private static final CreditCardProductService creditCardProductService = new CreditCardProductService();

	/**
	 * @param needWaitCRM ����� �� CRM ���� ����������� �� ��������� �����.
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initialize(boolean needWaitCRM) throws BusinessException, BusinessLogicException
	{
		super.initialize();
		Login login = personData.getLogin();
		//���� ����� �� ������
		if (!login.isFirstLogin())
			loanCardOffers = getOneLoanCardOfferByLogin(needWaitCRM) ;
		else
			loanCardOffers = new ArrayList<LoanCardOffer>();
	}

	/**
	 * ���������� �� ������������� �����������
	 * @return
	 */
	protected List<LoanCardOffer> getOneLoanCardOffer(boolean needWaitCRM) throws BusinessException
	{
		List<LoanCardOffer> loanCardOffers = personData.getLoanCardOfferByPersonData(1, null);
		if (!loanCardOffers.isEmpty())
			return loanCardOffers;
		else if (needWaitCRM)
		{
			// ���� ��� ������ � ��� �� ����.
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
				//��������� �������
				loanCardOffers = personData.getLoanCardOfferByPersonData(1, null);
			}
		}
		return loanCardOffers;
	}

	private List<LoanCardOffer> getOneLoanCardOfferByLogin(boolean needWaitCRM) throws BusinessException, BusinessLogicException
	{
		//��� ����������� ����� ������ ���� ��������� ����������� �� ��������� ��������� �����, ������ �� ����������.
		List<CardLink> cardLinks = personData.getCards();
		try
		{
			//� ����� � ��� ��� ��������� ����� ���� �����������(��������� ������, � ���� �����)
			//���������� ���������� ����������� ���� ���� � ������� ���� ��������� �����
			List<LoanCardOffer> loanCardOffers = getOneLoanCardOffer(needWaitCRM);

			if (loanCardOffers.isEmpty())
				return new ArrayList<LoanCardOffer>();

			//���� �� ��������������� ����������� �� ��������� ����� ��������� ��������
			List<CreditCardProduct> creditCardProductList = creditCardProductService.getPublicApprovedProducts(loanCardOffers.get(0).getMaxLimit().getCurrency().getCode(), loanCardOffers.get(0).getMaxLimit().getDecimal());
			if (CollectionUtils.isEmpty(creditCardProductList))
				return new ArrayList<LoanCardOffer>();

			//��������� ���� �� � ������� ��.�����
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

			//���� ��� ����������� �� ����� ��������� �����(newCard), � ��������� ����� � ������� ��� ����
			//�� ���������� ������ ������
			final LoanCardOfferType offerType = loanCardOffers.get(0).getOfferType();
			if (LoanCardOfferType.newCard == offerType)
			{
				if (hasKreditCard)
					return new ArrayList<LoanCardOffer>();
			}
			//���� ��� ����������� 2-changeLimit,3-changeType ���� ��������� ���� �� � ������� ��. �����
			//���� ��� �� ���������� ������ ������
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
