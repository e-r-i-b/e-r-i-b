package com.rssl.phizic.web.validators;

import com.rssl.common.forms.validators.CompareValidator;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.utils.StringHelper;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;
import org.apache.commons.lang.ArrayUtils;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Map;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
 * Валидатор проверки размера картинки.
 *
 * @author lepihina
 * @ created 14.06.2011
 * @ $Author$
 * @ $Revision$
 */

public class ImageSizeValidator implements FileValidator
{
	static final protected Map<String,int[]> RESULT_TABLE;
	private int width;
	private int height;
	private String operatorWidth;
	private String operatorHeight;

	static
	{
	    RESULT_TABLE = new HashMap<String, int[]>();
	    RESULT_TABLE.put(CompareValidator.EQUAL,      new int[]{0});
		RESULT_TABLE.put(CompareValidator.LESS_EQUAL, new int[]{-1, 0});
		RESULT_TABLE.put(CompareValidator.GREATE_EQUAL, new int[]{0, 1});
	}

	public ImageSizeValidator(int width, int height, String operatorWidth, String operatorHeight)
	{
		this.width = width;
		this.height = height;
		this.operatorWidth = operatorWidth;
		this.operatorHeight = operatorHeight;
	}

	/**
	 * валидация файла изображения
	 * @param file загружаемый файл
	 * @return сообщения об ошибках
	 */
	public ActionMessages validate(FormFile file) throws BusinessException
	{
		return validate(file, width, height, operatorWidth, operatorHeight);
	}

	private static Pair<Integer, Integer> getImageSize(FormFile file) throws Exception
	{
		InputStream inputStream = null;
		try
		{
			inputStream = file.getInputStream();
			BufferedImage bufImage = ImageIO.read(inputStream);
			if (bufImage == null)
				return new Pair<Integer, Integer>();

			return new Pair<Integer, Integer>(bufImage.getWidth(), bufImage.getHeight());
		}
		finally
		{
			if (inputStream != null)
				inputStream.close();
		}
	}

	private static ActionMessages addActionMessage(ActionMessages msgs, String messageKey, Object... params)
	{
		ActionMessage message = new ActionMessage(messageKey, params);
		msgs.add(ActionMessages.GLOBAL_MESSAGE, message);
		return msgs;
	}

	/**
	 * @param file загружаемый файл
	 * @param width установленная ширина картинки
	 * @param height установленная высота картинки
	 * @param operatorWidth  оператор для длины (== или <= или >=)
	 * @param operatorHeight оператор для высоты (== или <= или >=)
	 * @return сообщения об ошибках
	 */
	public static ActionMessages validate(FormFile file, int width, int height, String operatorWidth, String operatorHeight ) throws BusinessException
	{
		ActionMessages msgs = new ActionMessages();
		if (file == null || StringHelper.isEmpty(file.getFileName()))
			return msgs;

		if (!RESULT_TABLE.containsKey(operatorWidth))
			throw new BusinessException("Указан некорректный оператор при сравнении размера картинки [" + operatorWidth + "]");

		if (!RESULT_TABLE.containsKey(operatorHeight))
			throw new BusinessException("Указан некорректный оператор при сравнении размера картинки [" + operatorWidth + "]");

		try
		{
			Pair<Integer, Integer> imageSize = getImageSize(file);
			Integer imageWidth = imageSize.getFirst();
			Integer imageHeight = imageSize.getSecond();

			if (imageWidth == null || imageHeight == null)
				return addActionMessage(msgs, "com.rssl.phizic.web.validators.error.fileReadingError", file.getFileName());

			if (!ArrayUtils.contains(RESULT_TABLE.get(operatorWidth), imageWidth.compareTo(width)))
				addActionMessage(msgs, "com.rssl.phizic.web.validators.error.fileSizeIsNotRight." + operatorWidth + ".width", file.getFileName(), width);

			if (!ArrayUtils.contains(RESULT_TABLE.get(operatorHeight), imageHeight.compareTo(height)))
				addActionMessage(msgs, "com.rssl.phizic.web.validators.error.fileSizeIsNotRight." + operatorHeight + ".height", file.getFileName(), height);

			return msgs;
		}
		catch (Exception ignore)
		{
			ActionMessage message = new ActionMessage("com.rssl.phizic.web.validators.error.fileReadingError", file.getFileName());
			msgs.add(ActionMessages.GLOBAL_MESSAGE, message);
			return msgs;
		}
	}
}
