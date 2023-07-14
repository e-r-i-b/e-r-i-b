package com.rssl.phizic.web.common.mobile.finances.operations;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.*;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.ArrayList;
import java.util.List;

/**
 * форма добалвения карточной операции из мапи
 * @author Jatsky
 * @ created 05.11.14
 * @ $Author$
 * @ $Revision$
 */

public class AddCardOperationsMobileForm extends EditFormBase
{
	private static final String DATETIMESTAMP = "dd.MM.yyyy'T'HH:mm:ss";
	public static final Form EDIT_FORM = createForm();

	private String operationName;
	private String operationAmount;
	private String operationDate;
	private String operationCategoryId;
	private String operationType;
	private String loginId;
	private String pushUID;
	private String parentPushUID;
	private String cardNumber;
	private String operationAuthCode;
	private String operationTypeWAY;
	private String cash;
	private String hidden;

	/**
	 * @return название операции
	 */
	public String getOperationName()
	{
		return operationName;
	}

	/**
	 * @param operationName - название операции
	 */
	public void setOperationName(String operationName)
	{
		this.operationName = operationName;
	}

	/**
	 * @return сумма операции
	 */
	public String getOperationAmount()
	{
		return operationAmount;
	}

	/**
	 * @param operationAmount - сумма операции
	 */
	public void setOperationAmount(String operationAmount)
	{
		this.operationAmount = operationAmount;
	}

	/**
	 * @return дата операции
	 */
	public String getOperationDate()
	{
		return operationDate;
	}

	/**
	 * @param operationDate - дата операции
	 */
	public void setOperationDate(String operationDate)
	{
		this.operationDate = operationDate;
	}

	/**
	 * @return идентификатор категории операции
	 */
	public String getOperationCategoryId()
	{
		return operationCategoryId;
	}

	/**
	 * @param operationCategoryId - идентификатор категории операции
	 */
	public void setOperationCategoryId(String operationCategoryId)
	{
		this.operationCategoryId = operationCategoryId;
	}

	public String getOperationType()
	{
		return operationType;
	}

	public void setOperationType(String operationType)
	{
		this.operationType = operationType;
	}

	public String getLoginId()
	{
		return loginId;
	}

	public void setLoginId(String loginId)
	{
		this.loginId = loginId;
	}

	public String getPushUID()
	{
		return pushUID;
	}

	public void setPushUID(String pushUID)
	{
		this.pushUID = pushUID;
	}

	public String getParentPushUID()
	{
		return parentPushUID;
	}

	public void setParentPushUID(String parentPushUID)
	{
		this.parentPushUID = parentPushUID;
	}

	public String getCardNumber()
	{
		return cardNumber;
	}

	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	public String getOperationAuthCode()
	{
		return operationAuthCode;
	}

	public void setOperationAuthCode(String operationAuthCode)
	{
		this.operationAuthCode = operationAuthCode;
	}

	public String getOperationTypeWAY()
	{
		return operationTypeWAY;
	}

	public void setOperationTypeWAY(String operationTypeWAY)
	{
		this.operationTypeWAY = operationTypeWAY;
	}

	public String getCash()
	{
		return cash;
	}

	public void setCash(String cash)
	{
		this.cash = cash;
	}

	public String getHidden()
	{
		return hidden;
	}

	public void setHidden(String hidden)
	{
		this.hidden = hidden;
	}

	private static Form createForm()
	{
		List<Field> fields = new ArrayList<Field>();
		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("operationName");
		fieldBuilder.setDescription("Название операции");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredFieldValidator);
		fields.add(fieldBuilder.build());

		//noinspection ReuseOfLocalVariable
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("operationAmount");
		fieldBuilder.setDescription("Сумма");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredFieldValidator, new RegexpFieldValidator("^-?\\d+((\\.|,)\\d{0,2})?$"));
		fields.add(fieldBuilder.build());

		//noinspection ReuseOfLocalVariable
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("operationDate");
		String description = "Дата";
		fieldBuilder.setDescription(description);
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setParser(new DateParser(DATETIMESTAMP));
		DateFieldValidator dateValidator = new DateFieldValidator(DATETIMESTAMP);
		dateValidator.setMessage("Введите корректную дату в поле " + description + " в формате ДД.ММ.ГГГГTЧЧ:ММ:СС");
		fieldBuilder.addValidators(requiredFieldValidator, dateValidator, new DateNotInFutureValidator());
		fields.add(fieldBuilder.build());

		//noinspection ReuseOfLocalVariable
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("operationCategoryId");
		fieldBuilder.setDescription("Категория операции");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredFieldValidator);
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loginId");
		fieldBuilder.setDescription("ID логина пользователя в ЕРИБ");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredFieldValidator);
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("operationType");
		fieldBuilder.setDescription("Способ оплаты");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredFieldValidator);
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("pushUID");
		fieldBuilder.setDescription("идентификатор исходящего сообщения");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("parentPushUID");
		fieldBuilder.setDescription("идентификатор исходящего сообщения для дочерних операций");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("cardNumber");
		fieldBuilder.setDescription("номер карты");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredFieldValidator);
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("operationAuthCode");
		fieldBuilder.setDescription("код авторизации");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("operationTypeWAY");
		fieldBuilder.setDescription("Буквенный тип операции");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("cash");
		fieldBuilder.setDescription("Признак наличной операции");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("hidden");
		fieldBuilder.setDescription("Состояние операции (скрытая/видимая)");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		FormBuilder formBuilder = new FormBuilder();
		formBuilder.setFields(fields);

		return formBuilder.build();
	}
}

