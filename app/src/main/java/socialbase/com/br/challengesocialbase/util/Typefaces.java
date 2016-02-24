package socialbase.com.br.challengesocialbase.util;

import android.content.Context;
import android.graphics.Typeface;

public class Typefaces {

	public static Typeface ralewayTypeface(Context context) {
			return Typeface.createFromAsset(context.getAssets(),
					"fonts/Raleway-Medium.ttf");
	}

	public static Typeface ralewayBoldTypeface(Context context) {
		return Typeface.createFromAsset(context.getAssets(),
				"fonts/Raleway-Bold.ttf");
	}

	public static Typeface typoGroteskTypeface(Context context) {
		return Typeface.createFromAsset(context.getAssets(),
				"fonts/TypoGroteskRounded.otf");
	}
}
