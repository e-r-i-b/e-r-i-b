package com.rssl.phizic.web.ext.sbrf.mail;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.mail.MailConfig;
import com.rssl.phizic.config.ConfigFactory;
import org.apache.commons.lang.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kligina
 * @ created 06.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class EditMailForm extends com.rssl.phizic.web.mail.EditMailForm
{
	private static final List<Field> mainFields = getMainFields();
	private static final List<Field> multiBlockEditFormFields = createFormBuilder(true);
	private static final List<Field> singleBlockEditFormFields = createFormBuilder(false);
	private String answerType;
	private Long parentId;

	public String getAnswerType()
	{
		return answerType;
	}

	public void setAnswerType(String answerType)
	{
		this.answerType = answerType;
	}

	public String getEmployeeTextLength()
	{
		return Long.toString(ConfigFactory.getConfig(MailConfig.class).getEmployeeTextLength());
	}

	/**
	 * задать идентификатор родительского письма
	 * @param parentId идентификатор родительского письма
	 */
	public void setParentId(Long parentId)
	{
		this.parentId = parentId;
	}

	/**
	 * @return идентификатор родительского письма
	 */
	public Long getParentId()
	{
		return parentId;
	}

	private static Field getStringField(String name, String description, FieldValidator... validators)
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(name);
		fieldBuilder.setDescription(description);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		if (ArrayUtils.isNotEmpty(validators))
			fieldBuilder.addValidators(validators);
		return fieldBuilder.build();
	}

	private static Field getLongField(String name, String description)
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(name);
		fieldBuilder.setDescription(description);
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		return fieldBuilder.build();
	}

	private static List<Field> getMainFields()
	{
		List<Field> fields = new ArrayList<Field>();

		fields.addAll(fieldsBuid().build().getFields());

		fields.add(getStringField("type",           "Тип",           new RequiredFieldValidator()));
		fields.add(getStringField("mailState",      "Cтатус письма", new RequiredFieldValidator()));
		fields.add(getStringField("newMailState",   "Cтатус письма", new RequiredFieldValidator()));

		return fields;
	}

	private static List<Field> createFormBuilder(boolean isMultiBlock)
	{
		List<Field> fields = new ArrayList<Field>();

		fields.addAll(mainFields);

		if (isMultiBlock)
			fields.addAll(recipientMultiBlockFields());
		else
			fields.add(getLongField("recipientId", "ИД получателя"));

		return fields;
	}

	private static List<Field> recipientMultiBlockFields()
	{
		List<Field> fields = new ArrayList<Field>();

		fields.add(getStringField("firstName", "Получатель", new RequiredFieldValidator()));
		fields.add(getStringField("surname",    "Получатель", new RequiredFieldValidator()));
		fields.add(getStringField("patrname",   "Получатель"));
		fields.add(getStringField("tb",         "Получатель", new RequiredFieldValidator()));
		fields.add(getStringField("passport",   "Получатель", new RequiredFieldValidator()));
		fields.add(getLongField("nodeId",       "Получатель"));

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Получатель");
		fieldBuilder.setName("birthDate");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fields.add(fieldBuilder.build());

		return fields;
	}

	private static List<Field> getTextFields()
	{
		List<Field> fields = new ArrayList<Field>();

		String employeeTextLength = Long.toString(ConfigFactory.getConfig(MailConfig.class).getEmployeeTextLength());

		fields.add(getStringField("newText", "Сообщение",
				new RequiredFieldValidator(),
				new RegexpFieldValidator("(?s).{0," + employeeTextLength + "}", "Текст сообщения должен быть не более " + employeeTextLength + " символов")
		));
		fields.add(getStringField("body", "Сообщение",
				new RequiredFieldValidator(),
				new RegexpFieldValidator("(?s).{0," + employeeTextLength + "}", "Текст сообщения должен быть не более " + employeeTextLength + " символов")
		));
		return fields;
	}

	/**
	 * @return форма создания письма в многоблочном режиме
	 */
	public static Form getCreateNewMailMultiBlockModeForm()
	{
		FormBuilder fb = new FormBuilder();

		fb.addFields(multiBlockEditFormFields);
		fb.addFields(getTextFields());

		return fb.build();
	}

	/**
	 * @return форма создания письма в одноблочном режиме
	 */
	public static Form getCreateNewMailSingleBlockModeForm()
	{
		FormBuilder fb = new FormBuilder();

		fb.addFields(singleBlockEditFormFields);
		fb.addFields(getTextFields());

		return fb.build();
	}

	/**
	 * @return форма ответа на письмо
	 */
	public static Form getReplyForm()
	{
		FormBuilder fb = new FormBuilder();

		fb.addFields(mainFields);
		fb.addFields(getTextFields());

		return fb.build();
	}
}
