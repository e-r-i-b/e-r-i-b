package com.rssl.phizic.web.dictionaries;

import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.DictionaryDescriptor;
import com.rssl.phizic.operations.dictionaries.SynchronizeDictionariesOperation;
import com.rssl.phizic.web.configure.SynchronizeDictionariesActionBase;
import com.rssl.phizic.web.configure.SynchronizeDictionariesForm;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import org.apache.struts.action.*;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Roshka
 * @ created 23.11.2006
 * @ $Author$
 * @ $Revision$
 */

public class SynchronizeDictionariesAction extends SynchronizeDictionariesActionBase
{
	@Override
	public ActionForward load(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	                          HttpServletResponse response) throws Exception
	{
		SynchronizeDictionariesForm frm = (SynchronizeDictionariesForm) form;
		SynchronizeDictionariesOperation operation = createOperation(SynchronizeDictionariesOperation.class);

		ActionMessages actionMessages = new ActionMessages();
		//operation.synchronizeAll();
		ActionMessages errors = new ActionMessages();
		try
		{
			//TODO зачем перебирать дескрипторы?? просто operation.synchronize(name). если нет такого упасть!
			int i=0;
			for (DictionaryDescriptor descriptor : operation.getEntity())
			{
				i++;
				addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", descriptor));
				List<String> err;
				for (String name : frm.getSelected())
				{
					err = new ArrayList<String>();
					if (descriptor.getName().equals(name))
					{
						err = operation.synchronize(descriptor);
						operation.setUpdateDate(i, descriptor);
						if (err== null || err.isEmpty())
							actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(name+ " загружен.", false));
						else
						{
							actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(name+ " загружен с ошибками:", false));
							for (String e : err)
							{
								actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("  - "+e, false));
							}
						}
					}
				}
			}
		}
		catch (BusinessLogicException e)
		{
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
		}
		if (!errors.isEmpty())
		{
			saveErrors(request, errors);
		}
		else
		{
			saveMessages(request, actionMessages);
		}
		frm.setDescriptors(operation.getEntity());

		return mapping.findForward(FORWARD_START);
	}
}