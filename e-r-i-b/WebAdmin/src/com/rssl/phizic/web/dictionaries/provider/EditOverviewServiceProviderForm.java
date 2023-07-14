package com.rssl.phizic.web.dictionaries.provider;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.types.*;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.departments.froms.validators.DepartmentValidator;
import com.rssl.phizic.business.dictionaries.validators.ServiceProviderINNValidator;
import com.rssl.phizic.business.ermb.provider.ServiceProviderSmsAlias;
import com.rssl.phizic.business.ext.sbrf.mobilebank.validator.CardTransferAdapterRequiredValidator;
import com.rssl.phizic.business.ext.sbrf.mobilebank.validator.UniqueMobileBankCodeValidator;
import com.rssl.phizic.business.image.configure.ImagesSettingsService;
import com.rssl.phizic.business.limits.GroupRisk;
import com.rssl.phizic.business.payments.forms.validators.AccountKeyValidator;
import com.rssl.phizic.common.types.VersionNumber;
import com.rssl.phizic.common.types.documents.ServiceProvidersConstants;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.forms.validators.ExistsBankByBICValidator;
import com.rssl.phizic.operations.dictionaries.provider.EditServiceProvidersOperation;
import com.rssl.phizic.utils.mobile.MobileAPIVersions;
import com.rssl.phizic.web.validators.CompositeFileValidator;
import com.rssl.phizic.web.validators.FileValidator;
import com.rssl.phizic.web.validators.ImageSizeValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: khudyakov
 * Date: 26.02.2010
 * Time: 14:28:30
 */
public class EditOverviewServiceProviderForm extends EditServiceProviderFormBase
{
	public static final String USE_ESB_FIELD_NAME = "useESB";

	private static final List<String> imageIds;
	private List<ServiceProviderSmsAlias> smsAliases;
	private String smsAlias;

	private static final ImageSizeValidator LOGO_IMAGE_SIZE_VALIDATOR;
	private static final ImageSizeValidator LOGO_IMAGE_SIZE_VALIDATOR_MIN;
	private static final List<VersionNumber> MAPI_VERSIONS = new ArrayList<VersionNumber>();

	static
	{
		imageIds = new ArrayList<String>();
		imageIds.add(EditServiceProvidersOperation.ICON_IMAGE_ID);
		imageIds.add(EditServiceProvidersOperation.HELP_IMAGE_ID);

		PropertyReader propertyReader = ConfigFactory.getReaderByFileName("iccs.properties");
		int fileWidth = Integer.parseInt(propertyReader.getProperty("com.rssl.iccs.provider.icon.width"));
		int fileheight = Integer.parseInt(propertyReader.getProperty("com.rssl.iccs.provider.icon.height"));
		LOGO_IMAGE_SIZE_VALIDATOR = new ImageSizeValidator(fileWidth, fileheight, CompareValidator.LESS_EQUAL, CompareValidator.LESS_EQUAL);
		int fileWidthMin = Integer.parseInt(propertyReader.getProperty("com.rssl.iccs.provider.icon.width.min"));
		int fileHeightMin = Integer.parseInt(propertyReader.getProperty("com.rssl.iccs.provider.icon.height.min"));
		LOGO_IMAGE_SIZE_VALIDATOR_MIN = new ImageSizeValidator(fileWidthMin, fileHeightMin, CompareValidator.GREATE_EQUAL, CompareValidator.GREATE_EQUAL);

		MAPI_VERSIONS.add(MobileAPIVersions.V5_00);
		MAPI_VERSIONS.add(MobileAPIVersions.V5_10);
		MAPI_VERSIONS.add(MobileAPIVersions.V5_20);
	}

	public static final Form OVERVIEW_FORM = createOverviewForm();
	public static final Form SETUP_GROUP_RISK_FORM = createSetupGroupRiskForm();
	public static final Form SMS_ALIAS_FORM = createSmsAliasForm();

	private List<GroupRisk> groupsRisk;

	protected FileValidator getImageFileValidator(String imageId)
	{
		if (EditServiceProvidersOperation.ICON_IMAGE_ID.equals(imageId))
			return new CompositeFileValidator(getDefaultImageFileSizeLimitValidator(ImagesSettingsService.PROVIDER_LOGO_PARAMETER_NAME), LOGO_IMAGE_SIZE_VALIDATOR, LOGO_IMAGE_SIZE_VALIDATOR_MIN);
		else if (EditServiceProvidersOperation.HELP_IMAGE_ID.equals(imageId))
			return getDefaultImageFileSizeLimitValidator(ImagesSettingsService.PROVIDER_HELP_PARAMETER_NAME);

		return super.getImageFileValidator(imageId);
	}

