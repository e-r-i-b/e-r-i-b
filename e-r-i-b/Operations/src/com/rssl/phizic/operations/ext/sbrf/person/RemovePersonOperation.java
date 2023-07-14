package com.rssl.phizic.operations.ext.sbrf.person;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.MultiInstanceSecurityService;
import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.menulinks.MenuLinkService;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.DeleteCommonLoginQueryBuilder;
import com.rssl.phizic.business.persons.DeletedPerson;
import com.rssl.phizic.business.persons.InvalidPersonStateException;
import com.rssl.phizic.business.persons.cancelation.CancelationCallBackLink;
import com.rssl.phizic.business.persons.cancelation.CancelationCallBackLinkService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.GateInfoService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.clients.CancelationCallBack;
import com.rssl.phizic.gate.clients.CancelationType;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.RegistartionClientService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.operations.ChangeUserTypeLogDataWrapper;
import com.rssl.phizic.operations.person.RegisterPersonChangesOperation;
import com.rssl.phizic.operations.person.RemovePersonOperationBase;
import com.rssl.phizic.person.Person;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 27.09.2005
 * Time: 15:50:27
 */
public class RemovePersonOperation extends RemovePersonOperationBase
{
	private static final RegistartionClientService registartionClientService = GateSingleton.getFactory().service(RegistartionClientService.class);
	private static final MultiInstanceSecurityService personSecurityService = new MultiInstanceSecurityService();
	private static final CancelationCallBackLinkService cancelationCallBackLinkService = new CancelationCallBackLinkService();
	private static final GateInfoService gateInfoServiceImpl = GateSingleton.getFactory().service(GateInfoService.class);

	@Transactional
    public void remove() throws BusinessException, BusinessLogicException
	{
		//чтобы не было org.hibernate.NonUniqueObjectException: a different object with the same identifier value was already associated with the session
		// BUG016551
	    final ActivePerson personToRemove = (ActivePerson)personService.findById(getPerson().getId(), null);

	    if ( personToRemove == null )
            throw new IllegalStateException("Не установлено поле 'person' у объекта класса 'RemovePersonOperation'");

	    String personStatus = personToRemove.getStatus();
	    if(personStatus.equals(Person.DELETED) || personStatus.equals(Person.ERROE_CANCELATION) || personStatus.equals(Person.WAIT_CANCELATION))
	        throw new InvalidPersonStateException(personToRemove, "невозможно удалить ползователя со статусом:"+personStatus);

		try
		{
			//отписываемся от оповещений по счетам
			unsubscribeAccounts();
			unsubscribeSubscriptions(personToRemove.getLogin());

			if(personStatus.equals(Person.ACTIVE))
			{
				if(getNeedAgrementCancellation())
				{
					//надо дождаться когда гейт разрешит удаление
					sendUnregisterRequest(personToRemove);
					personToRemove.setStatus(Person.WAIT_CANCELATION);

					if (!personToRemove.getContractCancellationCouse().equals("C"))
					{
						personSecurityService.lock(personToRemove.getLogin(), getInstanceName());
					}
					if (currentUser != null)
						personToRemove.setEmployeeId(currentUser.getId());
					personService.update(personToRemove);
				}
				else
				{
					//помечаем как удаленного, но ничего не удаляем, ибо ссылки
					DeletedPerson deletedPerson = personService.markDeleted(personToRemove);

					ChangeUserTypeLogDataWrapper.writeRemoveClientLogData(personToRemove.getCreationType().name(), personToRemove.getDepartmentId());

				}
				//отзвать непроведенные платежи
				if (gateInfoServiceImpl.isPaymentsRecallSupported(departmentService.findById(personToRemove.getDepartmentId())))
				{
					recallByOwner(personToRemove.getLogin());
				}

				//удаляем избранные ссылки и шаблоны платежей
				deleteFavourites(personToRemove);
				//удаляем настройки главного меню
				deleteMainMenuSettings(personToRemove.getLogin());
				return;//активного клиента не удаляем, только помечаем как удаленного.
			}

			//если шаблон, то можно полностью удалить.
			HibernateExecutor.getInstance().execute(new HibernateAction<Object>()
			{
				private SecurityService securityService = new SecurityService();

				public Object run(Session session) throws Exception
				{
					DeleteCommonLoginQueryBuilder builder = new DeleteCommonLoginQueryBuilder();

					//noinspection unchecked
					List<ActivePerson> empoweredPersons = personService.getEmpoweredPersons(personToRemove);


					// Удалить доверенных лиц
					for ( ActivePerson empoweredPerson : empoweredPersons )
					{
						removePerson(builder, empoweredPerson);
						securityService.markDeleted(empoweredPerson.getLogin());
					}

					// Удалить персону из БД
					removePerson(builder, personToRemove);
					securityService.markDeleted(personToRemove.getLogin());

					return null;
				}
			});
		}
		catch(BusinessLogicException e)
		{
			throw e;
		}
		catch(GateException e)
		{
			throw new BusinessException(e);
		}
		catch(GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
    }

	protected void sendUnregisterRequest(ActivePerson personToRemove) throws BusinessException,BusinessLogicException
	{
		if(!isAgreementBroken(personToRemove))
		{
			throw new InvalidPersonStateException(personToRemove, "перед удалением необходимо расторжение договора");
		}

		//todo перевести getContractCancellationCouse на enum
		CancelationType type = null;
		String reasonType = personToRemove.getContractCancellationCouse();
		if(reasonType.equalsIgnoreCase("C"))
		{
			type = CancelationType.client_request;
		}

		if(reasonType.equalsIgnoreCase("B"))
		{
			type = CancelationType.admin_request;
		}
		if(reasonType.equalsIgnoreCase("W"))
		{
			type = CancelationType.without_charge;
		}

		try
		{
			Client client = personToRemove.asClient();
			CancelationCallBack cancelationCallBack = registartionClientService.cancellation(client , null,
													personToRemove.getProlongationRejectionDate(), type, "");

			//запоминаем линк, для дальнейшей обработки.
			CancelationCallBackLink link = new CancelationCallBackLink(personToRemove, cancelationCallBack);
			cancelationCallBackLinkService.add(link);
		}
		catch(GateException ex)
		{
			throw new BusinessException("Ошибка при регистрации расторжения договора",ex);
		}
		catch(GateLogicException ex)
		{
			throw new BusinessLogicException(ex);
		}
	}

	public ActivePerson getEntity()
	{
		return getPerson();
	}

	/**
	 * Удаляем настройки главного меню для клиента
	 * @throws BusinessException
	 */
	private void deleteMainMenuSettings(Login login) throws BusinessException
	{
		MenuLinkService menuService = new MenuLinkService();
		menuService.removeByUserId(login);
	}
}
