package com.rssl.phizic.business.documents.templates.stateMachine.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.BankDictionaryService;
import com.rssl.phizic.business.dictionaries.payment.services.ServiceImpl;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShort;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.TemplateHelper;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.dictionaries.officies.Office;

/**
 * Хендлер сохранения дополнительной информации о получателе в биллинговом платеже:
 * Сохраняется:
 * 1) информация о предоставляемой услуге.
 * 2) информация о транзитных реквизитах.
 * 3) информация об офисе заключения договора.
 *
 * @author khudyakov
 * @ created 10.02.14
 * @ $Author$
 * @ $Revision$
 */
public class ServiceProviderInfoSaveHandler extends TemplateHandlerBase<TemplateDocument>
{
	private static final DepartmentService departmentService = new DepartmentService();
	private static final BankDictionaryService bankService = new BankDictionaryService();

	public void process(TemplateDocument template, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		try
		{
			ServiceProviderShort serviceProvider = TemplateHelper.getTemplateProviderShort(template);
			if (serviceProvider == null)
			{
				//получателя нет -> и делать нечего.
				return;
			}

			Office office = getProviderOffice(serviceProvider);
			if (office == null)
			{
				throw new DocumentException("К получателю не привязан офис. id=" + serviceProvider.getId());
			}

			template.setService(new ServiceImpl(serviceProvider.getCodeService(), serviceProvider.getNameService()));
			template.setReceiverTransitBank(getReceiverTransitBank(serviceProvider));
			template.setReceiverTransitAccount(serviceProvider.getTransitAccount());
			template.setReceiverOfficeCode(office.getCode());
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	private ResidentBank getReceiverTransitBank(ServiceProviderShort serviceProvider) throws DocumentException
	{
		Office office = getProviderOffice(serviceProvider);
		if (office == null)
		{
			throw new DocumentException("К получателю не привязан офис. id=" + serviceProvider.getId());
		}

		ResidentBank result = new ResidentBank();
		try
		{
			ResidentBank bank = bankService.findByBIC(office.getBIC());
			if (bank != null)
			{
				result.setAccount(bank.getAccount());
			}
			result.setName(office.getName());
			result.setBIC(office.getBIC());
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
		return result;
	}

	private Office getProviderOffice(ServiceProviderShort serviceProvider) throws DocumentException
	{
		try
		{
			return departmentService.findById(serviceProvider.getDepartmentId());
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
