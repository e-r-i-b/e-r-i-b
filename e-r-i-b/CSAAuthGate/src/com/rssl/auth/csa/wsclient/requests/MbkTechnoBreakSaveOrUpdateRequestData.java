package com.rssl.auth.csa.wsclient.requests;

import com.rssl.auth.csa.wsclient.RequestConstants;
import com.rssl.phizgate.ext.sbrf.technobreaks.TechnoBreak;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

/**
 * Запрос на добавление технологического перерыва МБК
 * Отправляются только нужные в ЦСА поля
 * @author Puzikov
 * @ created 07.11.14
 * @ $Author$
 * @ $Revision$
 */

public class MbkTechnoBreakSaveOrUpdateRequestData extends RequestDataBase
{
	private static final String REQUEST_DATA_NAME = "mbkTechnoBreakSaveOrUpdateRq";

	private final TechnoBreak technoBreak;

	/**
	 * ctor
	 * @param technoBreak технологический перерыв
	 */
	public MbkTechnoBreakSaveOrUpdateRequestData(TechnoBreak technoBreak)
	{
		this.technoBreak = technoBreak;
	}

	public String getName()
	{
		return REQUEST_DATA_NAME;
	}

	public Document getBody()
	{
		Document document = createRequest();
		XmlHelper.appendSimpleElement(document.getDocumentElement(), RequestConstants.TECHNO_BREAK_UUID, technoBreak.getUuid().toString());
		XmlHelper.appendSimpleElement(document.getDocumentElement(), RequestConstants.TECHNO_BREAK_FROM_DATE, XMLDatatypeHelper.formatDateTimeWithoutTimeZone(technoBreak.getFromDate()));
		XmlHelper.appendSimpleElement(document.getDocumentElement(), RequestConstants.TECHNO_BREAK_TO_DATE, XMLDatatypeHelper.formatDateTimeWithoutTimeZone(technoBreak.getToDate()));
		XmlHelper.appendSimpleElement(document.getDocumentElement(), RequestConstants.TECHNO_BREAK_MESSAGE, technoBreak.getMessage());
		XmlHelper.appendSimpleElement(document.getDocumentElement(), RequestConstants.TECHNO_BREAK_STATUS, technoBreak.getStatus().name());
		XmlHelper.appendSimpleElement(document.getDocumentElement(), RequestConstants.TECHNO_BREAK_PERIODIC, technoBreak.getPeriodic().name());
		XmlHelper.appendSimpleElement(document.getDocumentElement(), RequestConstants.TECHNO_BREAK_IS_AUTO, Boolean.toString(technoBreak.isAutoEnabled()));
		return document;
	}
}
