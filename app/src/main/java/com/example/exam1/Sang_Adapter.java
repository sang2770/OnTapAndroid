package com.example.exam1;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class Sang_Adapter extends BaseAdapter implements Filterable{
    //    Ngữ cảnh của Activity sử dụng( ngữ cảnh lớp cha)
    private Activity activity;
    //    Nguồn dữ liệu
    private ArrayList<Contact_191203366> data;
    //    Bộ phân tích layout
    private LayoutInflater inflater;
    private ArrayList<Contact_191203366> databackup;
    public Sang_Adapter(Activity activity, ArrayList<Contact_191203366> data) {
        this.activity = activity;
        this.data = data;
        this.inflater =(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    //    hàm trả về số phần tư của ListView
    @Override
    public int getCount() {
        return data.size();
    }
    //  Hàm trả về phầm tử thứ i
    @Override
    public Object getItem(int i) {
        return data.get(i);
    }
    //  Hàm trả về ID
    @Override
    public long getItemId(int i) {
        Contact_191203366 s=data.get(i);
        return s.getId();
    }
    private  class ViewHolder{
        TextView Name, Number, ID;

    }
    //  Hàm trả về View
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v=view;
        ViewHolder viewHolder;
        if(v==null) {
            v = inflater.inflate(R.layout.item, null);
            viewHolder=new ViewHolder();
            viewHolder.Name=v.findViewById(R.id.ContactName);
            viewHolder.Number=v.findViewById(R.id.ContactNumber);
            viewHolder.ID=v.findViewById(R.id.TxtID);
            v.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) v.getTag();
        }

        viewHolder.Name.setText(data.get(i).getTen());
        viewHolder.Number.setText(data.get(i).getSDT());
        viewHolder.ID.setText(String.valueOf(i+1));

        return v;
    }

    @Override
    public Filter getFilter() {
        Filter f=new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults fr=new FilterResults();
                if(databackup==null)
                {
                    databackup=new ArrayList<>(data);
                }
                if(charSequence==null || charSequence.length()==0)
                {
                    fr.count=databackup.size();
                    fr.values=databackup;
                }else{
                    //Nếu không rỗng
                    ArrayList<Contact_191203366> newdata=new ArrayList<>();
                    for(Contact_191203366 u:databackup)
                    {
                        if(u.getTen().toLowerCase().contains(
                                charSequence.toString().toLowerCase()))
                        {
                            newdata.add(u);
                        }
                        fr.count=newdata.size();
                        fr.values=newdata;

                    }
                    return  fr;
                }
                return null;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                data=new ArrayList<Contact_191203366>();
                if(filterResults!=null)
                {
                    ArrayList<Contact_191203366> tmp=(ArrayList<Contact_191203366>) filterResults.values;
                    if(tmp!=null)
                    {
                        for(Contact_191203366 u:tmp)
                        {
                            data.add(u);
                        }
                    }
                }else{
                    data=new ArrayList<>(databackup);
                }
                notifyDataSetChanged();
            }
        };
        return f;
    }
}