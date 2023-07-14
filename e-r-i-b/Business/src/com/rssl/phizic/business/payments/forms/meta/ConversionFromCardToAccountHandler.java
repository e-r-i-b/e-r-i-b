package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.payments.ExchangeCurrencyTransferBase;
import com.rssl.phizic.business.payments.PaymentsConfig;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.business.resources.external.PaymentAbilityERL;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.utils.CurrencyUtils;

/**
 * ������� ��� �������� ���������/��������� �������������� �������� � ����� �� �����.
 * @author Jatsky
 * @ created 19.03.14
 * @ $Author$
 * @ $Revision$
 */

public class ConversionFromCardToAccountHandler extends BusinessDocumentHandlerBase
{
	protected static final String allowConversion = "��������! ���� ����������� ����� ���������� �� �������� �����. ����������� ����� ��������� �� ����� �� ������ ��������� �������� �� �����, ������� ������������ � ������� ���������� �����.";
	protected static final String notAllowConversion = "��� ���������� �������� �������� ������� � ����� �� �����/����, �������� � ������ �������, ����������, ������� ���������� �������� � ����� �� ���� �����/����, �������� � ��� �� ������ ��� � �����, � ����� ���������� �������� � ������/����� � ����� ������ �� �����/���� � ������ ������.";
	protected static final String forFullVer = " ���� � ��� ��� ������ ��� ��������������� ����� � ������ ������, �� �� ������ ��� ������� �� <a href = \"/PhizIC/private/deposits/products/list.do?form=AccountOpeningClaim\">������</a>";

	protected void showMessage(String fromResourceCurrencyCode, String toResourceCurrencyCode, StateMachineEvent stateMachineEvent) throws DocumentLogicException
	{
		if (CurrencyUtils.isSameCurrency(fromResourceCurrencyCode, toResourceCurrencyCode))
		{
			return;
		}
		if (ConfigFactory.getConfig(PaymentsConfig.class).isAllowConvertionFromCardToAccount())
		{
			stateMachineEvent.addMessage(allowConversion);
			return;
		}
		else
		{
			String message = notAllowConversion;
			if (LogThreadContext.getApplication().equals(Application.PhizIC))
			{
				message = message.concat(forFullVer);
			}
			throw new DocumentLogicException(message);
		}
	}

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		if (!(document instanceof ExchangeCurrencyTransferBase))
			throw new DocumentException("��������� ��������� ExchangeCurrencyTransferBase");

		ExchangeCurrencyTransferBase transfer = (ExchangeCurrencyTransferBase) document;

		//���� �������� ������ � ���-������, ������� � ������ ������� ��������
		CreationType creationType = transfer.getClientCreationChannel();
		if (creationType == CreationType.sms)
			return;

		PaymentAbilityERL fromResource = transfer.getChargeOffResourceLink();
		ExternalResourceLink toResource = transfer.getDestinationResourceLink();
		if (fromResource instanceof CardLink && toResource instanceof AccountLink)
		{
			showMessage(fromResource.getCurrency().getCode(), toResource.getCurrency().getCode(), stateMachineEvent);
		}
	}
}
