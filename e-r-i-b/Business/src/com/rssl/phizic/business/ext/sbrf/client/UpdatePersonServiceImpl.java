package com.rssl.phizic.business.ext.sbrf.client;

import com.rssl.phizic.auth.BlockingReasonType;
import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.employees.EmployeeService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.persons.cancelation.CancelationCallBackLink;
import com.rssl.phizic.business.persons.cancelation.CancelationCallBackLinkService;
import com.rssl.phizic.business.persons.clients.UserImpl;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.CancelationCallBack;
import com.rssl.phizic.gate.clients.ClientState;
import com.rssl.phizic.gate.clients.UpdatePersonService;
import com.rssl.phizic.gate.clients.ClientStateCategory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.InvalidTargetException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

import java.util.Date;

/**
 * @author Omeliyanchuk
 * @ created 07.06.2008
 * @ $Author$
 * @ $Revision$
 */

public class UpdatePersonServiceImpl extends AbstractService implements UpdatePersonService
{
	private static final PersonService personService = new PersonService();
	private static final SecurityService securityService = new SecurityService();
	private static final CancelationCallBackLinkService cancelationCallBackLinkService = new CancelationCallBackLinkService();

	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	public UpdatePersonServiceImpl(GateFactory factory)
	{
		super(factory);
	}
	
	public void updateState(String clientId, ClientState newState) throws GateException
	{
	    try
	    {
		    log.info("Обработка оповещения об обновлении клиента с clientId:" + clientId);
		    ActivePerson person = getPerson(clientId);
		    updateState(newState, person);
	    }
	    catch(BusinessLogicException ex)
	    {
		    log.error("Ошибка при обработка оповещения об удалении клиента с clientId:" + clientId);
		    throw new GateException(ex);
	    }
	    catch(BusinessException ex)
	    {
		    log.error("Ошибка при обработка оповещения об удалении клиента с clientId:" + clientId);
		    throw new GateException(ex);
	    }
	}

	private ActivePerson getPerson(String clientId) throws BusinessException, InvalidTargetException
	{
		ActivePerson person = personService.findByClientId(clientId);
		if (person==null)
		{
			throw new InvalidTargetException("Не найден клиент с логином "+ clientId);
		}
		return person;
	}

	private void removePerson(ActivePerson person) throws BusinessException, BusinessLogicException
	{
		personService.markDeleted(person);	
	}

	private void setErrorDissolve(ActivePerson person) throws BusinessException
	{
		person.setStatus(Person.ERROE_CANCELATION);
		personService.update(person);
	}

	public void lockOrUnlock(String clientId, Date lockDate, Boolean islock, Money liability) throws GateException
	{
		try
	    {
		    log.info("Обработка оповещения о блокировании клиента с clientId:" + clientId);
			ActivePerson person = getPerson(clientId);
		    String state = person.getStatus();

			if (state.equals(Person.TEMPLATE) || state.equals(Person.SIGN_AGREEMENT))
			{
				throw new BusinessLogicException("Невозможно блокировать клиента ["+person.getFullName()+"] в состоянии [Подключение]");
			}
		    if (islock)
				securityService.lock(person.getLogin(), lockDate, null, BlockingReasonType.system, "Недостаточно средств на счете.Объем задолженности: "+ liability.getDecimal() + " " + liability.getCurrency().getCode(), null);
		    else
		        securityService.unlock(person.getLogin(), BlockingReasonType.system, lockDate);
		    log.info("Оповещение о блокировании клиента с clientId:" + clientId + " успешно обработано.");
	    }
		catch(BusinessLogicException ex)
	    {
		    log.error("Ошибка при обработка оповещения об удалении клиента с clientId:" + clientId + ". Невозможно блокировать клиента в состоянии [Подключение]");
		    throw new GateException(ex);
	    }
		catch(InvalidTargetException ex)
		{
			throw ex;
		}
		catch(Exception ex)
		{
			log.error("Ошибка при обработка оповещения об удалении клиента с clientId:" + clientId);
			throw new GateException(ex);
		}
	}

	public void updateState(CancelationCallBack callback, ClientState newState) throws GateException, GateLogicException
	{
	    try
	    {
		    log.info("Обработка оповещения об обновлении в связи с коллбеком:" + callback.getId());
		    CancelationCallBackLink link = cancelationCallBackLinkService.findByCallBack(callback);
		    if (link == null || link.getPerson() == null)
		    {
			    String message = "Не найден линк для расторжения договора с идентификатором:" + callback.getId();
			    log.warn(message);
			    throw new InvalidTargetException(message);
		    }

		    updateState(newState, (ActivePerson)link.getPerson());

		    //удаляем использованный колбек.
		    cancelationCallBackLinkService.delete(link);
	    }
	    catch(BusinessLogicException ex)
	    {
		    log.error("Ошибка при обработка оповещения об обновлении в связи с коллбеком:" + callback.getId());
		    throw new GateException(ex);
	    }
	    catch(BusinessException ex)
	    {
		    log.error("Ошибка при обработка оповещения об обновлении в связи с коллбеком:" + callback.getId());
		    throw new GateException(ex);
	    }
	}

	/**
	 * обновить состояние клиента
	 * @param newState новое состояние
	 * @param person персона для обнобновления
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 * @throws GateException
	 */
	private void updateState(ClientState newState, ActivePerson person) throws BusinessException, BusinessLogicException, GateException
	{
		String clientId = person.getClientId();
		switch(newState.getCategory())
		    {
			    case agreement_dissolve:removePerson(person);break;
				case error_dissolve:setErrorDissolve(person);break;
				case system_error_dissolve:setErrorDissolve(person);break;
				default: throw new GateException("Неизвестный статус клиента");
		}
		log.info("Оповещение об удалении клиента с clientId:" + clientId + " успешно обработано.");
	}
}
