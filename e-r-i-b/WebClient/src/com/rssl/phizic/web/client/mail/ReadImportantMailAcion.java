package com.rssl.phizic.web.client.mail;

import com.rssl.phizic.web.security.LoginStageActionSupport;
import com.rssl.phizic.security.SecurityUtil;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.mail.ListClientMailOperation;
import com.rssl.phizic.operations.mail.EditClientMailOperation;
import com.rssl.phizic.business.mail.Mail;
import org.apache.struts.action.*;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Krenev
 * @ created 03.09.2007
 * @ $Author$
 * @ $Revision$
 */
public class ReadImportantMailAcion extends LoginStageActionSupport
{
	private static final String FORWARD_START = "Start";
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String,String> map = new HashMap<String, String>();
        map.put("button.next", "start");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if(SecurityUtil.isAuthenticationComplete())
		{
			return mapping.findForward(FORWARD_START);
		}

		ListClientMailOperation operation = createOperation(ListClientMailOperation.class);
		List<Mail> mails = operation.getNewImportantMailList();
		if (mails == null || mails.size() == 0)
		{
			completeStage();
			return null;
		}
		ActionMessages msgs = new ActionMessages();
		msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("У вас есть сообщения обязательные для прочтения", false));
		saveMessages(request, msgs);

		Mail mail = mails.get(0);
		EditClientMailOperation editClientMailOperation = createOperation(EditClientMailOperation.class);
		editClientMailOperation.initialize(mail.getId());
		ViewMailForm frm = (ViewMailForm)form;
		frm.setMail(mail);
		editClientMailOperation.markMailReceived();

		addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", mail));

		return mapping.findForward(FORWARD_START);
	}
}
