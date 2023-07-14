package com.rssl.phizic.operations.person;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.accounts.MockAccount;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.business.resources.external.MultiInstanceExternalResourceService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.notification.NotificationSubscribeService;
import org.hibernate.Session;

import java.util.List;

/** Created by IntelliJ IDEA. User: Evgrafov Date: 06.10.2005 Time: 12:56:47 */
public class RemoveResourceOwnOperation extends PersonOperationBase
{
	private static MultiInstanceExternalResourceService externalResourceService = new MultiInstanceExternalResourceService();


	private ExternalResourceLink externalResourceLink;

	public ExternalResourceLink getExternalResourceLink()
	{
		return externalResourceLink;
	}

	public void initialize(Long id, Class<? extends ExternalResourceLink> clazz) throws BusinessException
	{
		externalResourceLink = externalResourceService.findLinkById(clazz, id, getInstanceName());
	}

    public void remove() throws BusinessException
    {
	    try
	    {
	        HibernateExecutor.getInstance(getInstanceName()).execute(new HibernateAction<Void>()
	        {
	            public Void run(Session session) throws Exception
	            {
		            if(externalResourceLink instanceof AccountLink)
		            {
			            deleteAccountSubscription((AccountLink)externalResourceLink);

			            externalResourceService.removeLink(externalResourceLink, getInstanceName());

						// скорректировать список счетов представителей
						List<ActivePerson> empoweredPersons = personService.getEmpoweredPersons(getPerson(), getInstanceName());

						for (ActivePerson empoweredPerson : empoweredPersons)
						{
							EmpoweredAccountsModifier accountsModifier = new EmpoweredAccountsModifier(empoweredPerson, getPerson(),getInstanceName());
							accountsModifier.change(accountsModifier.getCurrentAccounts());
						}
		            }
		            //todo повторяющийся код. Нужно переписать "покрасивее".
		            else if(externalResourceLink instanceof CardLink)
		            {
			            externalResourceService.removeLink(externalResourceLink, getInstanceName());
			            
			            List<ActivePerson> empoweredPersons = personService.getEmpoweredPersons(getPerson(), getInstanceName());

						for (ActivePerson empoweredPerson : empoweredPersons)
						{
							EmpoweredCardsModifier cardsModifier = new EmpoweredCardsModifier(empoweredPerson, getPerson(), getInstanceName());
							cardsModifier.change(cardsModifier.getCurrentCards());
						}
		            }
		            else
		            {
			            externalResourceService.removeLink(externalResourceLink, getInstanceName());
		            }
		            return null;
	            }
	        });
	    }
	    catch (Exception e)
	    {
	       throw new BusinessException(e);
	    }
    }

	/**
	 * удаляем подписку на
	 * @throws BusinessException
	 * @throws GateException
	 */
	private void deleteAccountSubscription(AccountLink accountLink) throws BusinessException, GateException, GateLogicException
	{
		NotificationSubscribeService notificationService = GateSingleton.getFactory().service(NotificationSubscribeService.class);
		MockAccount account = new MockAccount();
		account.setNumber(accountLink.getNumber());
		account.setId(accountLink.getExternalId());
	    notificationService.unsubscribeAccount(account);
	}
}
