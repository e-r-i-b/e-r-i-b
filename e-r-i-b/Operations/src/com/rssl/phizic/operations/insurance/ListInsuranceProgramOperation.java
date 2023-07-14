package com.rssl.phizic.operations.insurance;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.clients.ClientResourcesService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.insurance.BusinessProcess;
import com.rssl.phizic.business.resources.external.insurance.InsuranceLink;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.insurance.InsuranceApp;
import com.rssl.phizic.gate.insurance.InsuranceService;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

/**
 * @author lukina
 * @ created 12.03.2013
 * @ $Author$
 * @ $Revision$
 * Операция для просмотра списка страховых/НПФ продуктов клиента
 */

public class ListInsuranceProgramOperation extends OperationBase implements ListEntitiesOperation
{
	private static final Log LOG = PhizICLogFactory.getLog(LogModule.Core);
	private List<InsuranceLink> programs; //список линков на страховые продукты клиента
	private Map<InsuranceLink, InsuranceApp> insuranceAppList = new HashMap<InsuranceLink, InsuranceApp>();
	private static final ClientResourcesService service = new ClientResourcesService();
	private  boolean isBackError; //были ли ошибки при получении списка  страховых продуктов

	public void initialize(BusinessProcess businessProcess)  throws BusinessException, BusinessLogicException
	{
		updateInsuranceList();
		programs = PersonContext.getPersonDataProvider().getPersonData().getInsuranceLinks(businessProcess);
	}
	
	public List<InsuranceLink> getInsurancePrograms()
	{
		return programs;
	}

	public Map<InsuranceLink, InsuranceApp> getInsuranceAppList()
	{
		return insuranceAppList;
	}

	public void setInsuranceAppList(Map<InsuranceLink, InsuranceApp> insuranceAppList)
	{
		this.insuranceAppList = insuranceAppList;
	}

	private void updateInsuranceList() throws BusinessException, BusinessLogicException
	{
		List<InsuranceLink> insuranceLinks = PersonContext.getPersonDataProvider().getPersonData().getInsuranceLinks();
		InsuranceService insuranceService = GateSingleton.getFactory().service(InsuranceService.class);
		ActivePerson activePerson = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		List<InsuranceApp> list = new ArrayList<InsuranceApp>();
		try
		{
			list = insuranceService.getInsuranceList(activePerson.asClient());
 		}
		 catch(GateException e)
	    {
		    LOG.error("Ошибка при получении списка страховых продуктов", e);
		    throw new BusinessException(e);
	    }
		catch (GateLogicException ex)
		{
			LOG.error("Ошибка при получении списка страховых продуктов", ex);
			isBackError = true;
		}
		finally
		{
			insuranceAppList = service.updateInsuranceLinks(activePerson, list, insuranceLinks);
		}
	}

	public boolean isBackError()
	{
		return isBackError;
	}
}
