package com.rssl.phizic.business.persons;

import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Kosyakova
 * Date: 31.10.2006
 * Time: 13:05:17
 * To change this template use File | Settings | File Templates.
 */

//проверяем на наличие хотя одного доступного счета
public class AccountExistValidator  extends MultiFieldsValidatorBase
{
	protected static final PersonService personService = new PersonService();
	private final static ExternalResourceService externalResourceService= new ExternalResourceService();

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

		Login login = person.getLogin();
		List<AccountLink> accountLinks =null;
		try
		{
			accountLinks  = externalResourceService.getLinks(login, AccountLink.class);
		}
		catch(BusinessException ex)
		{
			return false;
		}
		catch (BusinessLogicException e)
		{
			return false;
		}

		return accountLinks.size() > 0;
	}
}
