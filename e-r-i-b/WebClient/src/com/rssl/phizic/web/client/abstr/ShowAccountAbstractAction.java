package com.rssl.phizic.web.client.abstr;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.business.resources.external.DepositLink;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.bankroll.*;
import com.rssl.phizic.operations.account.GetAccountAbstractOperation;
import com.rssl.phizic.operations.card.GetCardAbstractOperation;
import com.rssl.phizic.operations.deposits.GetDepositAbstractOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Created by IntelliJ IDEA. User: Evgrafov Date: 17.10.2005 Time: 14:33:59 */
public class ShowAccountAbstractAction extends OperationalActionBase
{
	public static final String FORWARD_SHOW = "Show";
	protected String DAY_PERIOD = "datePeriod";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.filter", "filter");
		map.put("button.showAccountInformation","filter");
		map.put("button.print", "start");
		return map;
	}

	// сюда приходим из фильтра выписок
	public ActionForward filter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ShowAccountAbstractForm frm = (ShowAccountAbstractForm) form;
		ActionMessages errors = frm.validate(mapping, request);
        if(errors != null && errors.size() > 0)
        {
            saveErrors(request, errors);
	        setUserInfo(frm);
			return findForward(frm, mapping);
        }
		return doFilter(frm, mapping);
	}

	// сюда приходим со страницы информации по счетам
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ShowAccountAbstractForm frm = (ShowAccountAbstractForm) form;

		//заполняем форму
 		Calendar currDate = DateHelper.getCurrentDate();
		Calendar startDate = DateHelper.getCurrentDate();
		
		if(request.getParameter(DAY_PERIOD)==null)
			startDate.set(Calendar.DAY_OF_MONTH,1);					

		frm.setFromDate(startDate);
		frm.setToDate(currDate);

		//заполняем список счетов и карт для фильтра
		String list = request.getParameter("list");
		if (list != null)
		{
			String[] accounts = new String[0];
			String[] cards = new String[0];
			String[] deposits = new String[0];
			if ("selected".equals(list))
			{
				if(frm.getAccountIds() != null)
					accounts = frm.getAccountIds();
				if(frm.getCardIds() != null)
					cards = frm.getCardIds();
				if(frm.getDepositIds() != null)
					deposits = frm.getDepositIds();
			}else{
				if ("cards".equals(list) || "all".equals(list))
				{
					PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
					cards = getExternalResourceIdArray(personData.getCards());
				}
				if ("accounts".equals(list) || "all".equals(list))
				{
					PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
					accounts = getExternalResourceIdArray(personData.getAccounts());
				}
				if ("deposits".equals(list) || "all".equals(list))
				{
					PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
					deposits = getExternalResourceIdArray(personData.getDeposits());
				}
			}
			String[] selected = new String[accounts.length + cards.length+deposits.length];
			int i = 0;
			for (String id : accounts)
			{
				selected[i++] = "a:" + id;
			}
			for (String id : cards)
			{
				selected[i++] = "c:" + id;
			}
			for (String id : deposits)
			{
				selected[i++] = "d:" + id;
			}
			frm.setSelectedResources(selected);
		}

		return doFilter(frm, mapping);
	}

	protected String[] getExternalResourceIdArray(List<? extends ExternalResourceLink> links)
	{

		if (links.isEmpty())
		{
			return new String[0];
		}
		int i = 0;
		String[] ids = new String[links.size()];
		for (ExternalResourceLink link : links)
		{
			ids[i++] = link.getId().toString();
		}
		return ids;
	}

	protected void setUserInfo(ShowAccountAbstractForm frm) throws Exception
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		ActivePerson user = personData.getPerson();
		frm.setUser(user);
		frm.setAccountLinks(personData.getAccounts());
		frm.setCardLinks(personData.getCards());
		frm.setDepositLinks(personData.getDeposits());
	}
	
	protected ActionForward doFilter(ShowAccountAbstractForm frm, ActionMapping mapping) throws Exception
	{
		setUserInfo(frm);
		// построение выписок
		buildAbstract(frm);

		return findForward(frm, mapping);
	}

	protected void buildAbstract(ShowAccountAbstractForm frm) throws Exception
	{
		Calendar fromDate = frm.getFromDate();
		Calendar toDate = frm.getToDate();
		
		Map<AccountLink, AccountAbstract> accountAbstract = new HashMap<AccountLink, AccountAbstract>();
		Map<CardLink, CardAbstract> cardAbstract = new HashMap<CardLink, CardAbstract>();
		Map<DepositLink, DepositAbstract> depositAbstract = new HashMap<DepositLink, DepositAbstract>();

		String[] selectedResources = frm.getSelectedResources();
		for (String res : selectedResources)
		{
			String sid = res.substring(2);

			if(res.startsWith("no")) return;

			Long id = new Long(sid);
			if (res.startsWith("a:") && super.checkAccess(GetAccountAbstractOperation.class))
			{
				GetAccountAbstractOperation op = createGetAccountAbstractOperation(id,frm);
				op.setDateFrom(fromDate);
				op.setDateTo(toDate);
				accountAbstract.put(op.getAccount(), op.getAccountAbstract());
				frm = fillAccountSpecificData(frm, op);

				addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", op.getAccount()));
			}
			else if (res.startsWith("c:") && super.checkAccess(GetCardAbstractOperation.class))
			{
				GetCardAbstractOperation op = createGetCardAbstractOperation(id);
				op.setDateFrom(fromDate);
				op.setDateTo(toDate);
				cardAbstract.put(op.getCard(), op.getCardAbstract());

				addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", op.getCard()));
			}
			else if (res.startsWith("d:") && super.checkAccess(GetDepositAbstractOperation.class))
			{
				GetDepositAbstractOperation op = createGetDepositAbstractOperation(id);
				op.setDateFrom(fromDate);
				op.setDateTo(toDate);
				depositAbstract.put(op.getDeposit(),op.getDepositAbstract());

				addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", op.getDeposit()));
			}
		}

		frm.setAccountAbstract(accountAbstract);
		frm.setCardAbstract(cardAbstract);
		frm.setDepositAbstract(depositAbstract);

		frm = fillSpecificData(frm);
	}


	protected ShowAccountAbstractForm fillAccountSpecificData(ShowAccountAbstractForm frm, GetAccountAbstractOperation operation) throws Exception
	{
		return frm;
	}

	protected ActionForward findForward(ShowAccountAbstractForm frm, ActionMapping mapping)
	{
		return mapping.findForward(FORWARD_SHOW);
	}

	/**
	 * действия по заполнению формы, специфичные для конфигурации
	 * @param frm форма
	 * @return заполненную форму
	 */
	protected ShowAccountAbstractForm fillSpecificData(ShowAccountAbstractForm frm) throws Exception
	{

		return frm;
	}

	private GetDepositAbstractOperation createGetDepositAbstractOperation(Long id) throws BusinessLogicException, BusinessException
	{
		GetDepositAbstractOperation operation= createOperation(GetDepositAbstractOperation.class);
		operation.initialize(id);
		return operation;
	}

	protected GetAccountAbstractOperation createGetAccountAbstractOperation(Long accountId, ShowAccountAbstractForm frm)
			throws BusinessException, BusinessLogicException
	{
		GetAccountAbstractOperation operation = createOperation(GetAccountAbstractOperation.class);
		operation.initialize(accountId);
		return operation;
	}

	protected GetCardAbstractOperation createGetCardAbstractOperation(Long cardId) throws BusinessLogicException, BusinessException
	{
		GetCardAbstractOperation operation = createOperation(GetCardAbstractOperation.class);
		operation.initialize(cardId);
		return operation;
	}
}
