package com.rssl.phizic.business.dictionaries;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ext.sbrf.dictionaries.ProductUploadMode;
import com.rssl.phizic.business.ext.sbrf.dictionaries.SynchronizeSettingsUtil;
import com.rssl.phizic.business.ima.IMAProduct;
import com.rssl.phizic.common.types.Currency;
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
import com.rssl.phizic.utils.IMAConfig;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.lang.ArrayUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLFilter;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLFilterImpl;

import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.ParserConfigurationException;

/**
 * �������� ������ �� ����������� ���
 * @author Pankin
 * @ created 18.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class IMAProductReplicaSource extends XmlReplicaSourceBase
{
	private static final Log LOG = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private List<IMAProduct> source = new ArrayList<IMAProduct>();
	private SynchronizeResultImpl results;

	public void initialize(GateFactory factory) throws GateException
	{
	}

	public Iterator iterator() throws GateException, GateLogicException
	{
		internalParse();

		Collections.sort(source, new IMAProductComparator());
		return source.iterator();
	}

	protected void clearData()
	{
		source.clear();
		results = new SynchronizeResultImpl();
	}

	protected InputStream getDefaultStream()
	{
		return getResourceReader(com.rssl.phizic.business.Constants.DEFAULT_FILE_NAME);
	}

	protected XMLFilter getDefaultFilter() throws ParserConfigurationException, SAXException
	{
		return new SaxFilter(XmlHelper.newXMLReader());
	}

	public XMLReader chainXMLReader(XMLReader xmlReader)
	{
		return new SaxFilter(super.chainXMLReader(xmlReader));
	}

	/**
	 * @return ��������� �������������
	 */
	public SynchronizeResult getSynchronizeResult()
	{
		return results;
	}

	private class SaxFilter extends XMLFilterImpl
	{
		private boolean iMAProductSection = false;
		private boolean currencyIMAProductSection = false;
		private boolean iMAContractSection = false;
		private boolean templateSection = false;
		private boolean rightTemplateSection;
		private boolean templateText = false;

		private Map<Long, Map<Long, IMAProductTemplate>> iMAProductInfo = new HashMap<Long, Map<Long, IMAProductTemplate>>();
		private Map<String, Currency> currencyCache = new HashMap<String, Currency>();
		private Map<Long, Set<Long>> contractIds = new HashMap<Long, Set<Long>>();
		private Map<Long, StringBuilder> contractTemplate = new HashMap<Long, StringBuilder>();

		private boolean currentIMAProductType = false;
		private boolean currentIMAProductSubType = false;
		private Long subType;
		private Long type;
		private Map<Long, String[]> allowedIMAProductTypes = new HashMap<Long, String[]>();

		private static final String CURRENCY_TABLE_NAME = "qvkl_val";
		private static final String DICTIONARY_NAME = "qvb";
		private static final String CONTRACT_NAME = "ContractDeposit2";
		private static final String TEMPLATE_NAME = "ContractTemplate";
		private static final String TEMPLATE_TYPE = "33";

		private Pattern namePattern = Pattern.compile("(?<=\\().*(?=\\))");

		SaxFilter(XMLReader parent)
		{
			super(parent);
		}

		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
		{
			try
			{
				fillIMAProductKindValues();
			}
			catch (BusinessException e)
			{
				throw new SAXException(e);
			}

			if (qName.equalsIgnoreCase("table") && DICTIONARY_NAME.equalsIgnoreCase(attributes.getValue("name")))
			{
				iMAProductSection = true;   // ����� � ������� ���
			}
			else if (qName.equalsIgnoreCase("table") && CURRENCY_TABLE_NAME.equalsIgnoreCase(attributes.getValue("name")))
			{
				currencyIMAProductSection = true;  // ����� � ������� ������������ ��� � �����
			}
			else if (qName.equalsIgnoreCase("table") && CONTRACT_NAME.equalsIgnoreCase(attributes.getValue("name")))
			{
				iMAContractSection = true;  // ����� � ������� ������������ ��� � ���������
			}
			else if (qName.equalsIgnoreCase("table") && TEMPLATE_NAME.equalsIgnoreCase(attributes.getValue("name")))
			{
				templateSection = true;  // ����� � ������� �������� ���������
			}
			// ������� ����� ���
			else if (iMAProductSection)
			{
				parseIMAProducts(qName, attributes);
			}
			// ������� ����� ���
			else if (currencyIMAProductSection)
			{
				parseCurrencyInfo(qName, attributes);
			}
			// ������� ��������������� �������� ���������
			else if (iMAContractSection)
			{
				parseIMAContract(qName, attributes);
			}
			// ������� ������ ������� ��������
			else if (templateSection)
			{
				parseTemplate(qName, attributes);
			}

			if (!iMAProductSection && !currencyIMAProductSection && !iMAContractSection && !templateSection)
			{
				super.startElement(uri, localName, qName, attributes);
			}
		}

		public void endElement(String uri, String localName, String qName) throws SAXException
		{
			if (qName.equalsIgnoreCase("table"))
			{
				iMAProductSection = false;
				currencyIMAProductSection = false;
				iMAContractSection = false;
				templateSection = false;
			}
			else if (qName.equalsIgnoreCase("package"))
			{
				for (Long typeKey : iMAProductInfo.keySet())
				{
					for (Long subTypeKey : iMAProductInfo.get(typeKey).keySet())
					{

						IMAProductTemplate subTypeValue = iMAProductInfo.get(typeKey).get(subTypeKey);
						// ���� �� �������� ����-�� �� �������, �� ������  ��� ������ ��� ������� ������� � ����� (������ ������)
						if (subTypeValue != null && !StringHelper.isEmpty(subTypeValue.getName()))
						{

							if (subTypeValue.getCurrencies().isEmpty())
								saveResults(SynchronizeResultStatus.FAIL, "IMA_CUR_NO", typeKey + "-" + subTypeKey, "��� ���������� ���� ��� ����������� ������ � ����������� ��� ���");
							else if (StringHelper.isEmpty(contractTemplate.get(typeKey).toString()))
								saveResults(SynchronizeResultStatus.FAIL, "IMA_TEMPLATE_NO", typeKey + "-" + subTypeKey, "��� ���������� ���� ��� ����������� ������ �������� � ����������� ��� ���");
							else
							{
								for (Currency currency : subTypeValue.getCurrencies())
								{
									IMAProduct imaProduct = new IMAProduct();
									imaProduct.setType(typeKey);
									imaProduct.setSubType(subTypeKey);
									imaProduct.setName(subTypeValue.getName());
									imaProduct.setCurrency(currency);
									imaProduct.setContractTemplate(contractTemplate.get(typeKey).toString());

									source.add(imaProduct);
									saveResults(SynchronizeResultStatus.SUCCESS, "��� � �����=" + imaProduct.getType() + " � ��������=" + imaProduct.getSubType() + " � ����� ������=" + imaProduct.getCurrency().getCode() + " ���������", imaProduct.getSynchKey(), "��� ���������");
								}
							}
						}
					}
				}
			}

			if (iMAProductSection || currencyIMAProductSection || iMAContractSection || templateSection)
			{
				if (qName.equalsIgnoreCase("record"))
				{
					currentIMAProductType = false;
					currentIMAProductSubType = false;
					type = null;
					subType = null;
					rightTemplateSection = false;
					templateText = false;
				}
			}
			else
			{
				super.endElement(uri, localName, qName);
			}
		}

		/**
		 * �������� �� �������� ����������� � ������������ �������� ��������� ����� ���
		 */
		private void fillIMAProductKindValues() throws BusinessException
		{

			// ��������� ��� ���� �������
			if (!allowedIMAProductTypes.isEmpty())
				return;

			// �������� ����� �������� ���: ������/��������
			String mode = ConfigFactory.getConfig(IMAConfig.class).getImaProductMode();

			// ���� ����� ������, �� �������� �������� ���������� �������� �� ����������������� �����
			if (mode.equals(ProductUploadMode.ALL.name()))
			{
				allowedIMAProductTypes.putAll(readConfigFileAllMode());
			}
			else
			{
				allowedIMAProductTypes.putAll(readConfigFileOptionallyMode());
			}
		}

		/**
		 * ������ �� ����. ����� ���������� � ��������� ���������� ����� ����������� ��� ��� ������� ������
		 * @return - ������ ����� - ���������� �������� ����� ���
		 */
		private Map<Long, String[]> readConfigFileAllMode() throws BusinessException
		{
			List<Long> iMAProductTypes = SynchronizeSettingsUtil.getIMAProductTypeValues();
			Map<Long, String[]> results = new HashMap<Long, String[]>();

			for (Long type : iMAProductTypes)
				results.put(type, null);

			return results;
		}

		/**
		 * ������ �� ����. ����� ���������� � ��������� ���������� ����� ����������� ��� ��� ���������� ������
		 * @return - ������ ����� - ���������� �������� ����� ��� � ��������
		 * @throws BusinessException
		 */
		private Map<Long, String[]> readConfigFileOptionallyMode() throws BusinessException
		{
			List<Long> iMAProductTypes = SynchronizeSettingsUtil.getIMAProductTypeValues();
			// ������� �������� � ����. ����� ���� � ������� ����������� ���
			Map<Long, String[]> loadInfo = getKindAndSubkindLoadingIMAProduct();

			// ����������� �� ���� ������ � ������� ��� ��������, �� ���������� ��������� ���
			List<Long> keys = new ArrayList<Long>(loadInfo.keySet());
			for (Long key : keys)
			{
				if (!iMAProductTypes.contains(key))
					loadInfo.remove(key);
			}

			return loadInfo;
		}

		/**
		 * ���������� ���������, ���������� � ���� ������ ����������� � �������� ����� ���
		 * � ��������������� �� ��������
		 * @return ��� � ������������ ������-���������
		 */
		private Map<Long, String[]> getKindAndSubkindLoadingIMAProduct()
		{
			Map<Long, String[]> loadInfo = new HashMap<Long, String[]>();

			String unformatLoadIMAProductInfo = ConfigFactory.getConfig(IMAConfig.class).getUnformatLoadIMAProductInfo();

			// �������� ������ ��� � ���������������� ��� ���������
			String[] kindWithSubKinds = unformatLoadIMAProductInfo.split(";");
			// ��������� �� ������ ������ ��� � ��������������� ��� �������
			for (int i = 0; i < kindWithSubKinds.length; i++)
			{
				String[] parseKindAndSubKinds = kindWithSubKinds[i].split("\\.");
				if (parseKindAndSubKinds != null && parseKindAndSubKinds.length > 0)
				{
					String[] subKinds = null;
					if (parseKindAndSubKinds.length >= 2)
					{
						subKinds = parseKindAndSubKinds[1].split(",");
					}

					loadInfo.put(Long.parseLong(parseKindAndSubKinds[0]), subKinds);
				}
			}
			return loadInfo;
		}

		/**
		 * ������� ����� ���
		 * @param qName - �������� ����
		 * @param attributes - ��������� ����
		 * @throws SAXException - ������ ��������
		 */
		private void parseIMAProducts(String qName, Attributes attributes) throws SAXException
		{
			if (qName.equalsIgnoreCase("field"))
			{
				String name = attributes.getValue("name");
				String value = attributes.getValue("value");

				// ������� ������ � ���� ���
				if ("QDTN1".equalsIgnoreCase(name))
				{
					parseType(value);
					return;
				}

				if (!currentIMAProductType) // ��� ��� �� ��� ���������, ������� ������ ��� ������
					return;

				// ������ ������ ���
				if ("QDTSUB".equalsIgnoreCase(name))
				{
					parseSubType(value);
					return;
				}

				if (!currentIMAProductSubType) // ������� ���, ������� ������ ��� ������
					return;

				// ������ �������� ���
				if ("QDN".equalsIgnoreCase(name))
				{
					String subTypeName;
					Matcher matcher = namePattern.matcher(value);
					if (matcher.find())
						subTypeName = matcher.group();
					else
						subTypeName = value;
					iMAProductInfo.get(type).get(subType).setName(subTypeName);
					return;
				}
			}
		}

		/**
		 * ������� ���� ���
		 * @param value - �������� ����
		 */
		private void parseType(String value)
		{
			Long qdtn1 = Long.parseLong(value);
			List<Long> allowedTypes = new ArrayList<Long>(allowedIMAProductTypes.keySet());
			if (!allowedTypes.contains(qdtn1))
				return;

			type = qdtn1;
			currentIMAProductType = true;
		}

		/**
		 * ������� ������� ���
		 * @param value - �������� �������
		 */
		private void parseSubType(String value)
		{
			String[] subtypes = allowedIMAProductTypes.get(type);
			if (subtypes == null || ArrayUtils.contains(subtypes, value))
			{
				subType = Long.parseLong(value);
				Map<Long, IMAProductTemplate> tempInfo = new HashMap<Long, IMAProductTemplate>();
				tempInfo.put(subType, new IMAProductTemplate());
				if (iMAProductInfo.get(type) == null)
					iMAProductInfo.put(type, tempInfo);
				else if (iMAProductInfo.get(type).get(subType) == null)
					iMAProductInfo.get(type).put(subType, new IMAProductTemplate());

				currentIMAProductSubType = true;
			}
		}

		/**
		 * ��������� ���������� � ������� ��� ���
		 * @param qName - �������� ����
		 * @param attributes  - �������� ����
		 * @throws SAXException - ������ ��������
		 */
		private void parseCurrencyInfo(String qName, Attributes attributes) throws SAXException
		{
			if (qName.equalsIgnoreCase("field"))
			{
				String name = attributes.getValue("name");
				String value = attributes.getValue("value");

				// ������� ���� ��������, � ���. ����������� ������
				if ("QVKL_T_QDTN1".equalsIgnoreCase(name))
				{
					parseType(value);
					return;
				}

				if (!currentIMAProductType)
					return;

				// ������� ������� ��������, � ���. ����������� ������
				if ("QVKL_T_QDTSUB".equalsIgnoreCase(name))
				{
					parseSubType(value);
					return;
				}

				if (!currentIMAProductSubType)
					return;

				if ("QVKL_V".equalsIgnoreCase(name))
				{
					parseQVKL_V(value);
					return;
				}
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
				iMAProductInfo.get(type).get(subType).addCurrency(currency);
		}

		/**
		 * ������� ����� ������ �� �� ���� � ����� ��
		 * @param codeCurrency  - ��� ������
		 * @return ��������� ������ ��� null
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
					saveResults(SynchronizeResultStatus.FAIL, "CUR_IMA", type + "(" + subType + ")", "��� ���������� � ����� ���� ������ ��� ��������������� ������ � ����������� �������");
				}
				else
				{
					currencyCache.put(codeCurrency, currency);
				}

				return currency;
			}
			catch (GateException e)
			{
				throw new SAXException("������ ��������� ������", e);
			}
		}

		/**
		 * ���������� ����������� � �����������
		 * @param resultStatus  - ������ ���������: ������ ��� ���
		 * @param message - ����� ������
		 * @param kind - ���� ���
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
				LOG.error(kind + ": " + logMessage);
			}
			else if (SynchronizeResultStatus.SUCCESS.equals(resultStatus))
			{
				LOG.info(kind + ": " + logMessage);
			}

			results.setDictionaryType(DictionaryType.IMA);
			results.addResultRecord(record);
		}

		/**
		 * ������� ��������� �� �������� ���
		 * @param qName - �������� ����
		 * @param attributes - ��������� ����
		 * @throws SAXException - ������ ��������
		 */
		private void parseIMAContract(String qName, Attributes attributes) throws SAXException
		{
			if (qName.equalsIgnoreCase("field"))
			{
				String name = attributes.getValue("name");
				String value = attributes.getValue("value");

				// ������� ���� ��������
				if ("QDTN1".equalsIgnoreCase(name))
				{
					parseType(value);
					return;
				}

				if (!currentIMAProductType)
					return;

				// ������� ������� ��������
				if ("QDTSUB".equalsIgnoreCase(name))
				{
					parseSubType(value);
					return;
				}

				if (!currentIMAProductSubType)
					return;

				// ������� ��������������� �������� ���������
				if ("CONTRACTTEMPLATE".equalsIgnoreCase(name))
				{
					if (contractIds.get(type) == null)
						contractIds.put(type, new HashSet<Long>());
					contractIds.get(type).add(Long.parseLong(value));
					return;
				}
			}
		}

		/**
		 * ������� �������� ��������� �� �������� ���
		 * @param qName - �������� ����
		 * @param attributes - ��������� ����
		 * @throws SAXException - ������ ��������
		 */
		private void parseTemplate(String qName, Attributes attributes) throws SAXException
		{
			if (qName.equalsIgnoreCase("field"))
			{
				String name = attributes.getValue("name");
				String value = attributes.getValue("value");

				// ��������� ����� �������
				if ("TEMPLATEID".equalsIgnoreCase(name))
				{
					for (Long id : contractIds.keySet())
					{
						if (contractIds.get(id).contains(Long.parseLong(value)))
						{
							rightTemplateSection = true;
							type = id;
						}
					}
					return;
				}

				// ��������� ��� �������
				if ("TYPE".equalsIgnoreCase(name))
				{
					if (value.equals(TEMPLATE_TYPE))
						rightTemplateSection = true;
					else
						rightTemplateSection = false;
					return;
				}

				if (!rightTemplateSection)
					return;

				// ��������� ����� �������
				if ("TEXT".equalsIgnoreCase(name))
				{
					templateText = true;
					contractTemplate.put(type, new StringBuilder());
					return;
				}
			}
		}

		public void characters(char[] ch, int start, int length) throws SAXException
		{
			if (templateText)
			{
				contractTemplate.get(type).append(new String(ch, start, length));
			}
		}
	}

	/**
	 * ��������������� ����� ��� �������� ���������� �� ���
	 */
	private class IMAProductTemplate
	{
		private String name;
		private List<Currency> currencies = new ArrayList<Currency>();

		public String getName()
		{
			return name;
		}

		public void setName(String name)
		{
			this.name = name;
		}

		@SuppressWarnings({"ReturnOfCollectionOrArrayField"})
		public List<Currency> getCurrencies()
		{
			return currencies;
		}

		@SuppressWarnings({"AssignmentToCollectionOrArrayFieldFromParameter"})
		public void setCurrencies(List<Currency> currencies)
		{
			this.currencies = currencies;
		}

		public void addCurrency(Currency currency)
		{
			this.currencies.add(currency);
		}
	}
}
