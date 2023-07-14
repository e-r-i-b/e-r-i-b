package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.dictionaries.officies.OfficeTypeCompare;
import com.rssl.phizic.business.dictionaries.offices.OfficesComparator;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonContext;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;

import java.util.Map;

/**
 * @author malafeevsky
 * @ created 01.10.2009
 * @ $Author$
 * @ $Revision$
 */
public class AccountsFromOneOfficeValidator extends AccountsFromOneBranchValidator
{
	protected boolean compare(Office fromOffice, Office toOffice)
	{
		return OfficeTypeComparator.compare(fromOffice, toOffice, OfficeTypeCompare.full);
	}
}
