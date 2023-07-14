package com.rssl.phizic.operations.finances;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.finances.CardOperationCategory;
import com.rssl.phizic.business.dictionaries.finances.ExistOperationsInCardOperationCategoryException;
import com.rssl.phizic.business.finances.CardOperationService;
import com.rssl.phizic.business.finances.recategorization.ALFRecategorizationRuleService;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.finances.FinancesConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.EditEntityOperation;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

/**
 * @author Pankin
 * @ created 15.08.2011
 * @ $Author$
 * @ $Revision$
 */

public class CategoriesListOperation extends FinancesOperationBase implements EditEntityOperation
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final CardOperationService cardOperationService = new CardOperationService();
	private static final ALFRecategorizationRuleService recategorizationRuleService = new ALFRecategorizationRuleService();
	private CardOperationCategory cardOperationCategory;

	/**
	 * Инициализация операции
	 */
	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		initialize();
		if (id == null)
			cardOperationCategory = new CardOperationCategory();
		else
			cardOperationCategory = cardOperationCategoryService.findById(id, getLogin().getId());

		if (cardOperationCategory == null)
			throw new BusinessLogicException("Вы не можете отредактировать данную категорию.");
	}

	/**
	 * Получение категорий операций банка и клиента
	 * @param filterData - параметры фильтрации
	 * @return список категорий операций
	 * @throws BusinessException
	 */
	public List<CardOperationCategory> getCategories(FinanceFilterData filterData) throws BusinessException
	{
		return cardOperationService.getCardOperationCategories(filterData.isIncome(), getLogin());
	}

	/**
	 * Сохранить категорию
	 */
	@Transactional
	public void save() throws BusinessException, BusinessLogicException
	{
		Long loginId = getLogin().getId();
		Integer personCategoriesSize = cardOperationCategoryService.getPersonCategoriesSize(loginId);

		if(cardOperationCategory.getId() == null && personCategoriesSize >= ConfigFactory.getConfig(FinancesConfig.class).getMaxClientCategories())
			throw new BusinessLogicException("Вы уже создали максимальное количество категорий операций.");

		if (cardOperationCategoryService.isExistCategory(loginId, cardOperationCategory.getId(), cardOperationCategory.getName()))
			throw new BusinessLogicException("Категория операций с таким названием уже создана в системе. Пожалуйста, укажите другое название.");

		cardOperationCategory.setOwnerId(loginId);
		cardOperationCategoryService.addOrUpdate(cardOperationCategory);
	}

    /**
	 * Удалить категорию
	 * @param id Идентификатор удаляемой категории
     * @return Удаленная категория
	 */
	@Transactional
	public CardOperationCategory delete(Long id) throws BusinessException, ExistOperationsInCardOperationCategoryException
	{
        if (id == null)
            throw new IllegalArgumentException("Не задан идентификатор категории.");

        CardOperationCategory category = cardOperationCategoryService.findById(id);
        if (category == null)
            throw new BusinessException("Не найдена категория с id=" + id);

        //проверяем доступ
		Long loginId = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin().getId();
        if (!loginId.equals(category.getOwnerId()))
            throw new BusinessException("Попытка удалить системную или чужую категорию.");

		try
		{
			recategorizationRuleService.removeRecategorizationRuleByCategory(category.getId());
			cardOperationCategoryService.delete(category);
		}
		catch(ConstraintViolationException ex)
		{
			throw new ExistOperationsInCardOperationCategoryException(ex);
		}

        return category;
    }

	public CardOperationCategory getEntity() throws BusinessException, BusinessLogicException
	{
		return cardOperationCategory;
	}

	/**
	 * Возвращает цвет для клиентской категории
	 * @return цвет
	 * @throws BusinessException
	 */
	public String getFreeColor() throws BusinessException
	{
		if (cardOperationCategory.getColor() != null)
			return cardOperationCategory.getColor();

		List<String> allColors = ConfigFactory.getConfig(FinancesConfig.class).getColorsList();
		List<String> usedColors = cardOperationCategoryService.getUsedColors(getLogin().getId());

		for (String color : allColors)
		{
			if (!usedColors.contains(color))
				return color;
		}

		log.error("Нет свободного цвета для клиентской категории.");
		return allColors.get(0);
	}

	/**
	 * Проверяет наличие правил перекодировки операций, привязанных к категории
	 * @param category категория
	 * @return признак наличия правил перекатегоризации
	 */
	public boolean hasRecategorisationRules(CardOperationCategory category) throws BusinessException
	{
		return recategorizationRuleService.existRecategorisationRule(category.getId());
	}
}
