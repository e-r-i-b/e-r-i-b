package com.rssl.phizic.cryptoplugin;

import com.rssl.phizic.security.crypto.Certificate;
import com.rssl.crypto.Plugin;

import java.util.Date;

/**
 * @author Omeliyanchuk
 * @ created 28.02.2007
 * @ $Author$
 * @ $Revision$
 */

public class CertificateImpl implements Certificate
{
	private final String KEY_PAIR_ID_FIELD_NAME = "KeyPairId";
	private String id;
	private String name;
	private String organization;
	private Date dateExpire;
	private Date dateBegin;
	private String description;
	private int hashCode;
	private String keyPairId;

	public CertificateImpl(Plugin.Certificate cert) throws Plugin.Failure
	{
		id = cert.getId();
		name = cert.getName();
		organization = cert.getOrganization();
		dateExpire = cert.getDateExpire();
		dateBegin = cert.getDateBegin();
		description = cert.getPurpose();
		hashCode = cert.hashCode();
		if (cert.FieldExist(KEY_PAIR_ID_FIELD_NAME))
			keyPairId = cert.getStrField(KEY_PAIR_ID_FIELD_NAME);
		else keyPairId = "";
	}

	public String getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public String getOrganization()
	{
		return organization;
	}

	public Date getExpiration()
	{
		return dateExpire;
	}

	public Date getIssue()
	{
		return dateBegin;
	}

	public String getDescription()
	{
		return description;
	}

	public String getKeyPairId()
	{
		return keyPairId;
	}

	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;

		CertificateImpl that = (CertificateImpl) obj;

		if (!id.equals(that.id)) return false;

		return true;
	}

	public int hashCode()
	{
		return hashCode;
	}
}
