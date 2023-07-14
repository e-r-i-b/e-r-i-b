package com.rssl.phizic.business.statemachine;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.payments.forms.PaymentFormService;
import com.rssl.phizic.business.payments.forms.meta.MetadataBean;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author Erkin
 * @ created 20.04.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Парсер для машин состояний платёжных документов
 */
public class PaymentStateMachineParser extends StateMachineParser
{
	private static final PaymentFormService paymentFormService = new PaymentFormService();

	@Override
	protected void internalParse(String stateMachineXmlFile) throws BusinessException
	{
		try
		{
        	Document config = XmlHelper.loadDocumentFromResource(stateMachineXmlFile);
			Element root = config.getDocumentElement();

			parseStateMachineConfig(root);

			for (MetadataBean bean: paymentFormService.getAllForms())
			{
				if (StringHelper.isEmpty(bean.getStateMachine()))
					continue;

				Element element = XmlHelper.parse(bean.getStateMachine()).getDocumentElement();
				parseStateMachine(bean.getName(), element);
			}
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
		catch (SAXException e)
		{
			throw new BusinessException(e);
		}
		catch (ParserConfigurationException e)
		{
			throw new BusinessException(e);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
