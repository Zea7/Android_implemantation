package com.example.contact;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ContactsRvAdapter extends RecyclerView.Adapter<ContactsRvAdapter.ViewHolder> {
    private Context myContext;
    private LayoutInflater inflater;
    private List<ModelContacts> myListContacts;

    public ContactsRvAdapter(Context context, List<ModelContacts> listContacts){
        myListContacts = listContacts;
        myContext = context;
    }

    public ContactsRvAdapter(){

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
        final ModelContacts temp = myListContacts.get(position);

        ImageView contact_icon;
        TextView contact_name, contact_phoneNumber;

        contact_icon = holder.contact_icon;
        contact_name = holder.contact_name;
        contact_phoneNumber = holder.contact_phoneNumber;

        if(temp.getIcon() == null)
            Glide.with(myContext).load(R.drawable.person_black).into(contact_icon);
        else
            Glide.with(myContext).asBitmap().load(myListContacts.get(position).getIcon()).into(contact_icon);
        contact_name.setText(myListContacts.get(position).getName());
        contact_phoneNumber.setText(myListContacts.get(position).getPhoneNumber());


        holder.button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(myContext, ContactActivity2.class);
                intent.putExtra("Icon", temp.getIcon());
                intent.putExtra("Name2", temp.getName());
                intent.putExtra("PhoneNumber2", temp.getPhoneNumber());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                myContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(myListContacts == null) return 0;
        return myListContacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView contact_icon, button;
        TextView contact_name, contact_phoneNumber;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.blank_button);
            contact_icon = itemView.findViewById(R.id.imageView);
            contact_name = itemView.findViewById(R.id.name);
            contact_phoneNumber = itemView.findViewById(R.id.phone);
        }
    }
}
