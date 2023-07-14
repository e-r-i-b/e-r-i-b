package com.rssl.phizic.operations.userprofile;

import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.business.BankrollServiceHelper;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.OTPRestrictionImpl;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.business.resources.external.MainCardFilter;
import com.rssl.phizic.business.resources.external.NotVirtualCardsFilter;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.OTPRestriction;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientProductsService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.operations.ConfirmableOperationBase;
import com.rssl.phizic.security.SecurityLogicException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lepihina
 * @ created 13.12.2011
 * @ $Author$
 * @ $Revision$
 */
public class SetupOTPRestrictionOperation extends ConfirmableOperationBase
{
	private static final ExternalResourceService externalService = new ExternalResourceService();

	protected static final String OTP_GET_FIELD = "OTPGet";
	protected static final String OTP_USE_FIELD = "OTPUse";

	private List<CardLink> cards;
	private List<CardLink> changedCards = new ArrayList<CardLink>();
	private List<Pair<CardLink, Boolean>> changedOTPGet = new ArrayList<Pair<CardLink, Boolean>>();
	private List<Pair<CardLink, Boolean>> changedOTPUse = new ArrayList<Pair<CardLink, Boolean>>();
	private List<CardLink> notUpdatedCards = new ArrayList<CardLink>();

	private ConfirmableObject confirmableObject;

	public void initialize() throws BusinessException, BusinessLogicException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		cards = personData.getCards(new MainCardFilter());
		CollectionUtils.filter(cards, new NotVirtualCardsFilter());
	}

	/**
	 * ѕолучить клиентские карты
	 * @return клиентские карты
	 * @throws BusinessException
	 */
	public List<CardLink> getClientCards()
	{
		return cards;
	}

	/**
	 * ѕолучить список необновленных линков карт
	 * @return список необновленных линков карт
	 * @throws BusinessException
	 */
	public List<CardLink> getNotUpdatedCards()
	{
		return notUpdatedCards;
	}

	/**
	 * ѕровер€ем изменились ли настройки ограничений у карт.
	 *  арты, у которых изменились настройки сохран€ем в changedCards.
	 */
	public void setChangedCards(Map<String, Object> results)
	{
		for (CardLink cardLink : cards)
		{
			Long id = cardLink.getId();
			Boolean newOTPGet = (Boolean)results.get(OTP_GET_FIELD + id);
			Boolean newOTPUse = (Boolean)results.get(OTP_USE_FIELD + id);
			boolean changed = false;

			if (!newOTPGet.equals(cardLink.getOTPGet()))
			{
				cardLink.setOTPGet(newOTPGet);
				changedOTPGet.add(new Pair<CardLink, Boolean>(cardLink, newOTPGet));
				changed = true;

				//печать паролей разрешена
				if (newOTPGet)
					cardLink.setOTPUse(null);
			}
			if (!newOTPGet && !newOTPUse.equals(cardLink.getOTPUse()))
			{
				changedOTPUse.add(new Pair<CardLink, Boolean>(cardLink, newOTPUse));
				cardLink.setOTPUse(newOTPUse);
				changed = true;
			}
			if (changed)
				changedCards.add(cardLink);
		}
	}

	/**
	 * ѕолучить список необновленных линков карт
	 * @return список необновленных линков карт
	 */
	public List<CardLink> getChangedCards()
	{
		return changedCards;
	}

	protected void saveConfirm() throws BusinessException, BusinessLogicException, SecurityLogicException
	{
		updateCardLinks();
	}

	/**
	 * ќбновление состо€ние отображени€ карт
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	protected void updateCardLinks() throws BusinessException, BusinessLogicException
	{
		Client client = PersonContext.getPersonDataProvider().getPersonData().getPerson().asClient();
		ClientProductsService clientProductsService = GateSingleton.getFactory().service(ClientProductsService.class);

		if (CollectionUtils.isEmpty(changedCards))
			return;

		try
		{
			GroupResult<Object, Boolean> updateResources = clientProductsService.updateOTPRestriction(client, getCardRestrictions(changedCards));
			for (Map.Entry<Object, Boolean> restriction : updateResources.getResults().entrySet())
			{
				Card card = (Card) restriction.getKey();
				CardLink cardLink = BankrollServiceHelper.findCardLinkByNumber(card.getNumber(), changedCards);
				boolean updated = BooleanUtils.isTrue(restriction.getValue());
				if (updated)
					externalService.updateLink(cardLink);
				else
					notUpdatedCards.add(cardLink);
			}
		}
		catch (GateException ge)
		{
			throw new BusinessException(ge);
		}
		catch (GateLogicException gle)
		{
			throw new BusinessLogicException(gle);
		}
	}

	protected List<Pair<Object, OTPRestriction>> getCardRestrictions(List<CardLink> links) throws BusinessException
	{
		List<Pair<Object, OTPRestriction>> result = new ArrayList<Pair<Object, OTPRestriction>>();
		for (CardLink cardLink : links)
		{
			OTPRestrictionImpl restriction = new OTPRestrictionImpl();
			restriction.setOTPGet(cardLink.getOTPGet());
			restriction.setOTPUse(cardLink.getOTPUse());
			result.add(new Pair<Object, OTPRestriction>(cardLink.getCard(), restriction));
		}
		return result;
	}
	
	public ConfirmableObject getConfirmableObject()
	{
		if (confirmableObject == null)
		{
			confirmableObject = new CardsOTPRestrictions(changedOTPGet, changedOTPUse);
		}
		return confirmableObject;
	}
}
