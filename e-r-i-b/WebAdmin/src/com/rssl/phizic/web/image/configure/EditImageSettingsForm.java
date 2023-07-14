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
 * форма настройки ограничений на загружаемые картинки
 */

public class EditImageSettingsForm extends EditPropertiesFormBase
{

	@Override
	public Form getForm()
	{
		BigDecimal maxSize = ConfigFactory.getConfig(ImagesSettingsService.class).getMaxSize();

		FormBuilder formBuilder = new FormBuilder();

		formBuilder.addField(createField(ADVERTISING_PARAMETER_NAME, "Баннер на главной странице", maxSize));
		formBuilder.addField(createField(PROVIDER_LOGO_PARAMETER_NAME, "Логотип", maxSize));
		formBuilder.addField(createField(PROVIDER_PANEL_PARAMETER_NAME, "Иконка для панели быстрой оплаты", maxSize));
		formBuilder.addField(createField(PROVIDER_HELP_PARAMETER_NAME, "Графическая подсказка", maxSize));

		return formBuilder.build();
	}

	private static Field createField(String name, String description, BigDecimal maxSize)
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(name);
		fieldBuilder.setDescription(description);
		fieldBuilder.setType(IntType.INSTANCE.getName());
		fieldBuilder.addValidators(new NumericRangeValidator(BigDecimal.ZERO, maxSize, "Вы превысили максимально допустимый размер для " + description + ". Пожалуйста, укажите размер изображений не более " + maxSize + " Кб."));
		return fieldBuilder.build();
	}
}
