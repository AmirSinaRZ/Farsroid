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
import android.widget.ImageView;
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
import com.stx.xhb.androidx.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;
import meorg.jsoup.*;
import org.json.*;
import com.bumptech.glide.Glide;

public class MoreActivity extends AppCompatActivity {
	
	private String PN = "";
	private double currentPage = 0;
	private boolean isNextPage = false;
	private HashMap<String, Object> map = new HashMap<>();
	private String enString = "";
	private String faString = "";
	
	private ArrayList<HashMap<String, Object>> pagesListMap = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> moreListMap = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> pagesListMapTemp = new ArrayList<>();
	
	private LinearLayout toolbar;
	private LinearLayout counterHolder;
	private RecyclerView moreRecycler;
	private LinearLayout loadingLin;
	private TextView topText;
	private LinearLayout nextPage;
	private LinearLayout pagesHolder;
	private LinearLayout beforePage;
	private ImageView ic_next;
	private RecyclerView pagesRecycler;
	private ImageView ic_before;
	private ProgressBar progressBar;
	
	private RequestNetwork api;
	private RequestNetwork.RequestListener _api_request_listener;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.more);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		toolbar = findViewById(R.id.toolbar);
		counterHolder = findViewById(R.id.counterHolder);
		moreRecycler = findViewById(R.id.moreRecycler);
		loadingLin = findViewById(R.id.loadingLin);
		topText = findViewById(R.id.topText);
		nextPage = findViewById(R.id.nextPage);
		pagesHolder = findViewById(R.id.pagesHolder);
		beforePage = findViewById(R.id.beforePage);
		ic_next = findViewById(R.id.ic_next);
		pagesRecycler = findViewById(R.id.pagesRecycler);
		ic_before = findViewById(R.id.ic_before);
		progressBar = findViewById(R.id.progressBar);
		api = new RequestNetwork(this);
		
		_api_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				moreListMap.clear();
				loadingLin.setVisibility(View.GONE);
				moreRecycler.setVisibility(View.VISIBLE);
				counterHolder.setVisibility(View.VISIBLE);
				if (!isNextPage) {
					_setupPages(_response);
				}
				org.jsoup.nodes.Document doc_1 = org.jsoup.Jsoup.parse(_response);
				org.jsoup.select.Elements el1 = doc_1.getElementsByClass("post-item-wide");
				org.jsoup.select.Elements el2 = doc_1.select("h2.post-item-wide-title > a");
				org.jsoup.select.Elements el3 = doc_1.getElementsByClass("content-cat");
				org.jsoup.nodes.Document doc_2 = org.jsoup.Jsoup.parse(el3.toString());
				org.jsoup.select.Elements el4 = doc_2.select("img");
				for (int i = 0; i < (int)(el1.size()); i++) {
					map = new HashMap<>();
					map.put("farsroid_link", el2.get(i).attr("href"));
					enString = el2.get(i).attr("title").replaceAll("[^a-zA-Z]", " ").trim();
					faString = el2.get(i).attr("title").replaceAll("[^ا-ی]", " ").trim();
					map.put("farsroid_en", enString.trim().replaceAll(" +", " "));
					map.put("farsroid_fa", faString.trim().replaceAll(" +", " "));
					map.put("farsroid_img", el4.get(i * 4).attr("data-src"));
					moreListMap.add(map);
				}
				_refreshRecycler();
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
		_search(getIntent().getStringExtra("link"));
	}
	
	public void _ui() {
		topText.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/vazir.ttf"), 0);
		toolbar.setElevation((float)5);
		topText.setText(getIntent().getStringExtra("title"));
		nextPage.setElevation((float)3);
		beforePage.setElevation((float)3);
		nextPage.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)15, 0xFFFFFFFF));
		beforePage.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)15, 0xFFFFFFFF));
	}
	
	
	public void _search(final String _keyword) {
		api.startRequestNetwork(RequestNetworkController.GET, Preferences.main_url.concat(_keyword), "MoreActivity", _api_request_listener);
	}
	
	
	public void _setupPages(final String _Html) {
		org.jsoup.nodes.Document doc = org.jsoup.Jsoup.parse(_Html);
		org.jsoup.select.Elements el1 = doc.getElementsByClass("page-numbers");
		for (int i = 0; i < (int)(el1.size()); i++) {
			{
				HashMap<String, Object> _item = new HashMap<>();
				_item.put("page", "page".concat(String.valueOf((long)(i))));
				pagesListMap.add(_item);
			}
			
		}
		if (pagesListMap.size() == 0) {
			{
				HashMap<String, Object> _item = new HashMap<>();
				_item.put("page", "page".concat("1"));
				pagesListMap.add(_item);
			}
			
		}
		pagesRecycler.setAdapter(new PagesRecyclerAdapter(pagesListMap));
		pagesRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, true));
		_setupButtons();
	}
	
	
	public void _refresh() {
		pagesRecycler.setAdapter(new PagesRecyclerAdapter(pagesListMapTemp));
		pagesRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, true));
		pagesRecycler.setAdapter(new PagesRecyclerAdapter(pagesListMap));
		pagesRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, true));
		_setupButtons();
	}
	
	
	public String _persianNumber(final String _num) {
		String PN = _num;
		PN = PN.replace("0", "۰");
		PN = PN.replace("1", "۱");
		PN = PN.replace("2", "۲");
		PN = PN.replace("3", "۳");
		PN = PN.replace("4", "۴");
		PN = PN.replace("5", "۵");
		PN = PN.replace("6", "۶");
		PN = PN.replace("7", "۷");
		PN = PN.replace("8", "۸");
		PN = PN.replace("9", "۹");
		return (PN);
	}
	
	
	public void _refreshRecycler() {
		moreRecycler.setAdapter(new MoreRecyclerAdapter(moreListMap));
		moreRecycler.setLayoutManager(new GridLayoutManager(this, 3));
	}
	
	
	public void _searchPage(final String _page) {
		api.startRequestNetwork(RequestNetworkController.GET, Preferences.main_url.concat(getIntent().getStringExtra("link").concat("page/".concat(_page))), "MoreActivity", _api_request_listener);
	}
	
	
	public void _setupButtons() {
		if (currentPage == (pagesListMap.size() - 1)) {
			nextPage.setEnabled(false);
			nextPage.setAlpha((float)(0.7d));
		}
		else {
			nextPage.setEnabled(true);
			nextPage.setAlpha((float)(1));
			nextPage.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					loadingLin.setVisibility(View.VISIBLE);
					moreRecycler.setVisibility(View.GONE);
					counterHolder.setVisibility(View.GONE);
					isNextPage = true;
					currentPage = currentPage + 1;
					_searchPage(String.valueOf((long)(currentPage + 1)));
					_refresh();
				}
			});
		}
		if (currentPage == 0) {
			beforePage.setEnabled(false);
			beforePage.setAlpha((float)(0.7d));
		}
		else {
			beforePage.setEnabled(true);
			beforePage.setAlpha((float)(1));
			beforePage.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					loadingLin.setVisibility(View.VISIBLE);
					moreRecycler.setVisibility(View.GONE);
					counterHolder.setVisibility(View.GONE);
					isNextPage = true;
					currentPage = currentPage - 1;
					_searchPage(String.valueOf((long)(currentPage + 1)));
					_refresh();
				}
			});
		}
	}
	
	public class MoreRecyclerAdapter extends RecyclerView.Adapter<MoreRecyclerAdapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public MoreRecyclerAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getLayoutInflater();
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
			
			EnTitle.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/vazir.ttf"), 0);
			FaTitle.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/vazir.ttf"), 0);
			EnTitle.setText(_data.get((int)_position).get("farsroid_en").toString());
			FaTitle.setText(_data.get((int)_position).get("farsroid_fa").toString());
			Glide.with(getApplicationContext())
			.load(Uri.parse(_data.get((int)_position).get("farsroid_img").toString()))
			.placeholder(R.drawable.holder)
			.into(thumbnail);
			itemBg.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					Intent i = new Intent();
					i.putExtra("titles", new String[] {_data.get((int)_position).get("farsroid_en").toString(), _data.get((int)_position).get("farsroid_fa").toString()});
					i.putExtra("Link", _data.get((int)_position).get("farsroid_link").toString());
					i.setClass(getApplicationContext(), DownloadActivity.class);
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
	
	public class PagesRecyclerAdapter extends RecyclerView.Adapter<PagesRecyclerAdapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public PagesRecyclerAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getLayoutInflater();
			View _v = _inflater.inflate(R.layout.pages_item, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final androidx.cardview.widget.CardView cardView = _view.findViewById(R.id.cardView);
			final LinearLayout linin = _view.findViewById(R.id.linin);
			final LinearLayout uplin = _view.findViewById(R.id.uplin);
			final LinearLayout indicator = _view.findViewById(R.id.indicator);
			final TextView pageText = _view.findViewById(R.id.pageText);
			
			pageText.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/vazir.ttf"), 0);
			pageText.setText(_persianNumber(String.valueOf((long)(_position + 1))));
			linin.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					loadingLin.setVisibility(View.VISIBLE);
					moreRecycler.setVisibility(View.GONE);
					counterHolder.setVisibility(View.GONE);
					isNextPage = true;
					currentPage = _position;
					_searchPage(String.valueOf((long)(_position + 1)));
					_refresh();
				}
			});
			if (currentPage == _position) {
				pageText.setTextColor(0xFF3DA214);
				indicator.setBackgroundColor(0xFF4CB920);
			}
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