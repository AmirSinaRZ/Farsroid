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
import com.google.android.material.button.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import com.farsroid.custom.htmltextview.*;
import com.farsroid.custom.fab.FloatingActionButton;
import com.farsroid.android.custom.FButton;

public class DownloadActivity extends AppCompatActivity {
	
	private double comment_page = 1;
	private String pID = "";
	private HashMap<String, Object> map = new HashMap<>();
	private HashMap<String, Object> map2 = new HashMap<>();
	private String postVR = "";
	private HashMap<String, Object> map3 = new HashMap<>();
	private HashMap<String, Object> map4 = new HashMap<>();
	private HashMap<String, Object> map5 = new HashMap<>();
	
	private ArrayList<HashMap<String, Object>> downloadListMap = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> galleryListMap = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> commentListMap = new ArrayList<>();
	
	private LinearLayout toolbar;
	private ScrollView vscroll1;
	private LinearLayout loadingLin;
	private LinearLayout toolbar_font;
	private ImageView img_font;
	private LinearLayout linearBG;
	private LinearLayout headerInfo;
	private LinearLayout descHint;
	private HtmlTextView html_text;
	private LinearLayout boxHint;
	private LinearLayout RecyclerHolder;
	private LinearLayout galleryHint;
	private LinearLayout gRholger;
	private LinearLayout linearCommHint;
	private LinearLayout commentBox;
	private CardView cardView;
	private TextView title;
	private TextView faTitle;
	private ImageView thumbnail;
	private LinearLayout lineL;
	private TextView txt1;
	private LinearLayout lineR;
	private LinearLayout lineL2;
	private TextView txt2;
	private LinearLayout lineR2;
	private RecyclerView downloadRecycler;
	private TextView tarefeHint;
	private LinearLayout lineL3;
	private TextView txt3;
	private LinearLayout lineR3;
	private RecyclerView galleryRecycler;
	private LinearLayout lineL4;
	private TextView commentHint;
	private LinearLayout lineR4;
	private RecyclerView commentRecycler;
	private MaterialButton loadMoreComment;
	private ProgressBar progressBar;
	
