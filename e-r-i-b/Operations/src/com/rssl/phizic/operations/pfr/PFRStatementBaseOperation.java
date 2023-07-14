package com.rssl.phizic.operations.pfr;

import com.rssl.common.forms.xslt.XmlConverter;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.exceptions.NotOwnDocumentException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.validators.IsOwnDocumentValidator;
import com.rssl.phizic.business.payments.forms.meta.XSLTBean;
import com.rssl.phizic.business.payments.forms.meta.XSLTService;
import com.rssl.phizic.business.pfr.PFRStatement;
import com.rssl.phizic.business.pfr.PFRStatementService;
import com.rssl.phizic.business.xml.CommonXmlConverter;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.utils.DocumentConfig;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.StaticResolver;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.lang.BooleanUtils;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;

/**
 * @author gulov
 * @ created 11.03.2011
 * @ $Authors$
 * @ $Revision$
 */
public class PFRStatementBaseOperation extends OperationBase implements ViewEntityOperation
{
	/**
	 * Параметр для xslt
	 */
	private static final String PARAMETER_NAME = "mode";

	/**
	 * Режим просмотра
	 */
	public static final String VIEW_MODE = "view";

	/**
	 * Режим печати
	 */
	public static final String PRINT_MODE = "print";

	protected static final BusinessDocumentService documentService = new BusinessDocumentService();
	protected static final PFRStatementService statementService = new PFRStatementService();
	protected static final XSLTService xsltService = new XSLTService();

	private PFRStatement statement;

	public PFRStatement getEntity()
	{
		return statement;
	}

	/**
	 * Инициализация - поиск выписки по id заявки
	 * @param claimId id заявки
	 */
	public void initialize(Long claimId) throws BusinessException
	{
		BusinessDocument claim = documentService.findById(claimId);
		if(claim == null)
			throw new BusinessException("Не найдена заявка с id = " + claimId);

		try
		{
			new IsOwnDocumentValidator().validate(claim);
		}
		catch(NotOwnDocumentException e)
		{
			throw new AccessException("У данного пользователя нет прав на просмотр выписки из ПФР с claimId="+claimId);
		}

		statement = statementService.findByClaimId(claimId);

		if(statement == null)
			throw new BusinessException("Не найдена выписка с claimId = " + claimId);

		// Признак отображения невалидной выписки
		String showStatement = ConfigFactory.getConfig(DocumentConfig.class).getPfrShowStatement();
		if (StringHelper.isEmpty(showStatement))
			throw new BusinessException("В файле iccs.properties отсутствует свойство " + DocumentConfig.SHOW_STATEMENT_PROPERTY);
		// Проверка на валидность выписки или невалидность и признак отображения = true
		if (!(statement.isValid() || BooleanUtils.toBoolean(showStatement)))
			throw new BusinessException("Xml-документ выписки не валиден. id выписки = " + statement.getId());
	}

	/**
	 * Выполняет xslt-преобразование выписки из xml в html
	 * @return html-представление выписки
	 * @throws BusinessException
	 */
	public String getHtml(String value) throws BusinessException
	{
		XmlConverter converter = new CommonXmlConverter(getTemplate());

		converter.setData(getData(statement.getStatementXml()));
		converter.setParameter(PARAMETER_NAME, value);

		return converter.convert().toString();
	}

	protected String getTemplateName()
	{
		return XSLTService.PFR_STATEMENT_NAME;
	}

	private Templates getTemplate() throws BusinessException
	{
		try
		{
			XSLTBean xsltBean = xsltService.getBeanByName(getTemplateName());
			if (xsltBean == null)
				throw new BusinessException("В таблице нет xslt шаблона для преобразования выписки из ПФР.");
			InputStream inputStream = new ByteArrayInputStream(xsltBean.getXsltTemplate().getBytes("UTF-8"));
			Source templateSource = new StreamSource(inputStream);
			TransformerFactory fact = TransformerFactory.newInstance();
			fact.setURIResolver(new StaticResolver());
			return fact.newTemplates(templateSource);
		}
		catch (UnsupportedEncodingException e)
		{
			throw new BusinessException("Не удалось сконвертировать xslt-шаблон.", e);
		}
		catch(TransformerConfigurationException e)
		{
			throw new BusinessException("Не удалось создать xslt-шаблон.", e);
		}
	}

	private Source getData(String statementXml) throws BusinessException
	{
		try
		{
			return new DOMSource(XmlHelper.parse(statementXml));
		}
		catch(ParserConfigurationException e)
		{
			throw new BusinessException("Невозможно сконфигурировать парсер.", e);
		}
		catch(SAXException e)
		{
			throw new BusinessException("Xml файл неизвестного формата.", e);
		}
		catch(IOException e)
		{
			throw new BusinessException("Xml не найден.", e);
		}
	}
}
