package com.rssl.phizic.web.offerNotification;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.advertising.AdvertisingArea;
import com.rssl.phizic.business.advertising.Constants;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.dictionaries.productRequirements.AccTypesRequirement;
import com.rssl.phizic.business.dictionaries.productRequirements.ProductRequirement;
import com.rssl.phizic.business.dictionaries.productRequirements.ProductRequirementType;
import com.rssl.phizic.business.dictionaries.url.validators.WhiteListUrlForTextValidator;
import com.rssl.phizic.business.dictionaries.url.validators.WhiteListUrlValidator;
import com.rssl.phizic.business.image.configure.ImagesSettingsService;
import com.rssl.phizic.business.personalOffer.PersonalOfferNotificationArea;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.config.ResourcePropertyReader;
import com.rssl.phizic.operations.advertising.EditAdvertisingBlockOperation;
import com.rssl.phizic.web.image.ImageEditFormBase;
import com.rssl.phizic.web.validators.CompositeFileValidator;
import com.rssl.phizic.web.validators.FileValidator;
import com.rssl.phizic.web.validators.ImageSizeValidator;
import com.rssl.phizic.web.validators.LengthWithoutTagFieldValidator;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author lukina
 * @ created 20.12.2013
 * @ $Author$
 * @ $Revision$
 */
public class EditOfferNotificationForm  extends ImageEditFormBase
{
	private static final String DATESTAMP_FORMAT = "dd.MM.yyyy";

	private static final String START_DATE_MESSAGE = "Введите корректную дату начала отображения в формате ДД.ММ.ГГГГ.";
	private static final String CANCEL_DATE_MESSAGE = "Введите корректную дату окончания отображения в формате ДД.ММ.ГГГГ.";

	private Long[] selectedIds = new Long[]{};
	private String[]  departments = new String[]{}; //список ТБ которые будут выведены на экран
	private Long[] selectedAccountTypes = new Long[]{};
	private List<PersonalOfferNotificationArea> areas; // список областей баннера

	private static final List<String> imageIds;

	static
	{
		imageIds = new ArrayList<String>();
		imageIds.add(EditAdvertisingBlockOperation.IMAGE_AREA_IMAGE_ID);
		for (int i = 0; i < Constants.NUMBER_OF_BUTTONS; i++)
			imageIds.add(EditAdvertisingBlockOperation.BUTTON_IMAGE_ID_PREFIX + i);
	}

	public static final Form EDIT_FORM  = EditOfferNotificationForm.createForm();
	private static final ImageSizeValidator IMAGE_SIZE_VALIDATOR = new ImageSizeValidator(
			ConfigFactory.getConfig(ImagesSettingsService.class).getBannerWidth(),
			ConfigFactory.getConfig(ImagesSettingsService.class).getBannerHeight(), CompareValidator.EQUAL, CompareValidator.LESS_EQUAL);

	public List<String> getImageIds()
	{
		return imageIds;
	}

	protected FileValidator getImageFileValidator(String imageId)
	{
		if (EditAdvertisingBlockOperation.IMAGE_AREA_IMAGE_ID.equals(imageId))
			return new CompositeFileValidator(getDefaultImageFileSizeLimitValidator(ImagesSettingsService.ADVERTISING_PARAMETER_NAME), IMAGE_SIZE_VALIDATOR);

		return getDefaultImageFileSizeLimitValidator(ImagesSettingsService.ADVERTISING_PARAMETER_NAME);
	}

	public Long[] getSelectedIds()
	{
		return selectedIds;
	}

	public void setSelectedIds(Long[] selectedIds)
	{
		this.selectedIds = selectedIds;
	}

	public String[] getDepartments()
	{
		return departments;
	}

	public void setDepartments(String[] departments)
	{
		this.departments = departments;
	}

	public List<PersonalOfferNotificationArea> getAreas()
	{
		return areas;
	}

	public void setAreas(List<PersonalOfferNotificationArea> areas)
	{
		this.areas = areas;
	}

	public Long[] getSelectedAccountTypes()
	{
		return selectedAccountTypes;
	}

	public void setSelectedAccountTypes(Long[] selectedAccountTypes)
	{
		this.selectedAccountTypes = selectedAccountTypes;
	}

