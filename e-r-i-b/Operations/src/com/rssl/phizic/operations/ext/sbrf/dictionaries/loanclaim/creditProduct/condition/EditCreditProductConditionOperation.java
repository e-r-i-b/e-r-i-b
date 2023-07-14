package com.rssl.phizic.operations.ext.sbrf.dictionaries.loanclaim.creditProduct.condition;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.loanclaim.LoanClaimHelper;
import com.rssl.phizic.business.loanclaim.creditProduct.CreditProduct;
import com.rssl.phizic.business.loanclaim.creditProduct.CreditProductService;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CreditProductCondition;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CreditProductConditionService;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CurrencyCreditProductCondition;
import com.rssl.phizic.business.loanclaim.creditProduct.type.CreditProductType;
import com.rssl.phizic.business.loanclaim.creditProduct.type.CreditProductTypeService;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.StringHelper;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Moshenko
 * @ created 16.01.2014
 * @ $Author$
 * @ $Revision$
 * Операция редактирования условий по кредитным продуктам.
 */
public class EditCreditProductConditionOperation extends OperationBase implements EditEntityOperation
{
	private static final CreditProductConditionService  conditionService = new CreditProductConditionService();
	private static final CreditProductService productService = new CreditProductService();
	private static final CreditProductTypeService typeService = new CreditProductTypeService();
	private static final DepartmentService departmentService = new DepartmentService();
	private CreditProductCondition entity;
	private Set<CurrencyCreditProductCondition> remCurrCond;
	/**
	 *
	 * Редактирование кредитного условия.
	 * @param id Кредитного условия.
	 * @throws BusinessException
	 */
	public void initialize(Long id) throws BusinessException
	{
		entity = conditionService.findeById(id);

		if (entity == null)
			throw new BusinessException("Условие по кредитному продукту с Id = " + id + " не найдено.");

	}
	/**
	 * Создание нового кредитного условия.
	 * @throws BusinessException
	 */
	public void initializeNew() throws BusinessException
	{
		entity = new CreditProductCondition();
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		//Опубликовано и нет ни одного действующего.
		if (entity.isPublished() && !LoanClaimHelper.hasActiveCurrencyCondition(entity))
			throw new BusinessLogicException("Для опубликованного продукта должно быть задано хотя бы одно " +
					"условие в разрезе валют");
		if (entity.getMinDuration().isEmpty())
			throw new BusinessLogicException("“Срок продукта от” обязательно для заполнения ");
		if (entity.getMaxDuration().isEmpty())
			throw new BusinessLogicException("“Срок продукта до” обязательно для заполнения ");
		//Проверка Срока продукта.
		if (entity.getMinDuration().getDuration() >= entity.getMaxDuration().getDuration())
		throw new BusinessLogicException("“Срок продукта от” должен быть меньше “Срока продукта  до”." +
				" Пожалуйста, проверьте начало и окончание срока действия кредита. ");
		//Проверка первоначального взноса.
		if(entity.getMinInitialFee() != null && entity.getMaxInitialFee() != null &&
				entity.getMinInitialFee().compareTo(entity.getMaxInitialFee()) >= 0)
			throw new BusinessLogicException("“Сумма первоначального взноса от “ не может быть больше “Суммы первоначально взноса до”." +
					" Пожалуйста, проверьте сумму минимального и сумму максимального первоначального взноса.");
		//Проверка доступность подразделениям.
		if(StringHelper.isEmpty(entity.getDepartmentsStr()))
			throw new BusinessLogicException("Пожалуйста, укажите хотя бы одно подразделение, в котором будет доступен данный кредитный продукт.");
		 //Проверка возможность рассмотрения через TransactSM
		if (entity.isTransactSMPossibility())
		{
			if (StringHelper.isEmpty(entity.getCreditProductType().getCode())||
				StringHelper.isEmpty(entity.getCreditProduct().getCode()))
			throw new BusinessLogicException("Оформлять заявку через систему “Transact SM” можно только по кредитным продуктам, для которых заданы код типа продукта и код продукта.");

		}

		conditionService.addOrUpdate(entity);

		if (!remCurrCond.isEmpty())
			conditionService.remove(remCurrCond);

	}

