package com.example.cop290.assignment2;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Complaint_resolved extends Fragment {

    ArrayList<fraud> list = new ArrayList<fraud>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_complaint_resolved, container, false);
        populateListView(view);
        return view;
    }
    void populateListView(View view) {


        LoadData l = new LoadData();
        JSONObject loginR = l.loginResponseJSON;
        try {

            list = new ArrayList<fraud>();

            for (int i = 0; i < l.complaintDetailsArray.length; i++) {
                Log.i("sandj" + i, "sadjsakdas" + i);
                String title = l.complaintDetailsArray[i].getString("title");
                String l_name = l.complaintDetailsArray[i].getString("lodger_name");
                String des = l.complaintDetailsArray[i].getString("description");
                if(l.complaintDetailsArray[i].getString("current_status").equals("resolved") )
                    list.add(new fraud(title, l_name, des));
            }

        }catch(Exception e){Log.i("terimaaki","");e.printStackTrace();}

        // TODO ing : add elements to the list
        /*for(int i=0; i<10; ++i ){
            list.add(new fraud("Title ka naam kya hona chaiyeh?? Shreyan madarboard hai.\n New line karke kya milega tujhe? "+i, "Lodger "+i, "bla"));
        }*/
        UserAdapter adapter = new UserAdapter(getActivity(), list);
        ListView listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }

    public class UserAdapter extends ArrayAdapter<fraud> {
        public UserAdapter(Context context, ArrayList<fraud> items) {
            super(context, 0, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            fraud item = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.format_complaint, parent, false);
            }
            // TODO : populate the elements of the list view here

            TextView slno = (TextView) convertView.findViewById(R.id.slno);
            TextView title = (TextView) convertView.findViewById(R.id.title);
            TextView lodger = (TextView) convertView.findViewById(R.id.lodger);
            RelativeLayout layout = (RelativeLayout) convertView.findViewById(R.id.layout);
            TextView complaint_id = (TextView) convertView.findViewById(R.id.complaint_id);

            slno.setText((position+1)+"");
            title.setText(item.title);
            lodger.setText(item.lodger);
            layout.setBackgroundColor(getResources().getColor(R.color.resolved));
            complaint_id.setText(item.complaint_id);

            return convertView;
        }
    }
    public class fraud
    {
        public String title;
        public String lodger;
        public String complaint_id;

        public fraud(String title,String lodger, String complaint_id) {
            this.title = title;
            this.lodger = lodger;
            this.complaint_id = complaint_id;
        }
    }


}
