package com.rssl.phizic.business.ermb.card;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.cards.CardsUtil;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.bankroll.CardLevel;
import com.rssl.phizic.gate.bankroll.CardState;
import com.rssl.phizic.gate.bankroll.CardType;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.utils.CurrencyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Collection;
import java.util.LinkedList;

/**
 * ���������� ����� ���������� �������� � ����������� �� ����� �����������
 * ����� ����� ���������� �������������� �� ��������� ��������:
 * ������������ ������ ���� �� ���������� ������������ (�� �������� ����������):
 * �	�������� ��������� �����, �������� � ��� �� ���, ��� � �����-�����������;
 * �	�������� ��������� �����, �������� � ��� �� ��������, ��� � �����-�����������;
 * �	��������� ����� � ������, �������� � ��� �� ���, ��� � �����-�����������;
 * �	��������� ����� � ������, �������� � ��� �� ��������, ��� � �����-�����������;
 * �	��������� ����� � ������, �������� � ��������, �������� �� �������� �����-�����������;
 * �	��������� ����� � ������, �������� � ��������, �������� �� �������� �����-�����������;
 * �	�������� ��������� �����;
 * �	�������� ��������� �����;
 * �	��� ���������, � ��� ����� � ������������ (��. ������ BUG077152);
 * ����� ������ ���� �������� (��. ������ BUG077152);
 *
 * ���� � ��������� ��������� ���� ����� ����� ����� ��� ���������� ��������, �� ����������:
 * �	�������� ������������ ����� ��������� Gold;
 * �	�������� ������������ ����� ���� ��������� Gold;
 * �	�������� �������� ����� ��������� Premium (Infinite, Platinum,Gold);
 * �	�������� �������� ����� ��������� Classic/Standard;
 * �	�������� �������� ����� ���� ��������� Classic/Standard ( � �.�. ���100);
 * �	�������������� ������������ �����;
 * �	�������������� �������� �����;
 * ���������� ��� ��������� ����:
 * �	American Express;
 * �	����� ��������� Gold;
 * �	��� ��������� �����
 * ����� �� ErmbHelper, ���������� ��� Card
 * @author Jatsky
 * @ created 06.06.14
 * @ $Author$
 * @ $Revision$
 */

public class PrimaryCardRecipientResolver
{
	private static final DepartmentService departmentService = new DepartmentService();

	/**
	 *
	 * @param cards ������ ����
	 * @param officeOfChargeOffResource ���� �������� (may be null)
	 * @return �����
	 * @throws BusinessException
	 */
	public static Card getReceiverCardBySenderCard(Collection<Card> cards, Office officeOfChargeOffResource) throws BusinessException
	{
		if (CollectionUtils.isEmpty(cards))
			throw new IllegalArgumentException("������ ���� ������");
		Code fromTB = getTB(officeOfChargeOffResource);
		Code fromOSB = getOSB(officeOfChargeOffResource);

		//1. ��������� ���� <���������, ��������� ���� ���������� ������ ����������>, ��� ��������� - ����� �� 1 �� 9 (1 - ����� ������������ �����)
		MultiMap priorities = new MultiValueMap();

		for (Card toResource : cards)
		{
			if (toResource.getCardState() != CardState.active)
				continue;
			int priority = getReceiverCardPriority(fromTB, fromOSB, toResource);
			priorities.put(priority, toResource);
		}

		if (priorities.isEmpty())
		{
			Collection<String> blockedCards = new LinkedList<String>();
			for (Card card : cards)
				blockedCards.add(card.getNumber());
			throw new BusinessException("���������� ���������� ������������ �����, ��� ��� � ������� ��� �� ����� �������� �����: " +
					StringUtils.join(blockedCards, ", "));
		}

		//2. �������� ����� �� ����������
		Collection<Card> priorityCards;
		for (int i = 1; i <= 9; i++)
		{
			//noinspection unchecked
			priorityCards = (Collection<Card>) priorities.get(i);
			//���� ���� � ����� ����������� ���, ���� ����� � ������� �����������
			if (CollectionUtils.isEmpty(priorityCards))
				continue;
				//���� ����� ����, ���������� ��
			else if (priorityCards.size() == 1)
				return priorityCards.iterator().next();
				//���� ���� ����� �����, ��:
			else if (i != 7 && i != 8 && i != 9) // ��������� �����
				//���� ����� ��������� �� �������� � ������������ � ����������
				return selectDebitCardWithEqualsPriority(priorityCards);
			else if (i == 7 || i == 8) // ��������� �����
				//���� ����� ��������� �� �������� � ������������ � ����������
				return selectCreditCardWithEqualsPriority(priorityCards);
		}
		// ��� ��������� �����
		//noinspection unchecked
		priorityCards = (Collection<Card>) priorities.get(9);
		return priorityCards.iterator().next();
	}

