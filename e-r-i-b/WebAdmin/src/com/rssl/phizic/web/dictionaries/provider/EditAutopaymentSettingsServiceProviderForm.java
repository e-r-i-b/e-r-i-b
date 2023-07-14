package com.rssl.phizic.web.dictionaries.provider;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.claims.forms.validators.IsCheckedMultiFieldValidator;

import java.math.BigInteger;

/**
 * @author krenev
 * @ created 27.09.2011
 * @ $Author$
 * @ $Revision$
 */
public class EditAutopaymentSettingsServiceProviderForm extends EditServiceProviderFormBase
{
	static final String AUTO_PAYMENT_SUPPORTED_FIELD_NAME = "autoPaymentSupported";
	static final String AUTO_PAYMENT_SUPPORTED_FIELD_NAME_ATM = "autoPaymentSupportedInATM";
	static final String AUTO_PAYMENT_SUPPORTED_FIELD_NAME_SOCIAL_API = "autoPaymentSupportedInSocialApi";
	static final String AUTO_PAYMENT_SUPPORTED_FIELD_NAME_API = "autoPaymentSupportedInAPI";
	static final String AUTO_PAYMENT_SUPPORTED_FIELD_NAME_ERMB = "autoPaymentSupportedInERMB";
	static final String AUTO_PAYMENT_VISIBLE_FIELD_NAME = "autoPaymentVisible";
	static final String AUTO_PAYMENT_VISIBLE_FIELD_NAME_SOCIAL_API = "autoPaymentVisibleInSocialApi";
	static final String AUTO_PAYMENT_VISIBLE_FIELD_NAME_ATM = "autoPaymentVisibleInATM";
	static final String AUTO_PAYMENT_VISIBLE_FIELD_NAME_API = "autoPaymentVisibleInAPI";
	static final String AUTO_PAYMENT_VISIBLE_FIELD_NAME_ERMB = "autoPaymentVisibleInERMB";

	static final String BANKOMAT_SUPPORTED_FIELD_NAME = "bankomatSupported";

	static final String ALWAYS_AUTO_PAY_FIELD_NAME = "alwaysAutoPay";
	static final String MIN_SUM_ALWAYS_AUTO_PAY_FIELD_NAME = "minSumAlwaysAutoPay";
	static final String MAX_SUM_ALWAYS_AUTO_PAY_FIELD_NAME = "maxSumAlwaysAutoPay";
	static final String CLIENT_HINT_ALWAYS_AUTO_PAY_FIELD_NAME = "clientHintAlwaysAutoPay";

	static final String THRESHOLD_AUTO_PAY_FIELD_NAME = "thresholdAutoPay";
	static final String THRESHOLD_VALUES_TYPE_FIELD_NAME = "thresholdValuesType";
	static final String MIN_SUM_THRESHOLD_AUTO_PAY_FIELD_NAME = "minSumThresholdAutoPay";
	static final String MAX_SUM_THRESHOLD_AUTO_PAY_FIELD_NAME = "maxSumThresholdAutoPay";
	static final String MAX_VALUE_THRESHOLD_AUTO_PAY_FIELD_NAME = "maxValueThresholdAutoPay";
	static final String MIN_VALUE_THRESHOLD_AUTO_PAY_FIELD_NAME = "minValueThresholdAutoPay";
	static final String CLIENT_HINT_THRESHOLD_AUTO_PAY_FIELD_NAME = "clientHintThresholdAutoPay";
	static final String THRESHOLD_DISCRETE_VALUES_FIELD_NAME = "thresholdDiscreteValues";
	static final String ACCESS_TOTAL_MAX_SUM_THRESHOLD_AUTO_PAY_FIELD_NAME  = "accessTotalMaxSumThresholdAutoPay";
	static final String TOTAL_MAX_SUM_THRESHOLD_AUTO_PAY_FIELD_NAME         = "totalMaxSumThresholdAutoPay";
	static final String PERIOD_MAX_SUM_THRESHOLD_AUTO_PAY_FIELD_NAME        = "periodMaxSumThresholdAutoPay";

	static final String INVOICE_AUTO_PAY_FIELD_NAME = "invoiceAutoPay";
	static final String CLIENT_HINT_INVOICE_AUTO_PAY_FIELD_NAME = "clientHintInvoiceAutoPay";

