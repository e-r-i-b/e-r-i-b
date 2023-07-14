package com.rssl.phizic.business.dictionaries.billing;

import com.rssl.phizic.dictionaries.synchronization.MultiBlockDictionaryRecord;
import com.rssl.phizic.gate.Routable;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizicgate.manager.services.IDHelper;

/**
 * @author akrenev
 * @ created 11.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class Billing implements com.rssl.phizic.gate.dictionaries.billing.Billing, Routable, MultiBlockDictionaryRecord
{
	private Long id;
	private Comparable synchKey;  //UUID адаптера
	private String code;
	private String name;
	private boolean needUploadJBT;  //необходимость выгрузки
	private String adapterUUID;
	private TemplateState templateState; //состояение шаблонов

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public boolean isNeedUploadJBT()
	{
		return needUploadJBT;
	}

	public void setNeedUploadJBT(boolean needUploadJBT)
	{
		this.needUploadJBT = needUploadJBT;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Comparable getSynchKey()
	{
		return synchKey;
	}

	public void setSynchKey(Comparable synchKey)
	{
		this.synchKey = synchKey;
	}

	public TemplateState getTemplateState()
	{
		return templateState;
	}

	public void setTemplateState(TemplateState templateState)
	{
		this.templateState = templateState;
	}

	public void updateFrom(DictionaryRecord that)
	{
		BeanHelper.copyProperties(this, that);
	}

	public void storeRouteInfo(String info)
	{
		synchKey = IDHelper.storeRouteInfo((String) synchKey, info);
	}

	public String restoreRouteInfo()
	{
		return IDHelper.restoreRouteInfo((String) synchKey);
	}

	public String removeRouteInfo()
	{
		String info = IDHelper.restoreRouteInfo((String) synchKey);
		setSynchKey(IDHelper.restoreOriginalId((String) synchKey));
		return info;
	}

	public void setAdapterUUID(String adapterUUID)
	{
		this.adapterUUID = adapterUUID;
		removeRouteInfo();
		storeRouteInfo(adapterUUID);
	}

	public String getAdapterUUID()
	{
		return adapterUUID;
	}

	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Billing that = (Billing) o;
		if (!id.equals(that.id))
			return false;

		return true;
	}

	public int hashCode()
	{
		return id.hashCode();
	}

	public String getMultiBlockRecordId()
	{
		return synchKey.toString();
	}
}
