package com.rssl.phizic.web.pfp.ajax.loan;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.BigDecimalParser;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.parsers.FieldValueParser;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.pfp.products.loan.LoanKindProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.loan.LoanKindProductService;
import com.rssl.phizic.business.pfp.PersonLoan;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.EditFormBase;

import java.io.Serializable;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.List;

/**
 * @author mihaylov
 * @ created 22.07.2013
 * @ $Author$
 * @ $Revision$
 */
public class EditPfpLoanForm extends EditFormBase
{
	private static final String DATE_FORMAT = "dd/MM/yyyy";

	private static final LoanKindProductService loanKindProductService = new LoanKindProductService();
	private static final FieldValueParser LOAN_KIND_PRODUCT_PARSER = getParser();
	private static final FieldValueParser DATE_PARSER = new DateParser(DATE_FORMAT);
	private static final DateFieldValidator DATE_FIELD_VALIDATOR = new DateFieldValidator(DATE_FORMAT, "Пожалуйста, укажите корректную дату в поле \"Оформление кредита\".");

	private Long profileId;
	private Long loanId;

	private PersonLoan personLoan;

	public Long getProfileId()
	{
		return profileId;
	}

	public void setProfileId(Long profileId)
	{
		this.profileId = profileId;
	}

	public Long getLoanId()
	{
		return loanId;
	}

	public void setLoanId(Long loanId)
	{
		this.loanId = loanId;
	}

	public PersonLoan getPersonLoan()
	{
		return personLoan;
	}

	public void setPersonLoan(PersonLoan personLoan)
	{
		this.personLoan = personLoan;
	}

	public static Form createForm(List<LoanKindProduct> loanDictionary)
	{
		DateAfterStartQuarterValidator minDateFieldValidator = new DateAfterStartQuarterValidator(DATE_FORMAT);
		minDateFieldValidator.setMessage("Вы можете оформить кредит, начиная со следующего квартала.");

		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тип кредита");
		fieldBuilder.setName("dictionaryLoan");
		fieldBuilder.setParser(LOAN_KIND_PRODUCT_PARSER);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Краткий комментарий");
		fieldBuilder.setName("loanComment");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new LengthFieldValidator(BigInteger.valueOf(100)));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Сумма");
		fieldBuilder.setName("loanAmount");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		for(LoanKindProduct loanKindProduct: loanDictionary)
		{
			NumericRangeValidator minAmountRangeValidator = new NumericRangeValidator();
			minAmountRangeValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, loanKindProduct.getFromAmount().toString());
			minAmountRangeValidator.setEnabledExpression(new RhinoExpression("form.dictionaryLoan.id == " + loanKindProduct.getId()));
			minAmountRangeValidator.setMessage("Сумма Вашего кредита не может быть меньше минимально допустимой суммы кредита.");
			fieldBuilder.addValidators(minAmountRangeValidator);

			NumericRangeValidator maxAmountRangeValidator = new NumericRangeValidator();
			maxAmountRangeValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, loanKindProduct.getToAmount().toString());
			maxAmountRangeValidator.setEnabledExpression(new RhinoExpression("form.dictionaryLoan.id == " + loanKindProduct.getId()));
			maxAmountRangeValidator.setMessage("Сумма Вашего кредита не может быть больше максимально допустимой суммы кредита.");
			fieldBuilder.addValidators(maxAmountRangeValidator);
		}
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Начало платежей");
		fieldBuilder.setName("startDate");
		fieldBuilder.addValidators(DATE_FIELD_VALIDATOR, minDateFieldValidator);
		fieldBuilder.setParser(DATE_PARSER);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Окончание платежей");
		fieldBuilder.setName("endDate");
		fieldBuilder.addValidators(DATE_FIELD_VALIDATOR);
		fieldBuilder.setParser(DATE_PARSER);

		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Ставка по кредиту");
		fieldBuilder.setName("loanRate");
		fieldBuilder.setParser(new BigDecimalParser(1));
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	private static FieldValueParser getParser()
	{
		return new FieldValueParser()
		{
			public Serializable parse(String value) throws ParseException
			{
				if (StringHelper.isEmpty(value))
					return null;

				try
				{
					return loanKindProductService.getById(Long.valueOf(value));
				}
				catch (BusinessException e)
				{
					throw new ParseException(e.getMessage(),0);
				}
			}
		};
	}

}
