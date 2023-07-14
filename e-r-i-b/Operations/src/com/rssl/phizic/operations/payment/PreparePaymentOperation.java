package com.rssl.phizic.operations.payment;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.AbstractAccountsTransfer;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.source.DocumentFieldValuesSource;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.business.documents.payments.source.NewDocumentSource;
import com.rssl.phizic.business.documents.payments.validators.IsOwnDocumentValidator;
import com.rssl.phizic.business.documents.templates.validators.CheckInactiveBillingValidator;
import com.rssl.phizic.business.documents.templates.validators.OwnerTemplateValidator;
import com.rssl.phizic.business.resources.external.PaymentAbilityERL;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ext.sbrf.payment.PaymentSystemPaymentServiceSource;
import com.rssl.phizic.operations.ext.sbrf.payment.ServicePaymentInfoSource;
import com.rssl.phizic.operations.forms.processing.PersonalRecipientFieldValuesSource;
import com.rssl.phizic.utils.StringHelper;

import java.util.Calendar;
import java.util.HashMap;

import static com.rssl.common.forms.FormConstants.SERVICE_PAYMENT_FORM;

/**
 * @author Erkin
 * @ created 05.08.2010
 * @ $Author$
 * @ $Revision$
 */
public class PreparePaymentOperation extends OperationBase
{
	private PaymentContext paymentContext = null;

	private PersonData personData;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * �������������
	 * @param params - ��������� �������
	 */
	public void initialize(PaymentParameters params) throws BusinessException, BusinessLogicException
	{
		if (params == null)
			throw new NullPointerException("Argument 'params' cannot be null");

		this.personData = PersonContext.getPersonDataProvider().getPersonData();
		this.paymentContext = createPaymentContext(params);
	}

	public PaymentContext getPaymentContext()
	{
		return paymentContext;
	}

	///////////////////////////////////////////////////////////////////////////

	protected PersonData getPersonData()
	{
		if (personData == null)
			throw new IllegalStateException("Operation is not initialized yet");
		return personData;
	}

