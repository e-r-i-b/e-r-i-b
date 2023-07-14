package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.business.persons.*;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.person.Person;

import java.util.Map;
import java.util.Date;

/**
 * @author Gainanov
 * @ created 26.01.2009
 * @ $Author$
 * @ $Revision$
 */
public class PensionDepositTermsValidator extends MultiFieldsValidatorBase
{
	private static final String FIELD_ACCOUNT = "account";
	private static final String FIELD_DEPOSIT_NAME = "depositType";
	private static final String PENSION_DEPOSIT_NAME = "пенсион";
	public boolean validate(Map values) throws TemporalDocumentException
	{
		try
		{

			if(!((String)retrieveFieldValue(FIELD_DEPOSIT_NAME,values)).toLowerCase().contains(PENSION_DEPOSIT_NAME))
				return true;
			Account account = (Account) retrieveFieldValue(FIELD_ACCOUNT, values);
			BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
			Client client = GroupResultHelper.getOneResult(bankrollService.getOwnerInfo(account));
			PersonService service = new PersonService();
			Person person = service.findByClientId(client.getId());

			for(PersonDocument doc: person.getPersonDocuments())
			{
				if(doc.getDocumentType().equals(PersonDocumentType.PENSION_CERTIFICATE))
					if(doc != null && doc.getDocumentNumber() != null && !doc.getDocumentNumber().equals(""))
						return true;
			}
			int yearsCount;
			if(person.getGender().equals("F"))
				yearsCount = 55;
			else
				yearsCount = 60;

			Date years  = DateHelper.add(person.getBirthDay().getTime(), yearsCount,0,0);

			if(years.compareTo(DateHelper.getCurrentDate().getTime()) <=0)
				return true;
			return false;
		}
		catch (IKFLException e)
		{
			return false;
		}
	}
}
