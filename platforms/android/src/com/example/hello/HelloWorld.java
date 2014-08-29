/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */

package com.example.hello;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import org.apache.cordova.*;

public class HelloWorld extends Activity implements CordovaInterface {

	private String TAG = "CORDOVA_ACTIVITY";
	private final ExecutorService threadPool = Executors.newCachedThreadPool();

	private CordovaWebView cordovaWebView;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
    	super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		cordovaWebView = (CordovaWebView) findViewById(R.id.cordovaWebView);
		Config.init(this);
		cordovaWebView.loadUrl(Config.getStartUrl());
    }
    
    public void count() {
		Log.d(TAG, "count: " + System.currentTimeMillis());
		cordovaWebView.sendJavascript("window.count();");
	}
    
    @Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
    	this.count();
		return super.onTouchEvent(event);
	}


	@Override
	protected void onPause() {
		super.onPause();
		Log.d(TAG, "onPause");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (this.cordovaWebView != null) {
			this.cordovaWebView
					.loadUrl("javascript:try{cordova.require('cordova/channel').onDestroy.fire();}catch(e){console.log('exception firing destroy event from native');};");
			this.cordovaWebView.loadUrl("about:blank");
			cordovaWebView.handleDestroy();
		}
	}

	// Cordova Interface Events: see
	// http://www.infil00p.org/advanced-tutorial-using-cordovawebview-on-android/
	// for more details
	@Override
	public Activity getActivity() {
		return this;
	}

	@Override
	public ExecutorService getThreadPool() {
		return threadPool;
	}

	@Override
	public Object onMessage(String message, Object obj) {
		Log.d(TAG, message);
		if (message.equalsIgnoreCase("exit")) {
			super.finish();
		}
		return null;
	}

	@Override
	public void setActivityResultCallback(CordovaPlugin cordovaPlugin) {
		Log.d(TAG, "setActivityResultCallback is unimplemented");
	}

	@Override
	public void startActivityForResult(CordovaPlugin cordovaPlugin,
			Intent intent, int resultCode) {
		Log.d(TAG, "startActivityForResult is unimplemented");
	}
}

