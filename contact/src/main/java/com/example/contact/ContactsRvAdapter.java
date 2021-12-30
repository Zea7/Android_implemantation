package com.example.contact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactsRvAdapter extends RecyclerView.Adapter<ContactsRvAdapter.ViewHolder> {
    private Context myContext;
    private LayoutInflater inflater;
    private List<ModelContacts> myListContacts;

    public ContactsRvAdapter(Context context, List<ModelContacts> listContacts){
        myListContacts = listContacts;
        myContext = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(myContext);
        View view = inflater.inflate(R.layout.user_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView contact_name, contact_phoneNumber;
        contact_name = holder.contact_name;
        contact_phoneNumber = holder.contact_phoneNumber;

        contact_name.setText(myListContacts.get(position).getName());
        contact_phoneNumber.setText(myListContacts.get(position).getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return myListContacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView contact_name, contact_phoneNumber;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            contact_name = itemView.findViewById(R.id.name);
            contact_phoneNumber = itemView.findViewById(R.id.phone);
        }
    }
}
