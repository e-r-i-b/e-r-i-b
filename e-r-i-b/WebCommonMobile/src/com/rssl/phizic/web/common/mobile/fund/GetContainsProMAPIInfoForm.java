package com.rssl.phizic.web.common.mobile.fund;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.MAPIPhoneNumberListValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.fund.validators.PhonesMazSizeValidator;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.List;
import java.util.Map;

/**
 * @author osminin
 * @ created 16.04.15
 * @ $Author$
 * @ $Revision$
 *
 * Форма получения информации о налии мАпи ПРО версии у контактов
 */
public class GetContainsProMAPIInfoForm extends ActionFormBase
{
	public static final String PHONES_PARAM = "phones";

	public static final Form FORM = createForm();

	private Map<String, Boolean> infoMap;
	private List<String> phoneNumbers;

	/**
	 * @return информация о наличии мАпи про-версии
	 */
	public Map<String, Boolean> getInfoMap()
	{
		return infoMap;
	}

	/**
	 * @param infoMap информация о наличии мАпи про-версии
	 */
	public void setInfoMap(Map<String, Boolean> infoMap)
	{
		this.infoMap = infoMap;
	}

	/**
	 * @return список телефонов
	 */
	public List<String> getPhoneNumbers()
	{
		return phoneNumbers;
	}

	/**
	 * @param phoneNumbers список телефонов
	 */
	public void setPhoneNumbers(List<String> phoneNumbers)
	{
		this.phoneNumbers = phoneNumbers;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(PHONES_PARAM);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Список контактов");

		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new MAPIPhoneNumberListValidator(fieldBuilder.getName(),','),
				new PhonesMazSizeValidator()
		);

		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
