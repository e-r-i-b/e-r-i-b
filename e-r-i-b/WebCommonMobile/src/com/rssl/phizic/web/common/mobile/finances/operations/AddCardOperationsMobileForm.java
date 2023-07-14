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
 * ����� ���������� ��������� �������� �� ����
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
	 * @return �������� ��������
	 */
	public String getOperationName()
	{
		return operationName;
	}

	/**
	 * @param operationName - �������� ��������
	 */
	public void setOperationName(String operationName)
	{
		this.operationName = operationName;
	}

	/**
	 * @return ����� ��������
	 */
	public String getOperationAmount()
	{
		return operationAmount;
	}

	/**
	 * @param operationAmount - ����� ��������
	 */
	public void setOperationAmount(String operationAmount)
	{
		this.operationAmount = operationAmount;
	}

	/**
	 * @return ���� ��������
	 */
	public String getOperationDate()
	{
		return operationDate;
	}

	/**
	 * @param operationDate - ���� ��������
	 */
	public void setOperationDate(String operationDate)
	{
		this.operationDate = operationDate;
	}

	/**
	 * @return ������������� ��������� ��������
	 */
	public String getOperationCategoryId()
	{
		return operationCategoryId;
	}

	/**
	 * @param operationCategoryId - ������������� ��������� ��������
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
		fieldBuilder.setDescription("�������� ��������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredFieldValidator);
		fields.add(fieldBuilder.build());

		//noinspection ReuseOfLocalVariable
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("operationAmount");
		fieldBuilder.setDescription("�����");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredFieldValidator, new RegexpFieldValidator("^-?\\d+((\\.|,)\\d{0,2})?$"));
		fields.add(fieldBuilder.build());

		//noinspection ReuseOfLocalVariable
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("operationDate");
		String description = "����";
		fieldBuilder.setDescription(description);
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setParser(new DateParser(DATETIMESTAMP));
		DateFieldValidator dateValidator = new DateFieldValidator(DATETIMESTAMP);
		dateValidator.setMessage("������� ���������� ���� � ���� " + description + " � ������� ��.��.����T��:��:��");
		fieldBuilder.addValidators(requiredFieldValidator, dateValidator, new DateNotInFutureValidator());
		fields.add(fieldBuilder.build());

		//noinspection ReuseOfLocalVariable
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("operationCategoryId");
		fieldBuilder.setDescription("��������� ��������");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredFieldValidator);
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loginId");
		fieldBuilder.setDescription("ID ������ ������������ � ����");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredFieldValidator);
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("operationType");
		fieldBuilder.setDescription("������ ������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredFieldValidator);
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("pushUID");
		fieldBuilder.setDescription("������������� ���������� ���������");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("parentPushUID");
		fieldBuilder.setDescription("������������� ���������� ��������� ��� �������� ��������");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("cardNumber");
		fieldBuilder.setDescription("����� �����");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredFieldValidator);
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("operationAuthCode");
		fieldBuilder.setDescription("��� �����������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("operationTypeWAY");
		fieldBuilder.setDescription("��������� ��� ��������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("cash");
		fieldBuilder.setDescription("������� �������� ��������");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("hidden");
		fieldBuilder.setDescription("��������� �������� (�������/�������)");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		FormBuilder formBuilder = new FormBuilder();
		formBuilder.setFields(fields);

		return formBuilder.build();
	}
}

