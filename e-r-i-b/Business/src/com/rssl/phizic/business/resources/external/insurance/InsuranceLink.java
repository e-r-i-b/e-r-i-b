package com.rssl.phizic.business.resources.external.insurance;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.insurance.mock.MockInsuranceApp;
import com.rssl.phizic.business.resources.external.EditableExternalResourceLink;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.cache.CacheService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.insurance.InsuranceApp;
import com.rssl.phizic.gate.insurance.InsuranceService;

/**
 * Линк на страховой/НПФ продукт клиента
 * @author lukina
 * @ created 11.03.2013
 * @ $Author$
 * @ $Revision$
 */

public class InsuranceLink extends EditableExternalResourceLink
{
	public static final String CODE_PREFIX = "insurance-app";
	private String number; //reference страхового продукта
	private BusinessProcess businessProcess; //Наименование бизнес-процесса, в рамках которого оформлена страховка


	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	public BusinessProcess getBusinessProcess()
	{
		return businessProcess;
	}

	public void setBusinessProcess(BusinessProcess businessProcess)
	{
		this.businessProcess = businessProcess;
	}

	public String getCodePrefix()
	{
		return CODE_PREFIX;
	}

	public Object getValue() throws BusinessException, BusinessLogicException
	{
		return getInsuranceApp();
	}

	private InsuranceApp toInsuranceAppFromDb()
	{
		MockInsuranceApp app = new MockInsuranceApp();
		app.setId(getExternalId());

		return app;
	}

	public InsuranceApp getInsuranceApp()
	{
		try
		{
			InsuranceService insuranceService = GateSingleton.getFactory().service(InsuranceService.class);
			return insuranceService.getInsuranceApp(getExternalId());
		}
		catch (InactiveExternalSystemException e)
	    {
		    throw e;
	    }
		catch (Exception e)
		{
			log.error("Ошибка при получении страхового продукта по externalId " + getExternalId(), e);
			return  null;
		}
	}
	public void reset() throws BusinessLogicException, BusinessException
	{
		try
		{
			GateSingleton.getFactory().service(CacheService.class).clearInsuranceAppCache(toInsuranceAppFromDb());
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}

	public ResourceType getResourceType()
	{
		return ResourceType.INSURANCE_APP;
	}

	public String getPatternForFavouriteLink()
	{
		return  "$$insuranceLink:" + this.getId();
	}
}
