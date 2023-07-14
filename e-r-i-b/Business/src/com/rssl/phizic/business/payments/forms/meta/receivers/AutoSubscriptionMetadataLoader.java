package com.rssl.phizic.business.payments.forms.meta.receivers;

import com.rssl.common.forms.*;
import com.rssl.common.forms.types.SubType;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.ExtendedMetadataLoader;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.forms.ExtendedMetadata;
import com.rssl.phizic.business.resources.external.AutoSubscriptionLink;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.payments.longoffer.CardPaymentSystemPaymentLongOffer;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.Constants;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Загрузчик метаданных для автоподписки (редактирование, приостановка, возобновление, отмена)
 * @author niculichev
 * @ created 10.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class AutoSubscriptionMetadataLoader implements ExtendedMetadataLoader
{
	private static final String AUTOSUB_NUMBER = "autoSubNumber";
	private static final String EXTENDED_FIELDS_DICTIONARIES_NAME = "extendedFields.xml";
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	public Metadata load(Metadata metadata, FieldValuesSource fieldSource) throws BusinessException, BusinessLogicException
	{
		String autoSubNumber = fieldSource.getValue(AUTOSUB_NUMBER);
		if (StringHelper.isEmpty(autoSubNumber))
			throw new BusinessException("Для создания заявки необходим номер автоплатежа.");
		try
		{
			AutoSubscriptionLink link = getLink(autoSubNumber);
			CardPaymentSystemPaymentLongOffer autoSubDetailInfo = link.getAutoSubscriptionInfo();
			if(autoSubDetailInfo == null)
				throw new BusinessException("Автоподписка с номером " + autoSubNumber + " не существует");

			BillingServiceProvider provider = link.getServiceProvider();

			return loadByDetailInfo(metadata, autoSubDetailInfo, provider != null ? provider.getId() : null);
		}
		catch (BusinessLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (BusinessException e)
		{
			throw new BusinessException(e);
		}
	}

	public Metadata load(Metadata metadata, BusinessDocument document) throws BusinessException
	{
		return loadByDetailInfo(metadata, (CardPaymentSystemPaymentLongOffer) document, ((CardPaymentSystemPaymentLongOffer) document).getReceiverInternalId());
	}

	public Metadata load(Metadata metadata, TemplateDocument template) throws BusinessException, BusinessLogicException
	{
		throw new UnsupportedOperationException();
	}

	public Metadata load(Metadata metadata, BusinessDocument document, TemplateDocument template) throws BusinessException, BusinessLogicException
	{
		throw new UnsupportedOperationException();
	}

	private Metadata loadByDetailInfo(Metadata metadata, CardPaymentSystemPaymentLongOffer autoSubDetailInfo, Long providerInternalId) throws BusinessException
	{
		try
		{
			CompositeFieldsBuilder fieldsBuilder = new CompositeFieldsBuilder();
			fieldsBuilder.addBuilder(new GateFieldsBuilder(autoSubDetailInfo.getExtendedFields(), SubType.STATIC, autoSubDetailInfo.getBillingCode()));
			fieldsBuilder.addBuilder(getReceiverInfoFieldsBuilder(metadata, autoSubDetailInfo, providerInternalId));

			Map<String, Element> dictionaries = new HashMap<String, Element>();
			Element extendedFields = fieldsBuilder.buildFieldsDictionary();

			if (extendedFields == null)
				log.warn("Платеж " + metadata.getName() + ", не создан extendedFields.xml");
			else
				dictionaries.put(EXTENDED_FIELDS_DICTIONARIES_NAME, extendedFields);

			return load(metadata,  fieldsBuilder.buildFields(), dictionaries, autoSubDetailInfo.getService().getName());
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
	}

	public String getExtendedMetadataKey(FieldValuesSource fieldSource) throws FormException
	{
		return null;
	}

	private AutoSubscriptionLink getLink(String autoSubNumber) throws BusinessLogicException, BusinessException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		return personData.getAutoSubscriptionLink(Long.parseLong(autoSubNumber));
	}

	private ReceiverInfoFieldsBuilder getReceiverInfoFieldsBuilder(Metadata metadata, CardPaymentSystemPaymentLongOffer autoSubDetailInfo, Long providerInternalId) throws BusinessException
	{
		return new ReceiverInfoFieldsBuilder(
				metadata,
				autoSubDetailInfo.getReceiverPointCode(),
				autoSubDetailInfo.getReceiverINN(),
				autoSubDetailInfo.getReceiverAccount(),
				autoSubDetailInfo.getReceiverName(),
				autoSubDetailInfo.getBillingCode(),
				providerInternalId,
				autoSubDetailInfo.getReceiverBank()
				);
	}

	protected Metadata load(Metadata metadata,  List<Field> fields, Map<String, Element> dictionaries, String formDescription)
	{
		ExtendedMetadata newMetadata = new ExtendedMetadata();
		newMetadata.setOriginal(metadata);
		newMetadata.addAllDictionaries(metadata.getDictionaries());
		newMetadata.addAllDictionaries(dictionaries);

		Form form = metadata.getForm();
		FormBuilder formBuilder = new FormBuilder();
		formBuilder.setName(form.getName());
		formBuilder.setDetailedDescription(form.getDetailedDescription());
		formBuilder.setDescription(formDescription == null ? form.getDescription() : formDescription);

		formBuilder.addFields(fields);
		formBuilder.addFormValidators(form.getFormValidators());
		newMetadata.setExtendedForm(formBuilder.build());
		return newMetadata;
	}
}
