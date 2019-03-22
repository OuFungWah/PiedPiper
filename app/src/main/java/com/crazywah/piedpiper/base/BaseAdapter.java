package com.crazywah.piedpiper.base;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter extends RecyclerView.Adapter {

    protected boolean checkIsSameNext(int position){
        if (position+1>=getItemCount()){
            return false;
        }else{
            if(getItemViewType(position) == getItemViewType(position+1)){
                return true;
            }else{
                return false;
            }
        }
    }

}
