package com.rssl.phizic.web.groups;

import com.rssl.phizic.web.common.ListLimitActionForm;
import com.rssl.phizic.business.departments.Department;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.DateFieldValidator;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA. User: Omeliyanchuk Date: 13.11.2006 Time: 15:18:43 To change this template use
 * File | Settings | File Templates.
 */
public class GroupsContainPersonForm extends ListLimitActionForm
{
	public static String DATESTAMP = "dd.MM.yyyy";
	public static final Form FILTER_FORM = createForm();

	private Long id;
	private Long mailId;
	private String personLogins;
	private String category;
	//список ТБ, доступных сотруднику
	private List<Department> allowedTB = new ArrayList<Department>();
    private String groupName; //название группы

	public Long getMailId()
	{
		return mailId;
	}

	public void setMailId(Long mailId)
	{
		this.mailId = mailId;
	}

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}
	public String getPersonLogins()
	{
		return personLogins;
	}

	public void setPersonLogins(String personLogins)
	{
		this.personLogins = personLogins;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public List<Department> getAllowedTB()
	{
		return allowedTB;
	}

	public void setAllowedTB(List<Department> allowedTB)
	{
		this.allowedTB = allowedTB;
	}

    public String getGroupName()
    {
        return groupName;
    }

    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
    }

    private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		DateParser dateParser = new DateParser(DATESTAMP);

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("id");
		fieldBuilder.setName("id");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Клиент");
		fieldBuilder.setName("fio");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Мобильный телефон");
		fieldBuilder.setName("mobile_phone");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());
		fieldBuilder.addValidators(
                new RegexpFieldValidator("\\d*", "Поле Мобильный телефон должно содержать только цифры")
		);

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Фамилия");
		fieldBuilder.setName("surName");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Имя");
		fieldBuilder.setName("firstName");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Отчество");
		fieldBuilder.setName("patrName");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Login_ID");
		fieldBuilder.setName("loginId");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(new RegexpFieldValidator("^\\d*$","Login_ID может содержать только цифры"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Номер документа");
		fieldBuilder.setName("documentNumber");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
                new RegexpFieldValidator("\\d*", "Поле номер документа должно содержать только цифры"),
                new RegexpFieldValidator(".{0,16}", "Поле номер документа не должно превышать 16 цифр")
				);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Серия документа");
		fieldBuilder.setName("documentSeries");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,16}", "Поле серия документа не должно превышать 16 символов"),
				new RegexpFieldValidator("[^<>!#$%^&*{}|\\]\\[''\"~=]*","Поле серия документа не должно содержать спецсимволов")
				);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тип документа");
		fieldBuilder.setName("documentType");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Номер договора");
		fieldBuilder.setName("agreementNumber");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Статус");
		fieldBuilder.setName("state");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тип договора");
		fieldBuilder.setName("creationType");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Дата рождения");
		fieldBuilder.setName("birthDay");
		fieldBuilder.setType("date");
		fieldBuilder.addValidators(	new DateFieldValidator(DATESTAMP, "Дата должна быть в формате ДД.ММ.ГГГГ"));
		fieldBuilder.setParser(dateParser);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тербанк");
		fieldBuilder.setName("terBank");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
