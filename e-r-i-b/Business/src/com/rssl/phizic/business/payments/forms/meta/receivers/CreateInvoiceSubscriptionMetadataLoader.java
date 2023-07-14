package com.rssl.phizic.business.payments.forms.meta.receivers;

import com.rssl.common.forms.FormException;
import com.rssl.common.forms.PaymentFieldKeys;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.types.SubType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.documents.AutoSubscriptionTypeHelper;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.payments.AutoSubType;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.CreateInvoiceSubscriptionPayment;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

import java.util.*;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Лоадер меты для создания подписки на инвойсы
 * @author niculichev
 * @ created 04.05.14
 * @ $Author$
 * @ $Revision$
 */
public class CreateInvoiceSubscriptionMetadataLoader extends ExtendedMetadataLoaderBase
{
	public static final String RECIPIENT_ID_KEY = PaymentFieldKeys.PROVIDER_KEY;
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final ServiceProviderService providerService = new ServiceProviderService();

	public Metadata load(Metadata metadata, FieldValuesSource fieldSource) throws BusinessException, BusinessLogicException
	{
		String receiverId = fieldSource.getValue(RECIPIENT_ID_KEY);
		if (StringHelper.isEmpty(receiverId))
			throw new ResourceNotFoundBusinessException("Не задан получатель", BillingServiceProviderBase.class);

		BillingServiceProviderBase provider = (BillingServiceProviderBase) providerService.findById(Long.parseLong(receiverId));
		if (provider == null)
			throw new ResourceNotFoundBusinessException("Не задан получатель", BillingServiceProviderBase.class);

		CompositeFieldsBuilder fieldsBuilder = new CompositeFieldsBuilder();
		List<Field> keyFields = getProviderKeyFields(provider);
		fieldsBuilder.addBuilder(new GateFieldsBuilder(keyFields, provider.getBilling().getCode()));
		fieldsBuilder.addBuilder(new DocumentInfoFieldsBuilder(keyFields));
		fieldsBuilder.addBuilder(new ReceiverInfoFieldsBuilder(metadata, provider));

		Map<String, Element> dictionaries = new HashMap<String, Element>();
		Element dictionary = fieldsBuilder.buildFieldsDictionary();

		if (dictionary != null)
			dictionaries.put("extendedFields.xml", dictionary);
		else
			log.warn("Платеж " + metadata.getName() + ", не создан extendedFields.xml");

		return load(metadata, fieldsBuilder.buildFields(), dictionaries, provider.getNameService());
	}

	public Metadata load(Metadata metadata, BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		CreateInvoiceSubscriptionPayment payment = (CreateInvoiceSubscriptionPayment) document;

		CompositeFieldsBuilder fieldsBuilder = new CompositeFieldsBuilder();
		fieldsBuilder.addBuilder(new GateFieldsBuilder(DocumentHelper.getDocumentExtendedFields(payment), SubType.DINAMIC, payment.getBillingCode()));
		fieldsBuilder.addBuilder(new DocumentInfoFieldsBuilder(payment));
		fieldsBuilder.addBuilder(new ReceiverInfoFieldsBuilder(metadata, payment));

		Map<String, Element> dictionaries = new HashMap<String, Element>();
		Element dictionary = fieldsBuilder.buildFieldsDictionary();

		if (dictionary == null)
			log.warn("Платеж " + metadata.getName() + ", не создан extendedFields.xml");
		else
			dictionaries.put("extendedFields.xml", dictionary);

		Element supportedAutoPays = createSupportedAutoPays(payment);
		if(supportedAutoPays == null)
			log.warn("Платеж " + metadata.getName() + ", не создан supportedAutoPays.xml");
		else
			dictionaries.put("supportedAutoPays.xml", supportedAutoPays);

		return load(metadata,  fieldsBuilder.buildFields(), dictionaries, payment.getService().getName());
	}

	public Metadata load(Metadata metadata, TemplateDocument template) throws BusinessException, BusinessLogicException
	{
		throw new UnsupportedOperationException("Операция загрузки меты для создания шаблона не поддерживается");
	}

	public Metadata load(Metadata metadata, BusinessDocument document, TemplateDocument template) throws BusinessException, BusinessLogicException
	{
		throw new UnsupportedOperationException("Операция загрузки меты по шаблону не поддерживается");
	}

	public String getExtendedMetadataKey(FieldValuesSource fieldSource) throws FormException
	{
		return null;
	}

	private Element createSupportedAutoPays(JurPayment payment) throws BusinessException, BusinessLogicException
	{
		if(payment.getInvoiceAutoPayScheme() == null)
			return null;

		try
		{
			String content = AutoSubscriptionTypeHelper.serialize(
					Collections.<AutoSubType, Object>singletonMap(AutoSubType.INVOICE, payment.getInvoiceAutoPayScheme()));
			return XmlHelper.parse(content).getDocumentElement();
		}
		catch (ParserConfigurationException e)
		{
			throw new BusinessException(e);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private List<Field> getProviderKeyFields(BillingServiceProviderBase provider)
	{
		List<FieldDescription> providerFields = provider.getFieldDescriptions();
		List<Field> adoptFields = new ArrayList<Field>();
		for(Field field: providerFields)
		{
			if(field.isKey())
				adoptFields.add(field);
		}
		return adoptFields;
	}
}
