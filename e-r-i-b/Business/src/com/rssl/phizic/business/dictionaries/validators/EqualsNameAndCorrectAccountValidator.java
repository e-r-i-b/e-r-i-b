package com.rssl.phizic.business.dictionaries.validators;

import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.Constants;

import java.util.Map;

/**
 * @author Egorova
 * @ created 01.08.2008
 * @ $Author$
 * @ $Revision$
 */
public class EqualsNameAndCorrectAccountValidator extends MultiFieldsValidatorBase
{
	private final static PersonService personService = new PersonService();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private final static String FIELD_PERSON_PERSON_ID = "personId";
	private final static String FIELD_RECEIVER_NAME = "name";
	private final static String FIELD_RECEIVER_ACCOUNT = "account";
	private static final String LOCAL_ISO_CODE = "643";

	public boolean validate(Map values) throws TemporalDocumentException
	{
		String receiverAccount = retrieveFieldValue(FIELD_RECEIVER_ACCOUNT, values).toString();

		String code  = receiverAccount.substring(5, 8);

		//Патч - затычка по случаю смены кода валюты у России
		code = code.equals("810") ? "643" : code;
		//todo так как система не только в России, то местная валюта может быть другая, поэтому криво
		try
		{
			String personId = (String) retrieveFieldValue(FIELD_PERSON_PERSON_ID, values);
			Person person = personService.findById(	Long.valueOf(personId));
			String fullName = person.getFullName();
			if (!code.equals(LOCAL_ISO_CODE))
				if (!((receiverAccount.startsWith("40819") || receiverAccount.startsWith("40820") || receiverAccount.startsWith("423") || receiverAccount.startsWith("426")) && retrieveFieldValue(FIELD_RECEIVER_NAME, values).equals(fullName)))
					return false;
		}
		catch(BusinessException e)
		{
			log.info("Ошибка при получении полного имени клиента. "+e);
			return false;
		}
		return true;
	}

}
