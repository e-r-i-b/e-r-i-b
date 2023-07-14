package com.rssl.phizic.business.ermb.migration.list.entity.migrator;

import com.rssl.phizic.gate.ermb.MBKRegistration;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.Calendar;

/**
 * Клиент в контексте миграции
 * @author Puzikov
 * @ created 31.01.14
 * @ $Author$
 * @ $Revision$
 */

public class MigrationClient
{
	private Long id;
	private String lastName;                            //фамилия
	private String firstName;                           //имя
	private String middleName;                          //отчество
	private String document;                            //документ клиента
														//для загружаемого клиента берется из csv
														//для подгружаемых из мбк - паспорт way
														//для подгружаемых из мбв - серия + номер (игнорируя тип)
	private Calendar birthday;                          //дата рождения
	private String tb;                                  //тербанк
	private String vsp;                                 //внутренне структурное подразделение
	private String osb;                                 //отделение сбербанка
	private boolean vipOrMvs;                           //является ли ВИП или входит в МВС

	public MigrationClient()
	{
	}

	public MigrationClient(com.rssl.phizic.gate.clients.Client client)
	{
		this.lastName = client.getSurName();
		this.firstName = client.getFirstName();
		this.middleName = client.getPatrName();
		ClientDocument clientDocument = client.getDocuments().get(0);
		this.document = StringHelper.getEmptyIfNull(clientDocument.getDocSeries()) + StringHelper.getEmptyIfNull(clientDocument.getDocNumber());
		this.birthday = client.getBirthDay();
	}

	public MigrationClient(MBKRegistration mbkMessage)
	{
		this.lastName = mbkMessage.getOwner().getSurname();
		this.firstName = mbkMessage.getOwner().getFirstname();
		this.middleName = mbkMessage.getOwner().getPatrname();
		this.document = mbkMessage.getOwner().getPassport();
		this.birthday = mbkMessage.getOwner().getBirthdate();
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getMiddleName()
	{
		return middleName;
	}

	public void setMiddleName(String middleName)
	{
		this.middleName = middleName;
	}

	public Calendar getBirthday()
	{
		return birthday;
	}

	public void setBirthday(Calendar birthday)
	{
		this.birthday = birthday;
	}

	public String getTb()
	{
		return tb;
	}

	public void setTb(String tb)
	{
		this.tb = tb;
	}

	public boolean getVipOrMvs()
	{
		return vipOrMvs;
	}

	public void setVipOrMvs(boolean vipOrMvs)
	{
		this.vipOrMvs = vipOrMvs;
	}

	public String getVsp()
	{
		return vsp;
	}

	public void setVsp(String vsp)
	{
		this.vsp = vsp;
	}

	public String getOsb()
	{
		return osb;
	}

	public void setOsb(String osb)
	{
		this.osb = osb;
	}

	@Override
	public int hashCode()
	{
		return new HashCodeBuilder()
				.append(getNormalizedName())
				.append(StringUtils.deleteWhitespace(getDocument()))
				.append(DateHelper.startOfDay(getBirthday()))
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof MigrationClient)
		{
			MigrationClient that = (MigrationClient) obj;
			return new EqualsBuilder()
					.append(this.getNormalizedName(),
							that.getNormalizedName())
					.append(StringUtils.deleteWhitespace(this.getDocument()),
							StringUtils.deleteWhitespace(that.getDocument()))
					.append(DateHelper.startOfDay(this.getBirthday()),
							DateHelper.startOfDay(that.getBirthday()))
					.isEquals();
		}
		else
		{
			return false;
		}
	}

	public String getDocument()
	{
		return document;
	}

	public void setDocument(String document)
	{
		this.document = document;
	}

	public String getNormalizedName()
	{
		return StringHelper.normalizePersonName(this.getFirstName() + StringHelper.getEmptyIfNull(this.getMiddleName()) + this.getLastName());
	}
}
