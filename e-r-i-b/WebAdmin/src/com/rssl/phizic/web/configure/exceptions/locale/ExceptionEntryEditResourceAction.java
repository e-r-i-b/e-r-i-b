package com.rssl.phizic.web.configure.exceptions.locale;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.exception.locale.ExceptionMappingResources;
import com.rssl.phizic.locale.dynamic.resources.LanguageResources;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.config.exceptions.locale.ExceptionEntryEditResourceOperation;
import com.rssl.phizic.operations.locale.dynamic.resources.EditLanguageResourcesOperation;
import com.rssl.phizic.web.locale.EditLanguageResourcesBaseAction;
import com.rssl.phizic.web.locale.EditLanguageResourcesBaseForm;

import java.util.List;
import java.util.Map;

/**
 * @author komarov
 * @ created 14.10.2014
 * @ $Author$
 * @ $Revision$
 */
public class ExceptionEntryEditResourceAction extends EditLanguageResourcesBaseAction
{
	@Override
	protected void updateForm(EditLanguageResourcesBaseForm frm, LanguageResources entity) throws Exception	{}

	@Override
	protected void updateFormOperationalData(EditLanguageResourcesBaseForm frm, EditLanguageResourcesOperation operation) throws Exception
	{
		ExceptionEntryEditResourceOperation op = (ExceptionEntryEditResourceOperation)operation;

		List<ExceptionMappingResources> resources  = op.getResources();
		for(ExceptionMappingResources resource : resources)
			frm.setField("message_" + resource.getGroup(), resource.getMessage());

	}

	@Override
	protected void updateEntity(LanguageResources entity, Map<String, Object> data) throws Exception {}

	@Override
	protected void updateEntityOperationalData(EditEntityOperation operation, Map<String, Object> data) throws Exception
	{
		ExceptionEntryEditResourceOperation op = (ExceptionEntryEditResourceOperation)operation;

		List<ExceptionMappingResources> resources  = op.getResources();
		for(ExceptionMappingResources resource : resources)
			resource.setMessage((String)data.get("message_"+ resource.getGroup()));
	}

	@Override
	protected Form getEditForm(EditLanguageResourcesBaseForm frm)
	{
		return ((ExceptionEntryEditResourceForm) frm).getEditForm();
	}

	@Override
	protected EditLanguageResourcesOperation createEditOperation(EditLanguageResourcesBaseForm frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ExceptionEntryEditResourceOperation.class);
	}

	@Override
	protected void initializeOperation(EditLanguageResourcesOperation operation, EditLanguageResourcesBaseForm frm) throws BusinessLogicException, BusinessException
	{
		ExceptionEntryEditResourceOperation op = (ExceptionEntryEditResourceOperation)operation;
		op.initialize(frm.getStringId(), frm.getLocaleId());
		Long size = op.getResources() == null ? 0 : (long)op.getResources().size();
		((ExceptionEntryEditResourceForm) frm).setMessagesCount(size);
	}
}
