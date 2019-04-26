package com.aplid.mahjong;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HandAdapter extends RecyclerView.Adapter<HandViewHolder> {

    List<String> handList;

    public HandAdapter(){
        this.handList = new ArrayList<>();
    }


    public void update(List<String> handList){
        this.handList.clear();
        this.handList.addAll(MahjongUtil.getOrder(handList));
        notifyDataSetChanged();
    }

    public void add(String hand){
        this.handList.add(hand);
        notifyDataSetChanged();
    }

    public interface OnItemClickLitener {
        void onItemClick(int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener){
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @NonNull
    @Override
    public HandViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_hand,viewGroup,false);
        return new HandViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HandViewHolder handViewHolder, final int pos) {
        handViewHolder.tvMahjong.setText(MahjongUtil.changeToChar(handList.get(pos)));
        if(mOnItemClickLitener!= null){
            handViewHolder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    mOnItemClickLitener.onItemClick(pos);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return handList.size();
    }

}
class HandViewHolder extends RecyclerView.ViewHolder{

    TextView tvMahjong;

    public TextView getTvMahjong() {
        return tvMahjong;
    }

    public HandViewHolder(@NonNull View itemView) {
        super(itemView);
        tvMahjong = itemView.findViewById(R.id.tv_mahjong);
    }
}
