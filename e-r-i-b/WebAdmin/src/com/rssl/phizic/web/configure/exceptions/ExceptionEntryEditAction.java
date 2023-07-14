package com.rssl.phizic.web.configure.exceptions;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.exception.*;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.config.exceptions.ExceptionEntryEditOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.commons.collections.CollectionUtils;
import com.rssl.phizic.logging.exceptions.ExceptionEntry;
import com.rssl.phizic.logging.exceptions.ExternalExceptionEntry;
import com.rssl.phizic.logging.exceptions.InternalExceptionEntry;

import java.util.*;

/**
 * @author mihaylov
 * @ created 19.04.2013
 * @ $Author$
 * @ $Revision$
 * Ёкшен редактировани€ записи справочника маппина ошибок
 */
public class ExceptionEntryEditAction extends EditActionBase
{
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		ExceptionEntryEditOperation operation = createOperation(ExceptionEntryEditOperation.class);
		if(frm.getId() == null)
			operation.initializeNew();
		else
			operation.initialize(frm.getId());
		return operation;
	}

	protected EditEntityOperation createViewOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		ExceptionEntryEditForm form = (ExceptionEntryEditForm) frm;
		form.setNewEntry(form.getId() == null);
		return createEditOperation(frm);		
	}	

	protected Form getEditForm(EditFormBase frm)
	{
		ExceptionEntryEditForm form = (ExceptionEntryEditForm)frm;
		form.setExceptionEntryType(ExceptionEntryType.valueOf(getCurrentMapping().getParameter()));
		return form.createForm();
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{

	}

	@Override
	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		ExceptionEntryEditForm form = (ExceptionEntryEditForm) editForm;
		ExceptionEntryEditOperation operation = (ExceptionEntryEditOperation) editOperation;

		ExceptionEntry entry = operation.getEntity();
		if(entry == null)
			entry = operation.getNewEntry((Long)validationResult.get("id"));

		boolean isInternal = ExceptionEntryType.internal == ExceptionEntryType.valueOf(getCurrentMapping().getParameter());
		String entryChannel = isInternal ? ExceptionEntryApplication.fromApplication(((InternalExceptionEntry)operation.getEntity()).getApplication()).name() : null;

		operation.clearExceptionMapping();
		List<ExceptionMapping> exceptionMappings = operation.getExceptionMapping();
		operation.setDeletedGroups(form.getDeletedGroupIds());

		Long[] groupIds = form.getGroupIds();
		for(int i = 0; i < groupIds.length; i++)
		{
			ExceptionMapping mapping = new ExceptionMapping();
			mapping.setHash(entry.getHash());
			mapping.setGroup((long) i);
			Long groupId = groupIds[i];
			mapping.setMessage((String) validationResult.get("message_" + groupId));
			List<ExceptionMappingRestriction> exceptionMappingRestrictions = new ArrayList<ExceptionMappingRestriction>();
			Map<String, String> numOfDepartmentsInBlock = form.getNumOfDepartmentsInBlock();
			int numOfDepartments = StringHelper.isEmpty(numOfDepartmentsInBlock.get("group_" + groupId)) ? 0 : Integer.parseInt(numOfDepartmentsInBlock.get("group_" + groupId));

			for(int j = 0; j < numOfDepartments; j++)
			{
				String tb = (String)validationResult.get("department_"+ groupId +"_" + j);
				if(isInternal)
				{
					exceptionMappingRestrictions.add(createRestriction(tb, entryChannel));
				}
				else
				{
					exceptionMappingRestrictions.addAll(createRestrictions(tb, validationResult, groupId));
				}
			}
			mapping.setRestrictions(exceptionMappingRestrictions);
			exceptionMappings.add(mapping);
		}
	}

	private List<ExceptionMappingRestriction> createRestrictions(String tb, Map<String, Object> validationResult, Long groupId)
	{
		List<ExceptionMappingRestriction> restrictions = new ArrayList<ExceptionMappingRestriction>();
		for (ExceptionEntryApplication channel: ExceptionEntryApplication.values())
		{
			if((Boolean) validationResult.get(channel.name() + "_" + groupId))
				restrictions.add(createRestriction(tb, channel.name()));
		}
		return restrictions;
	}

	private ExceptionMappingRestriction createRestriction(String tb, String channel)
	{
		ExceptionMappingRestriction restriction = new ExceptionMappingRestriction();
		restriction.setTb(tb);
		restriction.setApplication(channel);
		return restriction;
	}


	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		((ExceptionEntryEditForm) frm).setExceptionEntryType(ExceptionEntryType.valueOf(getCurrentMapping().getParameter()));
		if(entity == null)
			return;
		ExceptionEntry exceptionEntry = (ExceptionEntry) entity;
		((ExceptionEntryEditForm) frm).setHash(exceptionEntry.getHash());
		frm.setField("id",exceptionEntry.getId());
		frm.setField("operation",exceptionEntry.getOperation());
		frm.setField("detail",exceptionEntry.getDetail());
		if (ExceptionEntryType.internal == ExceptionEntryType.valueOf(getCurrentMapping().getParameter()))
		{
			frm.setField("application",((InternalExceptionEntry)exceptionEntry).getApplication());
			return;
		}
		ExternalExceptionEntry externalExceptionEntry = (ExternalExceptionEntry) exceptionEntry;
		frm.setField("errorCode", externalExceptionEntry.getErrorCode());
	}

	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation editOperation) throws Exception
	{
		ExceptionEntryEditForm form = (ExceptionEntryEditForm) frm;
		ExceptionEntryEditOperation operation = (ExceptionEntryEditOperation) editOperation;

		if(frm.isFromStart())
		{
			form.setExceptionEntryType(ExceptionEntryType.valueOf(getCurrentMapping().getParameter()));

			List<ExceptionMapping> mappings = operation.getExceptionMapping();
			if(CollectionUtils.isEmpty(mappings))
				return;

			Map<String, String> map = new HashMap<String, String>();
			Long[] groupIds = new Long[mappings.size()];
			for(int i = 0; i < mappings.size(); i++)
			{
				groupIds[i] = Long.valueOf(i);
				ExceptionMapping mapping = mappings.get(i);
				form.setField("message_" + i , mapping.getMessage());
				List<ExceptionMappingRestriction> restrictions = mapping.getRestrictions();
				Set<String> tbs = new HashSet<String>();
				for(int j = 0; j < restrictions.size(); j++)
				{
					ExceptionMappingRestriction restriction = restrictions.get(j);
					if(tbs.add(restriction.getTb()))
						form.setField("department_" + i + "_" + j, restriction.getTb());
					form.setField(restriction.getApplication() + "_" + i, true);
				}
				map.put("group_" + i, String.valueOf(tbs.size()));
			}
			form.setNumOfDepartmentsInBlock(map);
			form.setGroupIds(groupIds);
			frm.setField("system", operation.getSystemName());
		}
	}
}
