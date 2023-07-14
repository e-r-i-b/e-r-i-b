package com.rssl.phizic.web.dictionaries.pfp.products;

import com.rssl.phizic.web.image.ImageEditFormBase;
import com.rssl.phizic.web.validators.ImageSizeValidator;
import com.rssl.common.forms.validators.CompareValidator;

/**
 * @author akrenev
 * @ created 04.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class PFPImageEditFormBase extends ImageEditFormBase
{
	private static final int WIDTH = 64;
	private static final int HEIGHT = 64;
	private static final ImageSizeValidator IMAGE_SIZE_VALIDATOR =
			new ImageSizeValidator(WIDTH, HEIGHT, CompareValidator.EQUAL, CompareValidator.EQUAL);


	protected ImageSizeValidator getImageFileValidator(String imageId)
	{
		return IMAGE_SIZE_VALIDATOR;
	}
}
