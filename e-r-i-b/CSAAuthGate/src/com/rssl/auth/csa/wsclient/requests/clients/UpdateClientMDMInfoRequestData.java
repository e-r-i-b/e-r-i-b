package com.rssl.auth.csa.wsclient.requests.clients;

import com.rssl.auth.csa.wsclient.RequestConstants;
import com.rssl.auth.csa.wsclient.requests.RequestDataBase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author komarov
 * @ created 13.07.15
 * @ $Author$
 * @ $Revision$
 *
 * ������ ������� �� ���������� ������� ������� �� ���
 */
public class UpdateClientMDMInfoRequestData extends RequestDataBase
{
	private static final String REQUEST_NAME = "updateClientMDMInfoRq";

	private Long profileId;
	private String mdmId;

	/**
	 * �����������
	 * @param profileId ������������� �������
	 * @param mdmId ������������� � ���
	 */
	public UpdateClientMDMInfoRequestData(Long profileId, String mdmId)
	{
		this.profileId = profileId;
		this.mdmId = mdmId;
	}

	public String getName()
	{
		return REQUEST_NAME;
	}

	public Document getBody()
	{
		Document request = createRequest();
		Element root = request.getDocumentElement();
		root.appendChild(createTag(request, RequestConstants.PROFILE_ID, profileId.toString()));
		root.appendChild(createTag(request, RequestConstants.MDM_ID, mdmId));
		return request;
	}
}
