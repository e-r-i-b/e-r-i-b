package com.rssl.phizic.operations.basket;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.basketident.*;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderForFieldFormulas;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.operations.OperationBase;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * Редактирование отображения идентификатора корзины.
 *
 * @author bogdanov
 * @ created 07.11.14
 * @ $Author$
 * @ $Revision$
 */

public class EditBasketIdentifierOperation extends OperationBase
{
	private static final BasketIdentifierService baskerIdentifierService = new BasketIdentifierService();
	private static FieldFormulaService fieldFormulaService = new FieldFormulaService();
	private static ServiceProviderService serviceProviderService = new ServiceProviderService();

	/**
	 * @return список идентификаторов.
	 * @throws BusinessException
	 */
	public List<BasketIndetifierType> getAllIdentifiers() throws BusinessException
	{
		return baskerIdentifierService.getIdentifiers();
	}

	/**
	 * Получение списка имен идентификаторов для подразделения.
	 * @return список.
	 * @throws BusinessException
	 */
	public List<Pair<Integer, String>> getAllIdentifiersNames() throws BusinessException
	{
		List<BasketIndetifierType> tps = getAllIdentifiers();
		List<Pair<Integer, String>> result = new LinkedList<Pair<Integer, String>>();

		result.add(new Pair<Integer, String>());
		result.get(0).setFirst(null);
		result.get(0).setSecond("");
		boolean first = true;
		for (BasketIndetifierType tp : tps)
		{
			if (!first)
				result.get(0).setSecond(result.get(0).getSecond() + ", " + tp.getName());
			else
			{
				result.get(0).setSecond(tp.getName());
				first = false;
			}
		}

		return result;
	}

	/**
	 * @return Спсиок аттрибутов.
	 * @throws BusinessException
	 */
	public List<Pair<BasketIndetifierType, String>> getAttributesNamesFor() throws BusinessException
	{
		List<BasketIndetifierType> tps = getAllIdentifiers();
		List<Pair<BasketIndetifierType, String>> result = new LinkedList<Pair<BasketIndetifierType, String>>();

		for (BasketIndetifierType tp : tps)
		{
			Map<String, AttributeForBasketIdentType> attr = tp.getAttributes();
			StringBuilder sb = new StringBuilder();
			boolean first = true;
			for (String sid : attr.keySet()) {
				if (first)
				{
					first = false;
					sb.append(attr.get(sid).getName());
				}
				else
				{
					sb.append(", ").append(attr.get(sid).getName());
				}
			}
			result.add(new Pair<BasketIndetifierType, String>(tp, sb.toString()));
		}
		return result;
	}

	public BasketIndetifierType findIdentById(long id) throws BusinessException
	{
		return baskerIdentifierService.getInedtifier(id);
	}

	public AttributeForBasketIdentType findAttrById(long id) throws BusinessException
	{
		return baskerIdentifierService.getAttribute(id);
	}

	public void deleteIdentById(long id) throws BusinessException
	{
		baskerIdentifierService.removeIdent(id);
	}

	public void deleteAttributeById(long id) throws BusinessException
	{
		baskerIdentifierService.removeAttribute(id);
	}

	public void addIdentifier(BasketIndetifierType identifier) throws BusinessException
	{
		baskerIdentifierService.addOrUpdate(identifier);
	}

	/**
	 * Получить мапу формул с именами полей ПУ (0 - имя, 1 - формула), ключ - идентификатор поставщика услуг
	 * @param id идентификтор документа
	 * @return мапа формул
	 * @throws BusinessException
	 */
	public Map<Long, List<Object[]>> getFormulasWithName(long id) throws BusinessException
	{
		List<Object[]> formulas = fieldFormulaService.getWithName(id);
		if (CollectionUtils.isEmpty(formulas))
		{
			return Collections.emptyMap();
		}

		Map<Long, List<Object[]>> formulaMap = new HashMap<Long, List<Object[]>>(formulas.size());
		for (Object[] formulaArr : formulas)
		{
			FieldFormula formula = (FieldFormula) formulaArr[1];
			List<Object[]> formulaList = formulaMap.get(formula.getProviderId());

			if (formulaList == null)
			{
				formulaList = new ArrayList<Object[]>();
			}

			formulaList.add(formulaArr);
			formulaMap.put(formula.getProviderId(), formulaList);
		}

		return formulaMap;
	}

	/**
	 * Получить список поставщиков услуг
	 * @param id идентификатор документа
	 * @return список
	 * @throws BusinessException
	 */
	public List<ServiceProviderForFieldFormulas> getServiceProviders(long id) throws BusinessException
	{
		return serviceProviderService.findShortProviderByFieldFormulas(id);
	}

	/**
	 * Удалить связки с поставщиком
	 * @param id идентификатор документа
	 * @param providerId идентификатор поставщика
	 * @throws BusinessException
	 */
	public void removeFormula(long id, long providerId) throws BusinessException
	{
		List<FieldFormula> formulas = fieldFormulaService.getByIdentIdAndProviderId(id, providerId);

		if (CollectionUtils.isNotEmpty(formulas))
		{
			fieldFormulaService.remove(formulas);
		}
	}
}
