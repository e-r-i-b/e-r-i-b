package com.rssl.phizicgate.rsV51.clients;

import java.io.Serializable;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * @author hudyakov
 * @ created 18.08.2008
 * @ $Author$
 * @ $Revision$
 */
public class ClientId implements Serializable
{
	private String personId;
	private String paperKind;

	public String getPersonId()
	{
		return personId;
	}

	public void setPersonId(String personId)
	{
		this.personId = personId;
	}

	public String getPaperKind()
	{
		return paperKind;
	}

	public void setPaperKind(String paperKind)
	{
		this.paperKind = paperKind;
	}

	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		ClientId clientId = (ClientId) o;

		if (personId != null ? !personId.equals(clientId.personId) : clientId.personId != null)
			return false;
		if (paperKind != null ? !paperKind.equals(clientId.paperKind) : clientId.paperKind != null)
			return false;

		return true;
	}

	public int hashCode()
	{
		int result;
		result = (personId != null ? personId.hashCode() : 0);
		result = 31 * result + (paperKind != null ? paperKind.hashCode() : 0);
		return result;
	}

	public String toString(){
		return "id:"+personId+", kind:"+paperKind;
	}

	public static ClientId valueOf(String str){
		String patternString = "id:(.+), kind:(.+)";
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(str);
		matcher.find();
		ClientId claimId = new ClientId();
		claimId.setPersonId(matcher.group(1));
		claimId.setPaperKind(matcher.group(2));
		return claimId;
	}
}
