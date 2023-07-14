package com.rssl.phizic.business.forms.types;

import com.rssl.common.forms.parsers.FieldValueParser;
import com.rssl.common.forms.types.FieldValueFormatter;
import com.rssl.common.forms.types.Type;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.phizic.business.payments.forms.validators.UserResourceValidator;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.business.resources.external.IMAccountLink;
import com.rssl.phizic.utils.ListUtil;

import java.util.Collections;
import java.util.List;

/**
 * @author krenev
 * @ created 21.04.2010
 * @ $Author$
 * @ $Revision$
 * “ип "ресурсы пользовател€". —чета, карты, кредиты....
 */
public class UserResourceType implements Type
{
	public static final String ACCOUNT_PREFIX = AccountLink.CODE_PREFIX + AccountLink.CODE_DELIMITER;
	public static final int ACCOUNT_PREFIX_LENGTH = ACCOUNT_PREFIX.length();
	public static final String CARD_PREFIX = CardLink.CODE_PREFIX + CardLink.CODE_DELIMITER;
	public static final int CARD_PREFIX_LENGTH = CARD_PREFIX.length();
	public static final String LOAN_PREFIX = LoanLink.CODE_PREFIX + LoanLink.CODE_DELIMITER;;
	public static final int LOAN_PREFIX_LENGTH = LOAN_PREFIX.length();
	public static final String IMACCOUNT_PREFIX = IMAccountLink.CODE_PREFIX + IMAccountLink.CODE_DELIMITER;;
	public static final int IMACCOUNT_PREFIX_LENGTH = IMACCOUNT_PREFIX.length();

	public static final Type INSTANCE = new UserResourceType();
	private static final FieldValueParser defaultParser = new UserResourceParser();
	private static final List<FieldValidator> defaultValidators = ListUtil.fromArray(new FieldValidator[]{new UserResourceValidator()});
	private static final FieldValueFormatter formatter = new Formatter();

	public String getName()
	{
		return "resource";
	}

	public FieldValueParser getDefaultParser()
	{
		return defaultParser;
	}

	public List<FieldValidator> getDefaultValidators()
	{
		return Collections.unmodifiableList(defaultValidators);
	}

	public FieldValueFormatter getFormatter()
	{
		return formatter;
	}

	private static class Formatter implements FieldValueFormatter
	{
		public String toSignableForm(String value)
		{
			return value;
		}
	}
}
