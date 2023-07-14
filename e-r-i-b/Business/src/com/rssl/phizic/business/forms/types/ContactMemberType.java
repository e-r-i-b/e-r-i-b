package com.rssl.phizic.business.forms.types;

import com.rssl.common.forms.types.Type;
import com.rssl.common.forms.types.FieldValueFormatter;
import com.rssl.common.forms.parsers.FieldValueParser;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.phizic.utils.ListUtil;

import java.util.List;

/**
 * @author Egorova
 * @ created 04.06.2008
 * @ $Author$
 * @ $Revision$
 */
public class ContactMemberType implements Type
{
	public static final Type INSTANCE = new ContactMemberType();

	private static final ContactMemberParser DEFAULT_PARSER      = new ContactMemberParser();
	private static final List<FieldValidator> DEFAULT_VALIDATORS = ListUtil.fromArray(new FieldValidator[0]);
	private static final FieldValueFormatter FORMATTER           = new Formatter();

	public String getName()
	{
		return "contactMember";
	}

	public FieldValueParser getDefaultParser()
	{
		return DEFAULT_PARSER;
	}

	public List<FieldValidator> getDefaultValidators()
	{
		return DEFAULT_VALIDATORS;
	}

		public FieldValueFormatter getFormatter()
	{
		return FORMATTER;
	}

	private static class Formatter implements FieldValueFormatter
	{
		public String toSignableForm(String value)
		{
			if(value == null)
				return "";

//          todo посмотреть, что в исправленном варианте ничто не поломалось
//			ContactMember contactMemeber = (ContactMember)value;
//			return contactMemeber.getSynchKey().toString();
			return value;
		}
	}
}
