package com.rssl.phizicgate.rsV51.demand;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: Novikov_A
 * Date: 22.03.2007
 * Time: 17:50:54
 * To change this template use File | Settings | File Templates.
 */
public class ClaimId implements Serializable
{
	private String applicationKey;
	private Long applicationKind;

	public String getApplicationKey()
	{
		return applicationKey;
	}

	public void setApplicationKey(String applicationKey)
	{
		this.applicationKey = applicationKey;
	}

	public Long getApplicationKind()
	{
		return applicationKind;
	}

	public void setApplicationKind(Long applicationKind)
	{
		this.applicationKind = applicationKind;
	}

	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		ClaimId claimId = (ClaimId) o;

		if (applicationKey != null ? !applicationKey.equals(claimId.applicationKey) : claimId.applicationKey != null)
			return false;
		if (applicationKind != null ? !applicationKind.equals(claimId.applicationKind) : claimId.applicationKind != null)
			return false;

		return true;
	}

	public int hashCode()
	{
		int result;
		result = (applicationKey != null ? applicationKey.hashCode() : 0);
		result = 31 * result + (applicationKind != null ? applicationKind.hashCode() : 0);
		return result;
	}

	public String toString(){
		return "key:"+applicationKey+", kind:"+applicationKind;
	}

	public static ClaimId valueOf(String str){
		String patternString = "key:(.+), kind:(.+)";
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(str);
		matcher.find();
		ClaimId claimId = new ClaimId();
		claimId.setApplicationKey(matcher.group(1));
		claimId.setApplicationKind(Long.valueOf(matcher.group(2)));
		return claimId;
	}
}
