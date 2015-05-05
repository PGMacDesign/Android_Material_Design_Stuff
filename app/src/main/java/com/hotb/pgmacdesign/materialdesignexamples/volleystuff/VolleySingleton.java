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

	private static ImageLoader imageLoader;
	private static VolleySingleton sInstance = null;
	private RequestQueue requestQueue;

	//This is private because we want to create a single instance, not multiple instances
	private VolleySingleton(){
		//This requires a custom application class to run. Cannot just use 'getContext()'
		requestQueue = Volley.newRequestQueue(MyApplication.getAppContext());

		//Create an image loader and initialize it
		imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {

			//This allocates max memory available to the cache. need to convert down to bits so /1024 /8.
			private LruCache<String, Bitmap> cache = new LruCache<>((int)(Runtime.getRuntime().maxMemory()/1024)/8);
			/**
			 * Caching mechanism. Uses the LruCache.
			 * @param url String url of the bitmap
			 * @return returns a bitmap
			 */
			@Override
			public Bitmap getBitmap(String url) {
				return cache.get(url);
			}

			@Override
			public void putBitmap(String url, Bitmap bitmap) {
				cache.put(url, bitmap);
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

	/**
	 * @return Returns an instance of an ImageLoader
	 */
	public static ImageLoader getImageLoader(){
		return imageLoader;
	}
}
