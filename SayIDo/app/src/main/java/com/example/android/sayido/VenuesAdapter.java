package com.example.android.sayido;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class VenuesAdapter extends ArrayAdapter <VenueOptions>{
    Context context;
    ArrayList<VenueOptions> selectedOptions = new ArrayList<VenueOptions>();
    public VenuesAdapter(@NonNull Context context, ArrayList<VenueOptions> optionsArrayList) {
        super(context, 0,optionsArrayList);
        this.context = context;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null)
        {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.venuepage,parent,false);
        }
        final VenueOptions CurrentOption = getItem(position);
        TextView TextView1 = (TextView) listItemView.findViewById(R.id.Country_text);
        TextView1.setText(CurrentOption.getCountry_text());

        TextView TextView2 = (TextView) listItemView.findViewById(R.id.text);
        TextView2.setText(CurrentOption.getText());

        TextView1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //context.startActivity(new Intent(context,(LoginActivity.class)));

            }
        });


        ImageView iconView = (ImageView) listItemView.findViewById(R.id.image);

        // Get the image resource ID from the current AndroidFlavor object and
        // set the image to iconView
        iconView.setImageResource(CurrentOption.getImageId());

        /*seected? change Image*/

        boolean flag=false;
        final ImageView btn = (ImageView)listItemView.findViewById(R.id.select);

// when you click this demo button
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ImageView iv = (ImageView) v;
                // Other way
                Drawable.ConstantState cs1 = iv.getDrawable().getConstantState();
                Drawable.ConstantState cs2 = btn.getResources().getDrawable(R.drawable.tick).getConstantState();
                Intent intent = new Intent(context,HomeActivity.class);
                DBHelper db = DBHelper.getDB(context);
                db.insertPrice(CurrentOption.price);
                context.startActivity(intent);

            }
        });
        /**/

        return listItemView;
    }

    public ArrayList<VenueOptions> getSelectedOptions() {
        return selectedOptions;
    }
}
