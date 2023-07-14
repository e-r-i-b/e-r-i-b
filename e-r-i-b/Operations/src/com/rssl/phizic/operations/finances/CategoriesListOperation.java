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
	 * ������������� ��������
	 */
	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		initialize();
		if (id == null)
			cardOperationCategory = new CardOperationCategory();
		else
			cardOperationCategory = cardOperationCategoryService.findById(id, getLogin().getId());

		if (cardOperationCategory == null)
			throw new BusinessLogicException("�� �� ������ ��������������� ������ ���������.");
	}

	/**
	 * ��������� ��������� �������� ����� � �������
	 * @param filterData - ��������� ����������
	 * @return ������ ��������� ��������
	 * @throws BusinessException
	 */
	public List<CardOperationCategory> getCategories(FinanceFilterData filterData) throws BusinessException
	{
		return cardOperationService.getCardOperationCategories(filterData.isIncome(), getLogin());
	}

	/**
	 * ��������� ���������
	 */
	@Transactional
	public void save() throws BusinessException, BusinessLogicException
	{
		Long loginId = getLogin().getId();
		Integer personCategoriesSize = cardOperationCategoryService.getPersonCategoriesSize(loginId);

		if(cardOperationCategory.getId() == null && personCategoriesSize >= ConfigFactory.getConfig(FinancesConfig.class).getMaxClientCategories())
			throw new BusinessLogicException("�� ��� ������� ������������ ���������� ��������� ��������.");

		if (cardOperationCategoryService.isExistCategory(loginId, cardOperationCategory.getId(), cardOperationCategory.getName()))
			throw new BusinessLogicException("��������� �������� � ����� ��������� ��� ������� � �������. ����������, ������� ������ ��������.");

		cardOperationCategory.setOwnerId(loginId);
		cardOperationCategoryService.addOrUpdate(cardOperationCategory);
	}

    /**
	 * ������� ���������
	 * @param id ������������� ��������� ���������
     * @return ��������� ���������
	 */
	@Transactional
	public CardOperationCategory delete(Long id) throws BusinessException, ExistOperationsInCardOperationCategoryException
	{
        if (id == null)
            throw new IllegalArgumentException("�� ����� ������������� ���������.");

        CardOperationCategory category = cardOperationCategoryService.findById(id);
        if (category == null)
            throw new BusinessException("�� ������� ��������� � id=" + id);

        //��������� ������
		Long loginId = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin().getId();
        if (!loginId.equals(category.getOwnerId()))
            throw new BusinessException("������� ������� ��������� ��� ����� ���������.");

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
	 * ���������� ���� ��� ���������� ���������
	 * @return ����
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

		log.error("��� ���������� ����� ��� ���������� ���������.");
		return allColors.get(0);
	}

	/**
	 * ��������� ������� ������ ������������� ��������, ����������� � ���������
	 * @param category ���������
	 * @return ������� ������� ������ �����������������
	 */
	public boolean hasRecategorisationRules(CardOperationCategory category) throws BusinessException
	{
		return recategorizationRuleService.existRecategorisationRule(category.getId());
	}
}
