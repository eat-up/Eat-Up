package com.eatup.android.eatup.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eatup.android.eatup.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by rbhavsar on 4/1/2015.
 */
public class BusinessArrayAdapter extends ArrayAdapter<Businesses>{

    /**
     * Created by rbhavsar on 3/7/2015.
     */
// Taking the business object and turning them into views
// that will be displayed in lists


    public BusinessArrayAdapter(Context context, List<Businesses> tweets) {
        super(context, 0, tweets);
    }

    // override and setup custom template
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the business
        Businesses business = getItem(position);
        business = getItem(position);
        // Find or inflate the template
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_restaurant, parent, false);

        }

        // find the subview to fill with data in template
        TextView tvBusinessName = (TextView) convertView.findViewById(R.id.tvBusinessName);
        //TextView  tvMobile = (TextView) convertView.findViewById(R.id.tvMobile);
        TextView  tvPhone = (TextView) convertView.findViewById(R.id.tvPhone);
        TextView  tvCategories = (TextView) convertView.findViewById(R.id.tvCategories);
        ImageView ivRating = (ImageView) convertView.findViewById(R.id.ivRating);

        // populate data into subviews
        tvBusinessName.setText(business.getBusinessName());

        tvCategories.setText(business.getCategories());
        tvPhone.setText(business.getPhone());

        // clear out old image
        //ivProfileImage.setImageResource(android.R.color.transparent);
        //Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);

        Picasso.with(getContext()).load(Businesses.getRatingImageUrl()).into(ivRating);
        return convertView;
    }

}