	public List<String> getImageIds()
	{
		return imageIds;
	}

	public List<GroupRisk> getGroupsRisk()
	{
		return groupsRisk;
	}

	public void setGroupsRisk(List<GroupRisk> groupsRisk)
	{
		this.groupsRisk = groupsRisk;
	}

	public String getSmsAlias()
	{
		return smsAlias;
	}

	public void setSmsAlias(String smsAlias)
	{
		this.smsAlias = smsAlias;
	}

	public List<ServiceProviderSmsAlias> getSmsAliases()
	{
		return smsAliases;
	}

	public void setSmsAliases(List<ServiceProviderSmsAlias> smsAliases)
	{
		this.smsAliases = smsAliases;
	}

	private static Form createOverviewForm()
	{
		Expression billingProviderExpression = new RhinoExpression("form.providerType == 'BILLING'");      // биллинговый поставщик
		Expression taxationProviderExpression = new RhinoExpression("form.providerType == 'TAXATION'");    // налоговый поставщик
		Expression externalProviderExpression = new RhinoExpression("form.providerType == 'EXTERNAL'");    // внешний поставщик
		Expression taxationOrBillingProviderExpression = new RhinoExpression("form.providerType == 'TAXATION' || form.providerType == 'BILLING'"); // налоговый или биллинговый поставщик
		Expression externalOrBillingProviderExpression = new RhinoExpression("form.providerType == 'BILLING'  || form.providerType == 'EXTERNAL'"); // внешний или биллинговый поставщик

		FormBuilder fb = new FormBuilder();
		fb.addFields(getImageFields(imageIds));
		FieldBuilder fieldBuilder;

		// Группа риска
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Группа риска");
		fieldBuilder.setName("groupRiskId");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		//Тип поставщика услуг
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тип поставщика услуг");
		fieldBuilder.setName("providerType");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());

