package com.example.android.sayido;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.startActivity;

public class OptionsAdapter extends ArrayAdapter<HomeOptions> {
    Context context;
    int decor;
    int venue;
    int vendor;
    public OptionsAdapter(@NonNull Context context, ArrayList<HomeOptions> optionsArrayList,int venue_price,int vendor_price,int decor_price) {
        super(context, 0,optionsArrayList);
        this.context = context;
        vendor = vendor_price;
        venue = venue_price;
        decor =decor_price;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null)
        {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.homepage,parent,false);
        }
        final HomeOptions CurrentOption = getItem(position);


        TextView TextView = (TextView) listItemView.findViewById(R.id.text);
        TextView.setText(CurrentOption.getText());
        LinearLayout option = (LinearLayout) listItemView.findViewById(R.id.option);
        option.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (CurrentOption.getActivityId() == 0)
                {
                    /*context.startActivity(new Intent(context,(VendorActivity.class)));*/
                    ((Activity) context).startActivityForResult(new Intent(context,(VendorActivity.class)),1);
                }else if (CurrentOption.getActivityId() == 1)
                {
                    /*context.startActivity(new Intent(context,(VenueActivity.class)));*/
                    ((Activity) context).startActivityForResult(new Intent(context,(VenueActivity.class)),2);
                }else if (CurrentOption.getActivityId() == 2)
                {
                    /*context.startActivity(new Intent(context,(DecorActivity.class)));*/
                    ((Activity) context).startActivityForResult(new Intent(context,(DecorActivity.class)),3);
                }else if (CurrentOption.getActivityId() == 3)
                {
                    /*context.startActivity(new Intent(context,(BudgetActivity.class)));*/
                    Intent intent = new Intent(context,BudgetActivity.class);
                    intent.putExtra("vendor_price1",vendor);
                    intent.putExtra("venue_price1",venue);
                    intent.putExtra("decore_price1",decor);
                    ((Activity) context).startActivity(intent);
                }


            }
        });


        ImageView iconView = (ImageView) listItemView.findViewById(R.id.image);

        // Get the image resource ID from the current AndroidFlavor object and
        // set the image to iconView
        iconView.setImageResource(CurrentOption.getImageId());


        return listItemView;
    }

}
