package com.rssl.phizic.business.ant.pfp.dictionary;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.pfp.PFPDictionaryServiceBase;
import com.rssl.phizic.business.dictionaries.pfp.common.PFPDictionaryRecord;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.ReplicaDestination;
import com.rssl.phizic.gate.dictionaries.SynchKeyComparator;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.*;

/**
 * @author akrenev
 * @ created 11.09.2012
 * @ $Author$
 * @ $Revision$
 *
 * базовый класс взаимодействия хранилищем данных
 */
public class PFPDictionaryReplicaDestination implements ReplicaDestination<PFPDictionaryRecord>
{
	protected static final PFPDictionaryServiceBase simpleService = new PFPDictionaryServiceBase();

	private boolean deleteUnknownProduct;            //удалять ли неизвестные записи
	private List<PFPDictionaryRecord> dictionary;   //сохраненный справочник
	private List<PFPDictionaryRecord> removedRecords = new ArrayList<PFPDictionaryRecord>();
	private String dbInstanceName; //имя инстанса БД

	/**
	 * @param dictionary справочник пфп
	 * @param dbInstanceName имя инстанса БД
	 */
	public PFPDictionaryReplicaDestination(Collection<PFPDictionaryRecord> dictionary, String dbInstanceName)
	{
		this.dbInstanceName = dbInstanceName;
		this.dictionary = new ArrayList<PFPDictionaryRecord>(dictionary);
		Collections.sort(this.dictionary, new SynchKeyComparator());
	}

	protected String getInstanceName()
	{
		return dbInstanceName;
	}

	/**
	 * задать необходимость удалять неизвестные записи
	 * @param deleteUnknownProduct удалять ли неизвестные записи
	 */
	public void setDeleteUnknownProduct(boolean deleteUnknownProduct)
	{
		this.deleteUnknownProduct = deleteUnknownProduct;
	}

	/**
	 * @return список удаленных сущностей
	 */
	public List<PFPDictionaryRecord> getRemovedRecords()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return removedRecords;
	}

	public void add(PFPDictionaryRecord newValue) throws GateException
	{
		try
		{
			simpleService.add(((PFPDictionaryRecordWrapper)newValue).getRecord(), getInstanceName());
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}

	public void update(PFPDictionaryRecord oldValue, PFPDictionaryRecord newValue) throws GateException
	{
		try
		{
			//получаем новые данные
			PFPDictionaryRecordWrapper wrapper = (PFPDictionaryRecordWrapper) newValue;
			PFPDictionaryRecord pfpDictionaryRecord = wrapper.getRecord();

			//обновляем сущность
			oldValue.updateFrom(pfpDictionaryRecord);
			simpleService.update(oldValue, getInstanceName());

			//добавляем идентификатор в новую сущность
			//необходимо при добавлении комплексных продуктов
			pfpDictionaryRecord.setId(oldValue.getId());
			pfpDictionaryRecord.setUuid(oldValue.getUuid());
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}

	protected void doRemove(PFPDictionaryRecord oldValue)
	{
		removedRecords.add(oldValue);
	}

	public void remove(PFPDictionaryRecord oldValue)
	{
		if (deleteUnknownProduct)
			doRemove(oldValue);
	}

	@SuppressWarnings({"NoopMethodInAbstractClass"})
	public void initialize(GateFactory factory){}

	public Iterator<PFPDictionaryRecord> iterator() throws GateException, GateLogicException
	{
		return dictionary.iterator();
	}

	public List<String> getErrors()
	{
		return Collections.emptyList();
	}

	@SuppressWarnings({"NoopMethodInAbstractClass"})
	public void close(){}
}
