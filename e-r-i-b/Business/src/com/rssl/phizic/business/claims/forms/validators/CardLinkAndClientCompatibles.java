package com.rssl.phizic.business.claims.forms.validators;

import com.rssl.phizic.business.payments.forms.validators.AccountAndCardValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;

import java.util.Map;

/**
 * @author Gulov
 * @ created 15.07.2010
 * @ $Authors$
 * @ $Revision$
 */
public class CardLinkAndClientCompatibles extends AccountAndCardValidatorBase
{
	private final static String FIELD_CARD_ID = "cardId";

	public CardLinkAndClientCompatibles()
	{
		setMessage("����� �� ����������� �������");
	}

	/**
	 * ��������� �������� ����� �� ���������� ���� ����� �� ���� ����������� ������ ���� THREAD SAFE!!!!!!!!!
	 *
	 * @param values �������� ��� ��������. Key - ��� ���� (� �����), Value - �������� ����.
	 */
	public boolean validate(Map values) throws TemporalDocumentException
	{
		return getCardLink(FIELD_CARD_ID, values) != null;
	}
}
