package com.rssl.phizic.web.common.client.cards.delivery;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author akrenev
 * @ created 25.06.2014
 * @ $Author$
 * @ $Revision$
 *
 * форма сохранения состояния закрытия сообщения о возможности подписаться
 */

public class CloseEmailDeliveryMessageForm extends ActionFormBase
{
	private Long cardId;

	/**
	 * @return идентификатор карты для закрытия сообщения
	 */
	public Long getCardId()
	{
		return cardId;
	}

	/**
	 * задать идентификатор карты для закрытия сообщения
	 * @param cardId идентификатор
	 */
	@SuppressWarnings("UnusedDeclaration") // вызывается в рамках обработки запроса
	public void setCardId(Long cardId)
	{
		this.cardId = cardId;
	}
}
