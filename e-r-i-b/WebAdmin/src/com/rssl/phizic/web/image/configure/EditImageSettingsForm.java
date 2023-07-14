package com.rssl.phizic.web.image.configure;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.IntType;
import com.rssl.common.forms.validators.NumericRangeValidator;
import com.rssl.phizic.business.image.configure.ImagesSettingsService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

import java.math.BigDecimal;

import static com.rssl.phizic.business.image.configure.ImagesSettingsService.*;

/**
 * @author akrenev
 * @ created 26.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * ����� ��������� ����������� �� ����������� ��������
 */

public class EditImageSettingsForm extends EditPropertiesFormBase
{

	@Override
	public Form getForm()
	{
		BigDecimal maxSize = ConfigFactory.getConfig(ImagesSettingsService.class).getMaxSize();

		FormBuilder formBuilder = new FormBuilder();

		formBuilder.addField(createField(ADVERTISING_PARAMETER_NAME, "������ �� ������� ��������", maxSize));
		formBuilder.addField(createField(PROVIDER_LOGO_PARAMETER_NAME, "�������", maxSize));
		formBuilder.addField(createField(PROVIDER_PANEL_PARAMETER_NAME, "������ ��� ������ ������� ������", maxSize));
		formBuilder.addField(createField(PROVIDER_HELP_PARAMETER_NAME, "����������� ���������", maxSize));

		return formBuilder.build();
	}

	private static Field createField(String name, String description, BigDecimal maxSize)
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(name);
		fieldBuilder.setDescription(description);
		fieldBuilder.setType(IntType.INSTANCE.getName());
		fieldBuilder.addValidators(new NumericRangeValidator(BigDecimal.ZERO, maxSize, "�� ��������� ����������� ���������� ������ ��� " + description + ". ����������, ������� ������ ����������� �� ����� " + maxSize + " ��."));
		return fieldBuilder.build();
	}
}
