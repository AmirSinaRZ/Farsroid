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
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stx.xhb.androidx.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import meorg.jsoup.*;
import org.json.*;

public class HomeFragmentActivity extends Fragment {
	
	private Timer _timer = new Timer();
	
	private HashMap<String, Object> parameters_1 = new HashMap<>();
	private HashMap<String, Object> map_1_1 = new HashMap<>();
	private HashMap<String, Object> parameters_2 = new HashMap<>();
	private HashMap<String, Object> map2_1 = new HashMap<>();
	private double randomAd = 0;
	private HashMap<String, Object> parameters_3 = new HashMap<>();
	private HashMap<String, Object> map_3_1 = new HashMap<>();
	private HashMap<String, Object> parameters_4 = new HashMap<>();
	private HashMap<String, Object> map_4_1 = new HashMap<>();
	private boolean ConnectionOne = false;
	private boolean ConnectionTwo = false;
	private boolean ConnectionThree = false;
	private boolean ConnectionFour = false;
	private boolean ConnectionFive = false;
	private boolean ConnectionSix = false;
	
	private ArrayList<String> bannerData = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> recommend_games_listMap = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> introduced_games_listMap = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> AdsListMap = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> recommend_apps_listMap = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> introduced_apps_listMap = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> companiesData = new ArrayList<>();
	
	private LinearLayout Loading_linear;
	private ScrollView vScroll;
	private ProgressBar progressBar;
	private LinearLayout home_bg;
	private XBanner bannerView;
	private LinearLayout linear_recommended_games;
	private LinearLayout linear_introduced_games;
	private CardView adContainer;
	private LinearLayout linear_recommended_apps;
	private LinearLayout linear_introduced_apps;
	private LinearLayout linear_top_companies;
	private LinearLayout linear_upper_1;
	private RecyclerView recommended_gamesRecycler;
	private TextView more_1;
	private LinearLayout line_1;
	private TextView title_text_1;
	private LinearLayout linear_upper_2;
	private RecyclerView introduced_gamesRecycler;
	private TextView more_2;
	private LinearLayout line_2;
	private TextView title_text_2;
	private ImageView adPoster;
	private LinearLayout linear_upper_3;
	private RecyclerView recommended_appsRecycler;
	private TextView more_3;
	private LinearLayout line_3;
	private TextView title_text_3;
	private LinearLayout linear_upper_4;
	private RecyclerView introduced_appsRecycler;
	private TextView more_4;
	private LinearLayout line_4;
	private TextView title_text_4;
	private LinearLayout linear_upper_5;
	private RecyclerView top_companiesRecycler;
	private LinearLayout line_5;
	private TextView title_text_5;
	
