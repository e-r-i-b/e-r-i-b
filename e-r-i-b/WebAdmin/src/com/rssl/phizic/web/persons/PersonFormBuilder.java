package com.rssl.phizic.web.persons;

import com.rssl.common.forms.Form;
import com.rssl.phizic.web.persons.formBuilders.*;

/**
 * Класс создает форму Form, соответствующую полям интерфейса Person
 *
 * @author Kidyaev
 * @ created 25.07.2006
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings({"OverlyLongMethod"})
public class PersonFormBuilder
{
    public PersonFormBuilder()
    {
    }

	private static class EmpoweredFullFormHolder
	{
		private static final Form form = new EmpoweredFullFormBuilder().buildForm();
	}

	private static class EmpoweredPartiallyFilledFormHolder
	{
		private static final Form form = new EmpoweredPartiallyFormBuilder().buildForm();
	}

	private static class FullFormHolder
	{
		private static final Form form = new FullPersonFormBuilder().buildForm();
	}

	private static class PartiallyFilledFormHolder
	{
		private static final Form form = new PartiallyPersonFormBuilder().buildForm();
	}

	private static class StopListFormHolder
	{
		private static final Form form = new StopListFormBuilder().buildForm();
	}

	public Form getEmpoweredFullForm()
    {
        return EmpoweredFullFormHolder.form;
    }

    public Form getEmpoweredPartiallyFilledForm()
    {
        return EmpoweredPartiallyFilledFormHolder.form;
    }

	public Form getFullForm()
    {
        return FullFormHolder.form;
    }

    public Form getPartiallyFilledForm()
    {
        return PartiallyFilledFormHolder.form;
    }

    public Form getStopListForm()
    {
        return StopListFormHolder.form;
    }
	/**
	 * Форма для PIN
	 */
	public Form buildPINForm()
	{
		return new PINFormBuilder().buildForm();
	}

    /**
     * Форма для регистрации договора
     */
    public Form buildClientRegistrationForm(Boolean needAccount)
    {
	    ClientRegistrationFormBuilder formBuilder = new ClientRegistrationFormBuilder();
	    formBuilder.setNeedAccount(needAccount);
		return formBuilder.buildForm();
    }

    /**
     * Форма для регистрации представителя
     */
    public Form buildEmpoweredRegistrationForm()
    {
	    return new EmpoweredRegistrationFormBuilder().buildForm();        
    }

    /**
     * Форма для расторжения договора
     */
    public Form buildClientCancelationForm(Boolean delete)
    {
	    ClientCancelationFormBuilder formBuilder = new ClientCancelationFormBuilder();
	    formBuilder.setDelete(delete);
		return formBuilder.buildForm();
    }
}

