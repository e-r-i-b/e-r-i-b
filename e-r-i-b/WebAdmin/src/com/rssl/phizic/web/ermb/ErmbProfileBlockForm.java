package com.rssl.phizic.web.ermb;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * User: Moshenko
 * Date: 13.06.2013
 * Time: 15:42:50
 * Блокировка профиля ЕРМБ
 */
public class ErmbProfileBlockForm extends EditFormBase
{
	public static final Form EDIT_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Статус");
		fieldBuilder.setName("status");
		fieldBuilder.setType("string");
		fb.addField(fieldBuilder.build());

		Expression enableExp = new RhinoExpression("form.status == 'true'");

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Причина блокировки");
		fieldBuilder.setName("lockDescription");
		fieldBuilder.setType("string");
		fieldBuilder.setEnabledExpression(enableExp);
		fieldBuilder.setValidators(
				new RequiredFieldValidator("Укажите причину блокировки"),
				new RegexpFieldValidator(".{1,256}", "Причина блокировки должно быть не более 256 символов.")
		);
	    fb.addField(fieldBuilder.build());

		return fb.build();
	}

}
