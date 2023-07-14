package com.rssl.phizic.business.dictionaries.departments;

import com.rssl.phizic.business.dictionaries.SynchronizeResultImpl;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.StopSAXParseException;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.lang.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLFilter;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLFilterImpl;

import java.io.InputStream;
import java.text.ParseException;
import java.util.*;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author Balovtsev
 * @version 15.04.13 14:18
 */
public class DepartmentsRecodingReplicaSource extends XmlReplicaSourceBase
{
	private static final int    HIBERNATE_THRESHOLD    = 999;
	private static final String VSP_CODES_DELIMITER    = "\\|";
	private static final String CODIFICATION_DELIMITER = ":";
	private static final String EMPTY_CODE             = "||";

	private static final DepartmentsRecordingService service = new DepartmentsRecordingService();

	private Map<String, Set<DepartmentsRecoding>>   source      = null;
	private Map<String, Set<DepartmentsRecoding>>   included    = null;

	private Set<DepartmentsRecoding>                recordings  = null;
	private Set<String>                             subBranches = null;

	private SynchronizeResultImpl result;

	private boolean stopParse;

	public XMLReader chainXMLReader(XMLReader xmlReader)
	{
		return new DepartmentsRecodingSaxFilter(super.chainXMLReader(xmlReader));
	}

	public Iterator iterator() throws GateException, GateLogicException
	{
		internalParse();

		try
		{
			addRecordings();
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}

		return recordings.iterator();
	}

	public SynchronizeResult getSynchronizeResult()
	{
		return result;
	}

	public void initialize(GateFactory factory) throws GateException {}

	@Override
	protected void clearData()
	{
		stopParse = false;
		result    = new SynchronizeResultImpl();
		result.setDictionaryType(DictionaryType.SPOOBK2);

		recordings  = new HashSet<DepartmentsRecoding>();
		subBranches = new HashSet<String>();

		included    = new HashMap<String, Set<DepartmentsRecoding>>();
		source      = new HashMap<String, Set<DepartmentsRecoding>>();
	}

	@Override
	protected InputStream getDefaultStream()
	{
		return null;
	}

	@Override
	protected XMLFilter getDefaultFilter() throws ParserConfigurationException, SAXException
	{
		return new DepartmentsRecodingSaxFilter(XmlHelper.newXMLReader());
	}

	private void addRecordings() throws Exception
	{
		loadMissedDepartments();

		filterByClosed();
		filterBySpoobkMatch();
		filterByDuplicates();
		filterByDepartments();

		fillRecordings();
	}

	private void filterByDepartments() throws Exception
	{
		if (included.size() == 0)
		{
			return;
		}

		Set<String> keySet        = included.keySet();
		Set<String> inDepartments = findCodesContainsInDepartments(keySet);

		Iterator<String> it = keySet.iterator();
		while (it.hasNext())
		{
			String key  = it.next();

			if (!inDepartments.contains( key ))
			{
				Set<DepartmentsRecoding> values = included.get(key);

				exclude("Запись %s DATE_SUC=%2$td.%2$tm.%2$tY не будет добавлена поскольку не найдена соответствующая ей запись в таблице департаментов", values.toArray(new DepartmentsRecoding[values.size()]) );
				it.remove();
			}
		}
	}

