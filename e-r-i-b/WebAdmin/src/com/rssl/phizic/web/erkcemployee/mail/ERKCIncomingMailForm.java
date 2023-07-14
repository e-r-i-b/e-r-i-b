package com.rssl.phizic.web.erkcemployee.mail;

import com.rssl.common.forms.Form;
import com.rssl.phizic.web.mail.ListMailForm;

/**
 * @author akrenev
 * @ created 02.02.2012
 * @ $Author$
 * @ $Revision$
 *
 * форма списка входящих писем для сотрудников ЕРКЦ
 */

public class ERKCIncomingMailForm extends ListMailForm
{
	public static final Form ERKC_FILTER_FORM = getPartlyFormBuilder().build();
}
