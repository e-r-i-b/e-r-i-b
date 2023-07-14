package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.utils.MockHelper;
import com.rssl.phizic.utils.MaskUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * ��������� ���������, ����� ���� ��������� ����������� ������� ���� ������, ���
 * ���� ��������� �������� �����/�����
 * @author niculichev
 * @ created 29.10.2010
 * @ $Author$
 * @ $Revision$
 */
public class CloseDateResourceValidator extends MultiFieldsValidatorBase
{
	private static final String FROM_RESOURCE = "resource";
	private static final String DOCUMENT_DATE = "longOfferEndDate";

	public boolean validate(Map values) throws TemporalDocumentException
	{
		Object resource = retrieveFieldValue(FROM_RESOURCE, values);
		Date   endDate  = (Date) retrieveFieldValue(DOCUMENT_DATE, values);

		if(resource instanceof AccountLink)
		{
			AccountLink accountLink = (AccountLink) resource;
			Account     account     = accountLink.getAccount();

			if(MockHelper.isMockObject(account))
			{
				throw new TemporalDocumentException("������ ��� ��������� ���������� � ����� �"+accountLink.getNumber());
			}

			Calendar calendar = account.getCloseDate();
			//���� null ������ ���� ����������
			return calendar == null || account.getDemand() || endDate.compareTo(calendar.getTime()) <= 0;
		}

		if(resource instanceof CardLink)
		{
			Card card = ((CardLink)resource).getCard();
			if(MockHelper.isMockObject(card))
				throw new TemporalDocumentException("������ ��� ��������� ���������� � ����� �"+ MaskUtil.getCutCardNumber(card.getNumber()));

			return endDate.compareTo(card.getExpireDate().getTime())<=0;
		}

		return false;
	}
}

