package com.rssl.phizic.operations.card;

import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.utils.GroupResultHelper;

/**
 * @author Danilov
 * @ created 25.09.2007
 * @ $Author$
 * @ $Revision$
 */

/**
 * тест находится здесь, потому что невозможно его разместить в любом из гейтов
 * (необходимо обращение к гейту 5.0 и гейту 5.1)
 */
public class CardOwnerTest extends BusinessTestCaseBase
{
	private static final Long   CLIENT_ID                          = 29L;
	private static final Long   CARD_ID                            = 9L;
	private static final String EXTERNAL_CARD_ID                   = CLIENT_ID + "*" + CARD_ID;

    public void testGetOwnerInfo() throws Exception
    {
	    initializeRSGate();
	    BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
	    Card card = GroupResultHelper.getOneResult(bankrollService.getCard(EXTERNAL_CARD_ID));
	    Client client = GroupResultHelper.getOneResult(bankrollService.getOwnerInfo(card));
	    assertNotNull(client);
    }
}
