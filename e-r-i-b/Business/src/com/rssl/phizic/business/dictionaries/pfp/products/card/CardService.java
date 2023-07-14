package com.rssl.phizic.business.dictionaries.pfp.products.card;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.pfp.PFPDictionaryServiceBase;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.List;

/**
 * @author akrenev
 * @ created 08.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * ������ ������ � ������� ���
 */

public class CardService
{
	private static final PFPDictionaryServiceBase service = new PFPDictionaryServiceBase();

	/**
	 * @return ����� ������� �����
	 */
	public Card getNewCard()
	{
		Card card = new Card();
		CardDiagramParameters diagramParameters = new CardDiagramParameters();
		diagramParameters.setUseNet(true);
		card.setDiagramParameters(diagramParameters);
		return card;
	}

	/**
	 * �������� �� id
	 * @param id �������������
	 * @return �����
	 * @throws BusinessException
	 */
	public Card findById(Long id) throws BusinessException
	{
		return findById(id, null);
	}

	/**
	 * �������� �� id
	 * @param id �������������
	 * @param instance ��� �������� ������ ��
	 * @return �����
	 * @throws BusinessException
	 */
	public Card findById(Long id, String instance) throws BusinessException
	{
		return service.findById(Card.class, id, instance);
	}

	/**
	 * @param cardProductIds ��������������
	 * @param instance ��� �������� ������ ��
	 * @return ������ ���� �� ���������������
	 * @throws BusinessException
	 */
	public List<Card> getListByIds(Long[] cardProductIds, String instance) throws BusinessException
	{
		return service.getListByIds(Card.class, cardProductIds, instance);
	}

	/**
	 * �������� ��� ��������
	 * @param card �����
	 * @param instance ��� �������� ������ ��
	 * @return ����������� �����
	 * @throws BusinessException
	 */
	public Card addOrUpdate(Card card, String instance) throws BusinessException
	{
		return service.addOrUpdate(card, instance);
	}

	/**
	 * �������
	 * @param card �����
	 * @param instance ��� �������� ������ ��
	 * @throws BusinessException
	 */
	public void remove(Card card, String instance) throws BusinessException
	{
		service.remove(card, instance);
	}

	/**
	 * ���� ����� � ��������� "���������� �� ���������"
	 * ������� ������: PFP_CARDS
	 * ��������� �������: fullscan
	 * ��������������: 1
	 *
	 * @param instance ��� ������ ��
	 * @return ����� � ��������� "���������� �� ���������"
	 * @throws BusinessException
	 */
	public Card findDefaultCard(String instance) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(Card.class);
		criteria.add(Expression.eq("showAsDefault", true));
		return service.findSingle(criteria, instance);
	}
}
