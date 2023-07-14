package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.*;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.payments.TitledLogicException;
import com.rssl.phizic.business.resources.external.AbstractStoredResource;
import com.rssl.phizic.business.resources.external.PaymentAbilityERL;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.MockHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.manager.ext.sbrf.ESBHelper;

/**
 * ������� ������� �� �������� ����������� ���������� ��������. ����������� �� ����� ���������(� ������ ��������):
 * 1) ����� ��������� �� ������ ��������� ������� �� ��������� ��������.
 * 2) ����� ��������� �� ������ ��������� ����������� ���������� ����� ��������(���� ������ ��� ��������� ��������)
 * @author Rtischeva
 * @created 16.09.13
 * @ $Author$
 * @ $Revision$
 */
public class DocumentSumValidationHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (ApplicationUtil.isErmbSms())
			return;

		AbstractPaymentDocument doc = (AbstractPaymentDocument) document;

		if (doc instanceof AbstractLongOfferDocument)
		{
			//���� ���������� ���������/���������� � ���� ����� ������� �� �����
			AbstractLongOfferDocument longOfferDocument = (AbstractLongOfferDocument) doc;
			if (longOfferDocument.isLongOffer() && !longOfferDocument.needInputAmount())
				return;
			//���� �������� �� ����������(���������� � �� "�����������" - ���������� ���������� ����� ����) ��������� �� ����� (BUG037512)
			if (ESBHelper.isAutoSubscriptionPayment(longOfferDocument))
				return;
		}

		if (doc instanceof AccountOpeningClaim)
		{
			AccountOpeningClaim accountOpeningClaim = (AccountOpeningClaim) document;
			if (!accountOpeningClaim.isNeedInitialFee())
				return;
		}

		Money amount = doc.getChargeOffAmount();
		//���� ���������� ����� �������� (��� ��������� ������), ��� ��������, ��� ������� ������ ����� � ������ ���������� � ������������� ��������� ������������� ������� ������
		if (amount == null)
		{
			if(doc.getDestinationAmount() != null && !compareCurrency(doc))
				return;

			amount = doc.getDestinationAmount();
		}

		Money commission = doc.getCommission();
		if (commission != null)
		{
			amount = amount.add(commission);
		}

		try
		{
			if (StringHelper.isEmpty(doc.getChargeOffAccount()))
				throw new DocumentException("� �������� ��������� �� ������ �������� ��������. DOC_ID=" + doc.getId());

			PaymentAbilityERL chargeOffResourceLink = doc.getChargeOffResourceLink();
			if (chargeOffResourceLink == null)
				throw new DocumentException("�� ������ ����-��-�������� �������� ���� " + doc.getChargeOffResourceType());

			if (chargeOffResourceLink.getValue() instanceof AbstractStoredResource)
				return;

			if (MockHelper.isMockObject(chargeOffResourceLink.getValue()))
				throw new DocumentLogicException("�������� �������� ����������. ��������� ������� �����.");

			checkAmounts(document, amount, chargeOffResourceLink.getRest(), chargeOffResourceLink.getMaxSumWrite(), chargeOffResourceLink.getResourceType());
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

	private boolean compareCurrency(AbstractPaymentDocument doc) throws DocumentException
	{
		try
		{
			return doc.getChargeOffCurrency().compare(doc.getDestinationAmount().getCurrency());
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}
	}

	/**
	 * ��������� �����:
	 * 1) ����� ��������� �� ������ ��������� ������� �� ��������� ��������.
	 * 2) ����� ��������� �� ������ ��������� ����������� ���������� ����� ��������(���� ������)
	 * @param documentSum ����� ���������
	 * @param balance ������� �� ��������� ��������
	 * @param maxSumWrite ������������ ����� ��������(����� �����������)
	 * @param resourceType ��� ��������� ��������
	 */
	private void checkAmounts(StateObject document, Money documentSum, Money balance, Money maxSumWrite, ResourceType resourceType) throws BusinessLogicException, BusinessException, DocumentException
	{
		if (documentSum == null)
		{
			throw new IllegalArgumentException("�� ������ ����� ���������");
		}
		Currency documentCurrency = documentSum.getCurrency();
		//1)��������� ������� �� ��������� ��������
		if (balance == null)
		{
			throw new IllegalArgumentException("�� ����� ������� ��������� �������� ��� ��������");
		}
		Currency balanceCurrency = balance.getCurrency();
		if (!documentCurrency.compare(balanceCurrency))
		{
			throw new BusinessException("�� ��������� ������ ����� ���������(" + documentCurrency.getCode() + ") � ������� �� "+ toStringResourceType(resourceType, false) +"(" + balanceCurrency.getCode() + ")");
		}
		if (documentSum.compareTo(balance) > 0)
		{
			if (DocumentHelper.postConfirmCommissionSupport((BusinessDocument) document) && ((AbstractPaymentDocument) document).getCommission() != null)
				throw new TitledLogicException("�� ����� ������� ������������ ������� ��� �������� � ������ �������� � ������������. ���������� ��������� ������, �������� ����� ��������." +
										"<a href='http://www.sberbank.ru/common/img/uploaded/files/pdf/person/bank_cards/Perevody__Tarify.pdf' target='_blank'>���������� � ���������� ���������.</a>", "������������ ������� �� ����� ��� ��������");
			String message = getNotEnoughMoneyMessage(document, resourceType);
			throw new BusinessLogicException(message);
		}
		//2)��������� ������������ ����� ��������
		if (maxSumWrite == null)
		{
			return;//����� �� ������. ��������� �������� ���� �����
		}
		Currency maxSumWriteCurrency = maxSumWrite.getCurrency();
		if (!documentCurrency.compare(maxSumWriteCurrency))
		{
			throw new BusinessException("�� ��������� ������ ����� ���������(" + documentCurrency.getCode() + ") � ������������ ����� ��������(" + maxSumWriteCurrency.getCode() + ")");
		}
		if (documentSum.compareTo(maxSumWrite) > 0)
		{
			throw new BusinessLogicException("������� �� "+ toStringResourceType(resourceType, false) +" ����� ���������� �������� �� ����� ���� ������ ������������.");
		}
	}

	protected String getNotEnoughMoneyMessage(StateObject document, ResourceType resourceType) throws DocumentException
	{
		AbstractPaymentDocument doc = (AbstractPaymentDocument) document;
		boolean isDepo = document instanceof RurPayment && ((RurPayment) document).isDepoPayment();
		boolean isAirlineReservation = DocumentHelper.isAirlineReservationPayment(doc);
		boolean isAeroexpressReservation = DocumentHelper.isAeroexpressReservationPayment(doc);
		boolean isInvoice = DocumentHelper.isInvoicePayment(doc);
		if(isDepo)
			return "�� ����� ����� ������������ ������� ��� ������ �������������. ����������, �������� ������ �����.";
		if(isAirlineReservation)
			return "����� �������� ��������� ������� �� �����. ����������, �������� ������ ����� ��� ������ �����.";
		if(isAeroexpressReservation){
			return "����� �������� ��������� ������� �� ����� �����. ����������, �������� ������ ����� ��� ������� �������.";
		}
		if(isInvoice)
		{
			return "����� �������� ��������� ������� ������� �� ����� �����. ����������, ��������� ����� ��������.";
		}
		return toStringResourceType(resourceType, true);
	}

	private String toStringResourceType(ResourceType resourceType, boolean useTreatment)
	{
		if (useTreatment)
		{
			String message = new String("�� ����� �����, ����������� ������� ������� �� ");
			switch (resourceType)
			{
				case ACCOUNT:
				{
					message += "����� �����. ����������, ������� ������ �����.";
					break;
				}
				case IM_ACCOUNT:
				{
					return "�����/����� �������� ��������� ������� �� ����� ��������. ����������, ������� ������ �����/����� ��������.";
				}
				default:
				{
					message += "����� �����. ����������, ������� ������ �����.";
					break;
				}
			}
			return message;
		}
		else
		{
			return resourceType == ResourceType.CARD ? "�����": "�����";
		}
	}
}
