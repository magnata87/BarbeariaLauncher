package br.com.juniortorres.launcher;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends Activity {
    private final Handler handler = new Handler();
    private TextView clock, date;
    private final int bg = Color.rgb(17,17,19), gold = Color.rgb(208,170,95), white = Color.rgb(245,242,235);
    // QR data decoded from the Wi-Fi QR image supplied in this chat.
    private static final String WIFI_QR = "WIFI:T:WPA;S:BARBEARIA JUNIOR TORRES;P:07251420;;";

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        buildScreen();
        updateClock();
    }

    private void buildScreen() {
        LinearLayout root = new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL); root.setPadding(38,24,38,24); root.setBackgroundColor(bg);
        LinearLayout header = new LinearLayout(this); header.setGravity(Gravity.CENTER_VERTICAL); header.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout brand = new LinearLayout(this); brand.setOrientation(LinearLayout.VERTICAL);
        TextView title = text("JUNIOR TÔRRES", 28, gold, true); TextView subtitle = text("BARBEARIA  •  TV DO AMBIENTE", 12, white, false);
        brand.addView(title); brand.addView(subtitle); header.addView(brand, new LinearLayout.LayoutParams(0,-2,1));
        LinearLayout timeBox = new LinearLayout(this); timeBox.setOrientation(LinearLayout.VERTICAL); timeBox.setGravity(Gravity.RIGHT);
        clock = text("00:00", 38, white, true); date = text("", 15, gold, false); timeBox.addView(clock); timeBox.addView(date); header.addView(timeBox);
        root.addView(header);
        View line = new View(this); line.setBackgroundColor(gold); LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1,2); lp.setMargins(0,16,0,18); root.addView(line,lp);

        LinearLayout body = new LinearLayout(this); body.setOrientation(LinearLayout.HORIZONTAL); body.setGravity(Gravity.CENTER_VERTICAL);
        LinearLayout apps = new LinearLayout(this); apps.setOrientation(LinearLayout.VERTICAL); apps.setPadding(0,0,18,0);
        TextView section = text("ENTRETENIMENTO", 16, gold, true); apps.addView(section);
        LinearLayout row1 = row(); row1.addView(tile("▶  YouTube", new String[]{"youtube"}), weight()); row1.addView(tile("♫  Spotify", new String[]{"spotify"}), weight());
        LinearLayout row2 = row(); row2.addView(tile("UNITV FREE", new String[]{"unitv free","unitv","unitvfree"}), weight()); row2.addView(tile("VELO PLAY", new String[]{"velo play","veloplay","velo"}), weight());
        LinearLayout row3 = row(); row3.addView(tile("XPLUS VOD", new String[]{"xplus vod","xplusvod"}), weight()); row3.addView(tile("XPLUS EXPRESS", new String[]{"xplus express","xplusexpress"}), weight());
        apps.addView(row1); apps.addView(row2); apps.addView(row3); body.addView(apps,new LinearLayout.LayoutParams(0,-1,3));
        LinearLayout wifi = new LinearLayout(this); wifi.setOrientation(LinearLayout.VERTICAL); wifi.setGravity(Gravity.CENTER); wifi.setPadding(16,10,16,10); wifi.setBackgroundColor(Color.rgb(31,29,27));
        TextView wifiTitle = text("WI-FI DA BARBEARIA", 15, gold, true); wifi.addView(wifiTitle);
        ImageView qr = new ImageView(this); Bitmap bitmap = makeQr(WIFI_QR, 250); if(bitmap!=null) qr.setImageBitmap(bitmap); LinearLayout.LayoutParams qlp = new LinearLayout.LayoutParams(250,250); qlp.setMargins(0,12,0,8); wifi.addView(qr,qlp);
        TextView qrCaption = text("ESCANEIE PARA CONECTAR", 11, white, false); wifi.addView(qrCaption); body.addView(wifi,new LinearLayout.LayoutParams(0,-2,1));
        root.addView(body,new LinearLayout.LayoutParams(-1,0,1));
        TextView footer = text("BEM-VINDO  •  RELAXE, SEU CORTE ESTÁ EM BOAS MÃOS", 12, gold, true); footer.setGravity(Gravity.CENTER); LinearLayout.LayoutParams flp=new LinearLayout.LayoutParams(-1,-2); flp.setMargins(0,14,0,0); root.addView(footer,flp);
        setContentView(root);
    }
    private LinearLayout row(){ LinearLayout l=new LinearLayout(this); l.setOrientation(LinearLayout.HORIZONTAL); l.setGravity(Gravity.CENTER); return l; }
    private LinearLayout.LayoutParams weight(){ LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(0,86,1); p.setMargins(7,7,7,7); return p; }
    private TextView text(String s,int size,int color,boolean bold){ TextView t=new TextView(this); t.setText(s); t.setTextSize(size); t.setTextColor(color); t.setGravity(Gravity.CENTER_VERTICAL); if(bold)t.setTypeface(Typeface.DEFAULT,Typeface.BOLD); return t; }
    private Button tile(String label, String[] keys){ Button b=new Button(this); b.setText(label); b.setTextSize(16); b.setTextColor(white); b.setTypeface(Typeface.DEFAULT,Typeface.BOLD); b.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.rgb(45,42,38))); b.setOnClickListener(v->launchByLabel(keys)); return b; }
    private void launchByLabel(String[] keys){
        PackageManager pm=getPackageManager();
        for(ApplicationInfo ai:pm.getInstalledApplications(PackageManager.GET_META_DATA)){
            String label=pm.getApplicationLabel(ai).toString().toLowerCase(Locale.ROOT);
            String pkg=ai.packageName.toLowerCase(Locale.ROOT);
            for(String key:keys){ if(label.contains(key.toLowerCase(Locale.ROOT)) || pkg.contains(key.toLowerCase(Locale.ROOT).replace(" ",""))){ Intent i=pm.getLaunchIntentForPackage(ai.packageName); if(i!=null){ startActivity(i); return; } } }
        }
        Toast.makeText(this,"App não encontrado instalado. Confira o nome ou instale-o.",Toast.LENGTH_SHORT).show();
    }
    private Bitmap makeQr(String contents,int size){ try { Map<EncodeHintType,Object> hints=new HashMap<>(); hints.put(EncodeHintType.MARGIN,1); BitMatrix m=new MultiFormatWriter().encode(contents,BarcodeFormat.QR_CODE,size,size,hints); Bitmap b=Bitmap.createBitmap(size,size,Bitmap.Config.ARGB_8888); for(int x=0;x<size;x++)for(int y=0;y<size;y++)b.setPixel(x,y,m.get(x,y)?Color.BLACK:Color.WHITE); return b; } catch(Exception e){return null;} }
    private void updateClock(){ SimpleDateFormat tf=new SimpleDateFormat("HH:mm",Locale.getDefault()); SimpleDateFormat df=new SimpleDateFormat("EEEE, dd 'de' MMMM",new Locale("pt","BR")); clock.setText(tf.format(new Date())); date.setText(df.format(new Date()).toUpperCase(new Locale("pt","BR"))); handler.postDelayed(this::updateClock,30000); }
}
