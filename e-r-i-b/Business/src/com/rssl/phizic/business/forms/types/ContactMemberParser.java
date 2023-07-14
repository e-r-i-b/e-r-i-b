package com.rssl.phizic.business.forms.types;

import com.rssl.common.forms.parsers.FieldValueParser;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.gate.dictionaries.ContactMember;

import java.text.ParseException;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

/**
 * @author Egorova
 * @ created 04.06.2008
 * @ $Author$
 * @ $Revision$
 */
public class ContactMemberParser implements FieldValueParser<ContactMember>
{
	private static SimpleService service = new SimpleService();

	public ContactMember parse(String value) throws ParseException
	{
		try
		{				   
			ContactMember contactMember = service.findSingle(DetachedCriteria.forClass(ContactMember.class).add(Expression.eq("code", value)));
			return contactMember;
		}
		catch (NumberFormatException e)
		{
			throw new ParseException(e.getMessage(), 0);
		}
		catch(BusinessException be)
		{
			throw new ParseException(be.getMessage(),0);
		}
	}
}
