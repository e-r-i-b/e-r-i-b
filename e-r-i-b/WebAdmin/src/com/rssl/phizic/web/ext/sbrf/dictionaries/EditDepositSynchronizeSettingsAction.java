package com.rssl.phizic.web.ext.sbrf.dictionaries;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ext.sbrf.dictionaries.SynchronizeSettingsUtil;
import com.rssl.phizic.common.types.MinMax;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.ext.sbrf.dictionaries.EditDepositSynchronizeSettingsOperation;
import com.rssl.phizic.utils.DepositConfig;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.log.FormLogParametersReader;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMessages;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Pankin
 * @ created 20.05.2011
 * @ $Author$
 * @ $Revision$
 */

public class EditDepositSynchronizeSettingsAction extends EditPropertiesActionBase<EditDepositSynchronizeSettingsOperation>
{
	@Override
	protected EditDepositSynchronizeSettingsOperation getEditOperation() throws BusinessException
	{
		return createOperation(EditDepositSynchronizeSettingsOperation.class);
	}

	@Override
	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		super.updateFormAdditionalData(frm, operation);
		Map<String, String> map = (Map<String, String>) operation.getEntity();
		String allowedDepositProducts = map.get(DepositConfig.DEPOSITPRODUCT_KINDS_ALLOWED_UPLOADING);
		EditDepositSynchronizeSettingsForm form = (EditDepositSynchronizeSettingsForm)frm;
		form.setNumbers(SynchronizeSettingsUtil.readAllowedDepositProductMap(allowedDepositProducts));
		MinMax<Long> minMax = ((EditDepositSynchronizeSettingsOperation)operation).getMinMax();
		form.setMinCode(minMax.getMin());
		form.setMaxCode(minMax.getMax());
	}

	@Override
	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm) throws BusinessException
	{
		return getCurrentMapping().findForward(FORWARD_START);
	}

	@Override
	protected ActionMessages validateAdditionalFormData(EditFormBase form, EditEntityOperation operation) throws Exception
	{
		ActionMessages actionMessages = new ActionMessages();
		EditDepositSynchronizeSettingsForm frm = (EditDepositSynchronizeSettingsForm) form;
		EditDepositSynchronizeSettingsOperation op = (EditDepositSynchronizeSettingsOperation) operation;
		Form logicForm = EditDepositSynchronizeSettingsForm.EDIT_FORM;

		if(frm.getKinds() != null)
			for(int i = 0; i < frm.getKinds().length; ++i)
			{
				Map<String, Object> fields = new HashMap<String, Object>();

				fields.put("kind", frm.getKinds()[i]);
				fields.put("subkinds", frm.getSubkinds()[i]);

				FieldValuesSource valuesSource = new MapValuesSource(fields);
				//Фиксируем данные, введенные пользователе
				addLogParameters(new FormLogParametersReader("Данные, введенные пользователем", logicForm, fields));

				FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, logicForm);
				if (!processor.process())
				{
					actionMessages.add(processor.getErrors());
					return actionMessages;
				}
			}

		op.updateEntity(frm.getKinds(), frm.getSubkinds());
		return actionMessages;
	}
}
