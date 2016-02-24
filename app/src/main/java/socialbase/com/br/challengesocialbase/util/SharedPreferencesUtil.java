package socialbase.com.br.challengesocialbase.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferencesUtil implements Constants{

	public static void putBoolean(Context context, String key, boolean value) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
	
	public static boolean getBoolean(Context context, String key, boolean defValue) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		return sharedPreferences.getBoolean(key, defValue);
	}
	
	public static void putFloat(Context context, String key, float value) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = sharedPreferences.edit();

		editor.putFloat(key, value);
		editor.commit();
	}
	
	public static float getFloat(Context context, String key, float defValue) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		return sharedPreferences.getFloat(key, defValue);
	}
	
	public static void putInt(Context context, String key, int value) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putInt(key, value);
		editor.commit();
	}
	
	public static int getInt(Context context, String key, int defValue) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		return sharedPreferences.getInt(key, defValue);
	}
	
	public static void putLong(Context context, String key, long value) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putLong(key, value);
		editor.commit();
	}
	
	public static long getLong(Context context, String key, long defValue) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		return sharedPreferences.getLong(key, defValue);
	}
	
	public static void putString(Context context, String key, String value) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	public static String getString(Context context, String key, String defValue) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		return sharedPreferences.getString(key, defValue);
	}

//	public static void putRemovedIdsList (Context context, String key, ArrayList<String> value) {
//		StringBuilder csvList = new StringBuilder();
//		for (String s : value){
//			csvList.append(s);
//			csvList.append(",");
//		}
//		putString(context, KEY_REMOVED_IDS, csvList.toString());
//	}
//
//	public static void putItemInRemovedIdsList (Context context, String value) {
//		ArrayList<String> removedIds = getRemovedIdsList(context);
//		removedIds.add(value);
//		StringBuilder csvList = new StringBuilder();
//		for (String s : removedIds){
//			csvList.append(s);
//			csvList.append(",");
//		}
//		putString(context, KEY_REMOVED_IDS, csvList.toString());
//	}
//	public static ArrayList<String> getRemovedIdsList (Context context) {
//		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//		String csvList = sharedPreferences.getString(KEY_REMOVED_IDS, "");
//		ArrayList<String> list = new ArrayList<String>();
//		if (!csvList.isEmpty()) {
//			String[] items = csvList.split(",");
//			for(int i=0; i < items.length; i++){
//				list.add(items[i]);
//			}
//		}
//		return list;
//	}

}