	/**
	 * //Производим  необходимые проверки новых повалютных пришедших с формы.
	 * @param currCondSet Новые повалютные условия
	 */
	public void currencyConditionTests(Set<CurrencyCreditProductCondition> currCondSet) throws BusinessException, BusinessLogicException
	{
		for(CurrencyCreditProductCondition cond:currCondSet)
		{
			//Поверка суммы продукта.
			Money minLimit = cond.getMinLimitAmount();
			Money maxLimit = cond.getMaxLimitAmount();
			if (maxLimit != null &&	minLimit.getDecimal().compareTo(maxLimit.getDecimal()) >= 0)
				throw new BusinessLogicException("“Сумма продукта от” не может быть больше “Суммы продукта до”. Пожалуйста проверьте минимальную и максимальную сумму продукта.");
			//Проверка процентной стаки.
			if (cond.getMinPercentRate().compareTo(cond.getMaxPercentRate()) > 0)
				throw new BusinessLogicException("«Процентная ставка до» не может быть меньше «Процентной ставки от» Пожалуйста, проверьте минимальную и максимальную процентную ставку.");
		}
	}
	/**
	 * @param remCurrCond По валютные условия на удаление.
	 */
	public void setRemoveCurrCond(Set<CurrencyCreditProductCondition> remCurrCond)
	{
		this.remCurrCond = remCurrCond;
	}

	/**
	 * @param currCode Код валюты
	 * @return Валюта
	 * @throws GateException
	 */
	public Currency getCurrency(String currCode) throws GateException
	{
		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		return currencyService.findByNumericCode(currCode);
	}

	public CreditProductCondition getEntity()
	{
		return  entity;
	}
	/**
	 * @return Список всех кодов головных подразделений.
	 * * @throws BusinessException
	 */
	public List<String> getAllTb() throws BusinessException
	{
		return departmentService.getTerbanksNumbers();
	}
	/**
	 *
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	public CreditProduct getCreditProduct(Long id) throws BusinessException
	{
		return productService.findById(id);
	}
	/**
	 *
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	public CreditProductType getCreditProductType(Long id) throws BusinessException
	{
		return typeService.findById(id);
	}
	/**
	 *
	 * @param currCodeNumber Цифровой код валюты.
	 * @return Возвращяет новое повалютные условия из общего условия, если таковые есть.
	 *  Новым считается повалютное условия с датой начала действия больше текущей.
	 *  Для каждой общего условия может быть только одно новое повалютно условие.
	 */
	public CurrencyCreditProductCondition getNewCurrCondition(String currCodeNumber) throws BusinessException
	{
	    return conditionService.getNewCurrCondition(entity,getCurrencyCodeNumberFromCode(currCodeNumber));
	}

	/**
	 *
	 * @param currCodeNumber Цифровой код валюты.
	 * @return Возвращяет текущие повалютные условия из общего условия, если таковое ест.
	 * @throws BusinessException
	 */
	public CurrencyCreditProductCondition getCurrentCurrCondition(String currCodeNumber) throws BusinessException
	{
		return conditionService.getActiveCurrCondition(entity,getCurrencyCodeNumberFromCode(currCodeNumber));
	}

	/**
	 *  Обновляем признак доступности для текущего по валютного условия, если есть.
	 * @param available Признак доступности для текущего по валютного  условия.
	 * @param currCodeNumber Цифровой код валюты.
	 */
	public void  updateClientAvaliable(boolean available,String currCodeNumber) throws BusinessException
	{
		CurrencyCreditProductCondition cond = conditionService.getActiveCurrCondition(entity,getCurrencyCodeNumberFromCode(currCodeNumber));
		if (cond != null)
			cond.setClientAvaliable(available);
	}

	/**
	 * @param startDate Дата начала действия повалютного условия
	 * @return Точная дата  дополненная часами и минутами
	 */
	public Calendar getCorrectTime(Date startDate)
	{
		Calendar resultDate = Calendar.getInstance();
	 	int hour = resultDate.get(Calendar.HOUR_OF_DAY);
	 	int minut = resultDate.get(Calendar.MINUTE);
		resultDate.setTime(startDate);
		resultDate.set(Calendar.HOUR_OF_DAY,hour);
		resultDate.set(Calendar.MINUTE,minut);
		return resultDate;
	}

	private String getCurrencyCodeNumberFromCode(String code) throws BusinessException
	{
		if ("810".equals(code) || "643".equals(code))
			return "RUB";
		else if ("840".equals(code))
			return "USD";
		else if ("978".equals(code))
			return "EUR";
		throw new BusinessException("Неизвестный код валюты.");
	}
}
