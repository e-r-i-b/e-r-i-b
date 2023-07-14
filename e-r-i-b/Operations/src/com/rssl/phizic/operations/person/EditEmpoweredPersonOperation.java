package com.rssl.phizic.operations.person;

import com.rssl.phizic.auth.MultiInstanceSecurityService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.operations.Transactional;

import com.rssl.phizic.business.operations.restrictions.UserRestriction;
import com.rssl.phizic.business.persons.*;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.business.AgreementNumberCreatorHelper;
import com.rssl.phizic.person.Person;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author Evgrafov
 * @ created 24.07.2006
 * @ $Author: moshenko $
 * @ $Revision: 49973 $
 */
public class EditEmpoweredPersonOperation extends PersonOperationBase implements EditEntityOperation<ActivePerson, UserRestriction>
{
    private static final MultiInstanceSecurityService securityService = new MultiInstanceSecurityService();

	//Представитель
    private ActivePerson empoweredPerson;
	//Доверитель
    private ActivePerson trustingPerson;

    public void initialize(Long trustingPersonId,Long personId) throws BusinessException, BusinessLogicException
    {
	    setPersonId(trustingPersonId);

        ActivePerson temp = (ActivePerson) personService.findById(personId, getInstanceName());

	    if(temp==null)
	    	temp = (ActivePerson) personService.findByIdNotNull(personId, null);

        if(temp.getTrustingPersonId() == null)
            throw new BusinessException("Пользователь с id " + personId + " не является представителем");

	    if(!temp.getTrustingPersonId().equals(getPerson().getId()))
	        throw new BusinessException("Представитель с id " + personId + "не пренадлежит клиенту с id " + getPerson().getId());

        PersonOperationBase.checkRestriction(getRestriction(), temp);
        empoweredPerson = temp;

	    if(!Person.ACTIVE.equals(empoweredPerson.getStatus()))
	        setMode(PersonOperationMode.direct);
    }

    public void initializeNew(Long trustingPersonId, String clientId) throws BusinessException, BusinessLogicException
    {
	    setPersonId(trustingPersonId);

        if (getPerson().getTrustingPersonId() != null)
            throw new BusinessException("Пользователь с id " + trustingPersonId + " сам является представителем.");

        trustingPerson = getPerson();
        empoweredPerson = new ActivePerson();

        setMode(PersonOperationMode.direct);

        if ( clientId == null )
          clientId = new RandomGUID().getStringValue();

        empoweredPerson.setClientId(clientId);
        empoweredPerson.setTrustingPersonId(getPerson().getId());
        empoweredPerson.setStatus(Person.TEMPLATE);

	    Department department      = getPersonDepartment();
	    empoweredPerson.setDepartmentId(department.getId());
	    String  agreementNumber = AgreementNumberCreatorHelper.getNextAgreementNumber(department);

	    empoweredPerson.setAgreementNumber(agreementNumber);
    }

	public ActivePerson getEntity()
    {
        return empoweredPerson;
    }

    public ActivePerson getTrustingPerson()
    {
        return trustingPerson;
    }

    @Transactional
    public void activate() throws BusinessException, BusinessLogicException
    {
        String state = empoweredPerson.getStatus();
	    final Calendar blockUntil = new GregorianCalendar();

        if (!state.equals(Person.TEMPLATE))
        {
            throw new BusinessLogicException("Неверный статус для подключения пользователя");
        }

        try
        {
            securityService.unlock(empoweredPerson.getLogin(), true,getInstanceName(), blockUntil.getTime());
            empoweredPerson.setStatus(Person.ACTIVE);
            personService.update(empoweredPerson,getInstanceName());
        }
        catch (SecurityDbException e)
        {
            throw new BusinessException("Ошибка при подключении клиента");
        }

    }

    @Transactional
    public void save() throws BusinessException
    {
        if(empoweredPerson.getId() == null)
        {
            personService.createLogin(empoweredPerson, getInstanceName());

            try
            {
                securityService.lock(empoweredPerson.getLogin(),getInstanceName());
            }
            catch (SecurityDbException e)
            {
                throw new BusinessException(e);
            }

            personService.add(empoweredPerson, getInstanceName());
        }
        else
        {
            personService.update(empoweredPerson, getInstanceName());
        }
    }
}