package com.rssl.phizic.business.persons;

import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.MultiInstanceExternalResourceService;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Omeliyanchuk
 * Date: 13.10.2006
 * Time: 13:09:30
 * To change this template use File | Settings | File Templates.
 */

//проверяем на наличие счетов для оплаты
public class PayAccountExistValidator  extends MultiFieldsValidatorBase
{
	protected static final MultiInstancePersonService personService = new MultiInstancePersonService();
	private final static MultiInstanceExternalResourceService externalResourceService = new MultiInstanceExternalResourceService();

	public static final String FIELD_O1 = "obj1";

	public boolean validate(Map values) throws TemporalDocumentException
	{
		String clientId = (String) retrieveFieldValue(FIELD_O1, values);
		if(clientId == null) return false;
		ActivePerson person = null;
		try
		{
			person = personService.findByClientId(clientId, null);
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
			accountLinks = externalResourceService.getLinks(login, AccountLink.class, personService.getPersonInstanceName(person.getId()));
		}
		catch(BusinessException e)
		{
			return false;
		}
		catch (BusinessLogicException e)
		{
			return false;
		}

		for (AccountLink accountLink : accountLinks)
			  if( accountLink.getPaymentAbility())
				   return true;

		return false;
	}
}
