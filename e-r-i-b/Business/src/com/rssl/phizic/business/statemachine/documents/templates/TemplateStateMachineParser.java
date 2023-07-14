package com.rssl.phizic.business.statemachine.documents.templates;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.payments.forms.PaymentFormService;
import com.rssl.phizic.business.payments.forms.meta.MetadataBean;
import com.rssl.phizic.business.statemachine.StateMachineParser;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author khudyakov
 * @ created 25.02.14
 * @ $Author$
 * @ $Revision$
 */
class TemplateStateMachineParser extends StateMachineParser
{
	private static final PaymentFormService paymentFormService = new PaymentFormService();

	@Override
	protected void internalParse(String stateMachineXmlFile) throws BusinessException
	{
		try
		{
			for (MetadataBean bean: paymentFormService.getAllForms())
			{
				if (StringHelper.isEmpty(bean.getTemplateStateMachine()))
				{
					continue;
				}

				Element element = XmlHelper.parse(bean.getTemplateStateMachine()).getDocumentElement();
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
