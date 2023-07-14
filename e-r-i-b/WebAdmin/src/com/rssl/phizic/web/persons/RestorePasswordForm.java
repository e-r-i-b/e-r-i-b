package com.rssl.phizic.web.persons;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.List;

/**
 * @author vagin
 * @ created 19.09.2012
 * @ $Author$
 * @ $Revision$
 * ����� �������������� ������ ������� ����� ��� ����������
 */
public class RestorePasswordForm extends EditFormBase
{
	public static final Form FORM = createForm();

	private List<CardLink> cardLinks;
	private ActivePerson activePerson;
	private boolean modified = false;
	private boolean failureIdentification = false;
	private boolean failureIMSICheck = false;

	public List<CardLink> getCardLinks()
	{
		return cardLinks;
	}

	public void setCardLinks(List<CardLink> links)
	{
		this.cardLinks = links;
	}

	public ActivePerson getActivePerson()
	{
		return activePerson;
	}

	public void setActivePerson(ActivePerson activePerson)
	{
		this.activePerson = activePerson;
	}

	public boolean isModified()
	{
		return modified;
	}

	public void setModified(boolean modified)
	{
		this.modified = modified;
	}

	/**
	 * ���������� ������� �������� �� ������������� �������
	 * @param failureIdentification ������� �������� �� �������������
	 */
	public void setFailureIdentification(boolean failureIdentification)
	{
		this.failureIdentification = failureIdentification;
	}

	/**
	 * @return ������� �������� �� �������������
	 */
	public boolean isFailureIdentification()
	{
		return failureIdentification;
	}

	public boolean isFailureIMSICheck()
	{
		return failureIMSICheck;
	}

	public void setFailureIMSICheck(boolean failureIMSICheck)
	{
		this.failureIMSICheck = failureIMSICheck;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		//��� �������������� ������ (�� �����, �� ������������ ������)
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("restoreType");
		fieldBuilder.setType("string");
		fieldBuilder.setDescription("��� �������������� ������");
		fieldBuilder.addValidators(new RegexpFieldValidator("(login)|(card)", "������������ ��� �������������� ������."));
		formBuilder.addField(fieldBuilder.build());

		//������������� �����
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("cardId");
		fieldBuilder.setType("string");
		fieldBuilder.setDescription("������������� �����");

		RequiredFieldValidator regexpValidator = new RequiredFieldValidator();
		regexpValidator.setEnabledExpression(new RhinoExpression("form.restoreType == 'card'"));

		fieldBuilder.addValidators(regexpValidator);
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
