package com.rssl.phizic.business.documents.templates.service.filters;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderState;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.TemplateHelper;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.gate.documents.attribute.ExtendedAttribute;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * Фильтр шаблонов документов мобильного банка
 *
 * нужно получить для заданного клиента список шаблонов платежей, таких что:
 * 1. поставщик услуги подключён
 * 2. поставщик услуги - поставщик мобильного банка
 * 3. поставщик имеет ровно одно ключевое поле
 *
 * @author khudyakov
 * @ created 13.07.14
 * @ $Author$
 * @ $Revision$
 */
public class MobileBankTemplateFilter implements TemplateDocumentFilter
{
	public boolean accept(TemplateDocument template)
	{
		if (FormType.INTERNAL_PAYMENT_SYSTEM_TRANSFER != template.getFormType())
		{
			return false;
		}

		try
		{
			BillingServiceProvider provider = (BillingServiceProvider) TemplateHelper.getTemplateProvider(template);
			if (provider == null)
			{
				return false;
			}

			if (ServiceProviderState.ACTIVE != provider.getState())
			{
				return false;
			}

			if (StringHelper.isEmpty(provider.getMobilebankCode()))
			{
				return false;
			}

			List<FieldDescription> fieldDescriptions = provider.getFieldDescriptions();
			if (CollectionUtils.isEmpty(fieldDescriptions) || fieldDescriptions.size() != 1)
			{
				return false;
			}

			FieldDescription fieldDescription = fieldDescriptions.get(0);
			if (!fieldDescription.isKey())
			{
				return false;
			}

			ExtendedAttribute attribute = template.getExtendedAttributes().get(fieldDescription.getExternalId());
			if (attribute == null)
			{
				return false;
			}

			return true;
		}
		catch (BusinessException e)
		{
			throw new IllegalArgumentException(e);
		}
		catch (GateException e)
		{
			throw new IllegalArgumentException(e);
		}
	}
}
