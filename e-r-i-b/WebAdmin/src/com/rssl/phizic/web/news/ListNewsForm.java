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
	 * @return мап<id новости которой принадлежит рассылка, рассылка>
	 */
	public Map<Long, NewsDistribution> getDistributions()
	{
		return distributions;
	}

	/**
	 * Установить список рассылок
	 * @param distributions - мап<id новости которой принадлежит рассылка, рассылка>
	 */
	public void setDistributions(Map<Long, NewsDistribution> distributions)
	{
		this.distributions = distributions;
	}

	private static Form createForm()
    {
        FormBuilder formBuilder = new FormBuilder();

        FieldBuilder fieldBuilder;

	    // Тема
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тема");
		fieldBuilder.setName("title");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

	    //Тип письма
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Статус");
		fieldBuilder.setName("state");
		fieldBuilder.setType("string");
	    formBuilder.addField(fieldBuilder.build());

	    fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Важность");
		fieldBuilder.setName("important");
		fieldBuilder.setType("string");
	    formBuilder.addField(fieldBuilder.build());

	    fieldBuilder = new FieldBuilder();
        fieldBuilder.setName("fromDate");
        fieldBuilder.setDescription("дата");
	    fieldBuilder.setType("date");
        formBuilder.addField(fieldBuilder.build());

	    fieldBuilder = new FieldBuilder();
        fieldBuilder.setName("toDate");
	    fieldBuilder.setType("date");
        fieldBuilder.setDescription("дата");
	    formBuilder.addField(fieldBuilder.build());

	    DateTimeCompareValidator dateTimeCompareValidator = new DateTimeCompareValidator();
		dateTimeCompareValidator.setParameter(DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.LESS_EQUAL);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_DATE, "fromDate");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_TIME, "fromTime");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_DATE, "toDate");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_TIME, "toTime");
		dateTimeCompareValidator.setMessage("Конечная дата должна быть больше либо равна начальной!");
		formBuilder.addFormValidators(dateTimeCompareValidator);

        return formBuilder.build();
    }
}
