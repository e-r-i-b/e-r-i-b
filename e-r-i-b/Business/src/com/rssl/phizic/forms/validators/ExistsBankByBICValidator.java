package com.rssl.phizic.forms.validators;

import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.BankDictionaryService;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import org.apache.commons.lang.BooleanUtils;

import java.util.Map;

/**
 * Валидатор на проверку, существует ли банк с заданным БИК в справочнике банков.
 *
 * @author bogdanov
 * @ created 13.02.2013
 * @ $Author$
 * @ $Revision$
 */

public class ExistsBankByBICValidator extends MultiFieldsValidatorBase
{
	public static final String BIC = "bic";
	public static final String NEED_BANK_REQUISITE = "needBankRequisite";

	private static final BankDictionaryService bankService = new BankDictionaryService();

	public boolean validate(Map values)
	{
		if (!BooleanUtils.isTrue((Boolean) retrieveFieldValue(NEED_BANK_REQUISITE, values)))
			return true;

		try
		{
			return bankService.findByBIC((String) retrieveFieldValue(BIC, values), MultiBlockModeDictionaryHelper.getDBInstanceName()) != null;
		}
		catch (BusinessException ex)
		{
			throw new RuntimeException(ex);
		}
	}
}
