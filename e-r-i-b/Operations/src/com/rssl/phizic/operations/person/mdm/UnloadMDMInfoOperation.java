package com.rssl.phizic.operations.person.mdm;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.CSAResponseConstants;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.mdm.MDMClientInfo;
import com.rssl.phizic.business.mdm.MDMInfoService;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;


/**
 * @author komarov
 * @ created 20.07.15
 * @ $Author$
 * @ $Revision$
 */
public abstract class UnloadMDMInfoOperation<T extends MDMClientInfo> extends OperationBase implements ViewEntityOperation<byte[]>
{
	protected static final String DELIMITER = "|";
	protected static final String STRING_DELIMITER = "\n";
	protected static final MDMInfoService service = new MDMInfoService();

	private static final String ENCODING = "cp866";
	private byte[] data;

	/**
	 * Инициализация
	 * @param ids идентификатор профилей
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public void initialize(String[] ids) throws BusinessLogicException, BusinessException
	{
		Iterator<T> info = getMdmClientInfo(ids);

		StringBuilder builder = new StringBuilder();
		while (info.hasNext())
		{
			MDMClientInfo inf = info.next();
			Long csaProfileId = getCsaUserId(inf);
			inf.setCsaProfileId(csaProfileId);
			createRecordLine(builder, inf);
		}
		try
		{
			data = builder.toString().getBytes(ENCODING);
		}
		catch (UnsupportedEncodingException e)
		{
			throw new BusinessException(e);
		}
	}

	public byte[] getEntity() throws BusinessException, BusinessLogicException
	{
		//noinspection ReturnOfCollectionOrArrayField
		return data;
	}

	protected abstract Iterator<T> getMdmClientInfo(String[] ids) throws BusinessException;

	protected abstract void createRecordLine(StringBuilder builder, MDMClientInfo info);

	/**
	 * Добавление параметра parameter в конец строки builder и символа-разделителя
	 * @param builder  Строка, в которую нужно вставить параметер
	 * @param parameter Параметер
	 */
	protected void addCell(StringBuilder builder, Object parameter)
	{
		builder.append(StringHelper.getEmptyIfNull(parameter)).append(DELIMITER);
	}

	/**
	 * Добавление параметра parameter в конец строки builder и символа переноса строки /n
	 * @param builder Строка, в которую нужно вставить параметер
	 * @param parameter Параметер
	 */
	protected void addLastCell(StringBuilder builder, Object parameter)
	{
		builder.append(StringHelper.getEmptyIfNull(parameter)).append(STRING_DELIMITER);
	}

	protected Long getCsaUserId(MDMClientInfo clientInfo) throws BusinessException
	{
		try
		{
			Document response = CSABackRequestHelper.getClientProfileIdRequestDataRq(clientInfo.asUserInfo());
			String stringValue =  XmlHelper.getSimpleElementValue(response.getDocumentElement(), CSAResponseConstants.PROFILE_ID_TAG);
			return stringValue == null ? null : Long.parseLong(stringValue);
		}
		catch (Exception e)
		{
			throw new BusinessException("Не удалось получить профиль клиента", e);
		}
	}



}
