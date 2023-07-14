package com.rssl.phizic.business.dictionaries.regions;

import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockDictionaryRecordBase;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;

/**
 * @author khudyakov
 * @ created 02.12.2009
 * @ $Author$
 * @ $Revision$
 */

public class Region extends MultiBlockDictionaryRecordBase implements DictionaryRecord
{
	private Long   id;
	private Comparable synchKey;
	private String name;
	private Region parent;
	private String codeTB;
	private String providerCodeMAPI;
	private String providerCodeATM;

	/**
	 * Идентификатор
	 * @return
	 */
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * Идентификатор региона,
	 * вводимый сотрудником
	 * @return
	 */
	public Comparable getSynchKey()
	{
		return synchKey;
	}

	public void setSynchKey(Comparable<? extends String> synchKey)
	{
		this.synchKey = synchKey;
	}

	/**
	 * Наименование региона
	 * @return
	 */
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Наследник/родитель
	 * @return
	 */
	public Region getParent()
	{
		return parent;
	}

	public void setParent(Region parent)
	{
		this.parent = parent;
	}

	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Region that = (Region) o;
		if (!id.equals(that.id))
			return false;

		return true;
	}

	public int hashCode()
	{
		return id.hashCode();
	}

	public String getCodeTB()
	{
		return codeTB;
	}

	public void setCodeTB(String codeTB)
	{
		this.codeTB = codeTB;
	}

	/**
	 * Код поставщика услуг для оплаты через МП (mAPI)
	 * @return
	 */
	public String getProviderCodeMAPI()
	{
		return providerCodeMAPI;
	}

	public void setProviderCodeMAPI(String providerCodeMAPI)
	{
		this.providerCodeMAPI = providerCodeMAPI;
	}

	/**
	 * Код поставщика услуг для оплаты через УС (atmAPI)
	 * @return
	 */
	public String getProviderCodeATM()
	{
		return providerCodeATM;
	}

	public void setProviderCodeATM(String providerCodeATM)
	{
		this.providerCodeATM = providerCodeATM;
	}

	public void updateFrom(DictionaryRecord that)
	{
		Region newRegion = (Region)that;
		this.setSynchKey(newRegion.getSynchKey());
		this.setName(newRegion.getName());
		this.setParent(newRegion.getParent());
		this.setCodeTB(newRegion.getCodeTB());
	}
}
