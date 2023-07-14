package com.rssl.phizic.operations.deposits;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.deposits.DepositProductService;
import com.rssl.phizic.business.deposits.TemplateConstants;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.ChangeDepositMinimumBalanceClaim;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.documents.payments.validators.IsOwnDocumentValidator;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonDocumentTypeComparator;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.utils.CurrencyUtils;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.MoneyHelper;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.StringEscapeUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Balovtsev
 * @version 24.09.13 9:13
 */
public class GetDepositCollateralAgreementsOperation extends OperationBase
{
	private static final DepositProductService   depositProductService   = new DepositProductService();
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();
	private static final ExternalResourceService resourceService = new ExternalResourceService();
	private static final IsOwnDocumentValidator  ownValidator = new IsOwnDocumentValidator();

	private static final String PATTERN_REPLACEABLE_VARIABLE = "(\\{[^\\{^\\}]*\\})";
	private static final String CLOSE_BODY_REGEXP            = "(?i)</body>";
	private static final String REMOVE_IMGS_SCRIPT           = "<script type=\"text/javascript\">" +
															   "var imgs = document.getElementsByTagName('img'); " +
															   "for(var i=0;i<imgs.length;i++) " +
															   "{imgs[i].parentNode.removeChild(imgs[i]);} </script></body>";

	private String collateralAgreement;

	/**
	 * ������������� ��������
	 * @param documentId ������������� ���������
	 */
	public void initialize(final Long documentId) throws BusinessException, BusinessLogicException, DocumentException
	{
		BusinessDocument document = businessDocumentService.findById(documentId);

		ApplicationConfig applicationConfig = ApplicationConfig.getIt();
		if (applicationConfig.getLoginContextApplication() != Application.PhizIA)
			ownValidator.validate(document);

		if (!(document instanceof ChangeDepositMinimumBalanceClaim))
		{
			throw new BusinessException("�������� ��� ���������. ��������� ChangeDepositMinimumBalanceClaim.");
		}

		ChangeDepositMinimumBalanceClaim doc = (ChangeDepositMinimumBalanceClaim) document;

		BusinessDocumentOwner documentOwner = doc.getOwner();
		if (documentOwner.isGuest())
			throw new UnsupportedOperationException("�������� � �������� ������ �� ��������������");
		ActivePerson person = documentOwner.getPerson();
		if (person == null)
		{
			throw new BusinessException("�� ������� ����� ��������� ������.");
		}

		AccountLink accountLink = resourceService.findLinkById(AccountLink.class, Long.parseLong(doc.getAccountId()));
		if (accountLink == null)
			throw new BusinessException("�� ������� ����� �����.");

		Map<String, String> inlineValues = new HashMap<String, String>();
		prepareBankInfo     (doc,        inlineValues);
		preparePersonInfo   (person, inlineValues);
		prepareDocumentInfo (person, inlineValues);
		prepareClaimInfo(doc, accountLink, inlineValues);

		collateralAgreement = depositProductService.findTemplateTextByTypeAndCode(1L, "012111064/1");
		if (StringHelper.isNotEmpty(collateralAgreement))
		{
			collateralAgreement = processCollateralAgreement(inlineValues, collateralAgreement);
		}
	}

	/**
	 * @return ����� ��������������� ����������
	 */
	public String getCollateralAgreement()
	{
		return collateralAgreement;
	}

	private void prepareClaimInfo(ChangeDepositMinimumBalanceClaim doc, AccountLink accountLink, Map<String, String> inlineValues)
	{
		inlineValues.put(TemplateConstants.OSTATOK_N1, MoneyHelper.describeSum(doc.getMinimumBalance().toString()));
		inlineValues.put(TemplateConstants.KOD_VAL_N1, CurrencyUtils.getCurrencySign(doc.getMinDepositBalanceCurrency()));
		inlineValues.put(TemplateConstants.PROC,       doc.getInterestRate().toString());
		inlineValues.put(TemplateConstants.NAME_ST,    doc.getAccountDescription());
		inlineValues.put(TemplateConstants.ST,         accountLink.getNumber());
		inlineValues.put(TemplateConstants.DATE_DOP_DATE_PROP_MONTH, DateHelper.formatDateToMonthInWords(doc.getDocumentDate()));
		Account account = accountLink.getAccount();
		inlineValues.put(TemplateConstants.DATE_OPEN_DATE_PROP_MONTH, DateHelper.formatDateToMonthInWords(account.getOpenDate()));
	}

