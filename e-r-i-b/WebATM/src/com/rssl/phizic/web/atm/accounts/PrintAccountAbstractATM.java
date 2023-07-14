package com.rssl.phizic.web.atm.accounts;

import com.rssl.phizic.business.bankroll.TransactionComparator;
import com.rssl.phizic.business.resources.external.MockAccountLink;
import com.rssl.phizic.gate.bankroll.AccountAbstract;
import com.rssl.phizic.gate.bankroll.TransactionBase;
import com.rssl.phizic.web.common.client.ext.sbrf.accounts.PrintAccountAbstractExtendedAction;
import com.rssl.phizic.web.common.client.ext.sbrf.accounts.PrintAccountAbstractExtendedForm;
import org.apache.struts.action.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Rydvanskiy
 * @ created 20.01.2011
 * @ $Author$
 * @ $Revision$
 */

public class PrintAccountAbstractATM extends PrintAccountAbstractExtendedAction
{
	private static final String ESB_ERROR = "Операция временно недоступна. Повторите попытку позже.";

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ActionForward superForward = super.start(mapping, form, request, response);

		PrintAccountAbstractExtendedForm frm = (PrintAccountAbstractExtendedForm) form;

		Map<MockAccountLink, AccountAbstract> cardAccountAbstract = frm.getCardAccountAbstract();

		if (frm.isBackError()) // шина вернула ошибку
		{
			ActionMessages msgs = new ActionMessages();
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(ESB_ERROR, false));
			saveErrors(currentRequest(), msgs);
		}
		else
		{
			// для мобильных устройств необходимо переворачивать результат "последняя транзакция должна быть первой"
			for (AccountAbstract accountAbstract:cardAccountAbstract.values() ){
				if (accountAbstract.getTransactions() != null)
				{
					//Оборачиваем в ArrayList, на тот случай если коллекция unmodifiable.
					//Чтобы при вызове Collections.sort не получить UnsupportedOperationException.
				    List<TransactionBase> modifList = new ArrayList<TransactionBase>(accountAbstract.getTransactions());
					Collections.sort(modifList, new TransactionComparator());
				}
			}
		}

		return superForward;
	}
}