	private void filterByDuplicates()
	{
		Iterator<String> keys = source.keySet().iterator();
		while (keys.hasNext())
		{
			String key = keys.next();

			for (DepartmentsRecoding recoding : source.get(key))
			{
				String   synchKey = recoding.getSynchKey();
				String[] codes    = synchKey.split(CODIFICATION_DELIMITER);

				boolean                  include        = false;
				Set<DepartmentsRecoding> includedValues = included.get(codes[0]);

				if (!(EMPTY_CODE.equals(codes[0]) || EMPTY_CODE.equals(codes[1])))
				{
					if (includedValues == null)
					{
						includedValues = new HashSet<DepartmentsRecoding>();
					}

					for (DepartmentsRecoding value : includedValues)
					{
						String[] savedCodes = value.getSynchKey().split(CODIFICATION_DELIMITER);
						String[] formatted  = Utils.formatCodesToErib(savedCodes[1].split(VSP_CODES_DELIMITER));

						if (Utils.concatCodes(formatted).equals(savedCodes[0]))
						{
							include = true;
							break;
						}
					}
				}

				if (!include)
				{
					String[] formatted = Utils.formatCodesToErib(codes[1].split(VSP_CODES_DELIMITER));
					if (Utils.concatCodes(formatted).equals(codes[0]))
					{
						exclude("Запись %s DATE_SUC=%2$td.%2$tm.%2$tY не будет добавлена поскольку найдены записи в которых TB_ERIB=TB_SPOOBK2, OSB_ERIB=OSB_SPOOBK2, OFFICE_ERIB=OFFICE_SPOOBK2.", includedValues.toArray(new DepartmentsRecoding[includedValues.size()]));
						include(true, recoding);
						continue;
					}
				}
				else
				{
					exclude("Запись %s DATE_SUC=%2$td.%2$tm.%2$tY не будет добавлена поскольку уже есть запись в которой TB_ERIB=TB_SPOOBK2, OSB_ERIB=OSB_SPOOBK2, OFFICE_ERIB=OFFICE_SPOOBK2.", recoding);
					continue;
				}

				Calendar dateSuc = null;
				for (DepartmentsRecoding value : includedValues)
				{
					if (value.getDateSuc() != null)
					{
						dateSuc = value.getDateSuc();
						break;
					}
				}

				if (recoding.getDateSuc() == null)
				{
					if (dateSuc == null)
					{
						include(false, recoding);
					}
					else
					{
						exclude("Запись %s DATE_SUC=%2$td.%2$tm.%2$tY не будет добавлена поскольку найдены дублирующие её записи.", recoding);
					}
				}
				else
				{
					if (dateSuc == null)
					{
						exclude("Запись %s DATE_SUC=%2$td.%2$tm.%2$tY не будет добавлена поскольку есть записи в которых параметр даты назначения правоприемника (DATE_SUC) заполнен.", includedValues.toArray(new DepartmentsRecoding[includedValues.size()]));
						include(true, recoding);
					}
					else
					{
						int comparison = recoding.getDateSuc().compareTo(dateSuc);
						if (comparison == 0)
						{
							include(false, recoding);
							continue;
						}

						if (comparison < 0)
						{
							exclude("Запись %s DATE_SUC=%2$td.%2$tm.%2$tY не будет добавлена поскольку есть записи в которых параметр даты назначения правоприемника (DATE_SUC) больше.", recoding);
							continue;
						}

						if (comparison > 0)
						{
							exclude("Запись %s DATE_SUC=%2$td.%2$tm.%2$tY не будет добавлена поскольку есть записи в которых параметр даты назначения правоприемника (DATE_SUC) больше.", includedValues.toArray(new DepartmentsRecoding[includedValues.size()]));
							include(true, recoding);
						}
					}
				}
			}

			Set<DepartmentsRecoding> includeIt = included.get(key);
			if (includeIt != null)
			{
				if (includeIt.size() > 1)
				{
					exclude("Запись %s DATE_SUC=%2$td.%2$tm.%2$tY не будет добавлена поскольку найдены дублирующие её записи.", includeIt.toArray(new DepartmentsRecoding[includeIt.size()]));
					included.remove(key);
				}
			}
		}
	}

