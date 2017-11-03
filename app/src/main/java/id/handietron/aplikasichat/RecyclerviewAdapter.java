package id.handietron.aplikasichat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ASUS on 03/11/2017.
 */

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder> {
	JSONArray jsonArray;
	LayoutInflater inflater;
	ImageHelper imageHelper;

	public RecyclerviewAdapter(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_chat, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		try {
			JSONObject jsonObject = jsonArray.getJSONObject(position);
			imageHelper = new ImageHelper();
			holder.iv.setImageResource(imageHelper.getImage(jsonObject.getInt("Foto")));
			holder.tv_nama.setText(jsonObject.getString("Pengirim"));
			holder.tv_isi.setText(jsonObject.getString("Content"));
			holder.tv_clock.setText(jsonObject.getString("Waktu"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getItemCount() {
		return  jsonArray.length();
	}

	public class ViewHolder extends RecyclerView.ViewHolder{
		ImageView iv;
		TextView tv_nama, tv_isi, tv_clock;
		public ViewHolder(View itemView) {
			super(itemView);
			iv = (ImageView) itemView.findViewById(R.id.iv_sender);
			tv_nama = (TextView) itemView.findViewById(R.id.tv_sender);
			tv_isi = (TextView) itemView.findViewById(R.id.tv_konten);
			tv_clock = (TextView) itemView.findViewById(R.id.tv_jam);
		}
	}
}
