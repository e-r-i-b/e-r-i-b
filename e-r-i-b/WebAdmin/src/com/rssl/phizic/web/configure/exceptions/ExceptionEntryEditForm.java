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
 * ����� �������������� ������ ����������� ������� ������
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
	 * @return �������� ��� ������, � ������� ��������
	 */
	public ExceptionEntryType getExceptionEntryType()
	{
		return exceptionEntryType;
	}

	/**
	 * ������ �������� ��� ������, � ������� ��������
	 * @param exceptionEntryType �������� ��� ������, � ������� ��������
	 */
	public void setExceptionEntryType(ExceptionEntryType exceptionEntryType)
	{
		this.exceptionEntryType = exceptionEntryType;
	}

	/**
	 * @return ����������� �� �� �������� ��� ��������� �����
	 */
	public boolean isNewEntry()
	{
		return isNewEntry;
	}

	/**
	 * @return ����� ����� ���������
	 */
	public Long[] getGroupIds()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return groupIds;
	}

	/**
	 * @param groupIds ����� ����� ���������
	 */
	public void setGroupIds(Long[] groupIds)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.groupIds = groupIds;
	}

	/**
	 * @return ������ �������� ������
	 */
	public Long[] getDeletedGroupIds()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return deletedGroupIds;
	}

	/**
	 * @param deletedGroupIds ������ �������� ������
	 */

	 public void setDeletedGroupIds(Long[] deletedGroupIds)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.deletedGroupIds = deletedGroupIds;
	}

	/**
	 * @return ������
	 */
	public ExceptionEntryApplication[] getChannels()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return channels;
	}

	/**
	 * @param newEntry ����������� �� �� �������� ��� ��������� �����
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
		fieldBuilder.setDescription("������������� ������");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator("������� ������������� ������, ��� ������������� ������� � ���������� ����� �������� ��������������� ��� ���������."),
				new ExistExceptionEntryValidator());
		formBuilder.addField(fieldBuilder.build());

		formBuilder.build();
		return formBuilder;
	}

	/**
	 * @return ���������� ������������� ��� ��������� �����
	 */
	public Map<String, String> getNumOfDepartmentsInBlock()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return numOfDepartmentsInBlock;
	}

	/**
	 * @param numOfDepartmentsInBlock ���������� ������������� ��� ��������� �����
	 */
	public void setNumOfDepartmentsInBlock(Map<String, String> numOfDepartmentsInBlock)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.numOfDepartmentsInBlock = numOfDepartmentsInBlock;
	}

	/**
	 * @return ��� ������
	 */
	public String getHash()
	{
		return hash;
	}

	/**
	 * @param hash ��� ������
	 */
	public void setHash(String hash)
	{
		this.hash = hash;
	}

	/**
	 * @return �����
	 */
	@SuppressWarnings("OverlyLongMethod")
	public Form createForm()
	{
		FormBuilder fb = new FormBuilder();
		fb.addFields(formBuilder.getFields());
		fb.addFormValidators(formBuilder.getFormValidators());

		UniqueTbValidator tbValidator = new UniqueTbValidator("��� ������ ��� ���������� ��������� ��������� ��� ������. ��������� ������������� �������� ���������.");
		tbValidator.setEnabledExpression(new ConstantExpression(getExceptionEntryType() == ExceptionEntryType.internal));

		UniqueCombinationTBsAndChannelsValidator combineValidator = new UniqueCombinationTBsAndChannelsValidator(
				"��� ����� ��� ���������� ���������� ������� � " +
				"��������� ��������� ��� ������. ��������� ������������� �������� ������� � ���������."
				);
		combineValidator.setGroupIds(groupIds);
		combineValidator.setNumOfDepartmentsInBlock(numOfDepartmentsInBlock);
		combineValidator.setEnabledExpression(new ConstantExpression(getExceptionEntryType() == ExceptionEntryType.external));

		for(int i = 0; i < groupIds.length; i++)
		{
			long groupId = groupIds[i];


			fb.addField(FieldBuilder.buildStringField("message_" + groupId, "���������", new RequiredFieldValidator("������� ����� ���������, ������������� ��� ������������� ������.")));
			IsCheckedMultiFieldValidator isCheckedMultiFieldValidator = new IsCheckedMultiFieldValidator();
			for (ExceptionEntryApplication channel: ExceptionEntryApplication.values())
			{
				fb.addField(FieldBuilder.buildBooleanField(channel.name() + "_" + groupId, "�����"));
				isCheckedMultiFieldValidator.setBinding(channel.name(), channel.name() + "_" + groupId);
				combineValidator.setBinding(channel.name() + "_" + groupId, channel.name() + "_" + groupId);
			}
			isCheckedMultiFieldValidator.setEnabledExpression(new ConstantExpression(getExceptionEntryType() == ExceptionEntryType.external));
			isCheckedMultiFieldValidator.setMessage("������� �������� ������ ��� �������� ������");
			fb.addFormValidators(isCheckedMultiFieldValidator);
			int numOfDepartments = StringHelper.isEmpty(numOfDepartmentsInBlock.get("group_" + groupId)) ? 0 : Integer.parseInt(numOfDepartmentsInBlock.get("group_" + groupId));

			RequiredMultiFieldValidator requiredMultiFieldValidator = new RequiredMultiFieldValidator();
			for(int j = 0; j < numOfDepartments; j++)
			{
				fb.addField(FieldBuilder.buildStringField("department_" + groupId + "_" + j, "�����������"));
				requiredMultiFieldValidator.setBinding("department_" + groupId, "department_" + groupId + "_" + j);
				combineValidator.setBinding("department_" + groupId + "_" + j, "department_" + groupId + "_" + j);
				tbValidator.setBinding("department_" + groupId + "_" + j, "department_" + groupId + "_" + j);
			}
			requiredMultiFieldValidator.setMessage("������� �������� �� ��� �������� ������.");
			fb.addFormValidators(tbValidator, combineValidator, requiredMultiFieldValidator);
		}
		return fb.build();
	}

}
