package com.rssl.phizic.web.client.ext.sbrf.accounts;

import com.rssl.common.forms.Form;

/**
 * @author Erkin
 * @ created 29.04.2011
 * @ $Author$
 * @ $Revision$
 */
public class ShowAccountGraphicAbstractForm extends ShowAccountOperationsForm
{
	@SuppressWarnings({"FieldNameHidesFieldInSuperclass"})
	public static final Form FILTER_FORM = new AccountGraphicAbstractFilterFormBuilder().build();
}
