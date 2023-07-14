package com.rssl.phizic.business.persons;

import com.rssl.phizic.person.Person;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 07.09.2005
 * Time: 14:15:21
 */
public class DeletedPerson extends PersonBase
{
    protected DeletedPerson()
    {
    }

    private DeletedPerson(Person person)
    {
        super(person);
    }

    static DeletedPerson create(Person person)
    {
      return new DeletedPerson(person);
    }

    public String getStatus()
    {
        return Person.DELETED;
    }

	public void setStatus(String status)
	{
	}

	public String getDiscriminator()
	{
		return Person.DELETED;
	}

}
