package com.tieutech.lostandfoundmapapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.tieutech.lostandfoundmapapp.Model.Advert;
import com.tieutech.lostandfoundmapapp.R;
import java.util.List;

//ABOUT:
//RecyclerView Adapter for the Adverts (to be displayed in the LostAndFoundItemsActivity)
//FUNCTION:
//Links the data of each item to be displayed in the RecyclerView to the RecyclerView itself
public class AdvertRecyclerViewAdapter extends RecyclerView.Adapter<AdvertRecyclerViewAdapter.ViewHolder> {

    //======= DEFINE VARIABLES =======
    private List<Advert> adverts; //Arraylist of Adverts for the RecyclerView
    private Context context; //Application Context
    private OnAdvertListener onAdvertClick; //Interface defining methods to override in the LostAndFoundItemsActivity


    //Constructor for the RecyclerView Adapter
    public AdvertRecyclerViewAdapter(List<Advert> adverts, Context context, OnAdvertListener onAdvertClick) {
        this.adverts = adverts;
        this.context = context;
        this.onAdvertClick = onAdvertClick;
    }

    //======= DEFINE METHODS =======
    //Upon the creation of the ViewHolder of each item in the RecyclerView
    @NonNull
    @Override
    public AdvertRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.advert_row, parent, false); //Create the view of the ViewHolder
        return new ViewHolder(itemView, onAdvertClick); //Link the ViewHolder to the RecyclerView Adapter
    }

    //Modify the display of the view elements in the ViewHolder
    @Override
    public void onBindViewHolder(@NonNull AdvertRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.itemStatusTextView.setText(adverts.get(position).isItemFound());
        holder.itemDescriptionTextView.setText(adverts.get(position).getDescription());
    }

    //Return the size of the dataset
    @Override
    public int getItemCount() {
        return adverts.size();
    }

    //ViewHolder for each item in the RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        //View variables
        TextView itemStatusTextView;
        TextView itemDescriptionTextView;

        //Interface variables
        OnAdvertListener onAdvertClick;

        public ViewHolder(@NonNull View itemView, OnAdvertListener onAdvertClick){
            super(itemView);

            //Obtain views
            itemStatusTextView = (TextView) itemView.findViewById(R.id.itemStatusTextView);
            itemDescriptionTextView = (TextView) itemView.findViewById(R.id.itemDescriptionTextView);

            //Define the interface variables
            this.onAdvertClick = onAdvertClick;

            itemView.setOnClickListener(this);
        }

        //OnClickListener for the ViewHolder
        @Override
        public void onClick(View view) {

            //Listener for the entire Advert item
            if (view == itemView) {
                onAdvertClick.onAdvertClick(getAdapterPosition()); //Defined in LostAndFoundItemsActivity
            }

        }
    }

    //Interface to be implemented by LostAndFoundItemsActivity
    public interface OnAdvertListener {
        void onAdvertClick(int position); //Listener method to override in LostAndFoundItemsActivity
    }
}