package com.rssl.phizic.web.ext.sevb.persons;

import com.rssl.phizic.web.persons.EditEmpoweredPersonAction;
import com.rssl.phizic.web.persons.PersonFormBuilder;

/**
 * @author eMakarov
 * @ created 03.09.2008
 * @ $Author$
 * @ $Revision$
 */
public class SBRFEditEmpoweredPersonAction extends EditEmpoweredPersonAction
{
	private static final PersonFormBuilder sbrfPersonFormBuilder = new SBRFPersonFormBuilder();

	protected PersonFormBuilder getFormBuilder()
	{
		return sbrfPersonFormBuilder;
	}
}
