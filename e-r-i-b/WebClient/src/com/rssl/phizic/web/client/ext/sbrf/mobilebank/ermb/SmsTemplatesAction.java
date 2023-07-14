package com.rssl.phizic.web.client.ext.sbrf.mobilebank.ermb;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.sms.config.SmsConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.operations.ermb.person.EditErmbOperation;
import com.rssl.phizic.operations.payment.ListTemplatesOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 @author: Egorovaa
 @ created: 28.09.2012
 @ $Author$
 @ $Revision$
 */
public class SmsTemplatesAction extends OperationalActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SmsTemplatesForm frm = (SmsTemplatesForm) form;
		
		EditErmbOperation operation = createOperation("EditErmbOperation");
        operation.initialize();

		frm.setCards(operation.getCards());
		updateFormTemplates(frm);
		return getCurrentMapping().findForward(FORWARD_START);
	}

	private void updateFormTemplates(SmsTemplatesForm frm) throws BusinessException, BusinessLogicException
	{
		if (checkAccess(ListTemplatesOperation.class))
		{
			ListTemplatesOperation tempOperation = createOperation(ListTemplatesOperation.class);

			Set<String> smsCommandAliases = ConfigFactory.getConfig(SmsConfig.class).getAllCommandAliases();
			tempOperation.initializeForSmsChannel(smsCommandAliases);
			frm.setTemplates(tempOperation.getEntity());
		}
	}
}
