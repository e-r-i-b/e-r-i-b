package com.rssl.phizic.business.payments.forms.meta.receivers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.FormException;
import com.rssl.common.forms.PaymentFieldKeys;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.types.SubType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.documents.AutoSubscriptionTypeHelper;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.payments.AutoSubType;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.gate.longoffer.autopayment.AlwaysAutoPayScheme;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author Gainanov
 * @ created 31.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class PaymentSystemPaymentMetadataLoader extends ExtendedMetadataLoaderBase
{
	public static final String RECIPIENT_ID_KEY = PaymentFieldKeys.PROVIDER_KEY;//��������� ������������� ����������(��������� �����)
	public static final String RECIPIENT_EXTERNAL_KEY = PaymentFieldKeys.PROVIDER_EXTERNAL_KEY;//������������� �������� ����������
	private static final ServiceProviderService providerService = new ServiceProviderService();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final String INITIAL_LONG_OFFER = "INITIAL_LONG_OFFER";
	private static final String isAutoPaymentSupported = "isAutoPaymentSupported";
	private static final String isAutoPaymentSupportedInApi = "isAutoPaymentSupportedInApi";
	private static final String isAutoPaymentSupportedInATM = "isAutoPaymentSupportedInATM";

	public Metadata load(Metadata metadata, FieldValuesSource fieldSource) throws BusinessException
	{
		String receiverId = fieldSource.getValue(RECIPIENT_ID_KEY);
		if (!StringHelper.isEmpty(receiverId))
		{
			//���� ������� ������������ ����������� ����������� ������ ���������� �� ����.
			try
			{
				//�������� ���������� �����.
				BillingServiceProviderBase provider = (BillingServiceProviderBase) providerService.findById(Long.parseLong(receiverId));
				return loadForInternalProvider(metadata, provider);
			}
			catch (BusinessException e)
			{
				throw new BusinessException(e);
			}
		}
		//���� ������� ������� ������������ ���������� ������ ���������� �� ����.
		String receiverAccount = fieldSource.getValue(PaymentFieldKeys.RECEIVER_ACCOUNT);
		if (StringHelper.isEmpty(receiverAccount))
		{
			throw new BusinessException("�� ����� ���� �������� ����������");
		}
		String receiverBIC = fieldSource.getValue(PaymentFieldKeys.RECEIVER_BIC);
		if (StringHelper.isEmpty(receiverBIC))
		{
			throw new BusinessException("�� ����� ��� ����� �������� ����������");
		}

		String receiverINN = fieldSource.getValue(PaymentFieldKeys.RECEIVER_INN);
		if (StringHelper.isEmpty(receiverBIC))
		{
			throw new BusinessException("�� ����� ��� ����� �������� ����������");
		}

		String externalProviderKey = fieldSource.getValue(RECIPIENT_EXTERNAL_KEY);
		String billingCode = fieldSource.getValue(PaymentFieldKeys.BILLING_CODE);
		String receiverName = fieldSource.getValue(PaymentFieldKeys.RECEIVER_NAME);
		return loadForExternalProvider(metadata, externalProviderKey, receiverINN, receiverAccount, receiverBIC, receiverName, billingCode);
	}

	/**
	 * ��������� ����������� ���������� ��� �������� ����������
	 * @param metadata ����������� ����������
	 * @param externalProviderKey ������� ������������ ����������
	 * @param receiverINN ��� �������� ����������
	 * @param receiverAccount ���� �������� ����������
	 * @param receiverBIC ��� ����� �������� ����������
	 * @param receiverName ������������ �������� ����������
	 * @param bilingCode ��� ��������
	 * @return ���������� ���������
	 */
	private Metadata loadForExternalProvider(Metadata metadata, String externalProviderKey, String receiverINN, String receiverAccount, String receiverBIC, String receiverName, String bilingCode) throws BusinessException
	{
		CompositeFieldsBuilder fieldsBuilder = new CompositeFieldsBuilder();
		fieldsBuilder.addBuilder(new ReceiverInfoFieldsBuilder(metadata, externalProviderKey, receiverINN, receiverAccount, receiverBIC, receiverName, bilingCode, null));

		Map<String, Element> dictionaries = new HashMap<String, Element>();
		Element dictionary = fieldsBuilder.buildFieldsDictionary();

		if (dictionary != null)
			dictionaries.put("extendedFields.xml", dictionary);
		else
			log.warn("������ " + metadata.getName() + ", �� ������ extendedFields.xml");

		return load(metadata, fieldsBuilder.buildFields(), dictionaries, null);
	}

	/**
	 * ��������� ����������� ���������� ��� ������ ������� � ����� �����������(����������� ��) ����������
	 * @param metadata ����������� ����������
	 * @param provider ������������ ����������
	 * @return ���������� ���������
	 */
	private Metadata loadForInternalProvider(Metadata metadata, BillingServiceProviderBase provider) throws BusinessException
	{
		if (provider == null)
		{
			throw new ResourceNotFoundBusinessException("�� ����� ����������", BillingServiceProviderBase.class);
		}
		CompositeFieldsBuilder fieldsBuilder = new CompositeFieldsBuilder();
		fieldsBuilder.addBuilder(new GateFieldsBuilder(provider));
		fieldsBuilder.addBuilder(new ReceiverInfoFieldsBuilder(metadata, provider));

		Map<String, Element> dictionaries = new HashMap<String, Element>();
		Element dictionary = fieldsBuilder.buildFieldsDictionary();

		if (dictionary != null)
			dictionaries.put("extendedFields.xml", dictionary);
		else
			log.warn("������ " + metadata.getName() + ", �� ������ extendedFields.xml");

		return load(metadata, fieldsBuilder.buildFields(), dictionaries, provider.getNameService());
	}

	public Metadata load(Metadata metadata, BusinessDocument document) throws BusinessException
	{
		try
		{
			JurPayment payment = (JurPayment) document;
			List<com.rssl.phizic.gate.payments.systems.recipients.Field> extendedFields = payment.getExtendedFields();

			// ���� ���� ��������, ��������� ����� ����
			if(extendedFields == null)
			{
				extendedFields = new ArrayList<com.rssl.phizic.gate.payments.systems.recipients.Field>();
				payment.setExtendedFields(extendedFields);
			}

			// �� ������������ ��������� ����������� �� ����,
			// �.�. ��� ������ ��� ������ ����� ��� �������������� ������� � PrepareEditDocumentHandler,
			// ������ �������������� ��� �������������������.
			/*try
			{
				if (extendedFields == null)
					DocumentConverter.conver(payment);
			}
			catch (BusinessException e)
			{
				throw new DocumentException(e);
			}*/

			// ��������� �� ������ � ������� INITIAL_LONG_OFFER
			boolean isInitialLongOffer = INITIAL_LONG_OFFER.equals(document.getState().getCode());

			CompositeFieldsBuilder fieldsBuilder = new CompositeFieldsBuilder();
			// � ������ ���� ���������� ��������� ������ �������� ��� ����� � ����������
			fieldsBuilder.addBuilder(new GateFieldsBuilder(payment.getExtendedFields(), isInitialLongOffer ? SubType.STATIC : SubType.DINAMIC, payment.getBillingCode()));
			fieldsBuilder.addBuilder(new ReceiverInfoFieldsBuilder(metadata, payment));

			Map<String, Element> dictionaries = new HashMap<String, Element>();
			Element dictionary = fieldsBuilder.buildFieldsDictionary();

			if (dictionary == null)
				log.warn("������ " + metadata.getName() + ", �� ������ extendedFields.xml");
			else
				dictionaries.put("extendedFields.xml", dictionary);

			if(isInitialLongOffer)
			{
				updateDictionaries(payment, metadata, dictionaries);
			}

			return load(metadata,  fieldsBuilder.buildFields(), dictionaries, payment.getService().getName());
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
	}

	public Metadata load(Metadata metadata, TemplateDocument template) throws BusinessException, BusinessLogicException
	{
		CompositeFieldsBuilder fieldsBuilder = new CompositeFieldsBuilder();
		fieldsBuilder.addBuilder(new TemplateFieldBuilder(metadata, template));
		fieldsBuilder.addBuilder(new TemplateGateFieldsBuilder(template));

		Map<String, Element> dictionaries = new HashMap<String, Element>();
		Element dictionary = fieldsBuilder.buildFieldsDictionary();

		if (dictionary == null)
		{
			log.warn("������ " + metadata.getName() + ", �� ������ extendedFields.xml");
		}
		dictionaries.put("extendedFields.xml", dictionary);

		return load(metadata,  fieldsBuilder.buildFields(), dictionaries, template.getService().getName());
	}

	public Metadata load(Metadata metadata, BusinessDocument document, TemplateDocument template) throws BusinessException, BusinessLogicException
	{
 		CompositeFieldsBuilder fieldsBuilder = new CompositeFieldsBuilder();
		JurPayment payment = (JurPayment) document;

		// ��������� �� ������ � ������� INITIAL_LONG_OFFER
		boolean isInitialLongOffer = document != null && INITIAL_LONG_OFFER.equals(document.getState().getCode());

		fieldsBuilder.addBuilder(new PaymentSystemTransferTemplateFieldBuilder(metadata, payment, template));
		fieldsBuilder.addBuilder(new TemplateGateFieldsBuilder(payment, template, isInitialLongOffer));

		Map<String, Element> dictionaries = new HashMap<String, Element>();
		Element dictionary = fieldsBuilder.buildFieldsDictionary();

		if (dictionary == null)
		{
			log.warn("������ " + metadata.getName() + ", �� ������ extendedFields.xml");
		}
		dictionaries.put("extendedFields.xml", dictionary);

		if(isInitialLongOffer)
		{
			updateDictionaries(payment, metadata, dictionaries);
		}

		return load(metadata,  fieldsBuilder.buildFields(), dictionaries, template.getService().getName());
	}

	public String getExtendedMetadataKey(FieldValuesSource fieldSource) throws FormException
	{
		return null;//��������� ���� ������� ������.
	}

	private void updateDictionaries(JurPayment payment, Metadata metadata, Map<String, Element> dictionaries) throws BusinessException
	{
		Element supportedAutoPays = createSupportedAutoPays(payment, isAutoPaymentSupported);
		if (supportedAutoPays == null)
		{
			log.warn("������ " + metadata.getName() + ", �� ������ supportedAutoPays.xml");
		}
		else
		{
			dictionaries.put("supportedAutoPays.xml", supportedAutoPays);
		}

		Element supportedAutoPaysApi = createSupportedAutoPays(payment, isAutoPaymentSupportedInApi);
		if (supportedAutoPaysApi == null)
		{
			log.warn("������ " + metadata.getName() + ", �� ������ supportedAutoPaysApi.xml");
		}
		else
		{
			dictionaries.put("supportedAutoPaysApi.xml", supportedAutoPaysApi);
		}

		Element supportedAutoPaysATM = createSupportedAutoPays(payment, isAutoPaymentSupportedInATM);
		if (supportedAutoPaysATM == null)
		{
			log.warn("������ " + metadata.getName() + ", �� ������ supportedAutoPaysATM.xml");
		}
		else
		{
			dictionaries.put("supportedAutoPaysATM.xml", supportedAutoPaysATM);
		}
	}

	private Element createSupportedAutoPays(JurPayment payment, String apiType) throws BusinessException
	{
		try
		{
			Map<AutoSubType, Object> accessAutoPaySchemes = new HashMap<AutoSubType, Object>();
			//���� receiverId ������� null ������ ������ �� ��������� ����������
			if (payment.getReceiverInternalId() == null)
			{
				//�������� ������ ��������� .
				accessAutoPaySchemes.put(AutoSubType.THRESHOLD, new AlwaysAutoPayScheme());
				return XmlHelper.parse(AutoSubscriptionTypeHelper.serialize(accessAutoPaySchemes)).getDocumentElement();
			}
			ServiceProviderBase provider = payment.getServiceProvider();
			if(provider == null || !(provider instanceof BillingServiceProvider))
				return null;

			BillingServiceProvider billingProvider = (BillingServiceProvider) provider;

			boolean isAutoPaySupport = false;
			if (apiType.equals(isAutoPaymentSupported))
				isAutoPaySupport = billingProvider.isAutoPaymentSupported();
			else if (apiType.equals(isAutoPaymentSupportedInApi))
				isAutoPaySupport = billingProvider.isAutoPaymentSupportedInApi();
			else if (apiType.equals(isAutoPaymentSupportedInATM))
				isAutoPaySupport = billingProvider.isAutoPaymentSupportedInATM();

			if(payment.getAlwaysAutoPayScheme() != null)
				accessAutoPaySchemes.put(AutoSubType.ALWAYS, payment.getAlwaysAutoPayScheme());

			if(payment.getThresholdAutoPayScheme() != null)
				accessAutoPaySchemes.put(AutoSubType.THRESHOLD, payment.getThresholdAutoPayScheme());

			if(payment.getInvoiceAutoPayScheme() != null)
				accessAutoPaySchemes.put(AutoSubType.INVOICE, payment.getInvoiceAutoPayScheme());

			// ���� ������ ����� ������ ���� �� ����, �� ����� ��
			if(!accessAutoPaySchemes.isEmpty())
			{
				String content = AutoSubscriptionTypeHelper.serialize(accessAutoPaySchemes);
				return XmlHelper.parse(content).getDocumentElement();
			}

			// ����� ����� ��, ��� � ��� � ���������� ���������
			if(isAutoPaySupport && billingProvider.getAlwaysAutoPayScheme() != null)
				accessAutoPaySchemes.put(AutoSubType.ALWAYS, billingProvider.getAlwaysAutoPayScheme());

			if(isAutoPaySupport && billingProvider.getThresholdAutoPayScheme() != null)
				accessAutoPaySchemes.put(AutoSubType.THRESHOLD, billingProvider.getThresholdAutoPayScheme());

			if(isAutoPaySupport && billingProvider.getInvoiceAutoPayScheme() != null)
				accessAutoPaySchemes.put(AutoSubType.INVOICE, billingProvider.getInvoiceAutoPayScheme());

			String content = AutoSubscriptionTypeHelper.serialize(accessAutoPaySchemes);
			if(StringHelper.isEmpty(content))
				return null;

			return XmlHelper.parse(content).getDocumentElement();
		}
		catch (ParserConfigurationException e)
		{
			throw new BusinessException(e);
		}
		catch (SAXException e)
		{
			throw new BusinessException(e);
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
	}
}
