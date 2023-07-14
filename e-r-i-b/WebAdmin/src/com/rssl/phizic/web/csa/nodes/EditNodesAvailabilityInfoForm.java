package com.rssl.phizic.web.csa.nodes;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.CompareValidator;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author akrenev
 * @ created 17.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * Форма работы с блоками
 */

public class EditNodesAvailabilityInfoForm extends EditFormBase
{
	public static final String NODE_INFO_NAME_FIELD_PREFIX                     = "name_";
	public static final String NODE_INFO_NEW_USERS_ALLOWED_FIELD_PREFIX        = "newUsersAllowed_";
	public static final String NODE_INFO_EXISTING_USERS_ALLOWED_FIELD_PREFIX   = "existingUsersAllowed_";
	public static final String NODE_INFO_TEMPORARY_USERS_ALLOWED_FIELD_PREFIX  = "temporaryUsersAllowed_";
	public static final String NODE_INFO_USERS_TRANSFER_ALLOWED_FIELD_PREFIX   = "usersTransferAllowed_";
	public static final String NODE_INFO_ADMIN_AVAILABLE_FIELD_PREFIX          = "adminAvailable_";
	public static final String NODE_INFO_GUEST_AVAILABLE_FIELD_PREFIX          = "guestAvailable_";

	private Long[] nodeIds = new Long[]{};

	/**
	 * @return выделенные идентификаторы
	 */
	public Long[] getNodeIds()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return nodeIds;
	}

	/**
	 *  Установить идентивикаторы
	 * @param nodeIds идентификаторы
	 */
	public void setNodeIds(Long[] nodeIds)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.nodeIds = nodeIds;
	}

	/**
	 * @return логическая форма
	 */
	public Form getEditForm()
	{
		FormBuilder builder = new FormBuilder();
		for (Long nodeId : nodeIds)
		{
			builder.addField(FieldBuilder.buildBooleanField(NODE_INFO_NEW_USERS_ALLOWED_FIELD_PREFIX + nodeId, ""));
			builder.addField(FieldBuilder.buildBooleanField(NODE_INFO_EXISTING_USERS_ALLOWED_FIELD_PREFIX + nodeId, ""));
			builder.addField(FieldBuilder.buildBooleanField(NODE_INFO_TEMPORARY_USERS_ALLOWED_FIELD_PREFIX + nodeId, ""));
			builder.addField(FieldBuilder.buildBooleanField(NODE_INFO_USERS_TRANSFER_ALLOWED_FIELD_PREFIX + nodeId, ""));
			builder.addField(FieldBuilder.buildBooleanField(NODE_INFO_ADMIN_AVAILABLE_FIELD_PREFIX + nodeId, ""));
			builder.addField(FieldBuilder.buildBooleanField(NODE_INFO_GUEST_AVAILABLE_FIELD_PREFIX + nodeId, ""));
		}
		return builder.build();
	}
}
