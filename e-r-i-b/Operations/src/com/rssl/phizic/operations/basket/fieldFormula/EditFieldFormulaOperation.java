package com.rssl.phizic.operations.basket.fieldFormula;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.basketident.*;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShort;
import com.rssl.phizic.business.fields.FieldDescriptionService;
import com.rssl.phizic.business.fields.FieldDescriptionShortcut;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * @author osminin
 * @ created 03.07.15
 * @ $Author$
 * @ $Revision$
 *
 * Операция редактирования связок полей ПУ с атрибутами идентификатора(документа) профиля
 */
public class EditFieldFormulaOperation extends OperationBase
{
	private static BasketIdentifierService basketIdentifierService = new BasketIdentifierService();
	private static FieldFormulaService fieldFormulaService = new FieldFormulaService();
	private static ServiceProviderService serviceProviderService = new ServiceProviderService();
	private static FieldDescriptionService fieldDescriptionService = new FieldDescriptionService();

	private List<FieldDescriptionShortcut> fieldDescriptions;
	private Set<AttributeForBasketIdentType> attributes;
	private List<FieldFormula> formulas;
	private ServiceProviderShort serviceProvider;

	/**
	 * Инициализация операции для новой связки
	 * @param externalId внешний идентификатор поставщика услуг
	 * @param identId идентификатор документа
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initializeNew(String externalId, Long identId) throws BusinessException, BusinessLogicException
	{
		if (StringHelper.isEmpty(externalId))
		{
			throw new BusinessException("Внешний идентификатор поставщика услуг не может быть null.");
		}
		if (identId == null)
		{
			throw new BusinessException("Идентификатор документа не может быть null.");
		}

		initializeServiceProvider(externalId);
		initializeFieldDescription();
		initializeAttributes(identId);
		initializeFormulas(identId);
	}

	/**
	 * Инициализация операции
	 * @param id идентификатор поставщика услуг
	 * @param identId идентификатор документа
	 */
	public void initialize(Long id, Long identId) throws BusinessException, BusinessLogicException
	{
		if (id == null)
		{
			throw new BusinessException("Идентификатор поставщика услуг не может быть null.");
		}
		if (identId == null)
		{
			throw new BusinessException("Идентификатор документа не может быть null.");
		}

		initializeServiceProvider(id);
		initializeFieldDescription();
		initializeAttributes(identId);
		initializeFormulas(identId);
	}

	/**
	 * Сохранить результаты
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void save() throws BusinessException, BusinessLogicException
	{
		fieldFormulaService.addOrUpdate(formulas);
	}

	/**
	 * Удалить выбранные связки поставщика
	 * @param formulasToRemove удаляемые связки
	 * @throws BusinessException
	 */
	public void remove(List<FieldFormula> formulasToRemove) throws BusinessException
	{
		if (CollectionUtils.isEmpty(formulasToRemove))
		{
			fieldFormulaService.remove(formulas);
		}
		else
		{
			fieldFormulaService.remove(formulasToRemove);
		}
	}

	/**
	 * Добавить новые связки
	 * @param newFormulas связки
	 * @throws BusinessException
	 */
	public void add(List<FieldFormula> newFormulas) throws BusinessException, BusinessLogicException
	{
		fieldFormulaService.addOrUpdate(newFormulas);
	}

	/**
	 * @return список формул
	 */
	public List<FieldFormula> getFormulas()
	{
		return formulas;
	}

	/**
	 * @return набор описаний полей ПУ
	 */
	public List<FieldDescriptionShortcut> getFieldDescriptions()
	{
		return fieldDescriptions;
	}

	/**
	 * @return набор атрибутов идентификатора профиля
	 */
	public Set<AttributeForBasketIdentType> getAttributes()
	{
		return attributes;
	}

	/**
	 * @return поставщик услуг
	 */
	public ServiceProviderShort getServiceProvider()
	{
		return serviceProvider;
	}

	private void initializeFormulas(Long identId) throws BusinessException
	{
		formulas = fieldFormulaService.getByIdentIdAndProviderId(identId, serviceProvider.getId());
		if (CollectionUtils.isNotEmpty(formulas))
		{
			Collections.sort(formulas, new Comparator<FieldFormula>()
			{
				public int compare(FieldFormula formula, FieldFormula formula2)
				{
					return formula.getId().compareTo(formula2.getId());
				}
			});
		}
	}

	private void initializeFieldDescription() throws BusinessException, BusinessLogicException
	{
		fieldDescriptions = fieldDescriptionService.getByRecipientId(serviceProvider.getId());
		if (CollectionUtils.isEmpty(fieldDescriptions))
		{
			throw new BusinessLogicException("Поставщик услуг не имеет полей для привязки.");
		}
	}

	private void initializeAttributes(Long identId) throws BusinessException, BusinessLogicException
	{
		BasketIndetifierType basketIndetifierType = basketIdentifierService.getInedtifier(identId);
		if (basketIndetifierType == null)
		{
			throw new BusinessException("Идентификатор профиля по id " + identId + " не найден.");
		}

		attributes = (Set<AttributeForBasketIdentType>) basketIndetifierType.getAttributes().values();
		if (CollectionUtils.isEmpty(attributes))
		{
			throw new BusinessLogicException("Идентификатор профиля не имеет атрибутов для привязки.");
		}
	}

	private void initializeServiceProvider(String externalId) throws BusinessException
	{
		serviceProvider = serviceProviderService.findShortProviderBySynchKey(externalId);
		if (serviceProvider == null)
		{
			throw new BusinessException("Поставщик услуг по идентификатору " + externalId + " не найден.");
		}
	}

	private void initializeServiceProvider(Long id) throws BusinessException
	{
		serviceProvider = serviceProviderService.findShortProviderById(id);
		if (serviceProvider == null)
		{
			throw new BusinessException("Поставщик услуг по идентификатору " + id + " не найден.");
		}
	}
}
