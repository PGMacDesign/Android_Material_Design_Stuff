package com.hotb.pgmacdesign.materialdesignexamples.volleystuff;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Singleton class to manage Volley requests
 * Created by pmacdowell on 5/4/2015.
 */
public class VolleySingleton {

	private ImageLoader imageLoader;
	private static VolleySingleton sInstance = null;
	private RequestQueue requestQueue;

	//This is private because we want to create a single instance, not multiple instances
	private VolleySingleton(){
		//This requires a custom application class to run. Cannot just use 'getContext()'
		requestQueue = Volley.newRequestQueue(MyApplication.getAppContext());
		imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {

			private LruCache<String, Bitmap> cache = new LruCache<>();
			/**
			 * Caching mechanism. Uses the LruCache.
			 * @param url
			 * @return
			 */
			@Override
			public Bitmap getBitmap(String url) {
				return null;
			}

			@Override
			public void putBitmap(String url, Bitmap bitmap) {

			}
		});
	}

	/**
	 * @return Returns the singleton created or the existing one
	 */
	public static VolleySingleton getsInstance(){
		if (sInstance == null){
			sInstance = new VolleySingleton();
		}

		return sInstance;
	}

	/**
	 * This actually returns a request queue
	 * @return RequestQueue
	 */
	public RequestQueue getRequestQueue(){
		return requestQueue;
	}
}
