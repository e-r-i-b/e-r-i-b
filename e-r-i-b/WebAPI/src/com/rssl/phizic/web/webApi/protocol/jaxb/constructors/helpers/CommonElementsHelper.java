package com.rssl.phizic.web.webApi.protocol.jaxb.constructors.helpers;

import com.rssl.phizic.business.accounts.AccountsUtil;
import com.rssl.phizic.business.cards.CardsUtil;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.web.util.DepartmentViewUtil;
import com.rssl.phizic.web.util.NodeUtil;
import com.rssl.phizic.web.util.PersonInfoUtil;
import com.rssl.phizic.web.webApi.protocol.jaxb.constructors.utils.LinkUtils;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.*;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.productlist.*;
import org.apache.commons.lang.StringUtils;

/**
 * @author Balovtsev
 * @since 08.05.2014
 */
public class CommonElementsHelper
{
	private static final String linkFIO =  LinkUtils.createRedirectUrl("/private/userprofile/userSettings.do", null);
	/**
	 * Создает и заполняет сущность PersonTag, которая в спецификации
	 * соответствует типу PersonType. Использовать при наличии активной сессии.
	 *
	 * @return PersonTag
	 */
	public static final PersonTag createPersonTag()
	{
		Person person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		PersonTag personTag = new PersonTag();
		personTag.setLoginId(person.getLogin().getId());
		personTag.setLinkFIO(linkFIO);
		personTag.setSurName(PersonHelper.getFormattedSurName(person.getSurName()).toUpperCase());
		personTag.setPatrName(StringUtils.capitalize(person.getPatrName().toLowerCase()));
		personTag.setFirstName(StringUtils.capitalize(person.getFirstName().toLowerCase()));
		personTag.setCreationType(CreationType.getCreationTypeByName(person.getCreationType().name()));
		personTag.setBlockId(NodeUtil.getCurrentNode().getId());
		if (PersonInfoUtil.getPersonLastLogonDate() != null)
			personTag.setLastLogonDate(PersonInfoUtil.getPersonLastLogonDate());
		if (!StringHelper.isEmpty(PersonInfoUtil.getPersonLastIpAddress()))
			personTag.setLastIpAddress(PersonInfoUtil.getPersonLastIpAddress());

		Department department = DepartmentViewUtil.getCurrentTerbankFromContext();
		if (department != null)
		{
			personTag.setDepartment(new PersonDepartment(department.getId(), department.getName()));
		}

		Region region = PersonInfoUtil.getPersonRegion();
		if (region != null)
		{
			personTag.setRegion(new PersonRegion(region.getId(), region.getName()));
		}
		else
		{
			personTag.setRegion(new PersonRegion(0L, "Все регионы"));
		}

		return personTag;
	}

	/**
	 * заполняет тег "карты"
	 * @param cardLink источник информации
	 * @param cardTag тег для заполнения
	 */
	public static void fillCardTag(CardLink cardLink, CardCommonTag cardTag)
	{
		Card card = cardLink.getCard();
		cardTag.setId(cardLink.getId());
		cardTag.setName(CardsUtil.getCardUserName(cardLink));
		cardTag.setDescription(card.getDescription());
		cardTag.setNumber(MaskUtil.getCutCardNumber(card.getNumber()));
		cardTag.setMain(card.isMain());
		cardTag.setType(CardTypeWebAPI.valueOf(card.getCardType().name()));
		if (card.getAvailableLimit() != null)
			cardTag.setAvailableLimit(new MoneyTag(card.getAvailableLimit()));
		cardTag.setState(CardState.valueOf(card.getCardState().name()));
		cardTag.setVirtual(card.isVirtual());
		if (card.getStatusDescExternalCode() != null)
			cardTag.setStatusDescExternalCode(StatusDescExternalCode.valueOf(card.getStatusDescExternalCode().name()));
		cardTag.setIssueDate(XMLDatatypeHelper.formatDateTimeWithoutTimeZone(card.getIssueDate()));
		cardTag.setExpireDate(CardsUtil.formatExpirationCardDate(cardLink));
		Office office = null;
		if (cardLink.getCardAccount() != null)
			office = cardLink.getCardAccount().getOffice();
		if (office == null)
			office = card.getOffice();
		if (office != null)
			cardTag.setOffice(new OfficeTag(card.getOffice()));
		if (card.getAdditionalCardType() != null)
			cardTag.setAdditionalCardType(AdditionalCardType.valueOf(card.getAdditionalCardType().name()));
		cardTag.setCurrency(new CurrencyTag(card.getCurrency()));
		if (card.getMainCardNumber() != null)
			cardTag.setMainCardNumber(card.getMainCardNumber());
		if (card.getPrimaryAccountNumber() != null)
			cardTag.setPrimaryAccountNumber(card.getPrimaryAccountNumber());
		if (card.getCardLevel() != null)
			cardTag.setCardLevel(CardLevel.valueOf(card.getCardLevel().name()));
		if (card.getCardBonusSign() != null)
			cardTag.setCardBonusSign(CardBonusSign.valueOf(card.getCardBonusSign().name()));
		cardTag.setShowOnMain(cardLink.getShowInMain());
	}

	/**
	 * заполняет тег "счета"
	 * @param accountLink источник информации
	 * @param accountTag тег для заполнения
	 */
	public static void fillAccountTag(AccountLink accountLink, AccountCommonTag accountTag)
	{
		Account account = accountLink.getAccount();
		accountTag.setId(accountLink.getId());
		accountTag.setName(accountLink.getName());
		accountTag.setNumber(AccountsUtil.getFormattedAccountNumber(account.getNumber()));
		if (account.getBalance() != null)
			accountTag.setBalance(new MoneyTag(account.getBalance()));
		accountTag.setState(AccountState.valueOf(account.getAccountState().name()));
		if (account.getDescription() != null)
			accountTag.setDescription(account.getDescription());
		if (account.getInterestRate() != null)
			accountTag.setRate(account.getInterestRate());
		if (account.getMaxSumWrite() != null)
			accountTag.setMaxSumWrite(new MoneyTag(account.getMaxSumWrite()));
		accountTag.setCurrency(new CurrencyTag(account.getCurrency()));
		if (account.getOpenDate() != null)
			accountTag.setOpenDate(XMLDatatypeHelper.formatDateTimeWithoutTimeZone(account.getOpenDate()));
		if (account.getCreditAllowed() != null)
			accountTag.setCreditAllowed(account.getCreditAllowed());
		if (account.getDebitAllowed() != null)
			accountTag.setDebitAllowed(account.getDebitAllowed());
		if (account.getMinimumBalance() != null)
			accountTag.setMinimumBalance(new MoneyTag(account.getMinimumBalance()));
		accountTag.setOffice(new OfficeTag(account.getOffice()));
		if (account.getDemand() != null)
			accountTag.setDemand(account.getDemand());
		if (account.getPassbook() != null)
			accountTag.setPassbook(account.getPassbook());
		accountTag.setShowOnMain(accountLink.getShowInMain());
	}
}
