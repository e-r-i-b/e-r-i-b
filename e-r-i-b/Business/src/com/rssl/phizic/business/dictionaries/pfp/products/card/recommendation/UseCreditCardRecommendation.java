package com.rssl.phizic.business.dictionaries.pfp.products.card.recommendation;

import com.rssl.phizic.business.dictionaries.pfp.products.card.Card;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockDictionaryRecordBase;

import java.util.List;

/**
 * @author akrenev
 * @ created 04.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ������������� �� ������������� ��������� �����
 */

public class UseCreditCardRecommendation extends MultiBlockDictionaryRecordBase
{
	private Long id;
	private List<UseCreditCardRecommendationStep> steps;
	private ProductEfficacy accountEfficacy;
	private ProductEfficacy thanksEfficacy;
	private String recommendation;
	private List<Card> cards;

	/**
	 * @return ����
	 */
	public List<UseCreditCardRecommendationStep> getSteps()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return steps;
	}

	/**
	 * ������ ����
	 * @param steps ����
	 */
	public void setSteps(List<UseCreditCardRecommendationStep> steps)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.steps = steps;
	}

	/**
	 * @return ��������� ������������� ������
	 */
	public ProductEfficacy getAccountEfficacy()
	{
		return accountEfficacy;
	}

	/**
	 * ������ ��������� �������������
	 * @param accountEfficacy ��������� �������������
	 */
	public void setAccountEfficacy(ProductEfficacy accountEfficacy)
	{
		this.accountEfficacy = accountEfficacy;
	}

	/**
	 * @return ��������� ������������� "�������"
	 */
	public ProductEfficacy getThanksEfficacy()
	{
		return thanksEfficacy;
	}

	/**
	 * ������ ��������� ������������� "�������"
	 * @param thanksEfficacy ���������
	 */
	public void setThanksEfficacy(ProductEfficacy thanksEfficacy)
	{
		this.thanksEfficacy = thanksEfficacy;
	}

	/**
	 * @return �������������
	 */
	public String getRecommendation()
	{
		return recommendation;
	}

	/**
	 * ������ �������������
	 * @param recommendation �������������
	 */
	public void setRecommendation(String recommendation)
	{
		this.recommendation = recommendation;
	}

	/**
	 * @return �����
	 */
	public List<Card> getCards()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return cards;
	}

	/**
	 * ������ �����
	 * @param cards �����
	 */
	public void setCards(List<Card> cards)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.cards = cards;
	}
}
