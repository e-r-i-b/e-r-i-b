package com.rssl.phizic.test.way4u;

import com.rssl.phizic.gate.bankroll.generated.impl.runtime.Util;
import com.rssl.phizic.utils.ListUtil;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import javax.xml.transform.TransformerException;

/**
 * @author krenev
 * @ created 03.10.2013
 * @ $Author$
 * @ $Revision$
 */

class ResponseBuilderBase
{
	protected static final List<String> WARNING_CODES = ListUtil.fromArray(new String[]{"2600", "2610", "2620", "50", "8", "10"});
	protected static final String SUCCESS_CODE = "0";

	protected void setResponseCodes(Element root, String errorCode) throws TransformerException
	{
		root.setAttribute("resp_code", errorCode);
		root.setAttribute("direction", "Rs");
		Element information = XmlHelper.selectSingleNode(root, "/UFXMsg/MsgData/Information");
		if (SUCCESS_CODE.equals(errorCode))
		{
			root.setAttribute("resp_class", "I");
		}
		else if (WARNING_CODES.contains(errorCode))
		{
			root.setAttribute("resp_class", "W");
		}
		else
		{
			root.setAttribute("resp_class", "E");
		}

		if (information == null)
		{
			return;
		}

		Element status = XmlHelper.appendSimpleElement(information, "Status");
		XmlHelper.appendSimpleElement(status, "RespCode", errorCode);
		if (SUCCESS_CODE.equals(errorCode))
		{
			XmlHelper.appendSimpleElement(status, "RespClass", "Information");
			XmlHelper.appendSimpleElement(status, "RespText", "Successfully processed.");
		}
		else if (WARNING_CODES.contains(errorCode))
		{
			XmlHelper.appendSimpleElement(status, "RespClass", "Warning");
			XmlHelper.appendSimpleElement(status, "RespText", "Предупреждение " + errorCode);
		}
		else
		{
			XmlHelper.appendSimpleElement(status, "RespClass", "Error");
			XmlHelper.appendSimpleElement(status, "RespText", "Ошибка " + errorCode);
		}

	}
}
