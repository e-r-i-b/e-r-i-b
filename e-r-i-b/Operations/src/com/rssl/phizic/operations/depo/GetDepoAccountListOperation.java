package com.rssl.phizic.operations.depo;

import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.resources.external.DepoAccountLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.business.resources.external.StoredDepoAccount;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.gate.depo.DepoAccount;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.ConfirmableOperationBase;
import com.rssl.phizic.utils.MockHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lukina
 * @ created 25.08.2010
 * @ $Author$
 * @ $Revision$
 */

public class GetDepoAccountListOperation extends ConfirmableOperationBase
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private Person person;
	private List<DepoAccount> depoAccounts;
	private boolean isRegisteredInDepo;

	private boolean isUseStoredResource;
	private boolean isBackError;

	public void initialize()
	{
		person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
	}
	/**
	 * Возвращает список всех счетов депо клиента
	 * @return список счетов депо
	 */
	public List<DepoAccountLink> getDepoAccounts()
	{
		PersonDataProvider provider = PersonContext.getPersonDataProvider();
		try
		{
			List<DepoAccountLink> links = provider.getPersonData().getDepoAccounts();
			for (DepoAccountLink link : links)
			{
				if (link.getDepoAccount() instanceof StoredDepoAccount)
				{
					isUseStoredResource = true;					
					break;
				}
			}

			for (DepoAccountLink link : links)
			{
				if(MockHelper.isMockObject(link.getDepoAccount()))
				{
					isBackError = true;
					break;
				}
			}

			return links;
		}
		catch (BusinessException e)
		{
			log.error("Ошибка получения списка счетов депо", e);
			return new ArrayList<DepoAccountLink>();
		}
		catch (BusinessLogicException e)
		{
			log.error("Ошибка получения списка счетов депо", e);
			return new ArrayList<DepoAccountLink>();
		}
	}

	/**
	 * Возвращает список счетов депо, отображаемых на главной странице
	 * @return список счетов депо
	 */
	public List<DepoAccountLink> getShowInMainDepoAccounts()
	{
		List<DepoAccountLink> depoAccountLinks = new ArrayList<DepoAccountLink>();
		for(DepoAccountLink link : getDepoAccounts())
			if(link.getShowInMain())
				depoAccountLinks.add(link);
		return depoAccountLinks;
	}

	public void updateRegisteredInDepo(boolean isRegisteredInDepo)
	{
		this.isRegisteredInDepo = isRegisteredInDepo;
	}
	public void updateDepoAccounts(List<DepoAccount> depoAccounts)
	{
		this.depoAccounts = depoAccounts;
	}
	public ConfirmableObject getConfirmableObject()
	{
		return person; 
	}

	/**
	 * Сохранение изменений в БД
	 * @throws BusinessException
	 */
	public void saveConfirm() throws BusinessException
	{
		PersonService personService = new PersonService();
		person.setIsRegisteredInDepo(isRegisteredInDepo);
		personService.update(person);
		ExternalResourceService resourceService = new ExternalResourceService();
		for (DepoAccount depoAccount : depoAccounts)
		{
			resourceService.addDepoAccountLink(person.getLogin(), depoAccount);
		}
	}

	public boolean isUseStoredResource()
	{
		return isUseStoredResource;
	}

	/**
	 * @return была ли ошибка во время получения данных
	 */
	public boolean isBackError()
	{
		return isBackError;
	}
}