	private void preparePersonInfo(ActivePerson person, Map<String, String> inlineValues) throws BusinessException, DocumentException
	{
		if (person.getResidenceAddress() != null)
		{
			inlineValues.put(TemplateConstants.ADR, "***");
		}

		inlineValues.put(TemplateConstants.FIO,         StringEscapeUtils.escapeHtml(PersonHelper.getFormattedPersonName(person.getFirstName(), person.getSurName(), person.getPatrName())));
		inlineValues.put(TemplateConstants.INN,         StringEscapeUtils.escapeHtml(person.getInn()));
		inlineValues.put(TemplateConstants.CITIZENSHIP, StringEscapeUtils.escapeHtml(person.getCitizenship()));
		inlineValues.put(TemplateConstants.ADR_REG,     "***");
		inlineValues.put(TemplateConstants.BIRTH_PLACE, "***");
		inlineValues.put(TemplateConstants.E_MAIL,      StringEscapeUtils.escapeHtml(person.getEmail()));
		inlineValues.put(TemplateConstants.DATE_R,      maskAll(DateHelper.formatDateToStringWithPoint(person.getBirthDay())));
		inlineValues.put(TemplateConstants.TEL,         maskAll(person.getMobilePhone()));
		inlineValues.put(TemplateConstants.IS_REPRESENT, "");
	}

	private void prepareDocumentInfo(ActivePerson person, Map<String, String> inlineValues)
	{
		List<PersonDocument> personDocuments = new ArrayList<PersonDocument>(person.getPersonDocuments());
		Collections.sort(personDocuments, new PersonDocumentTypeComparator());

		PersonDocument mainDoc = personDocuments.get(0);

		String docType = null;
		PersonDocumentType documentType = mainDoc.getDocumentType();

		switch (documentType)
		{
			case REGULAR_PASSPORT_RF:
			{
				docType = "��������������� ������� ��";
				break;
			}
			case MILITARY_IDCARD:
			{
				docType = "������������� �������� ���������������";
				break;
			}
			case SEAMEN_PASSPORT:
			{
				docType = "������� ������";
				break;
			}
			case FOREIGN_PASSPORT_RF:
			{
				docType = "����������� ������� ��";
				break;
			}
			case RESIDENTIAL_PERMIT_RF:
			{
				docType = "��� �� ���������� ��";
				break;
			}
			default:
			{
				docType = mainDoc.getDocumentName();
			}
		}

		inlineValues.put(TemplateConstants.TYPE_DOC, docType);
		inlineValues.put(TemplateConstants.SER_DOC,  maskAll(mainDoc.getDocumentSeries()));
		inlineValues.put(TemplateConstants.NOM_DOC,  maskAll(mainDoc.getDocumentNumber()));
		inlineValues.put(TemplateConstants.KOD_DOC,  maskAll(mainDoc.getDocumentIssueByCode()));

		if (mainDoc.getDocumentIssueBy() != null && mainDoc.getDocumentIssueDate() != null)
		{
			inlineValues.put(TemplateConstants.WYD_DOC, "***");
		}
	}

	private void prepareBankInfo(ChangeDepositMinimumBalanceClaim doc, Map<String, String> inlineValues) throws BusinessException
	{
		DepartmentService departmentService = new DepartmentService();
		Department        department        = departmentService.findByCode(doc.getOffice().getCode());

		if (department == null)
		{
			return;
		}

		inlineValues.put(TemplateConstants.TYPE_OSB, department.getName());
		inlineValues.put(TemplateConstants.NUM_OSB,  department.getCode().getFields().get("branch"));
		inlineValues.put(TemplateConstants.NUM_FOSB, department.getCode().getFields().get("office"));
		inlineValues.put(TemplateConstants.ADR_JURO, department.getAddress());
		inlineValues.put(TemplateConstants.ADR_FOSB, department.getLocation());
		inlineValues.put(TemplateConstants.TEL_FOSB, department.getTelephone());
		inlineValues.put(TemplateConstants.CITY,     department.getCity());
	}

	private String processCollateralAgreement(Map<String, String> inlineValues, String collateralAgreement)
	{
		Matcher matcher = Pattern.compile(PATTERN_REPLACEABLE_VARIABLE).matcher(collateralAgreement);

		StringBuffer sb  = new StringBuffer();
		while (matcher.find())
		{
			String val = inlineValues.get( matcher.group() );
			//��� ������� ������������� � ������� ($-����������������� ������), �������� ���
			if (StringHelper.isNotEmpty(val) && val.equals("$"))
				val = "&#36;";
			matcher.appendReplacement(sb, StringHelper.getEmptyIfNull(val));
		}

		return matcher.appendTail(sb).toString().replaceFirst(CLOSE_BODY_REGEXP, REMOVE_IMGS_SCRIPT);
	}

	private String maskAll(String source)
	{
		if (StringHelper.isEmpty(source))
		{
			return new String();
		}

		return source.replaceAll(".", "*");
	}
}
