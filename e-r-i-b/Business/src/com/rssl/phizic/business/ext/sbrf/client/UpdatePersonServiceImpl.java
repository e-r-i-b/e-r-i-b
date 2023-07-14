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
		    log.info("��������� ���������� �� ���������� ������� � clientId:" + clientId);
		    ActivePerson person = getPerson(clientId);
		    updateState(newState, person);
	    }
	    catch(BusinessLogicException ex)
	    {
		    log.error("������ ��� ��������� ���������� �� �������� ������� � clientId:" + clientId);
		    throw new GateException(ex);
	    }
	    catch(BusinessException ex)
	    {
		    log.error("������ ��� ��������� ���������� �� �������� ������� � clientId:" + clientId);
		    throw new GateException(ex);
	    }
	}

	private ActivePerson getPerson(String clientId) throws BusinessException, InvalidTargetException
	{
		ActivePerson person = personService.findByClientId(clientId);
		if (person==null)
		{
			throw new InvalidTargetException("�� ������ ������ � ������� "+ clientId);
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
		    log.info("��������� ���������� � ������������ ������� � clientId:" + clientId);
			ActivePerson person = getPerson(clientId);
		    String state = person.getStatus();

			if (state.equals(Person.TEMPLATE) || state.equals(Person.SIGN_AGREEMENT))
			{
				throw new BusinessLogicException("���������� ����������� ������� ["+person.getFullName()+"] � ��������� [�����������]");
			}
		    if (islock)
				securityService.lock(person.getLogin(), lockDate, null, BlockingReasonType.system, "������������ ������� �� �����.����� �������������: "+ liability.getDecimal() + " " + liability.getCurrency().getCode(), null);
		    else
		        securityService.unlock(person.getLogin(), BlockingReasonType.system, lockDate);
		    log.info("���������� � ������������ ������� � clientId:" + clientId + " ������� ����������.");
	    }
		catch(BusinessLogicException ex)
	    {
		    log.error("������ ��� ��������� ���������� �� �������� ������� � clientId:" + clientId + ". ���������� ����������� ������� � ��������� [�����������]");
		    throw new GateException(ex);
	    }
		catch(InvalidTargetException ex)
		{
			throw ex;
		}
		catch(Exception ex)
		{
			log.error("������ ��� ��������� ���������� �� �������� ������� � clientId:" + clientId);
			throw new GateException(ex);
		}
	}

	public void updateState(CancelationCallBack callback, ClientState newState) throws GateException, GateLogicException
	{
	    try
	    {
		    log.info("��������� ���������� �� ���������� � ����� � ���������:" + callback.getId());
		    CancelationCallBackLink link = cancelationCallBackLinkService.findByCallBack(callback);
		    if (link == null || link.getPerson() == null)
		    {
			    String message = "�� ������ ���� ��� ����������� �������� � ���������������:" + callback.getId();
			    log.warn(message);
			    throw new InvalidTargetException(message);
		    }

		    updateState(newState, (ActivePerson)link.getPerson());

		    //������� �������������� ������.
		    cancelationCallBackLinkService.delete(link);
	    }
	    catch(BusinessLogicException ex)
	    {
		    log.error("������ ��� ��������� ���������� �� ���������� � ����� � ���������:" + callback.getId());
		    throw new GateException(ex);
	    }
	    catch(BusinessException ex)
	    {
		    log.error("������ ��� ��������� ���������� �� ���������� � ����� � ���������:" + callback.getId());
		    throw new GateException(ex);
	    }
	}

	/**
	 * �������� ��������� �������
	 * @param newState ����� ���������
	 * @param person ������� ��� �������������
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
				default: throw new GateException("����������� ������ �������");
		}
		log.info("���������� �� �������� ������� � clientId:" + clientId + " ������� ����������.");
	}
}
