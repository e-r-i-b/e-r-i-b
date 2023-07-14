package com.rssl.phizic.business.documents.metadata;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FormException;
import com.rssl.common.forms.FormHelper;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.P2PAutoTransferClaimBase;
import com.rssl.phizic.business.documents.metadata.source.DocumentFieldValuesSource;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.documents.templates.Constants;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.payments.forms.meta.receivers.ExtendedMetadataLoaderBase;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.common.types.documents.StateCode;
import com.rssl.phizic.utils.StringHelper;
import org.w3c.dom.Element;

import java.util.*;

/**
 * Метадаталоадер заявки на создание автоплатежа карта-катра
 *
 * @author khudyakov
 * @ created 25.09.14
 * @ $Author$
 * @ $Revision$
 */
public class CardToCardAutoSubscriptionClaimMetadataLoader extends ExtendedMetadataLoaderBase
{
	private static final String FORM_NAME_ATTRIBUTE_NAME                            = "form";

	private static final Map<String, String> RECEIVER_TYPE_INFO = new HashMap<String, String>();
	static
	{
		RECEIVER_TYPE_INFO.put(Constants.RECEIVER_TYPE_ATTRIBUTE_NAME + FormType.INTERNAL_TRANSFER.getName(),           P2PAutoTransferClaimBase.SEVERAL_RECEIVER_TYPE_VALUE);
		RECEIVER_TYPE_INFO.put(Constants.RECEIVER_TYPE_ATTRIBUTE_NAME + FormType.INDIVIDUAL_TRANSFER.getName(),         RurPayment.PHIZ_RECEIVER_TYPE_VALUE);
		RECEIVER_TYPE_INFO.put(Constants.RECEIVER_TYPE_ATTRIBUTE_NAME + FormType.INDIVIDUAL_TRANSFER_NEW.getName(),     RurPayment.PHIZ_RECEIVER_TYPE_VALUE);
		RECEIVER_TYPE_INFO.put(Constants.RECEIVER_SUB_TYPE_ATTRIBUTE_NAME + FormType.INTERNAL_TRANSFER.getName(),       P2PAutoTransferClaimBase.SEVERAL_RECEIVER_SUB_TYPE_VALUE);
		RECEIVER_TYPE_INFO.put(Constants.RECEIVER_SUB_TYPE_ATTRIBUTE_NAME + FormType.INDIVIDUAL_TRANSFER.getName(),     RurPayment.OUR_CARD_TRANSFER_TYPE_VALUE);
	}

	public Metadata load(Metadata metadata, FieldValuesSource fieldSource) throws BusinessException, BusinessLogicException
	{
		if (isByDocument(fieldSource) || isByNewDocument(fieldSource))
		{
			//если подписка создается с формы платежа
			return load(metadata, new ExtendedFieldBuilder(metadata, fieldSource).build(), Collections.<String, Element>emptyMap(), metadata.getName());
		}

		return metadata;
	}

	private boolean isByDocument(FieldValuesSource fieldSource)
	{
		return StringHelper.isNotEmpty(fieldSource.getValue(Constants.DOCUMENT_NUMBER_ATTRIBUTE_NAME));
	}

	private boolean isByNewDocument(FieldValuesSource fieldSource)
	{
		String formName = fieldSource.getValue(FORM_NAME_ATTRIBUTE_NAME);
		return FormType.INTERNAL_TRANSFER.getName().equals(formName) || FormType.INDIVIDUAL_TRANSFER.getName().equals(formName);
	}

	public Metadata load(Metadata metadata, BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		if (!(document instanceof P2PAutoTransferClaimBase))
		{
			throw new BusinessException("Некорректный тип документа, ожидался наследник CardToCardAutoSubscriptionClaimBase");
		}

		return load(metadata, new ExtendedFieldBuilder(document, metadata).build(), Collections.<String, Element>emptyMap(), null);
	}

	public Metadata load(Metadata metadata, TemplateDocument template) throws BusinessException, BusinessLogicException
	{
		throw new UnsupportedOperationException();
	}

	public Metadata load(Metadata metadata, BusinessDocument document, TemplateDocument template) throws BusinessException, BusinessLogicException
	{
		throw new UnsupportedOperationException();
	}

	public String getExtendedMetadataKey(FieldValuesSource fieldSource) throws FormException
	{
		return null;
	}

	//TODO вынести данне приседания в pfd, отдельная задача
	private class ExtendedFieldBuilder
	{
		private List<Field> extendedFields = new ArrayList<Field>();

		private ExtendedFieldBuilder(Metadata metadata, FieldValuesSource source)
		{
			for (Field field : metadata.getForm().getFields())
			{
				if (isStaticInInitMode(field))
				{
					extendedFields.add(FormHelper.buildStaticField(field, getFieldValue(field, source)));
					continue;
				}
				extendedFields.add(field);
			}
		}

		private ExtendedFieldBuilder(BusinessDocument document, Metadata metadata) throws BusinessException
		{
			Map<String, String> values = new DocumentFieldValuesSource(metadata, document).getAllValues();

			if (StateCode.INITIAL.name().equals(document.getState().getCode()))
			{
				for (Field field : metadata.getForm().getFields())
				{
					if (isStaticInEditMode(field))
					{
						extendedFields.add(FormHelper.buildStaticField(field, values.get(field.getName())));
						continue;
					}
					extendedFields.add(field);
				}
			}
			else
			{
				for (Field field : metadata.getForm().getFields())
				{
					extendedFields.add(FormHelper.adaptField(field, values.get(field.getName())));
				}
			}
		}

		private List<Field> build()
		{
			return Collections.unmodifiableList(extendedFields);
		}

		private String getFieldValue(Field field, FieldValuesSource source)
		{
			String value = source.getValue(field.getName());
			if (StringHelper.isNotEmpty(value))
			{
				return value;
			}

			return RECEIVER_TYPE_INFO.get(field.getName() + source.getValue(FORM_NAME_ATTRIBUTE_NAME));
		}

		private boolean isStaticInInitMode(Field field)
		{
			String fieldName = field.getName();
			return Constants.RECEIVER_TYPE_ATTRIBUTE_NAME.equals(fieldName) || Constants.RECEIVER_SUB_TYPE_ATTRIBUTE_NAME.equals(fieldName);
		}

		private boolean isStaticInEditMode(Field field)
		{
			String fieldName = field.getName();
			return Constants.MESSAGE_TO_RECEIVER_ATTRIBUTE_NAME.equals(fieldName) || Constants.AUTO_SUBSCRIPTION_ATTRIBUTE_NAME.equals(fieldName)
					|| Constants.SELL_AMOUNT_VALUE_ATTRIBUTE_NAME.equals(fieldName) || Constants.AUTO_SUBSCRIPTION_START_DATE_ATTRIBUTE_NAME.equals(fieldName)
						|| Constants.AUTO_SUBSCRIPTION_EVENT_TYPE_ATTRIBUTE_NAME.equals(fieldName);
		}
	}
}
