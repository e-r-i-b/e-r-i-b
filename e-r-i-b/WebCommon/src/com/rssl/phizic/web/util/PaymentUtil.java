package com.rssl.phizic.web.util;

import com.rssl.common.forms.FormConstants;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.descriptions.ExtendedDescriptionData;
import com.rssl.phizic.business.descriptions.ExtendedDescriptionDataService;
import com.rssl.phizic.business.documents.BusinessDocumentBase;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.LoanPayment;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.loanclaim.LoanClaimConfig;
import com.rssl.phizic.gate.loans.PersonLoanRole;
import com.rssl.phizic.gate.payments.owner.EmployeeInfo;
import com.rssl.phizic.gate.payments.systems.AbstractPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DocumentConfig;
import com.rssl.phizicgate.manager.ext.sbrf.ESBHelper;

/**
 * @author niculichev
 * @ created 13.01.2011
 * @ $Author$
 * @ $Revision$
 */
public class PaymentUtil
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final ExtendedDescriptionDataService extendedDescriptionDataService = new ExtendedDescriptionDataService();

	/**
	 * Проверяет является ли платеж билинговым платежом с карты через шину
	 * @param document платеж
	 * @return true является
	 */
	public static boolean isCardESBBillingPayment(BusinessDocument document)
	{
		try
		{
			if(!(document instanceof AbstractPaymentSystemPayment))
				return false;

			AbstractPaymentSystemPayment payment = (AbstractPaymentSystemPayment) document;

			if((payment.getType() != CardPaymentSystemPayment.class))
				return false;

			if(ESBHelper.isIQWavePayment(payment))
				return false;

			return ESBHelper.isESBSupported(payment);
		}
		catch(Exception e)
		{
			log.error("Ошибка определения, является ли платеж шинным карточным и билинговым", e);
			return false;
		}
	}

	public static String getPersonLoanRole(BusinessDocument document)
	{
		if(!(document instanceof LoanPayment))
			return "";

		LoanPayment payment = (LoanPayment)document;
		try
		{
			LoanLink loanLink = payment.getLoanLink();
			PersonLoanRole role = loanLink.getPersonRole();
			return role.toString();
		}
		catch(Exception e)
		{
			log.error("Ошибка определения роли клиента в кредите: заемщик или поручитель", e);
			return "";
		}
	}

	/**
	 * Получить категорию по названию формы платежа
	 * @param formName - название формы
	 * @return категория
	 */
	public static String getCategoryId(String formName)
	{
		return FormConstants.formNameToCategory.get(formName);
	}

	/**
	 * Получить ссылку на платеж по названию формы платежа
	 * @param formName - название формы
	 * @return ссылка
	 */
	public static String getLinkPaymentByFormName(String formName)
	{
		if (formName.equals("LoanProduct"))
			return ConfigFactory.getConfig(LoanClaimConfig.class).isUseNewAlgorithm() ? "/private/payments/payment.do?form=ExtendedLoanClaim" : "/private/payments/loan_product.do";
		if (formName.equals("AccountOpeningClaim") || formName.equals("AccountOpeningClaimWithClose"))
			return "/private/deposits/products/list.do";
		if (formName.equals("IMAOpeningClaim"))
			return "/private/ima/products/list.do";
		if (formName.equals("LoanCardProduct"))
			return "/private/payments/income_level.do";
		if (formName.equals("JurPayment"))
			return "/private/payments/jurPayment/edit.do";
		return "/private/payments/payment.do";
	}

    /**
     * @return Значение настройки Оплата брони авиабилетов: интервал отправки ajax-запросов
     */
    public static Long getAirlineReservationPaymentAsyncInterval()
    {
        return ConfigFactory.getConfig(DocumentConfig.class).getAirlineReservationInterval();
    }

    /**
     * @return Значение настройки Оплата брони авиабилетов: таймаут отправки ajax-запросов
     */
    public static Long getAirlineReservationPaymentAsyncTimeout()
    {
        return ConfigFactory.getConfig(DocumentConfig.class).getAirlineReservationTimeout();
    }

	/**
	 * @param document платеж
	 * @return информация о сотруднике, создавшем платеж
	 */
	public static EmployeeInfo getCreatedEmployeeInfo(Object document)
	{
		try
		{
			return (document instanceof BusinessDocumentBase) ? ((BusinessDocumentBase) document).getCreatedEmployeeInfo() : null;
		}
		catch (Exception e)
		{
			log.error("Ошибка при получении информации о сотруднике, создавшем платеж", e);
			return null;
		}
	}

	public static String getItunesAgreementATM() throws BusinessException
	{
		ExtendedDescriptionData extendedDescriptionData = extendedDescriptionDataService.getDataByName("itunes-agreement-atm");
		return extendedDescriptionData.getContent();
	}
}
