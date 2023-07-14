package com.rssl.phizic.operations.finances;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.country.CountryCode;
import com.rssl.phizic.business.dictionaries.country.CountryService;
import com.rssl.phizic.business.dictionaries.finances.CardOperationCategory;
import com.rssl.phizic.business.finances.CardOperation;
import com.rssl.phizic.business.finances.CardOperationExtendedFields;
import com.rssl.phizic.business.finances.CardOperationService;
import com.rssl.phizic.business.finances.OperationType;
import com.rssl.phizic.business.finances.recategorization.ALFRecategorizationRule;
import com.rssl.phizic.business.finances.recategorization.ALFRecategorizationRuleLogHelper;
import com.rssl.phizic.business.finances.recategorization.ALFRecategorizationRuleService;
import com.rssl.phizic.common.types.Fraction;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.finances.recategorization.ALFRecategorizationRuleEntryType;
import com.rssl.phizic.logging.finances.recategorization.ALFRecategorizationRuleLogConfig;
import com.rssl.phizic.utils.EntityUtils;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Erkin
 * @ created 18.08.2011
 * @ $Author$
 * @ $Revision$
 */
public class EditCategoryAbstractOperation extends FinancesOperationBase
{
	private static final int OPERATION_NAME_MAX_LENGTH = 100;
	private static final int RATE_SCALE = 10000;

	private static final CardOperationService cardOperationService = new CardOperationService();
	private static final ALFRecategorizationRuleService recategorizationRuleService = new ALFRecategorizationRuleService();
	private static final CountryService countryService = new CountryService();

	private Map<Long, CardOperationCategory> categoriesById; // Мап доступных текущему пользователю категорий по ID
	private CardOperation cardOperation;
	private CardOperation originalOperation;
	private String cardOperationNewDescription;
	private String country;
	private boolean resetCountry;
	private Long cardOperationNewCategoryId;
	private ALFRecategorizationRule recategorizationRule;
	private List<EditCardOperationForm> newCardOperationForms;
	private List<Long> newCategoriesIds = new ArrayList<Long>();

	/**
	 * Инициализация операции
	 * @param editCardOperationId - ID редактируемой операции
	 */
	public void initialize(long editCardOperationId) throws BusinessException, BusinessLogicException
	{
		super.initialize();

		cardOperation = cardOperationService.findById(editCardOperationId);
		if (cardOperation == null)
			throw new BusinessException("Не найдена карточная операция ID=" + editCardOperationId);

		if (!getLogin().getId().equals(cardOperation.getOwnerId()))
			throw new BusinessException("Карточная операция ID=" + editCardOperationId + " не доступна клиенту LOGIN_ID=" + getLogin().getId());

		List<CardOperationCategory> categories = cardOperationCategoryService.getPersonAvailableCategories(getLogin().getId());
		categoriesById = EntityUtils.mapEntitiesById(categories);
		originalOperation = makeCardOperationCopy(cardOperation);
	}

	/**
	 * @return исходная (редактируемая) операция
	 */
	public CardOperation getOriginalOperation()
	{
		return originalOperation;
	}

	/**
	 * @param cardOperationNewDescription - описание операции, которое отредактировал пользователь
	 */
	public void setCardOperationNewDescription(String cardOperationNewDescription)
	{
		this.cardOperationNewDescription = cardOperationNewDescription;
	}

	/**
	 * @param cardOperationNewCategoryId - категория, выбранная для операции
	 */
	public void setCardOperationNewCategoryId(Long cardOperationNewCategoryId)
	{
		this.cardOperationNewCategoryId = cardOperationNewCategoryId;
	}

	public void setNewCardOperations(List<EditCardOperationForm> newCardOperations)
	{
		this.newCardOperationForms = newCardOperations;
	}

