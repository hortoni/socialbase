package br.com.socialbase.challengesocialbase.util;

import android.content.Context;
import android.graphics.Typeface;

public class Typefaces {

	public static Typeface ralewayTypeface(Context context) {
			return Typeface.createFromAsset(context.getAssets(),
					"fonts/Raleway-Medium.ttf");
	}
}
