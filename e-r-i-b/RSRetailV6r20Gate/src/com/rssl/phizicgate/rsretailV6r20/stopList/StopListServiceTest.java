package com.rssl.phizicgate.rsretailV6r20.stopList;

import com.rssl.phizic.gate.clients.stoplist.StopListService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.rsretailV6r20.junit.RSRetailV6r20GateTestCaseBase;

import java.text.ParseException;

/**
 * @author Krenev
 * @ created 11.02.2009
 * @ $Author$
 * @ $Revision$
 */
public class StopListServiceTest extends RSRetailV6r20GateTestCaseBase
{
	public void test() throws GateLogicException, GateException, ParseException
	{

		StopListService service = new StopListServiceImpl(gateFactory);
		//todo
//		assertEquals(ClientStopListState.shady, service.check("250800165739", "ÒÀÒÜßÍÀ", null ,"ÏÀÊÓËÎÂÀ"));
//		assertEquals(ClientStopListState.shady, service.check("250800165739", "ÒÀÒÜßÍÀ", "ÃÀËÀÊÒÈÎÍÎÂÍÀ" ,"ÏÀÊÓËÎÂÀ"));
//		assertEquals(ClientStopListState.trusted, service.check("250800165739", "qqqqÒÀÒÜßÍÀ", null ,"ÏÀÊÓËÎÂÀ"));
//
//		//íå òåğğîğèñòêà.
//		assertEquals(ClientStopListState.trusted, service.check("ÒÀÒÜßÍÀ", null ,"ÏÀÊÓËÎÂÀ"));
//		assertEquals(ClientStopListState.trusted, service.check("ÒÀÒÜßÍÀ", "ÃÀËÀÊÒÈÎÍÎÂÍÀ" ,"ÏÀÊÓËÎÂÀ"));
//
//		//òåğğîğèñò
//		assertEquals(ClientStopListState.shady, service.check("øàìèëü", "ñàëìàíîâè÷" ,"áàñàåâ"));
//		assertEquals(ClientStopListState.shady, service.check("øàìèëü", null ,"áàñàåâ"));
//
//		assertEquals(ClientStopListState.shady, service.check("05 00", "261808" ,ClientDocumentType.REGULAR_PASSPORT_RF));
//		assertEquals(ClientStopListState.trusted, service.check("05 00", "111111" ,ClientDocumentType.REGULAR_PASSPORT_RF));
//
//		assertEquals(ClientStopListState.shady, service.check("ÒÀÒÜßÍÀ", "ÃÀËÀÊÒÈÎÍÎÂÍÀ" ,"ÏÀÊÓËÎÂÀ", "05 00", "261808", DateHelper.parseCalendar("06.10.1951")));
//		assertEquals(ClientStopListState.trusted, service.check("ÒÀÒÜßÍÀ", "ÃÀËÀÊÒÈÎÍÎÂÍÀ" ,"ÏÀÊÓËÎÂÀ", "05 00", "261808", DateHelper.parseCalendar("01.01.1952")));
//		//ïàñïîğòíûå äàííûå èãíîğèì.
//		assertEquals(ClientStopListState.shady, service.check("ÒÀÒÜßÍÀ", "ÃÀËÀÊÒÈÎÍÎÂÍÀ" ,"ÏÀÊÓËÎÂÀ", "1111", "2222", DateHelper.parseCalendar("06.10.1951")));

	}
}
