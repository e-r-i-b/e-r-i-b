package com.rssl.phizic.asfilial.listener;

import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.asfilial.listener.generated.IdentityCardType;
import com.rssl.phizic.asfilial.listener.generated.IdentityType;
import com.rssl.phizic.asfilial.listener.generated.StatusType;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.ermb.migration.MigrationHelper;
import com.rssl.phizic.business.ermb.migration.list.task.migration.asfilial.ASFilialReturnCode;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gladishev
 * @ created 17.12.13
 * @ $Author$
 * @ $Revision$
 *
 * ������� ������ ������������ ��� ������������ ������� � �� ������
 */
public class ASFilialServiceBaseHelper extends MigrationHelper
{
	protected static final Map<ASFilialReturnCode,String> codeDescription = new HashMap<ASFilialReturnCode,String>();
    static
    {
	    codeDescription.put(ASFilialReturnCode.BUSINES_ERROR, "������ ������");
	    codeDescription.put(ASFilialReturnCode.TECHNICAL_ERROR, "����������� ������");
	    codeDescription.put(ASFilialReturnCode.PROFILE_NOT_FOUND, "������� �� ������");
	    codeDescription.put(ASFilialReturnCode.FORMAT_ERROR, "������ �������");
	    codeDescription.put(ASFilialReturnCode.CONFIRM_HOLDER_ERR, "��� ������ %s ��� ������ �� ������ ��� ������������� ��������� ������.");
	    codeDescription.put(ASFilialReturnCode.DUPLICATION_PHONE_ERR, "����� ��������  �����: ���� ��� ��������� ��������� ��������� ���������������� �� ������ ���");
	    codeDescription.put(ASFilialReturnCode.MB_NOT_CONNECT, "������ �� �� ����������");
	    codeDescription.put(ASFilialReturnCode.MB_HAVE_THIRD_PARTIES_ACCOUNTS, "��������� �������������� ����� ���������� �������������� ���������� ��������� ���� � ���������.");
	    codeDescription.put(ASFilialReturnCode.DEPARTMENT_NOT_FOUND, "������������� �� �������");
    }

	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 * ���������� ���������� � ���������� �������.
	 * @param status ���������� � ���������� �������.
	 * @param statusCode  ��� ��������.
	 */
	public void setStatus(StatusType status, ASFilialReturnCode statusCode)
	{
		setStatus(status, null, statusCode);
	}

	/**
     * ���������� ���������� � ���������� �������.
     * @param status ���������� � ���������� �������.
     * @param logMsg ���������� � ���.
     * @param statusCode  ��� ��������.
     */
    public void setStatus(StatusType status, Object logMsg, ASFilialReturnCode statusCode)
    {
        setStatus(status,logMsg,statusCode,null);
    }

    /**
     * ���������� ���������� � ���������� �������.
     * @param status ���������� � ���������� �������
     * @param logMsg ���������� � ���.
     * @param statusCode ��� ��������.
     * @param replaceStr ����� ������.
     */
    public void setStatus(StatusType status, Object logMsg, ASFilialReturnCode statusCode,String replaceStr)
    {
        String codeDesc = codeDescription.get(statusCode);

        if (!StringHelper.isEmpty(replaceStr))
            codeDesc = String.format(codeDesc,replaceStr);

        setStatus(status,logMsg,statusCode.toValue(),codeDesc);
    }


    /**
     * ���������� ���������� � ���������� �������.
     * @param status ���������� � ���������� �������.
     * @param logMsg ���������� � ���.
     * @param code ��� ��������.
     * @param codeDesc �������� ���� ��������.
     */
    public void setStatus(StatusType status, Object logMsg,Long code ,String codeDesc)
    {
	    if (logMsg != null)
			log.error(logMsg);

        status.setStatusDesc(codeDesc);
        status.setStatusCode(code);
    }

    /**
     * ���������� ���������� � ���������� �������.
     * @param status  ���������� � ���������� �������
     * @param clientInd ����������������� ������ �������
     */
    public void setClientNotFoundMsg(StatusType status,IdentityType clientInd)
    {
        String patrName = StringHelper.getNullIfEmpty(clientInd.getMiddleName());
        String surName = clientInd.getLastName();
        String firstName = clientInd.getFirstName();
        IdentityCardType docInd = clientInd.getIdentityCard();
        String docSeries = StringHelper.getNullIfEmpty(docInd.getIdSeries());
        String docNumber = docInd.getIdNum();
        String tb = clientInd.getRegionId();
        Calendar birthDate = DateHelper.toCalendar(clientInd.getBirthday());
        setStatus(status, "������ �� ������ � ������� ���:" + surName + " " + firstName + " " + patrName + ",��: " + DateHelper.formatDateToStringWithPoint(birthDate) + ", ����� � ����� ���������: " + docSeries + " " + docNumber, ASFilialReturnCode.PROFILE_NOT_FOUND);
    }

	/**
	 * ��������� ���������� � ������� ��� ������� � ���
	 * @param clientInd  ����������������� ������ �������
	 * @return ���������� � ������������
	 */
	public UserInfo createCsaUserInfo(IdentityType clientInd)
	{
		IdentityCardType identityCard = clientInd.getIdentityCard();
		String passport = DocumentHelper.getPassportWayNumber(identityCard.getIdSeries(), identityCard.getIdNum());
		Calendar birthday = DateHelper.toCalendar(clientInd.getBirthday());
		return new UserInfo(clientInd.getRegionId(), clientInd.getFirstName(), clientInd.getLastName(), clientInd.getMiddleName(), passport, birthday);
	}

	/**
	 * ������� ���������� ���� � ���� ��, ����������� �� ������������
	 * @param clientInd ����������������� ������ �������
	 */
	public void removeTbLeadingZeros(IdentityType clientInd)
	{
		clientInd.setRegionId(StringHelper.removeLeadingZeros(clientInd.getRegionId()));
	}
}
