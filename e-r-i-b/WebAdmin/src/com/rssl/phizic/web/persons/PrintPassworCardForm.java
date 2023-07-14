package com.rssl.phizic.web.persons;

import com.rssl.phizic.auth.passwordcards.PasswordCard;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.List;

/**
 * @author Roshka
 * @ created 05.12.2005
 * @ $Author$
 * @ $Revision$
 */
public class PrintPassworCardForm extends ActionFormBase
{
    private List passwords;
    private Object[] passTab = new Object[0];
    private PasswordCard card;
    private Person prsn;
    private Long cardId;
    private Long personId;
    private static final PersonService service = new PersonService();
    private double maxColumn;
    private double maxRow;

    public double getMaxColumn() {
        return maxColumn;
    }

    public double getMaxRow() {
        return maxRow;
    }

    public void setPasswords(List passwords)
    {
        this.passwords = passwords;
        this.passTab = this.passwords.toArray();
        Double N=new Double(passwords.size());
        Double d20 = new Double(20);
        this.maxColumn = Math.ceil(N / d20);
      //  this.maxRow=Math.ceil(N /maxColumn);
        this.maxRow = d20.intValue();
    }

    public PasswordCard getCard()
    {
        return card;
    }

    public void setCard(PasswordCard card)
    {
        this.card = card;
    }

    public Person getPerson()
    {
        return prsn;
    }

     public int getPasswordsCount()
    {
        return passwords.size();
    }
    public Object[] getPassword (){
           return passTab;
    }

    public Long getPasswordsAvailableCount()
    {
       return card.getValidPasswordsCount();
    }

    public Long getCardId()
    {
        return cardId;
    }

    public void setCardId(Long cardId)
    {
        this.cardId = cardId;
    }

    public Long getPersonId()
    {
        return personId;
    }

    public void setPersonId(Long personId)
    {
        this.personId = personId;
        try {
           if ( personId != null) this.prsn=service.findById(personId);
        }
        catch (BusinessException e) {this.prsn=null;}
    }



}
