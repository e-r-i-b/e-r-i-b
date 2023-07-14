package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.GroupResultHelper;

import java.util.Map;
import java.util.StringTokenizer;

/**
 * валидатор проверяет имя получателя-физлица
 * в соответствии с данными из ЦОД
 *
 * @author: Pakhomova
 * @created: 27.08.2008
 * @ $Author$
 * @ $Revision$
 */
public class ReceiverFullNameValidator extends MultiFieldsValidatorBase
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final String ACCOUNT_FIELD = "receiverAccount";
	private static final String FULLNAME_FIELD = "receiverFullName";
	private static final String REGION_BANK_FIELD = "tb";
	private static final String TB_PARAMETER = "ourTB";

	public boolean validate(Map values) throws TemporalDocumentException
	{
		String userDefinedName = (String)retrieveFieldValue(FULLNAME_FIELD, values);
		String userDefinedAccount = (String)retrieveFieldValue(ACCOUNT_FIELD, values);
		String userDefinedTB = (String)retrieveFieldValue(REGION_BANK_FIELD, values);
		boolean result = true;

		if (StringHelper.isEmpty(userDefinedName) || StringHelper.isEmpty(userDefinedAccount))
			return result;

		StringTokenizer tokenizer = new StringTokenizer(getParameter(TB_PARAMETER), ";");
		boolean needCODCheck = false;
		while(tokenizer.hasMoreTokens())
			if(tokenizer.nextToken().equals(userDefinedTB))
				needCODCheck = true;

		if( !needCODCheck)
			return result;

		BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
		try {
			Account account = GroupResultHelper.getOneResult(bankrollService.getAccount(userDefinedAccount));
			Client receiver = GroupResultHelper.getOneResult(bankrollService.getOwnerInfo(account));
			if(receiver.getFullName().equals(MOCK_FULL_NAME))
			{
				log.warn("Проверка принадлежности счета не корректна, получен ответ из mock-сервиса!");
				return true;
			}
			result = receiver.getFullName().equalsIgnoreCase(userDefinedName);
		}
		catch (IKFLException ex)
		{
			String mess = "Ошибка при получении информации по счету №" + userDefinedAccount;
			log.error(mess,ex);
			throw new TemporalDocumentException(mess,ex);
		}
		return result;
	}
}
