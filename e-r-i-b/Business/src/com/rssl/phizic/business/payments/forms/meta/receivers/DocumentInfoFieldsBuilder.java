package com.rssl.phizic.business.payments.forms.meta.receivers;

import com.rssl.common.forms.Field;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProviderBase;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.payments.forms.ExtendedFieldBuilderHelper;
import org.apache.commons.collections.CollectionUtils;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Билдер для создания полей, которые указывают на выбранное из справочника документов поле
 * @author niculichev
 * @ created 11.06.14
 * @ $Author$
 * @ $Revision$
 */
public class DocumentInfoFieldsBuilder implements FieldsBuilder
{
	private List<? extends com.rssl.phizic.gate.payments.systems.recipients.Field> fields;

	public DocumentInfoFieldsBuilder(BillingServiceProviderBase provider)
	{
		fields = provider.getFieldDescriptions();
	}

	public DocumentInfoFieldsBuilder(JurPayment payment) throws BusinessException
	{
		fields = DocumentHelper.getDocumentExtendedFields(payment);
	}

	public DocumentInfoFieldsBuilder(List<com.rssl.phizic.gate.payments.systems.recipients.Field> fields)
	{
		this.fields = fields;
	}

	public List<Field> buildFields() throws BusinessException
	{
		if(CollectionUtils.isEmpty(fields))
			return Collections.emptyList();

		List<Field> res = new ArrayList<Field>();
		for(com.rssl.phizic.gate.payments.systems.recipients.Field field: fields)
			res.add(ExtendedFieldBuilderHelper.createDocumentInfoField(field));

		return res;
	}

	public Element buildFieldsDictionary()
	{
		return null;
	}
}
