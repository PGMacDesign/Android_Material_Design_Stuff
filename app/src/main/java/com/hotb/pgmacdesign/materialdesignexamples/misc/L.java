package com.hotb.pgmacdesign.materialdesignexamples.misc;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by pmacdowell on 5/5/2015.
 */
public class L {

	public static void m(String message){
		Log.d("Log: ", message);
	}

	public static void t(Context context, String message){
		Toast.makeText(context, message, Toast.LENGTH_SHORT);
	}

	public static void tLong(Context context, String message){
		Toast.makeText(context, message, Toast.LENGTH_LONG);
	}


}

