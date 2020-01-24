package com.example.inclass07;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MusicAdapter extends ArrayAdapter<Music> {


    public MusicAdapter(@NonNull Context context, int resource, @NonNull List<Music> objects) {
        super(context, resource, objects);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        Music m=getItem(position);
        ViewHolder viewHolder;
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.music_item,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.tv_album_name=convertView.findViewById(R.id.tv_album_name);
            viewHolder.tv_artist_name=convertView.findViewById(R.id.tv_artist_name);
            viewHolder.tv_date=convertView.findViewById(R.id.tv_date);
            viewHolder.tv_track_name=convertView.findViewById(R.id.tv_track_name);
            convertView.setTag(viewHolder);

        }
        else{
            viewHolder=(ViewHolder)convertView.getTag();
        }



        viewHolder.tv_album_name.setText("Album: "+m.albumName);
        viewHolder.tv_track_name.setText("Track: "+m.trackName);
        viewHolder.tv_artist_name.setText("Artist Name: "+m.artistName);
        String convertDate = getDate(m.updatedTime);
        viewHolder.tv_date.setText(String.format("Date: %s", convertDate));
        //viewHolder.tv_date.setText("Date: "+m.updatedTime);



        return convertView;
    }

    public String getMusicItemURL(int position){
        Music music=getItem(position);
        return music.trackShareUrl;
    }
    public static class ViewHolder{

        TextView tv_artist_name;
        TextView tv_album_name;
        TextView tv_track_name;
        TextView tv_date;
}

    public String getDate(String date) {
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy");

        Date data = null;
        try
        {
            data = input.parse(date);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        String formatted = output.format(data);

        return formatted;
    }

}
