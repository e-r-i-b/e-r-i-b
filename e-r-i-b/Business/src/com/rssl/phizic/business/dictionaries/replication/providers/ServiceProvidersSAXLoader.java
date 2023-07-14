package com.rssl.phizic.business.dictionaries.replication.providers;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.dictionaries.billing.Billing;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.operations.background.ReplicationTaskResult;
import com.rssl.phizic.business.operations.restrictions.ServiceProvidersRestriction;
import com.rssl.phizic.common.types.documents.ServiceProvidersConstants;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.SynchKeyComparator;
import com.rssl.phizic.gate.dictionaries.XmlReplicaSourceBase;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.lang.BooleanUtils;
import org.xml.sax.*;
import org.xml.sax.helpers.XMLFilterImpl;

import java.io.InputStream;
import java.util.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;

import static com.rssl.phizic.business.dictionaries.replication.providers.ReplicationType.*;
import static com.rssl.phizic.common.types.documents.ServiceProvidersConstants.*;

/**
 * @author khudyakov
 * @ created 30.12.2009
 * @ $Author$
 * @ $Revision$
 */

public class ServiceProvidersSAXLoader extends XmlReplicaSourceBase
{
	private static final String SCHEMA_FILE_NAME = "com/rssl/phizic/business/dictionaries/providers/recipient.xsd";

	public static final String ACCESS_LOAD_TYPE_PREFIX = "com.rssl.phizic.providers.replica.access.";
	public static final String ACCESS_LOAD_TYPE_ERROR_MESSAGE = "Недостаточно прав для проведения репликации в установленном в файле режиме.";

	private List<ServiceProviderForReplicationWrapper> providers = new ArrayList<ServiceProviderForReplicationWrapper>();
	private ServiceProvidersRestriction restriction;
	private InputStream stream;
	private LoadType loadType;
	//биллинговые системы, которые участвуют в репликации (выбраны из интерфейса пользователя)
	private List<Long> initializedBillingIds;
	private ReplicationTaskResult result;
	private Properties specificProperties;
	//биллинговые системы, которые участвуют в репликации, дело в том, что OneWayReplicator, удаляет
	//все сущности, которые попали к нему в выборку (таков его алгоритм), поэтому необходимо сообщить ему с какими именно
	//биллиноговыми системами ему предстоит работать. Таким образом мы должны работать только с выбранными биллинговыми системами
	//из интерфейса пользователя и биллинговыми системами из файла репликации.
	private Set<Long> replicatedBillingIds;
	private List<Long> notReplicatedProvidersIds = new ArrayList<Long>();

	//тип формата репликации
	private ReplicationType replicationType;

	private String dbInstanceName;

	/**
	 * Тип репликации
	 */
	public enum LoadType
	{
		FULL_REPLICATION,
		PARTIAL_REPLICATION,
		DELETE
	}

	/**
	 * Инициализация
	 *
	 * @param stream        - поток документа
	 * @param billingIds    - выбранные биллинговые системы
	 * @param restriction   - ограничение на использование ПУ
	 * @param result        - результат(отчет о выполненных действиях).
	 */
	public ServiceProvidersSAXLoader(InputStream stream, List<Long> billingIds, ServiceProvidersRestriction restriction, ReplicationTaskResult result, Properties specificProperties, String dbInstanceName) throws GateException, GateLogicException
	{
		this.restriction = restriction;
		this.stream = stream;
		this.initializedBillingIds = billingIds;
		this.result = result;
		this.specificProperties = specificProperties;
		this.replicatedBillingIds = new HashSet<Long>();
		this.dbInstanceName = dbInstanceName;

		internalParse();
	}

	public void initialize(GateFactory factory) throws GateException
	{

	}

	/**
	 * @return тип загрузки
	 */
	public LoadType getLoadType() throws GateException
	{
		return loadType;
	}

	/**
	 * @return список сущностей
	 */
	public List<ServiceProviderForReplicationWrapper> getData() throws GateException, GateLogicException
	{
		if (!providers.isEmpty())
			Collections.sort(providers, new SynchKeyComparator());

		return providers;
	}

	protected InputStream getDefaultStream()
	{
		return stream;
	}

	protected XMLFilter getDefaultFilter() throws SAXException, ParserConfigurationException
	{
		try
		{
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream inputStream = classLoader.getResourceAsStream(SCHEMA_FILE_NAME);
			Schema schema = XmlHelper.newSchema(new StreamSource(inputStream));

			return new SaxFilter(XmlHelper.newXMLReader(schema));
		}
		catch (ParserConfigurationException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			log.error("Ошибка получения схемы recipient.xsd");
			throw new SAXException(e);
		}
	}

	public Iterator<ServiceProviderForReplicationWrapper> iterator() throws GateException, GateLogicException
	{
		return getData().iterator();
	}

	protected void clearData()
	{
		providers.clear();
	}

	protected String getErrorMessage()
	{
		return "Загрузка не может быть выполнена: структура загружаемого файла не соответствует структуре, используемой в системе.";
	}


	/**
	 * @return - вернуть список ид. поставщиков услуг, которые не попали в ресурс репликации,
	 * но при этом удалять их нельзя, destanationResource должен их учесть.
	 */
	public List<Long> getNotReplicatedProvidersIds()
	{
		return Collections.unmodifiableList(notReplicatedProvidersIds);
	}

	/**
	 * Возвращаем идентификаторы биллинговых систем,
	 * которые учавствуют в репликации
	 * @return идентификаторы биллинговых систем
	 */
	public Set<Long> getReplicatedBillings()
	{
		return Collections.unmodifiableSet(replicatedBillingIds);
	}

	/**
	 * @return тип формата репликации
	 */
	public ReplicationType getReplicationType()
	{
		return replicationType;
	}

	private class SaxFilter extends XMLFilterImpl
	{
		private Map<String, Object> defaultSource;
		private Map<String, Object> source;

		private Map<String, Object> defaultAttributes;
		private List<Map<String, Object>> attributes;
		private Map<String, Object> attribute;

		private List<Map<String, Object>> fieldValidators;
		private Map<String, Object> fieldValidator;

