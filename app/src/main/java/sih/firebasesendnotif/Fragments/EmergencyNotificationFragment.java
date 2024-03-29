package sih.firebasesendnotif.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import sih.firebasesendnotif.Classes.EmergencyData;
import sih.firebasesendnotif.R;

import static android.content.Context.MODE_PRIVATE;

public class EmergencyNotificationFragment extends Fragment {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("");
    private FirebaseAuth mAuth;
    SharedPreferences prefs ;
    String city_name;
    Button submit;

    public EmergencyNotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getActivity().getSharedPreferences("JaisPrefrence", MODE_PRIVATE);
        mAuth = FirebaseAuth.getInstance();
        city_name = prefs.getString("city_name", "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        //ref = database.getReference("pune");
        View v = inflater.inflate(R.layout.fragment_emergency_notification, container, false);

        Log.d("lw", "qwqwqwqwqwqw");

        //Button submit=(Button) v.findViewById(R.id.push);
        return v;
        }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final EditText text = (EditText) view.findViewById(R.id.text1);
        submit=(Button) view.findViewById(R.id.push);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference ref = database.getReference(city_name);
                DatabaseReference mydam,myalert;
                myalert = ref.child("Alert");
                //mydam = ref.child(mAuth.getUid());
                //myalert = mydam.child("Alert");
                //myalert = ref.child("Alert");
                String key=myalert.push().getKey();
                EmergencyData emergencyData =new EmergencyData(text.getText().toString(),mAuth.getUid(),1,city_name);
                myalert.child(key).setValue(emergencyData);
                //myalert.child(key).setValue(text.getText().toString());
                //myalert.child(key).setValue(text.getText().toString());
                text.setText("");
            }
        });
    }
}