	private void filterBySpoobkMatch() throws Exception
	{
		// 1. Сначала смотрим по кодам ЕРИБ.
		Map<String, Set<DepartmentsRecoding>> filteredByErib = new HashMap<String, Set<DepartmentsRecoding>>();

		Set<String> eribCodes = new HashSet<String>();
		for (String eribCode : source.keySet())
		{
			// Должно быть хотя бы что-то
			if (eribCode.equals(EMPTY_CODE))
			{
				continue;
			}

			String[] officeCodes = eribCode.split(VSP_CODES_DELIMITER);
			officeCodes = Utils.formatCodesToSpoobk(officeCodes);
			eribCodes.add(Utils.concatCodes(officeCodes));
		}

		Map<String, List<String>> byEribValues = new HashMap<String, List<String>>();
		for (String value : getDespatchFromSpoobk(eribCodes))
		{
			String[] codes     = value.split(CODIFICATION_DELIMITER);
			String[] formatted = Utils.formatCodesToErib(codes[0].split(VSP_CODES_DELIMITER));

			List<String> byEribValue = byEribValues.get( Utils.concatCodes(formatted) );
			if (byEribValue == null)
			{
				byEribValue = new ArrayList<String>();
				byEribValues.put(Utils.concatCodes(formatted), byEribValue);
			}

			byEribValue.add(codes.length == 1 ? null : codes[1]);
		}

		Iterator<String> sourceEribCodes = source.keySet().iterator();
		while (sourceEribCodes.hasNext())
		{
			String       key        = sourceEribCodes.next();
			List<String> despatches = byEribValues.get(key);

			// Не найдено ни одной записи в АС СПООБК - игнорируем запись, далее она будет проверяться по кодам СПООБК.
			if (despatches == null)
			{
				continue;
			}

			Set<DepartmentsRecoding> recodings = source.get(key);
			if (despatches.size() > 1)
			{
				exclude("Запись %s DATE_SUC=%2$td.%2$tm.%2$tY не будет добавлена поскольку в АС СПООБК найдено более одной записи соответствующей ей в кодификации ЕРИБ.", recodings.toArray(new DepartmentsRecoding[recodings.size()]) );
			}

			if (despatches.size() == 1)
			{
				String[] codes = Utils.formatCodesToSpoobk(key.split(VSP_CODES_DELIMITER));

				for (DepartmentsRecoding recoding : recodings)
				{
					recoding.setDateSuc(null);
					recoding.setDespatch(despatches.iterator().next());

					recoding.setTbSpoobk2    (codes[0]);
					recoding.setOsbSpoobk2   (codes.length < 2 ? "" : codes[1]);
					recoding.setOfficeSpoobk2(codes.length < 3 ? "" : codes[2]);
				}

				Set<DepartmentsRecoding> filtered = filteredByErib.get(key);
				if (filtered == null)
				{
					filtered = new HashSet<DepartmentsRecoding>();
					filteredByErib.put(key, filtered);
				}

				filtered.addAll(recodings);
			}

			sourceEribCodes.remove();
		}

		// 2. Теперь нужно искать по кодам СПООБК.
		Map<String, Set<DepartmentsRecoding>> filteredBySpoobk = new HashMap<String, Set<DepartmentsRecoding>>();
		Set<String> spoobkCodes = new HashSet<String>();
		for (Set<DepartmentsRecoding> values : source.values())
		{
			for (DepartmentsRecoding value : values)
			{
				spoobkCodes.add(value.getSynchKey().split(CODIFICATION_DELIMITER)[1]);
			}
		}

		Map<String, List<String>> bySpoobkValues = new HashMap<String, List<String>>();
		for (String res : getDespatchFromSpoobk(spoobkCodes))
		{
			String[] bySpoobkValue = res.split(CODIFICATION_DELIMITER);

			List<String> despatchCodes = bySpoobkValues.get(bySpoobkValue[0]);
			if (despatchCodes == null)
			{
				despatchCodes = new ArrayList<String>();
				bySpoobkValues.put(bySpoobkValue[0], despatchCodes);
			}

			despatchCodes.add(bySpoobkValue.length == 1 ? null : bySpoobkValue[1]);
		}

		Iterator<Set<DepartmentsRecoding>> sourceValuesSet = source.values().iterator();
		while (sourceValuesSet.hasNext())
		{
			for (DepartmentsRecoding recoding : sourceValuesSet.next())
			{
				String[]     codes      = recoding.getSynchKey().split(CODIFICATION_DELIMITER);
				List<String> despatches = bySpoobkValues.get(codes[1]);

				if (despatches == null)
				{
					exclude("Запись %s DATE_SUC=%2$td.%2$tm.%2$tY не будет добавлена поскольку в АС СПООБК не найдено ни одной записи соответствующей ей в кодификации СПООБК.", recoding);
				}
				else if (despatches.size() > 1)
				{
					exclude("Запись %s DATE_SUC=%2$td.%2$tm.%2$tY не будет добавлена поскольку в АС СПООБК найдено более одной записи соответствующей ей в кодификации СПООБК.", recoding);
				}
				else
				{
					recoding.setDespatch(despatches.iterator().next());

					Set<DepartmentsRecoding> filtered = filteredBySpoobk.get(codes[0]);
					if (filtered == null)
					{
						filtered = new HashSet<DepartmentsRecoding>();
						filteredBySpoobk.put(codes[0], filtered);
					}

					filtered.add(recoding);
				}
			}

			sourceValuesSet.remove();
		}

		source.putAll(filteredByErib);
		source.putAll(filteredBySpoobk);
	}

