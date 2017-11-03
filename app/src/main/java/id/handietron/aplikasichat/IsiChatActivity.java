package id.handietron.aplikasichat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

public class IsiChatActivity extends AppCompatActivity {

	private MaterialEditText edtSender;
	private MaterialEditText edtIsi;
	private Button btnSend;

	SharedPreferences preferences;
	SharedPreferences.Editor editor;

	String nama, pesan, waktu;
	int gambar;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_isi_chat);
		initView();

		preferences = getSharedPreferences(MainActivity.mainPrers,0);
		editor = preferences.edit();

		btnSend.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				sendDatabase();
			}
		});
	}

	public void sendDatabase(){
		if(!validasi()){return;}
		randomGambar();
		ambilWaktu();

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("Pengirim",edtSender.getText().toString());
			jsonObject.put("Content",edtIsi.getText().toString());
			jsonObject.put("Waktu",waktu);
			jsonObject.put("Foto",gambar);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		if(preferences.contains("message")) {
			String dataMessage = preferences.getString("message","");

			try {
				JSONArray jsonArray = new JSONArray(dataMessage);
				jsonArray.put(jsonObject);
				editor.putString("message", jsonArray.toString());
				editor.apply();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			JSONArray jsonArray = new JSONArray();
			jsonArray.put(jsonObject);
			editor.putString("message", jsonArray.toString());
			editor.apply();
		}

		finish();
	}

	public String ambilWaktu(){
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+7:00"));
		Date currentLocalTime = cal.getTime();
		DateFormat date = new SimpleDateFormat("HH:mm a");

		// you can get seconds by adding  "...:ss" to it
		date.setTimeZone(TimeZone.getTimeZone("GMT+7:00"));

		waktu = date.format(currentLocalTime);
		return waktu;
	}

	public boolean validasi(){
		if(edtSender.getText().toString().isEmpty()){
			edtSender.setError("Harap masukkan penerima");
			edtSender.requestFocus();
			return false;
		}
		if(edtIsi.getText().toString().isEmpty()){
			edtIsi.setError("Harap masukkan pesan");
			edtIsi.requestFocus();
			return false;
		}
		return true;
	}

	public void randomGambar(){
		Random random = new Random();
		gambar = random.nextInt(10);
	}

	private void initView() {
		edtSender = (MaterialEditText) findViewById(R.id.namaorang);
		edtIsi = (MaterialEditText) findViewById(R.id.isiChat);
		btnSend = (Button) findViewById(R.id.refactor);
	}
}
