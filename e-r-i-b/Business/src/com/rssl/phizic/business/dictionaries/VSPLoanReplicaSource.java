package com.rssl.phizic.business.dictionaries;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.XmlReplicaSourceBase;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.lang.StringUtils;
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
 * Источник данных по справочнику ВСП с возможностью выдачи кредита
 * @author Moshenko
 * @ created 18.02.14
 * @ $Author$
 * @ $Revision$
 */
public class VSPLoanReplicaSource extends XmlReplicaSourceBase
{
	private List<VSPLoanReplicaDepartment> source = new ArrayList<VSPLoanReplicaDepartment>();

	protected void clearData()
	{
		source.clear();
	}

	protected InputStream getDefaultStream()
	{
		//берем с формы
		return null;
	}

	protected XMLFilter getDefaultFilter() throws ParserConfigurationException, SAXException
	{
		return new SaxFilter(XmlHelper.newXMLReader());
	}

	public void initialize(GateFactory factory) throws GateException{}

	public Iterator iterator() throws GateException, GateLogicException
	{
		internalParse();

		Collections.sort(source, new VSPLoanReplicaComparator());
		return source.iterator();
	}

	public XMLReader chainXMLReader(XMLReader xmlReader)
	{
		return new SaxFilter(super.chainXMLReader(xmlReader));
	}

	private class SaxFilter extends XMLFilterImpl
	{
		private static final String VALUE = "value";
		private static final String QUOT = "&quot;";
		private String rbTbBranch; //Информация о номере tb, osp, vsp.
		private Boolean npcoVal;   //Признак того что подразделение поддерживает  кредитование.
		private boolean inRecord = false;
		private VSPLoanReplicaDepartment loanDepartment;

		SaxFilter(XMLReader parent)
		{
			super(parent);
		}

		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
		{
			if ("record".equals(qName))
				inRecord = true;
			else if ("field".equals(qName))
			{
				if (inRecord && attributes.getValue("name").equals("code"))
					rbTbBranch = getClearValue(attributes);
				else if (inRecord  && attributes.getValue("name").equals("natural_person_credit_oper"))
					npcoVal = StringHelper.equals("1",getClearValue(attributes));
			}
		}

		private String getClearValue(Attributes attributes)
		{
			return StringUtils.remove(StringUtils.remove(attributes.getValue(VALUE), QUOT), '"');
		}

		public void endElement(String uri, String localName, String qName) throws SAXException
		{
			if ("record".equals(qName))
			{
				inRecord = false;
				loanDepartment = new VSPLoanReplicaDepartment();
				loanDepartment.setLoanAvalible(npcoVal);
				loanDepartment.setTb(getTb(rbTbBranch));
				loanDepartment.setOsb(getOsb(rbTbBranch));
				loanDepartment.setOffice(getOffice(rbTbBranch));
				source.add(loanDepartment);
				loanDepartment = null;
			}
		}

		private String getTb(String rbTbBranch)
		{   if (rbTbBranch.length() >= 3)
			    return removeLeadingZero(StringUtils.left(rbTbBranch,3));
			else
			return "";
		}

		private String getOsb(String rbTbBranch)
		{
			if (rbTbBranch.length() >= 7)
				return removeLeadingZero(StringUtils.mid(rbTbBranch, 3,4));
			else
				return "";
		}

		private String getOffice(String rbTbBranch)
		{
			int size = rbTbBranch.length();
			if (size >= 8)
				return removeLeadingZero(StringUtils.mid(rbTbBranch, 7,size));
			else
				return "";
		}

		private String removeLeadingZero(String value)
		{
			return value.replaceFirst("^0+(?!$)", "");
		}
	}
}
