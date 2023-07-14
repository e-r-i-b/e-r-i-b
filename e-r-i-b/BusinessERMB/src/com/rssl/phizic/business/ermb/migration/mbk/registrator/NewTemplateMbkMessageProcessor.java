package com.rssl.phizic.business.ermb.migration.mbk.registrator;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.clients.ClientResourcesService;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.business.ermb.migration.MigrationHelper;
import com.rssl.phizic.business.ermb.migration.mbk.MBKTemplateConvertor;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.person.Person;

import java.util.List;

/**
 * Обработчик запросов на добавление шаблонов
 * @author Puzikov
 * @ created 11.07.14
 * @ $Author$
 * @ $Revision$
 */

public class NewTemplateMbkMessageProcessor extends TemplateMbkMessageProcessor
{
	private static final ExternalResourceService externalResourceService = new ExternalResourceService();
	private static final ClientResourcesService resourceService = new ClientResourcesService();

	@Override
	protected void updateTemplate(Person person, BillingServiceProvider provider, String paymentCardNumber, List<String> payerCodes) throws BusinessException, BusinessLogicException
	{
		MigrationHelper.initContext(person);
		resourceService.updateResources((ActivePerson) person, false, Card.class);
		CardLink cardLink = externalResourceService.findLinkByNumber(person.getLogin(), ResourceType.CARD, paymentCardNumber);
		if (cardLink == null)
			throw new IllegalArgumentException("Не найден кардлинк по карте списания");
		MBKTemplateConvertor converter = new MBKTemplateConvertor();
		TemplateDocument template = converter.getTemplate(person, provider, cardLink, payerCodes);
		TemplateDocumentService.getInstance().addOrUpdate(template);
	}
}
