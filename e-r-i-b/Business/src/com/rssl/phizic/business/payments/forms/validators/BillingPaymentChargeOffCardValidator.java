package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ActiveNotVirtualCardsFilter;
import com.rssl.phizic.business.resources.external.ActiveNotVirtualNotCreditCardFilter;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.utils.StringHelper;

import java.util.Map;

/**
 * @author krenev
 * @ created 27.05.2013
 * @ $Author$
 * @ $Revision$
 * ��������� ����� �������� ����������� ��������.
 */
public class BillingPaymentChargeOffCardValidator extends MultiFieldsValidatorBase
{
	public static final String FIELD_FROM_RESOURCE = "fromResource";
	public static final String FIELD_RECIPIENT = "recipient";
	private static final ServiceProviderService providerService = new ServiceProviderService();
	private static final ActiveNotVirtualCardsFilter ACTIVE_NOT_VIRTUAL_CARDS_FILTER = new ActiveNotVirtualCardsFilter();
	private static final ActiveNotVirtualNotCreditCardFilter ACTIVE_NOT_VIRTUAL_NOT_CREDIT_CARD_FILTER = new ActiveNotVirtualNotCreditCardFilter();

	public boolean validate(Map values) throws TemporalDocumentException
	{
		Object fromResource = retrieveFieldValue(FIELD_FROM_RESOURCE, values);
		if (fromResource == null)
		{
			return true; // ��������� �������� ��� - ��������� ������.
		}
		if (!(fromResource instanceof CardLink))
		{
			return true;//��������� ������ �����
		}
		Card card = ((CardLink) fromResource).getCard();
		String receiverId = StringHelper.getEmptyIfNull(retrieveFieldValue(FIELD_RECIPIENT, values));
		if (StringHelper.isEmpty(receiverId))
		{
			//���� ���������� ��� - ��������� �������� ������������� �����
			return ACTIVE_NOT_VIRTUAL_CARDS_FILTER.accept(card);
		}

		try
		{
			Boolean isCreditCardSupported = providerService.findCreditCardSupportedById(Long.parseLong(receiverId));
			if (isCreditCardSupported == null)
			{
				//���� ���������� ��� - ��������� ������ �������� ������������� �����
				return ACTIVE_NOT_VIRTUAL_CARDS_FILTER.accept(card);
			}
			//� � ����������� ������� �� ������� ��������� �� ������ � ��������.
			return isCreditCardSupported ? ACTIVE_NOT_VIRTUAL_CARDS_FILTER.accept(card) : ACTIVE_NOT_VIRTUAL_NOT_CREDIT_CARD_FILTER.accept(card);
		}
		catch (BusinessException e)
		{
			throw new TemporalDocumentException(e);
		}
	}
}
