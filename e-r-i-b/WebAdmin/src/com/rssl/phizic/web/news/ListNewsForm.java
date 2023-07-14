package com.rssl.phizic.web.news;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.DateTimeCompareValidator;
import com.rssl.phizic.business.news.News;
import com.rssl.phizic.business.news.NewsDistribution;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Zhuravleva
 * Date: 19.07.2006
 * Time: 13:28:58
 */                           
public class ListNewsForm extends ListFormBase<News>
{
	public static Form FILTER_FORM = createForm();
	private boolean mainNews = false;
	private Map<Long, NewsDistribution> distributions = new HashMap<Long, NewsDistribution>();

	public boolean isMainNews()
	{
		return mainNews;
	}

	public void setMainNews(boolean mainNews)
	{
		this.mainNews = mainNews;
	}

	/**
	 * @return ���<id ������� ������� ����������� ��������, ��������>
	 */
	public Map<Long, NewsDistribution> getDistributions()
	{
		return distributions;
	}

	/**
	 * ���������� ������ ��������
	 * @param distributions - ���<id ������� ������� ����������� ��������, ��������>
	 */
	public void setDistributions(Map<Long, NewsDistribution> distributions)
	{
		this.distributions = distributions;
	}

	private static Form createForm()
    {
        FormBuilder formBuilder = new FormBuilder();

        FieldBuilder fieldBuilder;

	    // ����
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����");
		fieldBuilder.setName("title");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

	    //��� ������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������");
		fieldBuilder.setName("state");
		fieldBuilder.setType("string");
	    formBuilder.addField(fieldBuilder.build());

	    fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������");
		fieldBuilder.setName("important");
		fieldBuilder.setType("string");
	    formBuilder.addField(fieldBuilder.build());

	    fieldBuilder = new FieldBuilder();
        fieldBuilder.setName("fromDate");
        fieldBuilder.setDescription("����");
	    fieldBuilder.setType("date");
        formBuilder.addField(fieldBuilder.build());

	    fieldBuilder = new FieldBuilder();
        fieldBuilder.setName("toDate");
	    fieldBuilder.setType("date");
        fieldBuilder.setDescription("����");
	    formBuilder.addField(fieldBuilder.build());

	    DateTimeCompareValidator dateTimeCompareValidator = new DateTimeCompareValidator();
		dateTimeCompareValidator.setParameter(DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.LESS_EQUAL);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_DATE, "fromDate");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_TIME, "fromTime");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_DATE, "toDate");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_TIME, "toTime");
		dateTimeCompareValidator.setMessage("�������� ���� ������ ���� ������ ���� ����� ���������!");
		formBuilder.addFormValidators(dateTimeCompareValidator);

        return formBuilder.build();
    }
}
