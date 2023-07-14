package com.rssl.phizic.business.dictionaries.pfp.products.card.recommendation;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.pfp.PFPDictionaryServiceBase;
import com.rssl.phizic.business.dictionaries.pfp.products.card.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * @author akrenev
 * @ created 04.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * ������ ��� ������ � ����������� ������������� �� ������������� ��������� �����
 */

public class UseCreditCardRecommendationService
{
	public static final int MAX_STEP_COUNT = 4;
	private static final PFPDictionaryServiceBase service = new PFPDictionaryServiceBase();

	/**
	 * @return ��������� �������������
	 * @throws BusinessException
	 */
	public UseCreditCardRecommendation getRecommendation() throws BusinessException
	{
		return getRecommendation(null);
	}

	/**
	 * @param instanceName ��� ��
	 * @return ��������� �������������
	 * @throws BusinessException
	 */
	public UseCreditCardRecommendation getRecommendation(String instanceName) throws BusinessException
	{
		List<UseCreditCardRecommendation> all = service.getAll(UseCreditCardRecommendation.class, instanceName);
		if (all.size() > 1)
			throw new BusinessException("������� ��������� �������������.");

		if (all.size() == 1)
		{
			UseCreditCardRecommendation recommendation = all.get(0);
			if (recommendation.getAccountEfficacy() == null)
				recommendation.setAccountEfficacy(new ProductEfficacy());
			if (recommendation.getThanksEfficacy() == null)
				recommendation.setThanksEfficacy(new ProductEfficacy());
			return recommendation;
		}

		UseCreditCardRecommendation recommendation = new UseCreditCardRecommendation();
		recommendation.setAccountEfficacy(new ProductEfficacy());
		recommendation.setThanksEfficacy(new ProductEfficacy());
		recommendation.setCards(new ArrayList<Card>());
		ArrayList<UseCreditCardRecommendationStep> steps = new ArrayList<UseCreditCardRecommendationStep>();
		for (int i = 0; i < MAX_STEP_COUNT; i++)
			steps.add(new UseCreditCardRecommendationStep());
		recommendation.setSteps(steps);
		return recommendation;
	}

	/**
	 * ���������� �������� �������������
	 * @param instanceName ��� ��
	 * @param recommendation �������������
	 * @throws BusinessException
	 */
	public void saveRecommendation(UseCreditCardRecommendation recommendation, String instanceName) throws BusinessException
	{
		service.addOrUpdate(recommendation, instanceName);
	}
}