		private List<Object> requisiteTypes;

		private List<String> defaultRegions;
		private List<String> servicesGroups;
		private List<String> regions;
		private List<String> thresholdLimits;
		private List<String> providerAutoPayTypes;
		private List<String> defaultErrorsCollector;                     //список ошибок по умолчанию в рамках одной биллинговой системы
		private List<String> errorsCollector;                            //список ошибок в рамках одного поставщика
		private String menu;

		private StringBuffer value = new StringBuffer();

		private boolean replicationSection = false;
		private boolean validatorSection = false;
		private boolean attributeSection = false;
		private boolean defaultSection = false;
		private boolean deletedSection = false;
		private boolean codeBSSelected = false;                         //секцию по умолчанию могут открыть либо CodeBS, либо DefaultValues
		private boolean defaultValuesSelected = false;                  //секцию по умолчанию могут открыть либо CodeBS, либо DefaultValues

		private String autoPayShemePrefix;

		private Locator locator;

		public SaxFilter(XMLReader parent)
		{
			super(parent);
		}

		public void setDocumentLocator(Locator locator)
		{
			this.locator = locator;
		}

		private void addError(String error)
		{
			result.addToReport(error + "\n");
		}

		private void addErrors(Collection<String> errors)
		{
			if (errors == null)
			{
				return;
			}
			for (String error : errors)
			{
				addError(error);
			}
		}

		public void characters(char ch[], int start, int length) throws SAXException
		{
			value.append(ch, start, length);
		}

		public void startElement(String uri, String localName, String qName, Attributes attributes)
				throws SAXException
		{
			value = new StringBuffer();

			if (qName.equalsIgnoreCase(RECIPIENT_LIST_FIELD))
			{
				String version = attributes.getValue("version");
				replicationType = StringHelper.isEmpty(version) ? ESB : version.equals("3.0") ? DEFAULT : TB;
			}
			if (qName.equalsIgnoreCase(LOAD_REPLICATION_FIELD))
			{
				loadType = LoadType.FULL_REPLICATION;
				checkAccessLoadType(loadType);
				replicationSection = true;
				return;
			}
			if (qName.equalsIgnoreCase(ServiceProvidersConstants.UPDATE_REPLICATION_FIELD))
			{
				loadType = LoadType.PARTIAL_REPLICATION;
				checkAccessLoadType(loadType);
				replicationSection = true;
				return;
			}
			if (replicationSection)
			{
				if (DEFAULT != replicationType)
				{
					//дополнительная проверка
					if (qName.equalsIgnoreCase(PROVIDER_TER_REGION_FIELD))
					{
						replicationType = ESB;
						return;
					}
					//дополнительная проверка
					if (qName.equalsIgnoreCase(PROVIDER_CODE_FIELD))
					{
						replicationType = TB;
						return;
					}
				}
				if (qName.equalsIgnoreCase(BILLING_FIELD))
				{
					defaultSource = new HashMap<String, Object>();
					defaultAttributes = new HashMap<String, Object>();
					return;
				}
				if (qName.equalsIgnoreCase(PROVIDER_CODE_BS_FIELD))
				{
					if (!defaultSection)
					{
						defaultErrorsCollector = new ArrayList<String>();
						defaultSection = true;
					}
					//если секцию по умолчанию открывает тег CodeBS, то устанавливаем этот атрибут
					codeBSSelected = true;
					return;
				}
				if (qName.equalsIgnoreCase(DEFAULT_VALUES_FIELD))
				{
					if (!defaultSection)
					{
						defaultErrorsCollector = new ArrayList<String>();
						defaultSection = true;
					}
					//если секцию по умолчанию открывает тег DefaultValues, то устанавливаем этот атрибут
					defaultValuesSelected = true;
					return;
				}
				if (qName.equalsIgnoreCase(PROVIDER_FIELD))
				{
					//закрываем секцию по умолчанию, если отсутствовал тег DefaultValues
					if (codeBSSelected && !defaultValuesSelected)
					{
						defaultSection = false;
						codeBSSelected = false;
						defaultValuesSelected = false;
					}

					//если не секция по умолчанию, инициализируем нового поставшщика
					if (!defaultSection)
					{
						this.attributes = new ArrayList<Map<String, Object>>();
						source = new HashMap<String, Object>();
						source.putAll(defaultSource);
						errorsCollector = new ArrayList<String>();
					}
					return;
				}

				if(qName.equals(PROVIDER_AUTO_PAYMENT_ALWAYS_SCHEM_FIELD))
				{
					// устанавливаем префикс, чтоб различать значения суммы для порогового, регулярного и по выставленному счету автоплатежа
					autoPayShemePrefix = PROVIDER_AUTO_PAYMENT_ALWAYS_SCHEM_PREFIX;
				}

				if(qName.equals(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_FIELD))
				{
					// устанавливаем префикс, чтоб различать значения суммы для порогового, регулярного и по выставленному счету автоплатежа
					autoPayShemePrefix = PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_PREFIX;
				}

				if(qName.equals(PROVIDER_AUTO_PAYMENT_INVOICE_SCHEM_FIELD))
				{
					// устанавливаем префикс, чтоб различать значения суммы для порогового, регулярного и по выставленному счету автоплатежа
					autoPayShemePrefix = PROVIDER_AUTO_PAYMENT_INVOICE_SCHEM_PREFIX;
				}


				if (qName.equalsIgnoreCase(REGIONS_FIELD))
				{
					if (defaultSection)
					{
						defaultRegions = new ArrayList<String>();
					}
					else
					{
						regions = new ArrayList<String>();
					}
					return;
				}
				if (qName.equalsIgnoreCase(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_FIELD))
				{
					thresholdLimits = new ArrayList<String>();
					return;
				}
				if (qName.equalsIgnoreCase(PROVIDER_AUTO_PAY_TYPES_FOR_ESB_FIELD))
				{
					providerAutoPayTypes = new ArrayList<String>();
					return;
				}
				if (qName.equalsIgnoreCase(PROVIDER_GRP_SERVICES_FIELD))
				{
					servicesGroups = new ArrayList<String>();
				}
				if (qName.equalsIgnoreCase(ATTRIBUTES_FIELD))
				{
					attributeSection = true;
					return;
				}
				if (qName.equalsIgnoreCase(ATTRIBUTE_FIELD))
				{
					if (defaultSection)
					{
						defaultAttributes = new HashMap<String, Object>();
					}
					else
					{
						attribute = new HashMap<String, Object>();
						attribute.putAll(defaultAttributes);
					}
					return;
				}
				if (qName.equalsIgnoreCase(ATTRIBUTE_REQUISITE_TYPES))
				{
					requisiteTypes = new ArrayList<Object>();
					return;
				}
				if (qName.equalsIgnoreCase(ATTRIBUTE_MENU_FIELD))
				{
					menu = "";
				}
				if (qName.equalsIgnoreCase(VALIDATORS_FIELD) && !defaultSection)
				{
					fieldValidators = new ArrayList<Map<String, Object>>();
					validatorSection = true;
					return;
				}
				if (qName.equalsIgnoreCase(VALIDATOR_FIELD) && !defaultSection)
				{
					fieldValidator = new HashMap<String, Object>();
					return;
				}
			}

			if (qName.equalsIgnoreCase(DELETE_REPLICATION_FIELD))
			{
				replicationType = ReplicationType.REMOVE;
				loadType = LoadType.DELETE;
				checkAccessLoadType(loadType);
				deletedSection = true;
				return;
			}
			else if (deletedSection)
			{
				if (qName.equalsIgnoreCase(BILLING_FIELD))
				{
					errorsCollector = new ArrayList<String>();
					source = new HashMap<String, Object>();
					return;
				}
			}

			if (!replicationSection && !deletedSection)
			{
				super.startElement(uri, localName, qName, attributes);
			}
		}

