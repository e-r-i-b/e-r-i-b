package com.rssl.phizic.business.claims.forms.validators;


import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.business.exception.ExceptionEntryApplication;
import com.rssl.phizic.utils.StringHelper;

import java.util.*;

/**
 * @author komarov
 * @ created 10.09.2014
 * @ $Author$
 * @ $Revision$
 */
public class UniqueCombinationTBsAndChannelsValidator extends MultiFieldsValidatorBase
{
	private Long[] groupIds;
	private Map<String, String> numOfDepartmentsInBlock;

	/**
	 * Конструктор
	 * @param message сообщение пользователю
	 */
	public UniqueCombinationTBsAndChannelsValidator(String message)
	{
		super(message);
	}

	/**
	 * Идентификаторы групп
	 * @param groupIds Идентификаторы групп
	 */
	public void setGroupIds(Long[] groupIds)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.groupIds = groupIds;
	}

	/**
	 * @param numOfDepartmentsInBlock количество департаментов в группе
	 */
	public void setNumOfDepartmentsInBlock(Map<String, String> numOfDepartmentsInBlock)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.numOfDepartmentsInBlock = numOfDepartmentsInBlock;
	}


	public boolean validate(Map values) throws TemporalDocumentException
	{
		Set<String> combinations = new HashSet<String>();
		for(int i = 0; i < groupIds.length; i++)
		{
			long groupId = groupIds[i];
			int numOfDepartments = StringHelper.isEmpty(numOfDepartmentsInBlock.get("group_" + groupId)) ? 0 : Integer.parseInt(numOfDepartmentsInBlock.get("group_" + groupId));
			for (ExceptionEntryApplication channel: ExceptionEntryApplication.values())
			{
				if((Boolean)values.get(channel.name() + "_" + groupId))
				{
					for(int j = 0; j < numOfDepartments; j++)
					{
						String tb = (String)values.get("department_" + i + "_" + j);
						if(!combinations.add(tb + "," + channel.name()))
							return false;

					}
				}
			}

		}


		return true;
	}
}
