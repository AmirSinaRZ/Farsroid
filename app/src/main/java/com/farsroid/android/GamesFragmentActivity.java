package com.farsroid.android;

import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.Typeface;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.os.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.github.chrisbanes.photoview.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stx.xhb.androidx.*;
import java.io.*;
import java.text.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.regex.*;
import meorg.jsoup.*;
import org.json.*;
import com.farsroid.custom.roozh;
import com.bumptech.glide.Glide;

public class GamesFragmentActivity extends Fragment {
	
	private HashMap<String, Object> map_1 = new HashMap<>();
	private HashMap<String, Object> map_2 = new HashMap<>();
	private double gamesCount = 0;
	
	private ArrayList<HashMap<String, Object>> today_gamesListMap = new ArrayList<>();
	
	private LinearLayout secondToolbar;
	private RecyclerView GamesRecycler;
	private LinearLayout Loading_lin;
	private LinearLayout Llin;
	private LinearLayout Rlin;
	private TextView dateText;
	private TextView title;
	private ProgressBar progressBar;
	
	private RequestNetwork todayReq;
	private RequestNetwork.RequestListener _todayReq_request_listener;
	private Calendar cal = Calendar.getInstance();
	private RequestNetwork todayGames;
	private RequestNetwork.RequestListener _todayGames_request_listener;
	
	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.games_fragment, _container, false);
		initialize(_savedInstanceState, _view);
		initializeLogic();
		return _view;
	}
	
	private void initialize(Bundle _savedInstanceState, View _view) {
		secondToolbar = _view.findViewById(R.id.secondToolbar);
		GamesRecycler = _view.findViewById(R.id.GamesRecycler);
		Loading_lin = _view.findViewById(R.id.Loading_lin);
		Llin = _view.findViewById(R.id.Llin);
		Rlin = _view.findViewById(R.id.Rlin);
		dateText = _view.findViewById(R.id.dateText);
		title = _view.findViewById(R.id.title);
		progressBar = _view.findViewById(R.id.progressBar);
		todayReq = new RequestNetwork((Activity) getContext());
		todayGames = new RequestNetwork((Activity) getContext());
		
		_todayReq_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				map_1 = new Gson().fromJson(_response, new TypeToken<HashMap<String, Object>>(){}.getType());
				map_1 = new Gson().fromJson(map_1.get("data").toString().replace("=", ":"), new TypeToken<HashMap<String, Object>>(){}.getType());
				title.setText(String.valueOf((long)(Double.parseDouble(map_1.get("today_games_count").toString()))).concat(" بازی منتشر شده امروز"));
				gamesCount = Double.parseDouble(map_1.get("today_games_count").toString());
				todayGames.startRequestNetwork(RequestNetworkController.GET, Preferences.main_url + Preferences.today_games_url, "GamesFragmentActivity", _todayGames_request_listener);
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				
			}
		};
		
		_todayGames_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				today_gamesListMap.clear();
				org.jsoup.nodes.Document doc_1_1 = org.jsoup.Jsoup.parse(_response);
				org.jsoup.select.Elements doc_1_2 = doc_1_1.getElementsByClass("abs-fill");
				org.jsoup.select.Elements doc_1_3 = doc_1_1.select("img");
				org.jsoup.select.Elements doc_1_4 = doc_1_1.getElementsByClass("entry-title");
				org.jsoup.select.Elements doc_1_5 = doc_1_1.getElementsByClass("fa-title");
				org.jsoup.select.Elements doc_1_6 = doc_1_1.getElementsByClass("average-rating");
				org.jsoup.nodes.Document doc_1_7 = org.jsoup.Jsoup.parse(doc_1_3.toString());
				org.jsoup.select.Elements doc_1_8 = doc_1_7.select("img");
				try {
					for (int i = 0; i < (int)(gamesCount); i++) {
						map_2 = new HashMap<>();
						map_2.put("farsroid_link", doc_1_2.get(i).attr("href"));
						map_2.put("farsroid_en", doc_1_4.get(i).text());
						map_2.put("farsroid_fa", doc_1_5.get(i).text());
						map_2.put("farsroid_rating", doc_1_6.get(i).text());
						map_2.put("farsroid_img", doc_1_8.get(i * 4).attr("data-src"));
						today_gamesListMap.add(map_2);
					}
					_updateRecycler();
					Loading_lin.setVisibility(View.GONE);
					GamesRecycler.setVisibility(View.VISIBLE);
				} catch (Exception e) {
					_toast("مشکل در سرور های فارسروید.");
				}
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				
			}
		};
	}
	
	private void initializeLogic() {
		_ui();
		_sendRequest();
		cal = Calendar.getInstance();
		int y = Integer.parseInt(new SimpleDateFormat("yyyy").format(cal.getTime()));
		int m = Integer.parseInt(new SimpleDateFormat("MM").format(cal.getTime()));
		int d = Integer.parseInt(new SimpleDateFormat("DD").format(cal.getTime()));
		roozh PersianDate = new roozh();
		PersianDate.gregorianToPersian(y, m, d);
		dateText.setText(PersianDate.toString());
	}
	
	public void _ui() {
		title.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/vazir.ttf"), 0);
		dateText.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/vazir.ttf"), 0);
		secondToolbar.setElevation((float)5);
	}
	
	
	public void _sendRequest() {
		todayReq.startRequestNetwork(RequestNetworkController.GET, Preferences.main_url + Preferences.today_cnt, "GamesFragmentActivity", _todayReq_request_listener);
	}
	
	
	public void _updateRecycler() {
		GamesRecycler.setAdapter(new GamesRecyclerAdapter(today_gamesListMap));
		GamesRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 3));
	}
	
	
	public void _toast(final String _text) {
		LayoutInflater i = getActivity().getLayoutInflater();
		View inflate = getActivity().getLayoutInflater().inflate(R.layout.custom_toast, null); 
		Toast t = Toast.makeText(getContext().getApplicationContext(),"",Toast.LENGTH_LONG);
		t.setView(inflate);
		t.show();
		final TextView msg = (TextView)inflate.findViewById(R.id.msg);
		final LinearLayout toast_bg = (LinearLayout)inflate.findViewById(R.id.toast_bg);
		toast_bg.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)25, 0xFF607D8B));
		msg.setText(_text);
		msg.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/vazir.ttf"), 0);
	}
	
	public class GamesRecyclerAdapter extends RecyclerView.Adapter<GamesRecyclerAdapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public GamesRecyclerAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getActivity().getLayoutInflater();
			View _v = _inflater.inflate(R.layout.recycler_item, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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
}