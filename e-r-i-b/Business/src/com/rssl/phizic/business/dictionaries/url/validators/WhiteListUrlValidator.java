package com.rssl.phizic.business.dictionaries.url.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.url.WhiteListUrl;
import com.rssl.phizic.business.dictionaries.url.WhiteListUrlService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * ��������� ��������� ���������� ��� �� ����������� (�������� �� �� � �������)
 *
 * @author akrenev
 * @ created 05.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class WhiteListUrlValidator extends FieldValidatorBase
{
	private static final WhiteListUrlService service = new WhiteListUrlService();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final String URL_DELIMITER = "\\n";
	private static final String ERROR_MESSAGE_PREFIX = "�� ����������� ������� ������. " +
			"����������, ������� ������, ������� ���������� � ������������ URL-������.";

	private String whiteListUrls = StringUtils.EMPTY;

	public String getMessage()
	{
		return ERROR_MESSAGE_PREFIX + whiteListUrls;
	}

	public boolean validate(String url) throws TemporalDocumentException
	{
		if (isValueEmpty(url))
			return true;

		boolean isValid = inWhiteList(url);
		if (isValid)
			return true;

		whiteListUrls = getMaskUrlList();
		return false;
	}

	/**
	 * ����������� �� URL ������ ����������� �������
	 * @param url ������� ���������
	 * @return true - �����������/false - �� �����������
	 */
	private boolean inWhiteList(String url)
	{
		try
		{
			return service.inWhiteList(url);
		}
		catch (BusinessException e)
		{
			log.error("������ ����������� �������������� URL-������ ������ ����������� �������.", e);
			return false;
		}
	}

	/**
	 * �������� ������ ����������� URL-������� � �������
	 * @return ������ ����������� URL-�������
	 */
	private String getMaskUrlList()
	{
		try
		{
			List<WhiteListUrl> list = service.getMaskUrlList();
			String urlList = StringUtils.EMPTY;
			for (WhiteListUrl url : list)
				urlList = urlList + URL_DELIMITER + url.getUrl();

			return  urlList;
		}
		catch (BusinessException e)
		{
			log.error("������ ��������� ������ ����������� URL-�������.", e);
			return StringUtils.EMPTY;
		}
	}
}
