package com.rssl.phizic.web.cards.delivery;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

/**
 * ����� ��� ��������� ������������� ������ �� �������.
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
		fb.setDescription("����������� ������ � �� ��������");
		fb.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("com.rssl.iccs.cardreports.asvypiska.VISA");
		fb.setDescription("����������� ������ � �� �������� VISA");
		fb.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("com.rssl.iccs.cardreports.asvypiska.VISA.PDF_RUS");
		fb.setDescription("����������� ������ � �� �������� VISA PDF RUS");
		fb.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("com.rssl.iccs.cardreports.asvypiska.VISA.PDF_ENG");
		fb.setDescription("����������� ������ � �� �������� VISA PDF ENG");
		fb.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("com.rssl.iccs.cardreports.asvypiska.VISA.HTML_RUS");
		fb.setDescription("����������� ������ � �� �������� VISA HTML RUS");
		fb.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("com.rssl.iccs.cardreports.asvypiska.VISA.HTML_ENG");
		fb.setDescription("����������� ������ � �� �������� VISA HTML ENG");
		fb.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("com.rssl.iccs.cardreports.asvypiska.MasterCard");
		fb.setDescription("����������� ������ � �� �������� MasterCard");
		fb.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("com.rssl.iccs.cardreports.asvypiska.MasterCard.PDF_RUS");
		fb.setDescription("����������� ������ � �� �������� MasterCard PDF RUS");
		fb.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("com.rssl.iccs.cardreports.asvypiska.MasterCard.PDF_ENG");
		fb.setDescription("����������� ������ � �� �������� MasterCard PDF ENG");
		fb.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("com.rssl.iccs.cardreports.asvypiska.MasterCard.HTML_RUS");
		fb.setDescription("����������� ������ � �� �������� MasterCard HTML RUS");
		fb.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("com.rssl.iccs.cardreports.asvypiska.MasterCard.HTML_ENG");
		fb.setDescription("����������� ������ � �� �������� MasterCard HTML ENG");
		fb.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("com.rssl.iccs.cardreports.asvypiska.AMEX");
		fb.setDescription("����������� ������ � �� �������� AMEX");
		fb.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("com.rssl.iccs.cardreports.asvypiska.AMEX.PDF_RUS");
		fb.setDescription("����������� ������ � �� �������� AMEX PDF RUS");
		fb.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("com.rssl.iccs.cardreports.asvypiska.AMEX.PDF_ENG");
		fb.setDescription("����������� ������ � �� �������� AMEX PDF ENG");
		fb.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("com.rssl.iccs.cardreports.asvypiska.AMEX.HTML_RUS");
		fb.setDescription("����������� ������ � �� �������� AMEX HTML RUS");
		fb.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("com.rssl.iccs.cardreports.asvypiska.AMEX.HTML_ENG");
		fb.setDescription("����������� ������ � �� �������� AMEX HTML ENG");
		fb.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		return formBuilder.build();
	}
}