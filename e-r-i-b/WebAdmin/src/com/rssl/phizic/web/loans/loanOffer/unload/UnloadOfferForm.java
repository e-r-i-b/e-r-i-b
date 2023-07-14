package com.rssl.phizic.web.loans.loanOffer.unload;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.DatePeriodMultiFieldValidator;
import com.rssl.common.forms.validators.DateTimeCompareValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.pereodicalTask.PereodicalTaskError;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Moshenko
 * Date: 17.06.2011
 * Time: 11:43:08
 * ����� �������� ������ �� ��������� ��������
 */
public class UnloadOfferForm extends ListFormBase
{
	public static String DATESTAMP = "dd.MM.yyyy";
	public static String TIMESTAMP = "HH:mm:ss";

	private Long unloadCount = 0L; /*���������� �����������*/
	private Long errorsCount = 0L; /*���������� ������*/
	private List<PereodicalTaskError> errors = new ArrayList<PereodicalTaskError>(); /*������� ��������*/

	public List<PereodicalTaskError> getErrors()
	{
		return errors;
	}

	public void setErrors(List<PereodicalTaskError> errors)
	{
		this.errors = errors;
	}

	public Long getUnloadCount()
	{
		return unloadCount;
	}

	public void setUnloadCount(Long unloadCount)
	{
		this.unloadCount = unloadCount;
	}

	public Long getErrorsCount()
	{
		return errorsCount;
	}

	public void setErrorsCount(Long errorsCount)
	{
		this.errorsCount = errorsCount;
	}

	/**
	 * 7 ���� � �������������
	 */
	private static final Long MAX_DATE_SPAN = 7 * 24 * 3600 * 1000L;

	public static Form createForm()
	{
		DateParser dateParser = new DateParser(DATESTAMP);
		DateParser timeParser = new DateParser(TIMESTAMP);

		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� ���������� ��������");
		fieldBuilder.setName("type");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RequiredFieldValidator()
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������ ��������");
		fieldBuilder.setName("fromDate");
		fieldBuilder.setType("date");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new DateFieldValidator(DATESTAMP, "���� ������ ���� � ������� ��.��.����")
		);
		fieldBuilder.setParser(dateParser);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������ ��������");
		fieldBuilder.setName("fromTime");
		fieldBuilder.setType("date");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new DateFieldValidator(TIMESTAMP, "����� ������ ���� � ������� ��:��:CC")
		);
		fieldBuilder.setParser(timeParser);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������ ��������");
		fieldBuilder.setName("toDate");
		fieldBuilder.setType("date");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new DateFieldValidator(DATESTAMP, "���� ������ ���� � ������� ��.��.����")
		);
		fieldBuilder.setParser(dateParser);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������ ��������");
		fieldBuilder.setName("toTime");
		fieldBuilder.setType("date");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new DateFieldValidator(TIMESTAMP, "����� ������ ���� � ������� ��:��:CC")
		);
		fieldBuilder.setParser(timeParser);
		fb.addField(fieldBuilder.build());

		DateTimeCompareValidator dateTimeCompareValidator = new DateTimeCompareValidator();
		dateTimeCompareValidator.setParameter(DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.LESS_EQUAL);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_DATE,   "fromDate");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_TIME,   "fromTime");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_DATE,     "toDate");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_TIME,     "toTime");
		dateTimeCompareValidator.setMessage("�������� ���� ������ ���� ������ ���� ����� ���������!");

		DatePeriodMultiFieldValidator datePeriodValidator = new DatePeriodMultiFieldValidator();
		datePeriodValidator.setMaxTimeSpan(MAX_DATE_SPAN);
		datePeriodValidator.setBinding(DatePeriodMultiFieldValidator.FIELD_DATE_FROM, "fromDate");
		datePeriodValidator.setBinding(DatePeriodMultiFieldValidator.FIELD_DATE_TO, "toDate");
		datePeriodValidator.setMessage("������ �� ����� ��������� 7 ����!");
	    fb.setFormValidators(dateTimeCompareValidator,datePeriodValidator);
		return fb.build();
	}
}
