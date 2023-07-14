package com.rssl.phizic.business.resources.external.guest;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.gate.bankroll.Card;

/**
 * @author Balovtsev
 * @since 06.05.2015.
 */
public class GuestCardLink extends CardLink
{
	private Card card;

	@Override
	public Long getId()
	{
		throw new UnsupportedOperationException("В гостевой сессии не поддерживается");
	}

	@Override
	public void setId(Long id)
	{
		throw new UnsupportedOperationException("В гостевой сессии не поддерживается");
	}

	public void setCard(Card card)
	{
		this.card = card;
	}

	@Override
	public Card getCard()
	{
		return card;
	}

	@Override
	public void reset() throws BusinessLogicException, BusinessException
	{
	}

	@Override
	public Card toLinkedObjectInDBView()
	{
		return null;
	}
}
