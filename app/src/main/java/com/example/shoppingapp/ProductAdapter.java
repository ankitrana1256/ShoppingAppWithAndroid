package com.example.shoppingapp;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class ProductAdapter extends ArrayAdapter<ProductModel> {

    public ProductAdapter(@NonNull Context context, ArrayList<ProductModel> arr) {
        super(context, 0, arr);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;

        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.griddesign, parent, false);
        }
        ProductModel courseModel = getItem(position);
        TextView t = listitemView.findViewById(R.id.textView2);
        ImageView y = listitemView.findViewById(R.id.imageView);
        String uri = courseModel.getUrl();
        Picasso.get().load(uri).into(y);

        t.setText(courseModel.getName());
        return listitemView;
    }
}
