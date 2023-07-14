package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.phizgate.ext.sbrf.technobreaks.PeriodicType;
import com.rssl.phizgate.ext.sbrf.technobreaks.TechnoBreak;
import com.rssl.phizgate.ext.sbrf.technobreaks.TechnoBreakStatus;
import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.common.types.UUID;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.hibernate.Query;
import org.hibernate.Session;
import org.w3c.dom.Element;

import java.text.ParseException;

/**
 * Обработчик запросов на выставление/снятие тех. перерывов МБК
 * Параметры запроса:
 *      technoBreakUUID         uuid тех. перерыва                                          [1]
 *      technoBreakFromDate     начало тех. перерыва                                        [1]
 *      technoBreakToDate       конец тех. перерыва                                         [1]
 *      technoBreakMessage      сообщение пользователю                                      [1]
 *      technoBreakStatus       состояние (включен/удален)                                  [1]
 *      technoBreakPeriodic     периодичность                                               [1]
 *
 * @author Puzikov
 * @ created 07.11.14
 * @ $Author$
 * @ $Revision$
 */

public class MbkTechnoBreakSaveOrUpdateProcessor extends RequestProcessorBase
{
	private static final String RESPONSE_TYPE = "mbkTechnoBreakSaveOrUpdateRs";
	private static final String REQUEST_TYPE = "mbkTechnoBreakSaveOrUpdateRq";

	@Override
	protected String getResponceType()
	{
		return RESPONSE_TYPE;
	}

	@Override
	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	@Override
	protected ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		Element document = requestInfo.getBody().getDocumentElement();

		TechnoBreak technoBreak = getTechnoBreak(document);
		fillTechnoBreak(technoBreak, document);
		saveOrUpdateTechnoBreak(technoBreak);

		return buildSuccessResponse();
	}

	private TechnoBreak getTechnoBreak(Element element) throws Exception
	{
		final UUID uuid = UUID.fromValue(XmlHelper.getSimpleElementValue(element, Constants.TECHNO_BREAK_UUID));
		return HibernateExecutor.getInstance().execute(new HibernateAction<TechnoBreak>()
		{
			public TechnoBreak run(Session session) throws Exception
			{
				//noinspection JpaQueryApiInspection
				Query query = session.getNamedQuery("com.rssl.phizgate.ext.sbrf.technobreaks.TechnoBreak.findByUUID")
						.setParameter("uuid", uuid);
				TechnoBreak technoBreak = (TechnoBreak) query.uniqueResult();
				return technoBreak != null ? technoBreak : new TechnoBreak();
			}
		});
	}

	private void fillTechnoBreak(TechnoBreak technoBreak, Element element) throws ParseException
	{
		technoBreak.setUuid(UUID.fromValue(XmlHelper.getSimpleElementValue(element, Constants.TECHNO_BREAK_UUID)));
		technoBreak.setFromDate(XMLDatatypeHelper.parseDateTime(XmlHelper.getSimpleElementValue(element, Constants.TECHNO_BREAK_FROM_DATE)));
		technoBreak.setToDate(XMLDatatypeHelper.parseDateTime(XmlHelper.getSimpleElementValue(element, Constants.TECHNO_BREAK_TO_DATE)));
		technoBreak.setMessage(XmlHelper.getSimpleElementValue(element, Constants.TECHNO_BREAK_MESSAGE));
		technoBreak.setStatus(TechnoBreakStatus.valueOf(XmlHelper.getSimpleElementValue(element, Constants.TECHNO_BREAK_STATUS)));
		technoBreak.setPeriodic(PeriodicType.valueOf(XmlHelper.getSimpleElementValue(element, Constants.TECHNO_BREAK_PERIODIC)));
		technoBreak.setAutoEnabled(Boolean.parseBoolean(XmlHelper.getSimpleElementValue(element, Constants.TECHNO_BREAK_IS_AUTO)));

		//значения по умолчанию для адаптера МБК
		technoBreak.setAdapterUUID(ExternalSystemHelper.getMbkSystemCode());
		technoBreak.setDefaultMessage(false);
		technoBreak.setAllowOfflinePayments(true);
	}

	private void saveOrUpdateTechnoBreak(final TechnoBreak technoBreak) throws Exception
	{
		HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
		{
			public Void run(Session session) throws Exception
			{
				session.saveOrUpdate(technoBreak);
				return null;
			}
		});
		CacheProvider.getCache("techno-breaks-cache").remove(technoBreak.getAdapterUUID());
	}
}
