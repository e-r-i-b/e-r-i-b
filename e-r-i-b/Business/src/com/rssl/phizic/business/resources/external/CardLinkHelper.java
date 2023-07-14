package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.utils.CurrencyUtils;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.MockHelper;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.*;

/**
 * @author Erkin
 * @ created 17.08.2011
 * @ $Author$
 * @ $Revision$
 */
public class CardLinkHelper
{
	private static final ExternalResourceService externalResourceService = new ExternalResourceService();
	private static final SimpleService simpleService = new SimpleService();


	/**
	 * ���������� ������ ���� �� ����������
	 * @param cardLinks - ��������� ����������
	 * @return ������ ������� ����
	 */
	public static List<String> listCardNumbers(Collection<CardLink> cardLinks)
	{
		List<String> list = new ArrayList<String>(cardLinks.size());
		for (CardLink cardLink : cardLinks)
			list.add(cardLink.getNumber());
		return list;
	}

	/**
	 * ���������� ��� "�����_����� -> �����" �� ����������
	 * @param cardLinks - ��������� ����������
	 * @return ��� "�����_����� -> �����"
	 */
	public static Map<String, Card> mapCardsByNumber(Collection<CardLink> cardLinks)
	{
		Map<String, Card> map = new LinkedHashMap<String, Card>(cardLinks.size());
		for (CardLink cardLink : cardLinks)
			map.put(cardLink.getNumber(), cardLink.getCard());
		return map;
	}

	/**
	 * �������� ����� �� ������
	 * @param cardLinks - ��������� ����������
	 * @param cardNumber - ����� �����
	 * @return ����� ��� null, ���� <cardLinks> �� �������� ����-���� � ����� ������� 
	 */
	public static Card selectCardByNumber(Collection<CardLink> cardLinks, String cardNumber)
	{
		if (StringHelper.isEmpty(cardNumber))
			throw new IllegalArgumentException("�������� 'cardNumber' �� ����� ���� ������");

		for (CardLink cardLink : cardLinks) {
			if (cardLink.getNumber().equals(cardNumber))
				return cardLink.getCard();
		}
		return null;
	}

	/**
	 * @return �������� ����� �������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public static List<CardLink> getMainCardLinks() throws BusinessException, BusinessLogicException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		List<CardLink> result = new ArrayList<CardLink>();
		//������ ������������ ������. ��������� ������ �� �� ��� ����� ��������.
		for(CardLink link : personData.getCards())
		{
			if (link.isMain() && link.isActive())
			{
				result.add(link);
			}
		}
		return result;
	}

	public static List<CardLink> getMainCardLinks(Login login) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(CardLink.class);
        criteria.add(Expression.eq("loginId",login.getId())).
                 add(Expression.eq("main", true));

		return simpleService.find(criteria);
	}

	/**
	 * ���������� ��������� ���������� �� ����� �� ������ �����: ��������������� ����� ����� [�������� �����] ������ �����
	 * @param cardNumber - ����� �����
	 * @return
	 * @throws BusinessException
	 */
	public static String getCardInfoByCardNumber(String cardNumber) throws BusinessException
	{
		if (StringHelper.isEmpty(cardNumber))
			return null;

		DetachedCriteria criteria = DetachedCriteria.forClass(CardLink.class);
		criteria.add(Expression.eq("number", cardNumber));
		//����� ��������� ��������� ������ � ���������� ������� �����, ������ ����� ������ ����� �����������
		List<CardLink> cardLinkList = simpleService.find(criteria);
		if (CollectionUtils.isEmpty(cardLinkList))
			return null;

		CardLink cardLink = cardLinkList.get(0);
		String cardInfo = MaskUtil.getCutCardNumber(cardLink.getNumber())+" ["+cardLink.getName()+"] "+
				CurrencyUtils.getCurrencySign(cardLink.getCurrency().getCode());

		return cardInfo;
	}

	/**
	 * �������� �����
	 * @param login �����
	 * @param cardNumber ����� �����
	 * @return ����
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public static Card getCardByCardNumber(Login login, String cardNumber) throws BusinessException, BusinessLogicException
	{
		CardLink cardLink = externalResourceService.findVisibleLinkByNumber(login, ResourceType.CARD ,cardNumber);
		if (cardLink == null)
		{
			throw new BusinessLogicException("���������� �������� ���������� �� ����� " + MaskUtil.getCutCardNumber(cardNumber));
		}
		Card card = cardLink.getCard();
		if (MockHelper.isMockObject(card))
		{
			throw new BusinessLogicException("���������� �������� ���������� �� ����� " + MaskUtil.getCutCardNumber(cardNumber));
		}
		return card;
	}
}
