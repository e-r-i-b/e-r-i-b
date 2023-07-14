package com.rssl.phizic.web.ext.sbrf.dictionaries;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ext.sbrf.dictionaries.SynchronizeSettingsUtil;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.ext.sbrf.dictionaries.EditCardSynchronizeSettingsOperation;
import com.rssl.phizic.utils.CardsConfig;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.log.FormLogParametersReader;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;
import org.apache.struts.action.ActionMessages;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Dorzhinov
 * @ created 30.09.2011
 * @ $Author$
 * @ $Revision$
 */
public class EditCardSynchronizeSettingsAction extends EditPropertiesActionBase<EditCardSynchronizeSettingsOperation>
{
	@Override
	protected EditCardSynchronizeSettingsOperation getEditOperation() throws BusinessException
	{
		return createOperation(EditCardSynchronizeSettingsOperation.class);
	}

	@Override
	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		super.updateFormAdditionalData(frm, operation);
		Map<String, String> map = (Map<String, String>) operation.getEntity();
		String allowedDepositProducts = map.get(CardsConfig.CARD_PRODUCT_USED_KINDS);
		EditCardSynchronizeSettingsForm form = (EditCardSynchronizeSettingsForm) frm;
		EditCardSynchronizeSettingsOperation op = (EditCardSynchronizeSettingsOperation) operation;

		form.setNumbers(SynchronizeSettingsUtil.readAllowedCardProducts(allowedDepositProducts));
		form.setMinCode(op.getMinMax().getMin());
		form.setMaxCode(op.getMinMax().getMax());
	}

	@Override
	protected ActionMessages validateAdditionalFormData(EditFormBase form, EditEntityOperation operation) throws Exception
	{
		ActionMessages actionMessages = new ActionMessages();
		EditCardSynchronizeSettingsForm frm = (EditCardSynchronizeSettingsForm) form;
		EditCardSynchronizeSettingsOperation op = (EditCardSynchronizeSettingsOperation) operation;
		Form logicForm = EditCardSynchronizeSettingsForm.EDIT_FORM;

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
