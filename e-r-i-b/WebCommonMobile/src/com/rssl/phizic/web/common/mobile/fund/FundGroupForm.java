package com.rssl.phizic.web.common.mobile.fund;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.MAPIPhoneNumberListValidator;
import com.rssl.phizic.business.fund.initiator.FundGroupPhone;
import org.apache.struts.action.ActionForm;

import java.util.List;

/**
 * @auhor: tisov
 * @ created 08.12.14
 * @ $Author$
 * @ $Revision$
 * Форма управления группой получателей
 */
public class FundGroupForm extends ActionForm
{
	private Long id;                //идентификатор группы
	private String name;            //имя группы
	private String phones;          //список телефонов
	private List<FundGroupPhone> phonesObjectList; //список получателей(объектное представление)
	private Long status;          //статус обработки запроса из мАпи
	private String errorDescription;//текст ошибки

	private static Form FORM = createForm();

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPhones()
	{
		return phones;
	}

	public void setPhones(String phones)
	{
		this.phones = phones;
	}

	public Long getStatus()
	{
		return status;
	}

	public void setStatus(Long status)
	{
		this.status = status;
	}

	public String getErrorDescription()
	{
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription)
	{
		this.errorDescription = errorDescription;
	}

	public List<FundGroupPhone> getPhonesObjectList()
	{
		return phonesObjectList;
	}

	public void setPhonesObjectList(List<FundGroupPhone> phonesObjectList)
	{
		this.phonesObjectList = phonesObjectList;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Список телефонов получателей");
		fieldBuilder.setName("phones");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new MAPIPhoneNumberListValidator(fieldBuilder.getName(),','));
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	public Form getForm()
	{
		return FORM;
	}
}
