package co.edu.eafit.equations.menuadapters;

import android.content.Context;
import android.content.Intent;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.etsy.android.grid.util.DynamicHeightTextView;

import java.util.ArrayList;
import java.util.Random;

import co.edu.eafit.equations.R;
import co.edu.eafit.equations.Tabs;

public class MenuAdapterIN extends ArrayAdapter<String>{
    public static final String TAG = "MenuAdapterSV";
    static class ViewHolder{
        DynamicHeightTextView txtLineOne;
        String type = "IN";
        Button btn_go;
    }
    private final LayoutInflater mLayoutInflater;
    private final Random mRandom;
    private final ArrayList<Integer> mBackgroundColors;
    private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();
    public MenuAdapterIN(final Context context, final int textViewResourceId){
        super(context,textViewResourceId);
        mLayoutInflater = LayoutInflater.from(context);
        mRandom = new Random();
        mBackgroundColors = new ArrayList<>();
        mBackgroundColors.add(R.color.sway);
        mBackgroundColors.add(R.color.excel);
        mBackgroundColors.add(R.color.powerpoint);
        mBackgroundColors.add(R.color.onenote);
        mBackgroundColors.add(R.color.publisher);
        mBackgroundColors.add(R.color.access);
        mBackgroundColors.add(R.color.word);
    }
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        ViewHolder vh;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_item_sample, parent, false);
            vh = new ViewHolder();
            vh.txtLineOne = (DynamicHeightTextView)convertView.findViewById(R.id.txt_line1);
            convertView.setTag(vh);
        }
        else {
            vh = (ViewHolder) convertView.getTag();
        }

        double positionHeight = getPositionRatio(position);
        int backgroundIndex = position >= mBackgroundColors.size() ?
                position % mBackgroundColors.size() : position;

        convertView.setBackgroundResource(mBackgroundColors.get(backgroundIndex));

        //Log.d(TAG, "getView position:" + position + " h:" + positionHeight);

        vh.txtLineOne.setHeightRatio(positionHeight);
        //vh.txtLineOne.setText(getItem(position) + position);
        vh.txtLineOne.setText(getItem(position));

        vh.txtLineOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), Tabs.class);
                switch (position){
                    case 0:
                        intent.putExtra("type","Newton Polynomial");
                        break;
                    case 1:
                        intent.putExtra("type","Lagrange Polynomial");
                        break;
                    case 2:
                        intent.putExtra("type","Linear Spline");
                        break;
                    case 3:
                        intent.putExtra("type","Cubic Spline");
                }
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }

    private double getPositionRatio(final int position) {
        double ratio = sPositionHeightRatios.get(position, 0.0);
        if (ratio == 0) {
            ratio = getRandomHeightRatio();
            sPositionHeightRatios.append(position, ratio);
        }
        return ratio;
    }

    private double getRandomHeightRatio() {
        return (mRandom.nextDouble() / 2.0) + 1.0; // height will be 1.0 - 1.5 the width
    }

}
