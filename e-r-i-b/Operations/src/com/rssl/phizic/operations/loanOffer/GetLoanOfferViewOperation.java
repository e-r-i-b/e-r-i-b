package com.rssl.phizic.operations.loanOffer;

import com.rssl.ikfl.crediting.CreditingConfig;
import com.rssl.ikfl.crediting.FeedbackType;
import com.rssl.ikfl.crediting.OfferId;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.loanOffer.LoanOfferComparator;
import com.rssl.phizic.business.loanOffer.LoanOfferHelper;
import com.rssl.phizic.business.offers.LoanOffer;
import com.rssl.phizic.config.ConfigFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: Moshenko
 * Date: 31.05.2011
 * Time: 11:56:32
 * Отображение  предложений по кредитам
 */
public class GetLoanOfferViewOperation extends GetLoanOfferViewOperationBase
{

	/**
	 * @param needWaitCRM Ждать ли CRM если предложения не поступили сразу.
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initialize(boolean needWaitCRM) throws BusinessException, BusinessLogicException
	{
		super.initialize();
		List<LoanOffer> offersList = getLoanOfferByPerson(needWaitCRM);
		if (!offersList.isEmpty())
		{
			loanOffers.addAll(LoanOfferHelper.filterLoanOffersByEndDate(offersList));
			Collections.sort(loanOffers, new LoanOfferComparator());
		}
	}

	/**
	 * Заполняет список loanOffer-ов в количестве count
	 * @param count
	 */
	public void initialize(int count) throws BusinessException, BusinessLogicException
	{
		super.initialize();
		loanOffers = personData.getLoanOfferByPersonData(count,null);
	}

	private List<LoanOffer> getLoanOfferByPerson(boolean needWaitCRM)
	{
		try
		{
			List<LoanOffer> loanOffers = personData.getLoanOfferByPersonData(null,null);
			if (!loanOffers.isEmpty())
				return loanOffers;
			if (needWaitCRM)
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
					loanOffers = personData.getLoanOfferByPersonData(null, null);
				}
			}
			return loanOffers;
		}
		catch (Exception e)
		{
			getFailOffer(personData.getLogin(), e);
		}
		return null;
	}

	public List<OfferId> getListNoInformedOffers() throws BusinessException
	{
		List<OfferId> offerIds = new ArrayList<OfferId>();
		for (LoanOffer offer : loanOffers)
			if (!personData.isFeedbackForLoanOfferSend(offer.getOfferId(), FeedbackType.INFORM))
				offerIds.add(offer.getOfferId());
		return offerIds;
	}

	public List<OfferId> getListNoPresentationOffers() throws BusinessException
	{
		List<OfferId> offerIds = new ArrayList<OfferId>();
		for (LoanOffer offer : loanOffers)
			if (!personData.isFeedbackForLoanOfferSend(offer.getOfferId(), FeedbackType.PRESENTATION))
				offerIds.add(offer.getOfferId());
		return offerIds;
	}
}