	public static final Form EDIT_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		//����������� �����������
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������� '����������'");
		fieldBuilder.setName(AUTO_PAYMENT_SUPPORTED_FIELD_NAME);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������� ����������� � ��������-�����");
		fieldBuilder.setName(AUTO_PAYMENT_VISIBLE_FIELD_NAME);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());
		//����������� ����������� � mApi
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������� '����������' � mApi");
		fieldBuilder.setName(AUTO_PAYMENT_SUPPORTED_FIELD_NAME_API);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������� ����������� � mApi");
		fieldBuilder.setName(AUTO_PAYMENT_VISIBLE_FIELD_NAME_API);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());
		//����������� �����������  � atmApi
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������� '����������' � atmApi");
		fieldBuilder.setName(AUTO_PAYMENT_SUPPORTED_FIELD_NAME_ATM);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������� ����������� � atmApi");
		fieldBuilder.setName(AUTO_PAYMENT_VISIBLE_FIELD_NAME_ATM);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());
		//����������� �����������  � socialApi
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������� '����������' � socialApi");
		fieldBuilder.setName(AUTO_PAYMENT_SUPPORTED_FIELD_NAME_SOCIAL_API);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������� ����������� � socialApi");
		fieldBuilder.setName(AUTO_PAYMENT_VISIBLE_FIELD_NAME_SOCIAL_API);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());
		//����������� ����������� � ����
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������� '����������' � ����");
		fieldBuilder.setName(AUTO_PAYMENT_SUPPORTED_FIELD_NAME_ERMB);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������� ����������� � � ����");
		fieldBuilder.setName(AUTO_PAYMENT_VISIBLE_FIELD_NAME_ERMB);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������� ��������������");
		fieldBuilder.setName(BANKOMAT_SUPPORTED_FIELD_NAME);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		buildFormForFloorAytopaymentSettings(fb); //��������� ����������
		buildFormForAlwaysAutoPaymentSettings(fb); //���������� ����������
		buildFormForInvoiceAutoPaymentSettings(fb); //���������� �� ������������� �����

		//���� ������� ������ "�������� ����������" �� ���������� ���� ��� ������ ���� �� ���� ��� �����������.
		MultiFieldsValidator checkedMultiFieldValidator = new IsCheckedMultiFieldValidator();
		checkedMultiFieldValidator.setBinding("ptr1", ALWAYS_AUTO_PAY_FIELD_NAME);
		checkedMultiFieldValidator.setBinding("ptr2", THRESHOLD_AUTO_PAY_FIELD_NAME);
		checkedMultiFieldValidator.setBinding("ptr3", INVOICE_AUTO_PAY_FIELD_NAME);
		checkedMultiFieldValidator.setMessage("�� ����������� ������� ��������� �����������. ����������, �������� ��� ����������� � ���������.");
		checkedMultiFieldValidator.setEnabledExpression(new RhinoExpression("form.autoPaymentSupported==true || form.autoPaymentSupportedInATM==true || form.autoPaymentSupportedInSocialApi==true  || form.autoPaymentSupportedInAPI==true  || form.autoPaymentSupportedInERMB==true "));
		fb.addFormValidators(checkedMultiFieldValidator);

		return fb.build();
	}

	/**
	 * ��������� ����� ��� ���������� ���������� �����������
	 * @param fb ������ �����
	 */
	private static void buildFormForFloorAytopaymentSettings(FormBuilder fb)
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���������");
		fieldBuilder.setName(THRESHOLD_AUTO_PAY_FIELD_NAME);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		//��������� "������ ��������� ����������"
		final Expression thresholdAutoPaySelectedExpression = new RhinoExpression("(form.autoPaymentSupported==true || form.autoPaymentSupportedInSocialApi==true || form.autoPaymentSupportedInATM==true  || form.autoPaymentSupportedInAPI==true  || form.autoPaymentSupportedInERMB==true ) && form.thresholdAutoPay==true");
		//��������� �� ��� ���������� ������������ �����
		final FieldValidator requiredFieldValidator = new RequiredFieldValidator("��� ��������� ����� ����������� ���������� ��������� ����:\\\\n" +
				"����������� �������� �����, ������������ �������� ����� � ��������� ��������");

		MoneyFieldValidator maxAmountValidator = new MoneyFieldValidator();
		maxAmountValidator.setParameter("maxValue", "9999999.99");
		maxAmountValidator.setMessage("������� ����������� ����� ���������� ��������� � ���������� �������: #######.##");

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����������� �������� ����� ���������� �����������");
		fieldBuilder.setName(MIN_SUM_THRESHOLD_AUTO_PAY_FIELD_NAME);
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredFieldValidator, maxAmountValidator);
		fieldBuilder.setEnabledExpression(thresholdAutoPaySelectedExpression);
		fb.addField(fieldBuilder.build());

		maxAmountValidator = new MoneyFieldValidator();
		maxAmountValidator.setParameter("maxValue", "9999999.99");
		maxAmountValidator.setMessage("������� ������������ ����� ���������� ��������� � ���������� �������: #######.##");

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������ �������� ����� ���������� �����������");
		fieldBuilder.setName(MAX_SUM_THRESHOLD_AUTO_PAY_FIELD_NAME);
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredFieldValidator, maxAmountValidator);
		fieldBuilder.setEnabledExpression(thresholdAutoPaySelectedExpression);
		fb.addField(fieldBuilder.build());

		// ��������� ����� ����������� ����� ����������� ���� ������ ������������
		CompareValidator compareValidator = new CompareValidator();
		compareValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS_EQUAL);
		compareValidator.setBinding(CompareValidator.FIELD_O1, MIN_SUM_THRESHOLD_AUTO_PAY_FIELD_NAME);
		compareValidator.setBinding(CompareValidator.FIELD_O2, MAX_SUM_THRESHOLD_AUTO_PAY_FIELD_NAME);
		compareValidator.setMessage("����������� �������� ����� ���������� ����������� �� ������ ��������� ������������ ��������.");
		fb.addFormValidators(compareValidator);

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������ ����� ����������� � �����");
		fieldBuilder.setName(ACCESS_TOTAL_MAX_SUM_THRESHOLD_AUTO_PAY_FIELD_NAME);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(thresholdAutoPaySelectedExpression);
		fb.addField(fieldBuilder.build());

		final Expression accessTotalMaxSumSelectedExpression = new RhinoExpression("form.accessTotalMaxSumThresholdAutoPay==true");

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������ ����� �����������");
		fieldBuilder.setName(TOTAL_MAX_SUM_THRESHOLD_AUTO_PAY_FIELD_NAME);
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(accessTotalMaxSumSelectedExpression);
		fieldBuilder.addValidators(maxAmountValidator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������ ������������ ����� ����������");
		fieldBuilder.setName(PERIOD_MAX_SUM_THRESHOLD_AUTO_PAY_FIELD_NAME);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(accessTotalMaxSumSelectedExpression);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� ���������� �������� (�������� ��� ���������� ��������)");
		fieldBuilder.setName(THRESHOLD_VALUES_TYPE_FIELD_NAME);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(thresholdAutoPaySelectedExpression);
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());

		// ���������, ��� ���������� �������� ������������ ������ �� ��� ��������� �������� ����� ��������� ��������
		final Expression intervalValuesExpression = new RhinoExpression("(form.autoPaymentSupported==true || form.autoPaymentSupportedInSocialApi==true || form.autoPaymentSupportedInATM==true  || form.autoPaymentSupportedInAPI==true  || form.autoPaymentSupportedInERMB==true )&& form.thresholdAutoPay==true && form.thresholdValuesType==true");
		FieldValidator requireFieldValidator = new RequiredFieldValidator("��� ��������� ����� ����������� ���������� ��������� ����:\\\\n  " +
						"����������� �������� ������ � ������������ �������� ������ ��� ������� ���������� ��������");

		maxAmountValidator = new MoneyFieldValidator();
		maxAmountValidator.setParameter("maxValue", "9999999.99");
		maxAmountValidator.setMessage("������� ����������� �������� ������ ���������� ��������� � ���������� �������: #######.##");

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����������� �������� ������ ���������� �����������");
		fieldBuilder.setName(MIN_VALUE_THRESHOLD_AUTO_PAY_FIELD_NAME);
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requireFieldValidator, maxAmountValidator);
		fieldBuilder.setEnabledExpression(intervalValuesExpression);
		fb.addField(fieldBuilder.build());

		maxAmountValidator = new MoneyFieldValidator();
		maxAmountValidator.setParameter("maxValue", "9999999.99");
		maxAmountValidator.setMessage("������� ������������ �������� ������ ���������� ��������� � ���������� �������: #######.##");

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������ �������� ������ ���������� �����������");
		fieldBuilder.setName(MAX_VALUE_THRESHOLD_AUTO_PAY_FIELD_NAME);
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requireFieldValidator, maxAmountValidator);
		fieldBuilder.setEnabledExpression(intervalValuesExpression);
		fb.addField(fieldBuilder.build());

		// ��������� ���� ����������� �������� ������ ���������� ����������� ���� ������ ������������
		compareValidator = new CompareValidator();
	    compareValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS_EQUAL);
	    compareValidator.setBinding(CompareValidator.FIELD_O1, "minValueThresholdAutoPay");
	    compareValidator.setBinding(CompareValidator.FIELD_O2, "maxValueThresholdAutoPay");
	    compareValidator.setMessage("����������� �������� ���������� ��������� �� ������ ��������� ������������ ��������.");
		compareValidator.setEnabledExpression(intervalValuesExpression);
	    fb.addFormValidators(compareValidator);

		//���������� ��������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���������� ��������");
		fieldBuilder.setName(THRESHOLD_DISCRETE_VALUES_FIELD_NAME);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(new RhinoExpression("(form.autoPaymentSupported==true || form.autoPaymentSupportedInSocialApi==true || form.autoPaymentSupportedInATM==true  || form.autoPaymentSupportedInAPI==true  || form.autoPaymentSupportedInERMB==true ) && form.thresholdAutoPay==true && form.thresholdValuesType==false"));
		fieldBuilder.addValidators(requireFieldValidator,
				new RegexpFieldValidator("^\\d{1,7}((\\.|,)\\d{0,2})?(\\|\\d{1,7}((\\.|,)\\d{0,2})?)*$", "�� ��������� ������� ���������� ��������"),
				new RegexpFieldValidator(".{0,256}", "��������� ������������ ���������� ���������� �������� ���������� �����������")
		);
		fb.addField(fieldBuilder.build());

		fb.addField(createClientHintField(CLIENT_HINT_THRESHOLD_AUTO_PAY_FIELD_NAME));
	}

	/**
	 * ��������� ����� ��� ���������� ����������� �����������
	 * @param fb ������ �����
	 */
	private static void buildFormForAlwaysAutoPaymentSettings(FormBuilder fb)
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����������");
		fieldBuilder.setName(ALWAYS_AUTO_PAY_FIELD_NAME);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		//��������� "������ ���������� ����������"
		final Expression alwaysAutoPaySelectedExpression = new RhinoExpression("form.alwaysAutoPay==true && (form.autoPaymentSupported==true || form.autoPaymentSupportedInSocialApi==true || form.autoPaymentSupportedInATM==true  || form.autoPaymentSupportedInAPI==true  || form.autoPaymentSupportedInERMB==true )");
		//��������� �� ��� ���������� ������������ �����
		final FieldValidator requiredFieldValidator = new RequiredFieldValidator("��� ���������� ����� ����������� ���������� ��������� ����:\\\\n" +
				"����������� �������� ����� � ������������ �������� �����");

		MoneyFieldValidator maxAmountValidator = new MoneyFieldValidator();
		maxAmountValidator.setParameter("maxValue", "9999999.99");
		maxAmountValidator.setMessage("������� ����������� ����� ����������� ��������� � ���������� �������: #######.##");

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����������� �������� ����� ����������� �����������");
		fieldBuilder.setName(MIN_SUM_ALWAYS_AUTO_PAY_FIELD_NAME);
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredFieldValidator, maxAmountValidator);
		fieldBuilder.setEnabledExpression(alwaysAutoPaySelectedExpression);
		fb.addField(fieldBuilder.build());

		maxAmountValidator = new MoneyFieldValidator();
		maxAmountValidator.setParameter("maxValue", "9999999.99");
		maxAmountValidator.setMessage("������� ������������ ����� ����������� ��������� � ���������� �������: #######.##");

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������ �������� ����� ����������� �����������");
		fieldBuilder.setName(MAX_SUM_ALWAYS_AUTO_PAY_FIELD_NAME);
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredFieldValidator, maxAmountValidator);
		fieldBuilder.setEnabledExpression(alwaysAutoPaySelectedExpression);
		fb.addField(fieldBuilder.build());

		fb.addField(createClientHintField(CLIENT_HINT_ALWAYS_AUTO_PAY_FIELD_NAME));

		//��������� ���� ������������ ����� ����������� ����������� ���� ������ �����������
		CompareValidator compareValidator = new CompareValidator();
		compareValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS_EQUAL);
		compareValidator.setBinding(CompareValidator.FIELD_O1, MIN_SUM_ALWAYS_AUTO_PAY_FIELD_NAME);
		compareValidator.setBinding(CompareValidator.FIELD_O2, MAX_SUM_ALWAYS_AUTO_PAY_FIELD_NAME);
		compareValidator.setMessage("����������� �������� ����� ����������� ����������� �� ������ ��������� ������������ ��������.");
		fb.addFormValidators(compareValidator);
	}

	/**
	 * ��������� ����� ��� ���������� ����������� �� ������������� �����
	 * @param fb ������ �����
	 */
	private static void buildFormForInvoiceAutoPaymentSettings(FormBuilder fb)
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�� ������������� �����");
		fieldBuilder.setName(INVOICE_AUTO_PAY_FIELD_NAME);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fb.addField(createClientHintField(CLIENT_HINT_INVOICE_AUTO_PAY_FIELD_NAME));
	}

	private static Field createClientHintField(String fieldName)
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������� ������� ��� ������ ������� ���� �����������");
		fieldBuilder.setName(fieldName);
		fieldBuilder.setType(StringType.INSTANCE.getName());

		LengthFieldValidator lengthValidator = new LengthFieldValidator();
		lengthValidator.setParameter("maxlength", BigInteger.valueOf(500));
		lengthValidator.setMessage("��������� ������� ��� ������ ������� ���� ����������� ������ ��������� �� ����� 500 ��������.");
		fieldBuilder.addValidators(lengthValidator);
		return fieldBuilder.build();
	}
}
