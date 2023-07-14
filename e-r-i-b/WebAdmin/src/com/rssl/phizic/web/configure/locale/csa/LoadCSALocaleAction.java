package com.rssl.phizic.web.configure.locale.csa;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.locale.utils.LocaleHelper;
import com.rssl.phizic.operations.locale.LoadEribMessagesOperation;
import com.rssl.phizic.operations.locale.csa.LoadCSAEribMessagesOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.configure.locale.LoadLocaleAction;
import com.rssl.phizic.web.configure.locale.LoadLocaleForm;
import com.rssl.phizic.web.validators.FileNotEmptyValidator;
import org.apache.struts.action.*;
import org.apache.struts.upload.FormFile;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Загрузка локалей в цса
 * @author koptyaev
 * @ created 25.09.2014
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings("UnusedParameters")
public class LoadCSALocaleAction extends LoadLocaleAction
{
	protected LoadEribMessagesOperation createOperation(LoadLocaleForm frm) throws BusinessException
	{
		frm.setIsCSA(true);
		return createOperation(LoadCSAEribMessagesOperation.class);
	}
}
