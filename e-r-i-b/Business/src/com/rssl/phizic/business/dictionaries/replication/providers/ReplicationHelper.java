package com.rssl.phizic.business.dictionaries.replication.providers;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.parsers.BigDecimalParser;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.BankDictionaryService;
import com.rssl.phizic.business.dictionaries.billing.Billing;
import com.rssl.phizic.business.dictionaries.billing.BillingService;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.business.dictionaries.payment.services.PaymentService;
import com.rssl.phizic.business.dictionaries.payment.services.PaymentServiceService;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.dictionaries.regions.RegionDictionaryService;
import com.rssl.phizic.business.limits.GroupRisk;
import com.rssl.phizic.business.limits.GroupRiskService;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.classic.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

import static com.rssl.phizic.common.types.documents.ServiceProvidersConstants.*;

/**
 * @author khudyakov
 * @ created 02.03.2011
 * @ $Author$
 * @ $Revision$
 */
public class ReplicationHelper
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final ServiceProviderService serviceProviderService = new ServiceProviderService();
	private static final PaymentServiceService paymentServiceService = new PaymentServiceService();
	private static final BankDictionaryService bankDictionaryService = new BankDictionaryService();
	private static final RegionDictionaryService regionService = new RegionDictionaryService();
	private static final GroupRiskService groupRiskService = new GroupRiskService();
	private static final DepartmentService departmentService = new DepartmentService();
	private static final BillingService billingService = new BillingService();
	private static final BigDecimalParser decimalParser = new BigDecimalParser();


	/**
	 * Проверка на наличие значения
	 * @param fieldValue      значение поля
	 * @param errorsCollector хранилище ошибок
	 * @param fieldValidators валидаторы
	 * @return true/false
	 */
	static boolean validate(String fieldValue, List<String> errorsCollector, FieldValidator ... fieldValidators)
	{
		boolean valid = true;
		for (FieldValidator fieldValidator : fieldValidators)
		{
			try
			{
				if (!fieldValidator.validate(fieldValue))
				{
					log.info("[Ошибка валидации(ReplicationHelper)]: " + fieldValidator.getMessage() + ". " +
					        "Значение: " + fieldValue);
					errorsCollector.add(fieldValidator.getMessage());
					valid = false;
				}
			}
			catch (TemporalDocumentException e)
			{
				log.error("Ошибка при валидации поля.", e);
			}
		}

		return valid;
	}

	/**
	 * Проверка на наличие значения
	 * @param fieldName       название поля
	 * @param fieldValue      значение поля
	 * @param obj             объектное значение поля
	 * @param errorsCollector хранилище ошибок
	 * @return true/false
	 */
	static boolean validate(String fieldName, String fieldValue, Object obj, List<String> errorsCollector)
	{
		if (obj != null)
			return true;

		errorsCollector.add(String.format("Значение %s для поля %s не найдено в справочнике системы.", fieldValue, fieldName));
		return false;
	}

	static RequiredFieldValidator buildRequiredValidator(String fieldName)
	{
		String message = String.format(REQUIRED_VALIDATOR_ERROR_MESSAGE, fieldName);
		return new RequiredFieldValidator(message);
	}

	static RegexpFieldValidator buildRegexpValidator(String regexp, String fieldName)
	{
		String message = String.format(REGEXP_VALIDATOR_ERROR_BASE_MESSAGE, fieldName);
		return new RegexpFieldValidator(regexp, message);
	}

	static BillingServiceProvider findProviderBySynchKey(Comparable synchKey)
	{
		Session session = null;
		BillingServiceProvider serviceProvider = null;

		try
		{
			//noinspection HibernateResourceOpenedButNotSafelyClosed
			session = HibernateExecutor.getSessionFactory().openSession();
			DetachedCriteria criteria = DetachedCriteria.forClass(ServiceProviderBase.class).add(Expression.eq("synchKey", synchKey));
			serviceProvider = (BillingServiceProvider) criteria.getExecutableCriteria(session).uniqueResult();
		}
		catch (Exception e)
		{
			//ошибка получения поставщика не должна прервать репликацию поставщика
			log.error("Ошибка при поиске поставщика услуг, synchKey = " + synchKey, e);
		}
		finally
		{
			if (session != null)
			{
				session.close();
			}
		}
		return serviceProvider;
	}

	static PaymentService findPaymentService(String synchKey, String dbInstanceName)
	{
		PaymentService service = null;
		try
		{
			service = paymentServiceService.findBySynchKey(synchKey, dbInstanceName);
		}
		catch (BusinessException e)
		{
			//ошибка получения услуги не должна прервать репликацию поставщика
			log.error("Ошибка получения услуги, synchKey = " + synchKey, e);
		}
		return service;
	}

	static ResidentBank findResidentBank(String bic, String dbInstanceName)
	{
		ResidentBank bank = null;
		try
		{
			bank = bankDictionaryService.findByBIC(bic, dbInstanceName);
		}
		catch (BusinessException e)
		{
			//ошибка получения банка не должна прервать репликацию поставщика
			log.error("Ошибка при поиске банка, bic = " + bic, e);
		}
		return bank;
	}

	static Region findRegion(String code, String dbInstanceName)
	{
		Region region = null;
		try
		{
			region = regionService.findBySynchKey(code, dbInstanceName);
		}
		catch (BusinessException e)
		{
			//ошибка получения региона не должна прервать репликацию поставщика
			log.error("Ошибка при поиске региона обслуживания, code = " + code, e);
		}
		return region;
	}

	static Department findDepartment(String value, String dbInstanceName)
	{
		if (StringHelper.isEmpty(value))
			return null;

		String[] code = value.split(SPLITER);
		String region = code[0].trim();
		String branch = (code.length > 1) ? code[1].trim() : null;
		String office = (code.length > 2) ? code[2].trim() : null;

		Department department = null;
		try
		{
			department = departmentService.findByCode(new ExtendedCodeImpl(region, branch, office), dbInstanceName);
		}
		catch (BusinessException e)
		{
			//ошибка получения департамента не должна прервать репликацию поставщика
			log.error("Ошибка при поиске департамента обслуживания, code = " + value, e);
		}
		return department;
	}

	static Billing findBilling(String code, String dbInstanceName)
	{
		Billing billing = null;
		try
		{
			billing = billingService.getByCode(code, dbInstanceName);
		}
		catch (BusinessException e)
		{
			//ошибка получения билинговой системы не должна прервать репликацию поставщика
			log.error("Ошибка при поиске биллинговой смистемы, code = " + code, e);
		}
		return billing;
	}

	static BillingServiceProvider findProviderByMobileBankCode(String mobileBankCode, String dbInstanceName)
	{
		BillingServiceProvider serviceProvider = null;
		try
		{
			serviceProvider = serviceProviderService.findByMobileBankCode(mobileBankCode, dbInstanceName);
		}
		catch (BusinessException e)
		{
			//ошибка получения поставщика не должна прервать репликацию поставщика
			log.error("Ошибка при поиске поставщика услуг, mobileBankCode = " + mobileBankCode, e);
		}
		return serviceProvider;
	}

	static BigDecimal parseBigDecimalValue(String value)
	{
		try
		{
			return decimalParser.parse(value);
		}
		catch (ParseException ignore)
		{
			return null;
		}
	}

	static boolean parseBooleanValue(String value)
	{
		return Boolean.valueOf(value) || ("1").equals(value);
	}

	public static GroupRisk findGroupRisk(String groupRiskName, String dbInstanceName)
	{
		GroupRisk groupRisk = null;
		try
		{
			groupRisk = groupRiskService.findByName(groupRiskName, dbInstanceName);
		}
		catch (BusinessException e)
		{
			//ошибка получения группы риска не должна прервать репликацию поставщика
			log.error("Ошибка при поиске группы риска, название = " + groupRiskName, e);
		}
		return groupRisk;
	}

	public static GroupRisk findDefaultGroupRisk(String dbInstanceName)
	{
		GroupRisk defaultGroupRisk = null;
		try
		{
			defaultGroupRisk = groupRiskService.getDefaultGroupRisk(dbInstanceName);
		}
		catch (BusinessException e)
		{
			//ошибка получения группы риска не должна прервать репликацию поставщика
			log.error("Ошибка при поиске группы риска по умолчанию", e);
		}
		return defaultGroupRisk;
	}
}
