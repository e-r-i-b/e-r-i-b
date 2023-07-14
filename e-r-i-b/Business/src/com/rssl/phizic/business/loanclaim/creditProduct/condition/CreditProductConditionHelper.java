package com.rssl.phizic.business.loanclaim.creditProduct.condition;

import com.rssl.ikfl.crediting.OfferId;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.loanclaim.creditProduct.CreditProduct;
import com.rssl.phizic.business.loanclaim.creditProduct.CreditProductService;
import com.rssl.phizic.business.loanclaim.creditProduct.type.CreditProductTypeService;
import com.rssl.phizic.business.loanclaim.creditProduct.type.CreditSubProductType;
import com.rssl.phizic.business.offers.LoanOffer;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User: Moshenko
 * Date: 31.07.14
 * Time: 20:59
 * Тут будут вынесены "витиеватые" алгоритмы поиска условие по кредитному продукту.
 */
public class CreditProductConditionHelper
{
	private static final CreditProductConditionService  conditionService  = new CreditProductConditionService();
	private static final CreditProductService productService    = new CreditProductService();
	private static final DepartmentService departmentService = new DepartmentService();

	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 * @param loanOffer Предодобренное предложение.
	 * @return Общие условие по кредитному продукту.
	 * @throws BusinessException
	 */
	public static CreditProductCondition getCreditProductConditionByLoanOffer(LoanOffer loanOffer) throws BusinessException
	{

		OfferId offerId = loanOffer.getOfferId();
		String productCode = loanOffer.getProductCode();

		if (StringHelper.isEmpty(productCode))
		{
			log.error(" В предложении " + offerId + " не задан параметр productCode, определить кредитное условие для него не возможно.");
			return null;
		}
		String subProductCode = loanOffer.getSubProductCode();
		if (StringHelper.isEmpty(subProductCode))
		{
			log.error(" В предложении " + offerId + " не задан параметр subProductCode, определить кредитное условие для него не возможно.");
			return null;
		}
		String offerTb = null;
		if (loanOffer.getTerbank() != null)
			offerTb = loanOffer.getTerbank().toString();
		else
		{
			Long depId = PersonContext.getPersonDataProvider().getPersonData().getPerson().getDepartmentId();
			Department department = departmentService.findById(depId);
			if (department==null)
			{
				log.error("По коду " + depId + " не найден оффис");
				return null;
			}
			Code loanCode = department.getCode();
			offerTb = loanCode.getFields().get("region");
		}
		String offerCurrCode = loanOffer.getMaxLimit().getCurrency().getCode();

		//0. Ищем кредитное продукты по коду из заявки
		List<CreditProduct> products = productService.findByCode(productCode);
		if (products.isEmpty())
		{
			log.error("По коду " + productCode + " в предложении " + offerId + " не найден кредитный продукт");
			return null;
		}
		CreditSubProductType ourSubProduct = null;
		CreditProduct ourProduct = null;
		for(CreditProduct product:products)
		{
			//1. Ищем тип кредитного суб продукта по полям из заявки: код типа суб продукта, тб, валюта
			for (CreditSubProductType subProdType:product.getCreditSubProductTypes())
			{
				String code = subProdType.getCode();
				if (StringHelper.isNotEmpty(code) && code.equals(subProductCode) &&
					StringHelper.equals(subProdType.getTerbank(), offerTb) &&
					StringHelper.equals(subProdType.getCurrency().getCode(), offerCurrCode))
				{
					ourSubProduct = subProdType;
					ourProduct = subProdType.getCreditProduct();
					break;
				}
			}
		}
		if (ourSubProduct == null)
		{
			log.error("По коду " + productCode + " и коду типа кредитного суб  продукта " + subProductCode + ", для предложении " + offerId + " не найден кредитный суб продукт");
			return null;
		}

		//2. Вытаскиваем все опубликованные кредитные условия по найденному  кредитному продукту
		List<CreditProductCondition> conditions = conditionService.getPublishedConditionsByProduct(ourProduct);
		for (CreditProductCondition creditProductCondition:conditions)
		{
			//3. Исключаем кредитное условие, если оно не доступно для ТБ предложения
			if (!creditProductCondition.getDepartmentsList().contains(offerTb))
				continue;
			//4. Ищем действующие повалютное условие
			CurrencyCreditProductCondition currCondition = conditionService.getActiveCurrCondition(creditProductCondition, offerCurrCode);
			//5. Если повалютное условие найдено и оно доступно для клиента, то кредитное условие нам подходит
			if (currCondition != null && currCondition.isClientAvaliable())
			{
				return creditProductCondition;
			}
		}
		log.error("Для предодобренного предложения " + offerId + " не найдено общего кредитного условия.");
		return null;
	}

	/**
	 * Проверка, доступно ли кредитное условие для тербанка клиента.
	 * Для гостя требуется передать список доступных ТБ. Если ТБ гостя не определён, то делается проверка, что кредитный продукт содержит все ТБ из переданного списка.
	 * @param personsTB Тербанк пользователя
	 * @param condition Кредитное условие
	 * @param availableTBs Список тербанков, которые должны содержаться в кредитном продукте
	 * @return Да, если условие доступно для ТБ клиента. Нет, в противном случае.
	 */
	public static boolean isCreditProductConditionAvailable(String personsTB, CreditProductCondition condition, List<String> availableTBs)
	{
		boolean available = false;
		if (personsTB != null)
		{
			String listOfDepartments = condition.getDepartmentsStr();
			if (StringHelper.isNotEmpty(listOfDepartments))
			{
				available =  listOfDepartments.contains(personsTB);
			}
		}
		else
		{
			Set<String> setOfAvailableTBs = new HashSet<String>(condition.getDepartmentsList());
			available = setOfAvailableTBs.containsAll(availableTBs);
		}

		return available;

	}
}