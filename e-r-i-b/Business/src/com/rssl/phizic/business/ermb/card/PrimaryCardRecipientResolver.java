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
 * Определяет карту получателя перевода в зависимости от карты отправителя
 * Выбор карты получателя осуществляется по следующим правилам:
 * Составляется список карт со следующими приоритетами (по убыванию приоритета):
 * •	Рублевые дебетовые карты, открытые в том же ОСБ, что и карта-отправитель;
 * •	Рублевые дебетовые карты, открытые в том же тербанке, что и карта-отправитель;
 * •	Дебетовые карты в валюте, открытые в том же ОСБ, что и карта-отправитель;
 * •	Дебетовые карты в валюте, открытые в том же тербанке, что и карта-отправитель;
 * •	Дебетовые карты в рублях, открытые в тербанке, отличном от тербанка карты-отправителя;
 * •	Дебетовые карты в валюте, открытые в тербанке, отличном от тербанка карты-отправителя;
 * •	Рублевые кредитные карты;
 * •	Валютные кредитные карты;
 * •	Все остальные, в том числе и овердрафтные (см. запрос BUG077152);
 * Карта должна быть активная (см. запрос BUG077152);
 *
 * Если в выбранной категории есть более одной карты для зачисления перевода, то выбираются:
 * •	основная «зарплатная» карта категории Gold;
 * •	основная «зарплатная» карта ниже категории Gold;
 * •	основная «личная» карта категории Premium (Infinite, Platinum,Gold);
 * •	основная «личная» карта категории Classic/Standard;
 * •	основная «личная» карта ниже категории Classic/Standard ( в т.ч. ПРО100);
 * •	дополнительная «зарплатная» карта;
 * •	дополнительная «личная» карта;
 * Приоритеты для кредитных карт:
 * •	American Express;
 * •	Карты категории Gold;
 * •	все остальные карты
 * Взято из ErmbHelper, переделано под Card
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
	 * @param cards список карт
	 * @param officeOfChargeOffResource офис списания (may be null)
	 * @return карта
	 * @throws BusinessException
	 */
	public static Card getReceiverCardBySenderCard(Collection<Card> cards, Office officeOfChargeOffResource) throws BusinessException
	{
		if (CollectionUtils.isEmpty(cards))
			throw new IllegalArgumentException("Список карт пустой");
		Code fromTB = getTB(officeOfChargeOffResource);
		Code fromOSB = getOSB(officeOfChargeOffResource);

		//1. заполняем мапу <приоритет, множество карт получателя одного приоритета>, где приоритет - число от 1 до 9 (1 - самые приоритетные карты)
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
			throw new BusinessException("Невозможно определить приоритетную карту, так как у клиента нет ни одной активной карты: " +
					StringUtils.join(blockedCards, ", "));
		}

		//2. выбираем карту по приоритету
		Collection<Card> priorityCards;
		for (int i = 1; i <= 9; i++)
		{
			//noinspection unchecked
			priorityCards = (Collection<Card>) priorities.get(i);
			//если карт с таким приоритетом нет, ищем карту с меньшим приоритетом
			if (CollectionUtils.isEmpty(priorityCards))
				continue;
				//если карта одна, возвращаем ее
			else if (priorityCards.size() == 1)
				return priorityCards.iterator().next();
				//если карт более одной, то:
			else if (i != 7 && i != 8 && i != 9) // дебетовые карты
				//если карты дебетовые то выбираем в соответствии с алгоритмом
				return selectDebitCardWithEqualsPriority(priorityCards);
			else if (i == 7 || i == 8) // кредитные карты
				//если карты кредитные то выбираем в соответствии с алгоритмом
				return selectCreditCardWithEqualsPriority(priorityCards);
		}
		// все остальные карты
		//noinspection unchecked
		priorityCards = (Collection<Card>) priorities.get(9);
		return priorityCards.iterator().next();
	}

	//возвращает проритет карты от 1 до 9 (1 - самая приоритетная)
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
			return 1; //Рублевые дебетовые карты, открытые в том же ОСБ, что и карта-отправитель;
		else if (isDebit && isRuble && ObjectUtils.equals(toTB, fromTB))
			return 2; //Рублевые дебетовые карты, открытые в том же тербанке, что и карта-отправитель;
		else if (isDebit && !isRuble && ObjectUtils.equals(toOSB, fromOSB))
			return 3; //Дебетовые карты в валюте, открытые в том же ОСБ, что и карта-отправитель;
		else if (isDebit && !isRuble && ObjectUtils.equals(toTB, fromTB))
			return 4; //Дебетовые карты в валюте, открытые в том же тербанке, что и карта-отправитель;
		else if (isDebit && isRuble && !ObjectUtils.equals(toTB, fromTB))
			return 5; //Дебетовые карты в рублях, открытые в тербанке, отличном от тербанка карты-отправителя;
		else if (isDebit && !isRuble && !ObjectUtils.equals(toTB, fromTB))
			return 6; //Дебетовые карты в рублях, открытые в тербанке, отличном от тербанка карты-отправителя;
		else if (isCredit && isRuble)
			return 7; //Рублевые кредитные карты;
		else if (isCredit && !isRuble)
			return 8; //Валютные кредитные карты;
		return 9; // все остальные
	}

	//выбрать одну дебетовую карту из списка с одним приоритетом
	private static Card selectDebitCardWithEqualsPriority(Collection<Card> cards)
	{
		MultiMap priorities = new MultiValueMap();
		//1. заполняем мапу <приоритет, множество дебетовых карт одного приоритета>, где приоритет - число от 1 до 7 (1 - самые приоритетные карты)
		for (Card cardLink : cards)
		{
			int priority = getReceiverDebitCardPriority(cardLink);
			priorities.put(priority, cardLink);
		}

		//2. выбираем карту по приоритету
		for (int i = 1; i <= 7; i++)
		{
			//noinspection unchecked
			Collection<Card> priorityCards = (Collection<Card>) priorities.get(i);
			//если карт с таким приоритетом нет, ищем карту с меньшим приоритетом
			if (CollectionUtils.isNotEmpty(priorityCards))
				//возвращаем первую попавшуюся карту
				return priorityCards.iterator().next();
		}
		return cards.iterator().next();
	}

	//выбрать одну кредитную карту из списка с одним приоритетом
	private static Card selectCreditCardWithEqualsPriority(Collection<Card> cards)
	{
		MultiMap map = new MultiValueMap();
		//1. заполняем мапу <приоритет, множество кредитных карт одного приоритета>, где приоритет - число от 1 до 3 (1 - самые приоритетные карты)
		for (Card cardLink : cards)
		{
			int priority = getReceiverCreditCardPriority(cardLink);
			map.put(priority, cardLink);
		}

		//2. выбираем карту по приоритету
		for (int i = 1; i <= 3; i++)
		{
			//noinspection unchecked
			Collection<Card> cardSet = (Collection<Card>) map.get(i);
			//если карт с таким приоритетом нет, ищем карту с меньшим приоритетом
			if (CollectionUtils.isNotEmpty(cardSet))
				//возвращаем первую попавшуюся карту
				return cardSet.iterator().next();
		}
		return cards.iterator().next();
	}

	//возвращает приоритет карты для выбора одной из дебетовых карт с одинаковым приоритетом getReceiverCardPriority
	/*
	* Если в выбранной категории есть более одной карты для зачисления перевода, то выбираются:
	* •	основная «зарплатная» карта категории Gold;
	* •	основная «зарплатная» карта ниже категории Gold;
	* •	основная «личная» карта категории Premium (Infinite, Platinum,Gold);
	* •	основная «личная» карта категории Classic/Standard;
	* •	основная «личная» карта ниже категории Classic/Standard ( в т.ч. ПРО100);
	* •	дополнительная «зарплатная» карта;
	* •	дополнительная «личная» карта;
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
					return 1; //основная «зарплатная» карта категории Gold;
				else
					return 2; //основная «зарплатная» карта ниже категории Gold;
			}
			//личная
			else
			{
				if (CardLevel.isPremium(cardLevel))
					return 3; //основная «личная» карта категории Premium (Infinite, Platinum,Gold);
				else if (CardLevel.isClassicOrStandard(cardLevel))
					return 4; //основная «личная» карта категории Classic/Standard;
				else
					return 5; //основная «личная» карта ниже категории Classic/Standard ( в т.ч. ПРО100);
			}
		}
		else
		{
			if (isSalary)
				return 6; //дополнительная «зарплатная» карта;
			else
				return 7; //дополнительная «личная» карта;
		}
	}

	//возвращает приоритет карты для выбора одной из кредитных карт с одинаковым приоритетом getReceiverCardPriority
	/* Приоритеты для кредитных карт:
	* •	American Express;
	* •	Карты категории Gold;
	* •	все остальные карты
	*/
	private static int getReceiverCreditCardPriority(Card card)
	{
		CardLevel cardLevel = card.getCardLevel();
		if (CardLevel.isAmericanExpress(cardLevel))
			return 1; //American Express;
		else if (CardLevel.isGold(cardLevel))
			return 2; //Карты категории Gold;
		else
			return 3; //все остальные карты
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
