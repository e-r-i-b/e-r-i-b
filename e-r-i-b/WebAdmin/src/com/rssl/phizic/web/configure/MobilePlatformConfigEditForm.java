package com.rssl.phizic.web.configure;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.image.configure.ImagesSettingsService;
import com.rssl.phizic.web.configure.exceptions.MobilePlatformFormValidator;
import com.rssl.phizic.web.image.ImageEditFormBase;
import com.rssl.phizic.web.validators.FileValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * Форма настроек mAPI в разрезе платформ: редактирование
 * @author Jatsky
 * @ created 31.07.13
 * @ $Author$
 * @ $Revision$
 */

public class MobilePlatformConfigEditForm extends ImageEditFormBase
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
		fieldBuilder.setName("version");
		fieldBuilder.setDescription("Минимальная поддерживаемая версия приложения");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator("\\d{1,100}$", "Версия должна содержать не более 100 цифр.")
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("errText");
		fieldBuilder.setDescription("Текст ошибки, отображаемый при несовместимости с поддерживаемой версией");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{1,500}", "Текст ошибки должен быть не более 500 символов.")
		);
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
		fieldBuilder.setDescription("Отображать в разделе Мобильные приложения");
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

	@Override public List<String> getImageIds()
	{
		return imageIds;
	}

	@Override protected FileValidator getImageFileValidator(String imageId)
	{
		if (ICON_IMAGE_ID.equals(imageId))
			return getDefaultImageFileSizeLimitValidator(ImagesSettingsService.MAX_SIZE_PARAMETER_NAME);

		return super.getImageFileValidator(imageId);
	}

	public void setImageID(Long imageID)
	{
		this.imageID = imageID;
	}

	public Long getImageID()
	{
		return imageID;
	}
}
