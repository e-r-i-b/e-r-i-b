package com.rssl.phizic.business.payments.forms.meta.receivers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.Field;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.source.DocumentFieldValuesSource;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.documents.templates.Constants;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.payments.forms.TemplateFieldBuilderHelper;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author khudyakov
 * @ created 03.06.2013
 * @ $Author$
 * @ $Revision$
 */

public class PaymentSystemTransferTemplateFieldBuilder implements FieldsBuilder
{
	private static final Map<String, String> needDynamicFieldMap = new HashMap<String, String>();

	private Metadata metadata;
	private Map<String, String> formData = new HashMap<String, String>();
	private Map<String, String> paymentFormData = new HashMap<String, String>();
	private boolean isNeedDynamic = false;

	static
	{
		needDynamicFieldMap.put(Constants.RECEIVER_INN_ATTRIBUTE_NAME, Boolean.TRUE.toString());
		needDynamicFieldMap.put(Constants.RECEIVER_ACCOUNT_ATTRIBUTE_NAME, Boolean.TRUE.toString());
		needDynamicFieldMap.put(Constants.RECEIVER_BANK_BIK_ATTRIBUTE_NAME, Boolean.TRUE.toString());
	}

	public PaymentSystemTransferTemplateFieldBuilder(Metadata metadata, TemplateDocument template) throws BusinessException
	{
		this.metadata = metadata;
		this.formData.putAll(template.getFormData());
	}

	public PaymentSystemTransferTemplateFieldBuilder(Metadata metadata, JurPayment jurPayment, TemplateDocument template) throws BusinessException
	{
		try
		{
			this.metadata = metadata;
			this.formData.putAll(template.getFormData());
			this.isNeedDynamic = jurPayment == null && FormType.EXTERNAL_PAYMENT_SYSTEM_TRANSFER.equals(template.getFormType()) && template.getReceiverInternalId() == null;

			if (template.getReceiverInternalId() == null && (jurPayment == null || CollectionUtils.isEmpty(jurPayment.getExtendedFields())))
			{
				//если поставщик не из нашей базы, начинаем запрос доп. полей с самого начала
				formData.remove(Constants.PROVIDER_BILLING_CODE_ATTRIBUTE_NAME);
			}

			if (jurPayment != null)
			{
				paymentFormData.putAll(new DocumentFieldValuesSource(metadata, jurPayment).getAllValues());
			}
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
	}

	public List<Field> buildFields() throws BusinessException
	{
		List<Field> fields = new ArrayList<Field>();
		for (Field field : metadata.getForm().getFields())
		{
			String value = MapUtils.isEmpty(paymentFormData) ? formData.get(field.getName()) : paymentFormData.get(field.getName());
			boolean isNeedDynamicType = isNeedDynamic && StringHelper.isNotEmpty(needDynamicFieldMap.get(field.getName()));

			fields.add(TemplateFieldBuilderHelper.adaptField(field, value, isNeedDynamicType));
		}
		return fields;
	}

	public Element buildFieldsDictionary()
	{
		return null;
	}
}
