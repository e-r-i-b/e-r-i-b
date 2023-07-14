package com.rssl.phizic.web.webApi.protocol.jaxb.model.request;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.web.component.DatePeriodFilter;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/** Тело запроса структуры расходов по категориям
 * @author Jatsky
 * @ created 20.05.14
 * @ $Author$
 * @ $Revision$
 */

@XmlRootElement(name = "body")
public class ALFCategoryFilterRequestBody extends SimpleRequestBody
{
	private String from; //дата с
	private String to; //дата по
	private String showCash; //показать внесение/выдачу наличных
	private String showCashPayments; //Отображать траты наличными
	private String selectedId; // id карты/группа карт, по которой отображать расходы
	private Boolean showTransfers; // исключаемые категории операций
	private Boolean showCents; // признак отображения копеек

	public static final Form FILTER_FORM = createForm();

	@XmlElement(name = "from", required = true)
	public String getFrom()
	{
		return from;
	}

	public void setFrom(String from)
	{
		this.from = from;
	}

	@XmlElement(name = "to", required = true)
	public String getTo()
	{
		return to;
	}

	public void setTo(String to)
	{
		this.to = to;
	}

	@XmlElement(name = "showCash", required = true)
	public String getShowCash()
	{
		return showCash;
	}

	public void setShowCash(String showCash)
	{
		this.showCash = showCash;
	}

	@XmlElement(name = "showCashPayments", required = true)
	public String getShowCashPayments()
	{
		return showCashPayments;
	}

	public void setShowCashPayments(String showCashPayments)
	{
		this.showCashPayments = showCashPayments;
	}

	@XmlElement(name = "selectedId", required = false)
	public String getSelectedId()
	{
		return selectedId;
	}

	public void setSelectedId(String selectedId)
	{
		this.selectedId = selectedId;
	}

	@XmlElement(name = "showTransfers", required = false)
	public Boolean getShowTransfers()
	{
		return showTransfers;
	}

	public void setShowTransfers(Boolean showTransfers)
	{
		this.showTransfers = showTransfers;
	}

	@XmlElement(name = "showCents", required = false)
	public Boolean isShowCents()
	{
		return showCents;
	}

	public void setShowCents(Boolean showCents)
	{
		this.showCents = showCents;
	}

	private static Form createForm()
	{
		List<Field> fields = new ArrayList<Field>();
		FieldBuilder fieldBuilder;

		DateParser dataParser = new DateParser();

		DateFieldValidator datePeriodValidator = new DateFieldValidator();
		datePeriodValidator.setMessage("Введите дату в поле Период в формате ДД.ММ.ГГГГ");

		DateInYearBeforeCurrentDateValidator dateFieldValidator = new DateInYearBeforeCurrentDateValidator();
		dateFieldValidator.setMessage("Вы можете просмотреть движение средств только за последний год.");

		RequiredFieldValidator requiredValidator = new RequiredFieldValidator();

		// Период
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("from");
		fieldBuilder.setDescription("Период");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredValidator, datePeriodValidator, dateFieldValidator);
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("to");
		fieldBuilder.setDescription("Период");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredValidator, datePeriodValidator, dateFieldValidator);
		fields.add(fieldBuilder.build());

		// включать операции с наличными
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("showCash");
		fieldBuilder.setDescription("Показать внесение/выдачу наличных");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredValidator);
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("showCashPayments");
		fieldBuilder.setDescription("Отображать траты наличными");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredValidator);
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("selectedId");
		fieldBuilder.setDescription("Карта");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("^(\\d*|allCards|mainCards|debitCards|overdraftCards|creditCards|ownAdditionalCards|otherAdditionalCards|allCardsAndCash|-1)$"));
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("showTransfers");
		fieldBuilder.setDescription("Признак отображения переводов");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("showCents");
		fieldBuilder.setDescription("Признак отображения копеек");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		FormBuilder formBuilder = new FormBuilder();
		formBuilder.setFields(fields);

		CompareValidator compareDateValidator = new CompareValidator();
		compareDateValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS_EQUAL);
		compareDateValidator.setBinding(CompareValidator.FIELD_O1, DatePeriodFilter.FROM_DATE);
		compareDateValidator.setBinding(CompareValidator.FIELD_O2, DatePeriodFilter.TO_DATE);
		compareDateValidator.setMessage("Конечная дата должна быть больше начальной!");

		formBuilder.setFormValidators(compareDateValidator);

		return formBuilder.build();
	}
}
