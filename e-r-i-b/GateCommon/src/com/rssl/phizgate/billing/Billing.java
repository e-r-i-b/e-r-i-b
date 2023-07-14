package com.rssl.phizgate.billing;

import com.rssl.phizic.gate.Routable;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.utils.BeanHelper;

/**
 * @author akrenev
 * @ created 11.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class Billing implements com.rssl.phizic.gate.dictionaries.billing.Billing, Routable
{
	private Long id;
	private Comparable synchKey;  //UUID адаптера
	private String code;
	private String name;
	private boolean needUploadJBT;  //необходимость выгрузки

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

	public void updateFrom(DictionaryRecord that)
	{
		BeanHelper.copyProperties(this, that);
	}

	public void storeRouteInfo(String info)
	{
		this.synchKey = info;
	}

	public String restoreRouteInfo()
	{
		return (String) synchKey;
	}

	public String removeRouteInfo()
	{
		synchKey = null;
		return (String) synchKey;
	}
}
