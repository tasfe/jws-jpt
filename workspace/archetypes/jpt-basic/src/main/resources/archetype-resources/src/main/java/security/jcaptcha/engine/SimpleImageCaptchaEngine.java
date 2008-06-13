package ${package}.security.jcaptcha.engine;

import java.awt.Color;
import java.security.SecureRandom;
import java.util.Locale;
import java.util.Random;

import com.octo.captcha.CaptchaFactory;
import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.FunkyBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomRangeColorGenerator;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.TwistedAndShearedRandomFontGenerator;
import com.octo.captcha.component.image.textpaster.RandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.CaptchaEngineException;
import com.octo.captcha.engine.image.ImageCaptchaEngine;
import com.octo.captcha.image.ImageCaptcha;
import com.octo.captcha.image.ImageCaptchaFactory;
import com.octo.captcha.image.gimpy.GimpyFactory;

public class SimpleImageCaptchaEngine extends ImageCaptchaEngine {

	private ImageCaptchaFactory[] factories;

	private Random myRandom = new SecureRandom();

	private String acceptedChars = "0123456789";

	private Integer acceptedWordLength = 5;

	private Integer width = 150;

	private Integer height = 50;

	private Integer fontSize = 25;

	private Color textColor = Color.BLACK;

	public String getAcceptedChars() {
		return acceptedChars;
	}

	public void setAcceptedChars(String acceptedChars) {
		this.acceptedChars = acceptedChars;
	}

	public Integer getAcceptedWordLength() {
		return acceptedWordLength;
	}

	public void setAcceptedWordLength(Integer acceptedWordLength) {
		this.acceptedWordLength = acceptedWordLength;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getFontSize() {
		return fontSize;
	}

	public void setFontSize(Integer fontSize) {
		this.fontSize = fontSize;
	}

	public Color getTextColor() {
		return textColor;
	}

	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	public void initialize() {
		int[] redRange = { 204, 255 };
		int[] greenRange = { 204, 255 };
		int[] blueRange = { 204, 255 };
		int[] alphaRange = { 0, 255 };
		FontGenerator fontGenerator = new TwistedAndShearedRandomFontGenerator(
				fontSize, fontSize);
		BackgroundGenerator backgroundGenerator = new FunkyBackgroundGenerator(
				width, height, new RandomRangeColorGenerator(redRange,
						greenRange, blueRange, alphaRange));
		TextPaster textPaster = new RandomTextPaster(acceptedWordLength,
				acceptedWordLength, textColor);
		WordToImage wordToImage = new ComposedWordToImage(fontGenerator,
				backgroundGenerator, textPaster);
		WordGenerator wordGenerator = new RandomWordGenerator(acceptedChars);
		factories = new ImageCaptchaFactory[] { new GimpyFactory(wordGenerator,
				wordToImage) };
	}

	public final ImageCaptchaFactory getImageCaptchaFactory() {
		return factories[myRandom.nextInt(factories.length)];
	}

	@Override
	public final ImageCaptcha getNextImageCaptcha() {
		return getImageCaptchaFactory().getImageCaptcha();
	}

	@Override
	public ImageCaptcha getNextImageCaptcha(Locale locale) {
		return getImageCaptchaFactory().getImageCaptcha(locale);
	}

	public CaptchaFactory[] getFactories() {
		return factories;
	}

	public void setFactories(CaptchaFactory[] factories)
			throws CaptchaEngineException {
		if (factories == null || factories.length == 0) {
			throw new CaptchaEngineException(
					"impossible to set null or empty factories");
		}

		for (CaptchaFactory factory : factories) {
			if (!ImageCaptchaFactory.class.isAssignableFrom(factory.getClass())) {
				throw new CaptchaEngineException(
						"This factory is not an image captcha factory "
								+ factory.getClass());
			}

		}
		this.factories = new ImageCaptchaFactory[factories.length];
		System.arraycopy(factories, 0, this.factories, 0, factories.length);
	}

}
