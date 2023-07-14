package com.rssl.phizic.web.ext.sevb.dictionaries;

import com.rssl.phizic.operations.dictionaries.SynchronizeDictionariesOperation;
import com.rssl.phizic.web.configure.SynchronizeDictionariesActionBase;
import com.rssl.phizic.web.configure.SynchronizeDictionariesForm;
import com.rssl.phizic.gate.dictionaries.SynchronizeResult;
import org.apache.struts.action.*;
import org.apache.struts.upload.FormFile;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Kosyakov
 * @ created 20.09.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 3021 $
 */
public class SBRFSynchronizeDictionariesAction extends SynchronizeDictionariesActionBase
{

	@Override
	public ActionForward load ( ActionMapping mapping, ActionForm form, HttpServletRequest request,
	                            HttpServletResponse response ) throws Exception
	{
		SynchronizeDictionariesForm frm = (SynchronizeDictionariesForm)form;
		SynchronizeDictionariesOperation operation = createOperation(SynchronizeDictionariesOperation.class);

		FormFile content = frm.getContent();

		ActionMessages actionMessages = new ActionMessages();

		if (content==null || content.getFileName().length()==0)
		{
			actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Файл не выбран", false));
		}
		else if (frm.getSelected() == null)
		{
			actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Справочники не выбраны", false));
		}
		else
		{
			List<SynchronizeResult> synchronizeResultList = operation.synchronizeXml(
					Arrays.asList(frm.getSelected()), new ByteArrayInputStream(content.getFileData()), content.getFileName(), frm.isTemporary());
			for (SynchronizeResult synchronizeResult : synchronizeResultList)
			{
				for (String message : synchronizeResult.getMessages())
					actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(message, false));
				for (String error : synchronizeResult.getErrors())
					actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(error, false));
			}
		}

		if (actionMessages.size()>0)
		{
			saveErrors(request, actionMessages);
		}
		else
		{
			actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Справочники загружены", false));
			saveMessages(request, actionMessages);
		}

		frm.setDescriptors(operation.getEntity());

		return mapping.findForward(FORWARD_START);
	}

}