	private void fillRecordings() throws Exception
	{
		for (String key : included.keySet())
		{
			for (DepartmentsRecoding recoding : included.get(key))
			{
				addSynchronizedRecord(SynchronizeResultStatus.SUCCESS, recoding.getSynchKey(), null, String.format(" Запись %s DATE_SUC=%2$td.%2$tm.%2$tY подготовлена для загрузки в справочник.", recoding.getSynchKey(), recoding.getDateSuc()));
				recordings.add(recoding);
			}
		}
	}

	private void loadMissedDepartments() throws Exception
	{
		try
		{
			for (DepartmentsRecoding recoding : service.findDepartmentsForLoadToSpoobk(MultiBlockModeDictionaryHelper.getDBInstanceName()))
			{
				if (!source.containsKey(recoding.getSynchKey().split(CODIFICATION_DELIMITER)[0]))
				{
					toSource(recoding);
				}
			}
		}
		catch (Exception e)
		{
			addSynchronizedRecord(SynchronizeResultStatus.FAIL, null, "Во время загрузки справочника произошла ошибка.", "Произошла ошибка при загрузке недостающих записей из таблици департаментов.");
			throw e;
		}
	}

	private void filterByClosed()
	{
		for (String code : subBranches)
		{
			if (source.containsKey(code))
			{
				Set<DepartmentsRecoding> values = source.get(code);

				exclude("Запись %s DATE_SUC=%2$td.%2$tm.%2$tY не будет добавлена поскольку найдена соответствующая ей запись в справочнике CLOSESUBBRANCH.", values.toArray(new DepartmentsRecoding[values.size()]));
				source.remove(code);
			}
		}
	}

	private void addSynchronizedRecord(final SynchronizeResultStatus status, final Comparable synchKey, final String errMessage, final String logMessage)
	{
		if (StringHelper.isNotEmpty(errMessage))
		{
			result.addError(errMessage);
		}

		switch (status)
		{
			case FAIL:
			{
				log.error(logMessage);
				break;
			}

			case SUCCESS:
			{
				SynchronizeResultRecord record = new SynchronizeResultRecord();
				record.setStatus  (status);
				record.setSynchKey(synchKey);
				result.addResultRecord(record);

				log.info(logMessage);
				break;
			}
		}
	}

	private Set<String> findCodesContainsInDepartments(final Collection<String> codes) throws Exception
	{
		try
		{
			int size      = codes.size();
			int toIndex   = 0;
			int fromIndex = 0;

			List<String> list        = new ArrayList<String>(codes);
			Set<String>  queryResult = new HashSet<String>();
			do
			{
				toIndex = fromIndex + HIBERNATE_THRESHOLD < size ? fromIndex + HIBERNATE_THRESHOLD : size;

				queryResult.addAll( service.findCodesContainsInDepartments(list.subList(fromIndex, toIndex), MultiBlockModeDictionaryHelper.getDBInstanceName()) );

				fromIndex = toIndex;
			}
			while (fromIndex < size - 1);

			return queryResult;
		}
		catch (Exception e)
		{
			addSynchronizedRecord(SynchronizeResultStatus.FAIL, null, "Во время загрузки справочника произошла ошибка.", "Произошла ошибка при проверке наличия записи в таблице департаментов.");
			throw e;
		}
	}

