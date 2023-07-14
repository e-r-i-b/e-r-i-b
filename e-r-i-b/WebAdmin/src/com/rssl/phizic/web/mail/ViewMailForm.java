package com.rssl.phizic.web.mail;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.MultiLineTextValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.mail.Mail;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author Gainanov
 * @ created 01.03.2007
 * @ $Author$
 * @ $Revision$
 */
public class ViewMailForm extends EditFormBase
{
	public static final Form EDIT_FORM = createForm(false);
	public static final Form MULTI_BLOCK_REASSIGN_FORM = createForm(true);
	private Mail mail;
	private Person sender;
	private boolean isErkc;
	private Boolean fromQuestionary = false;

	/**
	 * @return ��������������� ������
	 */
	public Mail getMail()
	{
		return mail;
	}

	/**
	 * @param mail - ��������������� ������
	 */
	public void setMail(Mail mail)
	{
		this.mail = mail;
	}

	/**
	 * @return �����������
	 */

	public Person getSender()
	{
		return sender;
	}

	/**
	 * @param sender �����������
	 */
	public void setSender(Person sender)
	{
		this.sender = sender;
	}

	/**
	 * @return true - ��������� ����
	 */
	public boolean isErkc()
	{
		return isErkc;
	}

	/**
	 * @param erkc - true - ��������� ����
	 */
	public void setErkc(boolean erkc)
	{
		isErkc = erkc;
	}

	/**
	 * ������� ���� ��� ������� �� ������
	 * @return �� ������ ��/���
	 */
	public Boolean getFromQuestionary()
	{
		return fromQuestionary;
	}

	/**
	 * ������� ���� ��� ������� �� ������
	 * @param fromQuestionary �� ������ ��/���
	 */
	public void setFromQuestionary(Boolean fromQuestionary)
	{
		this.fromQuestionary = fromQuestionary;
	}

	private static Form createForm(boolean multiBlock)
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder;

		// �����
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�����");
		fieldBuilder.setName("employeeLoginId");
		if (!multiBlock)
			fieldBuilder.setType(LongType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());


		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������� �������� ���������");
		fieldBuilder.setName("reassignReason");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator("������� ������� �������� ���������"),
				new MultiLineTextValidator("������� �������� ��������� ������ ���� �� �����", 500)
		);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.employeeLoginId != null"));
		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
