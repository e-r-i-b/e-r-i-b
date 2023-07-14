package com.rssl.phizic.web.dictionaries.provider;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.operations.dictionaries.provider.UnloadingServiceProvidersOperation;
import com.rssl.phizic.web.actions.UnloadOperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hudyakov
 * @ created 27.07.2010
 * @ $Author$
 * @ $Revision$
 */
public class UnloadingServiceProvidersAction extends UnloadOperationalActionBase<byte[]>
{
	public Pair<String, byte[]> createData(ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UnloadingServiceProvidersForm frm  = (UnloadingServiceProvidersForm) form;
		UnloadingServiceProvidersOperation operation = createOperation(UnloadingServiceProvidersOperation.class);

		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new MapValuesSource(frm.getFields()), UnloadingServiceProvidersForm.UNLOADING_FORM);
		if (processor.process())
		{
			Map<String, Object> result = processor.getResult();
			operation.initialize(frm.getProviders(), frm.getServices(), frm.getDepartments(), (Date) result.get("fromDate"), (Date) result.get("toDate"), frm.isUseNotActiveProviders());
			String data = operation.getUnloadedData();
			if (data == null)
			{
				ActionMessages msgs = new ActionMessages();
		        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Не найдены поставщики для выгрузки", false));
				saveErrors(request, msgs);

				return null;
			}
			return new Pair<String, byte[]>(operation.getFileName(), data.getBytes("UTF-8"));
		}
		else
		{
			saveErrors(request, processor.getErrors());
		}

		return null;
	}
}
