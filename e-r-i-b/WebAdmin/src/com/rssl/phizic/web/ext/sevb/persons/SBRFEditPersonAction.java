package com.rssl.phizic.web.ext.sevb.persons;

import com.rssl.phizic.web.persons.EditPersonAction;
import com.rssl.phizic.web.persons.PersonFormBuilder;

/**
 * @author Egorova
 * @ created 30.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class SBRFEditPersonAction extends EditPersonAction
{
	private static final PersonFormBuilder sbrfPersonFormBuilder = new SBRFPersonFormBuilder();

	protected PersonFormBuilder getFormBuilder()
	{
		return sbrfPersonFormBuilder;
	}
}
