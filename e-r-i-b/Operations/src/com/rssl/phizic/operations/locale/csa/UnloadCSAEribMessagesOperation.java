
package com.rssl.phizic.operations.locale.csa;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.locale.entities.ERIBStaticMessage;
import com.rssl.phizic.locale.services.EribStaticMessageService;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.locale.LocaleMessageTags;
import com.rssl.phizic.operations.locale.UnloadEribMessagesOperation;
import com.rssl.phizic.utils.StringHelper;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.StringWriter;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Выгрузка статических текстовок в цса
 * @author koptyaev
 * @ created 18.09.2014
 * @ $Author$
 * @ $Revision$
 */
public class UnloadCSAEribMessagesOperation extends UnloadEribMessagesOperation
{
	private static final String CSA_INSTANCE_NAME = "CSA";

	@Override
	protected String getInstanceName()
	{
		return CSA_INSTANCE_NAME;
	}
}
