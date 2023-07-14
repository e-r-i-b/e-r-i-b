package com.rssl.phizic.web.passwordcards;

import com.rssl.phizic.auth.passwordcards.PasswordCard;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.payments.forms.validators.PasswordCardNumberValidator;
import com.rssl.phizic.business.payments.forms.validators.PasswordCardExistValidator;
import com.rssl.phizic.business.payments.forms.validators.PasswordCardStateValidator;
import com.rssl.phizic.web.common.ListLimitActionForm;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.validators.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Roshka
 * @ created 05.12.2005
 * @ $Author: hudyakov $
 * @ $Revision: 11070 $
 */
public class ListUserPassworCardsForm extends ListLimitActionForm
{
	private ActivePerson activePerson;

	private Long         id;     // userId
    private Long         person; // userId
    private List         cards = new ArrayList();
    private PasswordCard currentCard;
    private Long[]       addCardIds;
	private String blockReason;
	private String unblockPassword;
	
	public String getUnblockPassword()
	{
		return unblockPassword;
	}

	public void setUnblockPassword( String unblockPassword)
	{
		this.unblockPassword = unblockPassword;
	}

	public String getBlockReason()
	{
		return blockReason;
	}

	public void setBlockReason( String blockReason)
	{
		this.blockReason = blockReason;
	}

	public ActivePerson getActivePerson()
	{
		return activePerson;
	}

	public void setActivePerson(ActivePerson activePerson)
	{
		this.activePerson = activePerson;
	}

	public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
        this.person=id;
    }

    public Long getPerson() {
        return person;
    }

    public void setPerson(Long person) {
        this.person = person;
        this.id = person;
    }

    public List getCards()
    {
        return cards;
    }

    public void setCards(List cards)
    {
        this.cards = cards;
    }

    public PasswordCard getCurrentCard()
    {
        return currentCard;
    }

    public void setCurrentCard(PasswordCard currentCard)
    {
       this.currentCard = currentCard;
    }

    public Long[] getAddCardIds() {
        return addCardIds;
    }

    public void setAddCardIds(Long[] addCardIds) {
        this.addCardIds = addCardIds;
    }

    public static final Form FILTER_FORM     = createFilterForm();

	@SuppressWarnings({"OverlyLongMethod"})
	private static Form createFilterForm()
    {
		FormBuilder formBuilder  = new FormBuilder();
		Field[]      fields       = new Field[7];
		FieldBuilder fieldBuilder;


		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("number");
		fieldBuilder.setDescription("Номер");
		fields[0] = fieldBuilder.build();

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("fromDate");
		fieldBuilder.setDescription("Дата выдачи (начальная)");
		fieldBuilder.setValidators(new FieldValidator[]{new DateFieldValidator()});
		fieldBuilder.setParser(new DateParser());
		fields[1] = fieldBuilder.build();

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("toDate");
		fieldBuilder.setDescription("Дата выдачи (конечная)");
		fieldBuilder.setValidators(new FieldValidator[]{new DateFieldValidator()});
		fieldBuilder.setParser(new DateParser());
		fields[2] = fieldBuilder.build();

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("passwordsCount");
		RegexpFieldValidator regexpFieldValidator = new RegexpFieldValidator("\\d{1,5}");
		regexpFieldValidator.setMessage("Неверный формат данных, введите числовое значение.");
		fieldBuilder.setValidators(new FieldValidator[]{regexpFieldValidator});
		fieldBuilder.setDescription("Количество паролей");
		fields[3] = fieldBuilder.build();

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("state");
		fieldBuilder.setDescription("Статус");
		fields[4] = fieldBuilder.build();

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("login");
	    fieldBuilder.setDescription("Логин");
		fields[5] = fieldBuilder.build();

	    fieldBuilder = new FieldBuilder();
	    fieldBuilder.setName("blockType");
	    fieldBuilder.setDescription("Тип блокирования");
	    fields[6] = fieldBuilder.build();
	    
		formBuilder.setFields(fields);

		CompareValidator compareValidator = new CompareValidator();
		compareValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS_EQUAL);
		compareValidator.setBinding(CompareValidator.FIELD_O1, "fromDate");
		compareValidator.setBinding(CompareValidator.FIELD_O2, "toDate");
		compareValidator.setMessage("Конечная дата должна быть больше либо равна начальной!");
		formBuilder.setFormValidators(new MultiFieldsValidator[]{compareValidator});

		return formBuilder.build();
    }

	public static final Form ADD_FORM     = createForm();

	@SuppressWarnings({"OverlyLongMethod"})
	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Номер карты");
		fieldBuilder.setName("cardNumber");
		fieldBuilder.setType("string");
		PasswordCardStateValidator cardStateValidator = new PasswordCardStateValidator();
		cardStateValidator.setParameter(PasswordCardStateValidator.PARAMETER_STATE, PasswordCard.STATE_NEW);
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new PasswordCardNumberValidator(),
				new PasswordCardExistValidator(),
				cardStateValidator);

		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
