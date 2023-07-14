package com.rssl.phizic.test.wsgateclient.esberibback;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.XSDSchemeValidator;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.test.wsgateclient.esberibback.generated.BackServiceStub;
import com.rssl.phizic.test.wsgateclient.esberibback.generated.BackService_ServiceLocator;
import org.apache.axis.client.Stub;
import org.w3c.dom.Document;

import java.rmi.RemoteException;
import javax.xml.rpc.ServiceException;
import javax.xml.validation.Schema;

/**
 * @author mihaylov
 * @ created 07.11.2010
 * @ $Author$
 * @ $Revision$
 */

public class ESBEribBackServiceClientTest
{
	private BackServiceStub stub;
	private static final String SCHEMA_FILE = "com/rssl/phizic/test/wsgateclient/esberibback/xml/depo.xsd";

	public ESBEribBackServiceClientTest(String urlAddress)
	{
		try
		{
			BackService_ServiceLocator service = new BackService_ServiceLocator();
			stub = (BackServiceStub)service.getbackService();
			stub._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY,urlAddress);
		}
		catch (ServiceException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * �������� ����� �� �������
	 * @param request - ������
	 * @return response
	 * @throws GateException
	 */
	public String getResponse(String request) throws GateException
	{
		try
		{
			return stub.doIFX(request);			
		}
		catch (RemoteException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * ������� ������ �� ���������� �������� ���������� �� �����������
	 * @param executeIds - id-�� ����������� ����������
	 * @param rejectIds - id-�� ���������� ����������
	 * @throws Exception
	 */
	public String updateDocumentsState(String[] executeIds, String[] rejectIds) throws Exception
	{
		if(executeIds == null && rejectIds == null)
			throw new Exception("�� ������ ��������� ��� ���������� �������");

		String[] executeDocIds = executeIds != null ? executeIds
													: new String[]{};

		String[] rejectDocIds  = rejectIds != null  ? rejectIds
													: new String[]{};

		Document requestDocument = UpdateDocStateHelper.getUpdateDocStateRequest(executeDocIds,rejectDocIds);
		String request = convert(requestDocument);
		return getResponse(request);		
	}

	public String updateDocumentsStateXML(String requestStr) throws Exception
	{
		return getResponse(requestStr);
	}

	private String convert(Document document) throws Exception
	{
		Schema schema = XmlHelper.schemaByFileName(SCHEMA_FILE);
		XSDSchemeValidator.validate(schema, document);

		return XmlHelper.convertDomToText(document);
	}

	/**
	 *  �������� �����
	 * @throws Exception
	 */
	public String updateSecDicInfo(String request) throws Exception
	{
		return getResponse(request);
	}

	/**
	 * ������������ ������ �� ���������� ����������� ��
	 * @throws Exception
	 */
	public String updateSecDicInfoReg(String issuer, String securityName, String securityNumber, String securityType,String securityNominal,String securityNominalCur,String  insideCode, String isDelete) throws Exception
	{
		Document requestDocument = UpdateSecurityDictionaryHelper.getSecDicInfoRequest(issuer, securityName, securityNumber, securityType,securityNominal,securityNominalCur, insideCode, isDelete);
		return convert(requestDocument);
	}
}
