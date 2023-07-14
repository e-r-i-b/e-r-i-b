package com.rssl.phizic.web.configure;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

/**
 * форма настроек подтверждени€ операций между своими счетами.
 *
 * @author bogdanov
 * @ created 30.05.14
 * @ $Author$
 * @ $Revision$
 */

public class ConfirmSelfOperationsForm extends EditPropertiesFormBase
{
	private static Form EDIT_FORM = createForm();
	private static final String CONFIRM_ACCOUNT_CARD_KEY = "com.rssl.iccs.payment.confirm.selfOperation";
	private static final String CONFIRM_CLOSE_ACCOUNT_KEY = "com.rssl.iccs.payment.confirm.self.closeAccount";

	@Override
	public Form getForm()
	{
		return EDIT_FORM;
	}

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("ѕодтверждать операции вклад-вклад, вклад-карта");
		fieldBuilder.setName(CONFIRM_ACCOUNT_CARD_KEY);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("ѕодтверждать операцию Ђзакрытие вкладаї");
		fieldBuilder.setName(CONFIRM_CLOSE_ACCOUNT_KEY);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
