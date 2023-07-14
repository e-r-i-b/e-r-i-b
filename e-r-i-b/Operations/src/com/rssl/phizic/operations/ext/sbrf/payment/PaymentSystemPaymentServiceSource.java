package com.rssl.phizic.operations.ext.sbrf.payment;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShort;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.documents.payments.source.NewDocumentSource;
import com.rssl.phizic.business.documents.payments.validators.IsOwnDocumentValidator;
import com.rssl.phizic.business.documents.templates.validators.CheckInactiveBillingValidator;
import com.rssl.phizic.business.documents.templates.validators.OwnerTemplateValidator;
import com.rssl.phizic.gate.payments.systems.recipients.Field;

import java.util.HashMap;
import java.util.Map;

/**
 * @author krenev
 * @ created 07.05.2010
 * @ $Author$
 * @ $Revision$
 * �������� �������������� ���������� �� ������� ������ �����
 */
public class PaymentSystemPaymentServiceSource implements ServicePaymentInfoSource
{
	private static final IsOwnDocumentValidator validator = new IsOwnDocumentValidator();
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();
	private static final ServiceProviderService serviceProviderService = new ServiceProviderService();
	private ServiceProviderShort provider;
	private Map<String, Object> keyFields;

	public static PaymentSystemPaymentServiceSource createFromPayment(Long paymentId)
			throws BusinessException, DocumentException
	{
		if (paymentId == null)
		{
			throw new IllegalArgumentException("�� ����� ������������ ����������");
		}
		BusinessDocument document = businessDocumentService.findById(paymentId);
		if (document == null)
		{
			throw new BusinessException("�� ������ ������ � �������������� " + paymentId);
		}
		return new PaymentSystemPaymentServiceSource(document);
	}

	public static PaymentSystemPaymentServiceSource createFromTemplate(Long templateId)
			throws BusinessException, DocumentException, BusinessLogicException, DocumentLogicException
	{
		if (templateId == null)
		{
			throw new IllegalArgumentException("�� ����� ������������ �������");
		}

		return new PaymentSystemPaymentServiceSource(new NewDocumentSource(templateId, CreationType.internet, new OwnerTemplateValidator(), new CheckInactiveBillingValidator()).getDocument());
	}

	private PaymentSystemPaymentServiceSource(BusinessDocument document)
			throws BusinessException, DocumentException
	{
		validator.validate(document);// ��������� ��� ����������� ��������� ����������� �������� ������������
		if (!(document instanceof JurPayment))
		{
			throw new BusinessException("�� ������ ��� �������");
		}
		JurPayment jurPayment = (JurPayment) document;
		String pointCode = jurPayment.getReceiverPointCode();//
		provider = serviceProviderService.findShortProviderBySynchKey(pointCode);
		keyFields = new HashMap<String, Object>();
		for (Field field : jurPayment.getExtendedFields())
		{
			keyFields.put(field.getExternalId(), field.getValue());
		}
	}

	public Long getServiceId()
	{
		//TODO � ���������� ����� ������ ��� id ������
		return null;//provider.getPaymentService().getId();
	}

	public Long getProviderId()
	{
		return provider == null ? null : provider.getId();
	}

	public Map<String, Object> getKeyFields()
	{
		return keyFields;
	}
}
