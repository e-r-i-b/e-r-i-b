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
	private Map<String,Boolean> filterState = new HashMap<String, Boolean>();//��������� �������: ������/��������.

	private String pagination_offset0;//��������
	private String pagination_size0;//���������� ������������ �������

	/**
	 * @return �������� ����� �������
	 */
	public Map<String, Object> getFilters()
	{
		return filter;
	}

	/**
	 * ���������� �������� ����� �������
	 * @param filter �������� �����
	 */
	public void setFilters(Map<String, Object> filter)
	{
		this.filter = filter;
	}

	/**
	 * ������� �������� ���� �������
	 * @param key ��� ����
	 * @return �������� ����
	 */
	public Object getFilter(String key)
	{
		return filter.get(key);
	}

	/**
	 * ���������� �������� ���� �������
	 * @param key ��� ����
	 * @param obj �������� ����
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

	//����� �������� ������� � ���, ��� �������� � ���������� ������� ��������
	// � ����� � ���������� $$pagination_offset0 � t$$pagination_size0
	public void set$$pagination_offset0(String pagination_offset0)
	{
		this.pagination_offset0 = pagination_offset0;
	}

	public String getPaginationSize()
	{
		return pagination_size0;
	}

	//����� �������� ������� � ���, ��� �������� � ���������� ������� �������� 
	// � ����� � ���������� $$pagination_offset0 � t$$pagination_size0
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
		dataValidator.setMessage("������� ���� � ���� ������ � ������� ��/��/����.");

		RequiredFieldValidator requiredValidator = new  RequiredFieldValidator();
		requiredValidator.setEnabledExpression(periodDatesExpression);

		DateNotInFutureValidator dateNotInFutureValidator = new DateNotInFutureValidator(DATESTAMP);
		dateNotInFutureValidator.setEnabledExpression(periodDatesExpression);

		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("important");
		fieldBuilder.setDescription("������ ����� ������ ��������");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� �������");
		fieldBuilder.setName("typeDate");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new ChooseValueValidator(ListUtil.fromArray(new String[] { "week", "month", "period" } ))
		);
		formBuilder.addField(fieldBuilder.build());


		fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("������");
	    fieldBuilder.setName("fromDate");
	    fieldBuilder.setType("date");
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredValidator, dataValidator, dateNotInFutureValidator);
	    formBuilder.addField(fieldBuilder.build());

	    fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("������");
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
		compareValidator.setMessage("�������� ���� ������ ���� ������ ���� ����� ���������!");
		compareValidator.setEnabledExpression(periodDatesExpression);

		formBuilder.addFormValidators(compareValidator);

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("search");
		fieldBuilder.setDescription("����� ��� ������");
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
