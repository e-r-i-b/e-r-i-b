package com.rssl.phizic.auth.modes;

import java.util.Map;
import java.util.HashMap;

/**
 * Author: Moshenko
 * Date: 30.04.2010
 * Time: 10:12:34
 */
public class PreConfirmObject
{
	private Map<String,Object> preConfimParamMap;

	PreConfirmObject(String param,Object value)
	{
        Map<String,Object> mp= new HashMap<String,Object>();
		mp.put(param,value);
    	setPreConfimParamMap(mp);
	}

	public PreConfirmObject(Map<String,Object> paramMap)
	{
    	setPreConfimParamMap(paramMap);
	}

	public Map<String, Object> getPreConfimParamMap()
	{
		return preConfimParamMap;
	}

	public void setPreConfimParamMap(Map<String, Object> preConfimParamMap)
	{
		this.preConfimParamMap = preConfimParamMap;
	}

	public Object getPreConfirmParam(String param)
	{
		   return preConfimParamMap.get(param);
	}

	public void setPreConfirmParam(String param,Object value)
	{
		   preConfimParamMap.put(param,value);
	}

}
