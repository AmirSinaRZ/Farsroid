package com.farsroid.android;

public class Preferences {
    
    public static final String main_url = "https://www.farsroid.com/";
    
    public static final String banner_url = "https://amirsinarz.github.io/Farsroid/banners/Banners.json";
    
    public static final String co_url = "https://amirsinarz.github.io/Farsroid/banners/Companies.json";
    
    public static final String today_cnt = "api/today-update-count";
    
    public static final String[] recommended_games = {"wp-admin/admin-ajax.php", "dw_get_posts_list", "7ae15702a6", "20", "5611", "recommended_games"};
    
    public static final String[] recommended_games_keys = {"action", "nonce", "count", "cat[]", "wrap_id"};
    
    public static final String[] introduced_games = {"wp-admin/admin-ajax.php", "dw_get_posts_list", "new", "c7a53ffa54", "20", "9", "introduced_games"};
    
    public static final String[] introduced_games_keys = {"action", "type", "nonce", "count", "cat", "wrap_id"};
        
    public static final String ad_url = "https://amirsinarz.github.io/Farsroid/ads/Ad.json";    
    
    public static final String[] recommended_apps = {"wp-admin/admin-ajax.php", "dw_get_posts_list", "4103aa7475", "20", "6471", "recommended_apps"};
        
    public static final String[] recommended_apps_keys = {"action", "nonce", "count", "cat[]", "wrap_id"};
        
    public static final String[] introduced_apps = {"wp-admin/admin-ajax.php", "dw_get_posts_list", "new", "1f9ffbfde2", "20", "2", "introduced_apps"};
    
    public static final String[] introduced_apps_keys = {"action", "type", "nonce", "count", "cat", "wrap_id"};
        
    public static final String today_games_url = "today-published-games/";    
    
    public static final String today_apps_url = "today-published-apps/";    
    
    public static final String getBox = "api/download-box/?post_id={id}&post_version={post_version}";
                
}
