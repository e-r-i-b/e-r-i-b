package com.rssl.phizic.web.currency;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.operations.ima.RateOfExchange;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.Map;

/**
 * @ author: Gololobov
 * @ created: 09.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class ShowCurrencyRateForm extends ListFormBase
{
	public static final String FIELD_RATE_ACTUAL_DATE = "rateActualDate";
	public static final String FIELD_DATE_ACTUAL_TIME = "rateActualTime";

	private static final String DATESTAMP_FORMAT = "dd.MM.yyyy";
	private static final String TIMESTAMP_FORMAT = "HH:mm:ss";

	//�� ������������
	private Long id;
	//���������� ���� �����
	private String rateActualDate;
	//���������� ����� �����
	private String rateActualTime;
	private Map<Currency, Map<String, RateOfExchange>> rates;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getRateActualDate()
	{
		return rateActualDate;
	}

	public void setRateActualDate(String rateActualDate)
	{
		this.rateActualDate = rateActualDate;
	}

	public String getRateActualTime()
	{
		return rateActualTime;
	}

	public void setRateActualTime(String rateActualTime)
	{
		this.rateActualTime = rateActualTime;
	}

	public Map<Currency, Map<String, RateOfExchange>> getRates()
	{
		return rates;
	}

	public void setRates(Map<Currency, Map<String, RateOfExchange>> rates)
	{
		this.rates = rates;
	}

	public static final Form FILTER_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		@SuppressWarnings({"TooBroadScope"})
		FieldBuilder fieldBuilder;

		DateParser dateParser = new DateParser(DATESTAMP_FORMAT);
		DateParser timeParser = new DateParser(TIMESTAMP_FORMAT);

		//���������� ���� ������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("date");
		fieldBuilder.setName(FIELD_RATE_ACTUAL_DATE);
		fieldBuilder.setDescription("���� ���������� �� ����");
		// ������� ������������� ��������� ���� ���� dd.MM.yy
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(
					new RequiredFieldValidator("������� ���������� ���� ��� ��������� ������"),
					new DateFieldValidator(DATESTAMP_FORMAT, "���� ������ ���� � ������� ��.��.����"));
		fieldBuilder.setParser(dateParser);
		formBuilder.addField(fieldBuilder.build());

		//���������� ����� ������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("date");
		fieldBuilder.setName(FIELD_DATE_ACTUAL_TIME);
		fieldBuilder.setDescription("���� ���������� �� �����");
		// ������� ������������� ��������� ���� ���� dd.MM.yy
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new DateFieldValidator(TIMESTAMP_FORMAT, "����� ������ ���� � ������� ��:��:CC"));
		fieldBuilder.setParser(timeParser);
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
