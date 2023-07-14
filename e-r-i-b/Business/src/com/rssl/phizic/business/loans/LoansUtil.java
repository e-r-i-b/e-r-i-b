package com.rssl.phizic.business.loans;

import com.rssl.ikfl.crediting.OfferId;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.LoanCardOfferClaim;
import com.rssl.phizic.business.loanCardOffer.LoanCardOfferType;
import com.rssl.phizic.business.loanclaim.type.LoanRate;
import com.rssl.phizic.business.loans.kinds.LoanKind;
import com.rssl.phizic.business.loans.kinds.LoanKindService;
import com.rssl.phizic.business.loans.products.LoanProductBase;
import com.rssl.phizic.business.loans.products.ModifiableLoanProduct;
import com.rssl.phizic.business.loans.products.ModifiableLoanProductService;
import com.rssl.phizic.business.offers.LoanCardOffer;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.context.EmployeeData;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 @author Pankin
 @ created 24.09.2010
 @ $Author$
 @ $Revision$
 */
public class LoansUtil
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final LoanKindService loanKindService = new LoanKindService();
	private static final ModifiableLoanProductService loanProductService = new ModifiableLoanProductService();

    private static final String FIRST_PART = "Кредит «";
    private static final String LAST_PART = "»";

	/**
	 * Определяем, есть ли у клиента непогашенные кредиты с дифференцированным способом погашения
	 * Сделано для определения наличия ссылки "Погасить кредит" на странице информации по продукту
	 * @return true - есть, false - нет
	 */
	public static boolean isOpenedDifLoan()
	{
		Boolean isHasOpenedDifLoan = PersonContext.getPersonDataProvider().getPersonData().isHasOpenedDifLoan();
		return  isHasOpenedDifLoan == null ? false : isHasOpenedDifLoan;
	}

	/**
	 * Поиск кредита по номеру ссудного счета
	 * @param number - номер ссудного счета
	 * @return LoanLink
	 */
	public static LoanLink getLoanLink(String number)
	{
		if (StringHelper.isEmpty(number))
			return null;
		try
		{
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			LoanLink link = personData.findLoan(number);
			if (link == null)
				log.warn("Не найден LoanLink. номер ссудного счета=" + number);

			return link;
		}
		catch (Exception ex)
		{
			log.error("Ошибка поиска кредита", ex);
			return null;
		}
	}

