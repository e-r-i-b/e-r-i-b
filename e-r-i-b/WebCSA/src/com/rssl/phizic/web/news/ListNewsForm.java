package com.rssl.phizic.web.news;

import com.rssl.auth.csa.front.business.news.News;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.utils.ListUtil;
import com.rssl.phizic.web.common.forms.expressions.js.RhinoExpression;
import org.apache.struts.action.ActionForm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author basharin
 * @ created 05.09.2012
 * @ $Author$
 * @ $Revision$
 */

public class ListNewsForm extends ActionForm
{
	public static final String DATESTAMP = "dd/MM/yyyy";
	public static final Form NEWS_FORM = createForm();
	private List<News> data = new ArrayList();
	private Map<String,Object> filter = new HashMap<String, Object>();
	private Map<String,Boolean> filterState = new HashMap<String, Boolean>();//Состояние фильтра: свёрнут/развёрнут.

	private String pagination_offset0;//Смещение
	private String pagination_size0;//Количество отображаемых записей

	/**
	 * @return значения полей фильтра
	 */
	public Map<String, Object> getFilters()
	{
		return filter;
	}

	/**
	 * Установить значения полей фильтра
	 * @param filter значения полей
	 */
	public void setFilters(Map<String, Object> filter)
	{
		this.filter = filter;
	}

	/**
	 * Вернуть значение поля фильтра
	 * @param key имя поля
	 * @return значения поля
	 */
	public Object getFilter(String key)
	{
		return filter.get(key);
	}

	/**
	 * Установить значение поля фильтра
	 * @param key имя поля
	 * @param obj значения поля
	 */
	public void setFilter(String key, Object obj)
	{
		filter.put(key, obj);
	}

	public String getPaginationOffset()
	{
		return pagination_offset0;
	}

	public Map<String, Boolean> getFilterState()
	{
		return filterState;
	}

	public void setFilterState(Map<String, Boolean> filterState)
	{
		this.filterState = filterState;
	}

	//Такое название связано с тем, что смещение и количество записей хранятся
	// в полях с названиями $$pagination_offset0 и t$$pagination_size0
	public void set$$pagination_offset0(String pagination_offset0)
	{
		this.pagination_offset0 = pagination_offset0;
	}

	public String getPaginationSize()
	{
		return pagination_size0;
	}

	//Такое название связано с тем, что смещение и количество записей хранятся 
	// в полях с названиями $$pagination_offset0 и t$$pagination_size0
	public void set$$pagination_size0(String pagination_size0)
	{
		this.pagination_size0 = pagination_size0;
	}

	public List<News> getData()
	{
		return data;
	}

	public void setData(List<News> data)
	{
		this.data = data;
	}

	private static Form createForm()
	{
		Expression periodDatesExpression = new RhinoExpression("form.typeDate == 'period' ");
		DateParser dataParser = new DateParser(DATESTAMP);

		DateFieldValidator dataValidator = new DateFieldValidator(DATESTAMP);
		dataValidator.setEnabledExpression(periodDatesExpression);
		dataValidator.setMessage("Введите дату в поле Период в формате ДД/ММ/ГГГГ.");

		RequiredFieldValidator requiredValidator = new  RequiredFieldValidator();
		requiredValidator.setEnabledExpression(periodDatesExpression);

		DateNotInFutureValidator dateNotInFutureValidator = new DateNotInFutureValidator(DATESTAMP);
		dateNotInFutureValidator.setEnabledExpression(periodDatesExpression);

		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("important");
		fieldBuilder.setDescription("Искать среди важных новостей");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тип периода");
		fieldBuilder.setName("typeDate");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new ChooseValueValidator(ListUtil.fromArray(new String[] { "week", "month", "period" } ))
		);
		formBuilder.addField(fieldBuilder.build());


		fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("Период");
	    fieldBuilder.setName("fromDate");
	    fieldBuilder.setType("date");
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredValidator, dataValidator, dateNotInFutureValidator);
	    formBuilder.addField(fieldBuilder.build());

	    fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("Период");
	    fieldBuilder.setName("toDate");
	    fieldBuilder.setType("date");
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredValidator, dataValidator);
	    formBuilder.addField(fieldBuilder.build());

		CompareValidator compareValidator = new CompareValidator();
		compareValidator.setParameter(CompareValidator.OPERATOR, DateTimeCompareValidator.LESS_EQUAL);
		compareValidator.setBinding(CompareValidator.FIELD_O1, "fromDate");
		compareValidator.setBinding(CompareValidator.FIELD_O2, "toDate");
		compareValidator.setMessage("Конечная дата должна быть больше либо равна начальной!");
		compareValidator.setEnabledExpression(periodDatesExpression);

		formBuilder.addFormValidators(compareValidator);

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("search");
		fieldBuilder.setDescription("Слова для поиска");
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