	private static Form createForm()
	{
		DateParser dateParser = new DateParser(DATESTAMP_FORMAT);

		FormBuilder formBuilder = new FormBuilder();
		formBuilder.addFields(getImageFields(imageIds));
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Статус");
		fieldBuilder.setName("state");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators (
				new RequiredFieldValidator("Выберите статус.")
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Частота отображения");
		fieldBuilder.setName("frequencyDisplay");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators (
				new RequiredFieldValidator("Выберите частоту отображения.")
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Раз в ");
		fieldBuilder.setName("frequencyDisplayDay");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RequiredFieldValidator("Задайте частоту отображения в днях.")
		);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.frequencyDisplay == 'PERIOD'"));
		formBuilder.addField(fieldBuilder.build());


		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тип продукта");
		fieldBuilder.setName("productType");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators (
				new RequiredFieldValidator("Тип продукта.")
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Название");
		fieldBuilder.setName("name");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators (
				new RegexpFieldValidator("(?s).{0,40}", "Название уведомления должно быть не более 40 символов."),
				new RequiredFieldValidator("Введите название.")
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Период с");
		fieldBuilder.setName("periodFrom");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RequiredFieldValidator("Пожалуйста, укажите дату начала для отображения уведомления."),
				new DateFieldValidator(DATESTAMP_FORMAT, START_DATE_MESSAGE),
				new RegexpFieldValidator(".{0,10}", START_DATE_MESSAGE)
		);
		fieldBuilder.setParser(dateParser);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("string");
		fieldBuilder.setName("periodTo");
		fieldBuilder.setDescription("Период по");
		fieldBuilder.addValidators(
				new DateFieldValidator(DATESTAMP_FORMAT, CANCEL_DATE_MESSAGE),
				new RegexpFieldValidator(".{0,10}", CANCEL_DATE_MESSAGE)
		);
		fieldBuilder.setParser(dateParser);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Заголовок уведомления");
		fieldBuilder.setName("title");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators (
				new RegexpFieldValidator("(?s).{0,100}", "Заголовок уведомления должен быть не более 100 символов.")
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Текст уведомления");
		fieldBuilder.setName("text");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators (
				new RegexpFieldValidator("(?s).{0,400}", "Текст уведомления должен быть не более 400 символов."),
				new WhiteListUrlForTextValidator()
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Время отображения");
		fieldBuilder.setName("showTime");
		fieldBuilder.setType("string");
		NumericRangeValidator numericValidator= new NumericRangeValidator();
		numericValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "5");
		numericValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "120");
		numericValidator.setMessage("Время показа уведомления должно быть от 5 до 120 секунд.");
		fieldBuilder.addValidators (
				new RequiredFieldValidator("Укажите время отображения."),
				new RegexpFieldValidator("\\d{1,3}","Время показа уведомления должно быть от 5 до 120 секунд."),
				numericValidator
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Приоритет");
		fieldBuilder.setName("orderIndex");
		fieldBuilder.setType("string");
		numericValidator= new NumericRangeValidator();
		numericValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "1");
		numericValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "100");
		numericValidator.setMessage("Приоритет уведомления должен быть от 1 до 100.");
		fieldBuilder.addValidators (
				new RequiredFieldValidator("Укажите порядок отображения."),
				new RegexpFieldValidator("\\d{1,3}","Приоритет уведомления должен быть от 1 до 100."),
				numericValidator
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("URL уведомления");
		fieldBuilder.setName("imageLinkURL");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators (
				new WhiteListUrlValidator()
		);
		formBuilder.addField(fieldBuilder.build());

		LengthWithoutTagFieldValidator lengthValidator = new LengthWithoutTagFieldValidator();
		lengthValidator.setParameter("maxlength", new BigInteger("20"));
		lengthValidator.setMessage("Название кнопки должно быть не более 20 символов");
		for(int i = 0; i < Constants.NUMBER_OF_BUTTONS ; i++)
		{
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setDescription("Название кнопки");
			fieldBuilder.setName("buttonTitle"+i);
			fieldBuilder.setType("string");
			fieldBuilder.addValidators (lengthValidator);
			formBuilder.addField(fieldBuilder.build());

			fieldBuilder = new FieldBuilder();
			fieldBuilder.setDescription("URL");
			fieldBuilder.setName("buttonURL"+i);
			fieldBuilder.setType("string");
			fieldBuilder.addValidators (
					new RegexpFieldValidator(".{0,256}", "Адрес ссылки должен быть не более 256 символов."),
					new WhiteListUrlValidator()
			);
			formBuilder.addField(fieldBuilder.build());

			fieldBuilder = new FieldBuilder();
			fieldBuilder.setDescription("Отображение");
			fieldBuilder.setName("buttonShow"+i);
			fieldBuilder.setType("boolean");
			formBuilder.addField(fieldBuilder.build());

			fieldBuilder = new FieldBuilder();
			fieldBuilder.setDescription("Порядок отображения");
			fieldBuilder.setName("buttonOrder"+i);
			fieldBuilder.setType("string");
			formBuilder.addField(fieldBuilder.build());

			fieldBuilder = new FieldBuilder();
			fieldBuilder.setDescription("Название файла для кнопки № "+ i );
			fieldBuilder.setName("buttonfileName"+i);
			fieldBuilder.setType("string");
			formBuilder.addField(fieldBuilder.build());

		}

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Порядок отображения области текста");
		fieldBuilder.setName("textAreaOrder");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Порядок отображения области с картинкой");
		fieldBuilder.setName("imageAreaOrder");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Порядок отображения области кнопок");
		fieldBuilder.setName("buttonsAreaOrder");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Порядок отображения области заголовка");
		fieldBuilder.setName("titleAreaOrder");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());


		CompareValidator compareValidator = new CompareValidator();
		compareValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS_EQUAL);
		compareValidator.setBinding(CompareValidator.FIELD_O1, "periodFrom");
		compareValidator.setBinding(CompareValidator.FIELD_O2, "periodTo");
		compareValidator.setMessage("Дата окончания должна быть больше даты начала периода публикации. Пожалуйста, укажите другие даты.");
		formBuilder.setFormValidators(compareValidator);

		return formBuilder.build();
	}
}
