package com.rssl.phizic.business.deposits;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.business.dictionaries.tariffPlan.TarifPlanConfigService;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanConfig;
import com.rssl.phizic.business.documents.AccountOpeningClaim;
import com.rssl.phizic.business.documents.BusinessDocumentBase;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonDocumentTypeComparator;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.utils.*;
import org.apache.commons.lang.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * Сервис для получения данных для шаблона договора на открытие вклада
 * @author Pankin
 * @ created 03.06.2011
 * @ $Author$
 * @ $Revision$
 */

public class DepositTermsService
{
	private static final PersonService personService = new PersonService();
	private static final DepartmentService departmentService = new DepartmentService();
	private static final TarifPlanConfigService tarifPlanConfigService = new TarifPlanConfigService();

	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	public DepositTermsService()
	{
	}

	/**
	 * Получить карту со значениями полей шаблона договора на открытие вклада
	 * @param accountOpeningClaim заявка
	 * @return заполненная карта
	 */
	public Map<String, String> getTemplateReplacements(AccountOpeningClaim accountOpeningClaim) throws BusinessException
	{
		Map<String, String> templateReplacements = new HashMap<String, String>();

		fillDepositTerms(templateReplacements, accountOpeningClaim);
		fillPersonInfo(templateReplacements, accountOpeningClaim);
		fillBankInfo(templateReplacements, accountOpeningClaim);

		return templateReplacements;
	}

