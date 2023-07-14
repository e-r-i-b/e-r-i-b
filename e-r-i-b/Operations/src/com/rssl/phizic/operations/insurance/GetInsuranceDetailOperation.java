package com.rssl.phizic.operations.insurance;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.resources.external.insurance.InsuranceLink;
import com.rssl.phizic.business.resources.external.insurance.BusinessProcess;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.gate.insurance.InsuranceApp;
import com.rssl.phizic.gate.insurance.InsuranceService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * @author lukina
 * @ created 22.03.2013
 * @ $Author$
 * @ $Revision$
 * Операция для просмотра детальной информации по страховому/НПФ продукту клиента
 */

public class GetInsuranceDetailOperation extends OperationBase implements ViewEntityOperation
{
	private InsuranceLink insuranceLink;

	public void initialize(Long cardId) throws BusinessException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		insuranceLink = personData.getInsurance(cardId);
		if (insuranceLink == null)
			throw new ResourceNotFoundBusinessException("Не установлен идентификатор страховой программы", InsuranceLink.class);
	}

	public InsuranceLink getEntity()
	{
		return insuranceLink;
	}

	/**
	 * Получаем детальную информацияю по страховому продукту
	 * @return InsuranceApp
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public InsuranceApp getInsuranceApp() throws BusinessException, BusinessLogicException
	{
		InsuranceService insuranceService = GateSingleton.getFactory().service(InsuranceService.class);
		try
		{
			return insuranceService.getInsuranceApp(insuranceLink.getExternalId());
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			String message = "Информация по пенсионным продуктам, полученным в негосударственных фондах, временно недоступна.";
			if (insuranceLink.getBusinessProcess() == BusinessProcess.Insurance)
				message = "Информация по страховым продуктам временно недоступна.";
			throw  new BusinessLogicException(message, e);
		}
	}
}
