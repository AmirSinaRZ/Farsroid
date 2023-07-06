package com.farsroid.android;

import android.app.DownloadManager;
import android.net.Uri;
import android.content.Context;
import android.os.Bundle;
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

public class Fdownloader {
    private static DownloadManager manager;
    
    public static void DownloadFarsroid(Context context, String link, String title, String desc, String name) {
        
        manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(link);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, name);
        request.setTitle(title);
        request.setDescription(desc);
        long reference = manager.enqueue(request);
        showToast(context, "در حال دانلود");
    }
    
    public static void showToast(Context context, String Msg) {
        
      LayoutInflater i = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
      View inflate = i.inflate(R.layout.custom_toast, null); 
      Toast t = Toast.makeText(context,"",Toast.LENGTH_LONG);
      t.setView(inflate);
      t.show();
      final TextView msg = (TextView)inflate.findViewById(R.id.msg);
      final LinearLayout toast_bg = (LinearLayout)inflate.findViewById(R.id.toast_bg);
      toast_bg.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)25, 0xFF607D8B));
      msg.setText(Msg);
      msg.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/vazir.ttf"));
        
    }
}
