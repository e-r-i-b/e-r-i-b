package com.rssl.phizic.web.ext.sevb.persons;

import com.rssl.phizic.web.persons.PersonFormBuilder;
import com.rssl.phizic.web.ext.sevb.persons.formBuilders.*;
import com.rssl.common.forms.Form;

/**
 * @author Egorova
 * @ created 30.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class SBRFPersonFormBuilder extends PersonFormBuilder
{
	private static class SBRFFullPersonFormHolder
	{
		private static final Form form = new SBRFFullPersonFormBuilder().buildForm();
	}

	private static class SBRFPartiallyPersonFormHolder
	{
		private static final Form form = new SBRFPartiallyPersonFormBuilder().buildForm();
	}

	private static class SBRFStopListFormHolder
	{
		private static final Form form = new SBRFStopListFormBuilder().buildForm();
	}

	private static class SBRFEmpoweredFullFormHolder
	{
		private static final Form form = new SBRFEmpoweredFullFormBuilder().buildForm();
	}

	public Form getFullForm()
    {
        return SBRFFullPersonFormHolder.form;
    }

	public Form getPartiallyFilledForm()
    {
        return SBRFPartiallyPersonFormHolder.form;
    }

	public Form getStopListForm()
    {
        return SBRFStopListFormHolder.form;
    }

	public Form getEmpoweredFullForm()
    {
        return SBRFEmpoweredFullFormHolder.form;
    }
}
