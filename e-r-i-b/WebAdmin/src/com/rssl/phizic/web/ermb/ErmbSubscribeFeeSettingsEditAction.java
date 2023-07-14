package com.rssl.phizic.web.ermb;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.config.ermb.SubscribeFeeConstants;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.ermb.SubscribeFeeSettingsEditOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Экшн для редактирования настроек ФПП для списания абонентской платы ЕРМБ
 * @author Rtischeva
 * @ created 02.06.14
 * @ $Author$
 * @ $Revision$
 */
public class ErmbSubscribeFeeSettingsEditAction extends EditPropertiesActionBase<SubscribeFeeSettingsEditOperation>
{
	@Override
	protected ActionForward doSave(EditEntityOperation operation, ActionMapping mapping, EditFormBase frm) throws Exception
	{
		String filePath = (String)frm.getField(SubscribeFeeConstants.FPP_PATH);
		((SubscribeFeeSettingsEditOperation)operation).checkFilePath(filePath);
		return super.doSave(operation, mapping, frm);
	}

	@Override
	protected SubscribeFeeSettingsEditOperation getEditOperation() throws BusinessException
	{
		return createOperation(SubscribeFeeSettingsEditOperation.class);
	}
}
