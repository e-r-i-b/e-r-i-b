package com.rssl.phizic.web.client.passwordcards;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.CompareValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Omeliyanchuk
 * @ created 27.11.2006
 * @ $Author$
 * @ $Revision$
 */

public class LastPasswordOnCardForm extends ActionFormBase
{
	private String oldCardId;
	private Integer needKeyNumber;
	private String returnPath;
	private Boolean success;
	private Boolean hasActiveCard = true;
	private Map<String, Object> fields = new HashMap<String, Object>();

	public Boolean getHasActiveCard()
	{
		return hasActiveCard;
	}

	public void setHasActiveCard(Boolean hasActiveCard)
	{
		this.hasActiveCard = hasActiveCard;
	}

	public Boolean getSuccess()
	{
		return success;
	}

	public void setSuccess(Boolean success)
	{
		this.success = success;
	}

	public String getReturnPath()
	{
		return returnPath;
	}

	public void setReturnPath(String returnPath)
	{
		this.returnPath = returnPath;
	}

	public String getOldCardId()
	{
		return oldCardId;
	}

	public void setOldCardId(String oldCardId)
	{
		this.oldCardId = oldCardId;
	}

	public Integer getNeedKeyNumber()
	{
		return needKeyNumber;
	}

	public void setNeedKeyNumber(Integer needKeyNumber)
	{
		this.needKeyNumber = needKeyNumber;
	}

	public Map<String, Object> getFields()
	{
		return fields;
	}

	public Object getField(String key)
	{
		return fields.get(key);
	}

	public void setField(String key, Object obj)
	{
		fields.put(key, obj);
	}

	public static final Form PSW_FORM = createForm();
	public static final Form PSW_SELF_ASSIGN_FORM = createClientSelfAssignForm();

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();
		RequiredFieldValidator reqValidator = new RequiredFieldValidator();
		RegexpFieldValidator regValidator = new RegexpFieldValidator("\\d*");

		FieldBuilder fieldBuilder;
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("password");
		fieldBuilder.setDescription("Пароль");
		reqValidator.setMessage("Укажите значение в поле Ключ");
		regValidator.setMessage("Поле Ключ может содержать только цифры");
		fieldBuilder.setValidators(reqValidator, regValidator);

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		reqValidator = new RequiredFieldValidator();
		regValidator = new RegexpFieldValidator("\\d*");
		fieldBuilder.setName("number");
		fieldBuilder.setDescription("Номер");
		reqValidator.setMessage("Укажите значение в поле № новой карты паролей");
		regValidator.setMessage("Поле № новой карты паролей может содержать только цифры");
		fieldBuilder.setValidators(reqValidator, regValidator);

		fb.addField(fieldBuilder.build());

		return fb.build();
	}

	private static Form createClientSelfAssignForm()
	{
		FormBuilder fb = new FormBuilder();
		RequiredFieldValidator reqValidator = new RequiredFieldValidator();
		RegexpFieldValidator regValidator = new RegexpFieldValidator("\\d*");

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("number");
		fieldBuilder.setDescription("Номер");
		reqValidator.setMessage("Укажите значение в поле № новой карты паролей");
		regValidator.setMessage("Поле № новой карты паролей может содержать только цифры");
		fieldBuilder.setValidators(reqValidator, regValidator);

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("renumber");
		fieldBuilder.setDescription("Номер");
		reqValidator = new RequiredFieldValidator();
		regValidator = new RegexpFieldValidator("\\d*");
		reqValidator.setMessage("Укажите значение в поле Введите повторно № новой карты паролей");
		regValidator.setMessage("Поле Введите повторно № новой карты паролей может содержать только цифры");
		fieldBuilder.setValidators(reqValidator, regValidator);
		fb.addField(fieldBuilder.build());

		CompareValidator compareValidator = new CompareValidator();
		compareValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.EQUAL);
		compareValidator.setBinding(CompareValidator.FIELD_O1, "number");
		compareValidator.setBinding(CompareValidator.FIELD_O2, "renumber");
		compareValidator.setMessage("Номера карт не совпадают");
		fb.setFormValidators(compareValidator);

		return fb.build();
	}
}
