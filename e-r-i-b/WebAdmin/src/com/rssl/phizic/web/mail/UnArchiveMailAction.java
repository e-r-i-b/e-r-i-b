package com.rssl.phizic.web.mail;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.mail.archive.UnArchiveMailOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mihaylov
 * @ created 04.07.2011
 * @ $Author$
 * @ $Revision$
 *
 * Ёкшен разархивации писем
 */

public class UnArchiveMailAction extends OperationalActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String,String>();
		map.put("button.unarchivate","unarchivate");
		return map;
	}

	private Map<String, Object> getDefaultParameters(UnArchiveMailOperation operation)
	{
		Map<String, Object> defaultParameters = new HashMap<String, Object>(1);
		defaultParameters.put("folder", operation.getDefaultFilePath());
		return defaultParameters;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UnArchiveMailForm frm = (UnArchiveMailForm) form;
		UnArchiveMailOperation operation = createOperation(UnArchiveMailOperation.class);
		Map<String, Object> defaultParameters = getDefaultParameters(operation);
		frm.setFields(defaultParameters);
		frm.setDefaultParameters(defaultParameters);
		return mapping.findForward(FORWARD_START);
	}

	/**
	 * разархиваци€ писем
	 * @param mapping стратс маппинг
	 * @param form    стратс форма
	 * @param request запрос
	 * @param response ответ
	 * @return следующий обработчик
	 * @throws Exception
	 */
	@SuppressWarnings("UnusedParameters")
	public ActionForward unarchivate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws  Exception
	{
		UnArchiveMailForm frm = (UnArchiveMailForm) form;
		UnArchiveMailOperation operation = createOperation(UnArchiveMailOperation.class);
		Map<String, Object> defaultParameters = getDefaultParameters(operation);
		frm.setDefaultParameters(defaultParameters);
		Map<String, Object> fields = frm.getFields();
		FieldValuesSource valuesSource = new MapValuesSource(fields);
		Form editForm = UnArchiveMailForm.UNARCHIVING_FORM;
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource,editForm);
		if(processor.process())
		{
			operation.initialize(processor.getResult());
			try
			{
				operation.process();
				saveMessage(request, getResourceMessage("mailBundle", "message.all-mails-unarchived")+" "+operation.getCountUnArchivedMails());
			}
			catch (BusinessLogicException e)
			{
				saveError(request,e);		        
			}
		}
		else
		{
			saveErrors(request,processor.getErrors());
		}
		return mapping.findForward(FORWARD_START);
	}
}
