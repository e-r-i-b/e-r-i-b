package com.rssl.phizic.operations.person;

import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.certification.CertDemand;
import com.rssl.phizic.business.certification.CertDemandService;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.business.favouritelinks.FavouriteLink;
import com.rssl.phizic.business.favouritelinks.FavouriteLinkManager;
import com.rssl.phizic.business.messaging.info.SubscriptionService;
import com.rssl.phizic.business.operations.restrictions.UserRestriction;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.DeleteCommonLoginQueryBuilder;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.persons.clients.AdditionalClientsService;
import com.rssl.phizic.business.persons.clients.ClientImpl;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.business.statemachine.PaymentStateMachineService;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.clients.User;
import com.rssl.phizic.gate.documents.GateTemplate;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.notification.NotificationSubscribeService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.certification.CertificateOwnService;
import com.rssl.phizic.security.crypto.Certificate;
import org.hibernate.Query;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * @author mihaylov
 * created 08.09.2008
 * @ $Author$
 * @ $Revision$
 */
public abstract class RemovePersonOperationBase extends PersonOperationBase implements RemoveEntityOperation<ActivePerson, UserRestriction>
{
	protected static final PersonService personService = new PersonService();
	private static final SubscriptionService subscriptionService = new SubscriptionService();
	protected static final AdditionalClientsService additionalClientsService = new AdditionalClientsService();
	private final PaymentStateMachineService paymentStateMachineService = new PaymentStateMachineService();

	private List<AccountLink> accountLinks = null;
	protected User currentUser;
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();
	private static final String PAYMENT_STATE = "DISPATCHED";
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	public void initialize(Long personId) throws BusinessException, BusinessLogicException
    {
	    ExternalResourceService externalResourceService = new ExternalResourceService();
        setPersonId(personId);
	    accountLinks = externalResourceService.getLinks(getPerson().getLogin(), AccountLink.class);
    }

	public List<AccountLink> getAccountLinks()
	{
		return accountLinks;
	}

	/**
	 * ѕолучить пользовател€, который инициировал операцию
	 * @param currentUser - пользователь
	 */
	public void setCurrentUser(User currentUser)
	{
		this.currentUser = currentUser;
	}

	/**
	 * ќтписываемс€ от всех оповещений по счетам пользовател€
	 * @throws BusinessException
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	public void unsubscribeAccounts() throws BusinessException, GateException, GateLogicException
	{
		NotificationSubscribeService notificationService = GateSingleton.getFactory().service(NotificationSubscribeService.class);
		for (AccountLink accountLink : accountLinks)
		{
			notificationService.unsubscribeAccount(accountLink.getAccount());
		}
	}

	/**
	 * ќтписываемс€ от всех оповещений пользовател€
	 * @param login
	 * @throws BusinessException
	 */
	public void unsubscribeSubscriptions(CommonLogin login) throws BusinessException
	{
		subscriptionService.unsubscribeSubscriptions(login);
	}

	/**
	 * ”дал€ем избранные ссылки и шаблоны клиента
	 * @param person клиент
	 * @throws BusinessException
	 */
	public void deleteFavourites(Person person) throws BusinessException, BusinessLogicException
	{
		FavouriteLinkManager favouriteLinkManager = new FavouriteLinkManager();
		List<FavouriteLink> links = favouriteLinkManager.findByUserId(person.getLogin().getId());
		for(FavouriteLink link:links)
		{
			favouriteLinkManager.remove(link);
		}

		List<? extends GateTemplate> templates = TemplateDocumentService.getInstance().getAll(new ClientImpl(person));
		TemplateDocumentService.getInstance().remove(templates);
	}

	public void certDemandMarkDeleted()
	{
		CertDemandService certDemandService = new CertDemandService();
		for (CertDemand cd : certDemandService.getPersonsCertDemands(getPerson().getLogin()))
		{
			certDemandService.setDeletedStatus(cd.getId());
		}
	}

	public void certOwnMarkDeleted() throws SecurityDbException
	{
		CertificateOwnService certificateOwnService = new CertificateOwnService();
		for (Certificate co : certificateOwnService.find(getPerson().getLogin()))
		{
			certificateOwnService.setDeletedStatus(getPerson().getLogin(), co);
		}
	}

	/**
	 * ѕровер€ем расторгнут ли договор.
	 * @param personToRemove персона
	 * @return true - договор расторгнут.
	 */
	public boolean isAgreementBroken(ActivePerson personToRemove)
	{
		String couse = personToRemove.getContractCancellationCouse();
		return personToRemove.getProlongationRejectionDate() != null && couse != null && !"".equals(couse);
	}

	protected void removePerson(DeleteCommonLoginQueryBuilder builder, ActivePerson person) throws Exception
    {
        builder.setLoginId(person.getLogin().getId());
        List<Query> queries = builder.build();

        for ( Query query : queries )
        {
            query.executeUpdate();
        }

	    additionalClientsService.cleanClientIdsConnection(person.getClientId());
    }

	protected void recallByOwner(Login login) throws BusinessException, BusinessLogicException
	{
		List<BusinessDocument> businessDocuments = businessDocumentService.findByloginAndState(login, PAYMENT_STATE);
		for (BusinessDocument doc: businessDocuments)
		{
			try
			{
				StateMachineExecutor executor = new StateMachineExecutor(paymentStateMachineService.getStateMachineByFormName(doc.getFormName()));
				executor.initialize(doc);
				executor.fireEvent(new ObjectEvent(DocumentEvent.RECALL, "system"));
			}
			catch(BusinessLogicException e)
			{
				log.error("ƒокумент c id=" + doc.getId() + " не отозван", e);
			}
		}
	}
}
