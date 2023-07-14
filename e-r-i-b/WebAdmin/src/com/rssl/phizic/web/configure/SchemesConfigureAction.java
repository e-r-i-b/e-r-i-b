package com.rssl.phizic.web.configure;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.scheme.SchemeConfigureOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;

@SuppressWarnings({"JavaDoc"})
public class SchemesConfigureAction extends EditPropertiesActionBase<SchemeConfigureOperation>
{
	@Override
	protected SchemeConfigureOperation getEditOperation() throws BusinessException
	{
		return createOperation(SchemeConfigureOperation.class);
	}

	@Override
	protected void updateFormAdditionalData(EditFormBase form, EditEntityOperation operation) throws Exception
	{
		super.updateFormAdditionalData(form, operation);

		SchemesConfigureForm frm = (SchemesConfigureForm) form;
		SchemeConfigureOperation op = (SchemeConfigureOperation) operation;
		frm.setHelpers(op.getHelpers());
	}
}
