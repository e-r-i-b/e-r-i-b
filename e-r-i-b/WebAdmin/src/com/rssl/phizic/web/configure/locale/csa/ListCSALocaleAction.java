package com.rssl.phizic.web.configure.locale.csa;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.locale.ListLocaleOperation;
import com.rssl.phizic.operations.locale.RemoveLocaleOperation;
import com.rssl.phizic.operations.locale.UnloadEribMessagesOperation;
import com.rssl.phizic.operations.locale.csa.RemoveCSALocaleOperation;
import com.rssl.phizic.operations.locale.csa.UnloadCSAEribMessagesOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.common.download.DownloadAction;
import com.rssl.phizic.web.common.exception.FormProcessorException;
import com.rssl.phizic.web.configure.locale.ListLocaleAction;
import com.rssl.phizic.web.configure.locale.ListLocaleForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Просмотр списка локалей в цса
 * @author koptyaev
 * @ created 11.09.2014
 * @ $Author$
 * @ $Revision$
 */
public class ListCSALocaleAction extends ListLocaleAction
{
	protected ListLocaleOperation createListOperation(ListLocaleForm frm) throws BusinessException
	{
		frm.setIsCSA(true);
		return createOperation("ListCSALocaleOperation", "LocaleManagement");
	}

	protected UnloadEribMessagesOperation createUnloadEribMessagesOperation(ListLocaleForm frm) throws BusinessException
	{
		frm.setIsCSA(true);
		return createOperation(UnloadCSAEribMessagesOperation.class);
	}

	protected RemoveLocaleOperation createRemoveLocaleOperation(ListLocaleForm frm) throws BusinessException
	{
		frm.setIsCSA(true);
		return createOperation(RemoveCSALocaleOperation.class);
	}
}