		private void checkAccessLoadType(LoadType loadType)
		{
			if(!BooleanUtils.toBoolean(specificProperties.getProperty(ACCESS_LOAD_TYPE_PREFIX + loadType.name())))
				throw new AccessException(ACCESS_LOAD_TYPE_ERROR_MESSAGE);
		}

		public void endElement(String uri, String localName, String qName)
				throws SAXException
		{
			try
			{
				if (qName.equalsIgnoreCase(LOAD_REPLICATION_FIELD)
						|| qName.equalsIgnoreCase(UPDATE_REPLICATION_FIELD))
				{
					replicationSection = false;
					return;
				}
				if (replicationSection)
				{
					if (qName.equalsIgnoreCase(BILLING_FIELD))
					{
						if (!defaultErrorsCollector.isEmpty())
						{
							//добавляем ошибки полей по умолчанию в разрезе биллинговой системы
							String codeBilling = (String) defaultSource.get(PROVIDER_CODE_BS_FIELD);
							addError("Ошибки в секции DefaultValue биллинговой системы " + codeBilling);
							addErrors(defaultErrorsCollector);
						}
					}
					if (qName.equalsIgnoreCase(DEFAULT_VALUES_FIELD))
					{
						//закрываем секцию по умолчанию
						if (codeBSSelected)
						{
							defaultSection = false;
							codeBSSelected = false;
							defaultValuesSelected = false;
						}
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_CODE_BS_FIELD))
					{
						defaultSource.put(PROVIDER_CODE_BS_FIELD, value.toString());

						//закрываем секцию по умолчанию
						if (defaultValuesSelected)
						{
							defaultSection = false;
							codeBSSelected = false;
							defaultValuesSelected = false;
						}
						return;
					}

					//основные поля репликации поставщика услуг
					if (qName.equalsIgnoreCase(REGIONS_FIELD))
					{
						if (defaultSection)
						{
							defaultSource.put(PROVIDER_CODE_REGION_FIELD, defaultRegions);
							return;
						}

						if (TB == replicationType || DEFAULT == replicationType)
						{
							//Заполняем регионы поставщика услуг собственными значениями,
							//если блок <Regions> пуст, то значения полей по умолчанию не должны установиться поставщику услуг
							source.put(PROVIDER_CODE_REGION_FIELD, regions);
							return;
						}
						if (ESB == replicationType)
						{
							source.put(PROVIDER_CODE_REGION_FIELD,
									!regions.isEmpty() ? regions : defaultRegions);
							return;
						}
					}
					if (qName.equalsIgnoreCase(PROVIDER_PAYMENT_CODE_OFFICE_FIELD))
					{
						if (ESB != replicationType)
						{
							if (defaultSection)
							{
								if (ReplicationHelper.validate(value.toString(), defaultErrorsCollector, ReplicationHelper.buildRegexpValidator("^.{0,50}$", qName)))
									defaultSource.put(PROVIDER_PAYMENT_CODE_OFFICE_FIELD, value.toString());
								return;
							}
							source.put(PROVIDER_PAYMENT_CODE_OFFICE_FIELD, value.toString());
						}
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_STATE_FIELD))
					{
						if (defaultSection)
						{
							if (ReplicationHelper.validate(value.toString(), defaultErrorsCollector, ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", qName)))
								defaultSource.put(PROVIDER_STATE_FIELD, value.toString());
							return;
						}
						source.put(PROVIDER_STATE_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_CODE_SERVICE_FIELD))
					{
						if (defaultSection)
						{
							if (ReplicationHelper.validate(value.toString(), errorsCollector, ReplicationHelper.buildRegexpValidator("^.{0,50}$", qName)))
								defaultSource.put(PROVIDER_CODE_SERVICE_FIELD, value.toString());
							return;
						}
						source.put(PROVIDER_CODE_SERVICE_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_NAME_FIELD))
					{
						source.put(PROVIDER_NAME_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_INN_FIELD))
					{
						source.put(PROVIDER_INN_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_KPP_FIELD))
					{
						source.put(PROVIDER_KPP_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_ACCOUNT_FIELD))
					{
						source.put(PROVIDER_ACCOUNT_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_BIC_FIELD))
					{
						source.put(PROVIDER_BIC_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_BANK_NAME_FIELD))
					{
						source.put(PROVIDER_BANK_NAME_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_BANK_COR_ACCOUNT_FIELD))
					{
						source.put(PROVIDER_BANK_COR_ACCOUNT_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_PHONE_TO_CLIENT_FIELD))
					{
						source.put(PROVIDER_PHONE_TO_CLIENT_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(SUB_TYPE_FIELD))
					{
						source.put(SUB_TYPE_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(COMMENT_FIELD) && !attributeSection)
					{
						source.put(PROVIDER_DESCRIPTION_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_IS_POPULAR_FIELD))
					{
						if (defaultSection)
						{
							if (ReplicationHelper.validate(value.toString(), defaultErrorsCollector, ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", qName)))
								defaultSource.put(PROVIDER_IS_POPULAR_FIELD, value.toString());
							return;
						}
						source.put(PROVIDER_IS_POPULAR_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_CODE_RECIPIENT_SBOL_FIELD))
					{
						source.put(PROVIDER_CODE_RECIPIENT_SBOL_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_CODE_RECIPIENT_BS_FIELD))
					{
						source.put(PROVIDER_CODE_RECIPIENT_BS_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_NAME_SERVICE_FIELD))
					{
						source.put(PROVIDER_NAME_SERVICE_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_GRP_SERVICES_FIELD))
					{
						source.put(PROVIDER_GRP_SERVICES_FIELD, servicesGroups);
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_GRP_SERVICE_CODE_FIELD))
					{
						servicesGroups.add(value.toString());
					}
					if (qName.equalsIgnoreCase(PROVIDER_ALIAS_FIELD))
					{
						source.put(PROVIDER_ALIAS_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_LEGAL_NAME_FIELD))
					{
						source.put(PROVIDER_LEGAL_NAME_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_NAME_ON_BILL_FIELD))
					{
						source.put(PROVIDER_NAME_ON_BILL_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_NOT_VISIBLE_BANK_DETAILS_FIELD))
					{
						source.put(PROVIDER_NOT_VISIBLE_BANK_DETAILS_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(VISIBLE_PAYMENTS_FOR_INTERNET_BANK))
					{
						source.put(VISIBLE_PAYMENTS_FOR_INTERNET_BANK, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(VISIBLE_PAYMENTS_FOR_MAPI))
					{
						source.put(VISIBLE_PAYMENTS_FOR_MAPI, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(VISIBLE_PAYMENTS_FOR_ATM_API))
					{
						source.put(VISIBLE_PAYMENTS_FOR_ATM_API, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(AVAILABLE_PAYMENTS_FOR_INTERNET_BANK))
					{
						source.put(AVAILABLE_PAYMENTS_FOR_INTERNET_BANK, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(AVAILABLE_PAYMENTS_FOR_MAPI))
					{
						source.put(AVAILABLE_PAYMENTS_FOR_MAPI, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(AVAILABLE_PAYMENTS_FOR_ATM_API))
					{
						source.put(AVAILABLE_PAYMENTS_FOR_ATM_API, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(AVAILABLE_PAYMENTS_FOR_ERMB))
					{
						source.put(AVAILABLE_PAYMENTS_FOR_ERMB, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_IS_FEDERAL_FIELD))
					{
						source.put(PROVIDER_IS_FEDERAL_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_PLANING_FOR_DEACTIVATE) && DEFAULT == replicationType)
					{
						source.put(PROVIDER_PLANING_FOR_DEACTIVATE, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_TER_REGION_FIELD))
					{
						if (defaultSection)
						{
							if (ReplicationHelper.validate(value.toString(), defaultErrorsCollector, ReplicationHelper.buildRegexpValidator("^.{2}$", qName)))
								defaultSource.put(PROVIDER_TER_REGION_FIELD, value.toString());
							return;
						}
						source.put(PROVIDER_TER_REGION_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_CODE1_REGION_FIELD))
					{
						if (defaultSection)
						{
							if (ReplicationHelper.validate(value.toString(), defaultErrorsCollector, ReplicationHelper.buildRegexpValidator("^.{3}$", qName)))
							{
								defaultRegions.add(defaultSource.get(PROVIDER_TER_REGION_FIELD) + DELIMITER + value.toString());
							}
							defaultSource.remove(PROVIDER_TER_REGION_FIELD);
							return;
						}

						//валидировать значения конкретного поставщика будем позже
						regions.add(source.get(PROVIDER_TER_REGION_FIELD) + DELIMITER + value.toString());
						source.remove(PROVIDER_TER_REGION_FIELD);
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_PAYMENT_OFFICE_TB_FIELD))
					{
						if (defaultSection)
						{
							if (ReplicationHelper.validate(value.toString(), defaultErrorsCollector, ReplicationHelper.buildRegexpValidator("^.{0,2}$", qName)))
								defaultSource.put(PROVIDER_PAYMENT_OFFICE_TB_FIELD, value.toString());
							return;
						}
						source.put(PROVIDER_PAYMENT_OFFICE_TB_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_PAYMENT_OFFICE_OSB_FIELD))
					{
						if (defaultSection)
						{
							if (ReplicationHelper.validate(value.toString(), defaultErrorsCollector, ReplicationHelper.buildRegexpValidator("^.{0,4}$", qName)))
							{
								defaultSource.put(PROVIDER_PAYMENT_CODE_OFFICE_FIELD, defaultSource.get(PROVIDER_PAYMENT_OFFICE_TB_FIELD) + DELIMITER + value.toString());
							}
							defaultSource.remove(PROVIDER_PAYMENT_OFFICE_TB_FIELD);
							return;
						}

						source.put(PROVIDER_PAYMENT_CODE_OFFICE_FIELD, source.get(PROVIDER_PAYMENT_OFFICE_TB_FIELD) + DELIMITER + value.toString());
						source.remove(PROVIDER_PAYMENT_OFFICE_TB_FIELD);
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_CODE_FIELD))
					{
						source.put(PROVIDER_CODE_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_SERVICE_TYPE_FIELD))
					{
						if (defaultSection)
						{
							if (ReplicationHelper.validate(value.toString(), defaultErrorsCollector, ReplicationHelper.buildRegexpValidator("^.{0,50}$", qName)))
								defaultSource.put(PROVIDER_SERVICE_TYPE_FIELD, value.toString());
							return;
						}
						source.put(PROVIDER_SERVICE_TYPE_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_IS_PROPS_ONLINE_FIELD))
					{
						if (defaultSection)
						{
							if (ReplicationHelper.validate(value.toString(), defaultErrorsCollector, ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", qName)))
								defaultSource.put(PROVIDER_IS_PROPS_ONLINE_FIELD, value.toString());
							return;
						}
						source.put(PROVIDER_IS_PROPS_ONLINE_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_IS_BANK_DETAILS_FIELD))
					{
						if (defaultSection)
						{
							if (ReplicationHelper.validate(value.toString(), defaultErrorsCollector, ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", qName)))
								defaultSource.put(PROVIDER_IS_BANK_DETAILS_FIELD, value.toString());
							return;
						}
						source.put(PROVIDER_IS_BANK_DETAILS_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_CODE_REGION_FIELD))
					{
						if (defaultSection)
						{
							if (ReplicationHelper.validate(value.toString(), defaultErrorsCollector, ReplicationHelper.buildRegexpValidator("^.{0,20}$", qName)))
								defaultRegions.add(value.toString());
							return;
						}
						regions.add(value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_PAYMENT_ACCOUNT_FIELD))
					{
						source.put(PROVIDER_PAYMENT_ACCOUNT_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_CODE_OFFICE_NSI_FIELD))
					{
						source.put(PROVIDER_CODE_OFFICE_NSI_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_ACCOUNT_TYPE_FIELD))
					{
						source.put(PROVIDER_ACCOUNT_TYPE_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_IS_MOBILE_BANK_FIELD))
					{
						if (defaultSection)
						{
							if (ReplicationHelper.validate(value.toString(), defaultErrorsCollector, ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", qName)))
								defaultSource.put(PROVIDER_IS_MOBILE_BANK_FIELD, value.toString());
							return;
						}
						source.put(PROVIDER_IS_MOBILE_BANK_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_IS_DEBT_FIELD))
					{
						if (defaultSection)
						{
							if (ReplicationHelper.validate(value.toString(), defaultErrorsCollector, ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", qName)))
								defaultSource.put(PROVIDER_IS_DEBT_FIELD, value.toString());
							return;
						}
						source.put(PROVIDER_IS_DEBT_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_MOBILE_BANK_CODE_FIELD))
					{
						source.put(PROVIDER_MOBILE_BANK_CODE_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_MIN_COMMISSION_FIELD))
					{
						if (defaultSection)
						{
							if (ReplicationHelper.validate(value.toString(), defaultErrorsCollector, ReplicationHelper.buildRegexpValidator("^\\d{1,8}+(.\\d{0,2})$", qName)))
								defaultSource.put(PROVIDER_MIN_COMMISSION_FIELD, value.toString());
							return;
						}
						source.put(PROVIDER_MIN_COMMISSION_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_MAX_COMMISSION_FIELD))
					{
						if (defaultSection)
						{
							if (ReplicationHelper.validate(value.toString(), defaultErrorsCollector, ReplicationHelper.buildRegexpValidator("^\\d{1,8}+(.\\d{0,2})$", qName)))
								defaultSource.put(PROVIDER_MAX_COMMISSION_FIELD, value.toString());
							return;
						}
						source.put(PROVIDER_MAX_COMMISSION_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_PERCENT_FIELD))
					{
						if (defaultSection)
						{
							if (ReplicationHelper.validate(value.toString(), defaultErrorsCollector, ReplicationHelper.buildRegexpValidator("^\\d{1,8}+(.\\d{0,2})$", qName)))
								defaultSource.put(PROVIDER_PERCENT_FIELD, value.toString());
							return;
						}
						source.put(PROVIDER_PERCENT_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_IS_GROUND_FIELD))
					{
						if (defaultSection)
						{
							if (ReplicationHelper.validate(value.toString(), defaultErrorsCollector, ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", qName)))
								defaultSource.put(PROVIDER_IS_GROUND_FIELD, value.toString());
							return;
						}
						source.put(PROVIDER_IS_GROUND_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_SEPARATOR1_FIELD))
					{
						if (defaultSection)
						{
							if (ReplicationHelper.validate(value.toString(), defaultErrorsCollector, ReplicationHelper.buildRegexpValidator("^.{1}$", qName)))
								defaultSource.put(PROVIDER_SEPARATOR1_FIELD, value.toString());
							return;
						}
						source.put(PROVIDER_SEPARATOR1_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_SEPARATOR2_FIELD))
					{
						if (defaultSection)
						{
							if (ReplicationHelper.validate(value.toString(), defaultErrorsCollector, ReplicationHelper.buildRegexpValidator("^.{1}$", qName)))
								defaultSource.put(PROVIDER_SEPARATOR2_FIELD, value.toString());
							return;
						}
						source.put(PROVIDER_SEPARATOR2_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_IS_AUTO_PAYMENT_FIELD))
					{
						source.put(PROVIDER_IS_AUTO_PAYMENT_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_IS_AUTO_PAYMENT_IN_API))
					{
						source.put(PROVIDER_IS_AUTO_PAYMENT_IN_API, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_IS_AUTO_PAYMENT_IN_ATM))
					{
						source.put(PROVIDER_IS_AUTO_PAYMENT_IN_ATM, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_IS_AUTO_PAYMENT_IN_ERMB))
					{
						source.put(PROVIDER_IS_AUTO_PAYMENT_IN_ERMB, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_AUTO_PAYMENT_VISIBLE))
					{
						source.put(PROVIDER_AUTO_PAYMENT_VISIBLE, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_AUTO_PAYMENT_VISIBLE_IN_API))
					{
						source.put(PROVIDER_AUTO_PAYMENT_VISIBLE_IN_API, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_AUTO_PAYMENT_VISIBLE_IN_ATM))
					{
						source.put(PROVIDER_AUTO_PAYMENT_VISIBLE_IN_ATM, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_AUTO_PAYMENT_VISIBLE_IN_ERMB))
					{
						source.put(PROVIDER_AUTO_PAYMENT_VISIBLE_IN_ERMB, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_IS_BANKOMAT_FIELD))
					{
						source.put(PROVIDER_IS_BANKOMAT_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_AUTO_PAYMENT_ALWAYS_SCHEM_FIELD))
					{
						if (replicationType == ESB)
							source.put(PROVIDER_AUTO_PAYMENT_ALWAYS_SCHEM_FIELD, value.toString());
						else
							source.put(PROVIDER_AUTO_PAYMENT_ALWAYS_SCHEM_FIELD, "1");
						autoPayShemePrefix = null;
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_FIELD))
					{
						source.put(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_FIELD, "1");
						autoPayShemePrefix = null;
						source.put(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_LIMIT_FIELD, thresholdLimits);
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_AUTO_PAYMENT_INVOICE_SCHEM_FIELD))
					{
						if (replicationType == ESB)
							source.put(PROVIDER_AUTO_PAYMENT_INVOICE_SCHEM_FIELD, value.toString());
						else
							source.put(PROVIDER_AUTO_PAYMENT_INVOICE_SCHEM_FIELD, "1");
						autoPayShemePrefix = null;
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_LIMIT_FIELD))
					{
						thresholdLimits.add(value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_AUTO_PAYMENT_ALWAYS_SCHEM_MIN_SUM_FIELD))
					{
						source.put(autoPayShemePrefix + PROVIDER_AUTO_PAYMENT_ALWAYS_SCHEM_MIN_SUM_FIELD , value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_AUTO_PAYMENT_ALWAYS_SCHEM_CLIENT_HINT_FIELD))
					{
						source.put(autoPayShemePrefix + PROVIDER_AUTO_PAYMENT_ALWAYS_SCHEM_CLIENT_HINT_FIELD , value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_CLIENT_HINT_FIELD))
					{
						source.put(autoPayShemePrefix + PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_CLIENT_HINT_FIELD , value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_AUTO_PAYMENT_INVOICE_SCHEM_CLIENT_HINT_FIELD))
					{
						source.put(autoPayShemePrefix + PROVIDER_AUTO_PAYMENT_INVOICE_SCHEM_CLIENT_HINT_FIELD , value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_AUTO_PAYMENT_ALWAYS_SCHEM_MAX_SUM_FIELD))
					{
						source.put(autoPayShemePrefix + PROVIDER_AUTO_PAYMENT_ALWAYS_SCHEM_MAX_SUM_FIELD , value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_MIN_SUM_FIELD))
					{
						source.put(autoPayShemePrefix + PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_MIN_SUM_FIELD , value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_MAX_SUM_FIELD))
					{
						source.put(autoPayShemePrefix + PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_MAX_SUM_FIELD , value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_MIN_VALUE_FIELD))
					{
						source.put(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_MIN_VALUE_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_MAX_VALUE_FIELD))
					{
						source.put(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_MAX_VALUE_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_IS_ACCESS_TOTAL_AMOUNT_LIMIT_FIELD))
					{
						source.put(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_IS_ACCESS_TOTAL_AMOUNT_LIMIT_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_TOTAL_AMOUNT_LIMIT_FIELD))
					{
						source.put(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_TOTAL_AMOUNT_LIMIT_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_TOTAL_AMOUNT_PERIOD_FIELD))
					{
						source.put(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_TOTAL_AMOUNT_PERIOD_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_IS_AUTO_PAY_ACCESSIBLE_FOR_ESB_FIELD))
					{
						source.put(PROVIDER_IS_AUTO_PAY_ACCESSIBLE_FOR_ESB_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_AUTO_PAY_TYPES_FOR_ESB_FIELD))
					{
						source.put(PROVIDER_AUTO_PAY_TYPE_FOR_ESB_FIELD, providerAutoPayTypes);
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_AUTO_PAY_TYPE_FOR_ESB_FIELD))
					{
						providerAutoPayTypes.add(value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_LIMIT_FOR_ESB_FIELD))
					{
						source.put(PROVIDER_LIMIT_FOR_ESB_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_GENERAL_GROUP_RISK_FIELD))
					{
						source.put(PROVIDER_GENERAL_GROUP_RISK_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_MOBILE_GROUP_RISK_FIELD))
					{
						source.put(PROVIDER_MOBILE_GROUP_RISK_FIELD, value.toString());
						return;
					}
					if(qName.equalsIgnoreCase(PROVIDER_VERSION_API_FIELD))
					{
						source.put(PROVIDER_VERSION_API_FIELD, value.toString());
						return;
					}
					if(qName.equalsIgnoreCase(PROVIDER_IS_TEMPLATE_SUPPORTED))
					{
						source.put(PROVIDER_IS_TEMPLATE_SUPPORTED, value.toString());
						return;
					}
					if(qName.equalsIgnoreCase(PROVIDER_IS_BAR_SUPPORTED))
					{
						source.put(PROVIDER_IS_BAR_SUPPORTED, value.toString());
						return;
					}
					if(qName.equalsIgnoreCase(PROVIDER_IS_OFFLINE_AVAILABLE_FIELD))
					{
						source.put(PROVIDER_IS_OFFLINE_AVAILABLE_FIELD, value.toString());
						return;
					}
					if(qName.equalsIgnoreCase(PROVIDER_IS_EDIT_PAYMENT_SUPPORTED))
					{
						source.put(PROVIDER_IS_EDIT_PAYMENT_SUPPORTED, value.toString());
						return;
					}
					if(qName.equalsIgnoreCase(PROVIDER_IS_CREDIT_CARD_ACCESSIBLE))
					{
						source.put(PROVIDER_IS_CREDIT_CARD_ACCESSIBLE, value.toString());
						return;
					}

					//дополнительные поля
					if (qName.equalsIgnoreCase(ATTRIBUTES_FIELD))
					{
						attributeSection = false;
						if (!defaultSection)
							source.put(ATTRIBUTES_FIELD, attributes);
						return;
					}
					if (qName.equalsIgnoreCase(ATTRIBUTE_FIELD))
					{
						if (!defaultSection)
							attributes.add(attribute);
						return;
					}
					if (qName.equalsIgnoreCase(ATTRIBUTE_NAME_BS_FIELD))
					{
						attribute.put(ATTRIBUTE_NAME_BS_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(ATTRIBUTE_NAME_VISIBLE_FIELD))
					{
						attribute.put(ATTRIBUTE_NAME_VISIBLE_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(COMMENT_FIELD) && attributeSection)
					{
						attribute.put(COMMENT_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(ATTRIBUTE_DESCRIPTION_FIELD))
					{
						attribute.put(ATTRIBUTE_DESCRIPTION_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(TYPE_FIELD) && !validatorSection)
					{
						attribute.put(ATTRIBUTE_TYPE_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(ATTRIBUTE_MAX_LENGTH_FIELD))
					{
						attribute.put(ATTRIBUTE_MAX_LENGTH_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(ATTRIBUTE_MIN_LENGTH_FIELD))
					{
						attribute.put(ATTRIBUTE_MIN_LENGTH_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(ATTRIBUTE_LENGTH_FIELD))
					{
						attribute.put(ATTRIBUTE_LENGTH_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(ATTRIBUTE_IS_REQUIRED_FIELD))
					{
						if (defaultSection)
						{
							if (ReplicationHelper.validate(value.toString(), defaultErrorsCollector, ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", qName)))
								defaultAttributes.put(ATTRIBUTE_IS_REQUIRED_FIELD, value.toString());
							return;
						}
						attribute.put(ATTRIBUTE_IS_REQUIRED_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(ATTRIBUTE_IS_EDITABLE_FIELD))
					{
						if (defaultSection)
						{
							if (ReplicationHelper.validate(value.toString(), defaultErrorsCollector, ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", qName)))
								defaultAttributes.put(ATTRIBUTE_IS_EDITABLE_FIELD, value.toString());
							return;
						}
						attribute.put(ATTRIBUTE_IS_EDITABLE_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(ATTRIBUTE_IS_VISIBLE_FIELD))
					{
						if (defaultSection)
						{
							if (ReplicationHelper.validate(value.toString(), defaultErrorsCollector, ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", qName)))
								defaultAttributes.put(ATTRIBUTE_IS_VISIBLE_FIELD, value.toString());
							return;
						}
						attribute.put(ATTRIBUTE_IS_VISIBLE_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(ATTRIBUTE_IS_SUM_FIELD))
					{
						if (defaultSection)
						{
							if (ReplicationHelper.validate(value.toString(), defaultErrorsCollector, ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", qName)))
								defaultAttributes.put(ATTRIBUTE_IS_SUM_FIELD, value.toString());
							return;
						}
						attribute.put(ATTRIBUTE_IS_SUM_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(ATTRIBUTE_IS_KEY_FIELD))
					{
						if (defaultSection)
						{
							if (ReplicationHelper.validate(value.toString(), defaultErrorsCollector, ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", qName)))
								defaultAttributes.put(ATTRIBUTE_IS_KEY_FIELD, value.toString());
							return;
						}
						attribute.put(ATTRIBUTE_IS_KEY_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(ATTRIBUTE_NUMBER_PRECISION_FIELD))
					{
						attribute.put(ATTRIBUTE_NUMBER_PRECISION_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(ATTRIBUTE_IS_FOR_BILL_FIELD))
					{
						attribute.put(ATTRIBUTE_IS_FOR_BILL_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(ATTRIBUTE_INCLUDE_IN_SMS_FIELD))
					{
						attribute.put(ATTRIBUTE_INCLUDE_IN_SMS_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(ATTRIBUTE_SAVE_IN_TEMPLATE_FIELD))
					{
						attribute.put(ATTRIBUTE_SAVE_IN_TEMPLATE_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(ATTRIBUTE_REQUISITE_TYPE))
					{
						requisiteTypes.add(value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(ATTRIBUTE_REQUISITE_TYPES))
					{
						attribute.put(ATTRIBUTE_REQUISITE_TYPES, requisiteTypes);
						return;
					}
					if (qName.equalsIgnoreCase(ATTRIBUTE_HIDE_IN_CONFIRMATION_FIELD))
					{
						attribute.put(ATTRIBUTE_HIDE_IN_CONFIRMATION_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(ATTRIBUTE_MENU_FIELD))
					{
						attribute.put(ATTRIBUTE_MENU_FIELD, menu);
						return;
					}
					if (qName.equalsIgnoreCase(ATTRIBUTE_MENU_ITEM_FIELD))
					{
						menu = StringHelper.isEmpty(menu) ? value.toString() : menu + DELIMITER + value.toString();
						return;
					}
					if (qName.equalsIgnoreCase(ATTRIBUTE_VALUES_FIELD))
					{
						attribute.put(ATTRIBUTE_VALUES_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(ATTRIBUTE_DEFAULT_VALUE_FIELD))
					{
						attribute.put(ATTRIBUTE_DEFAULT_VALUE_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(ATTRIBUTE_MASK_FIELD))
					{
						attribute.put(ATTRIBUTE_MASK_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(ATTRIBUTE_BUSINESS_SUB_TYPE_FIELD))
					{
						attribute.put(ATTRIBUTE_BUSINESS_SUB_TYPE_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(VALIDATORS_FIELD))
					{
						validatorSection = false;
						attribute.put(attribute.get(ATTRIBUTE_NAME_BS_FIELD) + DELIMITER + VALIDATORS_FIELD, fieldValidators);
						return;
					}
					if (qName.equalsIgnoreCase(VALIDATOR_FIELD))
					{
						fieldValidators.add(fieldValidator);
						return;
					}
					if (qName.equalsIgnoreCase(TYPE_FIELD) && validatorSection)
					{
						fieldValidator.put(VALIDATOR_TYPE_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(VALIDATOR_MESSAGE_FIELD))
					{
						fieldValidator.put(VALIDATOR_MESSAGE_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(VALIDATOR_PARAMETER_FIELD))
					{
						fieldValidator.put(VALIDATOR_PARAMETER_FIELD, value.toString());
						return;
					}

					if (qName.equalsIgnoreCase(PROVIDER_FIELD))
					{
						if (defaultSection)
							return;
						addToReplicaSource();
						addErrors(errorsCollector);
						//в DestanationResource должны вернуть идентификаторы используемых биллинговых систем
						Billing billing = ReplicationHelper.findBilling((String) source.get(PROVIDER_CODE_BS_FIELD), ServiceProvidersSAXLoader.this.dbInstanceName);
						if (billing != null)
							replicatedBillingIds.add(billing.getId());
						return;
					}
				}

				if (qName.equalsIgnoreCase(DELETE_REPLICATION_FIELD))
				{
					deletedSection = false;
					return;
				}
				else if (deletedSection)
				{
					if (qName.equalsIgnoreCase(PROVIDER_CODE_BS_FIELD))
					{
						source.put(PROVIDER_CODE_BS_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_CODE_RECIPIENT_BS_FIELD))
					{
						source.put(PROVIDER_CODE_RECIPIENT_BS_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_CODE_SERVICE_FIELD))
					{
						source.put(PROVIDER_CODE_SERVICE_FIELD, value.toString());
						return;
					}
					if (qName.equalsIgnoreCase(PROVIDER_FIELD))
					{
						addToReplicaSource();
    					addErrors(errorsCollector);
						//в DestanationResource должны вернуть идентификаторы используемых биллинговых систем
						Billing billing = ReplicationHelper.findBilling((String) source.get(PROVIDER_CODE_BS_FIELD), ServiceProvidersSAXLoader.this.dbInstanceName);
						if (billing != null)
						{
							replicatedBillingIds.add(billing.getId());
						}
						return;
					}
				}
			}
			finally
			{
				value = new StringBuffer();
			}
		}

		public void error (SAXParseException e) throws SAXException
        {
	        throw e;
		}

		private ServiceProviderFetcher getServiceProviderFetcher(ReplicationType replicationType)
		{
			if(LoadType.DELETE == loadType)
				return new DeleteServiceProviderFetcher(errorsCollector, dbInstanceName);
			else
			{
				if (ESB == replicationType)
					return new ESBServiceProviderFetcher(errorsCollector, dbInstanceName);

				if (TB == replicationType)
					return new TBServiceProviderFetcher(errorsCollector, dbInstanceName);

				if (DEFAULT == replicationType)
					return new DefaultServiceProviderFetcher(errorsCollector, dbInstanceName);
			}

			throw new IllegalStateException();
		}

		/**
		 * Добавление поставщика в ресурс репликации, с проверкой на дубликаты и ограничения пльзователя
		 * @param replicatedProvider - поставщик услуг
		 */
		private void addSource(ServiceProviderForReplicationWrapper replicatedProvider)
		{
			try
			{
				if(checkDublicate(replicatedProvider))
				{
					//проверка ограничений
					String code = replicatedProvider.getProvider().getCode();
					if (restriction.accept(replicatedProvider.getProvider()))
					{
						providers.add(replicatedProvider);
						result.sourceRecordProcessed();
						return;
					}
					else
					{
						errorsCollector.add(String.format("Отсутствует доступ к поставщику услуг, CodeRecipient = %s.", code));
						result.sourceRecordFailed();
					}
				}

			}
			catch (BusinessException e)
			{
				log.error(e.getMessage(), e);
			}
		}

		/**
		 * Добавить данные в ресурс реплакции.
		 */
		private void addToReplicaSource()
		{
			ServiceProviderFetcher fetcher = getServiceProviderFetcher(replicationType);
			fetcher.collect(source);

			ServiceProviderForReplicationWrapper wrapper = fetcher.getWrapper();

			if (fetcher.isReplicated())
			{
				//проверяется выбранная в бизнесе билинговая система
				if (initializedBillingIds == null || initializedBillingIds.isEmpty())
					addSource(wrapper);
				else
				{
					for (Long id : initializedBillingIds)
					{
						if (id.equals(wrapper.getProvider().getBilling().getId()))
							addSource(wrapper);
					}
				}
			}
			else
			{
				String providerCode = loadType == LoadType.DELETE ? (String) source.get(PROVIDER_CODE_FIELD) : wrapper.getProvider().getCode();
				if (!errorsCollector.isEmpty())
				{
					addError("Ошибки в описании поставщика " + providerCode);
				}
				//если поставщик не прошел проверку, но есть в б.д., то мы его не должны удалить
				BillingServiceProvider notReplicatedProvider = ReplicationHelper.findProviderBySynchKey(wrapper.getSynchKey());
				if (notReplicatedProvider != null && loadType != LoadType.DELETE)
					notReplicatedProvidersIds.add(notReplicatedProvider.getId());
				result.sourceRecordFailed();
			}
		}

		/**
		 * Проверка: если реплицируемый поставщик уже в списке поставщиков (убираем дубликаты)
		 * @param replicatedWrapper - врапер реплицируемого поставщика
		 * @return true - не дублирется/false - дублируется.
		 */
		private boolean checkDublicate(ServiceProviderForReplicationWrapper replicatedWrapper)
		{
			BillingServiceProvider replicatedProvider = replicatedWrapper.getProvider();
			for (ServiceProviderForReplicationWrapper wrapper : providers)
			{
				BillingServiceProvider serviceProvider = wrapper.getProvider();
				if (replicatedProvider.getSynchKey().equals(serviceProvider.getSynchKey()))
				{
					errorsCollector.add("Поставщик услуг code = " + serviceProvider.getCode() + " уже добавлен в ресурс репликации.");
					result.sourceRecordFailed();
					return false;
				}
				String replicatedCodeRecSBOL = StringHelper.getEmptyIfNull(replicatedProvider.getCodeRecipientSBOL());
				String serviceProviderCodeRecSBOL = StringHelper.getEmptyIfNull(serviceProvider.getCodeRecipientSBOL());
				if (replicatedCodeRecSBOL.equals(serviceProviderCodeRecSBOL)
						&& replicatedProvider.getCodeService().equals(serviceProvider.getCodeService())
						&& replicatedProvider.getBilling().equals(serviceProvider.getBilling()))
				{
					errorsCollector.add("Поставщик услуг сode = "+ serviceProvider.getCode() + " уже добавлен в систему. Пожалуйста, создайте поставщика с другим кодом.");
					result.sourceRecordFailed();
					return false;
				}
			}
			return true;
		}
	}
}
