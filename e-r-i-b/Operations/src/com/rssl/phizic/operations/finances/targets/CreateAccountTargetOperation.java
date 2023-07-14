package com.rssl.phizic.operations.finances.targets;

import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.deposits.DepositProduct;
import com.rssl.phizic.business.deposits.DepositProductService;
import com.rssl.phizic.business.deposits.FixedDepositProductHelper;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanHelper;
import com.rssl.phizic.business.documents.AccountOpeningClaim;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.documents.payments.source.NewDocumentSource;
import com.rssl.phizic.business.ext.sbrf.deposits.DepositProductShortCut;
import com.rssl.phizic.business.finances.targets.AccountTarget;
import com.rssl.phizic.business.finances.targets.AccountTargetService;
import com.rssl.phizic.business.finances.targets.TargetType;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.profile.images.ImageContainer;
import com.rssl.phizic.business.profile.images.ImageInfo;
import com.rssl.phizic.business.profile.images.NotLoadedImage;
import com.rssl.phizic.business.profile.images.UserImageService;
import com.rssl.phizic.business.statemachine.PaymentStateMachineService;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.payment.support.DbDocumentTarget;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.transform.TransformerException;

/**
 * @author lepihina
 * @ created 05.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class CreateAccountTargetOperation extends BaseTargetOperation implements EditEntityOperation
{
	private static final String TARIFF_TERMS = "tariffTerms";
	private static final String FORM_NAME = "AccountOpeningClaim";
	private static final Long DEPOSIT_TYPE = 61L;
	private static final Long DEPOSIT_SUB_TYPE = 1L;
	private static final Long DEPOSIT_GROUP = 0L;
	private static final String DEPOSIT_CURRENCY = "RUB";

	private static final String TEMPLATE_ID_PATH = "/product/data/options/element[id=%d]/templateId";
	private static final String TARIFF_TEMPLATE_ID_PATH = "/product/data/options/element[id=%d and tariffPlanCode=%d]/tariffPlanAgreementId";

	private static final String TEMPLATE_TEXT_PATH = "/product/data/templates/template[templateId='%d']/text";
	private static final String TARIFF_TEMPLATE_TEXT_PATH = "/product/data/collateralAgreements/collateralAgreementWithTariffPlan4Type_61/collateralAgreement[agreementId='%d']/text";

	private static final String TARIFF_TEMPLATE_PATH = "/product/data/options/element[tariffPlanCode='%s']";
	private static final String TEMPLATE_FIELD_REGEXP = "(\\{[^\\{^\\}]*\\})";
	private static final String CLOSE_BODY_REGEXP = "(?i)</body>";
	private static final String REMOVE_IMGS_SCRIPT = "<script type=\"text/javascript\">" +
			"var imgs = document.getElementsByTagName('img');" +
			"for(var i=0;i<imgs.length;i++)" +
			"{imgs[i].parentNode.removeChild(imgs[i]);}" +
			"</script></body>";
	
	private static final AccountTargetService targetService = new AccountTargetService();
	private static final DbDocumentTarget documentTarget = new DbDocumentTarget();
	private static final DepositProductService depositProductService = new DepositProductService();
	private static final BusinessDocumentService documentService = new BusinessDocumentService();
	private final PaymentStateMachineService stateMachineService = new PaymentStateMachineService();
	private Map<String, Object> formFieldsValues = new HashMap<String, Object>();
	private AccountTarget target;
	private byte[] imageData;
	private String fileType;

	/**
	 * Инициализация операции
	 * @param targetType - тип цели
	 */
	public void initialize(TargetType targetType) throws BusinessException
	{
		super.initialize();
		Login login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
		target = new AccountTarget(login.getId(), targetType);
	}

	/**
	 * @param targetType тип цели
	 * @param imageData картинка
	 * @param fileType тип файла
	 * @throws BusinessException
	 */
	@SuppressWarnings("AssignmentToCollectionOrArrayFieldFromParameter")
	public void initialize(TargetType targetType, byte[] imageData, String fileType) throws BusinessException
	{
		super.initialize();
		Login login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
		target = new AccountTarget(login.getId(), targetType);
		this.imageData = imageData;
		this.fileType = fileType;
	}
	/**
	 * Инициализация операции
	 * @param id - идентификатор цели
	 * @throws BusinessException
	 */
	public void initializeById(Long id) throws BusinessException
	{
		super.initialize();
		target = targetService.findTargetById(id);

		if (target == null)
			throw new ResourceNotFoundBusinessException("Цель с id=" + id + " не найдена.", AccountTarget.class);

		Login login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
		if (!login.getId().equals(target.getLoginId()))
			throw new AccessException("Клиент с id = " + login.getId() + " не имеет доступа к цели с id = " + target.getId());
	}

	/**
	 * Сохраняет созданную клиентом цель и создает заявку на открытие вклада
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void save() throws BusinessException, BusinessLogicException
	{
		AccountOpeningClaim accountOpeningClaim = (AccountOpeningClaim) initializeDocument();
		saveDocument(accountOpeningClaim);

		target.setClaimId(accountOpeningClaim.getId());
		target.setAccountNum(null);
		target.setAccountLink(null);
		if (fileType != null && imageData.length>0)
		{
			NotLoadedImage img = upload();
			ImageContainer info = UserImageService.get().createImage(img,fileType);
			UserImageService.get().saveImage(info, null,false);
			if (target.getUserImage() != null)
			{
				ImageInfo imgInfo = UserImageService.get().getImageInfo(target.getUserImage());
				if (imgInfo!= null)
					UserImageService.get().deleteImageInfo(imgInfo, null, false);
			}
			target.setUserImage(info.getImageInfo().getId());
		}
		targetService.addOrUpdate(target);
	}

	public AccountTarget getEntity()
	{
		return target;
	}

	private BusinessDocument initializeDocument() throws BusinessException, BusinessLogicException
	{
		DepositProductShortCut product = depositProductService.findShortByProductId(DEPOSIT_TYPE);

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("form", FORM_NAME);
		map.put("fromResource","");
		map.put("depositType", DEPOSIT_TYPE);
		map.put("depositSubType", DEPOSIT_SUB_TYPE);
		map.put("depositGroup", DEPOSIT_GROUP);
		map.put("depositId", product.getId());
		map.put("depositName", product.getName());
		map.put("buyAmount","0.00");
		map.put("exactAmount","destination-field-exact");
		map.put("toResourceCurrency", DEPOSIT_CURRENCY);
		map.put("openDate", Calendar.getInstance());
		map.put("ground", "Открытие вклада путем перевода");
		map.put("fromPersonalFinance", true);
		map.put("depositTariffPlanCode", haveTariffTemplate() ? PersonHelper.getActivePersonTarifPlanCode() : TariffPlanHelper.getUnknownTariffPlanCode());
		map.putAll(formFieldsValues);
		return createDocument(map);
	}

	private BusinessDocument createDocument(Map<String,Object> values) throws BusinessException, BusinessLogicException
	{
		FieldValuesSource valuesSource = new MapValuesSource(values);
		DocumentSource source = new NewDocumentSource(FORM_NAME, valuesSource, CreationType.internet, CreationSourceType.ordinary);
		return source.getDocument();
	}

	private void saveDocument(AccountOpeningClaim document) throws BusinessException, BusinessLogicException
	{
		document.setOffice(document.getDepartment());

		StateMachineExecutor executor = new StateMachineExecutor(stateMachineService.getStateMachineByFormName(document.getFormName()));
		StateMachineEvent stateMachineEvent = new StateMachineEvent();
		executor.setStateMachineEvent(stateMachineEvent);
		executor.initialize(document);
		executor.fireEvent(new ObjectEvent(DocumentEvent.SAVE, ObjectEvent.CLIENT_EVENT_TYPE));
		documentTarget.save(document);
	}

	/**
	 * Возвращает основной договор по вкладу или доп. соглашение к нему
	 * @param termsType - тип документа: Null-основной договор с условиями вклада
	 * @return html-код договора
	 * @throws BusinessException
	 */
	public String getHtmlByTermsType(String termsType) throws BusinessException
	{
		DepositProduct depositProduct = depositProductService.findByProductId(DEPOSIT_TYPE);
		try
		{
			Element depositDescription = XmlHelper.parse(depositProduct.getDescription()).getDocumentElement();

			String templatePath = null;
			if (StringHelper.equalsNullIgnore(termsType, TARIFF_TERMS))
			{
				Long tariffTemplateId = findTariffTemplateId(depositDescription, DEPOSIT_SUB_TYPE,
						Long.valueOf(PersonHelper.getActivePersonTarifPlanCode()), TARIFF_TEMPLATE_ID_PATH);
				templatePath = String.format(TARIFF_TEMPLATE_TEXT_PATH, tariffTemplateId);
			}
			else
			{
				Long templateId = findTemplateId(depositDescription, DEPOSIT_SUB_TYPE);
				templatePath =  String.format(TEMPLATE_TEXT_PATH, templateId);
			}

			String templateHtml = XmlHelper.getElementValueByPath(depositDescription, templatePath);

			Matcher matcher = Pattern.compile(TEMPLATE_FIELD_REGEXP).matcher(templateHtml);
			Map<String,String> templateReplacements = FixedDepositProductHelper.getTemplateValues(depositProduct);
			// Заменяем все вхождения типа {*} в тексте шаблона на значение из карты templateReplacements, либо на
			// пустое, если соответствующей подстановки нет.
			while (matcher.find())
			{
				templateHtml = matcher.replaceFirst(StringHelper.getEmptyIfNull(templateReplacements.get(matcher.group())));
				matcher.reset(templateHtml);
			}

			// Дописываем скрипт для скрытия рисунков в конец документа
			return templateHtml.replaceFirst(CLOSE_BODY_REGEXP, REMOVE_IMGS_SCRIPT);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private Long findTemplateId(Element description, Long accountSubType) throws BusinessException
	{
		try
		{
			String templateId = XmlHelper.getElementValueByPath(description, String.format(TEMPLATE_ID_PATH, accountSubType));
			return StringHelper.isEmpty(templateId) ? null : Long.parseLong(templateId);
		}
		catch (TransformerException e)
		{
			throw new BusinessException(e);
		}
	}

	private Long findTariffTemplateId(Element description, Long accountSubType, Long tariff, String templatePath) throws BusinessException
	{
		try
		{
			String templateId = XmlHelper.getElementValueByPath(description,
					String.format(templatePath, accountSubType, tariff));
			return StringHelper.isEmpty(templateId) ? null : Long.parseLong(templateId);
		}
		catch (TransformerException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Отвечает на вопрос у открываемого вклада есть процентные ставки для тарифного плана клиента или нет
	 * @return
	 */
	public boolean haveTariffTemplate() throws BusinessException
	{
		try
		{
			DepositProduct depositProduct = depositProductService.findByProductId(DEPOSIT_TYPE);
			Element depositDescription = XmlHelper.parse(depositProduct.getDescription()).getDocumentElement();

			String tariffPlanCode = PersonHelper.getActivePersonTarifPlanCode();
			String tariffTemplates = XmlHelper.getElementValueByPath(depositDescription, String.format(TARIFF_TEMPLATE_PATH, tariffPlanCode));

			return StringHelper.isNotEmpty(tariffTemplates);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}

	}

	/**
	 * Тарифный план открытого из цели вклада
	 * @return
	 * @throws BusinessException
	 */
	public String getDepositTarifPlanCode() throws BusinessException
	{
		AccountTarget accountTarget = getEntity();
		if (accountTarget == null)
			return null;
		BusinessDocument businessDocument = documentService.findById(accountTarget.getClaimId());
		AccountOpeningClaim claim = (AccountOpeningClaim) businessDocument;
		return claim.getDepositTariffPlanCode();
	}

	public void setFormFieldsValues(Map<String, Object> formFieldsValues)
	{
		this.formFieldsValues = formFieldsValues;
	}

	public AccountOpeningClaim findClaim() throws BusinessException
	{
		if (target.getClaimId() == null)
            return null;
		return (AccountOpeningClaim) documentService.findById(target.getClaimId());
	}

	/**
	 * @return загруженная картинка
	 * @throws BusinessException,BusinessLogicException
	 */
	private NotLoadedImage upload() throws BusinessException, BusinessLogicException
	{
		return UserImageService.get().upload(imageData);
	}
}