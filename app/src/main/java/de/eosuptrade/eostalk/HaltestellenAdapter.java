package de.eosuptrade.eostalk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by btreger on 16.11.2017.
 */

public class HaltestellenAdapter extends BaseAdapter {
    final private Context mContext;
    final private List<Haltestelle> mListe;

    HaltestellenAdapter(Context context, List<Haltestelle> liste) {
        mContext = context;
        mListe = liste;
    }

    @Override
    public int getCount() {
        return mListe.size();
    }

    @Override
    public Object getItem(int position) {
        return mListe.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Haltestelle haltestelle = mListe.get(position);
        View view;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(R.layout.listenzeile, null);
        }  else {
            view = convertView;
        }
        TextView tvName = view.findViewById(R.id.haltestelle_name);
        TextView tvRegion = view.findViewById(R.id.haltestelle_region);
        tvName.setText(haltestelle.getName());
        tvRegion.setText(haltestelle.getRegion());
        return view;
    }
}
