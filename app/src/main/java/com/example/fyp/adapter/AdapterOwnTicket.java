package com.example.fyp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fyp.R;
import com.example.fyp.model.TicketModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class AdapterOwnTicket extends RecyclerView.Adapter<AdapterOwnTicket.Holder>{

    private Context context;
    //array list <data class>
    public ArrayList<TicketModel> ticketModelArrayList;
    private ItemClickListener mListener;

    //constructor
    public AdapterOwnTicket(Context context, ArrayList<TicketModel> ticketModels, ItemClickListener listener) {
        this.context = context;
        this.ticketModelArrayList = ticketModels;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout
        View v = LayoutInflater.from(context).inflate(R.layout.item_own_ticket, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        //get data (need to modify-based on class name-that contain set and get)
        TicketModel ticketModel = ticketModelArrayList.get(position);

        //set data (need to modify)
        //holder.tc_content.setText(cleanerComplaint.getReason());

        holder.tv_id.setText(ticketModel.getTicketID());
        holder.tv_companyName.setText(ticketModel.getCompanyName());
        holder.tv_departureDate.setText(ticketModel.getDepartureTime());
        holder.tv_fromLocation.setText(String.format(context.getString(R.string.from_location), ticketModel.getFromLocation()));
        holder.tv_toLocation.setText(String.format(context.getString(R.string.to_location), ticketModel.getToLocation()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(position);
                //context.startActivity(new Intent(context, AdminUserEditActivity.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return ticketModelArrayList.size();
    }


    public class Holder extends RecyclerView.ViewHolder {
        TextView tv_id;
        TextView tv_companyName;
        TextView tv_departureDate;
        TextView tv_toLocation;
        TextView tv_fromLocation;
        // Button btnDelete;


        public Holder(@NonNull View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.tv_ticket_id);
            tv_companyName = itemView.findViewById(R.id.tv_company_name);
            tv_departureDate = itemView.findViewById(R.id.tv_ticket_departure);
            tv_toLocation = itemView.findViewById(R.id.tv_ticket_to);
            tv_fromLocation = itemView.findViewById(R.id.tv_ticket_from);

        }
    }

    public interface ItemClickListener{
        void onItemClick(int position);
    }


}