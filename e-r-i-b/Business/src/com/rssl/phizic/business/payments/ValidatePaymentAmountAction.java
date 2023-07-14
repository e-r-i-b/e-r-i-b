package com.rssl.phizic.business.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.documents.BusinessDocumentBase;
import com.rssl.phizic.business.extendedattributes.ExtendedAttribute;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.business.resources.external.PaymentAbilityERL;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author mihaylov
 * @ created 26.11.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * �������� �� ���������� ������� �� �����/����� ��������
 */
public class ValidatePaymentAmountAction extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof AbstractPaymentDocument))
			throw new DocumentException("Action ������������ ������ ������ � ���������");
		AbstractPaymentDocument abstractPaymentDocument = (AbstractPaymentDocument) document;

		ExtendedAttribute offlineAttribute = abstractPaymentDocument.getAttribute(BusinessDocumentBase.OFFLINE_DELAYED_ATTRIBUTE_NAME);
		if (offlineAttribute != null && (Boolean) offlineAttribute.getValue())
			return;

		PaymentAbilityERL link = abstractPaymentDocument.getChargeOffResourceLink();
		if (link == null)
		{
			log.error("�� ������ ����-��-�������� �������� ��� ������� � id:" + abstractPaymentDocument.getId());
			throw new DocumentLogicException("�� �� ������ ��������� ������ ��������, ��� ��� ���������� �� ����� �� ������� � �������.");
		}

		Money amount = abstractPaymentDocument.getChargeOffAmount();
		if (amount == null) // ���� �� ������� ����� ������� - ������ �� ����������
			return;

		if (link instanceof CardLink && !amount.getCurrency().equals(link.getCurrency()))
			return; //��� ������ � ����� ���� ��������� �� �������� �� ����� 

		try
		{
			if (MoneyUtil.compare(amount, link.getRest(), link.getOffice(), abstractPaymentDocument.getTarifPlanCodeType()) > 0)
				throw makeValidationFail(formatTooMuchAmountErrorMessage(link));
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new DocumentLogicException(e);
		}
	}

	private String formatTooMuchAmountErrorMessage(ExternalResourceLink link)
	{
		switch(link.getResourceType())
		{
			case ACCOUNT:
				return "������� �� ����� ����� ���������� �������� �� ����� ���� ������ ������������.";
			case CARD:
				return "������� �� ����� ����� ���������� �������� �� ����� ���� ������ ������������.";
			default:
				return "������� ����� ���������� �������� �� ����� ���� ������ ������������.";
		}
	}
}
