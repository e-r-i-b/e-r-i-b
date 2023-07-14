package com.rssl.phizic.business.documents.templates.stateMachine.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.documents.payments.BillingPaymentHelper;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.TemplateHelper;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Добавляет доп. поля при редактировании шаблона платежа
 *
 * @author khudyakov
 * @ created 27.06.2011
 * @ $Author$
 * @ $Revision$
 */
public class ExternalPaymentTemplateHandler extends TemplateHandlerBase<TemplateDocument>
{
	public void process(TemplateDocument template, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		try
		{
			ServiceProviderBase provider = TemplateHelper.getTemplateProvider(template);
			if (provider != null)
			{
				//noinspection unchecked
				template.setExtendedFields((List) getExtendedFields(template.getExtendedFields(), provider.getFieldDescriptions()));
			}
			else
			{
				template.setBillingCode(null);
			}

			template.setIdFromPaymentSystem(null);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	private List<FieldDescription> getExtendedFields(List<Field> extendedFields, List<FieldDescription> fieldDescriptions) throws DocumentException
	{
		if (CollectionUtils.isEmpty(fieldDescriptions))
		{
			return null;
		}

		List<FieldDescription> newExtendedFields = new ArrayList<FieldDescription>();
		for (FieldDescription fieldDescription : fieldDescriptions)
		{
			Field field = BillingPaymentHelper.getFieldById(extendedFields, fieldDescription.getExternalId());
			if (field != null)
			{
				fieldDescription.setValue(field.getValue());
			}
			newExtendedFields.add(fieldDescription);
		}
		return newExtendedFields;
	}
}
