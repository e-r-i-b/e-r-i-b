package com.rssl.phizic.web.blockingrules.locale;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ext.sbrf.csa.blockingrules.locale.BlockingRulesResources;
import com.rssl.phizic.locale.dynamic.resources.LanguageResources;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.csa.blockingrules.locale.EditBlockingRulesResourcesOperation;
import com.rssl.phizic.operations.locale.dynamic.resources.EditLanguageResourcesOperation;
import com.rssl.phizic.web.blockingrules.EditConstants;
import com.rssl.phizic.web.locale.EditLanguageResourcesBaseAction;
import com.rssl.phizic.web.locale.EditLanguageResourcesBaseForm;

import java.util.Map;

/**
 * Эушен редактирования текстовок в правилах блокировки
 * @author komarov
 * @ created 15.05.2015
 * @ $Author$
 * @ $Revision$
 */
public class EditBlockingRulesResourcesAction extends EditLanguageResourcesBaseAction
{
	@Override
	protected void updateForm(EditLanguageResourcesBaseForm frm, LanguageResources entity) throws Exception
	{
		BlockingRulesResources rule = (BlockingRulesResources) entity;
		frm.setField(EditConstants.MAPI_MESSAGE_FIELD, rule.getMapiMessage());
		frm.setField(EditConstants.ERIB_MESSAGE_FIELD, rule.getERIBMessage());
		frm.setField(EditConstants.ERMB_MESSAGE_FIELD, rule.getERMBMessage());
		frm.setField(EditConstants.ATM_MESSAGE_FIELD, rule.getATMMessage());
	}

	@Override
	protected void updateFormOperationalData(EditLanguageResourcesBaseForm frm, EditLanguageResourcesOperation operation) throws Exception
	{

	}

	@Override
	protected void updateEntity(LanguageResources entity, Map<String, Object> data) throws Exception
	{
		BlockingRulesResources rule = (BlockingRulesResources) entity;
		rule.setATMMessage((String) data.get(EditConstants.ATM_MESSAGE_FIELD));
		rule.setERIBMessage((String) data.get(EditConstants.ERIB_MESSAGE_FIELD));
		rule.setMapiMessage((String) data.get(EditConstants.MAPI_MESSAGE_FIELD));
		rule.setERMBMessage((String) data.get(EditConstants.ERMB_MESSAGE_FIELD));
	}

	@Override
	protected void updateEntityOperationalData(EditEntityOperation operation, Map<String, Object> data) throws Exception
	{

	}

	@Override
	protected Form getEditForm(EditLanguageResourcesBaseForm frm)
	{
		return EditBlockingRulesResourcesForm.EDIT_LANGUAGE_FORM;
	}

	@Override
	protected EditLanguageResourcesOperation createEditOperation(EditLanguageResourcesBaseForm frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(EditBlockingRulesResourcesOperation.class);
	}
}
