package com.rssl.phizic.web.common.client.abstr;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.bankroll.*;
import com.rssl.phizic.operations.account.GetAccountAbstractOperation;
import com.rssl.phizic.operations.card.GetCardAbstractOperation;
import com.rssl.phizic.operations.claims.operation.scan.claim.CreatePrivateScanClaimOperation;
import com.rssl.phizic.operations.deposits.GetDepositAbstractOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.utils.MockHelper;
import org.apache.struts.action.*;

import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Omeliyanchuk
 * @ created 08.12.2006
 * @ $Author$
 * @ $Revision$
 */

public class PrintAccountAbstractAction extends OperationalActionBase
{
	public static final String FORWARD_PRINT = "print";
	public static final String DEFAULT_METHOD = "start";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		PrintAccountAbstractForm frm = (PrintAccountAbstractForm) form;
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		ActivePerson user = personData.getPerson();
		frm.setUser(user);
		if (request.getParameter("type") != null)
			frm.setTypeExtract(request.getParameter("type"));
		return print(mapping, form, request, response);
	}

	public ActionForward print(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ActionMessages actionErrors = new ActionMessages();
		if (request.getParameterValues("sel") != null)
		{
			form = createResponse(request, (PrintAccountAbstractForm) form);
		}
		else
		{
			actionErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Не выбраны счета или карты!", false));
			saveErrors(request, actionErrors);
		}

		return findForward((PrintAccountAbstractForm)form, mapping);
	}

	protected Boolean isWithCardUseInfo()
	{
		return false;
	}

	protected PrintAccountAbstractForm createResponse(HttpServletRequest request, PrintAccountAbstractForm frm)
			throws Exception
	{
		Calendar fromDate;
		Calendar toDate;
		try
		{
			fromDate = frm.getFromDate();
			toDate = frm.getToDate();
		}
		catch (ParseException ex)
		{
			throw new IllegalArgumentException("неверный формат даты", ex);
		}

		Map<AccountLink, AccountAbstract> accountAbstract = new HashMap<AccountLink, AccountAbstract>();
		Map<CardLink, CardAbstract> cardAbstract = new HashMap<CardLink, CardAbstract>();
		//Временная заглушка. Пока не реализуем полноценную выписку по карте.
		Map<MockAccountLink, AccountAbstract> cardAccountAbstract = new HashMap<MockAccountLink, AccountAbstract>();
		Map<DepositLink, DepositAbstract> depositAbstract = new HashMap<DepositLink, DepositAbstract>();

		String[] selectedResources = request.getParameterValues("sel");
		for (String res : selectedResources)
		{
			String sid = res.substring(2);
			Long id = new Long(sid);
			if (res.startsWith("a:") && super.checkAccess(GetAccountAbstractOperation.class))
			{
				GetAccountAbstractOperation op = createGetAccountAbstractOperation(id, frm);

				if (op.isUseStoredResource())
				{
					saveMessage(request, StoredResourceMessages.getUnreachableFullStatement());
					return frm;
				}

				op.setDateFrom(fromDate);
				op.setDateTo(toDate);
				op.setWithCardUseInfo(isWithCardUseInfo());
				AccountLink account = op.getAccount();
				AccountAbstract accAbstract = op.getAccountAbstract();

				if (account == null || accAbstract == null)
				{
					saveMessage(request, getResourceMessage("accountInfoBundle", "extendAccountAbstractError"));
					return frm;
				}

				accountAbstract.put(account, accAbstract);
				frm = fillAccountSpecificData(frm, op);

				addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", op.getAccount()));
				frm.setClient(op.getAccount().getAccountClient());
			}
			//Временная заглушка. Пока не реализуем полноценную выписку по карте.
			else if (res.startsWith("c:") && super.checkAccess(GetCardAbstractOperation.class))
			{
				GetCardAbstractOperation op = createGetCardAbstractOperation(id);

				op.setDateFrom(fromDate);
				op.setDateTo(toDate);
				op.setWithCardUseInfo(isWithCardUseInfo());
				MockAccountLink accountLink = new MockAccountLink();
				Account cardAccount = op.getCardAccount();
				accountLink.setValue(cardAccount);
				accountLink.setOffice(op.getCard().getCard().getOffice());
				AccountAbstract accAbstract = op.getCardAccountAbstract();

				if (op.isUseStoredResource())
				{
					saveMessage(request, StoredResourceMessages.getUnreachableFullStatement());
					return frm;
				}

				if (MockHelper.isMockObject(cardAccount) || accAbstract== null)
				{
					saveMessage(request, getResourceMessage("accountInfoBundle", "extendAccountAbstractError"));
					return frm;
				}

				cardAccountAbstract.put(accountLink, accAbstract);
				frm.setBackError(op.isBackError());
				addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", op.getCard()));
				// Расширенная выписка разрешена только для основных карт, владелец карты и СКС один.
				frm.setClient(op.getCard().getCardClient());
			}
//			else if (res.startsWith("c:") && super.checkAccess(GetCardAbstractOperation.class))
//			{
//				GetCardAbstractOperation op = createGetCardAbstractOperation(id);
//				op.setDateFrom(fromDate);
//				op.setDateTo(toDate);
//				cardAbstract.put(op.getCard(), op.getCardAbstract());
//				GetCardInfoOperation cardInfoOperation = createOperation(GetCardInfoOperation.class);
//				cardInfoOperation.initialize(id);
//
//				addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", op.getCard()));
//			}
			else if (res.startsWith("d:") && super.checkAccess(GetDepositAbstractOperation.class))
			{
				GetDepositAbstractOperation op = createGetDepositAbstractOperation(id);
				op.setDateFrom(fromDate);
				op.setDateTo(toDate);
				depositAbstract.put(op.getDeposit(), op.getDepositAbstract());

				addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", op.getDeposit()));
			}
		}

		frm.setAccountAbstract(accountAbstract);
		frm.setCardAbstract(cardAbstract);
		//Временная заглушка. Пока не реализуем полноценную выписку по карте.
		frm.setCardAccountAbstract(cardAccountAbstract);
		frm.setDepositAbstract(depositAbstract);
		return frm;
	}

	private GetDepositAbstractOperation createGetDepositAbstractOperation(Long id) throws BusinessLogicException, BusinessException
	{
		GetDepositAbstractOperation operation = createOperation(GetDepositAbstractOperation.class);
		operation.initialize(id);
		return operation;
	}

	protected GetAccountAbstractOperation createGetAccountAbstractOperation(Long accountId, PrintAccountAbstractForm frm)
			throws BusinessException, BusinessLogicException
	{
		GetAccountAbstractOperation operation = createOperation(GetAccountAbstractOperation.class);
		operation.initialize(accountId);
		return operation;
	}

	private GetCardAbstractOperation createGetCardAbstractOperation(Long cardId)
			throws BusinessException, BusinessLogicException
	{
		GetCardAbstractOperation operation = createOperation(GetCardAbstractOperation.class);
		operation.initialize(cardId);
		return operation;
	}

	protected ActionForward findForward(PrintAccountAbstractForm frm, ActionMapping mapping)
	{
		return mapping.findForward(FORWARD_PRINT); 	
	}

	protected PrintAccountAbstractForm fillAccountSpecificData(PrintAccountAbstractForm frm, GetAccountAbstractOperation operation) throws Exception
	{
		return frm;
	}
}
