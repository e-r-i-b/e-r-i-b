package com.rssl.phizic.business.deposits;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.business.documents.AccountOpeningClaim;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonDocumentTypeComparator;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.OfficeCodeReplacer;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.jmx.BusinessSettingsConfig;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.lang.StringEscapeUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.*;
import javax.xml.transform.TransformerException;

/**
 * Заполняет мапу фиксированными значениями, необходимыми для просмотра условий вклада на странице создания цели.
 * Это временное решение, пока не будет реализован полный механизм открытия вклада.
 * @author lepihina
 * @ created 09.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class FixedDepositProductHelper
{
	private static final PersonService personService = new PersonService();
	private static final DepartmentService departmentService = new DepartmentService();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	protected static final String MAIN_DATA_PATH = "/product/data/main";
	protected static final String PERCENT_PATH = "/product/data/options/element[id=%d]/percent";
	private static final Map<String, String> textTermsToFields = new HashMap<String, String>(8);

	static
	{
		textTermsToFields.put("incomingTransactions", AccountOpeningClaim.INCOMING_TRANSACTIONS);
		textTermsToFields.put("frequencyAdd", AccountOpeningClaim.FREQUENCY_ADD);
		textTermsToFields.put("debitTransactions", AccountOpeningClaim.DEBIT_TRANSACTIONS);
		textTermsToFields.put("frequencyPercent", AccountOpeningClaim.FREQUENCY_PERCENT);
		textTermsToFields.put("percentOrder", AccountOpeningClaim.PERCENT_ORDER);
		textTermsToFields.put("incomeOrder", AccountOpeningClaim.INCOME_ORDER);
		textTermsToFields.put("renewals", AccountOpeningClaim.RENEWALS);
	}

	/**
	 * Возвращает мапу со значениями для шаблона
	 * @param depositProduct - депозитный продукт
	 * @return мапа со значениями для шаблона
	 * @throws BusinessException
	 */
	public static Map<String,String> getTemplateValues(DepositProduct depositProduct) throws BusinessException
	{
		Login login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();

		Map<String,String> templateValues = new HashMap<String, String>();
		fillDepositTerms(templateValues, depositProduct);
		fillPersonInfo(templateValues, login);
		fillBankInfo(templateValues, login);

		return templateValues;
	}

	private static void fillDepositTerms(Map<String, String> templateValues, DepositProduct depositProduct) throws BusinessException
	{
		Currency nationalCurrency = MoneyUtil.getNationalCurrency();

		Document description = null;
		try
		{
			description = XmlHelper.parse(depositProduct.getDescription());
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
		Map<String, String> termsPosition = getTerms(description.getDocumentElement());

		templateValues.put(TemplateConstants.NAME_ST, depositProduct.getName());
		templateValues.put(TemplateConstants.SUM_VKL, "0.00");
		templateValues.put(TemplateConstants.CURR_VKL, nationalCurrency.getName());
		templateValues.put(TemplateConstants.KOD_VAL, nationalCurrency.getName());

		templateValues.put(TemplateConstants.PERCENT, termsPosition.get(AccountOpeningClaim.PERCENT));
		templateValues.put(TemplateConstants.PROC, termsPosition.get(AccountOpeningClaim.INTEREST_RATE));
		templateValues.put(TemplateConstants.PRIXOD, termsPosition.get(AccountOpeningClaim.INCOMING_TRANSACTIONS));
		templateValues.put(TemplateConstants.MIN_ADD, termsPosition.get(AccountOpeningClaim.MIN_ADDITIONAL_FEE));
		templateValues.put(TemplateConstants.PER_ADD, termsPosition.get(AccountOpeningClaim.FREQUENCY_ADD));
		templateValues.put(TemplateConstants.RASXOD, termsPosition.get(AccountOpeningClaim.DEBIT_TRANSACTIONS));

		templateValues.put(TemplateConstants.SUM_NOST, "не предусмотрена");

		templateValues.put(TemplateConstants.PER_PERCENT, termsPosition.get(AccountOpeningClaim.FREQUENCY_PERCENT));
		templateValues.put(TemplateConstants.ORD_PERCENT, termsPosition.get(AccountOpeningClaim.PERCENT_ORDER));
		templateValues.put(TemplateConstants.ORD_DOXOD, termsPosition.get(AccountOpeningClaim.INCOME_ORDER));
		templateValues.put(TemplateConstants.QPROL, termsPosition.get(AccountOpeningClaim.RENEWALS));

		templateValues.put(TemplateConstants.DATE_PROP_MONTH, DateHelper.formatDateToStringWithPoint(Calendar.getInstance()));
		templateValues.put(TemplateConstants.KEY_PRC_CARD, "не согласен");
		templateValues.put(TemplateConstants.DATE_DOP_DATE_PROP_MONTH, DateHelper.formatDateToMonthInWords(Calendar.getInstance()));
		templateValues.put(TemplateConstants.DATE_OPEN_DATE_PROP_MONTH, DateHelper.formatDateToMonthInWords(Calendar.getInstance()));
	}

	private static void fillPersonInfo(Map<String, String> templateValues, Login login) throws BusinessException
	{
		ActivePerson owner = personService.findByLogin(login);

		if (owner == null)
		{
			log.info("Ошибка при получении информации о владельце заявки на открытие вклада.");
			throw new BusinessException("Не удалось найти владельца заявки");
		}

		templateValues.put(TemplateConstants.EDBO_CONTR_NUM, StringEscapeUtils.escapeHtml(owner.getAgreementNumber()));
		templateValues.put(TemplateConstants.EDBO_CONTR_DATE, DateHelper.formatDateToStringWithPoint(owner.getAgreementDate()));
		templateValues.put(TemplateConstants.FIO, StringEscapeUtils.escapeHtml(PersonHelper.getFormattedPersonName(
				owner.getFirstName(), owner.getSurName(), owner.getPatrName())));
		templateValues.put(TemplateConstants.ADR_REG, "***");
		if (owner.getResidenceAddress() != null)
			templateValues.put(TemplateConstants.ADR, "***");
		templateValues.put(TemplateConstants.INN, StringEscapeUtils.escapeHtml(owner.getInn()));
		templateValues.put(TemplateConstants.CITIZENSHIP, StringEscapeUtils.escapeHtml(owner.getCitizenship()));

		templateValues.put(TemplateConstants.DATE_R, maskAll(DateHelper.formatDateToStringWithPoint(owner.getBirthDay())));
		templateValues.put(TemplateConstants.BIRTH_PLACE, "***");
		templateValues.put(TemplateConstants.TEL, maskAll(owner.getMobilePhone()));
		templateValues.put(TemplateConstants.E_MAIL, StringEscapeUtils.escapeHtml(owner.getEmail()));

		fillDocumentInfo(templateValues, owner);
	}

	/**
	 * Заполняет мапу значениями о документе клиента
	 * @param templateValues - мапа
	 * @param owner - клиент
	 */
	public static void fillDocumentInfo(Map<String, String> templateValues, ActivePerson owner)
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

		templateValues.put(TemplateConstants.TYPE_DOC, docType);
		templateValues.put(TemplateConstants.SER_DOC, maskAll(mainDoc.getDocumentSeries()));
		templateValues.put(TemplateConstants.NOM_DOC, maskAll(mainDoc.getDocumentNumber()));

		if (mainDoc.getDocumentIssueBy() != null && mainDoc.getDocumentIssueDate() != null)
			templateValues.put(TemplateConstants.WYD_DOC, "***");

		templateValues.put(TemplateConstants.KOD_DOC, mainDoc.getDocumentIssueByCode());
	}

	private static void fillBankInfo(Map<String, String> templateReplacements, Login login) throws BusinessException
	{
		Map<String, String> tbReplacements = ConfigFactory.getConfig(BusinessSettingsConfig.class).getTBReplacementsMap();
		String tb = login.getLastLogonCardTB();

		if (StringHelper.isEmpty(tb) || StringHelper.isEmpty(login.getLastLogonCardOSB()))
			throw new BusinessException("Не заполнен код ТБ или ОСБ банка, к которому привязана карта");

		if (tbReplacements.keySet().contains(tb))
			tb = tbReplacements.get(tb);

		ExtendedCodeImpl officeCode = new ExtendedCodeImpl(
			StringHelper.removeLeadingZeros(tb),
			StringHelper.removeLeadingZeros(login.getLastLogonCardOSB()),
			StringHelper.removeLeadingZeros(login.getLastLogonCardVSP()));
		officeCode.setBranch(ConfigFactory.getConfig(OfficeCodeReplacer.class).replaceCode(officeCode.getRegion(), officeCode.getBranch()));

		templateReplacements.put(TemplateConstants.NUM_OSB, officeCode.getBranch());
		templateReplacements.put(TemplateConstants.NUM_FOSB, officeCode.getOffice());

		Department department = departmentService.findByCode(officeCode);
		if (department == null)
		{
			log.info("Ошибка при получении информации о подразделении.");
			return;
		}

		templateReplacements.put(TemplateConstants.TYPE_OSB, department.getFullName());
		templateReplacements.put(TemplateConstants.ADR_JURO, department.getAddress());
		templateReplacements.put(TemplateConstants.ADR_FOSB, department.getAddress());
		templateReplacements.put(TemplateConstants.TEL_FOSB, department.getTelephone());

		templateReplacements.put(TemplateConstants.CITY, department.getCity());
	}

	private static Map<String, String> getTerms(Element root) throws BusinessException
	{
		Map<String, String> terms = new HashMap<String, String>();
		try
		{
			Element mainData = XmlHelper.selectSingleNode(root, MAIN_DATA_PATH);
			for (String termsPosition : textTermsToFields.keySet())
			{
				terms.put(textTermsToFields.get(termsPosition), XmlHelper.getSimpleElementValue(mainData, termsPosition));
			}

			// Процентная ставка
			terms.put(AccountOpeningClaim.PERCENT, XmlHelper.getElementValueByPath(root, String.format(PERCENT_PATH, 1)));
		}
		catch (TransformerException e)
		{
			throw new BusinessException(e);
		}

		return terms;
	}

	private static PersonDocument getPersonDocument(ActivePerson owner)
	{
		List<PersonDocument> personDocuments = new ArrayList<PersonDocument>(owner.getPersonDocuments());
		Collections.sort(personDocuments, new PersonDocumentTypeComparator());
		return personDocuments.get(0);
	}

	private static String maskAll(String source)
	{
		if (StringHelper.isEmpty(source))
			return "";

		return source.replaceAll(".", "*");
	}
}