	private List<String> getDespatchFromSpoobk(final Collection<String> codes) throws Exception
	{
		try
		{
			int size      = codes.size();
			if (size == 0)
			{
				return new ArrayList<String>();
			}

			int toIndex   = 0;
			int fromIndex = 0;

			List<String> list        = new ArrayList<String>(codes);
			List<String> queryResult = new ArrayList<String>();
			do
			{
				toIndex = fromIndex + HIBERNATE_THRESHOLD < size ? fromIndex + HIBERNATE_THRESHOLD : size;

				queryResult.addAll( service.getDespatchFromSpoobk(list.subList(fromIndex, toIndex), MultiBlockModeDictionaryHelper.getDBInstanceName()) );

				fromIndex = toIndex;
			}
			while (fromIndex < size - 1);

			return queryResult;
		}
		catch (Exception e)
		{
			addSynchronizedRecord(SynchronizeResultStatus.FAIL, null, "Загрузка справочника временно недоступна.", "Произошла ошибка при проверке наличия записи в АС СПООБК2.");
			throw e;
		}
	}

	private void exclude(String reason, DepartmentsRecoding... recodings)
	{
		if (recodings == null || recodings.length == 0)
		{
			return;
		}

		for (DepartmentsRecoding recoding : recodings)
		{
			addSynchronizedRecord(SynchronizeResultStatus.FAIL, recoding.getSynchKey(), null, String.format(reason, recoding.getSynchKey(), recoding.getDateSuc()));
		}
	}

	private void include(boolean needClear, DepartmentsRecoding... recoding)
	{
		if (recoding == null || recoding.length == 0)
		{
			return;
		}

		String   synch = recoding[0].getSynchKey();
		String[] codes = synch.split(CODIFICATION_DELIMITER);

		Set<DepartmentsRecoding> includedValues = included.get(codes[0]);
		if (includedValues == null)
		{
			includedValues = new HashSet<DepartmentsRecoding>();
			included.put(codes[0], includedValues);
		}

		if (needClear)
		{
			includedValues.clear();
		}

		Collections.addAll(includedValues, recoding);
	}

	private void toSource(DepartmentsRecoding recoding)
	{
		String[] eribCodes   = Utils.formatCodesToErib  (recoding.getTbErib(),    recoding.getOsbErib(),    recoding.getOfficeErib());
		String[] spoobkCodes = Utils.formatCodesToSpoobk(recoding.getTbSpoobk2(), recoding.getOsbSpoobk2(), recoding.getOfficeSpoobk2());

		String key = Utils.concatCodes(eribCodes);

		Set<DepartmentsRecoding> sourceValues = source.get(key);
		if (sourceValues == null)
		{
			sourceValues = new HashSet<DepartmentsRecoding>();
			source.put(key, sourceValues);
		}

		recoding.setTbErib(eribCodes[0]);
		recoding.setOsbErib(eribCodes[1]);
		recoding.setOfficeErib(eribCodes[2]);
		recoding.setTbSpoobk2(spoobkCodes[0]);
		recoding.setOsbSpoobk2(spoobkCodes[1]);
		recoding.setOfficeSpoobk2(spoobkCodes[2]);

		sourceValues.add(recoding);
	}

	private static class Utils
	{
		static String[] formatCodesToSpoobk(String... codes)
		{
			String[] prepared = formatCodesToErib(codes);

			return new String[]
			{
				StringHelper.isNotEmpty(prepared[0]) ? StringHelper.addLeadingZeros(prepared[0], 2) : prepared[0],
				StringHelper.isNotEmpty(prepared[1]) ? StringHelper.addLeadingZeros(prepared[1], 4) : prepared[1],
				StringHelper.isNotEmpty(prepared[2]) ? StringHelper.addLeadingZeros(prepared[2], 5) : prepared[2]
			};
		}

		static String[] formatCodesToErib(String... codes)
		{
			String tb     = StringHelper.getEmptyIfNull(codes[0]).trim();
			String osb    = (codes.length < 2 ? "" : codes[1]).trim();
			String office = (codes.length < 3 ? "" : codes[2]).trim();

			return new String[]
			{
				StringUtils.stripStart(tb,     "0"),
				StringUtils.stripStart(osb,    "0"),
				StringUtils.stripStart(office, "0")
			};
		}

