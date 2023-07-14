package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.clients.RemoteConnectionUDBOHelper;
import com.rssl.phizic.business.documents.RemoteConnectionUDBOClaim;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.List;

/**
 * Согласно РО в редких случаях для Московским клиентам надо устанавливать не тот ТБ где они прописаны:
 * Для Московского банка используются два кода ТБ: 38 и 99. Любые комбинации этих кодов интерпретируются как один ТБ.
 * При наличии у клиента Московского банка двух карт в разных московских ТБ, УДБО подключается в том ТБ, к которому привязан профиль.
 * Иначе - в том ТБ, в котором открыта карта.
 * @author basharin
 * @ created 11.02.15
 * @ $Author$
 * @ $Revision$
 */

public class ChangeTbUDBOClaimHandler extends BusinessDocumentHandlerBase
{
	private static final PersonService personService = new PersonService();

	public void process(StateObject stateObject, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		try
		{
			if (stateObject instanceof RemoteConnectionUDBOClaim)
			{
				RemoteConnectionUDBOClaim document = (RemoteConnectionUDBOClaim) stateObject;
				if (document.getRbTbBranch().equals(RemoteConnectionUDBOHelper.MOSCOW_TB_38) || document.getRbTbBranch().equals(RemoteConnectionUDBOHelper.MOSCOW_TB_99))
				{
					ActivePerson person = personService.findByLoginId(document.getLoginId());

					if (person != null)
					{
						List<Card> cards = GateSingleton.getFactory().service(BankrollService.class).getClientCards(person.asClient());
						boolean isCardWithMoscowTb38 = false;
						boolean isCardWithMoscowTb99 = false;
						for (Card card : cards)
						{
							if (card.getOffice().getCode().getFields().get("region").equals(RemoteConnectionUDBOHelper.MOSCOW_TB_38))
								isCardWithMoscowTb38 = true;
							if (card.getOffice().getCode().getFields().get("region").equals(RemoteConnectionUDBOHelper.MOSCOW_TB_99))
								isCardWithMoscowTb99 = true;
						}
						if (isCardWithMoscowTb38 && !isCardWithMoscowTb99)
							document.setRbTbBranch(RemoteConnectionUDBOHelper.MOSCOW_TB_38);

						if (!isCardWithMoscowTb38 && isCardWithMoscowTb99)
							document.setRbTbBranch(RemoteConnectionUDBOHelper.MOSCOW_TB_99);
					}
				}
			}
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}
		catch (GateLogicException e)
		{
			throw new DocumentLogicException(e);
		}
	}
}