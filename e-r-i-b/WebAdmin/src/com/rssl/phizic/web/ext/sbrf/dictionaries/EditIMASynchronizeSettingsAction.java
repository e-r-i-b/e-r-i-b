package com.rssl.phizic.web.ext.sbrf.dictionaries;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ext.sbrf.dictionaries.SynchronizeSettingsUtil;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.ext.sbrf.dictionaries.EditIMASynchronizeSettingsOperation;
import com.rssl.phizic.utils.IMAConfig;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.log.FormLogParametersReader;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;
import org.apache.struts.action.ActionMessages;

import java.util.HashMap;
import java.util.Map;

/**
 * Редактирование настроек загрузки справочника ОМС
 * @author Pankin
 * @ created 25.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class EditIMASynchronizeSettingsAction extends EditPropertiesActionBase<EditIMASynchronizeSettingsOperation>
{
	@Override
	protected EditIMASynchronizeSettingsOperation getEditOperation() throws BusinessException
	{
		return createOperation(EditIMASynchronizeSettingsOperation.class);
	}

	@Override
	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		super.updateFormAdditionalData(frm, operation);
		Map<String, String> map = (Map<String, String>) operation.getEntity();
		String allowedIMAProducts = map.get(IMAConfig.IMA_PRODUCT_USED_KINDS);
		EditIMASynchronizeSettingsForm form = (EditIMASynchronizeSettingsForm)frm;
		form.setNumbers(SynchronizeSettingsUtil.readAllowedIMAProducts(allowedIMAProducts));
		form.setImaTypes(((EditIMASynchronizeSettingsOperation)operation).getIMATypes());
	}

	protected ActionMessages validateAdditionalFormData(EditFormBase form, EditEntityOperation operation) throws Exception
	{
		ActionMessages actionMessages = new ActionMessages();
		EditIMASynchronizeSettingsForm frm = (EditIMASynchronizeSettingsForm) form;
		EditIMASynchronizeSettingsOperation op = (EditIMASynchronizeSettingsOperation) operation;
		Form logicForm = EditIMASynchronizeSettingsForm.EDIT_FIELD_FORM;

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
