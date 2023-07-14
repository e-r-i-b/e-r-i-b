package com.rssl.phizic.operations.securities;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.clients.ClientResourcesService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.AbstractStoredResource;
import com.rssl.phizic.business.resources.external.StoredResourceMessages;
import com.rssl.phizic.business.resources.external.security.SecurityAccountLink;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.security.SecurityAccount;
import com.rssl.phizic.gate.security.SecurityAccountService;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.link.GetExternalResourceLinkOperation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lukina
 * @ created 05.09.13
 * @ $Author$
 * @ $Revision$
 */

public class ListSecuritiesOperation extends GetExternalResourceLinkOperation
{
	private static final Log LOG = PhizICLogFactory.getLog(LogModule.Core);
	private static final ClientResourcesService service = new ClientResourcesService();
	private  boolean isBackError; //были ли ошибки при получении списка  сберегательных сертификатов
	private  boolean isUseStoredResource = false; //неактивна внешн€€ система
	private Map<SecurityAccountLink, SecurityAccount> securityAccounts = new HashMap<SecurityAccountLink, SecurityAccount>();
	private String inactiveMessage;
	private String errorMessage;

	public void initialize()  throws BusinessException, BusinessLogicException
	{
		updateSecurityAccountList();
	}


	public Map<SecurityAccountLink, SecurityAccount> getSecurityAccounts()
	{
		return securityAccounts;
	}

	/**
	 * @return список линков, у которых выставлен признак "отображать в системе"
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public List<SecurityAccountLink> getSecurityAccountLinksInSystem() throws BusinessException, BusinessLogicException
	{

		List<SecurityAccountLink> securityAccountLinksInSystem = new ArrayList<SecurityAccountLink>();
		List<SecurityAccountLink> securityAccountLinks = PersonContext.getPersonDataProvider().getPersonData().getSecurityAccountLinksAll();
		for(SecurityAccountLink link : securityAccountLinks)
			if(link.getShowInSystem())
				securityAccountLinksInSystem.add(link);
		return securityAccountLinksInSystem;
	}

	private void updateSecurityAccountList() throws BusinessException, BusinessLogicException
	{
		List<SecurityAccountLink> securityAccountLinks = PersonContext.getPersonDataProvider().getPersonData().getSecurityAccountLinksAll();
		SecurityAccountService securityAccountService = GateSingleton.getFactory().service(SecurityAccountService.class);
		ActivePerson activePerson = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		List<SecurityAccount> list = new ArrayList<SecurityAccount>();
		try
		{
			list = securityAccountService.getSecurityAccountList(activePerson.asClient());
			securityAccounts = service.updateSecurityAccountLink(activePerson, list, securityAccountLinks);
        }
		 catch(GateException e)
	    {

		    LOG.error("ќшибка при получении списка сберегательных сертификатов", e);
		    throw new BusinessException(e);
	    }
		catch (GateLogicException ex)
		{
			LOG.error("ќшибка при получении списка сберегательных сертификатов", ex);
			isBackError = true;
			errorMessage = ex.getMessage();
		}
		catch (InactiveExternalSystemException ex)
		{
			securityAccounts = service.getStoredSecurityAccounts(securityAccountLinks);
			isUseStoredResource = true;
			if (securityAccounts.isEmpty())
				inactiveMessage = ex.getMessage();
			else
				inactiveMessage = StoredResourceMessages.getUnreachableMessageSystem((AbstractStoredResource) securityAccounts.entrySet().iterator().next().getValue());
			LOG.error("ќшибка при получении списка сберегательных сертификатов", ex);
		}
	}

	public String getInactiveMessage()
	{
		return inactiveMessage;
	}

	public boolean isBackError()
	{
		return isBackError;
	}

	public boolean isUseStoredResource()
	{
		return isUseStoredResource;
	}

	public String getErrorMessage()
	{
		return errorMessage;
	}
}
