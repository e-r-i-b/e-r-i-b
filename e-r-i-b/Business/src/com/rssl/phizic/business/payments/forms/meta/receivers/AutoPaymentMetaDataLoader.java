package com.rssl.phizic.business.payments.forms.meta.receivers;

import com.rssl.common.forms.*;
import com.rssl.common.forms.expressions.ConstantExpression;
import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.common.forms.validators.MoneyFieldValidator;
import com.rssl.common.forms.validators.NumericRangeValidator;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.source.DocumentFieldValuesSource;
import com.rssl.phizic.business.documents.payments.BillingPaymentHelper;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.business.payments.forms.ExtendedFieldBuilderHelper;
import com.rssl.phizic.gate.longoffer.autopayment.ThresholdAutoPayScheme;
import com.rssl.phizic.gate.payments.systems.recipients.FieldValidationRule;
import com.rssl.phizic.utils.NumericUtil;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author niculichev
 * @ created 17.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class AutoPaymentMetaDataLoader extends ExtendedMetadataLoaderBase
{
	public static final String RECIPIENT_ID_KEY                 = PaymentFieldKeys.PROVIDER_KEY;
	private static final String AMOUNT_FIELD_ERROR_MESSAGE      = "����������, ������� �����, ������� ���������� ���������. ��������, 320,66.";

	private static final String REQUISITE                       = "requisite";
	private static final String REQUISITENAME                   = "requisiteName";
	private static final String SELL_AMOUNT_FIELD_NAME          = "sellAmount";

	protected static final String IS_AUTOPAY_TOTAL_AMOUNT_LIMIT_FIELD_NAME = "isAutoPaymentTotalAmountLimit";
	protected static final String AUTOPAY_TOTAL_AMOUNT_PERIOD_FIELD_NAME   = "autoPaymentTotalAmountPeriod";

	private static final String VALIDATION_MODE                 = "long-offer|prepare-long-offer";

	private static final ServiceProviderService providerService = new ServiceProviderService();

	public Metadata load(Metadata metadata, FieldValuesSource fieldSource) throws BusinessException
	{
		return load(metadata, fieldSource, false);
	}

	public Metadata load(Metadata metadata, BusinessDocument document) throws BusinessException
	{
		FieldValuesSource fieldSource = new DocumentFieldValuesSource(metadata,document);
		return load(metadata, fieldSource, true);
	}

	public Metadata load(Metadata metadata, TemplateDocument template) throws BusinessException, BusinessLogicException
	{
		throw new UnsupportedOperationException();
	}

	public Metadata load(Metadata metadata, BusinessDocument document, TemplateDocument template) throws BusinessException, BusinessLogicException
	{
		throw new UnsupportedOperationException();
	}

	private Metadata load(Metadata metadata, FieldValuesSource fieldSource, boolean isByDocument) throws BusinessException
	{
		try
		{
			BillingServiceProviderBase serviceProvider = getServiceProvider(fieldSource);

			List<Field> fields = getFieldsBuilder(metadata, serviceProvider, isByDocument).buildFields();
			// ������������ �� ��������� ������������ ����(������������ ��������)
			preLoadMetadata(fields, fieldSource, serviceProvider, isByDocument);
			// �������� ����������
			return load(metadata, fields, null, null);
		}
		catch (BusinessException e)
		{
			throw new FormException(e);
		}
	}

	protected FieldsBuilder getFieldsBuilder(Metadata metadata, BillingServiceProviderBase serviceProvider, boolean isByDocument)
	{
		// ���������  ���������� � ����������
		CompositeFieldsBuilder fieldsBuilder = new CompositeFieldsBuilder();
		fieldsBuilder.addBuilder(new ReceiverInfoFieldsBuilder(metadata, serviceProvider));
		if (!isByDocument)
			 fieldsBuilder.addBuilder(new GateFieldsBuilder(serviceProvider));

		return fieldsBuilder;
	}

	protected BillingServiceProviderBase getServiceProvider(FieldValuesSource fieldSource) throws BusinessException
	{
		String receiverId = fieldSource.getValue(RECIPIENT_ID_KEY);
		if (StringHelper.isEmpty(receiverId))
			throw new FormException("�� ����� [ " + RECIPIENT_ID_KEY + " ]");

		Long receiverLongId = Long.parseLong(receiverId);

		BillingServiceProviderBase serviceProvider = (BillingServiceProviderBase) providerService.findById(receiverLongId);
		if (serviceProvider == null)
		{
			throw new BusinessException("��������� � ��������������� "+receiverLongId + "�� ������");
		}

		return serviceProvider;
	}

	public String getExtendedMetadataKey(FieldValuesSource fieldSource) throws FormException
	{
		return null;
	}

	private boolean validateKeyFieldValue(String value, List<FieldValidator> validators) throws BusinessException
	{
		if (StringHelper.isEmpty(value))
			return false;

		try
		{
			for (FieldValidator validator : validators)
			{
				if (!validator.validate(value))
					return false;
			}
		}
		catch (TemporalDocumentException e)
		{
			throw new BusinessException(e);
		}

		return true;
	}

	private List<FieldValidator> getKeyFieldValidators(FieldDescription fieldDescription, Field field)
	{
		List<FieldValidator> validators = new ArrayList<FieldValidator>();

		if (CollectionUtils.isNotEmpty(fieldDescription.getFieldValidationRules()))
		{
			for (FieldValidationRule rule : fieldDescription.getFieldValidationRules())
			{
				//����������� ��� ���������� ����
				validators.add(ExtendedFieldBuilderHelper.adaptValidator(rule, VALIDATION_MODE, fieldDescription.getType().toString()));
			}
		}

		if (fieldDescription.getMinLength() != null || fieldDescription.getMaxLength() != null)
		{
			//������� ��������� �������������� ����� �������� ����
			FieldValidator validator = ExtendedFieldBuilderHelper.buildLengthValidator(fieldDescription.getMinLength(), fieldDescription.getMaxLength(), fieldDescription.getHint(), fieldDescription.getDescription(), fieldDescription.getType());
			validator.setMode(VALIDATION_MODE);
			validators.add(validator);
		}

		if (fieldDescription.isMainSum())
		{
			FieldValidator validator = new MoneyFieldValidator();
			validator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "0.01");
			validator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "999999999999999.99");
			validator.setMessage(AMOUNT_FIELD_ERROR_MESSAGE);
			validators.add(validator);
		}

		//��������� ���������� ���� ���� �����
		if(field != null)
			validators.addAll(field.getValidators());

		return validators;
	}

	/**
	 * �������������� ��������� ���������������� ����� ����� ����������� ����������
	 * @param buildedFields ����
	 * @param fieldSource �������� ������
	 * @param provider ���������
	 * @param isByDocument ������ �������� ����������� ���������?
	 */
	protected void preLoadMetadata(List<Field> buildedFields, FieldValuesSource fieldSource, BillingServiceProviderBase provider, boolean isByDocument) throws BusinessException
	{
		// ��� ����������� ������ ���� ������ ���� �������� ����, ������� ��������� � �������� ���������
		List<FieldDescription> fieldDescriptions = BillingPaymentHelper.getKeyFieldDescription(provider);
		if(fieldDescriptions.size() != 1)
			throw new BusinessException("��� ����������� ������ ���� ���� �������� ����");

		FieldDescription keyFieldDescription = fieldDescriptions.get(0);

		// ����� ���������� �����������, ���� ��������, ��� ����������� ������������� �����
		ThresholdAutoPayScheme thresholdScheme =
				(provider instanceof BillingServiceProvider) ? ((BillingServiceProvider) provider).getThresholdAutoPayScheme() : null;

		//���� ����� ��� ��� ����, ���������� ������ ��� ������
		for(int i = 0, n = 0; i < buildedFields.size()-1 && n < 5; i++)
		{
			Field field = buildedFields.get(i);
			//������������� �������� ���������
			if(REQUISITE.equals(field.getName()))
			{
				// �������� �������� ���������
				String temp = fieldSource.getValue(keyFieldDescription.getExternalId());
				String value = StringHelper.isEmpty(temp) ? fieldSource.getValue(REQUISITE): temp;
				// �������� �������, �.�. ���� ��������� ���������� ��� ���.
				buildedFields.set(i, getModifyRequisiteField(keyFieldDescription, field, StringUtils.trim(value)));
				n++;
			}
			//������������� ��� ���������
			else if(REQUISITENAME.equals(field.getName()))
			{
				buildedFields.set(i, FieldBuilderHelper.modifyExpressions(field, keyFieldDescription.getName()));
				n++;
			}
			else if(SELL_AMOUNT_FIELD_NAME.equals(field.getName()))
			{
				try
				{
					FieldDescription sumField = BillingPaymentHelper.getMainSumFieldDescription(provider);
					if (sumField != null)
					{
						String strValue = StringUtils.deleteWhitespace(fieldSource.getValue(sumField.getExternalId()));
						// ���� ���� ����� ���, ��� ��� �� ������ ����������, ��� ��� �� �������, �� ��������� �� �����!
						if (!isByDocument && validateKeyFieldValue(strValue, getKeyFieldValidators(sumField, null)))
							((FieldImpl)field).setInitalValueExpression(new ConstantExpression(NumericUtil.parseBigDecimal(strValue)));
					}
				}
				catch (ParseException e)
				{
					throw new BusinessException(e);
				}
				n++;
			}
			else if(IS_AUTOPAY_TOTAL_AMOUNT_LIMIT_FIELD_NAME.equals(field.getName()))
			{
				if(thresholdScheme != null)
					buildedFields.set(i, FieldBuilderHelper.modifyExpressions(field, thresholdScheme.isAccessTotalMaxSum()));
				n++;
			}
			else if(AUTOPAY_TOTAL_AMOUNT_PERIOD_FIELD_NAME.equals(field.getName()))
			{
				if(thresholdScheme != null)
					buildedFields.set(i, FieldBuilderHelper.modifyExpressions(field, thresholdScheme.getPeriodMaxSum()));
				n++;
			}
		}
	}

	private Field getModifyRequisiteField(FieldDescription keyFieldDescription, Field requisiteField, String value) throws BusinessException
	{
		List<FieldValidator> validators = getKeyFieldValidators(keyFieldDescription, requisiteField);

		FieldBuilder builder = FieldBuilderHelper.getFieldBuilder(requisiteField);

		builder.setBusinessCategory(ExtendedFieldBuilderHelper.adaptBusinessCategory(keyFieldDescription.getBusinessSubType()));
		builder.setDescription(keyFieldDescription.getName());
		builder.setKey(true);

		//���� ���� �� �������������, ������ �������� ������, ������� ��������� ����������
		if (!validateKeyFieldValue(value, validators))
		{
			// ������������� ����������, ���� ��� ���������
			builder.clearValidators();
			builder.addValidators(validators.toArray(new FieldValidator[validators.size()]));
			// ������������� ���, ����� ���� ������������ �� �����
			builder.setName(keyFieldDescription.getExternalId());
		}
		else
		{
			Expression expression = new ConstantExpression(value);
			builder.setValueExpression(expression);
			builder.setInitalValueExpression(expression);
		}

		return builder.build();
	}
}
