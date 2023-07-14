package com.rssl.phizic.business.ermb.migration.mbk.registrator;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.TemplateHelper;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.business.documents.templates.service.filters.TemplateDocumentFilter;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.business.persons.clients.ClientImpl;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.store.SimpleStore;
import com.rssl.phizic.utils.store.StoreManager;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * Обработчик запросов на удаление шаблонов
 * @author Puzikov
 * @ created 11.07.14
 * @ $Author$
 * @ $Revision$
 */

public class RemoveTemplateMbkMessageProcessor extends TemplateMbkMessageProcessor
{
	@Override
	protected void updateTemplate(final Person person, final BillingServiceProvider provider, final String paymentCardNumber, final List<String> payerCodes)
			throws BusinessException, BusinessLogicException
	{
		StoreManager.setCurrentStore(new SimpleStore());
		List<TemplateDocument> toDelete = TemplateDocumentService.getInstance().getFiltered(new ClientImpl(person), new TemplateDocumentFilter()
		{
			public boolean accept(TemplateDocument template)
			{
				try
				{
					BillingServiceProvider templateProvider = (BillingServiceProvider) TemplateHelper.getTemplateProvider(template);
					if (templateProvider == null || StringHelper.isEmpty(templateProvider.getMobilebankCode()) || !templateProvider.getId().equals(provider.getId()))
					{
						return false;
					}

					List<FieldDescription> fieldDescriptions = templateProvider.getKeyFields();
					if (CollectionUtils.isEmpty(fieldDescriptions) || fieldDescriptions.size() != 1)
					{
						return false;
					}

					String id = fieldDescriptions.get(0).getExternalId();
					if (StringUtils.isEmpty(id))
					{
						return false;
					}

					String stringValue = template.getExtendedAttributes().get(id).getStringValue();
					if (StringUtils.isEmpty(stringValue))
					{
						return false;
					}

					if (!payerCodes.contains(stringValue))
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
		});
		if (CollectionUtils.isEmpty(toDelete))
		{
			log.error("Не нашлось шаблонов для удаления по данным МБК");
			return;
		}

		TemplateDocumentService.getInstance().remove(toDelete);
	}
}