		static String concatCodes(String... codes)
		{
			StringBuilder builder = new StringBuilder();
			builder.append(codes.length == 0 ? "" : codes[0]);
			builder.append("|");
			builder.append(codes.length  < 2 ? "" : codes[1]);
			builder.append("|");
			builder.append(codes.length  < 3 ? "" : codes[2]);

			return builder.toString();
		}
	}

	private class DepartmentsRecodingSaxFilter extends XMLFilterImpl
	{
		private final static String ELEMENT_TABLE   = "table";
		private final static String ELEMENT_FIELD   = "field";
		private final static String ELEMENT_RECORD  = "record";

		private final static String ATTRIBUTE_NAME  = "name";
		private final static String ATTRIBUTE_VALUE = "value";

		private DepartmentsRecoding recoding;

		private String[]            codes = null;
		private boolean             successor;
		private boolean             closeSubBranch;

		private DepartmentsRecodingSaxFilter(XMLReader parent)
		{
			super(parent);
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException
		{
			if (stopParse)
			{
				throw new StopSAXParseException();
			}

			if (ELEMENT_TABLE.equals(qName))
			{
				if (atts.getValue(ATTRIBUTE_NAME).equals("SUCCESSOR"))
				{
					successor = true;
				}

				if (atts.getValue(ATTRIBUTE_NAME).equals("CLOSESUBBRANCH"))
				{
					closeSubBranch = true;
				}
			}

			if (successor)
			{
				if (ELEMENT_RECORD.equals(qName))
				{
					recoding = new DepartmentsRecoding();
				}

				if (ELEMENT_FIELD.equals(qName))
				{
					String fieldName  = atts.getValue(ATTRIBUTE_NAME);
					String fieldValue = atts.getValue(ATTRIBUTE_VALUE);

					if (fieldName.equals("TERBANK_CL"))
					{
						recoding.setTbSpoobk2(fieldValue);
					}
					else if (fieldName.equals("BRANCH_CL"))
					{
						recoding.setOsbSpoobk2(fieldValue);
					}
					else if (fieldName.equals("SUBBRANCH_CL"))
					{
						recoding.setOfficeSpoobk2(fieldValue);
					}
					else if (fieldName.equals("TERBANK_SUC"))
					{
						recoding.setTbErib(fieldValue);
					}
					else if (fieldName.equals("BRANCH_SUC"))
					{
						recoding.setOsbErib(fieldValue);
					}
					else if (fieldName.equals("SUBBRANCH_SUC"))
					{
						recoding.setOfficeErib(fieldValue);
					}
					else if (fieldName.equals("DATE_SUC"))
					{
						if (StringHelper.isNotEmpty(fieldValue))
						{
							try
							{
								recoding.setDateSuc(DateHelper.parseCalendar(fieldValue, "yyyy.MM.dd"));
							}
							catch (ParseException e)
							{
								log.error("Произошла ошибка при попытке разобрать значение поля DATE_SUC.", e);
								throw new SAXException(e);
							}
						}
					}
				}
			}

			if (closeSubBranch)
			{
				if (ELEMENT_RECORD.equals(qName))
				{
					codes = new String[] {"" , "", ""};
				}

				if (ELEMENT_FIELD.equals(qName))
				{
					String fieldName  = atts.getValue(ATTRIBUTE_NAME);
					String fieldValue = atts.getValue(ATTRIBUTE_VALUE);

					if (fieldName.equals("TERBANK"))
					{
						codes[0] = fieldValue;
					}
					else if (fieldName.equals("BRANCH"))
					{
						codes[1] = fieldValue;
					}
					else if (fieldName.equals("SUBBRANCH"))
					{
						codes[2] = fieldValue;
					}
				}
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException
		{
			if (successor)
			{
				if (ELEMENT_TABLE.equals(qName))
				{
					successor = false;
				}

				if (ELEMENT_RECORD.equals(qName))
				{
					toSource(recoding);
				}
			}

			if (closeSubBranch)
			{
				if (ELEMENT_TABLE.equals(qName))
				{
					closeSubBranch = false;
				}

				if (ELEMENT_RECORD.equals(qName))
				{
					subBranches.add( Utils.concatCodes(Utils.formatCodesToErib(codes)) );
				}
			}
		}
	}
}