	private void fillDepositTerms(Map<String, String> templateReplacements, AccountOpeningClaim accountOpeningClaim) throws BusinessException
	{
		templateReplacements.put(TemplateConstants.NAME_ST, accountOpeningClaim.getDepositName());

		String destinationAmount = accountOpeningClaim.getDestinationAmount().getDecimal().toString();
		templateReplacements.put(TemplateConstants.SUM_VKL, MoneyHelper.describeSum(destinationAmount));

		templateReplacements.put(TemplateConstants.CURR_VKL, accountOpeningClaim.getCurrency().getName());
		templateReplacements.put(TemplateConstants.KOD_VAL, accountOpeningClaim.getCurrency().getName());
		if (accountOpeningClaim.isNeedInitialFee())
		{
			templateReplacements.put(TemplateConstants.QSROK, DateSpanFormat.format(accountOpeningClaim.getPeriod()));
			templateReplacements.put(TemplateConstants.DATA_END,
					DateHelper.formatDateToStringWithPoint(accountOpeningClaim.getClosingDate()));
		}
		templateReplacements.put(TemplateConstants.PERCENT,
				accountOpeningClaim.getTermsPosition(AccountOpeningClaim.PERCENT));
		templateReplacements.put(TemplateConstants.PROC,
				accountOpeningClaim.getTermsPosition(AccountOpeningClaim.INTEREST_RATE));
		templateReplacements.put(TemplateConstants.PRIXOD,
				accountOpeningClaim.getTermsPosition(AccountOpeningClaim.INCOMING_TRANSACTIONS));

		//Сумма минимального размера дополнительного взноса
		String minAdd = accountOpeningClaim.getTermsPosition(AccountOpeningClaim.MIN_ADDITIONAL_FEE);
		String minAddValue = (StringHelper.isNotEmpty(minAdd) && BigDecimal.ZERO.compareTo(new BigDecimal(minAdd)) == 0) ? "не предусмотрен" : minAdd;
		templateReplacements.put(TemplateConstants.MIN_ADD, minAddValue);
		templateReplacements.put(TemplateConstants.PER_ADD,
				accountOpeningClaim.getTermsPosition(AccountOpeningClaim.FREQUENCY_ADD));
		templateReplacements.put(TemplateConstants.RASXOD,
				accountOpeningClaim.getTermsPosition(AccountOpeningClaim.DEBIT_TRANSACTIONS));

		//Сумма неснижаемого остатка
		String minDepositBalance = accountOpeningClaim.isWithMinimumBalance() ?
				MoneyHelper.describeSum(accountOpeningClaim.getTermsPosition(AccountOpeningClaim.MIN_DEPOSIT_BALANCE)) :
								   "не предусмотрена";
		templateReplacements.put(TemplateConstants.SUM_NOST,minDepositBalance);

		templateReplacements.put(TemplateConstants.PER_PERCENT,
				accountOpeningClaim.getTermsPosition(AccountOpeningClaim.FREQUENCY_PERCENT));
		templateReplacements.put(TemplateConstants.ORD_PERCENT,
				accountOpeningClaim.getTermsPosition(AccountOpeningClaim.PERCENT_ORDER));
		templateReplacements.put(TemplateConstants.ORD_DOXOD,
				accountOpeningClaim.getTermsPosition(AccountOpeningClaim.INCOME_ORDER));
		templateReplacements.put(TemplateConstants.QPROL,
				accountOpeningClaim.getTermsPosition(AccountOpeningClaim.RENEWALS));
		templateReplacements.put(TemplateConstants.DATE_PROP_MONTH, accountOpeningClaim.getExecutionDate() == null ?
				DateHelper.formatDateToStringWithPoint(accountOpeningClaim.getOpeningDate()) :
				DateHelper.formatDateToStringWithPoint(accountOpeningClaim.getExecutionDate()));
		templateReplacements.put(TemplateConstants.KEY_PRC_CARD,
				StringHelper.isNotEmpty(accountOpeningClaim.getPercentCardNumber()) ? "согласен" : "не согласен");
		templateReplacements.put(TemplateConstants.KEY_PRC_CARD_NUM,
				MaskUtil.getCutCardNumber(accountOpeningClaim.getPercentCardNumber()));
		templateReplacements.put(TemplateConstants.DATE_DOP_DATE_PROP_MONTH, DateHelper.formatDateToMonthInWords(accountOpeningClaim.getDocumentDate()));
		templateReplacements.put(TemplateConstants.DATE_OPEN_DATE_PROP_MONTH, DateHelper.formatDateToMonthInWords(accountOpeningClaim.getDocumentDate()));
		templateReplacements.put(TemplateConstants.DCF_VAL, accountOpeningClaim.getCurrency().getName());

		try
		{
			String tarifPlanCodeType = accountOpeningClaim.getTarifPlanCodeType();
			if (StringHelper.isNotEmpty(tarifPlanCodeType))
			{
				TariffPlanConfig tariffPlanConfig = tarifPlanConfigService.getTarifPlanConfigByTarifPlanCodeType(tarifPlanCodeType);
				if (tariffPlanConfig != null)
					templateReplacements.put(TemplateConstants.TARIF_PLAN_DESC, tariffPlanConfig.getName());
			}
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
	}

	public void fillPersonInfo(Map<String, String> templateReplacements, BusinessDocumentBase accountOpeningClaim) throws BusinessException
	{
		BusinessDocumentOwner documentOwner = accountOpeningClaim.getOwner();
		if (documentOwner.isGuest())
			throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
		ActivePerson owner = personService.findByLogin(documentOwner.getLogin());

		if (owner == null)
		{
			log.info("Ошибка при получении информации о владельце заявки на открытие вклада. ID документа " +
					accountOpeningClaim.getId());
			throw new BusinessException("Не удалось найти владельца заявки");
		}

		templateReplacements.put(TemplateConstants.EDBO_CONTR_NUM,  StringEscapeUtils.escapeHtml(owner.getAgreementNumber()));

		Calendar agreementDate = owner.getAgreementDate();
		templateReplacements.put(TemplateConstants.EDBO_CONTR_DATE,            DateHelper.formatDateToStringWithPoint(agreementDate));
		templateReplacements.put(TemplateConstants.EDBO_CONTR_DATE_PROP_MONTH, DateHelper.formatDateToMonthInWords(agreementDate));

		templateReplacements.put(TemplateConstants.FIO, StringEscapeUtils.escapeHtml(PersonHelper.getFormattedPersonName(
				owner.getFirstName(), owner.getSurName(), owner.getPatrName())));
		// Открытие вклада доступно только для УДБО клиентов, адрес у них хранится строкой
		templateReplacements.put(TemplateConstants.ADR_REG, "***");
		if (owner.getResidenceAddress() != null)
			templateReplacements.put(TemplateConstants.ADR, "***");
		templateReplacements.put(TemplateConstants.INN, StringEscapeUtils.escapeHtml(owner.getInn()));
		templateReplacements.put(TemplateConstants.CITIZENSHIP, StringEscapeUtils.escapeHtml(owner.getCitizenship()));

		templateReplacements.put(TemplateConstants.DATE_R, maskAll(DateHelper.formatDateToStringWithPoint(owner.getBirthDay())));
		templateReplacements.put(TemplateConstants.BIRTH_PLACE, "***");
		templateReplacements.put(TemplateConstants.TEL, maskAll(owner.getMobilePhone()));
		templateReplacements.put(TemplateConstants.E_MAIL, StringEscapeUtils.escapeHtml(owner.getEmail()));

		Calendar tarifPlanConnectionDate = owner.getTarifPlanConnectionDate();
		if (tarifPlanConnectionDate != null)
		{
			templateReplacements.put(TemplateConstants.DATE_TP_PROP_MONTH, DateHelper.formatDateToMonthInWords(tarifPlanConnectionDate));
		}

		fillDocumentInfo(templateReplacements, owner);
	}

	public void fillDocumentInfo(Map<String, String> templateReplacements, ActivePerson owner)
	{
		PersonDocument mainDoc = getPersonDocument(owner);

		String docType;
		PersonDocumentType documentType = mainDoc.getDocumentType();

		// аналогично анкете клиента
		switch (documentType)
		{
			case REGULAR_PASSPORT_RF:
				docType = "Общегражданский паспорт РФ";
				break;
			case MILITARY_IDCARD:
				docType = "Удостоверение личности военнослужащего";
				break;
			case SEAMEN_PASSPORT:
				docType = "Паспорт моряка";
				break;
			case RESIDENTIAL_PERMIT_RF:
				docType = "Вид на жительство РФ";
				break;
			case FOREIGN_PASSPORT_RF:
				docType = "Заграничный паспорт РФ";
				break;
			default:
				docType = mainDoc.getDocumentName();
		}

		templateReplacements.put(TemplateConstants.TYPE_DOC, docType);
		templateReplacements.put(TemplateConstants.SER_DOC, maskAll(mainDoc.getDocumentSeries()));
		templateReplacements.put(TemplateConstants.NOM_DOC, maskAll(mainDoc.getDocumentNumber()));

		if (mainDoc.getDocumentIssueBy() != null && mainDoc.getDocumentIssueDate() != null)
			templateReplacements.put(TemplateConstants.WYD_DOC, "***");

		templateReplacements.put(TemplateConstants.KOD_DOC, maskAll(mainDoc.getDocumentIssueByCode()));
	}

	private void fillBankInfo(Map<String, String> templateReplacements, AccountOpeningClaim accountOpeningClaim) throws BusinessException
	{
		ExtendedCodeImpl officeCode = null;
		Department department = null;

		String curatorTb = accountOpeningClaim.getCuratorTb();
		String curatorOsb = accountOpeningClaim.getCuratorOsb();
		String curatorVsp = accountOpeningClaim.getCuratorVsp();

		if (StringHelper.isNotEmpty(curatorTb) && StringHelper.isNotEmpty(curatorOsb) && StringHelper.isNotEmpty(curatorVsp))
		{
			officeCode = new ExtendedCodeImpl(
					StringHelper.removeLeadingZeros(curatorTb),
					StringHelper.removeLeadingZeros(curatorOsb),
					StringHelper.removeLeadingZeros(curatorVsp));
			department = departmentService.findByCode(officeCode);

			if (department == null)
			{
				String errorMsg = accountOpeningClaim.getCuratorType().equalsIgnoreCase("КМ") ? "клиентского менеджера" : "промоутера";
				log.info(String.format("Ошибка при получении информации о подразделении %s (ТБ=%s ОСБ=%s ВСП=%s), к которому привязана заявка на открытие " +
									   "вклада. ID документа = %s.", errorMsg, curatorTb, curatorOsb, curatorVsp, accountOpeningClaim.getId()));
			}
		}
		//Если не нашли подразделение по КМ или промоутеру, то ищем по-старому
		if (department == null)
		{
			officeCode = new ExtendedCodeImpl(
				StringHelper.removeLeadingZeros(accountOpeningClaim.getAccountTb()),
				StringHelper.removeLeadingZeros(accountOpeningClaim.getAccountOsb()),
				StringHelper.removeLeadingZeros(accountOpeningClaim.getAccountVsp()));
			department = departmentService.findByCode(officeCode);
		}

		templateReplacements.put(TemplateConstants.NUM_OSB, officeCode.getBranch());
		templateReplacements.put(TemplateConstants.NUM_FOSB, officeCode.getOffice());

		if (department == null)
		{
			log.info("Ошибка при получении информации о подразделении, к которому привязана заявка на открытие " +
					 "вклада. ID документа = " + accountOpeningClaim.getId());
			return;
		}

		templateReplacements.put(TemplateConstants.TYPE_OSB, department.getFullName());
		templateReplacements.put(TemplateConstants.ADR_JURO, department.getAddress());
		templateReplacements.put(TemplateConstants.ADR_FOSB, department.getAddress());
		templateReplacements.put(TemplateConstants.TEL_FOSB, department.getTelephone());

		templateReplacements.put(TemplateConstants.CITY, department.getCity());
	}

	private PersonDocument getPersonDocument(ActivePerson owner)
	{
		List<PersonDocument> personDocuments = new ArrayList<PersonDocument>(owner.getPersonDocuments());
		Collections.sort(personDocuments, new PersonDocumentTypeComparator());
		return personDocuments.get(0);
	}

	private String maskAll(String source)
	{
		if (StringHelper.isEmpty(source))
			return "";

		return source.replaceAll(".", "*");
	}
}
