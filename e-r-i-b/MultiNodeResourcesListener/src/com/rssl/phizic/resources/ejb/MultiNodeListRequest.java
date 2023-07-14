package com.rssl.phizic.resources.ejb;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.mail.Constants;
import com.rssl.phizic.dataaccess.query.OrderParameter;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.utils.xml.xstream.XStreamSerializer;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 25.05.14
 * @ $Author$
 * @ $Revision$
 *
 * ������ ������� �� ��������� ������ �����.
 */
public class MultiNodeListRequest
{
	private Map<String,Object> parameters;
	private Integer maxResults = null;
	private Integer firstResult = null;
	private List<OrderParameter> orderParameters = new ArrayList<OrderParameter>();

	/**
	 * �����������
	 * @param request - ������
	 * @throws SystemException
	 */
	public MultiNodeListRequest(String request) throws SystemException
	{
		try
		{
			Document dom = XmlHelper.parse(request);
			String parametersStr = XmlHelper.getSimpleElementValue(dom.getDocumentElement(), Constants.PARAMETERS_TAG_NAME);
			String orderParametersStr = XmlHelper.getSimpleElementValue(dom.getDocumentElement(), Constants.ORDER_PARAMETERS_TAG_NAME);
			String firstResultStr = XmlHelper.getSimpleElementValue(dom.getDocumentElement(), Constants.FIRST_RESULT_TAG_NAME);
			String maxResultsStr = XmlHelper.getSimpleElementValue(dom.getDocumentElement(), Constants.MAX_RESULTS_TAG_NAME);
			this.parameters = (Map<String,Object>)XStreamSerializer.deserialize(parametersStr);
			if(StringHelper.isNotEmpty(firstResultStr))
				this.firstResult = Integer.valueOf(firstResultStr);
			if(StringHelper.isNotEmpty(maxResultsStr))
				this.maxResults = Integer.valueOf(maxResultsStr);
			if(StringHelper.isNotEmpty(orderParametersStr))
				this.orderParameters = (List<OrderParameter>)XStreamSerializer.deserialize(orderParametersStr);
		}
		catch (Exception e)
		{
			throw new SystemException(e);
		}
	}

	/**
	 * @return ��������� �������
	 */
	public Map<String, Object> getParameters()
	{
		return parameters;
	}

	/**
	 * @return ���������� ��������� � ������
	 */
	public Integer getMaxResults()
	{
		return maxResults;
	}

	/**
	 * @return �����
	 */
	public Integer getFirstResult()
	{
		return firstResult;
	}

	/**
	 * @return ��������� ���������� ����������
	 */
	public List<OrderParameter> getOrderParameters()
	{
		return orderParameters;
	}
}
