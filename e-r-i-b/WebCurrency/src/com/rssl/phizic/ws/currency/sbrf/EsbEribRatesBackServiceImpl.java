package com.rssl.phizic.ws.currency.sbrf;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.tariffPlan.TarifPlanConfigService;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanHelper;
import com.rssl.phizic.business.rates.event.CurrencyRateCacheEvent;
import com.rssl.phizic.common.types.*;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.events.EventSender;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyRateUpdateService;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.jmx.BusinessSettingsConfig;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.ValidateException;
import com.rssl.phizic.utils.XSDSchemeValidator;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.web.util.ExceptionLogHelper;
import org.apache.commons.collections.CollectionUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.TransformerException;
import javax.xml.validation.Schema;

/**
 * ���������� �������� �������� �� ���� �� ���������� ������ �����
 *
 * @author gladishev
 * @ created 29.10.13
 * @ $Author$
 * @ $Revision$
 */
public class EsbEribRatesBackServiceImpl implements EsbEribRatesBackService
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final String UNIQ_ERROR_MESSAGE = "������� ��������� �������� ������ � ������� ������� %s ��� ��������� ����� \"%s\"";

	/**
	 * ���� xml ������
	 */
	private static final String RESULT_FILE_NAME = "com/rssl/phizic/ws/currency/sbrf/CurListInqRs.xml";

	/**
	 * ����� ���������
	 */
	private static final String SCHEMA_FILE_NAME = "com/rssl/phizic/ws/currency/sbrf/rates.xsd";

	private static final Object SCHEMA_LOCKER = new Object();
	/**
	 * ����� ���������
	 */
	private static volatile Schema schema;

	/**
	 * ����� ��� �������� BigDecimal
	 */
	private static final int SCALE_RATE = 4;

	/**
	 * ����� ������ �� ������� �������
	 */
	private static final String INTERNAL_ERROR = "Internal error";

	/**
	 * ��� 99 ��������
	 */
	private static final String TB_99 = "99";

	/**
	 * ��� 38 ��������
	 */
	private static final String TB_38 = "38";

	/**
	 * ������ �������� - ��� � �������
	 */
	protected static final Long ALL_OK_STATUS = 0L;

	/**
	 * ������ �������� - ���� ������
	 */
	private static final Long ERROR_STATUS = -1L;

	private static final TarifPlanConfigService tarifPlanConfigService = new TarifPlanConfigService();

	private Schema getSchema()
	{
		Schema localSchema = schema;
		if (localSchema == null)
		{
			synchronized (SCHEMA_LOCKER)
			{
				if (schema == null)
				{
					try
					{
						schema = XmlHelper.schemaByFileName(SCHEMA_FILE_NAME);
					}
					catch (Exception e)
					{
						ExceptionLogHelper.writeLogMessage(e);
					}
				}
				localSchema = schema;
			}
		}
		return localSchema;
	}

	public java.lang.String doIFX(java.lang.String req) throws java.rmi.RemoteException
    {
	    validate(req);

	    EribRatesHandler handler = handleMessage(req);

	    Long statusCode;
	    try
	    {
		    String region = StringHelper.removeLeadingZeros(handler.getRegionId());
		    Department department = getTbOffice(region);//�� ����� ����� ���������� ��� ��

		    if (department == null)
		    {
		        statusCode = ERROR_STATUS;
			    ExceptionLogHelper.writeLogMessage(new BusinessException("����������� ���� RegionId = " +
			        handler.getRegionId() + " BranchId = " + handler.getBranchId() +
			        " AgencyId = " + handler.getAgencyId()));
		    }
		    else
		    {
			    TariffPlan tarifPlanCodeType = getTarifPlanCodeType(handler);

			    Map<String, Object> paramsMap = new HashMap<String, Object>();
			    paramsMap.put("orderNumber", handler.getOrderNum());
			    paramsMap.put("departmentId", department.getId());
			    paramsMap.put("year", handler.getOrderDate().get(Calendar.YEAR));
			    paramsMap.put("tarifPlanCodeType", TariffPlanHelper.getCodeBySynonym(tarifPlanCodeType.getCode()));

			    if (rateExistsByOrderNumber(paramsMap))
			    {
				    ExceptionLogHelper.writeLogMessage(new BusinessException(String.format(UNIQ_ERROR_MESSAGE, handler.getOrderNum(), tarifPlanCodeType.getName())));
				    return makeResult(ALL_OK_STATUS, handler);
			    }

			    List<Pair<CurrencyRate, BigDecimal>> listRate = makeListRate(handler);

			    if (listRate.size() > 0)
			    {
					updateCurrencyRate(handler, department, listRate);

				    if (TB_99.equals(region))
				    {
					    department = getTbOffice(TB_38);//�� ����� ����� ���������� ��� ��

					    if (department == null)
					    {
							ExceptionLogHelper.writeLogMessage(
								new BusinessException("����������� ���� RegionId = " + TB_38));
					    }
					    else
					    {
							updateCurrencyRate(handler, department, listRate);
					    }
				    }
			    }
				statusCode = ALL_OK_STATUS;

		    }
	    }
	    catch (Exception e)
	    {
		    ExceptionLogHelper.writeLogMessage(e);
		    statusCode = ERROR_STATUS;
	    }

	    return makeResult(statusCode, handler);
    }

	/**
	 * �������� ���� ��� �������� ������ �����. �� ������������ � ������ �� ���� ��� ����� �������� ��� ������ ��������� �����.
	 * @param handler
	 * @return
	 */
	private TariffPlan getTarifPlanCodeType(EribRatesHandler handler) throws BusinessException
	{
		if (handler == null || CollectionUtils.isEmpty(handler.getRateList()))
			return TariffPlanHelper.getUnknownTariffPlan();

		List<Map<String, String>> rateList = handler.getRateList();
		Map<String, String> rateMap = rateList.get(0);
		String tarifPlanCodeType = rateMap.get(EribRatesHandler.RATE_TARIF_PLAN);
		if (StringHelper.isEmpty(tarifPlanCodeType))
			return TariffPlanHelper.getUnknownTariffPlan();
		return tarifPlanConfigService.getTarifPlanConfigByTarifPlanCodeType(tarifPlanCodeType);
	}

	/**
	 * ����� ������ ���������� ������ �����
	 * @param handler - ���������� ���������
	 * @param office - ����
	 * @param listRate - ������ ������ �����
	 * @throws java.rmi.RemoteException
	 */
	private void updateCurrencyRate(EribRatesHandler handler, Office office, List<Pair<CurrencyRate, BigDecimal>> listRate) throws Exception
	{
		CurrencyRateUpdateService currencyUpdateService = GateSingleton.getFactory().service(CurrencyRateUpdateService.class);

		try
		{
			currencyUpdateService.updateRate(listRate, office, handler.getOrderNum(), handler.getOrderDate(), handler.getStartDate());
			BusinessSettingsConfig businessSettingsConfig = ConfigFactory.getConfig(BusinessSettingsConfig.class);
			if (businessSettingsConfig.getClearRateCacheUseJMS())
				clearRatesCache(listRate, office, handler.getStartDate());
		}
		catch (GateException e)
		{
			throw new RemoteException(INTERNAL_ERROR, e);
		}
	}

	//��������� ������� ������� ���� ������ �����
	private void clearRatesCache(List<Pair<CurrencyRate, BigDecimal>> rates, Office office, Calendar startDate)
	{
		try
		{
			EventSender.getInstance().sendEvent(new CurrencyRateCacheEvent(rates, office));
		}
		catch (Exception e)
		{
			log.error("������ ��� �������� ������� ������� ���� ������ �����.", e);
		}
	}

	/**
	 * �������� �� ������������� ������ �� ����������
	 * @param paramsMap - ���� � ����������� ��� �������
	 * @return - true - ����������, false - ���
	 * @throws GateException
	 */
	private boolean rateExistsByOrderNumber(Map<String, Object> paramsMap) throws Exception
	{
		CurrencyRateUpdateService currencyUpdateService = GateSingleton.getFactory().service(CurrencyRateUpdateService.class);
		try
		{
			return currencyUpdateService.rateExistByOrderNumber(paramsMap);
		}
		catch (GateException e)
		{
			throw new RemoteException(INTERNAL_ERROR, e);
		}
	}

	/**
	 * ������������ ������ ������ �����, ������ �������� � �����-������
	 * @param handler - ���������� ���������
	 * @return - ������ ������ �����
	 * @throws RemoteException
	 */
	private List<Pair<CurrencyRate, BigDecimal>> makeListRate(EribRatesHandler handler) throws Exception
	{
		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);

		List<Pair<CurrencyRate, BigDecimal>> listRate = new ArrayList<Pair<CurrencyRate, BigDecimal>>(handler.getRateList().size());

		Currency nationalCurrency = currencyService.getNationalCurrency();
		if (nationalCurrency == null)
		{
			ExceptionLogHelper.writeLogMessage(new BusinessException("���������� ���������� ������������ ������"));

			return listRate;
		}

		for (Map<String, String> rate : handler.getRateList())
		{
			String fromCurrencyCode = rate.get(EribRatesHandler.CURRENCY_CODE_TAG_NAME);
			Currency fromCurrency = currencyService.findByAlphabeticCode(fromCurrencyCode);

			if (fromCurrency == null)
			{
				ExceptionLogHelper.writeLogMessage(new BusinessException("���������� ���������� ������ � ����� " + fromCurrencyCode));
				continue;
			}

			//���� ������ �����������, �� ��� �����-����
			//����� - ��� ���� ������ � ����� ��� ���� ������� � �����
			String toCurrencyCode = rate.get(EribRatesHandler.CURRENCY_CODE_2_TAG_NAME);
			boolean isCrossRate = !StringHelper.isEmpty(toCurrencyCode);
			Currency toCurrency = isCrossRate ? currencyService.findByAlphabeticCode(toCurrencyCode) : nationalCurrency;

			if (isCrossRate && toCurrency == null)
			{
				ExceptionLogHelper.writeLogMessage(new BusinessException("���������� ���������� ������ � ����� " + toCurrencyCode));
				continue;
			}

			BigDecimal quotient = createBigDecimal(rate.get(EribRatesHandler.QUOTIENT_TAG_NAME));

			//������/�������� ����. ���� ���� ���, �� �������, ��� ��� ������ ����.
			Boolean isStraight = StringHelper.isEmpty(rate.get(EribRatesHandler.STRAIGHT_TAG_NAME)) ? true : Boolean.valueOf(rate.get(EribRatesHandler.STRAIGHT_TAG_NAME));
			if (!isStraight) {
				Currency temp = fromCurrency;
				fromCurrency = toCurrency;
				toCurrency = temp;
			}

			addCurrencyRate(fromCurrency, quotient, toCurrency, rate.get(EribRatesHandler.RATE_CB_TAG_NAME),
				CurrencyRateType.CB, rate.get(EribRatesHandler.RATE_CB_DELTA_TAG_NAME), listRate, rate.get(EribRatesHandler.RATE_TARIF_PLAN));

			addCurrencyRate(fromCurrency, quotient, toCurrency,	rate.get(EribRatesHandler.RATE_BUY_TAG_NAME),
				CurrencyRateType.BUY, rate.get(EribRatesHandler.RATE_BUY_DELTA_TAG_NAME), listRate, rate.get(EribRatesHandler.RATE_TARIF_PLAN));

			addCurrencyRate(fromCurrency, quotient, toCurrency, rate.get(EribRatesHandler.RATE_SALE_TAG_NAME),
				CurrencyRateType.SALE, rate.get(EribRatesHandler.RATE_SALE_DELTA_TAG_NAME), listRate, rate.get(EribRatesHandler.RATE_TARIF_PLAN));

			addCurrencyRate(fromCurrency, quotient, toCurrency, rate.get(EribRatesHandler.RATE_REMOTE_BUY_TAG_NAME),
				CurrencyRateType.BUY_REMOTE, rate.get(EribRatesHandler.RATE_REMOTE_BUY_DELTA_TAG_NAME), listRate, rate.get(EribRatesHandler.RATE_TARIF_PLAN));

			addCurrencyRate(fromCurrency, quotient, toCurrency, rate.get(EribRatesHandler.RATE_REMOTE_SALE_TAG_NAME),
				CurrencyRateType.SALE_REMOTE, rate.get(EribRatesHandler.RATE_REMOTE_SALE_DELTA_TAG_NAME), listRate, rate.get(EribRatesHandler.RATE_TARIF_PLAN));
		}

		return listRate;
	}

	/**
	 * ������� ������ CurrencyRate � ��������� ��� � ���������
	 * @param fromCurrency - �� ����� ������
	 * @param fromValue - �� ������ ��������
	 * @param toCurrency - � ����� ������
	 * @param toValue - � ����� ��������
	 * @param rateType - ��� ����� (�������, �������...)
	 * @param listRate - ��������� ������
	 */
	private void addCurrencyRate(Currency fromCurrency, BigDecimal fromValue, Currency toCurrency,
	                             String toValue, CurrencyRateType rateType, String dynamicValue,
	                             List<Pair<CurrencyRate, BigDecimal>> listRate, String tarifPlanCodeType)
	{
		if (!StringHelper.isEmpty(toValue))
		{
			BigDecimal dynamicValueBD = StringHelper.isEmpty(dynamicValue) ? null : createBigDecimal(dynamicValue);
			Pair<CurrencyRate, BigDecimal> pair = new Pair<CurrencyRate, BigDecimal>(
					new CurrencyRate(fromCurrency, fromValue, toCurrency, createBigDecimal(toValue), rateType,
							//�������� �������� �������� ��������� �����
							(StringHelper.isEmpty(tarifPlanCodeType) ? "0" :
									tarifPlanCodeType)),
					dynamicValueBD
			);
			listRate.add(pair);
		}
	}

	/**
	 * �� ������ ������� ����
	 * @param text - ������
	 * @return - ����
	 */
	private BigDecimal createBigDecimal(String text)
	{
		return new BigDecimal(text).setScale(SCALE_RATE, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * ���������� ���������
	 * @param req - ������ ���������
	 * @return - ���������� ���������
	 * @throws RemoteException
	 */
	protected EribRatesHandler handleMessage(String req) throws RemoteException
	{
		EribRatesHandler handler = new EribRatesHandler();

		try
		{
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			saxParser.parse(new InputSource(new StringReader(req)), handler);
		}
		catch (Exception e)
		{
			Exception exception = new Exception("������ ��� ������� ��������� ������ �����: " +	req);
			ExceptionLogHelper.writeLogMessage(exception);

			throw new RemoteException(INTERNAL_ERROR);
		}

		return handler;
	}

	/**
	 * �������� ������� �� �������
	 * @param regionId - ��� ��������
	 * @return - �������
	 * @throws RemoteException
	 */
	private Department getTbOffice(String regionId) throws BusinessException
	{
		return new DepartmentService().getDepartmentTBByTBNumber(regionId);
	}

	/**
	 * ��������� ���������
	 * @param xmlString - ���������
	 * @throws java.rmi.RemoteException
	 */
	private void validate(String xmlString) throws java.rmi.RemoteException
	{
		try
		{
			XSDSchemeValidator.validate(getSchema(), xmlString);
		}
		catch (ValidateException e)
		{
			ExceptionLogHelper.writeLogMessage(e);
			throw new RemoteException(INTERNAL_ERROR);
		}
	}

	/**
	 * ������������ ������
	 * @param statusCode - ������ ����������
	 * @param handler - SAX ����������
	 * @return - ������ XML
	 * @throws java.rmi.RemoteException
	 */
	protected String makeResult(Long statusCode, EribRatesHandler handler) throws java.rmi.RemoteException
	{
		Document document;

		try
		{
			document = XmlHelper.loadDocumentFromResource(RESULT_FILE_NAME);

			findAndSetElementText(document, "RqUID", new RandomGUID().getStringValue());

			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			findAndSetElementText(document, "RqTm", simpleDateFormat.format(Calendar.getInstance().getTime()));

			findAndSetElementText(document, "OperUID", handler.getOperationId());
			findAndSetElementText(document, "ReqUIDRep", handler.getRequestId());
			findAndSetElementText(document, "Status/StatusCode", statusCode.toString());

			if (!statusCode.equals(ALL_OK_STATUS))
			{
				findAndSetElementText(document, "Status/StatusDesc", "Error");
			}

		}
		catch (Exception e)
		{
			ExceptionLogHelper.writeLogMessage(e);
			throw new java.rmi.RemoteException(INTERNAL_ERROR);
		}

		String result;

		try
		{
			result = XmlHelper.convertDomToText(document);
		}
		catch (TransformerException e)
		{
			ExceptionLogHelper.writeLogMessage(e);
			throw new java.rmi.RemoteException(INTERNAL_ERROR);
		}

		validate(result);

		return result;
	}

	/**
	 * ����� � ���������� ������ ���� � ���������
	 * @param document - ��������
	 * @param elementName - ��� ����
	 * @param text - �����
	 * @throws Exception
	 */
	private void findAndSetElementText(Document document, String elementName, String text) throws Exception
	{
		Element element = XmlHelper.selectSingleNode(document.getDocumentElement(), elementName);

		if (element != null)
			element.setTextContent(text);
	}
}