	/**
	 * Сохранение операций и правила перекатегоризации
	 * @param needAddRule - необходимо ли добавить правило перекатегоризации для операции
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void save(final Boolean needAddRule) throws BusinessException, BusinessLogicException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					saveOperations(needAddRule);
					saveRecategorizationRule(needAddRule);
					return null;
				}
			}
			);
		}
		catch (ConstraintViolationException e)
		{
			throw new BusinessLogicException("Для данного вида операций уже настроена автоматическая перекатегоризация. Вы хотите её обновить?", e);
		}
		catch (BusinessLogicException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException("Не удалось обновить операцию.",e);
		}

		saveLogs(needAddRule);
	}

	/**
	 * Валидация и сохранение изменений
	 * @param needAddRule - нужно ли добавить правило перекатегоризации
	 */
	public void saveOperations(Boolean needAddRule) throws BusinessException, BusinessLogicException
	{
		BigDecimal restCardAmounr = cardOperation.getCardAmount();
		BigDecimal restNationalAmount = cardOperation.getNationalAmount();
		CountryCode operationCountry = null;
		if (StringHelper.isNotEmpty(country))
		{
			if (resetCountry)
				throw new BusinessException("Нельзя одновременно задавать новую страну совершения операции и сбрасывать её на дефолтную.");
			else
				operationCountry = countryService.getCountryByISO3(country);
		}

		List<Pair<CardOperation, CardOperationExtendedFields>> newOperations = null;
		if (!CollectionUtils.isEmpty(newCardOperationForms))
		{
			if (BooleanUtils.isTrue(needAddRule))
			{
				throw new BusinessLogicException("Операция недоступна.");
			}

			Fraction rate = null;
			if (cardOperation.getOperationType() == OperationType.BY_CARD)
				rate = computeCardOperationCurrencyRate(cardOperation);

			newOperations = new ArrayList<Pair<CardOperation, CardOperationExtendedFields>>(newCardOperationForms.size());
			for (EditCardOperationForm form : newCardOperationForms)
			{
				// Положительная сумма разбиения в валюте карты и нац.валюте
				BigDecimal formAmount = form.getAmount();
				if (formAmount == null || formAmount.signum()==0)
					throw new BusinessLogicException("Введите сумму разбиения");

				BigDecimal newOperationCardAmount = null;
				BigDecimal newOperationNationalAmount = null;
				if (cardOperation.getOperationType() == OperationType.BY_CARD)
				{
					newOperationCardAmount = formAmount;
					newOperationNationalAmount = Fraction.divide(form.getAmount(), rate);
				}
				else
				{
					newOperationNationalAmount = formAmount;
				}

				if(newOperationNationalAmount.signum() * restNationalAmount.signum()<0)
					throw new BusinessLogicException("Вы неправильно указали сумму операции.");

				// 1.1 Поля из старой операции
				CardOperation newCardOperation = makeCardOperationCopy(cardOperation);

				// 1.2 Поля, введённые пользователем
				CardOperationCategory newCardOperationCategory = selectCardOperationCategory(newCardOperation, form.getCategoryId());
				newCardOperation.setDescription(form.getDescription());
				newCardOperation.setCategory(newCardOperationCategory);
				newCardOperation.setCardAmount(newOperationCardAmount);
				newCardOperation.setNationalAmount(newOperationNationalAmount);
				if (operationCountry!=null)//если
					newCardOperation.setClientCountry(operationCountry);
				else if (resetCountry)
					newCardOperation.setClientCountry(newCardOperation.getOriginalCountry());
				validateNewCardOperation(newCardOperation);

				// 1.3 В список
				newOperations.add(new Pair<CardOperation, CardOperationExtendedFields>(newCardOperation, null));

				// 1.4 Пересчёт суммы родительской операции
				if (cardOperation.getOperationType() == OperationType.BY_CARD)
					restCardAmounr = restCardAmounr.subtract(newOperationCardAmount);
				restNationalAmount = restNationalAmount.subtract(newOperationNationalAmount);

				// 1.5 Данные для логов
				if (!form.getCategoryId().equals(cardOperation.getCategory().getId()) && !newCategoriesIds.contains(newCardOperationCategory.getId()))
				{
					newCategoriesIds.add(newCardOperationCategory.getId());
				}
			}
		}

		// 2. Обновляем данные родительской операции
		CardOperationCategory newCategory = selectCardOperationCategory(cardOperation, cardOperationNewCategoryId);
		if (!newCategory.getId().equals(cardOperation.getCategory().getId()) && !newCategoriesIds.contains(newCategory.getId()))
		{
			newCategoriesIds.add(newCategory.getId());
		}

		cardOperation.setDescription(cardOperationNewDescription);
		cardOperation.setCategory(newCategory);
		cardOperation.setCardAmount(restCardAmounr);
		cardOperation.setNationalAmount(restNationalAmount);
		if (operationCountry!=null)
			cardOperation.setClientCountry(operationCountry);
		else if (resetCountry)
			cardOperation.setClientCountry(cardOperation.getOriginalCountry());
		validateCardOperation(cardOperation);

		// 2.1 Если после обновления в родительской операции осталась сумма, то обновляем её. Иначе удаляем родительскую операцию.
		if(cardOperation.getNationalAmount().compareTo(BigDecimal.ZERO) != 0)
		{
			cardOperationService.addOrUpdate(cardOperation);
		}
		else
		{
			//Если это была последняя загруженная операция по карте, то она загрузится заново, т.к. проверка при загрузке происходит по externalId (BUG073267)
			//Поэтому необходимо одной из новых операций присвоить externalId, принадлежащий исходной операции
			newOperations.get(0).getFirst().setExternalId(cardOperation.getExternalId());
			cardOperationService.remove(cardOperation);
		}

		// 3. Сохраняем новые операции
		if (newOperations != null)
			cardOperationService.addOperations(newOperations);
	}

