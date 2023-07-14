package com.rssl.phizic.web.security.node;

import com.rssl.phizic.operations.csaadmin.node.ChangeNodeOperation;
import com.rssl.phizic.operations.csaadmin.node.NodeNotAvailableException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mihaylov
 * @ created 25.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Экшен принудительной смены блока
 */
public class ChangeNodeAction extends SelfChangeNodeAction
{
	private static final String PARAMETERS_NAME = "parameters";

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ChangeNodeForm frm = (ChangeNodeForm) form;
		ChangeNodeOperation operation = createOperation(ChangeNodeOperation.class);
		Map<String, String> parameters = getParameters(request);
		try
		{
			operation.initialize(frm.getNodeId(),frm.getAction(), parameters);
			operation.changeNode();
			closeSession(request);
			return new ActionRedirect(operation.getNodeUrl());
		}
		catch (NodeNotAvailableException e)
		{
			return getNodeNotAvailableForward(e.getNodeInfo());
		}
	}

	@Deprecated
	//Десериализацию параметров должен делать стратс, однако сейчас он некоррктно разбирает параметры типа parameters(field(name))
	//Необходимо либо заменять commons-beanutils, или делать надстройку для нашей десериализации.
	private Map<String,String> getParameters(HttpServletRequest request)
	{
		Map<String,String> resultMap = new HashMap<String,String>();
		Map<String,String> requestParameters = request.getParameterMap();
		for(String key: requestParameters.keySet())
		{
			if(key.startsWith(PARAMETERS_NAME))
			{
				int startKey = key.indexOf(PropertyUtils.MAPPED_DELIM);
				if(startKey >=0)
				{
					int endKey = key.lastIndexOf(PropertyUtils.MAPPED_DELIM2);
					String resultKey = key.substring(startKey + 1, endKey);
					resultMap.put(resultKey,request.getParameter(key));
				}
			}
		}
		return resultMap;
	}
}
