package com.rssl.phizic.business.certification;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.security.ConfirmableObject;
import com.sun.org.apache.xml.internal.serialize.Method;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import org.xml.sax.SAXException;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA. User: Omeliyanchuk Date: 17.11.2006 Time: 10:39:30 To change this template use
 * File | Settings | File Templates.
 */
public class CertDemand implements ConfirmableObject
{
	private Long id;
	private Calendar issueDate;
	private String status;
	private CommonLogin login;
	private String certRequest;
	private String certRequestFile;
	private String certRequestAnswer;
	private String certRequestAnswerFile;
	private String certDemandCryptoId;
	private String refuseReason;
	private Boolean signed = false;//подписан или нет запрос

	public Boolean getSigned()
	{
		return signed;
	}

	public void setSigned(Boolean signed)
	{
		this.signed = signed;
	}

	public String getRefuseReason()
	{
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason)
	{
		this.refuseReason = refuseReason;
	}

	public String getCertDemandCryptoId()
	{
		return certDemandCryptoId;
	}

	public void setCertDemandCryptoId(String certDemandCryptoId)
	{
		this.certDemandCryptoId = certDemandCryptoId;
	}

	public String getCertRequestAnswer()
	{
		return certRequestAnswer;
	}

	public void setCertRequestAnswer(String certRequestAnswer)
	{
		this.certRequestAnswer = certRequestAnswer;
	}

	public String getCertRequestAnswerFile()
	{
		return certRequestAnswerFile;
	}

	public void setCertRequestAnswerFile(String certRequestAnswerFile)
	{
		this.certRequestAnswerFile = certRequestAnswerFile;
	}

	public Long getId()
	{
		return id;
	}

	public byte[] getSignableObject()
	{
		try
		{
			String cryptoId = getCertDemandCryptoId();
			String issueDate = getIssueDate().toString();
			String fullname   = (new PersonService()).findByLogin((Login)getLogin()).getFullName();


			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			XMLSerializer serializer = new XMLSerializer(stream, new OutputFormat(Method.XML, "UTF-8", false));
			serializer.startDocument();
			serializer.startElement("signable-document", null);

			serializer.startElement("cryptoId", null);
			serializer.characters(cryptoId.toCharArray(), 0, cryptoId.length());
			serializer.endElement("cryptoId");

			serializer.startElement("issueDate", null);
			serializer.characters(issueDate.toCharArray(), 0, issueDate.length());
			serializer.endElement("issueDate");

			serializer.startElement("fullname", null);
			serializer.characters(fullname.toCharArray(), 0, fullname.length());
			serializer.endElement("fullname");

			serializer.endElement("signable-document");
			serializer.endDocument();

			return stream.toByteArray();
		}
		catch (BusinessException ex)
		{
			throw new RuntimeException("Ошибка при создании документа для пoдписания", ex);
		}
		catch (SAXException ex)
		{
			throw new RuntimeException("Ошибка при создании документа для подписания", ex);
		}
	}

	public void setConfirmStrategyType(ConfirmStrategyType confirmStrategyType)
	{
		
	}

	public void setId( Long id)
	{
		this.id = id;
	}

	public Calendar getIssueDate()
	{
		return issueDate;
	}

	public void setIssueDate(Calendar issueDate)
	{
		this.issueDate = issueDate;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public CommonLogin getLogin()
	{
		return login;
	}

	public void setLogin(CommonLogin login)
	{
		this.login = login;
	}

	public String getCertRequest()
	{
		return certRequest;
	}

	public void setCertRequest(String certRequest)
	{
		this.certRequest = certRequest;
	}

	public String getCertRequestFile()
	{
		return certRequestFile;
	}

	public void setCertRequestFile(String certRequestFile)
	{
		this.certRequestFile = certRequestFile;
		if(certRequestFile!=null)
			certDemandCryptoId = certRequestFile.substring(0, certRequestFile.indexOf(".agr"));
	}

	public String getIssueDate2String()
	{
		return String.format("%1$td.%1$tm.%1$tY", issueDate);
	}
}
