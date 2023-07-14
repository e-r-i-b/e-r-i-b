package com.rssl.phizic.web.configure;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.config.PropertyCategory;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.config.ConfigureConcreteExternalSystemOperation;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * экшн редактирования настроек внешней системы.
 *
 * @author bogdanov
 * @ created 11.01.2013
 * @ $Author$
 * @ $Revision$
 */

public class ConcreteExternalSystemConfigAction extends EditPropertiesActionBase
{
	@Override
	protected EditPropertiesOperation getEditOperation() throws BusinessException
	{
		return createOperation(ConfigureConcreteExternalSystemOperation.class);
	}

	@Override
	protected PropertyCategory getCategory(EditFormBase form)
	{
		return ExternalSystemType.valueOf(((ConcreteExternalSystemConfigForm) form).getSystemName()).getCategory();
	}

	@Override
	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm)
	{
		ActionForward forward = new ActionForward(getCurrentMapping().findForward(FORWARD_SUCCESS));
		forward.setPath(forward.getPath() + "?systemName=" + ((ConcreteExternalSystemConfigForm) frm).getSystemName());
		return forward;
	}

}
