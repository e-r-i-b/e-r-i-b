package com.rssl.phizic.web.common.socialApi.dictionaries;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.dictionaries.regions.RegionsListOperation;
import com.rssl.phizic.web.common.client.ext.sbrf.security.SelectRegionForm;
import com.rssl.phizic.business.BusinessException;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Изменение региона в профиле пользователя
 * @author Barinov
 * @ created 03.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class ChangeRegionMobileAction extends ShowRegionsListMobileAction
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException
	{
		SelectRegionForm frm = (SelectRegionForm)form;
		RegionsListOperation operation = createRegionListOperation();
		operation.initialize(frm.getId());

		CommonLogin login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
		operation.saveRegion(login);

		saveMessage(currentRequest(), "Регион сохранен");

		return mapping.findForward(FORWARD_START);
	}
}
