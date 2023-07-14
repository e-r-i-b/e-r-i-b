package com.rssl.phizic.web.configure.exceptions;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.ConstantExpression;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.claims.forms.validators.IsCheckedMultiFieldValidator;
import com.rssl.phizic.business.claims.forms.validators.RequiredMultiFieldValidator;
import com.rssl.phizic.business.claims.forms.validators.UniqueCombinationTBsAndChannelsValidator;
import com.rssl.phizic.business.claims.forms.validators.UniqueTbValidator;
import com.rssl.phizic.business.exception.ExceptionEntryApplication;
import com.rssl.phizic.business.exception.ExceptionEntryType;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 19.04.2013
 * @ $Author$
 * @ $Revision$
 * Форма редактирования записи справочника маппина ошибок
 */
public class ExceptionEntryEditForm extends EditFormBase
{
	private static final FormBuilder formBuilder = createFormBuilder();

	private ExceptionEntryType exceptionEntryType;
	private String hash;
	private boolean isNewEntry = true;
	private Long[] groupIds = {0L};
	private Long[] deletedGroupIds = {};
	private Map<String, String> numOfDepartmentsInBlock = new HashMap<String, String>();
	private ExceptionEntryApplication[] channels = ExceptionEntryApplication.values();

	/**
	 * @return получить тип ошибки, с которым работаем
	 */
	public ExceptionEntryType getExceptionEntryType()
	{
		return exceptionEntryType;
	}

	/**
	 * задать получить тип ошибки, с которым работаем
	 * @param exceptionEntryType получить тип ошибки, с которым работаем
	 */
	public void setExceptionEntryType(ExceptionEntryType exceptionEntryType)
	{
		this.exceptionEntryType = exceptionEntryType;
	}

	/**
	 * @return Редактируем ли мы сущность или добавляем новую
	 */
	public boolean isNewEntry()
	{
		return isNewEntry;
	}

	/**
	 * @return номер блока сообщений
	 */
	public Long[] getGroupIds()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return groupIds;
	}

	/**
	 * @param groupIds номер блока сообщений
	 */
	public void setGroupIds(Long[] groupIds)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.groupIds = groupIds;
	}

	/**
	 * @return номера удалённых блоков
	 */
	public Long[] getDeletedGroupIds()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return deletedGroupIds;
	}

	/**
	 * @param deletedGroupIds номера удалённых блоков
	 */

	 public void setDeletedGroupIds(Long[] deletedGroupIds)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.deletedGroupIds = deletedGroupIds;
	}

	/**
	 * @return каналы
	 */
	public ExceptionEntryApplication[] getChannels()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return channels;
	}

	/**
	 * @param newEntry редактируем ли мы сущность или добавляем новую
	 */
	public void setNewEntry(boolean newEntry)
	{
		isNewEntry = newEntry;
	}

	private static FormBuilder createFormBuilder()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("id");
		fieldBuilder.setDescription("Идентификатор ошибки");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator("Укажите идентификатор ошибки, при возникновении которой в приложении будет выведено соответствующее ему сообщение."),
				new ExistExceptionEntryValidator());
		formBuilder.addField(fieldBuilder.build());

		formBuilder.build();
		return formBuilder;
	}

	/**
	 * @return количество департаментов для заданного блока
	 */
	public Map<String, String> getNumOfDepartmentsInBlock()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return numOfDepartmentsInBlock;
	}

	/**
	 * @param numOfDepartmentsInBlock количество департаментов для заданного блока
	 */
	public void setNumOfDepartmentsInBlock(Map<String, String> numOfDepartmentsInBlock)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.numOfDepartmentsInBlock = numOfDepartmentsInBlock;
	}

	/**
	 * @return хеш ошибки
	 */
	public String getHash()
	{
		return hash;
	}

	/**
	 * @param hash хеш ошибки
	 */
	public void setHash(String hash)
	{
		this.hash = hash;
	}

	/**
	 * @return форма
	 */
	@SuppressWarnings("OverlyLongMethod")
	public Form createForm()
	{
		FormBuilder fb = new FormBuilder();
		fb.addFields(formBuilder.getFields());
		fb.addFormValidators(formBuilder.getFormValidators());

		UniqueTbValidator tbValidator = new UniqueTbValidator("Для одного или нескольких тербанков сообщение уже задано. Проверьте настраиваемые значения тербанков.");
		tbValidator.setEnabledExpression(new ConstantExpression(getExceptionEntryType() == ExceptionEntryType.internal));

		UniqueCombinationTBsAndChannelsValidator combineValidator = new UniqueCombinationTBsAndChannelsValidator(
				"Для одной или нескольких комбинаций каналов и " +
				"тербанков сообщение уже задано. Проверьте настраиваемые значения каналов и тербанков."
				);
		combineValidator.setGroupIds(groupIds);
		combineValidator.setNumOfDepartmentsInBlock(numOfDepartmentsInBlock);
		combineValidator.setEnabledExpression(new ConstantExpression(getExceptionEntryType() == ExceptionEntryType.external));

		for(int i = 0; i < groupIds.length; i++)
		{
			long groupId = groupIds[i];


			fb.addField(FieldBuilder.buildStringField("message_" + groupId, "Сообщение", new RequiredFieldValidator("Введите текст сообщения, отображаемого при возникновении ошибки.")));
			IsCheckedMultiFieldValidator isCheckedMultiFieldValidator = new IsCheckedMultiFieldValidator();
			for (ExceptionEntryApplication channel: ExceptionEntryApplication.values())
			{
				fb.addField(FieldBuilder.buildBooleanField(channel.name() + "_" + groupId, "Канал"));
				isCheckedMultiFieldValidator.setBinding(channel.name(), channel.name() + "_" + groupId);
				combineValidator.setBinding(channel.name() + "_" + groupId, channel.name() + "_" + groupId);
			}
			isCheckedMultiFieldValidator.setEnabledExpression(new ConstantExpression(getExceptionEntryType() == ExceptionEntryType.external));
			isCheckedMultiFieldValidator.setMessage("Укажите значение канала для маппинга ошибки");
			fb.addFormValidators(isCheckedMultiFieldValidator);
			int numOfDepartments = StringHelper.isEmpty(numOfDepartmentsInBlock.get("group_" + groupId)) ? 0 : Integer.parseInt(numOfDepartmentsInBlock.get("group_" + groupId));

			RequiredMultiFieldValidator requiredMultiFieldValidator = new RequiredMultiFieldValidator();
			for(int j = 0; j < numOfDepartments; j++)
			{
				fb.addField(FieldBuilder.buildStringField("department_" + groupId + "_" + j, "Департамент"));
				requiredMultiFieldValidator.setBinding("department_" + groupId, "department_" + groupId + "_" + j);
				combineValidator.setBinding("department_" + groupId + "_" + j, "department_" + groupId + "_" + j);
				tbValidator.setBinding("department_" + groupId + "_" + j, "department_" + groupId + "_" + j);
			}
			requiredMultiFieldValidator.setMessage("Укажите значение ТБ для маппинга ошибки.");
			fb.addFormValidators(tbValidator, combineValidator, requiredMultiFieldValidator);
		}
		return fb.build();
	}

}
