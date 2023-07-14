package com.rssl.phizicgate.mobilebank;

import com.sun.xml.txw2.annotation.XmlElement;

import java.util.List;
import javax.xml.bind.annotation.*;

/**
 * User: Moshenko
 * Date: 02.09.13
 * Time: 12:16
 * Бин для разбора тэга template. В Ermb_BeginMigration.
 */
@XmlRootElement(name="templates")
@XmlAccessorType(XmlAccessType.NONE)
class TemplatesBean
{
	@javax.xml.bind.annotation.XmlElement(name = "template")
	private List<Template> template;

	public List<Template> getTemplate()
	{
		return template;
	}

	public void setTemplate(List<Template> template)
	{
		this.template = template;
	}

	@XmlType(name = "TemplateType")
	@XmlAccessorType(XmlAccessType.NONE)
	static class Template
	{
		@javax.xml.bind.annotation.XmlElement(name = "ID")
		private  int id;

		public int getId()
		{
			return id;
		}

		public void setId(int id)
		{
			this.id = id;
		}

		@javax.xml.bind.annotation.XmlElement(name = "operationID")
		private  String operationID;

		public String getOperationID()
		{
			return operationID;
		}

		public void setOperationID(String operationID)
		{
			this.operationID = operationID;
		}

		@javax.xml.bind.annotation.XmlElement(name = "clientAccountID")
		private String clientAccountID;

		public String getClientAccountID()
		{
			return clientAccountID;
		}

		public void setClientAccountID(String clientAccountID)
		{
			this.clientAccountID = clientAccountID;
		}

		@javax.xml.bind.annotation.XmlElement(name = "seq")
		private int seq;

		public int getSeq()
		{
			return seq;
		}

		public void setSeq(int seq)
		{
			this.seq = seq;
		}
	}

}
