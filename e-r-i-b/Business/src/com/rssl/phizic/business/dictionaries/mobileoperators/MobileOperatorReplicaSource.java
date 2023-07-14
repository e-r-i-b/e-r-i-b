package com.rssl.phizic.business.dictionaries.mobileoperators;

import com.rssl.phizic.business.Constants;
import com.rssl.phizic.business.mobileOperators.MobileOperator;
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
import java.util.Iterator;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author Dorzhinov
 * @ created 05.10.2011
 * @ $Author$
 * @ $Revision$
 */
public class MobileOperatorReplicaSource extends XmlReplicaSourceBase
{
	private List<MobileOperator> mobileOperators = new ArrayList<MobileOperator>();

	protected InputStream getDefaultStream()
	{
		return getResourceReader(Constants.DEFAULT_FILE_NAME);
	}
	
	protected XMLFilter getDefaultFilter() throws ParserConfigurationException, SAXException
	{
		return new SaxFilter(XmlHelper.newXMLReader());
	}

	public XMLReader chainXMLReader(XMLReader xmlReader)
	{
		return new SaxFilter(super.chainXMLReader(xmlReader));
	}

	public void initialize(GateFactory factory) throws GateException
	{
	}

	public Iterator iterator() throws GateException, GateLogicException
	{
		internalParse();
		return mobileOperators.iterator();
	}

	protected void clearData()
	{
		mobileOperators.clear();
	}

	private class SaxFilter extends XMLFilterImpl
	{
		private static final String SECTION_NAME = "MB_OPERATORS";
		private Boolean insideSection = false;
		private MobileOperator tmpMO;

		public SaxFilter ( XMLReader parent )
		{
			super(parent);
		}

		public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException
		{
			if("table".equalsIgnoreCase(qName) && SECTION_NAME.equalsIgnoreCase(atts.getValue("name")))
				insideSection = true;
			else if(insideSection)
				if("record".equalsIgnoreCase(qName))
					tmpMO = new MobileOperator();
				else if("field".equalsIgnoreCase(qName))
				{
					if(tmpMO == null)
						return;
					String name = atts.getValue("name");
					if("ID".equalsIgnoreCase(name))
						tmpMO.setCode(Long.valueOf(atts.getValue("value")));
					if("NAME".equalsIgnoreCase(name))
						tmpMO.setName(atts.getValue("value"));
					if("FL_AUTO".equalsIgnoreCase(name))
						tmpMO.setFlAuto(atts.getValue("value").equals("1"));
					if("BALANCE".equalsIgnoreCase(name))
						tmpMO.setBalance(Integer.parseInt(atts.getValue("value")));
					if("MIN_SUMM".equalsIgnoreCase(name))
						tmpMO.setMinSumm(Integer.parseInt(atts.getValue("value")));
					if("MAX_SUMM".equalsIgnoreCase(name))
						tmpMO.setMaxSumm(Integer.parseInt(atts.getValue("value")));
				}
			else
				super.startElement(uri, localName, qName, atts);
		}

		public void endElement(String uri, String localName, String qName) throws SAXException
		{
			if(insideSection)
				if("table".equalsIgnoreCase(qName))
					insideSection = false;
				else if("record".equalsIgnoreCase(qName))
				{
					mobileOperators.add(tmpMO);
					tmpMO = null;
				}
			else
				super.endElement(uri, localName, qName);
		}
	}
}
