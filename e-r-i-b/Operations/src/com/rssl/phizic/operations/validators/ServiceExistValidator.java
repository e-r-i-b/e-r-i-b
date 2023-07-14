package com.rssl.phizic.operations.validators;

import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.services.Service;
import com.rssl.phizic.operations.access.EmpoweredAccessModifier;
import com.rssl.phizic.auth.modes.AccessType;

import java.util.List;
import java.util.Map;


/**
 * Created by IntelliJ IDEA.
 * User: Kosyakova
 * Date: 31.10.2006
 * Time: 13:33:52
 * To change this template use File | Settings | File Templates.
 */

//проверяем на наличие хотя одной доступной операции
public class ServiceExistValidator extends MultiFieldsValidatorBase
{
    protected static final PersonService personService = new PersonService();

    public static final String FIELD_O1 = "obj1";

    public boolean validate(Map values) throws TemporalDocumentException
    {
        String clientId = (String) retrieveFieldValue(FIELD_O1, values);
        if(clientId == null) return false;
        ActivePerson person = null;
        try
        {
            person = personService.findByClientId(clientId);
        }

        catch(BusinessException ex)
        {
            return false;
        }
        if( person == null )
            return false;
        ActivePerson trustingPerson = null;

        try
        {
            trustingPerson = (ActivePerson) personService.findById(person.getTrustingPersonId());
        }

        catch (BusinessException e)
        {
            return false;
        }
        if( trustingPerson == null )
            return false;

        EmpoweredAccessModifier simpleAccessModifier = null;
	    EmpoweredAccessModifier secureAccessModifier = null;
        try
        {
            simpleAccessModifier = new EmpoweredAccessModifier(person, trustingPerson, AccessType.simple, null);
            List<Service> simpleCurrentServices = simpleAccessModifier.getCurrentServices();
	        secureAccessModifier = new EmpoweredAccessModifier(person, trustingPerson, AccessType.secure, null);
            List<Service> secureCurrentServices = secureAccessModifier.getCurrentServices();
            return ( (simpleCurrentServices.size() > 0) || (secureCurrentServices.size() > 0) );
        }

        catch (BusinessException e)
        {
            return false;
        }

        catch (BusinessLogicException e)
        {
            return false;
        }
    }
}