	private RequestNetwork getBanner;
	private RequestNetwork.RequestListener _getBanner_request_listener;
	private RequestNetwork req_recommended_games;
	private RequestNetwork.RequestListener _req_recommended_games_request_listener;
	private RequestNetwork req_introduced_games;
	private RequestNetwork.RequestListener _req_introduced_games_request_listener;
	private RequestNetwork getAd;
	private RequestNetwork.RequestListener _getAd_request_listener;
	private Intent intentAd = new Intent();
	private RequestNetwork req_recommend_apps;
	private RequestNetwork.RequestListener _req_recommend_apps_request_listener;
	private RequestNetwork req_introduced_apps;
	private RequestNetwork.RequestListener _req_introduced_apps_request_listener;
	private RequestNetwork getCoes;
	private RequestNetwork.RequestListener _getCoes_request_listener;
	private TimerTask checkConnection;
	private Intent moreInt = new Intent();
	
	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.home_fragment, _container, false);
		initialize(_savedInstanceState, _view);
		initializeLogic();
		return _view;
	}
	
	private void initialize(Bundle _savedInstanceState, View _view) {
		Loading_linear = _view.findViewById(R.id.Loading_linear);
		vScroll = _view.findViewById(R.id.vScroll);
		progressBar = _view.findViewById(R.id.progressBar);
		home_bg = _view.findViewById(R.id.home_bg);
		bannerView = _view.findViewById(R.id.bannerView);
		linear_recommended_games = _view.findViewById(R.id.linear_recommended_games);
		linear_introduced_games = _view.findViewById(R.id.linear_introduced_games);
		adContainer = _view.findViewById(R.id.adContainer);
		linear_recommended_apps = _view.findViewById(R.id.linear_recommended_apps);
		linear_introduced_apps = _view.findViewById(R.id.linear_introduced_apps);
		linear_top_companies = _view.findViewById(R.id.linear_top_companies);
		linear_upper_1 = _view.findViewById(R.id.linear_upper_1);
		recommended_gamesRecycler = _view.findViewById(R.id.recommended_gamesRecycler);
		more_1 = _view.findViewById(R.id.more_1);
		line_1 = _view.findViewById(R.id.line_1);
		title_text_1 = _view.findViewById(R.id.title_text_1);
		linear_upper_2 = _view.findViewById(R.id.linear_upper_2);
		introduced_gamesRecycler = _view.findViewById(R.id.introduced_gamesRecycler);
		more_2 = _view.findViewById(R.id.more_2);
		line_2 = _view.findViewById(R.id.line_2);
		title_text_2 = _view.findViewById(R.id.title_text_2);
		adPoster = _view.findViewById(R.id.adPoster);
		linear_upper_3 = _view.findViewById(R.id.linear_upper_3);
		recommended_appsRecycler = _view.findViewById(R.id.recommended_appsRecycler);
		more_3 = _view.findViewById(R.id.more_3);
		line_3 = _view.findViewById(R.id.line_3);
		title_text_3 = _view.findViewById(R.id.title_text_3);
		linear_upper_4 = _view.findViewById(R.id.linear_upper_4);
		introduced_appsRecycler = _view.findViewById(R.id.introduced_appsRecycler);
		more_4 = _view.findViewById(R.id.more_4);
		line_4 = _view.findViewById(R.id.line_4);
		title_text_4 = _view.findViewById(R.id.title_text_4);
		linear_upper_5 = _view.findViewById(R.id.linear_upper_5);
		top_companiesRecycler = _view.findViewById(R.id.top_companiesRecycler);
		line_5 = _view.findViewById(R.id.line_5);
		title_text_5 = _view.findViewById(R.id.title_text_5);
		getBanner = new RequestNetwork((Activity) getContext());
		req_recommended_games = new RequestNetwork((Activity) getContext());
		req_introduced_games = new RequestNetwork((Activity) getContext());
		getAd = new RequestNetwork((Activity) getContext());
		req_recommend_apps = new RequestNetwork((Activity) getContext());
		req_introduced_apps = new RequestNetwork((Activity) getContext());
		getCoes = new RequestNetwork((Activity) getContext());
		
		more_1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				moreInt.putExtra("title", "بازی های پیشنهادی");
				moreInt.putExtra("link", "cat/game/suggested-games/");
				moreInt.setClass(getContext().getApplicationContext(), MoreActivity.class);
				startActivity(moreInt);
			}
		});
		
		more_2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				moreInt.putExtra("title", "بازی های رونمایی شده");
				moreInt.putExtra("link", "the-latest-unveiled-android-games/");
				moreInt.setClass(getContext().getApplicationContext(), MoreActivity.class);
				startActivity(moreInt);
			}
		});
		
		more_3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				moreInt.putExtra("title", "برنامه های پیشنهادی");
				moreInt.putExtra("link", "cat/game/suggested-apps/");
				moreInt.setClass(getContext().getApplicationContext(), MoreActivity.class);
				startActivity(moreInt);
			}
		});
		
		more_4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				moreInt.putExtra("title", "برنامه های رونمایی شده");
				moreInt.putExtra("link", "the-latest-unveiled-android-apps/");
				moreInt.setClass(getContext().getApplicationContext(), MoreActivity.class);
				startActivity(moreInt);
			}
		});
		
		_getBanner_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				ConnectionOne = true;
				ArrayList<HashMap<String, Object>> map = new ArrayList<>();
				map = new Gson().fromJson(_response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
				for (int i = 0; i < (int)(map.size()); i++) {
					bannerData.add(map.get((int)i).get("img").toString());
				}
				Collections.reverse(bannerData);
				_setupBanner();
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				
			}
		};
		
		_req_recommended_games_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				ConnectionTwo = true;
				org.jsoup.nodes.Document doc_1_1 = org.jsoup.Jsoup.parse(_response);
				org.jsoup.select.Elements doc_1_2 = doc_1_1.getElementsByClass("abs-fill");
				org.jsoup.select.Elements doc_1_3 = doc_1_1.select("img");
				org.jsoup.select.Elements doc_1_4 = doc_1_1.getElementsByClass("entry-title");
				org.jsoup.select.Elements doc_1_5 = doc_1_1.getElementsByClass("fa-title");
				org.jsoup.select.Elements doc_1_6 = doc_1_1.getElementsByClass("average-rating");
				org.jsoup.nodes.Document doc_1_7 = org.jsoup.Jsoup.parse(doc_1_3.toString());
				org.jsoup.select.Elements doc_1_8 = doc_1_7.select("img");
				for (int i = 0; i < (int)(doc_1_2.size()); i++) {
					map_1_1 = new HashMap<>();
					map_1_1.put("farsroid_link", doc_1_2.get(i).attr("href"));
					map_1_1.put("farsroid_en", doc_1_4.get(i).text());
					map_1_1.put("farsroid_fa", doc_1_5.get(i).text());
					map_1_1.put("farsroid_rating", doc_1_6.get(i).text());
					map_1_1.put("farsroid_img", doc_1_8.get(i * 4).attr("data-src"));
					recommend_games_listMap.add(map_1_1);
				}
				_updateRecycler(1);
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				
			}
		};
		
		_req_introduced_games_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				ConnectionThree = true;
				org.jsoup.nodes.Document doc_2_1 = org.jsoup.Jsoup.parse(_response);
				org.jsoup.select.Elements doc_2_2 = doc_2_1.getElementsByClass("abs-fill");
				org.jsoup.select.Elements doc_2_3 = doc_2_1.select("img");
				org.jsoup.select.Elements doc_2_4 = doc_2_1.getElementsByClass("entry-title");
				org.jsoup.select.Elements doc_2_5 = doc_2_1.getElementsByClass("fa-title");
				org.jsoup.select.Elements doc_2_6 = doc_2_1.getElementsByClass("average-rating");
				org.jsoup.nodes.Document doc_2_7 = org.jsoup.Jsoup.parse(doc_2_3.toString());
				org.jsoup.select.Elements doc_2_8 = doc_2_7.select("img");
				for (int i = 0; i < (int)(doc_2_2.size()); i++) {
					map2_1 = new HashMap<>();
					map2_1.put("farsroid_link", doc_2_2.get(i).attr("href"));
					map2_1.put("farsroid_en", doc_2_4.get(i).text());
					map2_1.put("farsroid_fa", doc_2_5.get(i).text());
					map2_1.put("farsroid_rating", doc_2_6.get(i).text());
					map2_1.put("farsroid_img", doc_2_8.get(i * 4).attr("data-src"));
					introduced_games_listMap.add(map2_1);
				}
				_updateRecycler(2);
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				
			}
		};
		
		_getAd_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				adContainer.setVisibility(View.VISIBLE);
				AdsListMap = new Gson().fromJson(_response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
				randomAd = SketchwareUtil.getRandom((int)(0), (int)(AdsListMap.size() - 1));
				Glide.with(getContext().getApplicationContext()).load(Uri.parse(AdsListMap.get((int)randomAd).get("poster").toString())).into(adPoster);
				adContainer.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						intentAd.setAction(Intent.ACTION_VIEW);
						intentAd.setData(Uri.parse(AdsListMap.get((int)randomAd).get("link").toString()));
						startActivity(intentAd);
					}
				});
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				adContainer.setVisibility(View.GONE);
			}
		};
		
		_req_recommend_apps_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				ConnectionFour = true;
				org.jsoup.nodes.Document doc_3_1 = org.jsoup.Jsoup.parse(_response);
				org.jsoup.select.Elements doc_3_2 = doc_3_1.getElementsByClass("abs-fill");
				org.jsoup.select.Elements doc_3_3 = doc_3_1.select("img");
				org.jsoup.select.Elements doc_3_4 = doc_3_1.getElementsByClass("entry-title");
				org.jsoup.select.Elements doc_3_5 = doc_3_1.getElementsByClass("fa-title");
				org.jsoup.select.Elements doc_3_6 = doc_3_1.getElementsByClass("average-rating");
				org.jsoup.nodes.Document doc_3_7 = org.jsoup.Jsoup.parse(doc_3_1.toString());
				org.jsoup.select.Elements doc_3_8 = doc_3_7.select("img");
				for (int i = 0; i < (int)(doc_3_2.size()); i++) {
					map_3_1 = new HashMap<>();
					map_3_1.put("farsroid_link", doc_3_2.get(i).attr("href"));
					map_3_1.put("farsroid_en", doc_3_4.get(i).text());
					map_3_1.put("farsroid_fa", doc_3_5.get(i).text());
					map_3_1.put("farsroid_rating", doc_3_6.get(i).text());
					map_3_1.put("farsroid_img", doc_3_8.get(i * 4).attr("data-src"));
					recommend_apps_listMap.add(map_3_1);
				}
				_updateRecycler(3);
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				
			}
		};
		
		_req_introduced_apps_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				ConnectionFive = true;
				org.jsoup.nodes.Document doc_4_1 = org.jsoup.Jsoup.parse(_response);
				org.jsoup.select.Elements doc_4_2 = doc_4_1.getElementsByClass("abs-fill");
				org.jsoup.select.Elements doc_4_3 = doc_4_1.select("img");
				org.jsoup.select.Elements doc_4_4 = doc_4_1.getElementsByClass("entry-title");
				org.jsoup.select.Elements doc_4_5 = doc_4_1.getElementsByClass("fa-title");
				org.jsoup.select.Elements doc_4_6 = doc_4_1.getElementsByClass("average-rating");
				org.jsoup.nodes.Document doc_4_7 = org.jsoup.Jsoup.parse(doc_4_3.toString());
				org.jsoup.select.Elements doc_4_8 = doc_4_7.select("img");
				for (int i = 0; i < (int)(doc_4_2.size()); i++) {
					map_4_1 = new HashMap<>();
					map_4_1.put("farsroid_link", doc_4_2.get(i).attr("href"));
					map_4_1.put("farsroid_en", doc_4_4.get(i).text());
					map_4_1.put("farsroid_fa", doc_4_5.get(i).text());
					map_4_1.put("farsroid_rating", doc_4_6.get(i).text());
					map_4_1.put("farsroid_img", doc_4_8.get(i * 4).attr("data-src"));
					introduced_apps_listMap.add(map_4_1);
				}
				_updateRecycler(4);
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				
			}
		};
		
		_getCoes_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				ConnectionSix = true;
				companiesData = new Gson().fromJson(_response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
				_updateRecycler(5);
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				
			}
		};
	}
	
	private void initializeLogic() {
		bannerData.clear();
		recommend_games_listMap.clear();
		introduced_games_listMap.clear();
		AdsListMap.clear();
		recommend_apps_listMap.clear();
		introduced_apps_listMap.clear();
		companiesData.clear();
		_sendRequest();
		_ui();
		checkConnection = new TimerTask() {
			@Override
			public void run() {
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (ConnectionOne && (ConnectionTwo && (ConnectionThree && (ConnectionFour && (ConnectionFive && ConnectionSix))))) {
							Loading_linear.setVisibility(View.GONE);
							home_bg.setVisibility(View.VISIBLE);
							checkConnection.cancel();
						}
					}
				});
			}
		};
		_timer.scheduleAtFixedRate(checkConnection, (int)(100), (int)(100));
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		ConnectionOne = false;
		ConnectionTwo = false;
		ConnectionThree = false;
		ConnectionFour = false;
		ConnectionFive = false;
		ConnectionSix = false;
		checkConnection.cancel();
	}
	public void _sendRequest() {
		getBanner.startRequestNetwork(RequestNetworkController.GET, Preferences.banner_url, "HomeFragmentActivity", _getBanner_request_listener);
		parameters_1.put(Preferences.recommended_games_keys[0], Preferences.recommended_games[1]);
		parameters_1.put(Preferences.recommended_games_keys[1], Preferences.recommended_games[2]);
		parameters_1.put(Preferences.recommended_games_keys[2], Preferences.recommended_games[3]);
		parameters_1.put(Preferences.recommended_games_keys[3], Preferences.recommended_games[4]);
		parameters_1.put(Preferences.recommended_games_keys[4], Preferences.recommended_games[5]);
		req_recommended_games.setParams(parameters_1, RequestNetworkController.REQUEST_PARAM);
		req_recommended_games.startRequestNetwork(RequestNetworkController.POST, Preferences.main_url + Preferences.recommended_games[0], "HomeFragmentActivity", _req_recommended_games_request_listener);
		parameters_2.put(Preferences.introduced_games_keys[0], Preferences.introduced_games[1]);
		parameters_2.put(Preferences.introduced_games_keys[1], Preferences.introduced_games[2]);
		parameters_2.put(Preferences.introduced_games_keys[2], Preferences.introduced_games[3]);
		parameters_2.put(Preferences.introduced_games_keys[3], Preferences.introduced_games[4]);
		parameters_2.put(Preferences.introduced_games_keys[4], Preferences.introduced_games[5]);
		parameters_2.put(Preferences.introduced_games_keys[5], Preferences.introduced_games[6]);
		req_introduced_games.setParams(parameters_2, RequestNetworkController.REQUEST_PARAM);
		req_introduced_games.startRequestNetwork(RequestNetworkController.POST, Preferences.main_url + Preferences.introduced_games[0], "HomeFragmentActivity", _req_introduced_games_request_listener);
		getAd.startRequestNetwork(RequestNetworkController.GET, Preferences.ad_url, "HomeFragmentActivity", _getAd_request_listener);
		parameters_3.put(Preferences.recommended_apps_keys[0], Preferences.recommended_apps[1]);
		parameters_3.put(Preferences.recommended_apps_keys[1], Preferences.recommended_apps[2]);
		parameters_3.put(Preferences.recommended_apps_keys[2], Preferences.recommended_apps[3]);
		parameters_3.put(Preferences.recommended_apps_keys[3], Preferences.recommended_apps[4]);
		parameters_3.put(Preferences.recommended_apps_keys[4], Preferences.recommended_apps[5]);
		req_recommend_apps.setParams(parameters_3, RequestNetworkController.REQUEST_PARAM);
		req_recommend_apps.startRequestNetwork(RequestNetworkController.POST, Preferences.main_url + Preferences.recommended_apps[0], "HomeFragmentActivity", _req_recommend_apps_request_listener);
		parameters_4.put(Preferences.introduced_apps_keys[0], Preferences.introduced_apps[1]);
		parameters_4.put(Preferences.introduced_apps_keys[1], Preferences.introduced_apps[2]);
		parameters_4.put(Preferences.introduced_apps_keys[2], Preferences.introduced_apps[3]);
		parameters_4.put(Preferences.introduced_apps_keys[3], Preferences.introduced_apps[4]);
		parameters_4.put(Preferences.introduced_apps_keys[4], Preferences.introduced_apps[5]);
		parameters_4.put(Preferences.introduced_apps_keys[5], Preferences.introduced_apps[6]);
		req_introduced_apps.setParams(parameters_4, RequestNetworkController.REQUEST_PARAM);
		req_introduced_apps.startRequestNetwork(RequestNetworkController.POST, Preferences.main_url + Preferences.introduced_apps[0], "HomeFragmentActivity", _req_introduced_apps_request_listener);
		getCoes.startRequestNetwork(RequestNetworkController.GET, Preferences.co_url, "HomeFragmentActivity", _getCoes_request_listener);
	}
	
	
	public void _setupBanner() {
		
		bannerView.setData(R.layout.banner_item , bannerData, null);
		bannerView.loadImage(new com.stx.xhb.androidx.XBanner.XBannerAdapter() {
					    @Override
					    public void loadBanner(final com.stx.xhb.androidx.XBanner banner, final Object model, final View vw, final int po) {
				                
					           	final ImageView poster = (ImageView)vw.findViewById(R.id.poster);
				                   String img_link = bannerData.get(po);
					           	Glide.with(getContext().getApplicationContext()).load(Uri.parse(img_link)).into(poster);
				        
								 }
					  });
		bannerView.setBannerCurrentItem(bannerData.size() - 1);
	}
	
	
	public void _ui() {
		more_1.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/vazir.ttf"), 0);
		title_text_1.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/vazir.ttf"), 0);
		more_2.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/vazir.ttf"), 0);
		title_text_2.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/vazir.ttf"), 0);
		more_3.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/vazir.ttf"), 0);
		title_text_3.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/vazir.ttf"), 0);
		more_4.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/vazir.ttf"), 0);
		title_text_4.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/vazir.ttf"), 0);
		title_text_5.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/vazir.ttf"), 0);
		vScroll.setVerticalScrollBarEnabled(false);
	}
	
	
	public void _updateRecycler(final double _num) {
		if (_num == 1) {
			recommended_gamesRecycler.setHasFixedSize(false);
			recommended_gamesRecycler.setAdapter(new Recommended_gamesRecyclerAdapter(recommend_games_listMap));
			recommended_gamesRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, true));
		}
		if (_num == 2) {
			introduced_gamesRecycler.setHasFixedSize(false);
			introduced_gamesRecycler.setAdapter(new Introduced_gamesRecyclerAdapter(introduced_games_listMap));
			introduced_gamesRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, true));
		}
		if (_num == 3) {
			recommended_appsRecycler.setHasFixedSize(false);
			recommended_appsRecycler.setAdapter(new Recommended_appsRecyclerAdapter(recommend_apps_listMap));
			recommended_appsRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, true));
		}
		if (_num == 4) {
			introduced_appsRecycler.setHasFixedSize(false);
			introduced_appsRecycler.setAdapter(new Introduced_appsRecyclerAdapter(introduced_apps_listMap));
			introduced_appsRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, true));
		}
		if (_num == 5) {
			top_companiesRecycler.setHasFixedSize(false);
			top_companiesRecycler.setAdapter(new Top_companiesRecyclerAdapter(companiesData));
			top_companiesRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, true));
		}
	}
	
	public class Recommended_gamesRecyclerAdapter extends RecyclerView.Adapter<Recommended_gamesRecyclerAdapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Recommended_gamesRecyclerAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getActivity().getLayoutInflater();
			View _v = _inflater.inflate(R.layout.recycler_item, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final androidx.cardview.widget.CardView itemCard = _view.findViewById(R.id.itemCard);
			final LinearLayout itemBg = _view.findViewById(R.id.itemBg);
			final androidx.cardview.widget.CardView imageCard = _view.findViewById(R.id.imageCard);
			final TextView EnTitle = _view.findViewById(R.id.EnTitle);
			final TextView FaTitle = _view.findViewById(R.id.FaTitle);
			final ImageView thumbnail = _view.findViewById(R.id.thumbnail);
			
			EnTitle.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/vazir.ttf"), 0);
			FaTitle.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/vazir.ttf"), 0);
			EnTitle.setText(_data.get((int)_position).get("farsroid_en").toString());
			FaTitle.setText(_data.get((int)_position).get("farsroid_fa").toString());
			Glide.with(getContext().getApplicationContext())
			.load(Uri.parse(_data.get((int)_position).get("farsroid_img").toString()))
			.placeholder(R.drawable.holder)
			.into(thumbnail);
			itemBg.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					Intent i = new Intent();
					i.putExtra("titles", new String[] {_data.get((int)_position).get("farsroid_en").toString(), _data.get((int)_position).get("farsroid_fa").toString()});
					i.putExtra("Link", _data.get((int)_position).get("farsroid_link").toString());
					i.setClass(getContext().getApplicationContext(), DownloadActivity.class);
					startActivity(i);
				}
			});
		}
		
		@Override
		public int getItemCount() {
			return _data.size();
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder {
			public ViewHolder(View v) {
				super(v);
			}
		}
	}
	
	public class Introduced_gamesRecyclerAdapter extends RecyclerView.Adapter<Introduced_gamesRecyclerAdapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Introduced_gamesRecyclerAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getActivity().getLayoutInflater();
			View _v = _inflater.inflate(R.layout.recycler_item, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final androidx.cardview.widget.CardView itemCard = _view.findViewById(R.id.itemCard);
			final LinearLayout itemBg = _view.findViewById(R.id.itemBg);
			final androidx.cardview.widget.CardView imageCard = _view.findViewById(R.id.imageCard);
			final TextView EnTitle = _view.findViewById(R.id.EnTitle);
			final TextView FaTitle = _view.findViewById(R.id.FaTitle);
			final ImageView thumbnail = _view.findViewById(R.id.thumbnail);
			
			EnTitle.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/vazir.ttf"), 0);
			FaTitle.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/vazir.ttf"), 0);
			EnTitle.setText(_data.get((int)_position).get("farsroid_en").toString());
			FaTitle.setText(_data.get((int)_position).get("farsroid_fa").toString());
			Glide.with(getContext().getApplicationContext())
			.load(Uri.parse(_data.get((int)_position).get("farsroid_img").toString()))
			.placeholder(R.drawable.holder)
			.into(thumbnail);
			itemBg.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					Intent i = new Intent();
					i.putExtra("titles", new String[] {_data.get((int)_position).get("farsroid_en").toString(), _data.get((int)_position).get("farsroid_fa").toString()});
					i.putExtra("Link", _data.get((int)_position).get("farsroid_link").toString());
					i.setClass(getContext().getApplicationContext(), DownloadActivity.class);
					startActivity(i);
				}
			});
		}
		
		@Override
		public int getItemCount() {
			return _data.size();
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder {
			public ViewHolder(View v) {
				super(v);
			}
		}
	}
	
	public class Recommended_appsRecyclerAdapter extends RecyclerView.Adapter<Recommended_appsRecyclerAdapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Recommended_appsRecyclerAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getActivity().getLayoutInflater();
			View _v = _inflater.inflate(R.layout.recycler_item, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final androidx.cardview.widget.CardView itemCard = _view.findViewById(R.id.itemCard);
			final LinearLayout itemBg = _view.findViewById(R.id.itemBg);
			final androidx.cardview.widget.CardView imageCard = _view.findViewById(R.id.imageCard);
			final TextView EnTitle = _view.findViewById(R.id.EnTitle);
			final TextView FaTitle = _view.findViewById(R.id.FaTitle);
			final ImageView thumbnail = _view.findViewById(R.id.thumbnail);
			
			EnTitle.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/vazir.ttf"), 0);
			FaTitle.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/vazir.ttf"), 0);
			EnTitle.setText(_data.get((int)_position).get("farsroid_en").toString());
			FaTitle.setText(_data.get((int)_position).get("farsroid_fa").toString());
			Glide.with(getContext().getApplicationContext())
			.load(Uri.parse(_data.get((int)_position).get("farsroid_img").toString()))
			.placeholder(R.drawable.holder)
			.into(thumbnail);
			itemBg.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					Intent i = new Intent();
					i.putExtra("titles", new String[] {_data.get((int)_position).get("farsroid_en").toString(), _data.get((int)_position).get("farsroid_fa").toString()});
					i.putExtra("Link", _data.get((int)_position).get("farsroid_link").toString());
					i.setClass(getContext().getApplicationContext(), DownloadActivity.class);
					startActivity(i);
				}
			});
		}
		
		@Override
		public int getItemCount() {
			return _data.size();
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder {
			public ViewHolder(View v) {
				super(v);
			}
		}
	}
	
	public class Introduced_appsRecyclerAdapter extends RecyclerView.Adapter<Introduced_appsRecyclerAdapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Introduced_appsRecyclerAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getActivity().getLayoutInflater();
			View _v = _inflater.inflate(R.layout.recycler_item, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final androidx.cardview.widget.CardView itemCard = _view.findViewById(R.id.itemCard);
			final LinearLayout itemBg = _view.findViewById(R.id.itemBg);
			final androidx.cardview.widget.CardView imageCard = _view.findViewById(R.id.imageCard);
			final TextView EnTitle = _view.findViewById(R.id.EnTitle);
			final TextView FaTitle = _view.findViewById(R.id.FaTitle);
			final ImageView thumbnail = _view.findViewById(R.id.thumbnail);
			
			EnTitle.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/vazir.ttf"), 0);
			FaTitle.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/vazir.ttf"), 0);
			EnTitle.setText(_data.get((int)_position).get("farsroid_en").toString());
			FaTitle.setText(_data.get((int)_position).get("farsroid_fa").toString());
			Glide.with(getContext().getApplicationContext())
			.load(Uri.parse(_data.get((int)_position).get("farsroid_img").toString()))
			.placeholder(R.drawable.holder)
			.into(thumbnail);
			itemBg.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					Intent i = new Intent();
					i.putExtra("titles", new String[] {_data.get((int)_position).get("farsroid_en").toString(), _data.get((int)_position).get("farsroid_fa").toString()});
					i.putExtra("Link", _data.get((int)_position).get("farsroid_link").toString());
					i.setClass(getContext().getApplicationContext(), DownloadActivity.class);
					startActivity(i);
				}
			});
		}
		
		@Override
		public int getItemCount() {
			return _data.size();
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder {
			public ViewHolder(View v) {
				super(v);
			}
		}
	}
	
	public class Top_companiesRecyclerAdapter extends RecyclerView.Adapter<Top_companiesRecyclerAdapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Top_companiesRecyclerAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getActivity().getLayoutInflater();
			View _v = _inflater.inflate(R.layout.coes_item, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final androidx.cardview.widget.CardView cardView = _view.findViewById(R.id.cardView);
			final TextView name = _view.findViewById(R.id.name);
			final ImageView poster = _view.findViewById(R.id.poster);
			
			name.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/vazir.ttf"), 0);
			name.setText(_data.get((int)_position).get("name").toString());
			Glide.with(getContext().getApplicationContext()).load(Uri.parse(_data.get((int)_position).get("img").toString())).into(poster);
		}
		
		@Override
		public int getItemCount() {
			return _data.size();
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder {
			public ViewHolder(View v) {
				super(v);
			}
		}
	}
}