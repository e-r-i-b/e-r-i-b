package com.rssl.phizic.auth.modes;

import com.jhlabs.image.WaterFilter;
import com.jhlabs.image.CrystalizeFilter;
import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.UniColorBackgroundGenerator;
import com.octo.captcha.component.image.color.SingleColorGenerator;
import com.octo.captcha.component.image.deformation.ImageDeformation;
import com.octo.captcha.component.image.deformation.ImageDeformationByFilters;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.textpaster.NonLinearTextPaster;
import com.octo.captcha.component.image.wordtoimage.DeformedComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.engine.image.DefaultImageCaptchaEngine;
import com.octo.captcha.engine.image.ImageCaptchaEngine;
import com.octo.captcha.image.ImageCaptchaFactory;
import com.octo.captcha.image.gimpy.GimpyFactory;
import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;
import com.octo.captcha.service.image.ImageCaptchaService;

import java.awt.*;
import java.awt.image.ImageFilter;

/**
 * @author Krenev
 * @ created 27.08.2008
 * @ $Author$
 * @ $Revision$
 */
public class CaptchaServiceSingleton
{

	private static final DefaultManageableImageCaptchaService instance;

	static
	{
		//TODO хорошо бы задавать все это в настройках;)
		WaterFilter water = new WaterFilter();

		water.setAmplitude(1d);
		water.setAntialias(true);
		water.setPhase(20d);
		water.setWavelength(210d);

		CrystalizeFilter crystalize = new CrystalizeFilter();
		crystalize.setScale(0.5);
		crystalize.setGridType(2);
		crystalize.setFadeEdges(false);
		crystalize.setEdgeThickness(0.4);
		crystalize.setRandomness(1.0);

		ImageDeformation backDef = new ImageDeformationByFilters(
				new ImageFilter[]{crystalize});
		ImageDeformation textDef = new ImageDeformationByFilters(
				new ImageFilter[]{water});
		ImageDeformation postDef = new ImageDeformationByFilters(
				new ImageFilter[]{});

		//word generator

		RandomWordGenerator wordGenerator = new RandomWordGenerator("qwertyuiopasdfghjklzxcvbnm1234567890");
		//WordGenerator wordGenerator = new ComposeDictionaryWordGenerator(new FileDictionary("toddlist"));

		//wordtoimage components
		SingleColorGenerator colorGenerator = new SingleColorGenerator(Color.black);

		TextPaster nonLinearPaster = new NonLinearTextPaster(6, 7, colorGenerator, true);


		BackgroundGenerator back = new UniColorBackgroundGenerator(200, 80, Color.white);

		//допустимые шрифты
		Font[] fonts = new Font[]{new Font("Arial",0,7), new Font("Tahoma",1,10), new Font("Comic sans MS",0,9),
								  new Font("Lucida console",2,12)};
		FontGenerator shearedFont = new RandomFontGenerator(30, 35, fonts);

		//word2image 1
		WordToImage wordToImage = new DeformedComposedWordToImage(shearedFont, back, nonLinearPaster, backDef, textDef, postDef);
		ImageCaptchaFactory factory = new GimpyFactory(wordGenerator, wordToImage);
		ImageCaptchaEngine engine = new DefaultImageCaptchaEngine(new ImageCaptchaFactory[]{factory}){};

		instance = new DefaultManageableImageCaptchaService();
		instance.setCaptchaEngine(engine);
	}

	public static ImageCaptchaService getInstance()
	{
		return instance;
	}
}
