package com.rssl.phizic.business.forms.types;

import com.rssl.common.forms.parsers.FieldValueParser;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.utils.StringHelper;

import java.text.ParseException;

/**
 * @author krenev
 * @ created 21.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class UserResourceParser implements FieldValueParser<ExternalResourceLink>
{
	public ExternalResourceLink parse(String value) throws ParseException
	{
		if (StringHelper.isEmpty(value))
		{
			return null;
		}
		try
		{
			PersonDataProvider provider = PersonContext.getPersonDataProvider();
			PersonData personData = provider.getPersonData();
			if (value.startsWith(UserResourceType.ACCOUNT_PREFIX))
			{
				String linkId = value.substring(UserResourceType.ACCOUNT_PREFIX_LENGTH);
				return personData.getAccount(Long.parseLong(linkId));
			}
			else if (value.startsWith(UserResourceType.CARD_PREFIX))
			{
				String linkId = value.substring(UserResourceType.CARD_PREFIX_LENGTH);
				return personData.getCard(Long.parseLong(linkId));
			}
			else if (value.startsWith(UserResourceType.LOAN_PREFIX))
			{
				String linkId = value.substring(UserResourceType.LOAN_PREFIX_LENGTH);
				return personData.getLoan(Long.parseLong(linkId));
			}
			else if (value.startsWith(UserResourceType.IMACCOUNT_PREFIX))
			{
				String linkId = value.substring(UserResourceType.IMACCOUNT_PREFIX_LENGTH);
				return personData.getImAccountLinkById(Long.parseLong(linkId));
			}
			throw new ParseException("ќжидаетс€ " + UserResourceType.ACCOUNT_PREFIX + ", "
					+ UserResourceType.CARD_PREFIX + ", " + UserResourceType.LOAN_PREFIX + " или "
					+ UserResourceType.IMACCOUNT_PREFIX, 0);
		}
		catch (BusinessException e)
		{
			throw new ParseException(e.getMessage(), 0);
		}
		catch (BusinessLogicException e)
		{
			throw new ParseException(e.getMessage(), 0);
		}
	}
}
