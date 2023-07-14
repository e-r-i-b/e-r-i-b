package com.rssl.phizic.business.dictionaries;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ext.sbrf.dictionaries.CASNSICardProduct;
import com.rssl.phizic.business.ext.sbrf.dictionaries.ProductUploadMode;
import com.rssl.phizic.business.ext.sbrf.dictionaries.SynchronizeSettingsUtil;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.MinMax;
import com.rssl.phizic.config.*;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.dictionaries.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.CardsConfig;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.lang.ArrayUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLFilter;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLFilterImpl;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author Mescheryakova
 * @ created 28.09.2011
 * @ $Author$
 * @ $Revision$
 */

public class CASNSICardProductReplicaSource extends XmlReplicaSourceBase
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private List<CASNSICardProduct> source = new ArrayList<CASNSICardProduct>();
	private SynchronizeResultImpl results;

	protected void clearData ()
	{
		source.clear();
		results = new SynchronizeResultImpl();
	}

	protected InputStream getDefaultStream()
	{
		return getResourceReader(com.rssl.phizic.business.Constants.DEFAULT_FILE_NAME);
	}

	protected XMLFilter getDefaultFilter () throws ParserConfigurationException, SAXException
	{
		return new SaxFilter(XmlHelper.newXMLReader());
	}

	public XMLReader chainXMLReader(XMLReader xmlReader)
	{
		return new SaxFilter(super.chainXMLReader(xmlReader));
	}

	public void initialize(GateFactory factory) throws GateException {}

	public Iterator iterator() throws GateException, GateLogicException
	{
		internalParse();

		return source.iterator();
	}

	public SynchronizeResult getSynchronizeResult()
	{
		return results;
	}

	private class SaxFilter extends XMLFilterImpl
	{
		private Boolean cardProductSection = false;
		private Boolean currencyCardProductSection = false;

		private Map<Long, Map<Long, TempCASNSICardProduct>> cardProductInfo = new HashMap<Long, Map<Long, TempCASNSICardProduct>>();
		private Map<String, Currency> currencyCache = new HashMap<String, Currency>();

		private Boolean currentCardProductKind = false;
		private Boolean currentCardProductSubKind = false;
		private Long subKind;
		private Long kind;
		private Map<Long, String[]> allowedCardProductKinds = new HashMap<Long, String[]>();

		private static final String CURRENCY_TABLE_NAME = "qvkl_val";
		private static final String DICTIONARY_NAME = "qvb";		

		private static final String FILE_CONFIG_NAME = "iccs.properties";

		public SaxFilter ( XMLReader parent )
		{
			super(parent);
		}

		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
		{
			try
			{
				fillCardProductKindValues();       // ��������� ��������� ����� ����
			}
			catch(BusinessException e)
			{
				throw new SAXException(e);
			}
			
			if(qName.equalsIgnoreCase("table") && DICTIONARY_NAME.equalsIgnoreCase(attributes.getValue("name")))
			{
				cardProductSection = true;   // ����� � ������� ��������� ���������
			}
			if(qName.equalsIgnoreCase("table") && CURRENCY_TABLE_NAME.equalsIgnoreCase(attributes.getValue("name")))
			{
				currencyCardProductSection = true;  // ����� � ������� ������������ ��������� ��������� � �����
			}
			// ������� ����� ��������� ���������
			else if (cardProductSection)
			{
				parseCardProducts(qName, attributes);
			}
			// ������� ����� ��������� ���������
			else if (currencyCardProductSection)
			{
				parseCurrencyInfo(qName, attributes);
			}

			if (!cardProductSection)
			{
				super.startElement(uri, localName, qName, attributes);
			}
		}

		public void endElement(String uri, String localName, String qName) throws SAXException
		{
			if (qName.equalsIgnoreCase("table"))
			{
				cardProductSection = false;
				currencyCardProductSection = false;
			}
			else if (qName.equalsIgnoreCase("package"))
			{
				for (Long kindKey : new ArrayList<Long>(cardProductInfo.keySet()))
				{
					for (Long subKindKey : new ArrayList<Long>(cardProductInfo.get(kindKey).keySet()))
					{

						TempCASNSICardProduct subKindValue = cardProductInfo.get(kindKey).get(subKindKey);
						// ���� �� �������� ����-�� �� �������, �� ������  ��� ������ ��� ������� ������� � ����� (������ ������)
						if (subKindValue != null && !StringHelper.isEmpty(subKindValue.getName()) && subKindValue.getDate() != null)
						{

							if (subKindValue.getCurrencies().isEmpty())
								saveResults(SynchronizeResultStatus.FAIL, "CUR_NO", kindKey + "-" + subKindKey, "��� ���������� ���� ����� ����������� ������ � ����������� ��� ���");
							else
							{
								for (Currency currency : subKindValue.getCurrencies())
								{
									CASNSICardProduct casnsiCardProduct = new CASNSICardProduct();
									casnsiCardProduct.setProductId(kindKey);
									casnsiCardProduct.setProductSubId(subKindKey);
									casnsiCardProduct.setName(subKindValue.getName());
									casnsiCardProduct.setStopOpenDeposit(subKindValue.getDate());
									casnsiCardProduct.setCurrency(currency);

									source.add(casnsiCardProduct);
									saveResults(SynchronizeResultStatus.SUCCESS, "����� � �����=" + casnsiCardProduct.getProductId() + " � ��������=" + casnsiCardProduct.getProductSubId() + " � ����� ������=" + casnsiCardProduct.getCurrency().getCode() + " ����������", casnsiCardProduct.getSynchKey(), "����� ����������");
								}
							}
						}
					}
				}
			}

			if (cardProductSection || currencyCardProductSection)
			{
				if (qName.equalsIgnoreCase("record"))
				{
					clearParams();
				}
			}
			else
			{
				super.endElement(uri, localName, qName);
			}
		}

		/**
		 * ��������� ���� ��������������� ������ ��� ������ �����
		 */
		private void clearParams()
		{
			currentCardProductKind = false;
			currentCardProductSubKind = false;
			kind = null;
			subKind = null;
		}

		/**
		 * �������� �� �������� ����������� � ������������ �������� ��������� ����� ��������� ���������
		 */
		private void fillCardProductKindValues() throws BusinessException
		{

			// ��������� ��� ���� �������
			if (!allowedCardProductKinds.isEmpty())
				return;

			// �������� ����� �������� ����: ������/��������
			String mode = readLoadMode();

			// ���� ����� ������, �� �������� �������� ���������� �������� �� ����������������� �����
			if (mode.equals(ProductUploadMode.ALL.name()))
			{
				allowedCardProductKinds.putAll(readConfigFileAllMode());
			}
			else
			{
				allowedCardProductKinds.putAll(readConfigFileOptionallyMode());
			}
		}

		/**
		 * ������� ����� ��������
		 */
		private String readLoadMode()
		{
			return ConfigFactory.getConfig(CardsConfig.class).getCardProductMode();
		}

		/**
		 * ������ �� ����. ����� ���������� � ��������� ���������� ����� ����������� ���� ��� ������� ������
		 * @return - ������ ����� - ���������� �������� ����� ����
		 * @throws BusinessException
		 */
		private Map<Long, String[]> readConfigFileAllMode() throws BusinessException
		{
			MinMax<Long> minMaxCardProductKinds = SynchronizeSettingsUtil.getMinMaxCardProductKindValues();
			Map<Long, String[]> results = new HashMap<Long, String[]>();

			// ����������� ���� ���� ��� �������� - ��� ��� ����� �� ������������ �� �������������
			for (Long i = minMaxCardProductKinds.getMin(); i <= minMaxCardProductKinds.getMax(); i++)
				results.put(i, null);

			return results;
		}

		/**
		 * ������ �� ����. ����� ���������� � ��������� ���������� ����� ����������� ���� ��� ���������� ������
		 * @return - ������ ����� - ���������� �������� ����� ���� � ��������
		 * @throws BusinessException
		 */
		private Map<Long, String[]> readConfigFileOptionallyMode() throws BusinessException
		{
			MinMax<Long> minMaxCardProductKinds = SynchronizeSettingsUtil.getMinMaxCardProductKindValues();
			// ������� �������� � ����. ����� ���� � ������� ����������� ����
			Map<Long, String[]> loadInfo = getKindAndSubkindLoadingCardProduct();

			// ����������� �� ���� ������ � ������� ��� ��������, �� ���������� ��������� ��������� ���������
			List<Long> keys = new ArrayList<Long>(loadInfo.keySet());
			for (Long key : keys)
			{
				if (key < minMaxCardProductKinds.getMin() || key > minMaxCardProductKinds.getMax())
					loadInfo.remove(key);
			}

			return loadInfo;
		}

		/**
		 * ���������� ���������, ���������� � ���� ������ ����������� � �������� ����� ����
		 * � ��������������� �� ��������
		 */
		private Map<Long, String[]> getKindAndSubkindLoadingCardProduct()
		{
			Map<Long, String[]> loadInfo = new HashMap<Long, String[]>();

			String unformatLoadCardProductInfo = ConfigFactory.getConfig(CardsConfig.class).getCardProductUsedKinds();

			// �������� ������ ��� � ���������������� ��� ���������
			String[] kindWithSubKinds = unformatLoadCardProductInfo.split(";");
			// ��������� �� ������ ������ ��� � ��������������� ��� �������
			for (int i = 0; i < kindWithSubKinds.length; i++)
			{
				String[] parseKindAndSubKinds = kindWithSubKinds[i].split("\\.");
				if (parseKindAndSubKinds != null && parseKindAndSubKinds.length > 0)
				{
					String[] subKinds = null;
					if (parseKindAndSubKinds.length >= 2)
					{
						subKinds = 	parseKindAndSubKinds[1].split(",");
					}

					loadInfo.put(Long.parseLong(parseKindAndSubKinds[0]), subKinds);
				}
			}
			return loadInfo;
		}

		/**
		 * ���������� ����������� � �����������
		 * @param resultStatus  - ������ ���������: ������ ��� ���
		 * @param message - ����� ������
		 * @param kind - ���� ����� (� ���������� ������� ������ ���-������, ����� ������ ��� �� ������� �������)
		 * @param logMessage - ��������� ��� �����
		 */
		private void saveResults(SynchronizeResultStatus resultStatus, String message, Comparable kind, String logMessage)
		{
			SynchronizeResultRecord record = new SynchronizeResultRecord();

			record.setStatus(resultStatus);
			List<String> errorDescriptions = new ArrayList<String>();
			errorDescriptions.add(message);
			record.setErrorDescriptions(errorDescriptions);
			record.setSynchKey(kind);

			if (SynchronizeResultStatus.FAIL.equals(resultStatus))
			{
				log.error(kind + ": " + logMessage);
			}
			else if (SynchronizeResultStatus.SUCCESS.equals(resultStatus))
			{
				log.info(kind + ": " + logMessage);
			}
			
			results.setDictionaryType(DictionaryType.CARD);
			results.addResultRecord(record);
		}

		/**
		 * ������� ����� ��������� ���������
		 * @param qName - �������� ����
		 * @param attributes - ��������� ����
		 * @throws SAXException - ������ ��������
		 */
		private void parseCardProducts(String qName, Attributes attributes) throws SAXException
		{
			if (qName.equalsIgnoreCase("field"))
			{
				String name  = attributes.getValue("name");
				String value = attributes.getValue("value");

				// ������� ������ � ���� ���������� ��������
				if ("QDTN1".equalsIgnoreCase(name))
				{
					parseQDTN1(value);
					return;
				}

				if (!currentCardProductKind) // ��� ��� �� ��� ���������, ������� ������ ��� ������
					return;

				// ������ ������ ���������� ��������
				if ("QDTSUB".equalsIgnoreCase(name))
				{
					parseQDTSUB(value);
					return;
				}

				if (!currentCardProductSubKind) // ������� ���, ������� ������ ��� ������
					return;

				// ������ �������� ���������� ��������
				if ("QDN".equalsIgnoreCase(name))
				{
					cardProductInfo.get(kind).get(subKind).setName(value);
					return;
				}

				// ������ ���� ����������� �������� �������
				if("QOPEND".equals(name))
				{
					parseQOPEND(value);
					return;
				}
			}
		}

		/**
		 * ������� ���� ���������� ��������
		 * @param value - �������� ����
		 */
		private void parseQDTN1(String value)
		{
			Long qdtn1 = Long.parseLong(value);
			List<Long> allowedKinds = new ArrayList<Long>(allowedCardProductKinds.keySet());
			if (!allowedKinds.contains(qdtn1))
				return;
			
			kind =  qdtn1;
			currentCardProductKind = true;
		}

		/**
		 * ������� ������� ���������� ��������
		 * @param value - �������� �������
		 */
		private void parseQDTSUB(String value)
		{
			String[] subkinds = allowedCardProductKinds.get(kind);
			if (subkinds == null || ArrayUtils.contains(subkinds, value))
			{
				subKind = Long.parseLong(value);
				Map<Long, TempCASNSICardProduct> tempInfo = new HashMap<Long, TempCASNSICardProduct>();
				tempInfo.put(subKind, new TempCASNSICardProduct());
				if (cardProductInfo.get(kind) == null)
					cardProductInfo.put(kind, tempInfo);
				else if (cardProductInfo.get(kind).get(subKind) == null)
					cardProductInfo.get(kind).put(subKind, new TempCASNSICardProduct());

				currentCardProductSubKind = true;
			}
		}

		/**
		 * ������� ���� ����������� �������� �������
		 * @param value -  �������� ����
		 * @throws SAXException
		 */
		private void parseQOPEND(String value) throws SAXException
		{
			if (value == null)
			{
			    saveResults(SynchronizeResultStatus.FAIL, "DT_CARD", kind + "-" + subKind, "���� ����������� �������� �������");
			    return;
			}

			DateFormat formatter = new SimpleDateFormat("yyyy.mm.dd");
			try
			{
        	     Date date = formatter.parse(value);

        	     Calendar calendar=Calendar.getInstance();
        	     calendar.setTime(date);
				cardProductInfo.get(kind).get(subKind).setDate(calendar);
			}
			catch(ParseException e)
			{
				throw new SAXException("������ �������� ���� " + value, e);
			}
		}

		/**
		 * ��������� ���������� � ������� ��� ��������� ���������
		 * @param qName - �������� ����
		 * @param attributes  - �������� ����
		 * @throws SAXException - ������ ��������
		 */
		private void parseCurrencyInfo(String qName, Attributes attributes) throws SAXException
		{
				if (qName.equalsIgnoreCase("field"))
				{
						String name  = attributes.getValue("name");
						String value = attributes.getValue("value");

						// ������� ���� ��������, � ���. ����������� ������
						if ("QVKL_T_QDTN1".equalsIgnoreCase(name))
						{
							parseQVKL_T_QDTN1(value);
							return;
						}

						if (!currentCardProductKind)
							return;

						// ������� ������� ��������, � ���. ����������� ������
						if ("QVKL_T_QDTSUB".equalsIgnoreCase(name))
						{
							parseQVKL_T_QDTSUB(value);
							return;
						}

						if (!currentCardProductSubKind)
							return;

					    if ("QVKL_V".equalsIgnoreCase(name))
						{
							parseQVKL_V(value);
							return;
						}
				}
		}

		/**
		 * ������ ��� ��������, � ���. ��������� ������
		 * @param value - �������� ���� ��������
		 */
		private void parseQVKL_T_QDTN1(String value)
		{
			Long qdtn1 = Long.parseLong(value);
			List<Long> allowedKinds = new ArrayList<Long>(allowedCardProductKinds.keySet());
			if (!allowedKinds.contains(qdtn1))
				return;

			kind =  qdtn1;
			currentCardProductKind = true;

		}

		/**
		 * ������� ������� ��������, � ���. ��������� ������
		 * @param value - �������� ������� ��������
		 */
		private void parseQVKL_T_QDTSUB(String value)
		{
			String[] subkinds = allowedCardProductKinds.get(kind);
			if (subkinds == null || ArrayUtils.contains(subkinds, value))
			{
				subKind = Long.parseLong(value);
				Map<Long, TempCASNSICardProduct> tempInfo = new HashMap<Long, TempCASNSICardProduct>();
				tempInfo.put(subKind, new TempCASNSICardProduct());
				if (cardProductInfo.get(kind) == null)
					cardProductInfo.put(kind, tempInfo);
				else if (cardProductInfo.get(kind).get(subKind) == null)
					cardProductInfo.get(kind).putAll(tempInfo);

				currentCardProductSubKind = true;
			}
		}

		/**
		 * ������� ���� ������
		 * @param value - �������� ���� ������
		 * @throws SAXException - ������ ��������
		 */
		private void parseQVKL_V(String value) throws SAXException
		{
			Currency currency = currencyCache.get(value);
			if (currency == null)
				currency = getCurrencyFromDB(value);
			
			if (currency != null)
				cardProductInfo.get(kind).get(subKind).addCurrencies(currency);
		}

		/**
		 * ������� ����� ������ �� �� ���� � ����� ��
		 * @param codeCurrency  - ��� ������
		 * @return  ��������� ������ ��� null
		 * @throws SAXException
		 */
		private Currency getCurrencyFromDB(String codeCurrency) throws SAXException
		{
			// ���� � ��
			CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
			try
			{
				Currency currency = currencyService.findByNumericCode(codeCurrency);
				if (currency == null)
				{
				  saveResults(SynchronizeResultStatus.FAIL, "CUR_CARD", kind + "-" + currentCardProductSubKind, "��� ���������� � ����� ���� ������ ��� ��������������� ������ � ����������� �������");
				}
				else
				{
					currencyCache.put(codeCurrency, currency);
				}

				return currency;
			}
			catch(GateException e)
			{
				throw new SAXException("������ ��������� ������", e);
			}
		}
	}
}