/**
	 * Ищет LoanProduct по идентификатору
	 * @param id - идентификатор
	 * @return
	 */
	public static ModifiableLoanProduct getLoanProductById(Long id)
	{
		try
		{
			return loanProductService.findById(id);
		}
		catch (Exception e)
		{
			log.error("Ошибка определения кредитного продукта по id", e);
			return null;
		}
	}

	/**
	 * Преобразуем пришедшую заявку на кредит типа LoanProductBase к ModifiableLoanProduct
	 * Нужно исключительно для нужд jsp
	 * @param product -  кредитный продукт
	 * @return тот же кредитный продукт, но приведенный к другому типу
	 */
	public static ModifiableLoanProduct toModifiableLoanProduct(LoanProductBase product)
	{
		if (product instanceof  ModifiableLoanProduct)
			return (ModifiableLoanProduct) product;

		return null;
	}

	/**
	 * Получить список всех видов кредитов
	 * @return список всех видов кредитов
	 */
	public static List<LoanKind> getAllLoanKinds()
	{
		try
		{
			return loanKindService.getAll();
		}
		catch (BusinessException e)
		{
			log.error("Ошибка получения списка всех видов кредитов", e);
			return new ArrayList<LoanKind>();
		}
	}

	/**
	 * Получить количество всех видов кредитов
	 * @return количество всех видов кредитов
	 */
	public static int getCountAllLoanKinds()
	{
		List<LoanKind> loanKinds = getAllLoanKinds();
		if (loanKinds == null)
			return 0;
		return loanKinds.size();
	}

	/**
	 * Получить список всех кредитных продуктов
	 * @return список всех кредитных продуктов
	 */
	public static List<ModifiableLoanProduct> getAllLoanProducts()
	{
		try
		{
			EmployeeData employeeData = EmployeeContext.getEmployeeDataProvider().getEmployeeData();
			return loanProductService.getAllProducts(employeeData.isAllTbAccess(),employeeData.getEmployee().getLogin().getId());
		}
		catch (BusinessException e)
		{
			log.error("Ошибка получения списка всех кредитных продуктов", e);
			return new ArrayList<ModifiableLoanProduct>();
		}
	}

	/**
	 * Определить есть ли опубликованные карточные кредитные продукты
	 * @return true/false
	 */
	public static Boolean isPublishedProductsExists()
	{
		try
		{
			return loanProductService.isPublishedProductsExists();
		}
		catch (BusinessException e)
		{
			log.error("Ошибка определения наличия опубликованных  кредитных продуктов", e);
			return false;
		}
	}

	/**
	 * @param offerIdAsString - идентификатор предложения
	 * @return тип предложения
	 */
	public static LoanCardOfferType getLoanCardOfferType(String offerIdAsString)
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		OfferId offerId = OfferId.fromString(offerIdAsString);
		try
		{
			LoanCardOffer offer = personData.findLoanCardOfferById(offerId);
			if (offer != null)
				return offer.getOfferType();

			log.error("Предложение с id=" + offerIdAsString + " не найдено.");
			return null;
		}
		catch (BusinessException e)
		{
			log.error("Ошибка при получении типа предложения.", e);
			return null;
		}
	}

    /**
     * Проверяет существует ли еще предложение, по которому была оформлена заявка на кред. карту
     * @param document заявка на предодобренную кред. карту
     * @return true - предложение еще существует, false - предложение уже удалено
     */
    public static boolean existCreditCardOffer(BusinessDocument document)
    {
        try
        {
            if (!(document instanceof LoanCardOfferClaim))
                return true;
            
            LoanCardOfferClaim claim = (LoanCardOfferClaim) document;
	        PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
	        if (StringHelper.isNotEmpty(claim.getLoan()))
	            return personData.existCreditCardOffer(Long.valueOf(claim.getLoan()));
	        else
		        return false;
        }
        catch (BusinessException e)
        {
            log.error("Ошибка при проверке наличия уже отправленных в банк заявок", e);
            return false;
        }
    }

	/**
	 * Возвращает ставку по кредиту в формате n% или n1 - n2%
	 * @param loanRate
	 * @return
	 */
	public static String formatLoanRate(LoanRate loanRate)
	{
		try
		{
			BigDecimal minLoanRate = loanRate.getMinLoanRate();
			BigDecimal maxLoanRate = loanRate.getMaxLoanRate();

			DecimalFormat df = new DecimalFormat("0.##");

			if (minLoanRate.compareTo(maxLoanRate) == 0)
				return df.format(minLoanRate.stripTrailingZeros()) + "%";

			return df.format(minLoanRate.stripTrailingZeros()) +  " - " + df.format(maxLoanRate.stripTrailingZeros()) + "%";
		} catch (Exception e)
		{
			log.error("Ошибка при форматировании процентной ставки по кредиту.", e);
			return "";
		}
	}

	/**
	 * Возвращает ставку по кредиту в формате n%
	 * @param percentRate
	 * @return
	 */
	public static String formatPercentRate(BigDecimal percentRate)
	{
		try
		{
			DecimalFormat df = new DecimalFormat("0.##");
			return df.format(percentRate.stripTrailingZeros()) + "%";

		} catch (Exception e)
		{
			log.error("Ошибка при форматировании процентной ставки по кредиту.", e);
			return "";
		}
	}

    /**
     * Возвращает красивое название кредита
     * @param name
     * @return
     */
    public static String buildFullLoanTitle(String name) {
        StringBuilder builder = new StringBuilder(FIRST_PART);
        builder.append(name);
        builder.append(LAST_PART);
        return builder.toString();
	}
}
