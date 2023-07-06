package com.farsroid.android;

import com.farsroid.android.SplashActivity;
import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.Context;
import android.content.Intent;
import android.content.res.*;
import android.graphics.*;
import android.graphics.Typeface;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
import android.os.*;
import android.os.Vibrator;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.github.chrisbanes.photoview.*;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;
import com.stx.xhb.androidx.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.HashMap;
import java.util.regex.*;
import meorg.jsoup.*;
import org.json.*;
import androidx.fragment.app.FragmentTransaction;
import com.farsroid.custom.MaterialRippleLayout;
import android.view.inputmethod.EditorInfo;

public class MainActivity extends AppCompatActivity {
	
	FragmentManager fm = getSupportFragmentManager();
	HomeFragmentActivity HomeFrag = new HomeFragmentActivity();
	GamesFragmentActivity GamesFrag = new GamesFragmentActivity();
	AppsFragmentActivity AppsFrag = new AppsFragmentActivity();
	private HashMap<String, Object> updateMap = new HashMap<>();
	private double appsCnt = 0;
	private double gamesCnt = 0;
	private boolean isSearchBar = false;
	
	private LinearLayout toolbarsHolder;
	private FrameLayout frame;
	private BottomNavigationView bottomNavigation;
	private LinearLayout toolbar;
	private LinearLayout searchBar;
	private LinearLayout toolbar_more_holder;
	private LinearLayout toolbar_font;
	private LinearLayout toolbar_search_holder;
	private ImageView toolbar_more;
	private ImageView img_font;
	private ImageView toolbar_search;
	private EditText search_input;
	private LinearLayout searchIcon_Holder;
	private ImageView searchIcon;
	
	private Vibrator Vib;
	private Intent si = new Intent();
	private Intent mailIn = new Intent();
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		toolbarsHolder = findViewById(R.id.toolbarsHolder);
		frame = findViewById(R.id.frame);
		bottomNavigation = findViewById(R.id.bottomNavigation);
		toolbar = findViewById(R.id.toolbar);
		searchBar = findViewById(R.id.searchBar);
		toolbar_more_holder = findViewById(R.id.toolbar_more_holder);
		toolbar_font = findViewById(R.id.toolbar_font);
		toolbar_search_holder = findViewById(R.id.toolbar_search_holder);
		toolbar_more = findViewById(R.id.toolbar_more);
		img_font = findViewById(R.id.img_font);
		toolbar_search = findViewById(R.id.toolbar_search);
		search_input = findViewById(R.id.search_input);
		searchIcon_Holder = findViewById(R.id.searchIcon_Holder);
		searchIcon = findViewById(R.id.searchIcon);
		Vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		
		bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(MenuItem item) {
				final int _itemId = item.getItemId();
				switch((int)_itemId) {
					case ((int)0): {
						FragmentTransaction ft = fm.beginTransaction();
						ft.replace(R.id.frame, AppsFrag);
						ft.commit();
						break;
					}
					case ((int)1): {
						FragmentTransaction ft = fm.beginTransaction();
						ft.replace(R.id.frame, HomeFrag);
						ft.commit();
						break;
					}
					case ((int)2): {
						FragmentTransaction ft = fm.beginTransaction();
						ft.replace(R.id.frame, GamesFrag);
						ft.commit();
						break;
					}
				}
				return true;
			}
		});
		
		toolbar_search_holder.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				toolbar.setVisibility(View.GONE);
				searchBar.setVisibility(View.VISIBLE);
				isSearchBar = true;
				search_input.requestFocus();
			}
		});
	}
	
	private void initializeLogic() {
		
		_ui();
		_handleSearch();
	}
	
	
	@Override
	public void onBackPressed() {
		if (isSearchBar) {
			toolbar.setVisibility(View.VISIBLE);
			searchBar.setVisibility(View.GONE);
			isSearchBar = false;
		}
		else {
			finishAffinity();
		}
	}
	public void _ui() {
		img_font.setImageResource(R.drawable.farsroid_font);
		MaterialRippleLayout.on(toolbar_more_holder)
		           .rippleColor(Color.WHITE)
		           .rippleRoundedCorners(360)
		           .create();
		MaterialRippleLayout.on(toolbar_search_holder)
		           .rippleColor(Color.WHITE)
		           .rippleRoundedCorners(360)
		           .create();
		bottomNavigation.getMenu().add(0, 0, 0, "برنامه ها").setIcon(R.drawable.ic_apps);
		bottomNavigation.getMenu().add(0, 1, 0, "خانه").setIcon(R.drawable.ic_home);
		bottomNavigation.getMenu().add(0, 2, 0, "بازی ها").setIcon(R.drawable.ic_games);
		bottomNavigation.setSelectedItemId(1);
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.frame, HomeFrag);
		ft.commit();
		_popup();
		search_input.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/vazir.ttf"), 0);
	}
	
	
	public void _popup() {
		toolbar_more_holder.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				Vib.vibrate((long)(100));
				View popupView = getLayoutInflater().inflate(R.layout.pop_up, null);
				final PopupWindow popup = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
				
				TextView i2 = popupView.findViewById(R.id.i2);
				TextView i3 = popupView.findViewById(R.id.i3);
				TextView i4 = popupView.findViewById(R.id.i4);
				
				LinearLayout l2 = popupView.findViewById(R.id.l2);
				LinearLayout l3 = popupView.findViewById(R.id.l3);
				LinearLayout l4 = popupView.findViewById(R.id.l4);
				
				
				i2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/vazir.ttf"), 0);
				i3.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/vazir.ttf"), 0);
				i4.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/vazir.ttf"), 0);
				MaterialRippleLayout.on(l2)
				           .rippleColor(Color.BLACK)
				           .rippleRoundedCorners(360)
				           .create();
				MaterialRippleLayout.on(l3)
				           .rippleColor(Color.BLACK)
				           .rippleRoundedCorners(360)
				           .create();
				MaterialRippleLayout.on(l4)
				           .rippleColor(Color.BLACK)
				           .rippleRoundedCorners(360)
				           .create();
				l2.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						Intent i = new Intent();
						i.setAction(Intent.ACTION_VIEW);
						i.setData(Uri.parse(Preferences.main_url + "request"));
						startActivity(i);
					}
				});
				l3.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						_sendMail("درخواست تبلیغات");
					}
				});
				l4.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						_sendMail("تماس با سازنده");
					}
				});
				popup.setAnimationStyle(android.R.style.Animation_Dialog);
				
				popup.showAsDropDown(toolbar_more_holder, 0,0);
				
				popup.setBackgroundDrawable(new BitmapDrawable());
			}
		});
	}
	
	
	public void _handleSearch() {
		search_input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			    @Override
			    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					            si.putExtra("searchKey", search_input.getText().toString());
					            si.setClass(getApplicationContext(), SearchActivity.class);
					            startActivity(si);
					            return true;
					        }
				        return false;
				    }
		});
	}
	
	
	public void _sendMail(final String _sub) {
		mailIn.setAction(Intent.ACTION_SENDTO);
		mailIn.setData(Uri.parse("mailto:"));
		mailIn.putExtra(Intent.EXTRA_EMAIL, new String[]{"farsroidapp@gmail.com"});
		mailIn.putExtra(Intent.EXTRA_SUBJECT, _sub);
		startActivity(mailIn);
	}
	
}