	//���������� �������� ����� �� 1 �� 9 (1 - ����� ������������)
	private static int getReceiverCardPriority(Code fromTB, Code fromOSB, Card card) throws BusinessException
	{
		Office toOffice = card.getOffice();
		Code toTB = getTB(toOffice);
		Code toOSB = getOSB(toOffice);

		CardType cardType = card.getCardType();

		boolean isDebit = cardType == CardType.debit || cardType == CardType.overdraft;
		boolean isCredit = cardType == CardType.credit;
		boolean isRuble = CurrencyUtils.isRUR(card.getCurrency());

		if (isDebit && isRuble && ObjectUtils.equals(toOSB, fromOSB))
			return 1; //�������� ��������� �����, �������� � ��� �� ���, ��� � �����-�����������;
		else if (isDebit && isRuble && ObjectUtils.equals(toTB, fromTB))
			return 2; //�������� ��������� �����, �������� � ��� �� ��������, ��� � �����-�����������;
		else if (isDebit && !isRuble && ObjectUtils.equals(toOSB, fromOSB))
			return 3; //��������� ����� � ������, �������� � ��� �� ���, ��� � �����-�����������;
		else if (isDebit && !isRuble && ObjectUtils.equals(toTB, fromTB))
			return 4; //��������� ����� � ������, �������� � ��� �� ��������, ��� � �����-�����������;
		else if (isDebit && isRuble && !ObjectUtils.equals(toTB, fromTB))
			return 5; //��������� ����� � ������, �������� � ��������, �������� �� �������� �����-�����������;
		else if (isDebit && !isRuble && !ObjectUtils.equals(toTB, fromTB))
			return 6; //��������� ����� � ������, �������� � ��������, �������� �� �������� �����-�����������;
		else if (isCredit && isRuble)
			return 7; //�������� ��������� �����;
		else if (isCredit && !isRuble)
			return 8; //�������� ��������� �����;
		return 9; // ��� ���������
	}

	//������� ���� ��������� ����� �� ������ � ����� �����������
	private static Card selectDebitCardWithEqualsPriority(Collection<Card> cards)
	{
		MultiMap priorities = new MultiValueMap();
		//1. ��������� ���� <���������, ��������� ��������� ���� ������ ����������>, ��� ��������� - ����� �� 1 �� 7 (1 - ����� ������������ �����)
		for (Card cardLink : cards)
		{
			int priority = getReceiverDebitCardPriority(cardLink);
			priorities.put(priority, cardLink);
		}

		//2. �������� ����� �� ����������
		for (int i = 1; i <= 7; i++)
		{
			//noinspection unchecked
			Collection<Card> priorityCards = (Collection<Card>) priorities.get(i);
			//���� ���� � ����� ����������� ���, ���� ����� � ������� �����������
			if (CollectionUtils.isNotEmpty(priorityCards))
				//���������� ������ ���������� �����
				return priorityCards.iterator().next();
		}
		return cards.iterator().next();
	}

	//������� ���� ��������� ����� �� ������ � ����� �����������
	private static Card selectCreditCardWithEqualsPriority(Collection<Card> cards)
	{
		MultiMap map = new MultiValueMap();
		//1. ��������� ���� <���������, ��������� ��������� ���� ������ ����������>, ��� ��������� - ����� �� 1 �� 3 (1 - ����� ������������ �����)
		for (Card cardLink : cards)
		{
			int priority = getReceiverCreditCardPriority(cardLink);
			map.put(priority, cardLink);
		}

		//2. �������� ����� �� ����������
		for (int i = 1; i <= 3; i++)
		{
			//noinspection unchecked
			Collection<Card> cardSet = (Collection<Card>) map.get(i);
			//���� ���� � ����� ����������� ���, ���� ����� � ������� �����������
			if (CollectionUtils.isNotEmpty(cardSet))
				//���������� ������ ���������� �����
				return cardSet.iterator().next();
		}
		return cards.iterator().next();
	}

	//���������� ��������� ����� ��� ������ ����� �� ��������� ���� � ���������� ����������� getReceiverCardPriority
	/*
	* ���� � ��������� ��������� ���� ����� ����� ����� ��� ���������� ��������, �� ����������:
	* �	�������� ������������ ����� ��������� Gold;
	* �	�������� ������������ ����� ���� ��������� Gold;
	* �	�������� �������� ����� ��������� Premium (Infinite, Platinum,Gold);
	* �	�������� �������� ����� ��������� Classic/Standard;
	* �	�������� �������� ����� ���� ��������� Classic/Standard ( � �.�. ���100);
	* �	�������������� ������������ �����;
	* �	�������������� �������� �����;
	*/
	private static int getReceiverDebitCardPriority(Card card)
	{
		CardLevel cardLevel = card.getCardLevel();
		boolean isSalary = CardsUtil.isSalaryCard(card);
		if (card.isMain())
		{
			if (isSalary)
			{
				if (CardLevel.isGold(cardLevel))
					return 1; //�������� ������������ ����� ��������� Gold;
				else
					return 2; //�������� ������������ ����� ���� ��������� Gold;
			}
			//������
			else
			{
				if (CardLevel.isPremium(cardLevel))
					return 3; //�������� �������� ����� ��������� Premium (Infinite, Platinum,Gold);
				else if (CardLevel.isClassicOrStandard(cardLevel))
					return 4; //�������� �������� ����� ��������� Classic/Standard;
				else
					return 5; //�������� �������� ����� ���� ��������� Classic/Standard ( � �.�. ���100);
			}
		}
		else
		{
			if (isSalary)
				return 6; //�������������� ������������ �����;
			else
				return 7; //�������������� �������� �����;
		}
	}

	//���������� ��������� ����� ��� ������ ����� �� ��������� ���� � ���������� ����������� getReceiverCardPriority
	/* ���������� ��� ��������� ����:
	* �	American Express;
	* �	����� ��������� Gold;
	* �	��� ��������� �����
	*/
	private static int getReceiverCreditCardPriority(Card card)
	{
		CardLevel cardLevel = card.getCardLevel();
		if (CardLevel.isAmericanExpress(cardLevel))
			return 1; //American Express;
		else if (CardLevel.isGold(cardLevel))
			return 2; //����� ��������� Gold;
		else
			return 3; //��� ��������� �����
	}

	private static Code getTB(Office office) throws BusinessException
	{
		if (office == null)
			return null;
		return getCode(departmentService.getTBByOffice(office));
	}

	private static Code getOSB(Office office) throws BusinessException
	{
		if (office == null)
			return null;
		return getCode(departmentService.getOSBByOffice(office));
	}

	private static Code getCode(Department department)
	{
		return department == null ? null : department.getCode();
	}
}