	/**
	 * ������ �������� ������� ��:
	 * - ������ �� ��������� ���������
	 * - ������ �������
	 * - ������ �������
	 * - ����� �����
	 * @param params - ��������� �������
	 * @return �������� �������
	 */
	private PaymentContext createPaymentContext(PaymentParameters params) throws BusinessException, BusinessLogicException
	{
		try
		{
			String formName = params.getPaymentFormName();
			Long paymentId = params.getPaymentId();
			Long templateId = params.getPaymentTemplateId();
			Long providerId = params.getProviderId();
			boolean personal = params.isPersonalPayment();

			// 1.
			PaymentContext context = new PaymentContext();

			// 2. ����������� �������� � ����������� �� ������� ��������
			// 2.1 ������ ID �������
			if (paymentId != null && paymentId > 0)
				loadPaymentContext(context, paymentId);

			// 2.2 ������ ������ �������
			if (templateId != null && templateId > 0)
				loadTemplatePaymentContext(context, templateId);

			// 2.4 ������ ���������
			if (providerId != null)
				loadProviderPaymentContext(context, providerId);

			// 2.5 ������� ����� �������
			if (!StringHelper.isEmpty(formName))
				loadPaymentFormContext(context, formName);

			// 2.6 �������� "������������ �����"
			if (personal)
				loadPersonalPaymentContext(context);

			context.setCopy(params.isCopy());
			if (!StringHelper.isEmpty(params.getFromResource()))
				context.setFromResource(params.getFromResource());
			context.putFields(new HashMap(params.getPaymentFields()));
			if(context.getCreationSource() == null)
				context.setCreationSource(CreationSourceType.ordinary);

			return context;
		}
		catch (DocumentException ex)
		{
			throw new BusinessException(ex);
		}
		catch (DocumentLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}

	/**
	 * ��������� �������� ������� ������� ��� �������������� �������
	 * @param context - ��������
	 * @param paymentId - ID �������
	 */
	private void loadPaymentContext(PaymentContext context, Long paymentId) throws BusinessException, DocumentException, BusinessLogicException, DocumentLogicException
	{
		if (!paymentId.equals(context.getPaymentId()))
		{
			// ������ ������ �����
			context.clear();

			DocumentSource documentSource = new ExistingSource(
					paymentId, new IsOwnDocumentValidator());
			Metadata metadata = documentSource.getMetadata();
			BusinessDocument document = documentSource.getDocument();
			context.setFormName(document.getFormName());
			context.setPaymentId(paymentId);
			context.putFields(new DocumentFieldValuesSource(metadata, document).getAllValues());

			if (document instanceof AbstractAccountsTransfer)
			{
				//���������� �������� �������� ��������� �� ���������
				AbstractAccountsTransfer transfer = (AbstractAccountsTransfer) document;
				PaymentAbilityERL chargeOffResourceLink = transfer.getChargeOffResourceLink();
				String fromResource= null;
				if (chargeOffResourceLink != null)
					fromResource = chargeOffResourceLink.getCode();
				context.setFromResource(fromResource);
			}

			if (FormConstants.SERVICE_PAYMENT_FORM.equals(document.getFormName())) {
				ServicePaymentInfoSource paymentInfoSource =
						PaymentSystemPaymentServiceSource.createFromPayment(paymentId);
				context.setProviderId(paymentInfoSource.getProviderId());
			}
		}
	}

	/**
	 * ��������� �������� ������� ������� ������� ��� �������� ������ �������
	 * @param context - ��������
	 * @param templateId - ID ������� �������
	 */
	private void loadTemplatePaymentContext(PaymentContext context, Long templateId) throws BusinessException, BusinessLogicException, DocumentException, DocumentLogicException
	{
		context.clear();

		DocumentSource documentSource = new NewDocumentSource(templateId, CreationType.internet, new OwnerTemplateValidator(), new CheckInactiveBillingValidator());
		Metadata metadata = documentSource.getMetadata();
		BusinessDocument document = documentSource.getDocument();
		// ���� ��������� ������������� �������, ����� ���� �������� ������� ���������� � ����� ��������� �������
		// ��� ���� documentDate � ����� ��������� ���� ������������ �� ����� ������� ���������
		document.setDocumentDate(Calendar.getInstance());

		context.setFormName(document.getFormName());
		context.setTemplateId(templateId);
		context.setCreationSource(CreationSourceType.template);
		context.putFields(new DocumentFieldValuesSource(metadata, document).getAllValues());

		if (SERVICE_PAYMENT_FORM.equals(document.getFormName())) {
			ServicePaymentInfoSource paymentInfoSource =
					PaymentSystemPaymentServiceSource.createFromTemplate(templateId);
			context.setProviderId(paymentInfoSource.getProviderId());
			context.setServiceId(paymentInfoSource.getServiceId());
		}
	}

	private void loadProviderPaymentContext(PaymentContext context, Long providerId) throws BusinessException, BusinessLogicException
	{
		if (context.getProviderId() != null && !providerId.equals(context.getProviderId()))
			// ��������� � ��������� ���������� �� ����������
			context.clear();
		context.setProviderId(providerId);
		context.setFormName(SERVICE_PAYMENT_FORM);
	}

	private void loadPaymentFormContext(PaymentContext context, String formName)
	{
		// ������� �� ����� ������ ����������
		if (!FormConstants.SERVICE_PAYMENT_FORM.equals(formName)) {
			context.clear();
			context.setFormName(formName);
		}
	}

	private void loadPersonalPaymentContext(PaymentContext context) throws BusinessLogicException, BusinessException
	{
		Long providerId = context.getProviderId();
		if (providerId == null)
			throw new BusinessLogicException("�� ������ ��������� �����");

		PersonalRecipientFieldValuesSource recipientFieldValuesSource
				= new PersonalRecipientFieldValuesSource(getOperationFactory(), providerId);
		context.putFields(recipientFieldValuesSource.getAllValues());

		context.setPersonal(true);
	}
}
