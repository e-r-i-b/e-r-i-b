package com.rssl.phizic.web.news;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.parsers.EnumParser;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.departments.froms.validators.TBValidator;
import com.rssl.phizic.business.news.NewsType;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * User: Zhuravleva
 * Date: 21.07.2006
 * Time: 13:42:21
 */
public class EditNewsForm extends EditFormBase
{
	public static String DATESTAMP = "dd.MM.yyyy";
	public static String TIMESTAMP = "HH:mm";
	public static final Form EDIT_FORM = createForm();
	private Long template;
	private String type;
	private boolean mainNews = false;

	public boolean isMainNews()
	{
		return mainNews;
	}

	public void setMainNews(boolean mainNews)
	{
		this.mainNews = mainNews;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public Long getTemplate()
	{
		return template;
	}

	public void setTemplate(Long template)
	{
		this.template = template;
	}

	@SuppressWarnings({"OverlyLongMethod"})
	private static Form createForm()
	{
		DateParser dateParser = new DateParser(DATESTAMP);
		DateParser timeParser = new DateParser(TIMESTAMP);
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder;

		// Заголовок
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Заголовок");
		fieldBuilder.setName("title");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,100}", "Заголовок должен быть не более 100 символов")
		);
		fb.addField(fieldBuilder.build());

		// Короткий текст новости
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Краткий текст");
		fieldBuilder.setName("shortText");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,150}", "Краткий текст события должен быть не более 150 символов")
		);
		fb.addField(fieldBuilder.build());

		// Текст
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Текст");
		fieldBuilder.setName("text");
		fieldBuilder.setType("string");
	    fieldBuilder.addValidators(
				new RequiredFieldValidator(),
			    new RegexpFieldValidator("(?s).{0,5000}", "Текст события должен быть не более 5000 символов")
		);
		fb.addField(fieldBuilder.build());

		// Дата
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Дата");
		fieldBuilder.setName("newsDate");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(dateParser);
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new DateFieldValidator(DATESTAMP));
		fb.addField(fieldBuilder.build());

		// Время
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Время");
		fieldBuilder.setName("newsDateTime");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(timeParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new DateFieldValidator(TIMESTAMP, "Время должно быть в формате ЧЧ:ММ"));
		fb.addField(fieldBuilder.build());

		// Важность
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Степень важности");
		fieldBuilder.setName("important");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());

		// Статус
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Статус");
		fieldBuilder.setName("state");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());

		// Тип отображения
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тип отображения");
		fieldBuilder.setName("type");
		fieldBuilder.setType("string");
		fieldBuilder.setParser(new EnumParser<NewsType>(NewsType.class));
		fieldBuilder.addValidators(new RequiredFieldValidator(), new EnumFieldValidator<NewsType>(NewsType.class, "Выберите значение Тип отображения."));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();		
		fieldBuilder.setDescription("Тербанк");
		fieldBuilder.setName("TB");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(new RequiredFieldValidator(), new RegexpFieldValidator("^\\d+(,\\d+)*$"), new TBValidator());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Названия тербанков");
		fieldBuilder.setName("departmentName");
		fieldBuilder.setType("string");
		fb.addField(fieldBuilder.build());

		// Автоматически опубликовать
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Автоматически опубликовать");
		fieldBuilder.setName("automaticPublish");
		fieldBuilder.setType("boolean");
		fb.addField(fieldBuilder.build());


		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Автоматически опубликовать(дата)");
		fieldBuilder.setName("automaticPublishDate");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(dateParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new DateFieldValidator(DATESTAMP));
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.automaticPublish == true"));
		fb.addField(fieldBuilder.build());


		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Автоматически опубликовать(время)");
		fieldBuilder.setName("automaticPublishTime");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(timeParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new DateFieldValidator(TIMESTAMP, "Время должно быть в формате ЧЧ:ММ"));
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.automaticPublish == true"));
		fb.addField(fieldBuilder.build());

		//Автоматически снять с публикации
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Автоматически снять с публикации");
		fieldBuilder.setName("cancelPublish");
		fieldBuilder.setType("boolean");
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Автоматически снять с публикации(дата)");
		fieldBuilder.setName("cancelPublishDate");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(dateParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new DateFieldValidator(DATESTAMP));
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.cancelPublish == true"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Автоматически снять с публикации(время)");
		fieldBuilder.setName("cancelPublishTime");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(timeParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new DateFieldValidator(TIMESTAMP, "Время должно быть в формате ЧЧ:ММ"));
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.cancelPublish == true"));
		fb.addField(fieldBuilder.build());

		DateTimeCompareValidator dateTimeCompareValidator = new DateTimeCompareValidator();
		dateTimeCompareValidator.setParameter(DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.LESS);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_DATE, "automaticPublishDate");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_TIME, "automaticPublishTime");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_DATE, "cancelPublishDate");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_TIME, "cancelPublishTime");
		dateTimeCompareValidator.setMessage("Дата снятия с публикации должна быть больше даты публикации!");

		fb.setFormValidators(dateTimeCompareValidator);
		return fb.build();
	}
}
