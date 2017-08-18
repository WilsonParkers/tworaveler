package com.developer.hare.tworaveler.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.hare.tworaveler.Listener.OnListScrollListener;
import com.developer.hare.tworaveler.Model.FeedItemModel;
import com.developer.hare.tworaveler.R;
import com.developer.hare.tworaveler.UI.UIFactory;
import com.developer.hare.tworaveler.Util.Image.ImageManager;

import java.util.ArrayList;

/**
 * Created by Hare on 2017-08-01.
 */

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.ViewHolder> {
    private ArrayList<FeedItemModel> items;
    private OnListScrollListener onListScrollListener;

    public HomeListAdapter(ArrayList<FeedItemModel> items, OnListScrollListener onListScrollListener) {
        this.items = items;
        this.onListScrollListener = onListScrollListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mypage, parent, false), parent.getContext());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.toBind(items.get(position));
        if (items.size() - 1 == position)
            onListScrollListener.scrollEnd();
//        Log_HR.log(Log_HR.LOG_INFO, getClass(), "onBindViewHolder(ViewHolder, int)", "binding");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private Context context;
        private ImageView IV_cover;
        private TextView TV_title, TV_date, TV_like, TV_commenet;

        public ViewHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;
            UIFactory uiFactory = UIFactory.getInstance(itemView);
            IV_cover = uiFactory.createView(R.id.item_mypage$IV_cover);
            TV_title = uiFactory.createView(R.id.item_mypage$TV_title);
            TV_date = uiFactory.createView(R.id.item_mypage$TV_date);
            TV_like = uiFactory.createView(R.id.item_mypage$TV_like);
            TV_commenet = uiFactory.createView(R.id.item_mypage$TV_comment);
        }

        public void toBind(FeedItemModel model) {
            ImageManager imageManager = ImageManager.getInstance();
            imageManager.loadImage(imageManager.createRequestCreator(context, model.getTrip_pic_url()).centerCrop(), IV_cover);
            TV_title.setText(model.getTripName());
            TV_date.setText(model.getStart_date() + " ~ " + model.getEnd_date());
            TV_like.setText(model.getLikeCount() + "");
            TV_commenet.setText(model.getCommentCount() + "");
        }
    }

}
