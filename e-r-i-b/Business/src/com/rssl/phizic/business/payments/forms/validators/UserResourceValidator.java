package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.forms.types.UserResourceType;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.utils.StringHelper;

/**
 * Проверка на принадлежность ресурса(счета, карты) пользователю
 * @author krenev
 * @ created 21.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class UserResourceValidator extends FieldValidatorBase
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	public boolean validate(String value) throws TemporalDocumentException
	{
		if (StringHelper.isEmpty(value))
		{
			return true;
		}
		try
		{
			PersonDataProvider provider = PersonContext.getPersonDataProvider();
			PersonData personData = provider.getPersonData();
			if (value.startsWith(UserResourceType.ACCOUNT_PREFIX))
			{
				String linkId = value.substring(UserResourceType.ACCOUNT_PREFIX_LENGTH);
				return personData.getAccount(Long.parseLong(linkId)) != null;
			}
			else if (value.startsWith(UserResourceType.CARD_PREFIX))
			{
				String linkId = value.substring(UserResourceType.CARD_PREFIX_LENGTH);
				return personData.getCard(Long.parseLong(linkId)) != null;
			}
			else if (value.startsWith(UserResourceType.LOAN_PREFIX))
			{
				String linkId = value.substring(UserResourceType.LOAN_PREFIX_LENGTH);
				return personData.getLoan(Long.parseLong(linkId)) != null;
			}
			else if (value.startsWith(UserResourceType.IMACCOUNT_PREFIX))
			{
				String linkId = value.substring(UserResourceType.IMACCOUNT_PREFIX_LENGTH);
				return personData.getImAccountLinkById(Long.parseLong(linkId)) != null;
			}
			return false;
		}
		catch (BusinessException e)
		{
			log.warn(e);
			return false;
		}
		catch (BusinessLogicException e)
		{
			log.warn(e);
			return false;
		}
	}
}
