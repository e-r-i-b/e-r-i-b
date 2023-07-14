package com.rssl.phizic.operations.pfr;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.PFRStatementClaim;
import com.rssl.phizic.business.pfr.PFRLink;
import com.rssl.phizic.business.pfr.PFRLinkService;
import com.rssl.phizic.business.resources.external.ShowOperationLinkFilter;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Dorzhinov
 * @ created 14.02.2011
 * @ $Author $
 * @ $Revision $
 */
public class ListPFRClaimOperation extends OperationBase implements ListEntitiesOperation
{
	private static final PFRLinkService pfrLinkService = new PFRLinkService();
	private static final Log LOG = PhizICLogFactory.getLog(LogModule.Core);
	private ShowOperationLinkFilter showOperationLinkFilter = new ShowOperationLinkFilter(); //фильтр, определяющий показывать ли мини-выписку

	private Login login;
	private PFRLink pfrLink; //карточка ПФР
	private boolean isError; //возникла ошибка при получении заявок

	/**
	 * Инициализация операции
	 */
	public void initialize() throws BusinessException
	{
		login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
		//ищем карточку ПФР в БД
		pfrLink = pfrLinkService.findByLoginId(login.getId());
		//если не нашли, то добавим ее с дефолтными настройками
		if(pfrLink == null)
		{
			pfrLink = new PFRLink();
			pfrLink.setLoginId(login.getId());
			pfrLink.setShowInMain(true);
			pfrLink.setShowOperations(false);
			pfrLink.setShowInSystem(true);
			pfrLinkService.addOrUpdate(pfrLink);
		}
	}

	/**
	 * @return получение идентфикатора логина клиента, в контексте которого выполняется операция
	 */
	public Long getLoginId()
	{
		return login.getId();
	}
	/**
	 * Получить карточку ПФР
	 * @return
	 */
	public PFRLink getPFRLink()
	{
		return pfrLink;
	}

	/**
	 * Показывать ли мини-выписку на главной
	 * @return
	 */
	public boolean isShowOperations()
	{
		return showOperationLinkFilter.evaluate(pfrLink);
	}

	/**
	 * Находит заявки на получение выписок из ПФР текущего пользователя (глубина поиска 1 месяц).
	 * @param count максимальное кол-во заявок в списке. Если не задано, то размер списка не ограничен
	 * @return список заявок на получение выписки из ПФР, отсортированный в обратном порядке по дате создания.
	 */
	public List<PFRStatementClaim> getPfrClaims(Integer count)
	{
		// выводим все за последний месяц, но не более this.count
		Calendar dateTo = DateHelper.endOfDay(DateHelper.getCurrentDate());
		Calendar dateFrom = DateHelper.startOfDay(DateHelper.getPreviousMonth(dateTo));

		List<PFRStatementClaim> claimList = new ArrayList<PFRStatementClaim>();
		try
		{
			com.rssl.phizic.dataaccess.query.Query query = createQuery("list");
			query.setParameter("dateFrom", dateFrom);
			query.setParameter("dateTo", dateTo);
			if (count != null)
			    query.setMaxResults(count);

			claimList = query.executeList();
		}
		catch (DataAccessException e)
		{
			LOG.error(e);
			isError = true;
		}
		return claimList;
	}

	/**
	 * Находит заявки на получение выписок из ПФР текущего пользователя за заданный период(если не задан - глубина поиска 1 месяц).
	 * @param fromDate начальная дата периода
	 * @param toDate конечная дата периода
	 * @return список заявок на получение выписки из ПФР, отсортированный в обратном порядке по дате создания.
	 */
	public List<PFRStatementClaim> getPfrClaims(Calendar fromDate, Calendar toDate) throws BusinessLogicException
	{
		if (fromDate == null || toDate == null)
			return getPfrClaims(null);

		List<PFRStatementClaim> claimList = new ArrayList<PFRStatementClaim>();
		try
		{
			com.rssl.phizic.dataaccess.query.Query query = createQuery("list");
			query.setParameter("dateFrom", fromDate);
			query.setParameter("dateTo", toDate);

			claimList = query.executeList();
		}
		catch (DataAccessException e)
		{
			LOG.error(e);
			isError = true;
		}
		return claimList;
	}

	/**
	 * Возникла ли ошибка при получении заявок
	 * @return
	 */
	public boolean isError()
	{
		return isError;
	}

	/**
	 * @return список статусов, по которым происходит фильтрация документов
	 */
	public List<String> getStateCodes()
	{
		List<String> stateCodes = new ArrayList<String>();
		stateCodes.add("INITIAL");
		stateCodes.add("SAVED");
		stateCodes.add("DRAFT");
		stateCodes.add("DISPATCHED");
		stateCodes.add("STATEMENT_READY");
		stateCodes.add("EXECUTED");
		stateCodes.add("REFUSED");
		return stateCodes;
	}
}
