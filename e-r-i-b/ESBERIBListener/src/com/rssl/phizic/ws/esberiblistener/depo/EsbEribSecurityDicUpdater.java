package com.rssl.phizic.ws.esberiblistener.depo;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.security.Security;
import com.rssl.phizic.business.dictionaries.security.SecurityService;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.NotRoundedMoney;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.StringUtils;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.math.BigDecimal;
import java.util.GregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.TransformerException;

/**
 * @author lukina
 * @ created 22.11.2010
 * @ $Author$
 * @ $Revision$
 */

public class EsbEribSecurityDicUpdater
{
	private static final Long STATUS_CODE = 0L;
	private static final Long ERROR_STATUS_CODE = -1L;
    private static final int MESSAGE_LENGTH = 255;

	protected Document request;

	public EsbEribSecurityDicUpdater(Document request)
	{
		this.request = request;
	}

	public Document updateSecurityDictionary() throws TransformerException, GateException, GateLogicException
	{
		Document response = createResponseDocument(request);

		Element documentElement = request.getDocumentElement();
		NodeList nodes = XmlHelper.selectNodeList(documentElement,"DictRec");
		String errorDesc="";
		for (int i=0; i < nodes.getLength(); i++)
		{
			Element element = (Element) nodes.item(i);
			Security security = new Security();
			security.setIssuer(XmlHelper.getSimpleElementValue(element, "Issuer"));
			security.setName(XmlHelper.getSimpleElementValue(element, "SecurityName"));
			security.setRegistrationNumber(XmlHelper.getSimpleElementValue(element, "SecurityNumber"));
			security.setType(XmlHelper.getSimpleElementValue(element, "SecurityType"));

			CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
			Currency cur = currencyService.findByAlphabeticCode(XmlHelper.getSimpleElementValue(element, "SecurityNominalCur"));
			String securityNominal = XmlHelper.getSimpleElementValue(element, "SecurityNominal");
			if (cur != null &&  !StringHelper.isEmpty(securityNominal))
				security.setNominal(new NotRoundedMoney(new BigDecimal(securityNominal), cur));
			else
				security.setNominal(null);

			security.setInsideCode(XmlHelper.getSimpleElementValue(element, "InsideCode"));
			boolean isDelete = XmlHelper.getSimpleElementValue(element, "IsDelete").equals("true");
			security.setIsDelete(isDelete);
			errorDesc = updateSecurity(security);
			if (!StringHelper.isEmpty(errorDesc))
				break;
		}
		appendDocumentBlock(response,errorDesc);
		return response;
	}

	protected void appendDocumentBlock(Document document, String statusDesc)
	{
		Element responseRoot = document.getDocumentElement();
		Element status = XmlHelper.appendSimpleElement(responseRoot,"Status");
		if(StringHelper.isEmpty(statusDesc))
			XmlHelper.appendSimpleElement(status,"StatusCode",STATUS_CODE.toString());
		else
		{
			XmlHelper.appendSimpleElement(status,"StatusCode",ERROR_STATUS_CODE.toString());
			XmlHelper.appendSimpleElement(status,"StatusDesc", StringUtils.safeTrunc(statusDesc, MESSAGE_LENGTH));
		}
	}

	/**
	 * ќбновить запись в справочнике ценных бумаг
	 * @param security  - ценна€ бумага
	 * @return null в случае удачного обновлени€, иначе текст ошибки
	 */
	private String updateSecurity(Security security)
	{
		SecurityService updateSecurityService = new SecurityService();
		try
		{
			updateSecurityService.update(security);
		}
		catch (BusinessException e)
		{
			return e.getMessage();
		}
		return null;
	}

	/**
	 * —оздать ответное сообщение
	 * @param request - запрос
	 * @return response
	 */
	protected Document createResponseDocument(Document request)
	{
		Element requestRoot = request.getDocumentElement();

		DocumentBuilder builder = XmlHelper.getDocumentBuilder();
		Document response = builder.newDocument();
		Element root = response.createElement("SecDicInfoRs");
		response.appendChild(root);
		XmlHelper.appendSimpleElement(root,"RqUID",XmlHelper.getSimpleElementValue(requestRoot, "RqUID"));
		XmlHelper.appendSimpleElement(root,"RqTm", XMLDatatypeHelper.formatDateTimeWithoutTimeZone(new GregorianCalendar()));
		XmlHelper.appendSimpleElement(root,"OperUID",XmlHelper.getSimpleElementValue(requestRoot, "OperUID"));

		return response;
	}
}