	private void saveRecategorizationRule(Boolean needAddRule) throws BusinessException, BusinessLogicException
	{
		if (needAddRule == null)
			return;

		if (needAddRule)
		{
			recategorizationRule = new ALFRecategorizationRule();
			recategorizationRule.setLoginId(getLogin().getId());
			recategorizationRule.setDescription(originalOperation.getOriginalDescription() == null ? originalOperation.getDescription() : originalOperation.getOriginalDescription());
			recategorizationRule.setMccCode(originalOperation.getMccCode());
			recategorizationRule.setNewCategory(cardOperation.getCategory());
			recategorizationRuleService.add(recategorizationRule);
		}
		else
		{
			recategorizationRule = recategorizationRuleService.findRecategorizationRuleByOperation(originalOperation);
			if (recategorizationRule != null)
				recategorizationRuleService.remove(recategorizationRule);
		}
	}

	private void saveLogs(Boolean needAddRule)
	{
		if (needAddRule == null || recategorizationRule == null)
			return;

		ALFRecategorizationRuleLogConfig config = ConfigFactory.getConfig(ALFRecategorizationRuleLogConfig.class);
		if (!config.isLoggingOn())
			return;

		ALFRecategorizationRuleLogHelper.writeEntryToLog(recategorizationRule, originalOperation.getOriginalCategoryName(), needAddRule ? ALFRecategorizationRuleEntryType.ADD : ALFRecategorizationRuleEntryType.REMOVE );
	}

	private Fraction computeCardOperationCurrencyRate(CardOperation cardOperation)
	{
		BigDecimal cardAmount = cardOperation.getCardAmount()
				.setScale(RATE_SCALE, BigDecimal.ROUND_HALF_UP);
		BigDecimal nationalAmount = cardOperation.getNationalAmount()
				.setScale(RATE_SCALE, BigDecimal.ROUND_HALF_UP);
		return Fraction.getFraction(cardAmount, nationalAmount);
	}

	private CardOperation makeCardOperationCopy(CardOperation operation)
	{
		// ID и external ID не копируем!
		CardOperation copy = new CardOperation();
		copy.setDate(operation.getDate());
		copy.setLoadDate(operation.getLoadDate());
		copy.setOperationType(operation.getOperationType());
		copy.setCardNumber(operation.getCardNumber());
		copy.setOriginalDescription(operation.getOriginalDescription());
		copy.setDescription(operation.getDescription());
		copy.setCardAmount(operation.getCardAmount());
		copy.setNationalAmount(operation.getNationalAmount());
		copy.setCash(operation.isCash());
		copy.setDeviceNumber(operation.getDeviceNumber());
		copy.setOwnerId(operation.getOwnerId());
		copy.setCategory(operation.getCategory());
		copy.setOriginalCountry(operation.getOriginalCountry());
		copy.setClientCountry(operation.getClientCountry());
		copy.setMccCode(operation.getMccCode());
		copy.setOriginalCategoryName(operation.getOriginalCategoryName());
		return copy;
	}

	private CardOperationCategory selectCardOperationCategory(CardOperation operation, Long categoryId) throws BusinessException
	{
		CardOperationCategory category = categoriesById.get(categoryId);
		if (category == null)
			throw new BusinessException("Категория ID=" + categoryId + " не доступна пользователю LOGIN_ID=" + getLogin().getId());

		// Пользователь не может выбрать неправильную категорию:
		// в списке только доступные категории
		if (!cardOperationService.testOperationCompatibleToCategory(operation, category))
			throw new BusinessException("Категория " + category + " не доступна для карточной операции " + operation);

		return category;
	}

	private void validateNewCardOperation(CardOperation operation) throws BusinessLogicException
	{
		validateCardOperation(operation);
		if (operation.getOperationType() == OperationType.BY_CARD && operation.getCardAmount().doubleValue() == 0.00)
			throw new BusinessLogicException("Пожалуйста, введите сумму операции");
	}

	private void validateCardOperation(CardOperation operation) throws BusinessLogicException
	{
		if (StringHelper.isEmpty(operation.getDescription()))
			throw new BusinessLogicException("Пожалуйста, введите название операции");
		if (operation.getDescription().length() > OPERATION_NAME_MAX_LENGTH)
			throw new BusinessLogicException("Слишком длинное название операции. " +
					"Введите не более " + OPERATION_NAME_MAX_LENGTH + " символов");
		if (operation.getCategory() == null)
			throw new BusinessLogicException("Пожалуйста, укажите категорию операции");
	}

	/**
	 * @return список новых категорий
	 */
	public String getNewCategories()
	{
		StringBuilder newCategoriesStr = new StringBuilder();
		for (Long newCategoryId : newCategoriesIds)
		{
			if (newCategoriesStr.length() != 0)
				newCategoriesStr.append(", ");
			newCategoriesStr.append(categoriesById.get(newCategoryId).getName());
		}
		return newCategoriesStr.toString();
	}

	/**
	 * @return количество новых категорий
	 */
	public int getNewCategoriesCount()
	{
		return newCategoriesIds.size();
	}

	public void setCountry(String country)
	{
		this.country = country;
	}

	public void setResetCountry(boolean resetCountry)
	{
		this.resetCountry = resetCountry;
	}
}
