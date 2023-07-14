package com.rssl.phizic.operations.finances;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.finances.CardOperationCategoryService;
import com.rssl.phizic.business.finances.Budget;
import com.rssl.phizic.business.finances.BudgetService;
import com.rssl.phizic.business.finances.CardOperationService;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.userSettings.UserPropertiesConfig;
import com.rssl.phizic.utils.DateHelper;
import org.hibernate.Session;

import java.util.*;

/**
 * @author komarov
 * @ created 06.05.2013 
 * @ $Author$
 * @ $Revision$
 */

public class EditBudgetOperation extends OperationBase implements EditEntityOperation
{
	private static final int PERIOD = 6;
	private static final double COEF = 100.0/6.0;
	private static final double PERCENTS_100 = 100.0;

	private final BudgetService budgetService = new BudgetService();
	private final CardOperationService cardService = new CardOperationService();
	private final CardOperationCategoryService cardOperationCategoryService = new CardOperationCategoryService();

	private Budget budget;
	private Login login;
	private Long categoryId;

	/**
	 * Инициализация бюджета
	 * @param id идентификатор категории
	 * @throws BusinessException
	 */
	public void initialize(Long id) throws BusinessException
	{
		categoryId = id;
		login = getLogin();
		if(login == null)
			throw new BusinessException("Не найден пользователь");

		budget = budgetService.findBudgetByLoginAndCategory(login, categoryId);
		//не нашли бюджет, значит нужно его создать.
		if(budget == null)
		{
			budget = new Budget();
			budget.setCategoryId(categoryId);
			budget.setLogin(login);
		}
	}

	/**
	 * Возвращает средний расход по категории за 6 месяцев
	 * @param date дата
	 * @return расход
	 * @throws BusinessException
	 */
	public Double calculateAvgBudget(Calendar date) throws BusinessException, BusinessLogicException
	{
		Calendar fromDate = (Calendar) date.clone();
		fromDate.add(Calendar.MONTH, -PERIOD);
		List<String> cardNumbers = FinanceHelper.getCardNumbersList(PersonContext.getPersonDataProvider().getPersonData().getCards());
		Double res = Math.abs(cardService.getCategorySumBudget(fromDate, date, cardNumbers, login, categoryId));
		return Math.round(res/PERIOD*100)/100.0;
	}

	private Login getLogin()
	{
		PersonDataProvider dataProvider = PersonContext.getPersonDataProvider();
		if(dataProvider == null)
			return null;

		PersonData personData = dataProvider.getPersonData();
		if(personData == null)
			return null;

		Login login = personData.getLogin();

		if(login == null)
			return null;

		return login;
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		budgetService.addOrUpdateBudget(budget);
	}

	/**
	 * Сохранение бюджета
	 * @param filterParams параметры фильтрации
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void save(final Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		try
		{
			HibernateExecutor.getInstance(getInstanceName()).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					save();
					saveDefaultBudgets(filterParams);
					return null;
				}
			}
			);
		}
		catch(BusinessLogicException e)
		{
			throw e;
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Удаление бюджета
	 * @param filterParams параметры фильтрации
	 * @throws BusinessException
	 */
	public void remove(final Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		try
		{
			HibernateExecutor.getInstance(getInstanceName()).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					budgetService.removeBudget(budget);
					saveDefaultBudgets(filterParams);
					return null;
				}
			}
			);
		}
		catch(BusinessLogicException e)
		{
			throw e;
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private void saveDefaultBudgets(Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		if(isUsedALFBudgets())
			return;

		Calendar maxLoadDate = DateHelper.toCalendar((Date) filterParams.get("openPageDate"));
		Calendar fromDate = DateHelper.getCurrentDate();
		fromDate.add(Calendar.MONTH, -6);

		List<CardLink> cards = PersonContext.getPersonDataProvider().getPersonData().getCards();
		List<String> cardsNumbers = FinanceHelper.getCardNumbersList(cards, (String)filterParams.get("selectedCardIds"));

		List defaultCategoriesBudgets = budgetService.getCategoriesDefaultBudgets(fromDate, Calendar.getInstance(), maxLoadDate, cardsNumbers, getLogin());
		List<Budget> budgets = new ArrayList<Budget>();
		for(Object object : defaultCategoriesBudgets)
		{
			Object[] categoryBudget = (Object[]) object;
			Long budgetCategoryId = (Long)categoryBudget[0];
			if (budgetCategoryId.equals(categoryId))
				continue;

			Budget newBudget = new Budget();
			newBudget.setLogin(login);
			newBudget.setCategoryId(budgetCategoryId);
			newBudget.setBudget(Math.abs(Math.round(((Double)categoryBudget[1]) * COEF) / PERCENTS_100));
			budgets.add(newBudget);
		}
		budgetService.addBudgetsList(budgets);
		setupUsedALFBudgets();
	}

	/**
	 * Запомнить, что клиент настраивал бюджеты для категорий
	 * @throws BusinessException
	 */
	public void setupUsedALFBudgets() throws BusinessException
	{
		ConfigFactory.getConfig(UserPropertiesConfig.class).setUsedALFBudgets(true);
	}

	/**
	 * @return true - клиент настраивал бюджеты для категорий
	 * @throws BusinessException
	 */
	public boolean isUsedALFBudgets() throws BusinessException
	{
		return ConfigFactory.getConfig(UserPropertiesConfig.class).isUsedALFBudgets();
	}

	public Budget getEntity() throws BusinessException, BusinessLogicException
	{
		return budget;
	}
}
