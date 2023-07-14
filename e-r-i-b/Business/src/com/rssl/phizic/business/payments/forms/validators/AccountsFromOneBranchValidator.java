package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.dictionaries.officies.OfficeTypeCompare;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.MockHelper;

import java.util.Map;

/**
 * @author Egorova
 * @ created 24.10.2008
 * @ $Author$
 * @ $Revision$
 */
public class AccountsFromOneBranchValidator extends MultiFieldsValidatorBase
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	public static final String FIELD_FROM_ACCOUNT = "fromAccount";
	public static final String FIELD_TO_ACCOUNT   = "toAccount";



	public boolean validate(Map values) throws TemporalDocumentException
	{
		Account fromAccount = (Account) retrieveFieldValue(FIELD_FROM_ACCOUNT, values);
		Account toAccount   = (Account) retrieveFieldValue(FIELD_TO_ACCOUNT, values);

		if(fromAccount == null)
			return true;

		if (toAccount == null)
			return true;


		Office fromOffice = null;
		Office toOffice = null;

		PersonDataProvider provider = PersonContext.getPersonDataProvider();

		try
		{
			//смотрим доступен ли контест, если да то берем оттуда т.к. там кеш
			if(provider!=null)
			{
				PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
				AccountLink fromAccountLink = personData.findAccount(fromAccount.getNumber());
				AccountLink toAccountLink = personData.findAccount(toAccount.getNumber());
				//если счет добавлен клиенту, то берем у него офис
				if(fromAccountLink!=null)
						fromOffice = fromAccountLink.getOffice();
				if(toAccountLink!=null)
						toOffice = toAccountLink.getOffice();
			}
		}
		catch (BusinessException e)
		{
			log.warn("Не удалось сравнить офисы счетов:"+toAccount.getNumber() +","+fromAccount.getNumber(), e);
			throw new RuntimeException(e);
		}
		catch (BusinessLogicException e)
		{
			log.warn("Не удалось сравнить офисы счетов:"+toAccount.getNumber() +","+fromAccount.getNumber(), e);
			throw new RuntimeException(e);
		}

		//если офис не найден в контексте, то придется обращаться к абс
		if(MockHelper.isMockObject(toOffice))
		{
			toOffice = toAccount.getOffice();
		}

		if(MockHelper.isMockObject(fromOffice))
		{
			fromOffice = fromAccount.getOffice();
		}

		if(fromOffice==null || toOffice==null)
		{
			log.warn("Не удалось сравнить офисы счетов:"+toAccount.getNumber() +","+fromAccount.getNumber());
			return false;
		}
		return compare(fromOffice, toOffice);

	}

	protected boolean compare(Office fromOffice, Office toOffice)
	{
		return OfficeTypeComparator.compare(fromOffice, toOffice, OfficeTypeCompare.oneBranch);
	}
}
