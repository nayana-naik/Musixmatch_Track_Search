package com.example.inclass07;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MusicAdapterRecycle extends RecyclerView.Adapter<MusicAdapterRecycle.ViewHolder> {

    ArrayList<Music> mList;
    
    public MusicAdapterRecycle(ArrayList<Music> mList) {
        this.mList = mList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.music_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Music m=mList.get(position);
        holder.tv_date.setText(m.updatedTime);
        holder.tv_track_name.setText(m.trackName);
        holder.tv_artist_name.setText(m.artistName);
        holder.tv_album_name.setText(m.albumName);
        holder.music=m;
        holder.URL=m.trackShareUrl;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder{
        TextView tv_artist_name;
        TextView tv_album_name;
        TextView tv_track_name;
        TextView tv_date;
        Music music;
        String URL;
        public ViewHolder (final View itemView){


            super(itemView);
            tv_album_name=(TextView)itemView.findViewById(R.id.tv_album_name);
            tv_artist_name=(TextView)itemView.findViewById(R.id.tv_artist_name);
            tv_track_name=(TextView)itemView.findViewById(R.id.tv_track_name);
            tv_date=(TextView)itemView.findViewById(R.id.tv_date);
            this.music=music;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //String url=musicAdapter.getMusicItemURL(position);
                    //String url = "http://www.example.com";
                   // int itemPosition=getLayoutPosition();

                    String url=URL;
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    itemView.getContext().startActivity(i);
                    Log.d("MusicURL",url);
                }
            });
        }
    }
}
