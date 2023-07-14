package com.rssl.phizic.web.configure;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.IntType;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.earlyloanrepayment.EarlyLoanRepaymentConfig;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Форма настроек досрочного погашения кредита
 * User: petuhov
 * Date: 08.05.15
 * Time: 16:02 
 */
public class EarlyLoanRepaymentConfigureForm  extends EditPropertiesFormBase
{
	private static final String EARLY_REPAYMENT_ALLOWED_DESCRIPTION = "Предоставить возможность создать заявку на преждевременное погашение кредита";
	private static final String EARLY_REPAYMENT_MIN_DATE_DESCRIPTION = "Дата начала интервала подачи заявления ДП";
	private static final String EARLY_REPAYMENT_MAX_DATE_TOO_BIG_MESSAGE = "Конечная дата не должна превышать 365";
	private static final String EARLY_REPAYMENT_MAX_DATE_DESCRIPTION = "Дата окончания интервала подачи заявления ДП";
	private static final String EARLY_REPAYMENT_MIN_AMOUNT_DESCRIPTION = "Минимальная сумма досрочного погашения кредита";
	private static final String EARLY_REPAYMENT_CANCEL_ERIB_ALLOWED_DESCRIPTION = "Предоставить возможность отмены заявки на досрочное погашение кредита, созданной в канале ЕРИБ";
	private static final String EARLY_REPAYMENT_CANCEL_VSP_ALLOWED_DESCRIPTION = "Предоставить возможность отмены заявки на досрочное погашение кредита, созданной в канале ВСП";

	private static final String EARLY_REPAYMENT_WRONG_DATES_MESSAGE = "Конечная дата должна быть больше или равна начальной";

	private static final BigInteger LENGTH_3 = BigInteger.valueOf(3);
	private static final BigInteger LENGTH_2 = BigInteger.valueOf(2);
	private static final BigDecimal LENGTH_365 = new BigDecimal(365);

	private static final Form FORM = createForm();

	@Override
	public Form getForm()
	{
		return FORM;
	}

	@SuppressWarnings("ReuseOfLocalVariable")
	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(EarlyLoanRepaymentConfig.EARLY_REPAYMENT_ALLOWED_KEY);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription(EARLY_REPAYMENT_ALLOWED_DESCRIPTION);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(EarlyLoanRepaymentConfig.EARLY_REPAYMENT_MIN_DATE_KEY);
		fieldBuilder.setType(IntType.INSTANCE.getName());
		fieldBuilder.setDescription(EARLY_REPAYMENT_MIN_DATE_DESCRIPTION);
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new LengthFieldValidator(LENGTH_3));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(EarlyLoanRepaymentConfig.EARLY_REPAYMENT_MAX_DATE_KEY);
		fieldBuilder.setType(IntType.INSTANCE.getName());
		fieldBuilder.setDescription(EARLY_REPAYMENT_MAX_DATE_DESCRIPTION);
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new LengthFieldValidator(LENGTH_3),
				new NumericRangeValidator(BigDecimal.ZERO, LENGTH_365, EARLY_REPAYMENT_MAX_DATE_TOO_BIG_MESSAGE));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(EarlyLoanRepaymentConfig.EARLY_REPAYMENT_MIN_AMOUNT_KEY);
		fieldBuilder.setType(IntType.INSTANCE.getName());
		fieldBuilder.setDescription(EARLY_REPAYMENT_MIN_AMOUNT_DESCRIPTION);
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new LengthFieldValidator(LENGTH_2),
				new NumericRangeValidator(BigDecimal.ZERO,null,null));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(EarlyLoanRepaymentConfig.EARLY_REPAYMENT_CANCEL_ERIB_ALLOWED_KEY);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription(EARLY_REPAYMENT_CANCEL_ERIB_ALLOWED_DESCRIPTION);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(EarlyLoanRepaymentConfig.EARLY_REPAYMENT_CANCEL_VSP_ALLOWED_KEY);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription(EARLY_REPAYMENT_CANCEL_VSP_ALLOWED_DESCRIPTION);
		formBuilder.addField(fieldBuilder.build());

		CompareValidator dateCompareValidator = new CompareValidator();
		dateCompareValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS_EQUAL);
		dateCompareValidator.setBinding(CompareValidator.FIELD_O1, EarlyLoanRepaymentConfig.EARLY_REPAYMENT_MIN_DATE_KEY);
		dateCompareValidator.setBinding(CompareValidator.FIELD_O2, EarlyLoanRepaymentConfig.EARLY_REPAYMENT_MAX_DATE_KEY);
		dateCompareValidator.setMessage(EARLY_REPAYMENT_WRONG_DATES_MESSAGE);
		formBuilder.addFormValidators(dateCompareValidator);

		return formBuilder.build();
	}
}
