package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.StaticPersonData;
import com.rssl.phizic.business.documents.LoyaltyProgramRegistrationClaim;
import com.rssl.phizic.business.ermb.MobileBankManager;
import com.rssl.phizic.context.PersonContext;

import java.util.Collection;

/**
 * Заменяет хеш номера телефона клиента актуальным номером
 * @author gladishev
 * @ created 01.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class LoyaltyProgramRegistrationClaimPhoneHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{

		try
		{
			Login login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
			Collection<String> phones = StaticPersonData.getPhones(login, null);
			LoyaltyProgramRegistrationClaim claim = (LoyaltyProgramRegistrationClaim) document;
			String phoneHash = claim.getPhoneNumber();
			for (String phone : phones)
			{
				if (Integer.toString(phone.hashCode()).equals(phoneHash))
				{
					claim.setPhoneNumber(phone);
					return;
				}
			}
			throw new DocumentLogicException("В списке номеров телефонов, подключенных к Мобильному банку, " +
					"не найден номер с ключем " + phoneHash + ". Пожалуйста, обратитесь в отделение банка.");
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new DocumentLogicException(e);
		}
	}
}
