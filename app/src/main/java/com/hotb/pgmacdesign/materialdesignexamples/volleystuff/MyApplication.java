package com.hotb.pgmacdesign.materialdesignexamples.volleystuff;

import android.app.Application;
import android.content.Context;

/**
 * Custom application class used in the Volley Singleton
 * Created by pmacdowell on 5/4/2015.
 */
public class MyApplication extends Application {

	/*
	YOU MUST DO THIS FOR THIS TO BE OF ANY USE:
	In the android manifest, under the application tag but before the Activity tags are listed,
	create the following attribute:
	android:name=".MyApplication"
	Note, you may need to change the packages to match if things are organized differently
	 */
	private static MyApplication sInstance;

	public void onCreate(){
		super.onCreate();
		sInstance = this;
	}

	/**
	 * @return Returns an instance of the application class
	 */
	public static MyApplication getsInstance(){
		return sInstance;
	}

	/**
	 * Return the context of the MyApplication class
	 */
	public static Context getAppContext(){
		return sInstance;
	}
}
