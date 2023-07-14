package com.rssl.phizic.business.payments.forms.meta.receivers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.BankDictionaryService;
import com.rssl.phizic.business.dictionaries.payment.services.ServiceImpl;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderHelper;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShort;
import com.rssl.phizic.business.documents.exceptions.WrongDocumentTypeException;
import com.rssl.phizic.business.documents.payments.AutoSubscriptionPaymentBase;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.longoffer.autopayment.AutoPaymentHelper;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.dictionaries.officies.Office;

/**
 * @author krenev
 * @ created 27.06.2011
 * @ $Author$
 * @ $Revision$
 * Хендлер сохранения дополнительной информации о получателе в биллинговом платеже:
 * Сохраняется:
 * 1) информация о предоставляемой услуге.
 * 2) информация о транзитных реквизитах.
 * 3) информация об офисе заключения договора.
 */
public class ServiceProviderInfoSaveHandler extends BusinessDocumentHandlerBase
{
	private static final BankDictionaryService bankService = new BankDictionaryService();
	private static final DepartmentService departmentService = new DepartmentService();
	/**
	 * Обработать документ
	 * @param document документ
	 * @param stateMachineEvent
	 * @throws com.rssl.common.forms.DocumentLogicException неправильно заполнен документ, нужно исправить ошибки
	 */
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof JurPayment))
		{
			throw new WrongDocumentTypeException((BusinessDocument) document, JurPayment.class);
		}
		JurPayment jurPayment = (JurPayment) document;
		ServiceProviderShort serviceProvider = null;
		try
		{
			serviceProvider = ServiceProviderHelper.getServiceProvider(jurPayment.getReceiverInternalId());
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
		if (serviceProvider == null)
		{
			//получателя нет -> значит по свободным реквизитам
			//заполняем только код группы услуг значением по умолчанию
			jurPayment.setGroupService(AutoSubscriptionPaymentBase.DEFAULT_GROUP_SERVICE);
			return;
		}
		Office office = getProviderOffice(serviceProvider);
		if (office == null)
		{
			throw new DocumentException("К получателю не привязан офис. id=" + serviceProvider.getId());
		}

		jurPayment.setService(new ServiceImpl(serviceProvider.getCodeService(), serviceProvider.getNameService()));
		jurPayment.setReceiverTransitBank(getReceiverTransitBank(serviceProvider));
		jurPayment.setReceiverTransitAccount(serviceProvider.getTransitAccount());
		jurPayment.setReceiverOfficeCode(office.getCode());

		// Для автоподписки необходимо сохранить код группы услуг
		if(serviceProvider.getKind().equals("B"))
		{
			try
			{
				String groupService = AutoPaymentHelper.getGroupServiceByProvider(serviceProvider.getId());
				jurPayment.setGroupService(groupService);
			}
			catch (BusinessException e)
			{
				throw new DocumentException(e);
			}
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