		//признак поставщика
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Признак поставщика услуг");
		fieldBuilder.setName("subType");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("(mobile|wallet)"));
		fb.addField(fieldBuilder.build());

		// id поставщика нужен для проверки уникальности кода МБ
		// Поскольку LongType (либо IdType) нету,
		// приходится пользоваться StringType...
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("id");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Наименование");
		fieldBuilder.setName("name");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,160}", "Наименование должено быть не более 160 символов."));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Юридическое наименование поставщика услуг");
		fieldBuilder.setName("legalName");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator("^.{1,250}$", "Поле Юридическое наименование поставщика услуг не должно превышать 250 символов.")
			);
		fieldBuilder.setEnabledExpression(externalOrBillingProviderExpression);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Псевдонимы поставщика услуг");
		fieldBuilder.setName("alias");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fieldBuilder.setEnabledExpression(externalOrBillingProviderExpression);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Приоритет при сортировке");
		fieldBuilder.setName("sortPriority");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator("^\\d{1,2}|(100)$", "Поле Приоритет по сортировке может принимать значение от 0 до 100.")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Настройка доступности оплаты услуг для интернет-банка");
		fieldBuilder.setName("availablePaymentsForInternetBank");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Настройка видимости услуг для интернет-банка");
		fieldBuilder.setName("visiblePaymentsForInternetBank");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Настройка доступности оплаты услуг для mApi");
		fieldBuilder.setName("availablePaymentsForMApi");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Настройка видимости услуг для mApi");
		fieldBuilder.setName("visiblePaymentsForMApi");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Настройка доступности оплаты услуг для atmApi");
		fieldBuilder.setName("availablePaymentsForAtmApi");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Настройка видимости услуг для atmApi");
		fieldBuilder.setName("visiblePaymentsForAtmApi");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Настройка доступности оплаты услуг для socialApi");
		fieldBuilder.setName("availablePaymentsForSocialApi");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Настройка видимости услуг для socialApi");
		fieldBuilder.setName("visiblePaymentsForSocialApi");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Настройка доступности оплаты услуг для ЕРМБ");
		fieldBuilder.setName("availablePaymentsForErmb");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Запрос реквизитов в on-line");
		fieldBuilder.setName("propsOnline");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fieldBuilder.setEnabledExpression(billingProviderExpression);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Заполнение банковских реквизитов");
		fieldBuilder.setName("bankDetails");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fieldBuilder.setEnabledExpression(taxationOrBillingProviderExpression);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("ИНН");
		fieldBuilder.setName("inn");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		//Валидатор для полей реквизитов банка. Введен для корректной обработки ситуации когда поля активны но не нужны.
		FieldValidator reqFieldValidator = new RequiredFieldValidator();
		reqFieldValidator.setEnabledExpression(new RhinoExpression("form.bankDetails == true"));

		FieldValidator reqINNFieldValidator = new RequiredFieldValidator();
		reqINNFieldValidator.setEnabledExpression(new RhinoExpression("form.bankDetails == true || form.providerType == 'EXTERNAL'"));

		fieldBuilder.addValidators(
				reqINNFieldValidator,
				new RegexpFieldValidator("\\d{10}|\\d{12}", "Поле ИНН должно состоять из 10 или 12 цифр.")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("КПП");
		fieldBuilder.setName("kpp");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{9}", "Поле КПП должно состоять из 9 цифр.")
		);

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Расчетный счет");
		fieldBuilder.setName("account");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				reqFieldValidator,
				new RegexpFieldValidator("\\d{20,25}", "Поле Расчетный счет должно состоять из 20-25 цифр.")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("БИК банка");
		fieldBuilder.setName("bankCode");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(reqFieldValidator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Наименование банка");
		fieldBuilder.setName("bankName");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(reqFieldValidator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Корсчет банка");
		fieldBuilder.setName("corAccount");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Код поставщика услуг СБОЛ");
		fieldBuilder.setName("codeRecipientSBOL");
		fieldBuilder.setType(StringType.INSTANCE.getName());

		LengthFieldValidator lengthValidator = new LengthFieldValidator();
		lengthValidator.setParameter("maxlength", "25");
		lengthValidator.setParameter("minlength", "1");
		lengthValidator.setMessage("Краткое наименование налога должно содержать не более 25 символов.");

		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				lengthValidator
			);
		fieldBuilder.setEnabledExpression(externalOrBillingProviderExpression);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Код поставщика услуг");
		fieldBuilder.setName("providerCode");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator("^.{1,20}$", "Поле Код поставщика услуг не должно превышать 20 символов.")
			);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Наименование биллинговой системы");
		fieldBuilder.setName("billingId");
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fieldBuilder.setEnabledExpression(externalOrBillingProviderExpression);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Наименование биллинговой системы");
		fieldBuilder.setName("billingName");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fieldBuilder.setEnabledExpression(externalOrBillingProviderExpression);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Название услуги в биллинговой системе");
		fieldBuilder.setName("nameService");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{1,150}", "Поле Название услуги в биллинговой системе не должно превышать 150 символов.")
			);
		fieldBuilder.setEnabledExpression(externalOrBillingProviderExpression);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Код услуги");
		fieldBuilder.setName("serviceCode");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fieldBuilder.setEnabledExpression(externalOrBillingProviderExpression);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Поддерживает задолженность");
		fieldBuilder.setName("debtSupported");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fieldBuilder.setEnabledExpression(billingProviderExpression);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Признак федерального поставщика услуг");
		fieldBuilder.setName("federal");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fieldBuilder.setEnabledExpression(billingProviderExpression);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тип счета при оплате");
		fieldBuilder.setName("accountType");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fieldBuilder.setEnabledExpression(externalOrBillingProviderExpression);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Комментарий для клиентов");
		fieldBuilder.setName("comment");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("(?s).{0,255}", "Поле Комментарий для клиентов не должно превышать 255 символов."));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Подсказка по поставщику услуг");
		fieldBuilder.setName("tip");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("(?s).{0,255}", "Поле Подсказка по поставщику услуг не должно превышать 255 символов."));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Сообщение о комиссии");
		fieldBuilder.setName("commissionMessage");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("(?s).{0,250}", "Поле Сообщение о комиссии не должно превышать 250 символов."));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Наименование поставщика, выводимое в чеке");
		fieldBuilder.setName("nameOnBill");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(externalOrBillingProviderExpression);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Подразделение банка");
		fieldBuilder.setName("departmentName");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Идентифткатор подразделение банка");
		fieldBuilder.setName("departmentId");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator() ,new DepartmentValidator());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Счет для расчетов с поставщиками");
		fieldBuilder.setName("transitAccount");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("\\d{20,25}","Поле Счет для расчетов с поставщиками должно состоять из 20-25 цифр"));
		fieldBuilder.setEnabledExpression(taxationOrBillingProviderExpression);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Назначение платежа");
		fieldBuilder.setName("ground");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fieldBuilder.setEnabledExpression(billingProviderExpression);
		fb.addField(fieldBuilder.build());

		Expression expression = new RhinoExpression("form.ground");

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Разделитель реквизитов");
		fieldBuilder.setName("separator1");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		RequiredFieldValidator validator = new RequiredFieldValidator();
		validator.setEnabledExpression(expression);
		fieldBuilder.addValidators(validator, new RegexpFieldValidator(".{0,1}$", "Разделитель реквизитов должен состоять из одного символа."));
		fieldBuilder.setEnabledExpression(billingProviderExpression);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Разделитель значений реквизитов");
		fieldBuilder.setName("separator2");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		validator = new RequiredFieldValidator();
		validator.setEnabledExpression(expression);
		fieldBuilder.addValidators(validator, new RegexpFieldValidator(".{0,1}$", "Разделитель значений реквизитов должен состоять из одного символа."));
		fieldBuilder.setEnabledExpression(billingProviderExpression);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Номер из eng4000.nsi");
		fieldBuilder.setName("eng4000nsi");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(taxationOrBillingProviderExpression);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Подсказка для пиктограммы");
		fieldBuilder.setName("imageHelp");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator(".{0,25}", "Подсказка для пиктограммы должена быть не более 25 символов."));
		fieldBuilder.setEnabledExpression(externalOrBillingProviderExpression);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Название подсказки");
		fieldBuilder.setName("imageHelpTitle");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator(".{0,100}", "Название подсказки должено быть не более 100 символов."));
		fieldBuilder.setEnabledExpression(externalOrBillingProviderExpression);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Показывать в популярных платежах клиентам");
		fieldBuilder.setName("popular");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fieldBuilder.setEnabledExpression(billingProviderExpression);
		fb.addField(fieldBuilder.build());

		AccountKeyValidator accountKeyValidator = new AccountKeyValidator();
		accountKeyValidator.setBinding(AccountKeyValidator.FIELD_BIC, "bankCode");
		accountKeyValidator.setBinding(AccountKeyValidator.FIELD_RECEIVER_ACCOUNT, "account");
		accountKeyValidator.setMessage("Неверный ключ счета получателя.");
		fb.addFormValidators(accountKeyValidator);

		ServiceProviderINNValidator innValidator = new ServiceProviderINNValidator();
		innValidator.setBinding(ServiceProviderINNValidator.PROVIDER_ID_FIELD, "id");
		innValidator.setBinding(ServiceProviderINNValidator.PROVIDER_TYPE_FIELD, "providerType");
		innValidator.setBinding(ServiceProviderINNValidator.PROVIDER_INN_FIELD, "inn");
		innValidator.setMessage("В системе уже существует налоговый поставщик с таким ИНН. Пожалуйста, проверьте ИНН поставщика.");
		fb.addFormValidators(innValidator);

		ExistsBankByBICValidator existsBankByBICValidator = new ExistsBankByBICValidator();
		existsBankByBICValidator.setBinding(ExistsBankByBICValidator.BIC, "bankCode");
		existsBankByBICValidator.setBinding(ExistsBankByBICValidator.NEED_BANK_REQUISITE, "bankDetails");
		existsBankByBICValidator.setMessage("Реквизиты данного поставщика отсутствуют в справочнике банков. Пожалуйста, обновите справочник банков.");
		fb.addFormValidators(existsBankByBICValidator);

		// Мобильный банк
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Настройка доступности для создания СМС-шаблонов");
		fieldBuilder.setName("mobilebank");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(billingProviderExpression);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Код поставщика в Мобильном Банке");
		fieldBuilder.setName("mobilebankCode");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(billingProviderExpression);

		RequiredFieldValidator requiredMBCodeValidator = new RequiredFieldValidator();
		requiredMBCodeValidator.setEnabledExpression(new RhinoExpression("form.mobilebank == true"));
		requiredMBCodeValidator.setMessage("Укажите код поставщика в Мобильном Банке");

		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{1,32}", "Длина кода поставщика в Мобильном Банке не должна превышать 32 символа"),
				new RegexpFieldValidator("[a-zA-Z0-9 \\-]*", "Код поставщика в Мобильном Банке должен содержать только буквы латинского алфавита, цифры и пробелы."),
				requiredMBCodeValidator
		);
		fieldBuilder.setEnabledExpression(billingProviderExpression);
		fb.addField(fieldBuilder.build());

		Expression mbExpression = new RhinoExpression(
				"form.providerType == 'BILLING' && form.mobilebank == true");

		UniqueMobileBankCodeValidator uniqMBCodeValidator = new UniqueMobileBankCodeValidator("Введённый код поставщика в Мобильном Банке уже занят другим поставщиком");
		uniqMBCodeValidator.setBinding(UniqueMobileBankCodeValidator.FIELD_PROVIDER_ID, "id");
		uniqMBCodeValidator.setBinding(UniqueMobileBankCodeValidator.FIELD_MOBILEBANK_CODE, "mobilebankCode");
		uniqMBCodeValidator.setEnabledExpression(mbExpression);

		fb.addFormValidators(uniqMBCodeValidator);

		//Доступность в мобильной версии
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Активен начиная с версии API");
		fieldBuilder.setName("versionAPI");
		fieldBuilder.setType(IntType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(billingProviderExpression);
		fb.addField(fieldBuilder.build());

		//Тип оплаты
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тип оплаты");
		fieldBuilder.setName("payType");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fieldBuilder.setEnabledExpression(taxationProviderExpression);
		fb.addField(fieldBuilder.build());

		//Действия после оплаты
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Действия после оплаты");
		fieldBuilder.setName("backUrlAction");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fieldBuilder.setEnabledExpression(externalProviderExpression);
		fb.addField(fieldBuilder.build());

		//Проверять ли заказ перед оплатой
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Проверка заказа перед оплатой");
		fieldBuilder.setName("checkOrder");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fieldBuilder.setEnabledExpression(externalProviderExpression);
		fb.addField(fieldBuilder.build());

		//Полноформатный платеж
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Полноформатный платеж");
		fieldBuilder.setName("fullPayment");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fieldBuilder.setEnabledExpression(taxationProviderExpression);
		fb.addField(fieldBuilder.build());

		//Телефон провайдера для обращений клиентов
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Телефон для обращений клиентов");
		fieldBuilder.setName("phoneNumber");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator(".{1,15}","Поле \"Телефон для обращений клиентов\" не должно быть больше 15 символов"));
		fb.addField(fieldBuilder.build());

		// URL для обратной связи
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("URL для обратной связи");
		fieldBuilder.setName("url");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator(".{1,256}","Поле \"URL для обратной связи\" не должно быть больше 256 символов"));
		fieldBuilder.setEnabledExpression(externalProviderExpression);
		fb.addField(fieldBuilder.build());

		// Работа через КСШ
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Работа через КСШ");
		fieldBuilder.setName(USE_ESB_FIELD_NAME);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(externalProviderExpression);
		fb.addField(fieldBuilder.build());

		//URL перехода после оплаты
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("URL перехода после оплаты");
		fieldBuilder.setName("providerBackUrl");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator(".{1,1000}","Поле \"URL для обратной связи\" не должно быть больше 10000 символов"));
		fieldBuilder.setEnabledExpression(externalProviderExpression);
		fb.addField(fieldBuilder.build());

		// Поставщик для оплаты платёжного поручения
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Поставщик для оплаты платёжного поручения");
		fieldBuilder.setName("formName");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fieldBuilder.setEnabledExpression(externalProviderExpression);
		fb.addField(fieldBuilder.build());

		// Передавать информацию о карте списания
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Передавать в интернет-магазин информацию о карте списания");
		fieldBuilder.setName("sendChargeOffInfo");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(externalProviderExpression);
		fb.addField(fieldBuilder.build());

		// Доступна ли проеврка по МБ для интернет-магазина.
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Доступна ли проеврка по МБ для интернет-магазина.");
		fieldBuilder.setName("availableMBCheck");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(externalProviderExpression);
		fb.addField(fieldBuilder.build());

		// Доступен ли Mobile Checkout для интернет-магазина.
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Доступен ли Mobile Checkout для интернет-магазина.");
		fieldBuilder.setName("availableMobileCheckout");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(externalProviderExpression);
		fb.addField(fieldBuilder.build());

		// Является ли поставщик фасилитатором
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Является ли поставщик фасилитатором.");
		fieldBuilder.setName("isFasilitator");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(externalProviderExpression);
		fb.addField(fieldBuilder.build());

		// Доступность фасилитатора в E-invoicing
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Доступность фасилитатора в E-invoicing.");
		fieldBuilder.setName("eInvoicingProperty");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(externalProviderExpression);
		fb.addField(fieldBuilder.build());

		// Доступность фасилитатора в Mobile Checkout
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Доступность фасилитатора в Mobile Checkout.");
		fieldBuilder.setName("mobileCheckoutProperty");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(externalProviderExpression);
		fb.addField(fieldBuilder.build());

		// Доступность фасилитатора для проверки клиента через МБ
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Доступность фасилитатора для проверки клиента через МБ.");
		fieldBuilder.setName("mbCheckProperty");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(externalProviderExpression);
		fb.addField(fieldBuilder.build());

		// Поддерживает ли создание шаблонов
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Доступен для создания шаблонов");
		fieldBuilder.setName("templateSupported");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(billingProviderExpression);
		fb.addField(fieldBuilder.build());

		// Планируется к отключению
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Планируется к отлючению");
		fieldBuilder.setName("planingForDeactivate");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(billingProviderExpression);
		fb.addField(fieldBuilder.build());

		// Поддерживает ли редактирование платежа
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Доступно редактирование платежа");
		fieldBuilder.setName("editPaymentSupported");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(billingProviderExpression);
		fb.addField(fieldBuilder.build());

		//Для создания нестандартных SMS-шаблонов

		//Валидатор
		MultiFieldsValidator multiFieldsValidator = new MultiFieldsValidatorBase()
		{
			public boolean validate(Map values) throws TemporalDocumentException
			{
				Boolean standart = (Boolean)retrieveFieldValue("standart",values);
				if(standart) return true;
				FieldValidator validator = new RequiredFieldValidator();
				if(!validator.validate((String)retrieveFieldValue("format",values)))
					return false;
				if(!validator.validate((String)retrieveFieldValue("example",values)))
					return false;
				return true;
			}
		};
		multiFieldsValidator.setMessage("Поля: формат, подсказка и пример СМС-шаблона - обязательны для нестандартных СМС-шаблонов!");

		multiFieldsValidator.setBinding("standart","standartSmsTemplate");
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Является ли СМС-шаблон поставщика стандартным.");
		fieldBuilder.setName("standartSmsTemplate");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		//Формат шаблона
		multiFieldsValidator.setBinding("format","format");
		fieldBuilder =  new FieldBuilder();
		fieldBuilder.setDescription("Формат нестандартного СМС-шаблона.");
		fieldBuilder.setName("format");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator(".{1,255}","Формат шаблона не должен содержать более 255 символов"));
		fb.addField(fieldBuilder.build());

		//Пример
		multiFieldsValidator.setBinding("example","example");
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Пример нестандартного СМС-шаблона");
		fieldBuilder.setName("example");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator(".{1,255}","Пример шаблона не должен содержать более 255 символов"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Использовать код (QR, BAR) в мобильном приложении");
		fieldBuilder.setName("isBarSupported");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Доступен ли поставщик при неактивной внешней системе");
		fieldBuilder.setName("isOfflineAvailable");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Запрещена ли оплата с кредитных карт");
		fieldBuilder.setName(ServiceProvidersConstants.PROVIDER_IS_CREDIT_CARD_ACCESSIBLE);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fb.addFormValidators(multiFieldsValidator);

		return fb.build();
	}

	private static Form createSetupGroupRiskForm()
	{
		FormBuilder fb = new FormBuilder();
		// Группа риска
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Группа риска");
		fieldBuilder.setName("groupRiskId");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		return fb.build();
	}

	private static Form createSmsAliasForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Значение по умолчанию");
		fieldBuilder.setName("smsAlias");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RegexpFieldValidator("([[0-9a-zA-Zа-яА-ЯЁё]]{1,20}\\;)*([[0-9a-zA-Zа-яА-ЯЁё]]{1,20}\\s)*([0-9a-zA-Zа-яА-ЯЁё]{1,20}){1}", "Значение по умолчанию не должно превышать 20 символов, допустимый разделители: ';' или пробел.")
		);
		fb.addField(fieldBuilder.build());

		return fb.build();
	}

	public List<VersionNumber> getMapiVersions()
	{
		return MAPI_VERSIONS;
	}
}
