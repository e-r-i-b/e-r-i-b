package com.rssl.phizic.business.filters;

import com.rssl.phizic.test.BusinessTestCaseBase;

import java.util.Map;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * @author mihaylov
 * created 15.10.2008
 * @ $Author$
 * @ $Revision$
 */
public class FilterParametersServiceTest extends BusinessTestCaseBase
{
	public void testFilterParameters() throws Exception
	{
		FilterParametersByUrl fp = new FilterParametersByUrl();
		fp.setUrl("/url.do");
		fp.setSessionId("123123");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("name;","Ivan");
		map.put("surName","Pet;rov");
		map.put("patrName",null);
		fp.setParameters(map);




		FilterParametersByUrl fp2 = new FilterParametersByUrl();
		fp2.setUrl("/url2.do");
		fp2.setSessionId("123123");
		map.clear();
		map.put("name;","Igor");
		map.put("surName","Pet;rov");
		map.put("patrName","23");
		fp2.setParameters(map);



		FilterParametersService fps = new FilterParametersService();
		fps.addOrUpdate(fp);
		fp.setParameters(map);
		fps.addOrUpdate(fp);
		fps.addOrUpdate(fp2);

		FilterParametersByUrl fp3 = new FilterParametersByUrl();
		fp3 = fps.getBySessionIdAndUrl(fp.getSessionId(),fp.getUrl());
		Map<String,Object> params = new HashMap<String, Object>();
		params = fp3.getParameters();


		fps.addOrUpdate(fp);
		
		fps.delete(fp);
		fps.delete(fp2);
	}
}