	private RequestNetwork api;
	private RequestNetwork.RequestListener _api_request_listener;
	private RequestNetwork getBoxApi;
	private RequestNetwork.RequestListener _getBoxApi_request_listener;
	private Intent i = new Intent();
	private RequestNetwork commentApi;
	private RequestNetwork.RequestListener _commentApi_request_listener;
	private RequestNetwork commentAddApi;
	private RequestNetwork.RequestListener _commentAddApi_request_listener;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.download);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		toolbar = findViewById(R.id.toolbar);
		vscroll1 = findViewById(R.id.vscroll1);
		loadingLin = findViewById(R.id.loadingLin);
		toolbar_font = findViewById(R.id.toolbar_font);
		img_font = findViewById(R.id.img_font);
		linearBG = findViewById(R.id.linearBG);
		headerInfo = findViewById(R.id.headerInfo);
		descHint = findViewById(R.id.descHint);
		html_text = findViewById(R.id.html_text);
		boxHint = findViewById(R.id.boxHint);
		RecyclerHolder = findViewById(R.id.RecyclerHolder);
		galleryHint = findViewById(R.id.galleryHint);
		gRholger = findViewById(R.id.gRholger);
		linearCommHint = findViewById(R.id.linearCommHint);
		commentBox = findViewById(R.id.commentBox);
		cardView = findViewById(R.id.cardView);
		title = findViewById(R.id.title);
		faTitle = findViewById(R.id.faTitle);
		thumbnail = findViewById(R.id.thumbnail);
		lineL = findViewById(R.id.lineL);
		txt1 = findViewById(R.id.txt1);
		lineR = findViewById(R.id.lineR);
		lineL2 = findViewById(R.id.lineL2);
		txt2 = findViewById(R.id.txt2);
		lineR2 = findViewById(R.id.lineR2);
		downloadRecycler = findViewById(R.id.downloadRecycler);
		tarefeHint = findViewById(R.id.tarefeHint);
		lineL3 = findViewById(R.id.lineL3);
		txt3 = findViewById(R.id.txt3);
		lineR3 = findViewById(R.id.lineR3);
		galleryRecycler = findViewById(R.id.galleryRecycler);
		lineL4 = findViewById(R.id.lineL4);
		commentHint = findViewById(R.id.commentHint);
		lineR4 = findViewById(R.id.lineR4);
		commentRecycler = findViewById(R.id.commentRecycler);
		loadMoreComment = findViewById(R.id.loadMoreComment);
		progressBar = findViewById(R.id.progressBar);
		api = new RequestNetwork(this);
		getBoxApi = new RequestNetwork(this);
		commentApi = new RequestNetwork(this);
		commentAddApi = new RequestNetwork(this);
		
		loadMoreComment.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				comment_page++;
				String commentLink = getIntent().getStringExtra("Link").concat("comment-page-".concat(String.valueOf((long)(comment_page)).concat("/#comments")));
				commentApi.startRequestNetwork(RequestNetworkController.GET, commentLink, "DownloadActivity", _commentApi_request_listener);
			}
		});
		
		_api_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				loadingLin.setVisibility(View.GONE);
				vscroll1.setVisibility(View.VISIBLE);
				_getImg(_response);
				_getID(_responseHeaders);
				_setContent(_response);
				_getGallery(_response);
				_getComments(_response);
				postVR = _getVersion(_response);
				String boxLink = Preferences.main_url + Preferences.getBox.replace("{id}", pID);
				boxLink = boxLink.replace("{post_version}", postVR.concat("+"));
				getBoxApi.startRequestNetwork(RequestNetworkController.GET, boxLink, "DownloadActivity", _getBoxApi_request_listener);
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				
			}
		};
		
		_getBoxApi_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				map2.clear();
				map2 = new Gson().fromJson(_response, new TypeToken<HashMap<String, Object>>(){}.getType());
				if (!(boolean)map2.get("status")) {
					_tryAgain();
				}
				else {
					_parseBox(map2.get("data").toString());
				}
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				
			}
		};
		
		_commentApi_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				_getComments(_response);
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				
			}
		};
		
		_commentAddApi_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				_addedT();
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
		String[] ts = getIntent().getStringArrayExtra("titles");
		faTitle.setText(ts[1]);
		title.setText(ts[0]);
		api.startRequestNetwork(RequestNetworkController.GET, getIntent().getStringExtra("Link"), "DownloadActivity", _api_request_listener);
		
		FloatingActionButton fab1 = findViewById(R.id._fab1);
		FloatingActionButton fab2 = findViewById(R.id._fab2);
		fab1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setAction(Intent.ACTION_SEND);
				i.putExtra(Intent.EXTRA_TEXT, Preferences.main_url + "?p=".concat(pID));
				i.setType("text/plain");
				startActivity(i);
			}
		});
		fab2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_commentDialog();
			}
		});
	}
	
	public void _ui() {
		vscroll1.setVerticalScrollBarEnabled(false);
		toolbar.setElevation((float)5);
		headerInfo.setElevation((float)5);
		html_text.setElevation((float)5);
		RecyclerHolder.setElevation((float)5);
		gRholger.setElevation((float)5);
		title.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/vazir.ttf"), 0);
		faTitle.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/vazir.ttf"), 0);
		txt1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/vazir.ttf"), 0);
		html_text.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/vazir.ttf"), 0);
		txt2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/vazir.ttf"), 0);
		txt3.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/vazir.ttf"), 0);
		tarefeHint.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/vazir.ttf"), 0);
		commentHint.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/vazir.ttf"), 0);
		loadMoreComment.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/vazir.ttf"), 0);
	}
	
	
	public void _getImg(final String _html) {
		org.jsoup.nodes.Document doc_1 = org.jsoup.Jsoup.parse(_html);
		org.jsoup.select.Elements el1 = doc_1.getElementsByTag("meta");
		for (int i = 0; i < (int)(el1.size()); i++) {
			if (el1.get(i).attr("content").endsWith(".png") || el1.get(i).attr("content").endsWith(".jpg")) {
				Glide.with(getApplicationContext())
				.load(Uri.parse(el1.get(i).attr("content")))
				.placeholder(R.drawable.holder)
				.into(thumbnail);
				break;
			}
		}
	}
	
	
	public void _getID(final HashMap<String, Object> _res) {
		map = new Gson().fromJson(new Gson().toJson(_res), new TypeToken<HashMap<String, Object>>(){}.getType());
		pID = map.get("link").toString().substring((int)(1), (int)(map.get("link").toString().indexOf(">")));
		pID = pID.replace("https://www.farsroid.com/?p=", "");
	}
	
	
	public String _getVersion(final String _html) {
		org.jsoup.nodes.Document doc_1 = org.jsoup.Jsoup.parse(_html);
		org.jsoup.select.Elements el1 = doc_1.getElementsByClass("inf-cnt");
		return (el1.get(2).text().replace("نسخه نهایی", "").trim());
	}
	
	
	public void _setContent(final String _html) {
		org.jsoup.nodes.Document doc_1 = org.jsoup.Jsoup.parse(_html);
		org.jsoup.select.Elements el1 = doc_1.getElementsByClass("post-content");
		String myHtml = el1.toString();
		html_text.setHtml(myHtml,
		    new HtmlHttpImageGetter(html_text));
		html_text.setOnClickATagListener(new OnClickATagListener() {
			
			    @Override
			    public boolean onClick(View widget, String spannedText, @Nullable String href) {
				        i.setAction(Intent.ACTION_VIEW);
				        i.setData(Uri.parse(href));
				        startActivity(i);
				        return true;
				    }
		});
	}
	
	
	public void _parseBox(final String _html) {
		String fixedHtml = _html.replace("{content=", "");
		fixedHtml = fixedHtml.replace("}", "");
		org.jsoup.nodes.Document doc = org.jsoup.Jsoup.parse(fixedHtml);
		org.jsoup.select.Elements el1 = doc.getElementsByClass("download-link grid-row ");
		org.jsoup.select.Elements el2 = doc.getElementsByClass("txt");
		org.jsoup.select.Elements el3 = doc.getElementsByClass("download-link grid-row download-link--last-update");
		for (int i = 0; i < (int)(el2.size()); i++) {
			map3 = new HashMap<>();
			try {
				try {
					map3.put("link", el1.get(i).attr("data-link"));
				} catch (Exception e) {
					map3.put("link", el3.get(i).attr("data-link"));
				}
			} catch (Exception e) {
				map3.put("link", getIntent().getStringExtra("Link"));
			}
			map3.put("title", el2.get(i).text());
			downloadListMap.add(map3);
		}
		downloadRecycler.setAdapter(new DownloadRecyclerAdapter(downloadListMap));
		downloadRecycler.setLayoutManager(new LinearLayoutManager(this));
	}
	
	
	public void _tryAgain() {
		String newBoxLink = Preferences.main_url + Preferences.getBox.replace("{id}", pID);
		newBoxLink = newBoxLink.replace("{post_version}", postVR);
		getBoxApi.startRequestNetwork(RequestNetworkController.GET, newBoxLink, "DownloadActivity", _getBoxApi_request_listener);
	}
	
	
	public void _getGallery(final String _html) {
		org.jsoup.nodes.Document doc = org.jsoup.Jsoup.parse(_html);
		org.jsoup.select.Elements el1 = doc.getElementsByClass("screenshots-gallery");
		org.jsoup.nodes.Document doc2 = org.jsoup.Jsoup.parse(el1.toString());
		org.jsoup.select.Elements el2 = doc2.select("div.swiper-slide > a");
		for (int i = 0; i < (int)(el2.size()); i++) {
			if (el2.get(i).attr("href").endsWith(".png") || el2.get(i).attr("href").endsWith(".jpg")) {
				{
					HashMap<String, Object> _item = new HashMap<>();
					_item.put("img", el2.get(i).attr("href"));
					galleryListMap.add(_item);
				}
				
			}
		}
		galleryRecycler.setAdapter(new GalleryRecyclerAdapter(galleryListMap));
		galleryRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, true));
	}
	
	
	public void _getComments(final String _html) {
		org.jsoup.nodes.Document doc = org.jsoup.Jsoup.parse(_html);
		org.jsoup.select.Elements el1 = doc.getElementsByClass("comments-list-container");
		org.jsoup.nodes.Document doc2 = org.jsoup.Jsoup.parse(el1.toString());
		org.jsoup.select.Elements el2 = doc2.getElementsByClass("author");
		org.jsoup.select.Elements el3 = doc2.getElementsByClass("date");
		org.jsoup.select.Elements el4 = doc2.getElementsByClass("model");
		org.jsoup.select.Elements el5 = doc2.getElementsByClass("android");
		org.jsoup.select.Elements el6 = doc2.getElementsByClass("comment-body-text");
		for (int i = 0; i < (int)(el2.size()); i++) {
			map4 = new HashMap<>();
			map4.put("author", el2.get(i).text());
			map4.put("date", el3.get(i).text());
			try {
				map4.put("model", el4.get(i).text());
				map4.put("android", el5.get(i).text());
			} catch (Exception e) {
				 
			}
			map4.put("body", el6.get(i).text());
			commentListMap.add(map4);
		}
		commentRecycler.setAdapter(new CommentRecyclerAdapter(commentListMap));
		commentRecycler.setLayoutManager(new LinearLayoutManager(this));
		if (commentListMap.size() == 0) {
			linearCommHint.setVisibility(View.GONE);
			commentBox.setVisibility(View.GONE);
			loadMoreComment.setVisibility(View.GONE);
		}
	}
	
	
	public void _download(final double _pos) {
		String DL = downloadListMap.get((int)_pos).get("link").toString();
		String FN = DL.substring((int)(DL.lastIndexOf('/') + 1), (int)(DL.length()));
		Fdownloader.DownloadFarsroid(this, DL, title.getText().toString(), faTitle.getText().toString(), FN);
	}
	
	
	public void _viewImage(final double _pos) {
		AlertDialog iv = new AlertDialog.Builder(this).create();
		
		View inflate = getLayoutInflater().inflate(R.layout.zoom_item, null);
		 
		iv.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		
		iv.setView(inflate);
		
		ImageView photo_view = (ImageView)inflate.findViewById(R.id.photo_view);
		
		Glide.with(getApplicationContext()).load(Uri.parse(galleryListMap.get((int)_pos).get("img").toString())).into(photo_view);
		iv.show();
	}
	
	
	public void _commentDialog() {
		AlertDialog cd = new AlertDialog.Builder(this).create();
		
		View inflate = getLayoutInflater().inflate(R.layout.comment_dialog, null);
		 
		cd.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		
		cd.setView(inflate);
		
		LinearLayout box1 = (LinearLayout)inflate.findViewById(R.id.nameBox);
		
		LinearLayout box2 = (LinearLayout)inflate.findViewById(R.id.numberBox);
		
		LinearLayout box3 = (LinearLayout)inflate.findViewById(R.id.commentBox);
		
		FButton sendBTN = (FButton)inflate.findViewById(R.id.sendBTN);
		
		TextView Txt1 = (TextView)inflate.findViewById(R.id.textview1);
		EditText inputName = (EditText)inflate.findViewById(R.id.inputName);
		EditText inputNumber = (EditText)inflate.findViewById(R.id.inputNumber);
		EditText inputComment = (EditText)inflate.findViewById(R.id.inputComment);
		Txt1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/vazir.ttf"), 0);
		sendBTN.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/vazir.ttf"), 0);
		inputName.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/vazir.ttf"), 0);
		inputNumber.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/vazir.ttf"), 0);
		inputComment.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/vazir.ttf"), 0);
		box1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)15, 0xFFE0E0E0));
		box2.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)15, 0xFFE0E0E0));
		box3.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)15, 0xFFE0E0E0));
		sendBTN.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (!inputName.getText().toString().equals("") && (!inputNumber.getText().toString().equals("") && !inputComment.getText().toString().equals(""))) {
					map5 = new HashMap<>();
					map5.put("comment", inputComment.getText().toString());
					map5.put("author", inputName.getText().toString());
					map5.put("phone", inputNumber.getText().toString());
					map5.put("phone_model", _getDeviceName());
					map5.put("android_version", Build.VERSION.RELEASE);
					map5.put("terms", "agreed");
					map5.put("comment_post_ID", pID);
					map5.put("comment_parent", "0");
					commentAddApi.setParams(map5, RequestNetworkController.REQUEST_PARAM);
					commentAddApi.startRequestNetwork(RequestNetworkController.POST, Preferences.main_url + "wp-comments-post.php", "DownloadActivity", _commentAddApi_request_listener);
					cd.dismiss();
				}
			}
		});
		cd.show();
	}
	
	
	public String _getDeviceName() {
		    String manufacturer = Build.MANUFACTURER;
		    String model = Build.MODEL;
		    if (model.toLowerCase().startsWith(manufacturer.toLowerCase())) {
			        return capitalize(model);
			    } else {
			        return capitalize(manufacturer) + " " + model;
			    }
	}
	private String capitalize(String s) {
		    if (s == null || s.length() == 0) {
			        return "";
			    }
		    char first = s.charAt(0);
		    if (Character.isUpperCase(first)) {
			        return s;
			    } else {
			        return Character.toUpperCase(first) + s.substring(1);
			    }
	}
	
	
	public void _addedT() {
		Fdownloader.showToast(this, "دیدگاه شما ارسال شد و پس از تایید نمایش داده میشود");
	}
	
	public class DownloadRecyclerAdapter extends RecyclerView.Adapter<DownloadRecyclerAdapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public DownloadRecyclerAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getLayoutInflater();
			View _v = _inflater.inflate(R.layout.download_item, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final com.farsroid.android.custom.FButton downloadButton = _view.findViewById(R.id.downloadButton);
			
			downloadButton.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/vazir.ttf"), 0);
			downloadButton.setText(_data.get((int)_position).get("title").toString());
			downloadButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					_download(_position);
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
	
	public class GalleryRecyclerAdapter extends RecyclerView.Adapter<GalleryRecyclerAdapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public GalleryRecyclerAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getLayoutInflater();
			View _v = _inflater.inflate(R.layout.gallery_item, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final androidx.cardview.widget.CardView cardview1 = _view.findViewById(R.id.cardview1);
			final ImageView img = _view.findViewById(R.id.img);
			
			Glide.with(getApplicationContext()).load(Uri.parse(_data.get((int)_position).get("img").toString())).into(img);
			img.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					_viewImage(_position);
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
	
	public class CommentRecyclerAdapter extends RecyclerView.Adapter<CommentRecyclerAdapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public CommentRecyclerAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getLayoutInflater();
			View _v = _inflater.inflate(R.layout.comment_item, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final androidx.cardview.widget.CardView cardview1 = _view.findViewById(R.id.cardview1);
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final LinearLayout linear3 = _view.findViewById(R.id.linear3);
			final LinearLayout linear4 = _view.findViewById(R.id.linear4);
			final LinearLayout linear5 = _view.findViewById(R.id.linear5);
			final LinearLayout linear6 = _view.findViewById(R.id.linear6);
			final TextView os_name = _view.findViewById(R.id.os_name);
			final TextView os_version = _view.findViewById(R.id.os_version);
			final TextView username = _view.findViewById(R.id.username);
			final TextView date = _view.findViewById(R.id.date);
			final TextView content = _view.findViewById(R.id.content);
			
			os_version.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/vazir.ttf"), 0);
			os_name.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/vazir.ttf"), 0);
			username.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/vazir.ttf"), 0);
			date.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/vazir.ttf"), 0);
			content.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/vazir.ttf"), 0);
			try {
				os_version.setText(_data.get((int)_position).get("android").toString());
				os_name.setText(_data.get((int)_position).get("model").toString());
			} catch (Exception e) {
				os_version.setVisibility(View.GONE);
				os_name.setVisibility(View.GONE);
			}
			username.setText(_data.get((int)_position).get("author").toString());
			date.setText(_data.get((int)_position).get("date").toString());
			content.setText(_data.get((int)_position).get("body").toString());
			if (_data.get((int)_position).get("author").toString().equals("امید انصاری‌مهر") || _data.get((int)_position).get("author").toString().equals("امید")) {
				username.setTextColor(0xFFF44336);
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