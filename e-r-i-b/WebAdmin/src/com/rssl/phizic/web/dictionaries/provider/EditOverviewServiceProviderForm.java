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
		Expression billingProviderExpression = new RhinoExpression("form.providerType == 'BILLING'");      // ����������� ���������
		Expression taxationProviderExpression = new RhinoExpression("form.providerType == 'TAXATION'");    // ��������� ���������
		Expression externalProviderExpression = new RhinoExpression("form.providerType == 'EXTERNAL'");    // ������� ���������
		Expression taxationOrBillingProviderExpression = new RhinoExpression("form.providerType == 'TAXATION' || form.providerType == 'BILLING'"); // ��������� ��� ����������� ���������
		Expression externalOrBillingProviderExpression = new RhinoExpression("form.providerType == 'BILLING'  || form.providerType == 'EXTERNAL'"); // ������� ��� ����������� ���������

		FormBuilder fb = new FormBuilder();
		fb.addFields(getImageFields(imageIds));
		FieldBuilder fieldBuilder;

		// ������ �����
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������ �����");
		fieldBuilder.setName("groupRiskId");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		//��� ���������� �����
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� ���������� �����");
		fieldBuilder.setName("providerType");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());

		//������� ����������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������� ���������� �����");
		fieldBuilder.setName("subType");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("(mobile|wallet)"));
		fb.addField(fieldBuilder.build());

		// id ���������� ����� ��� �������� ������������ ���� ��
		// ��������� LongType (���� IdType) ����,
		// ���������� ������������ StringType...
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("id");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������");
		fieldBuilder.setName("name");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,160}", "������������ ������� ���� �� ����� 160 ��������."));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����������� ������������ ���������� �����");
		fieldBuilder.setName("legalName");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator("^.{1,250}$", "���� ����������� ������������ ���������� ����� �� ������ ��������� 250 ��������.")
			);
		fieldBuilder.setEnabledExpression(externalOrBillingProviderExpression);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���������� ���������� �����");
		fieldBuilder.setName("alias");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fieldBuilder.setEnabledExpression(externalOrBillingProviderExpression);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������� ��� ����������");
		fieldBuilder.setName("sortPriority");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator("^\\d{1,2}|(100)$", "���� ��������� �� ���������� ����� ��������� �������� �� 0 �� 100.")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������� ����������� ������ ����� ��� ��������-�����");
		fieldBuilder.setName("availablePaymentsForInternetBank");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������� ��������� ����� ��� ��������-�����");
		fieldBuilder.setName("visiblePaymentsForInternetBank");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������� ����������� ������ ����� ��� mApi");
		fieldBuilder.setName("availablePaymentsForMApi");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������� ��������� ����� ��� mApi");
		fieldBuilder.setName("visiblePaymentsForMApi");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������� ����������� ������ ����� ��� atmApi");
		fieldBuilder.setName("availablePaymentsForAtmApi");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������� ��������� ����� ��� atmApi");
		fieldBuilder.setName("visiblePaymentsForAtmApi");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������� ����������� ������ ����� ��� socialApi");
		fieldBuilder.setName("availablePaymentsForSocialApi");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������� ��������� ����� ��� socialApi");
		fieldBuilder.setName("visiblePaymentsForSocialApi");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������� ����������� ������ ����� ��� ����");
		fieldBuilder.setName("availablePaymentsForErmb");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������ ���������� � on-line");
		fieldBuilder.setName("propsOnline");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fieldBuilder.setEnabledExpression(billingProviderExpression);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���������� ���������� ����������");
		fieldBuilder.setName("bankDetails");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fieldBuilder.setEnabledExpression(taxationOrBillingProviderExpression);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���");
		fieldBuilder.setName("inn");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		//��������� ��� ����� ���������� �����. ������ ��� ���������� ��������� �������� ����� ���� ������� �� �� �����.
		FieldValidator reqFieldValidator = new RequiredFieldValidator();
		reqFieldValidator.setEnabledExpression(new RhinoExpression("form.bankDetails == true"));

		FieldValidator reqINNFieldValidator = new RequiredFieldValidator();
		reqINNFieldValidator.setEnabledExpression(new RhinoExpression("form.bankDetails == true || form.providerType == 'EXTERNAL'"));

		fieldBuilder.addValidators(
				reqINNFieldValidator,
				new RegexpFieldValidator("\\d{10}|\\d{12}", "���� ��� ������ �������� �� 10 ��� 12 ����.")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���");
		fieldBuilder.setName("kpp");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{9}", "���� ��� ������ �������� �� 9 ����.")
		);

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������� ����");
		fieldBuilder.setName("account");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				reqFieldValidator,
				new RegexpFieldValidator("\\d{20,25}", "���� ��������� ���� ������ �������� �� 20-25 ����.")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� �����");
		fieldBuilder.setName("bankCode");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(reqFieldValidator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������ �����");
		fieldBuilder.setName("bankName");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(reqFieldValidator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������� �����");
		fieldBuilder.setName("corAccount");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� ���������� ����� ����");
		fieldBuilder.setName("codeRecipientSBOL");
		fieldBuilder.setType(StringType.INSTANCE.getName());

		LengthFieldValidator lengthValidator = new LengthFieldValidator();
		lengthValidator.setParameter("maxlength", "25");
		lengthValidator.setParameter("minlength", "1");
		lengthValidator.setMessage("������� ������������ ������ ������ ��������� �� ����� 25 ��������.");

		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				lengthValidator
			);
		fieldBuilder.setEnabledExpression(externalOrBillingProviderExpression);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� ���������� �����");
		fieldBuilder.setName("providerCode");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator("^.{1,20}$", "���� ��� ���������� ����� �� ������ ��������� 20 ��������.")
			);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������ ����������� �������");
		fieldBuilder.setName("billingId");
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fieldBuilder.setEnabledExpression(externalOrBillingProviderExpression);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������ ����������� �������");
		fieldBuilder.setName("billingName");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fieldBuilder.setEnabledExpression(externalOrBillingProviderExpression);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������� ������ � ����������� �������");
		fieldBuilder.setName("nameService");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{1,150}", "���� �������� ������ � ����������� ������� �� ������ ��������� 150 ��������.")
			);
		fieldBuilder.setEnabledExpression(externalOrBillingProviderExpression);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� ������");
		fieldBuilder.setName("serviceCode");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fieldBuilder.setEnabledExpression(externalOrBillingProviderExpression);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������ �������������");
		fieldBuilder.setName("debtSupported");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fieldBuilder.setEnabledExpression(billingProviderExpression);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������� ������������ ���������� �����");
		fieldBuilder.setName("federal");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fieldBuilder.setEnabledExpression(billingProviderExpression);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� ����� ��� ������");
		fieldBuilder.setName("accountType");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fieldBuilder.setEnabledExpression(externalOrBillingProviderExpression);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����������� ��� ��������");
		fieldBuilder.setName("comment");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("(?s).{0,255}", "���� ����������� ��� �������� �� ������ ��������� 255 ��������."));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������� �� ���������� �����");
		fieldBuilder.setName("tip");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("(?s).{0,255}", "���� ��������� �� ���������� ����� �� ������ ��������� 255 ��������."));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������� � ��������");
		fieldBuilder.setName("commissionMessage");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("(?s).{0,250}", "���� ��������� � �������� �� ������ ��������� 250 ��������."));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������ ����������, ��������� � ����");
		fieldBuilder.setName("nameOnBill");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(externalOrBillingProviderExpression);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������� �����");
		fieldBuilder.setName("departmentName");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������� ������������� �����");
		fieldBuilder.setName("departmentId");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator() ,new DepartmentValidator());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���� ��� �������� � ������������");
		fieldBuilder.setName("transitAccount");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("\\d{20,25}","���� ���� ��� �������� � ������������ ������ �������� �� 20-25 ����"));
		fieldBuilder.setEnabledExpression(taxationOrBillingProviderExpression);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���������� �������");
		fieldBuilder.setName("ground");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fieldBuilder.setEnabledExpression(billingProviderExpression);
		fb.addField(fieldBuilder.build());

		Expression expression = new RhinoExpression("form.ground");

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����������� ����������");
		fieldBuilder.setName("separator1");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		RequiredFieldValidator validator = new RequiredFieldValidator();
		validator.setEnabledExpression(expression);
		fieldBuilder.addValidators(validator, new RegexpFieldValidator(".{0,1}$", "����������� ���������� ������ �������� �� ������ �������."));
		fieldBuilder.setEnabledExpression(billingProviderExpression);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����������� �������� ����������");
		fieldBuilder.setName("separator2");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		validator = new RequiredFieldValidator();
		validator.setEnabledExpression(expression);
		fieldBuilder.addValidators(validator, new RegexpFieldValidator(".{0,1}$", "����������� �������� ���������� ������ �������� �� ������ �������."));
		fieldBuilder.setEnabledExpression(billingProviderExpression);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� �� eng4000.nsi");
		fieldBuilder.setName("eng4000nsi");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(taxationOrBillingProviderExpression);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������� ��� �����������");
		fieldBuilder.setName("imageHelp");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator(".{0,25}", "��������� ��� ����������� ������� ���� �� ����� 25 ��������."));
		fieldBuilder.setEnabledExpression(externalOrBillingProviderExpression);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������� ���������");
		fieldBuilder.setName("imageHelpTitle");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator(".{0,100}", "�������� ��������� ������� ���� �� ����� 100 ��������."));
		fieldBuilder.setEnabledExpression(externalOrBillingProviderExpression);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���������� � ���������� �������� ��������");
		fieldBuilder.setName("popular");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fieldBuilder.setEnabledExpression(billingProviderExpression);
		fb.addField(fieldBuilder.build());

		AccountKeyValidator accountKeyValidator = new AccountKeyValidator();
		accountKeyValidator.setBinding(AccountKeyValidator.FIELD_BIC, "bankCode");
		accountKeyValidator.setBinding(AccountKeyValidator.FIELD_RECEIVER_ACCOUNT, "account");
		accountKeyValidator.setMessage("�������� ���� ����� ����������.");
		fb.addFormValidators(accountKeyValidator);

		ServiceProviderINNValidator innValidator = new ServiceProviderINNValidator();
		innValidator.setBinding(ServiceProviderINNValidator.PROVIDER_ID_FIELD, "id");
		innValidator.setBinding(ServiceProviderINNValidator.PROVIDER_TYPE_FIELD, "providerType");
		innValidator.setBinding(ServiceProviderINNValidator.PROVIDER_INN_FIELD, "inn");
		innValidator.setMessage("� ������� ��� ���������� ��������� ��������� � ����� ���. ����������, ��������� ��� ����������.");
		fb.addFormValidators(innValidator);

		ExistsBankByBICValidator existsBankByBICValidator = new ExistsBankByBICValidator();
		existsBankByBICValidator.setBinding(ExistsBankByBICValidator.BIC, "bankCode");
		existsBankByBICValidator.setBinding(ExistsBankByBICValidator.NEED_BANK_REQUISITE, "bankDetails");
		existsBankByBICValidator.setMessage("��������� ������� ���������� ����������� � ����������� ������. ����������, �������� ���������� ������.");
		fb.addFormValidators(existsBankByBICValidator);

		// ��������� ����
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������� ����������� ��� �������� ���-��������");
		fieldBuilder.setName("mobilebank");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(billingProviderExpression);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� ���������� � ��������� �����");
		fieldBuilder.setName("mobilebankCode");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(billingProviderExpression);

		RequiredFieldValidator requiredMBCodeValidator = new RequiredFieldValidator();
		requiredMBCodeValidator.setEnabledExpression(new RhinoExpression("form.mobilebank == true"));
		requiredMBCodeValidator.setMessage("������� ��� ���������� � ��������� �����");

		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{1,32}", "����� ���� ���������� � ��������� ����� �� ������ ��������� 32 �������"),
				new RegexpFieldValidator("[a-zA-Z0-9 \\-]*", "��� ���������� � ��������� ����� ������ ��������� ������ ����� ���������� ��������, ����� � �������."),
				requiredMBCodeValidator
		);
		fieldBuilder.setEnabledExpression(billingProviderExpression);
		fb.addField(fieldBuilder.build());

		Expression mbExpression = new RhinoExpression(
				"form.providerType == 'BILLING' && form.mobilebank == true");

		UniqueMobileBankCodeValidator uniqMBCodeValidator = new UniqueMobileBankCodeValidator("�������� ��� ���������� � ��������� ����� ��� ����� ������ �����������");
		uniqMBCodeValidator.setBinding(UniqueMobileBankCodeValidator.FIELD_PROVIDER_ID, "id");
		uniqMBCodeValidator.setBinding(UniqueMobileBankCodeValidator.FIELD_MOBILEBANK_CODE, "mobilebankCode");
		uniqMBCodeValidator.setEnabledExpression(mbExpression);

		fb.addFormValidators(uniqMBCodeValidator);

		//����������� � ��������� ������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������� ������� � ������ API");
		fieldBuilder.setName("versionAPI");
		fieldBuilder.setType(IntType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(billingProviderExpression);
		fb.addField(fieldBuilder.build());

		//��� ������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� ������");
		fieldBuilder.setName("payType");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fieldBuilder.setEnabledExpression(taxationProviderExpression);
		fb.addField(fieldBuilder.build());

		//�������� ����� ������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������� ����� ������");
		fieldBuilder.setName("backUrlAction");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fieldBuilder.setEnabledExpression(externalProviderExpression);
		fb.addField(fieldBuilder.build());

		//��������� �� ����� ����� �������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������� ������ ����� �������");
		fieldBuilder.setName("checkOrder");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fieldBuilder.setEnabledExpression(externalProviderExpression);
		fb.addField(fieldBuilder.build());

		//�������������� ������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������������� ������");
		fieldBuilder.setName("fullPayment");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fieldBuilder.setEnabledExpression(taxationProviderExpression);
		fb.addField(fieldBuilder.build());

		//������� ���������� ��� ��������� ��������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������� ��� ��������� ��������");
		fieldBuilder.setName("phoneNumber");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator(".{1,15}","���� \"������� ��� ��������� ��������\" �� ������ ���� ������ 15 ��������"));
		fb.addField(fieldBuilder.build());

		// URL ��� �������� �����
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("URL ��� �������� �����");
		fieldBuilder.setName("url");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator(".{1,256}","���� \"URL ��� �������� �����\" �� ������ ���� ������ 256 ��������"));
		fieldBuilder.setEnabledExpression(externalProviderExpression);
		fb.addField(fieldBuilder.build());

		// ������ ����� ���
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������ ����� ���");
		fieldBuilder.setName(USE_ESB_FIELD_NAME);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(externalProviderExpression);
		fb.addField(fieldBuilder.build());

		//URL �������� ����� ������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("URL �������� ����� ������");
		fieldBuilder.setName("providerBackUrl");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator(".{1,1000}","���� \"URL ��� �������� �����\" �� ������ ���� ������ 10000 ��������"));
		fieldBuilder.setEnabledExpression(externalProviderExpression);
		fb.addField(fieldBuilder.build());

		// ��������� ��� ������ ��������� ���������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������� ��� ������ ��������� ���������");
		fieldBuilder.setName("formName");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fieldBuilder.setEnabledExpression(externalProviderExpression);
		fb.addField(fieldBuilder.build());

		// ���������� ���������� � ����� ��������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���������� � ��������-������� ���������� � ����� ��������");
		fieldBuilder.setName("sendChargeOffInfo");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(externalProviderExpression);
		fb.addField(fieldBuilder.build());

		// �������� �� �������� �� �� ��� ��������-��������.
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������� �� �������� �� �� ��� ��������-��������.");
		fieldBuilder.setName("availableMBCheck");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(externalProviderExpression);
		fb.addField(fieldBuilder.build());

		// �������� �� Mobile Checkout ��� ��������-��������.
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������� �� Mobile Checkout ��� ��������-��������.");
		fieldBuilder.setName("availableMobileCheckout");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(externalProviderExpression);
		fb.addField(fieldBuilder.build());

		// �������� �� ��������� �������������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������� �� ��������� �������������.");
		fieldBuilder.setName("isFasilitator");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(externalProviderExpression);
		fb.addField(fieldBuilder.build());

		// ����������� ������������ � E-invoicing
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����������� ������������ � E-invoicing.");
		fieldBuilder.setName("eInvoicingProperty");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(externalProviderExpression);
		fb.addField(fieldBuilder.build());

		// ����������� ������������ � Mobile Checkout
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����������� ������������ � Mobile Checkout.");
		fieldBuilder.setName("mobileCheckoutProperty");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(externalProviderExpression);
		fb.addField(fieldBuilder.build());

		// ����������� ������������ ��� �������� ������� ����� ��
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����������� ������������ ��� �������� ������� ����� ��.");
		fieldBuilder.setName("mbCheckProperty");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(externalProviderExpression);
		fb.addField(fieldBuilder.build());

		// ������������ �� �������� ��������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������� ��� �������� ��������");
		fieldBuilder.setName("templateSupported");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(billingProviderExpression);
		fb.addField(fieldBuilder.build());

		// ����������� � ����������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����������� � ���������");
		fieldBuilder.setName("planingForDeactivate");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(billingProviderExpression);
		fb.addField(fieldBuilder.build());

		// ������������ �� �������������� �������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������� �������������� �������");
		fieldBuilder.setName("editPaymentSupported");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(billingProviderExpression);
		fb.addField(fieldBuilder.build());

		//��� �������� ������������� SMS-��������

		//���������
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
		multiFieldsValidator.setMessage("����: ������, ��������� � ������ ���-������� - ����������� ��� ������������� ���-��������!");

		multiFieldsValidator.setBinding("standart","standartSmsTemplate");
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������� �� ���-������ ���������� �����������.");
		fieldBuilder.setName("standartSmsTemplate");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		//������ �������
		multiFieldsValidator.setBinding("format","format");
		fieldBuilder =  new FieldBuilder();
		fieldBuilder.setDescription("������ �������������� ���-�������.");
		fieldBuilder.setName("format");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator(".{1,255}","������ ������� �� ������ ��������� ����� 255 ��������"));
		fb.addField(fieldBuilder.build());

		//������
		multiFieldsValidator.setBinding("example","example");
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������ �������������� ���-�������");
		fieldBuilder.setName("example");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator(".{1,255}","������ ������� �� ������ ��������� ����� 255 ��������"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������ ��� (QR, BAR) � ��������� ����������");
		fieldBuilder.setName("isBarSupported");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������� �� ��������� ��� ���������� ������� �������");
		fieldBuilder.setName("isOfflineAvailable");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������� �� ������ � ��������� ����");
		fieldBuilder.setName(ServiceProvidersConstants.PROVIDER_IS_CREDIT_CARD_ACCESSIBLE);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fb.addFormValidators(multiFieldsValidator);

		return fb.build();
	}

	private static Form createSetupGroupRiskForm()
	{
		FormBuilder fb = new FormBuilder();
		// ������ �����
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������ �����");
		fieldBuilder.setName("groupRiskId");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		return fb.build();
	}

	private static Form createSmsAliasForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������� �� ���������");
		fieldBuilder.setName("smsAlias");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RegexpFieldValidator("([[0-9a-zA-Z�-��-ߨ�]]{1,20}\\;)*([[0-9a-zA-Z�-��-ߨ�]]{1,20}\\s)*([0-9a-zA-Z�-��-ߨ�]{1,20}){1}", "�������� �� ��������� �� ������ ��������� 20 ��������, ���������� �����������: ';' ��� ������.")
		);
		fb.addField(fieldBuilder.build());

		return fb.build();
	}

	public List<VersionNumber> getMapiVersions()
	{
		return MAPI_VERSIONS;
	}
}
