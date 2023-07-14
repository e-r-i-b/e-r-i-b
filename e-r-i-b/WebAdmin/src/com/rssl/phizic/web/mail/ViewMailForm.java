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
	 * @return просматриваемое письмо
	 */
	public Mail getMail()
	{
		return mail;
	}

	/**
	 * @param mail - просматриваемое письмо
	 */
	public void setMail(Mail mail)
	{
		this.mail = mail;
	}

	/**
	 * @return отправитель
	 */

	public Person getSender()
	{
		return sender;
	}

	/**
	 * @param sender отправитель
	 */
	public void setSender(Person sender)
	{
		this.sender = sender;
	}

	/**
	 * @return true - сотрудник ЕРКЦ
	 */
	public boolean isErkc()
	{
		return isErkc;
	}

	/**
	 * @param erkc - true - сотрудник ЕРКЦ
	 */
	public void setErkc(boolean erkc)
	{
		isErkc = erkc;
	}

	/**
	 * Признак того что перешли из анкеты
	 * @return из анкеты да/нет
	 */
	public Boolean getFromQuestionary()
	{
		return fromQuestionary;
	}

	/**
	 * Признак того что перешли из анкеты
	 * @param fromQuestionary из анкеты да/нет
	 */
	public void setFromQuestionary(Boolean fromQuestionary)
	{
		this.fromQuestionary = fromQuestionary;
	}

	private static Form createForm(boolean multiBlock)
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder;

		// Номер
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Логин");
		fieldBuilder.setName("employeeLoginId");
		if (!multiBlock)
			fieldBuilder.setType(LongType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());


		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Причина передачи обращения");
		fieldBuilder.setName("reassignReason");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator("Укажите причину передачи обращения"),
				new MultiLineTextValidator("Причина передачи обращения должна быть не более", 500)
		);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.employeeLoginId != null"));
		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
