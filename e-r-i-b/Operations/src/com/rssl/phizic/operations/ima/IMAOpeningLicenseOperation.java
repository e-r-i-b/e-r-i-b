package com.rssl.phizic.operations.ima;

import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.deposits.DepositTermsService;
import com.rssl.phizic.business.deposits.TemplateConstants;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.IMAOpeningClaim;
import com.rssl.phizic.business.ima.IMAProduct;
import com.rssl.phizic.business.ima.IMAProductService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.business.documents.exceptions.NotOwnDocumentException;
import com.rssl.phizic.business.documents.payments.validators.IsOwnDocumentValidator;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Mescheryakova
 * @ created 27.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class IMAOpeningLicenseOperation extends OperationBase
{
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();
	private static final String TEMPLATE_FIELD_REGEXP = "(\\{[^\\{^\\}]*\\})";
	private static final String CLOSE_BODY_REGEXP = "(?i)</body>";
	private static final String REMOVE_IMGS_SCRIPT = "<script type=\"text/javascript\">" +
			"var imgs = document.getElementsByTagName('img');" +
			"for(var i=0;i<imgs.length;i++)" +
			"{imgs[i].parentNode.removeChild(imgs[i]);}" +
			"</script></body>";
	private static final String NEW = "Новый";
	private static final String TYPE_VNES = "покупки у Банка обезличенного драгоценного металла";

	private static final Log LOG = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final IMAProductService service = new  IMAProductService();
	private static final DepartmentService departmentService = new DepartmentService();
	private static final PersonService personService = new PersonService();

	private IMAProduct imaProduct;
	private IMAOpeningClaim imaOpeningClaim;

	public void initialize(Long id, Long documentId) throws BusinessException, BusinessLogicException
	{
		BusinessDocument document = businessDocumentService.findById(documentId);
		if (!(document instanceof IMAOpeningClaim))
			throw new BusinessException("Неверный тип документа. Ожидается IMAOpeningClaim.");

		try
		{
			//Проверка владельца документа кроме "АРМ сотрудника"
			if (ApplicationConfig.getIt().getLoginContextApplication() != Application.PhizIA)
			{
				new IsOwnDocumentValidator().validate(document);
			}
		}
		catch(NotOwnDocumentException e)
		{
			throw new AccessException("У данного пользователя нет прав на просмотр договора на открытием ОМС с id заявки ="+documentId, e);
		}

		imaOpeningClaim = (IMAOpeningClaim) document;

		imaProduct = service.findById(id);

		if (imaProduct == null)
			throw new BusinessException("ОМС продукт с id=" + id + " не найден в БД");

	}

	public String getConvertContractTemplate() throws BusinessException
	{
		Map<String,String> templateReplacements = new HashMap<String, String>();
		String amountIMA = imaOpeningClaim.getDestinationAmount().getDecimal().toString();
		String contractTemplate = imaProduct.getContractTemplate().replaceFirst("\\{RUB\\}.\\{KOP\\}", amountIMA.substring(0, amountIMA.indexOf('.') + 2));
		contractTemplate = contractTemplate.replaceFirst("\\{RUB_PROP\\}-\\{KOP\\}","");
		contractTemplate = contractTemplate.replaceFirst("\\(прописью\\)","");
		contractTemplate = contractTemplate.replaceFirst("(\\{TYPE_VNES)\\D*(Банка\\})", TYPE_VNES);
		contractTemplate = contractTemplate.replaceFirst("(\\{TYPE_VNES)\\D*(металла\\})", TYPE_VNES);

		getTemplateReplacements(templateReplacements);
		Matcher matcher = Pattern.compile(TEMPLATE_FIELD_REGEXP).matcher(contractTemplate);
		String convertContractTemplate = null;
		while (matcher.find())
		{
			convertContractTemplate = matcher.replaceFirst(StringHelper.getEmptyIfNull(templateReplacements.get(matcher.group())));
			matcher.reset(convertContractTemplate);
		}
		if (!StringHelper.isEmpty(convertContractTemplate))
			// Дописываем скрипт для скрытия рисунков в конец документа
			return convertContractTemplate.replaceFirst(CLOSE_BODY_REGEXP, REMOVE_IMGS_SCRIPT);
		return "";
	}

	public void getTemplateReplacements(Map<String, String> templateReplacements) throws BusinessException
	{
		templateReplacements.put(TemplateConstants.KOD_VAL, imaProduct.getDefaultLocaleName());

		BusinessDocumentOwner documentOwner = imaOpeningClaim.getOwner();
		if (documentOwner.isGuest())
			throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
		ActivePerson person = personService.findByLogin(documentOwner.getLogin());
		if (person == null)
		{
			LOG.info("Ошибка при получении информации о владельце заявки на открытие ОМС. ID документа " +
					imaOpeningClaim.getId());
			throw new BusinessException("Не удалось найти владельца заявки");
		}

		DepositTermsService depositTermsService = new DepositTermsService();
		depositTermsService.fillDocumentInfo(templateReplacements, person);
		depositTermsService.fillPersonInfo(templateReplacements, imaOpeningClaim);
		fillIMAOpeningClaimInfo(templateReplacements, imaOpeningClaim);
		fillBankInfo(templateReplacements);
	}

	private void fillIMAOpeningClaimInfo(Map<String, String> templateReplacements, IMAOpeningClaim imaOpeningClaim)
	{
		templateReplacements.put(TemplateConstants.ST, NEW);
		templateReplacements.put(TemplateConstants.DATE_OPEN, DateHelper.formatDateToStringWithPoint(imaOpeningClaim.getOpeningDate()));
	}

	private void fillBankInfo(Map<String, String> templateReplacements) throws BusinessException
	{
		ExtendedCodeImpl officeCode = new ExtendedCodeImpl(imaOpeningClaim.getOfficeRegion(),
					imaOpeningClaim.getOfficeBranch(), imaOpeningClaim.getOfficeVSP());
		Department department = departmentService.findByCode(officeCode);
		if (department == null)
			return;

		templateReplacements.put(TemplateConstants.TYPE_OSB, department.getName());
		templateReplacements.put(TemplateConstants.NUM_OSB, department.getCode().getFields().get("branch"));
		templateReplacements.put(TemplateConstants.NUM_FOSB, department.getCode().getFields().get("office"));
		templateReplacements.put(TemplateConstants.ADR_JURO, department.getAddress());
		templateReplacements.put(TemplateConstants.ADR_FOSB, department.getLocation());
		templateReplacements.put(TemplateConstants.TEL_FOSB, department.getTelephone());
	}
}
