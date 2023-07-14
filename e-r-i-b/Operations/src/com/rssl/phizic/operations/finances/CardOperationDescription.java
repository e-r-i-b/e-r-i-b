package com.rssl.phizic.operations.finances;

import com.rssl.phizic.business.dictionaries.finances.CardOperationCategory;
import com.rssl.phizic.business.finances.CardOperation;
import com.rssl.phizic.business.finances.OperationType;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.bankroll.Card;
import org.apache.commons.lang.BooleanUtils;

import java.util.*;

/**
 * @author Erkin
 * @ created 17.08.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Расширенная информация по карточной операции
 * Включает атрибуты операции, которые не могут быть получены из бд простым путём:
 * - карта
 * - суммы в виде денег
 * - категории, доступные операции
 */
public class CardOperationDescription
{
	private CardOperation operation;
	private Card card;
	private Money cardAmount;
	private Money nationalAmount;
	private List<CardOperationCategory> availableCategories;

	public void setOperation(CardOperation operation)
	{
		this.operation = operation;
	}

	public Long getId()
	{
		return operation.getId();
	}

	public Date getDate()
	{
		return operation.getDate().getTime();
	}

	public Calendar getDateAsCalendar()
	{
		return operation.getDate();
	}

	public String getTitle()
	{
		return operation.getDescription();
	}

	public Long getCategoryId()
	{
		CardOperationCategory category = operation.getCategory();
		return (category != null) ? category.getId() : null;
	}

	public CardOperationCategory getCategory()
	{
		return operation.getCategory();
	}

	public Card getCard()
	{
		return card;
	}

	public void setCard(Card card)
	{
		this.card = card;
	}

	public Money getCardAmount()
	{
		return cardAmount;
	}

	public void setCardAmount(Money cardAmount)
	{
		this.cardAmount = cardAmount;
	}

	public Money getNationalAmount()
	{
		return nationalAmount;
	}

	public void setNationalAmount(Money nationalAmount)
	{
		this.nationalAmount = nationalAmount;
	}

	public List<CardOperationCategory> getAvailableCategories()
	{
		return Collections.unmodifiableList(availableCategories);
	}

	public void setAvailableCategories(Collection<CardOperationCategory> availableCategories)
	{
		this.availableCategories = new ArrayList<CardOperationCategory>(availableCategories);
	}

	public boolean isHidden()
	{
		return BooleanUtils.isTrue(operation.getHidden());
	}

	public String getCountry()
	{
		if (operation.getClientCountry()!=null)
			return operation.getClientCountry().getIso3();
		return null;
	}

	/**
	 * @return id записи в таблице BUSINESS_DOCUMENTS
	 */
	public Long getBusinessDocumentId()
	{
		return operation.getBusinessDocumentId();
	}

	public OperationType getOperationType()
	{
		return operation.getOperationType();
	}
}
