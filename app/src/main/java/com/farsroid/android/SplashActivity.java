package com.farsroid.android;

import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.Intent;
import android.content.res.*;
import android.graphics.*;
import android.graphics.Typeface;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
import android.os.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.github.chrisbanes.photoview.*;
import com.stx.xhb.androidx.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import meorg.jsoup.*;
import org.json.*;
import com.wang.avi.AVLoadingIndicatorView;

public class SplashActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	
	private boolean isNoticed = false;
	
	private LinearLayout bg;
	private LinearLayout bg2;
	private ImageView logo;
	private TextView textView;
	private AVLoadingIndicatorView AVview;
	
	private TimerTask timer;
	private RequestNetwork req;
	private RequestNetwork.RequestListener _req_request_listener;
	private Intent i = new Intent();
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.splash);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		bg = findViewById(R.id.bg);
		bg2 = findViewById(R.id.bg2);
		logo = findViewById(R.id.logo);
		textView = findViewById(R.id.textView);
		AVview = findViewById(R.id.AVview);
		req = new RequestNetwork(this);
		
		_req_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				timer = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								i.setClass(getApplicationContext(), MainActivity.class);
								startActivity(i);
								overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
								finish();
							}
						});
					}
				};
				_timer.schedule(timer, (int)(2000));
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				if (!isNoticed) {
					isNoticed = true;
					_toast("خطا در ارتباط با سرور.");
				}
				timer = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								_CheckConnection();
							}
						});
					}
				};
				_timer.schedule(timer, (int)(1000));
			}
		};
	}
	
	private void initializeLogic() {
		_CheckConnection();
		_ui();
	}
	
	public void _ui() {
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
				Window w =SplashActivity.this.getWindow();
				w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
				w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			    w.setStatusBarColor(0xFF4CB920);
			    w.setNavigationBarColor(0xFF4CB920);
		}
		textView.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/vazir.ttf"), 0);
	}
	
	
	public void _CheckConnection() {
		req.startRequestNetwork(RequestNetworkController.GET, Preferences.main_url, "check", _req_request_listener);
	}
	
	
	public void _toast(final String _text) {
		LayoutInflater i = getLayoutInflater();
		View inflate = getLayoutInflater().inflate(R.layout.custom_toast, null); 
		Toast t = Toast.makeText(getApplicationContext(),"",Toast.LENGTH_LONG);
		t.setView(inflate);
		t.show();
		final TextView msg = (TextView)inflate.findViewById(R.id.msg);
		final LinearLayout toast_bg = (LinearLayout)inflate.findViewById(R.id.toast_bg);
		toast_bg.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)25, 0xFF607D8B));
		msg.setText(_text);
		msg.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/vazir.ttf"), 0);
	}
	
}