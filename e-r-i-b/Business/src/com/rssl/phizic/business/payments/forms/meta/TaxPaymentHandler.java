package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.BankDictionaryService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.TaxationServiceProvider;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.gate.dictionaries.ResidentBank;

/**
 * @author gladishev
 * @ created 18.06.2010
 * @ $Author$
 * @ $Revision$
 */
public class TaxPaymentHandler extends BusinessDocumentHandlerBase
{
	private static final ServiceProviderService providerService = new ServiceProviderService();
	private static final BankDictionaryService bankService = new BankDictionaryService();
	private static final DepartmentService departmentService = new DepartmentService();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof RurPayment))
			throw new DocumentException("Некорректный тип документа, ожидался RurPayment");

		RurPayment payment = (RurPayment) document;
		if (!payment.isTaxPayment())
			return;

		try
		{
			TaxationServiceProvider provider = providerService.findTaxProviderByINN(payment.getReceiverINN());
			if (provider == null) //пользователь ввел получателя не из справочника
				return;

			payment.setReceiverName(provider.getName());
			payment.setReceiverKPP(provider.getKPP());
			payment.setReceiverAccount(provider.getAccount());
			payment.setReceiverBank(new ResidentBank(provider.getName(), provider.getBIC(), provider.getCorrAccount()));
			payment.setFullPayment(provider.isFullPayment());
			if (!provider.isFullPayment())
			{
				Department providerDepartment = departmentService.findById(provider.getDepartmentId());
				payment.setTransitBankName(providerDepartment.getName());
				String providerBIC = providerDepartment.getBIC();
				payment.setTransitBankBIC(providerBIC);
				payment.setTransitBankAccount(provider.getTransitAccount());
				ResidentBank bank = bankService.findByBIC(providerBIC);
				if (bank == null)
				{
					throw new DocumentException("Не найден банк в справочнике с БИКом " + providerBIC);
				}
				payment.setTransitBankCorAccount(bank.getAccount());
			}
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
