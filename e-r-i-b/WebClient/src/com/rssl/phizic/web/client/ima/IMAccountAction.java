package com.rssl.phizic.web.client.ima;

import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanHelper;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.resources.external.IMAccountLink;
import com.rssl.phizic.common.types.bankroll.BankProductType;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.ima.GetIMAccountOperation;
import com.rssl.phizic.operations.ima.RateOfExchange;
import com.rssl.phizic.operations.ima.ShowIMAccountRateOperation;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.web.client.ShowAccountsForm;
import com.rssl.phizic.web.client.ext.sbrf.accounts.ShowAccountsExtendedAction;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ author Balovtsev
 * @ created 25.08.2010
 * @ $Author$
 * @ $Revision$
 */
public class IMAccountAction extends ShowAccountsExtendedAction
{
	public  static final String FORWARD_SHOW = "Show";
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> keyMap = new HashMap<String, String>();
		keyMap.put("button.showInMainMenu", "showInMainMenu");
		return keyMap;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShowAccountsForm frm = (ShowAccountsForm) form;

		ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		
		if (checkAccess("ShowIMAccountRateOperation") && person.getCreationType() == CreationType.UDBO)
		{
			ShowIMAccountRateOperation rateOperation = createOperation("ShowIMAccountRateOperation");
			rateOperation.initialize();

			String tarifPlanCodeType = !PermissionUtil.impliesService("ReducedRateService") ?
					TariffPlanHelper.getUnknownTariffPlanCode() : PersonHelper.getActivePersonTarifPlanCode();
			GroupResult<String, RateOfExchange> rateGroup = rateOperation.getIMARates(tarifPlanCodeType, null);
			for (Map.Entry<String, IKFLException> entry: rateGroup.getExceptions().entrySet())
			{
				 log.error("Ошибка при получении курсов для"+entry.getKey(), entry.getValue());
			}

			frm.setRates(rateGroup.getResults());
		}

		try
		{
			setIMAccountAbstract(frm);
			if (frm.getImAccounts().isEmpty())
			{
				ExternalSystemHelper.check(person.asClient().getOffice(), BankProductType.IMA);
			}
		}
	    catch (InactiveExternalSystemException e)
	    {
		    saveInactiveESMessage(request, e);
	    }	

		ActionMessages msgs = new ActionMessages();
		if(frm.isAllIMAccountDown())
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Информация по обезличенным металлическим счетам из АБС временно недоступна. Повторите операцию позже.", false));
			saveMessages(request, msgs);
		}

		return mapping.findForward(FORWARD_SHOW);
	}

	protected List<IMAccountLink> getPersonIMAccountLinks(GetIMAccountOperation operationAccounts)
	{
		return operationAccounts.getIMAccounts();
	}
}