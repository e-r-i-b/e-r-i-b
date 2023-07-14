package com.rssl.phizic.web.configure;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.configure.exceptions.MobilePlatformFormValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * Форма настроек mAPI в разрезе платформ: редактирование
 * @author sergunin
 * @ created 21.08.14
 * @ $Author$
 * @ $Revision$
 */

public class SocialPlatformConfigEditForm extends MobilePlatformConfigEditForm
{
	private static final List<String> imageIds;
	private static final String ICON_IMAGE_ID = "Icon";
	private Long imageID;

	static
	{
		imageIds = new ArrayList<String>();
		imageIds.add(ICON_IMAGE_ID);
	}

	public static final Form EDIT_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		formBuilder.addFields(getImageFields(imageIds));
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("ИД");
		fieldBuilder.setName("id");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("platformName");
		fieldBuilder.setDescription("Название платформы");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{1,100}", "Название должно быть не более 100 символов.")
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("platformId");
		fieldBuilder.setDescription("ID платформы, присылаемый мобильным приложением");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{1,10}", "ID должно быть не более 10 символов.")
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("social");
		fieldBuilder.setDescription("Тип платформы");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("scheme");
		fieldBuilder.setDescription("Режим использования приложения");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("platform_icon");
		fieldBuilder.setDescription("Иконка платформы");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("downloadFromSBRF");
		fieldBuilder.setDescription("признак загрузки с сайта сбербанка");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("bankURL");
		fieldBuilder.setDescription("URL на сайте банка");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "URL на сайте банка должен быть не более 100 символов.")
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("externalURL");
		fieldBuilder.setDescription("URL на сайте разработчика");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "URL на сайте разработчика должен быть не более 100 символов.")
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("isQR");
		fieldBuilder.setDescription("Использовать QR-код");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("isShownInApps");
		fieldBuilder.setDescription("Отображать в разделе Социальные приложения");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("QRName");
		fieldBuilder.setDescription("Название файла с изображением QR-кода");
		formBuilder.addField(fieldBuilder.build());
		formBuilder.addFormValidators(new MobilePlatformFormValidator());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("isPasswordConfirm");
		fieldBuilder.setDescription("Подтверждение одноразовым паролем");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("isShowSbAttribute");
		fieldBuilder.setDescription("Отображать признак клиента Сбербанка");
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

}
