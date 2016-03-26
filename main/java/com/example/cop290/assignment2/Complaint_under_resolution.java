package com.example.cop290.assignment2;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Complaint_under_resolution extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_complaint_under_resolution, container, false);
        populateListView(view);
        return view;
    }

    void populateListView(View view) {

        ArrayList<fraud> list = new ArrayList<fraud>();

        // TODO : add elements to the list
        for(int i=0; i<10; ++i ){
            list.add(new fraud("Title ka naam kya hona chaiyeh?? Ion madarboard hai.\n New line karke kya milega tujhe? "+i, "Lodger "+i, "bla"));
        }

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
            layout.setBackgroundColor(getResources().getColor(R.color.under_resolution));
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