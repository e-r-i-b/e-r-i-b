package com.rssl.phizic.web.common.client.cards.delivery;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author akrenev
 * @ created 25.06.2014
 * @ $Author$
 * @ $Revision$
 *
 * ����� ���������� ��������� �������� ��������� � ����������� �����������
 */

public class CloseEmailDeliveryMessageForm extends ActionFormBase
{
	private Long cardId;

	/**
	 * @return ������������� ����� ��� �������� ���������
	 */
	public Long getCardId()
	{
		return cardId;
	}

	/**
	 * ������ ������������� ����� ��� �������� ���������
	 * @param cardId �������������
	 */
	@SuppressWarnings("UnusedDeclaration") // ���������� � ������ ��������� �������
	public void setCardId(Long cardId)
	{
		this.cardId = cardId;
	}
}
