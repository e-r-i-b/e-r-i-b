package com.rssl.phizic.web.cards.delivery;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

/**
 * форма для настройки переключателя работы АС выписка.
 *
 * @author bogdanov
 * @ created 21.05.15
 * @ $Author$
 * @ $Revision$
 */

public class ASVypiskaSettingsForm extends EditPropertiesFormBase
{
	private static final Form EDIT_FORM = createEditForm();

	@Override
	public Form getForm()
	{
		return EDIT_FORM;
	}

	private static Form createEditForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fb = new FieldBuilder();
		fb.setName("com.rssl.iccs.cardreports.asvypiska.active");
		fb.setDescription("Формировать отчеты в АС «Выписка»");
		fb.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("com.rssl.iccs.cardreports.asvypiska.VISA");
		fb.setDescription("Формировать отчеты в АС «Выписка» VISA");
		fb.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("com.rssl.iccs.cardreports.asvypiska.VISA.PDF_RUS");
		fb.setDescription("Формировать отчеты в АС «Выписка» VISA PDF RUS");
		fb.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("com.rssl.iccs.cardreports.asvypiska.VISA.PDF_ENG");
		fb.setDescription("Формировать отчеты в АС «Выписка» VISA PDF ENG");
		fb.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("com.rssl.iccs.cardreports.asvypiska.VISA.HTML_RUS");
		fb.setDescription("Формировать отчеты в АС «Выписка» VISA HTML RUS");
		fb.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("com.rssl.iccs.cardreports.asvypiska.VISA.HTML_ENG");
		fb.setDescription("Формировать отчеты в АС «Выписка» VISA HTML ENG");
		fb.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("com.rssl.iccs.cardreports.asvypiska.MasterCard");
		fb.setDescription("Формировать отчеты в АС «Выписка» MasterCard");
		fb.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("com.rssl.iccs.cardreports.asvypiska.MasterCard.PDF_RUS");
		fb.setDescription("Формировать отчеты в АС «Выписка» MasterCard PDF RUS");
		fb.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("com.rssl.iccs.cardreports.asvypiska.MasterCard.PDF_ENG");
		fb.setDescription("Формировать отчеты в АС «Выписка» MasterCard PDF ENG");
		fb.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("com.rssl.iccs.cardreports.asvypiska.MasterCard.HTML_RUS");
		fb.setDescription("Формировать отчеты в АС «Выписка» MasterCard HTML RUS");
		fb.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("com.rssl.iccs.cardreports.asvypiska.MasterCard.HTML_ENG");
		fb.setDescription("Формировать отчеты в АС «Выписка» MasterCard HTML ENG");
		fb.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("com.rssl.iccs.cardreports.asvypiska.AMEX");
		fb.setDescription("Формировать отчеты в АС «Выписка» AMEX");
		fb.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("com.rssl.iccs.cardreports.asvypiska.AMEX.PDF_RUS");
		fb.setDescription("Формировать отчеты в АС «Выписка» AMEX PDF RUS");
		fb.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("com.rssl.iccs.cardreports.asvypiska.AMEX.PDF_ENG");
		fb.setDescription("Формировать отчеты в АС «Выписка» AMEX PDF ENG");
		fb.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("com.rssl.iccs.cardreports.asvypiska.AMEX.HTML_RUS");
		fb.setDescription("Формировать отчеты в АС «Выписка» AMEX HTML RUS");
		fb.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("com.rssl.iccs.cardreports.asvypiska.AMEX.HTML_ENG");
		fb.setDescription("Формировать отчеты в АС «Выписка» AMEX HTML ENG");
		fb.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		return formBuilder.build();
	}
}