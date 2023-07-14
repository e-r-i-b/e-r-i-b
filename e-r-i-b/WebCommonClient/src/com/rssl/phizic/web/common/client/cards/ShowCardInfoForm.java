package com.rssl.phizic.web.common.client.cards;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.ermb.forms.validators.DuplicateAliasValidator;
import com.rssl.phizic.business.ermb.forms.validators.IdenticalToSmsCommandValidator;
import com.rssl.phizic.business.resources.external.AutoSubscriptionLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.gate.bankroll.CardAbstract;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoSubscription;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.*;

/**
 * @author Krenev
 * @ created 09.10.2007
 * @ $Author$
 * @ $Revision$
 */
public class ShowCardInfoForm extends EditFormBase implements CardInfoForm
{
	public static final Form EDIT_CARD_NAME_FORM  = editCardNameForm();
	private CardLink cardLink;
	private Set<CardLink> additionalCardInfoSet = new HashSet<CardLink>();
	private Client cardAccountClient;
	private Long warningPeriod;
	private CardAbstract cardAbstract;
	private List<CardLink> additionalCards;
	private List<CardLink> otherCards;
	private List<TemplateDocument> templates;
	private boolean cardOperation;
	private boolean accountOperation;
	private boolean extendedCardAbstractAvailable = true;
	private Map<Object, ArrayList<AutoSubscriptionLink>> moneyBoxesTargets = new HashMap<Object, ArrayList<AutoSubscriptionLink>>();
    List<AutoSubscriptionLink> p2pSubscriptions = new ArrayList<AutoSubscriptionLink>();
	//Сообщение об ощибке для отображения пользователю
	private String abstractMsgError;

	public List<TemplateDocument> getTemplates()
	{
		return templates;
	}

	public void setTemplates(List<TemplateDocument> templates)
	{
		this.templates = templates;
	}

	public List<CardLink> getAdditionalCards()
	{
		return additionalCards;
	}

	public void setAdditionalCards(List<CardLink> additionalCards)
	{
		this.additionalCards = additionalCards;
	}

	public List<CardLink> getOtherCards()
	{
		return otherCards;
	}

	public void setOtherCards(List<CardLink> otherCards)
	{
		this.otherCards = otherCards;
	}

	public CardAbstract getCardAbstract()
	{
		return cardAbstract;
	}

	public void setCardAbstract(CardAbstract cardAbstract)
	{
		this.cardAbstract = cardAbstract;
	}

	public CardLink getCardLink() {
	    return cardLink;
	}

	public void setCardLink(CardLink cardLink) {
	    this.cardLink = cardLink;
	}

	public Set<CardLink> getAdditionalCardInfoSet()
	{
		return additionalCardInfoSet;
	}

	public void setAdditionalCardInfoSet(Set<CardLink> additionalCardInfoSet)
	{
		this.additionalCardInfoSet = additionalCardInfoSet;
	}

	public Long getWarningPeriod()
	{
		return warningPeriod;
	}

	public void setWarningPeriod(Long warningPeriod)
	{
		this.warningPeriod = warningPeriod;
	}

	public Client getCardAccountClient()
	{
		return cardAccountClient;
	}

	public void setCardAccountClient(Client cardAccountClient)
	{
		this.cardAccountClient = cardAccountClient;
	}

	public boolean isExtendedCardAbstractAvailable()
	{
		return extendedCardAbstractAvailable;
	}

	public void setExtendedCardAbstractAvailable(boolean extendedCardAbstractAvailable)
	{
		this.extendedCardAbstractAvailable = extendedCardAbstractAvailable;
	}

	private static Form editCardNameForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Название");
		fieldBuilder.setName("cardName");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{1,56}", "Наименование должно быть не более 56 символов.")
		);
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	public Form editCardAliasForm() throws BusinessException
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("SMS-идентификатор");
		fieldBuilder.setName("clientSmsAlias");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new DuplicateAliasValidator(this.getId(), CardLink.class),
				new IdenticalToSmsCommandValidator(),
				//Алиас может состоять только из цифр, в таком случае длина д.б. не менее 4 символов.
				//Или же алиас может состоять из цифр и/или букв, в таком случае д.б. не короче 3 символов.
				new RegexpFieldValidator("\\d{4,}|(?=.{3,})(\\d*[A-zА-яЁё][A-zА-яЁё0-9]*)", "SMS-идентификатор должен содержать " +
						"только буквы и цифры и быть не короче 3 символов (не короче 4, если содержит только цифры)")
		);
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	public String getAbstractMsgError()
	{
		return abstractMsgError;
	}

	public void setAbstractMsgError(String abstractMsgError)
	{
		this.abstractMsgError = abstractMsgError;
	}

	public boolean isCardOperation()
	{
		return cardOperation;
	}

	public void setCardOperation(boolean cardOperation)
	{
		this.cardOperation = cardOperation;
	}

	public boolean isAccountOperation()
	{
		return accountOperation;
	}

	public void setAccountOperation(boolean accountOperation)
	{
		this.accountOperation = accountOperation;
	}

	public Map<Object, ArrayList<AutoSubscriptionLink>> getMoneyBoxesTargets()
	{
		return Collections.unmodifiableMap(moneyBoxesTargets);
	}

	public void setMoneyBoxesTargets(Map<Object, ArrayList<AutoSubscriptionLink>> moneyBoxesTargets)
	{
		this.moneyBoxesTargets = moneyBoxesTargets;
	}

    public List<AutoSubscriptionLink> getP2pSubscriptions()
    {
        return p2pSubscriptions;
    }

    public void setP2pSubscriptions(List<AutoSubscriptionLink> p2pSubscriptions)
    {
        this.p2pSubscriptions = p2pSubscriptions;
	}
}
