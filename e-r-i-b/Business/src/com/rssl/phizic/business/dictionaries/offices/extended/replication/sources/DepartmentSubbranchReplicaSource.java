package com.rssl.phizic.business.dictionaries.offices.extended.replication.sources;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment;
import com.rssl.phizic.business.dictionaries.offices.extended.replication.DepartmentFilter;
import com.rssl.phizic.business.dictionaries.offices.extended.replication.DepartmentLevelComparator;
import com.rssl.phizic.business.dictionaries.offices.extended.replication.ReplicationDepartmentsTaskResult;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.XmlReplicaSourceBase;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.collections.CollectionUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLFilter;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLFilterImpl;

import java.io.InputStream;
import java.util.*;
import javax.xml.parsers.ParserConfigurationException;

import static com.rssl.phizic.business.dictionaries.offices.extended.replication.Constants.*;

/**
 * @author niculichev
 * @ created 02.09.13
 * @ $Author$
 * @ $Revision$
 */
public class DepartmentSubbranchReplicaSource extends XmlReplicaSourceBase
{
	private static final DepartmentService departmentService = new DepartmentService();

	private InputStream inputStream;
	private DepartmentFetcher fetcher;

	public DepartmentSubbranchReplicaSource(InputStream inputStream, List<Long> parentIds, ReplicationDepartmentsTaskResult taskResult) throws BusinessException, BusinessLogicException
	{
		List<Code> codes = departmentService.getCodesByDepartmentIds(parentIds);
		fetcher = new DepartmentFetcher(taskResult);

		// если список пустой, парсить не нужно
		if(CollectionUtils.isEmpty(codes))
			return;

		fetcher.setFilter(new DepartmentFilter(codes));
		this.inputStream = inputStream;

		try
		{
			internalParse();
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}

	@Override
	protected void clearData()
	{
	}

	@Override
	protected InputStream getDefaultStream()
	{
		return inputStream;
	}

	@Override
	protected XMLFilter getDefaultFilter() throws ParserConfigurationException, SAXException
	{
		return new SaxFilter(XmlHelper.newXMLReader());
	}

	public void initialize(GateFactory factory) throws GateException
	{
	}

	public Iterator<ExtendedDepartment> iterator() throws GateException, GateLogicException
	{
		List<ExtendedDepartment> departments = getDepartments();

		if(CollectionUtils.isNotEmpty(departments))
			Collections.sort(departments, new DepartmentLevelComparator());

		return departments.iterator();
	}

	/**
	 * @return список построенных подразделений
	 */
	List<ExtendedDepartment> getDepartments()
	{
		return fetcher.getResult();
	}

	/**
	 * @return список ошибок
	 */
	public  Map<Code, String> getErrors()
	{
		return fetcher.getErrors();
	}


	//////////////////////////////////////////////////////////////////////////////////////
	private enum State
	{
		/**
		 * Поиск нужной таблицы
		 */
		FIND,

		/**
		 * Обработка нужной таблицы
		 */
		PROCESS,
	}

	private class SaxFilter extends XMLFilterImpl
	{
		private Map<String, String> fieldsRecord;
		private State state = State.FIND;

		private SaxFilter(XMLReader parent)
		{
			super(parent);
		}

		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
		{
			// нашли нужную таблицу
			if(state == State.FIND && qName.equals("table") && SUBBRANCH_TABLE_NAME.equals(attributes.getValue("name")))
			{
				state = State.PROCESS;
				return;
			}

			if(state == State.PROCESS)
			{
				if(RECORD_TAG_NAME.equals(qName))
					fieldsRecord = new HashMap<String, String>();

				else if(FIELD_TAG_NAME.equals(qName))
					fieldsRecord.put(attributes.getValue("name"), attributes.getValue("value"));
			}
		}

		public void endElement(String uri, String localName, String qName) throws SAXException
		{
			if(state != State.PROCESS)
				return;

			// если получили достаточно данных прекращаем парсинг
			if(TABLE_TAG_NAME.equals(qName))
				prematureComplete();

			if(RECORD_TAG_NAME.equals(qName))
			{
				fetcher.addDepartment(fieldsRecord);
				return;
			}
		}
	}
}
