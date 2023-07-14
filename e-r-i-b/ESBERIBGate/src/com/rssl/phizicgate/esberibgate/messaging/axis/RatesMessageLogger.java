package com.rssl.phizicgate.esberibgate.messaging.axis;

import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.log.LogService;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author gulov
 * @ created 14.10.2010
 * @ $Authors$
 * @ $Revision$
 */

public class RatesMessageLogger extends ESBERIBExtendLogger
{
	public RatesMessageLogger()
	{
		super();
	}

	private class RatesMessageHandler extends MessageHandler
	{

		private String regionId = null;
		private String branchId = null;
		private String agencyId = null;
		private boolean configured = false;

		private RatesMessageHandler(MessagingLogEntry logEntry, boolean request)
		{
			super(logEntry, request);
			messageTypeLevel = 1;
		}

		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
		{
			if(qName.equals("BranchId") || qName.equals("RegionId") || qName.equals("AgencyId"))
				currentElementValue = new StringBuilder();
			super.startElement(uri, localName, qName, attributes);
		}

		public void endElement(String uri, String localName, String qName) throws SAXException
		{
			if(qName.equals("BranchId"))
				branchId = currentElementValue.toString();
			else if (qName.equals("RegionId"))
				regionId = currentElementValue.toString();
			else if (qName.equals("AgencyId"))
				agencyId = currentElementValue.toString();
			super.endElement(uri, localName, qName);
		}

		protected void checkComplete() throws SAXException
		{
			if (regionId != null && branchId !=null && agencyId != null)
			{
				if (!configured)
					configureLogThreadContext();
				super.checkComplete();
			}
		}

		private void configureLogThreadContext() throws SAXException
		{
			LogService logService = GateSingleton.getFactory().service(LogService.class);
			try
			{
				LogThreadContext.setDepartmentName(logService.getDepartmentNameByCode(regionId, branchId, agencyId));
				LogThreadContext.setDepartmentRegion(regionId);
				LogThreadContext.setDepartmentOSB(branchId);
				LogThreadContext.setDepartmentVSP(agencyId);
				configured = true;
			}
			catch (Exception e)
			{
				throw new SAXException(e);
			}
		}
	}

	protected DefaultHandler getMessageHandler(MessagingLogEntry logEntry, boolean request)
	{
		return new RatesMessageHandler(logEntry, request);
	}
}
