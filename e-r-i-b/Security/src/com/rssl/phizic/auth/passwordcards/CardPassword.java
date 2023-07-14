package com.rssl.phizic.auth.passwordcards;

import com.rssl.phizic.security.password.Password;

/**
 * Карточка одноразовых паролей пользователя.
 * @author Roshka
 * @ created 01.12.2005
 * @ $Author$
 * @ $Revision$
 */
public class CardPassword implements Password
{
    private Long id;
    private PasswordCardImpl card;
    private Integer number;
    private char[] value;
    private boolean valid = true;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public PasswordCard getCard()
    {
        return card;
    }

    public void setCard(PasswordCardImpl card)
    {
        this.card = card;
    }

    public Integer getNumber()
    {
        return number;
    }

    public void setNumber(Integer number)
    {
        this.number = number;
    }

    public char[]  getValue()
    {
        return value.clone();
    }

    public void setValue(char[] value)
    {
        this.value = value.clone();
    }

    /**
     * @noinspection UNUSED_SYMBOL
     */
    private void setStringValue(String value)
    {
        this.value = value.toCharArray();
    }

    /**
     * @noinspection UNUSED_SYMBOL
     */
    public String getStringValue()
    {
        return String.valueOf(value);
    }

    public boolean isValid()
    {
	    return isUsed() && card.isActive();
    }

    /* используется при отображении карт ключей */
    /* isValid - не годится */
    public boolean isUsed()
    {
        return isValidInternal();
    }

    private boolean isValidInternal()
	{
		return valid;
	}

	public void setUsed()
	{
		Long validPasswordCount = new Long(card.getValidPasswordsCount() - 1);
		card.setValidPasswordsCount(validPasswordCount);
		setValidInternal(false);
	}

	private void setValidInternal(boolean valid)
	{
		this.valid = valid;
	}

}
