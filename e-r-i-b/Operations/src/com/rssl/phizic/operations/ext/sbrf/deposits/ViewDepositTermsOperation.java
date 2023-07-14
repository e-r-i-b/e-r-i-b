package com.rssl.phizic.operations.ext.sbrf.deposits;

import com.rssl.common.forms.xslt.XmlConverter;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.deposits.*;
import com.rssl.phizic.business.documents.AccountOpeningClaim;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.validators.IsOwnDocumentValidator;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.NumericUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.StaticResolver;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;

/**
 * ќпераци€ дл€ получени€ договора(условий) на открытие вклада
 * @author Pankin
 * @ created 03.06.2011
 * @ $Author$
 * @ $Revision$
 */

public class ViewDepositTermsOperation extends OperationBase
{
	private static final String TARIFF_TERMS = "tariffTerms";
	private static final String TEMPLATE_FIELD_REGEXP = "(\\{[^\\{^\\}]*\\})";
	private static final String CLOSE_BODY_REGEXP = "(?i)</body>";
	private static final String HTML_HEAD = "<head>";
	private static final String HTML_STYLE = "<head><style type='text/css'>body {font-size: 12px; font-family: Arial;} table {border-collapse: collapse; border: 1px solid #000000;font-size: 12px; font-family: Arial;}</style>";
	private static final String REMOVE_IMGS_SCRIPT = "<script type=\"text/javascript\">" +
			"var imgs = document.getElementsByTagName('img');" +
			"var accountOpenTextDiv = document.getElementById('accountOpenText');" +
			"if (accountOpenTextDiv != undefined)" +
			"{imgs = accountOpenTextDiv.getElementsByTagName('img');}" +
			"for(var i=0;i<imgs.length;i++)" +
			"{imgs[i].parentNode.removeChild(imgs[i]);}" +
			"</script></body>";

	private AccountOpeningClaim accountOpeningClaim;

	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();
	private static final DepositProductService depositProductService = new DepositProductService();
	private static final DepositTermsService depositTermsService = new DepositTermsService();

	private static final IsOwnDocumentValidator ownValidator = new IsOwnDocumentValidator();

	//	ѕродукт с типом 61 €вл€етс€ сберегательным счетом
	private static final long ACCOUNT_TYPE_SBEREGATELNYI = 61L;

	/**
	 * »нициализаци€ операции
	 * @param documentId - идентификатор за€вки
	 */
	public void initialize(Long documentId) throws BusinessException
	{
		BusinessDocument document = businessDocumentService.findById(documentId);
		//ѕроверка владельца документа кроме "ј–ћ сотрудника" 
		ApplicationConfig applicationConfig = ApplicationConfig.getIt();
		if (applicationConfig.getLoginContextApplication() != Application.PhizIA)
			ownValidator.validate(document);

		if (!(document instanceof AccountOpeningClaim))
			throw new BusinessException("Ќеверный тип документа. ќжидаетс€ AccountOpeningClaim.");

		accountOpeningClaim = (AccountOpeningClaim) document;
	}

	/**
	 * @param termsType тип печатаемого договора
	 * @param loadInDiv признак: загрузка условий в диве
	 * @return html-код страницы с услови€ми договора
	 */
	public String getHtml(String termsType, boolean loadInDiv) throws BusinessException
	{
		String templateHtml = null;

		if (StringHelper.equalsNullIgnore(termsType, TARIFF_TERMS))
			templateHtml = depositProductService.findTemplateByTemplateId(accountOpeningClaim.getTariffTermsTemplateId());
		else
			templateHtml = depositProductService.findTemplateByTemplateId(accountOpeningClaim.getTermsTemplateId());
		//если шаблон договора не нашли, отображаем пустую страницу
		if (templateHtml == null)
			return "";
		Matcher matcher = Pattern.compile(TEMPLATE_FIELD_REGEXP).matcher(templateHtml);

		Map<String, String> templateReplacements = depositTermsService.getTemplateReplacements(accountOpeningClaim);
		templateReplacements.put(TemplateConstants.LIST_TP_PROC, createListTpProc());

		// «амен€ем все вхождени€ типа {*} в тексте шаблона на значение из карты templateReplacements, либо на
		// пустое, если соответствующей подстановки нет.
		while (matcher.find())
		{
			templateHtml = matcher.replaceFirst(StringHelper.getEmptyIfNull(templateReplacements.get(matcher.group())));
			matcher.reset(templateHtml);
		}

		// ƒописываем скрипт дл€ скрыти€ рисунков в конец документа
		templateHtml = templateHtml.replaceFirst(CLOSE_BODY_REGEXP, REMOVE_IMGS_SCRIPT);
		// ≈сли услови€ загружаютс€ в див на странице - стиль не добавл€ем
		if (!loadInDiv)
			templateHtml = templateHtml.replaceFirst(HTML_HEAD, HTML_STYLE);
		return templateHtml;
	}

	/**
	 *
	 * @return таблица соответстви€ пороговых сумм и процентных ставок
	 */
	private String createListTpProc() throws BusinessException
	{
		Long accountType = accountOpeningClaim.getAccountType();

		if (!accountType.equals(ACCOUNT_TYPE_SBEREGATELNYI))
		{
			return org.apache.commons.lang.StringUtils.EMPTY;
		}

		DepositGlobal global = depositProductService.getGlobal();
		Source source = new StreamSource(new StringReader(global.getDepositPercentRateTransformation()));

		TransformerFactory factory = TransformerFactory.newInstance();
		factory.setURIResolver(new StaticResolver());

		try
		{
			Templates templates = factory.newTemplates(source);
			String tariffType = accountOpeningClaim.getTarifPlanCodeType();
			if (StringHelper.isEmpty(tariffType))
			{
				if (PersonContext.isAvailable())
				{
					tariffType = PersonContext.getPersonDataProvider().getPersonData().getPerson().getTarifPlanCodeType();
				}
				else
				{
					tariffType = "0";
				}
			}

			XmlConverter converter = new DepositHtmlConverter(templates);
			DepositProduct depositProduct = depositProductService.findByProductId(accountOpeningClaim.getAccountType());
			if (depositProduct == null)
				return org.apache.commons.lang.StringUtils.EMPTY;
			Document document = XmlHelper.parse(depositProduct.getDescription());

			converter.setData(new DOMSource(document));
			converter.setParameter("type", accountType);
			converter.setParameter("termsTemplateId", NumericUtil.getEmptyIfNull(accountOpeningClaim.getTariffTermsTemplateId()));
			converter.setParameter("subtype", accountOpeningClaim.getAccountSubType());
			converter.setParameter("currency", accountOpeningClaim.getCurrency().getCode());
			converter.setParameter("currentDate", new SimpleDateFormat("yyyy.MM.dd").format(Calendar.getInstance().getTime()));
			converter.setParameter("clientCategoryCode", StringHelper.getZeroIfEmptyOrNull(tariffType));

			return converter.convert().toString();
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
