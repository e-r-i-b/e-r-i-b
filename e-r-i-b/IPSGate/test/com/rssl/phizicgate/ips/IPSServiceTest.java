package com.rssl.phizicgate.ips;

import com.rssl.phizgate.ips.IPSCardOperationClaimImpl;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.ips.IPSCardOperationClaim;
import com.rssl.phizic.gate.ips.IPSExecutiveService;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.test.DataSourceHelper;
import junit.framework.TestCase;

import java.io.InvalidClassException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import javax.naming.NamingException;

/**
 * @author Erkin
 * @ created 29.07.2011
 * @ $Author$
 * @ $Revision$
 */
public class IPSServiceTest extends TestCase
{
	protected void setUp() throws InvalidClassException, NamingException
	{
		initConfigs();
	}

	private void initConfigs() throws InvalidClassException, NamingException
	{
		DataSourceHelper.createJUnitDataSource(new IPSJUnitDatabaseConfig());
	}

	/**
	 * Тест загрузки карточных операций из ИПС
	 */
	public void testExecuteCardOperationClaims() throws GateException
	{
		IPSExecutiveService executiveService = GateSingleton.getFactory().service(IPSExecutiveService.class);

		List<IPSCardOperationClaim> claims = buildClaimsList();
		executiveService.executeCardOperationClaims(claims);
	}

	private List<IPSCardOperationClaim> buildClaimsList()
	{
		List<IPSCardOperationClaim> list = new LinkedList<IPSCardOperationClaim>();
		list.add(buildClaim("10005678901234567801"));
		list.add(buildClaim("91605678901234567802"));
		list.add(buildClaim("91605678901234567803"));
		return list;
	}

	private IPSCardOperationClaim buildClaim(String cardNumber)
	{
		Calendar startDate = Calendar.getInstance();
		// 5 лет назад + (0-400 дней)
		startDate = DateHelper.addDays(startDate, -5*365 + (int)(Math.random()*400));

		IPSCardOperationClaimImpl claim = new IPSCardOperationClaimImpl();
		claim.setClient(buildClient());
		claim.setCard(buildCard(cardNumber));
		claim.setStartDate(startDate);
		return claim;
	}

	private Client buildClient()
	{
		TestClient client = new TestClient();
		client.setId("PCB96AF4|phiz-gate-cod");
		client.setShortName("ClientShortName");
		client.setFullName("ClientFullName");
		client.setFirstName("ClientFirstName");
		client.setSurName("ClientSurName");
		client.setPatrName("ClientPatrName");
		return client;
	}

	private Card buildCard(String cardNumber)
	{
		TestCard card = new TestCard();
		card.setNumber(cardNumber);
		return card;
	}
}
