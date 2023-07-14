package com.rssl.phizic.business.dictionaries.payment.services.replication;

import com.rssl.phizic.business.dictionaries.payment.services.PaymentService;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.XmlReplicaSourceBase;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLFilter;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLFilterImpl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author khudyakov
 * @ created 03.12.2010
 * @ $Author$
 * @ $Revision$
 */
public class PaymentServicesXmlSourceSAX extends XmlReplicaSourceBase
{
	private static final String DICTIONARY_NAME = "GroupService";

	private List<PaymentService> source = new ArrayList<PaymentService>();

	protected void clearData ()
	{
		source.clear();
	}

	protected InputStream getDefaultStream()
	{
		//берем с формы
		return null;
	}

	protected XMLFilter getDefaultFilter () throws ParserConfigurationException, SAXException
	{
		return new SaxFilter(XmlHelper.newXMLReader());
	}

	public XMLReader chainXMLReader(XMLReader xmlReader)
	{
		return new SaxFilter(super.chainXMLReader(xmlReader));
	}

	public void initialize(GateFactory factory) throws GateException {}

	public Iterator iterator() throws GateException, GateLogicException
	{
		internalParse();
		if(!source.isEmpty())
		{
			Collections.sort(source, new PaymentServicesComparator());
		}

		return source.iterator();
	}

	private class SaxFilter extends XMLFilterImpl
	{
		private Boolean paymentServicesSection = false;
		private PaymentService paymentService;

		public SaxFilter ( XMLReader parent )
		{
			super(parent);
		}

		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
		{
			if (qName.equalsIgnoreCase("table") && DICTIONARY_NAME.equals(attributes.getValue("name")))
			{
				paymentServicesSection = true;
			}
			else if (paymentServicesSection)
			{
				if (qName.equalsIgnoreCase("record"))
				{
					paymentService = new PaymentService();
				}

				if (qName.equalsIgnoreCase("field"))
				{
					String name  = attributes.getValue("name");
					String value = attributes.getValue("value");

					if ("GrpServiceCode".equals(name))
					{
						paymentService.setSynchKey(value);
						return;
					}
					if("GrpServiceName".equals(name))
					{
						paymentService.setName(value);
						return;
					}
					if("GrpServiceATM".equals(name))
					{
						paymentService.setDescription(value);
						return;
					}
					if("GrpServiceFile".equals(name))
					{
						if (value != null)
							paymentService.setDefaultImage("/payment_service/"+value+".jpg");
						return;
					}
				}
			}

			if (!paymentServicesSection)
			{
				super.startElement(uri, localName, qName, attributes);
			}
		}

		public void endElement(String uri, String localName, String qName) throws SAXException
		{
			if (qName.equalsIgnoreCase("table"))
			{
				paymentServicesSection = false;
			}

			if (paymentServicesSection)
			{
				if (qName.equalsIgnoreCase("table"))
				{
					paymentServicesSection = false;
				}
				else if (qName.equalsIgnoreCase("record"))
				{
					source.add(paymentService);
				}
			}
			else
			{
				super.endElement(uri, localName, qName);
			}
		}
	}
}
