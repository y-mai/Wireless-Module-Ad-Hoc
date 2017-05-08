package com.example.apple.wireless_module_ad_hoc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Easy_MAI on 2017/5/8.
 */

public class RescueInfoActivity extends AppCompatActivity implements View.OnClickListener {

    String TAG="RescueInfoActivity";

    CacheUtils cacheUtils;
    ArrayList<String> rescueMessages;
    ListView resuceListView;
    ListAdapter adapter;
    Button cleanButton;
    Button refreshButton;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rescueinfo);

        cleanButton=(Button)findViewById(R.id.clean_rescue);
        refreshButton=(Button)findViewById(R.id.refresh_dialogue_rescue);
        refreshButton.setOnClickListener(this);
        cleanButton.setOnClickListener(this);

        cacheUtils=new CacheUtils();
        rescueMessages=new ArrayList<>();
        //dialogueMessages.add("1111");
        resuceListView=(ListView)findViewById(R.id.rescue_list);
        adapter=new ListAdapter(this,rescueMessages);
        resuceListView.setAdapter(adapter);
        getRescueList();

    }

    public void getRescueList(){
        ArrayList<String> dialogueList=new ArrayList<>();
        ArrayList<String> list=cacheUtils.readJson(RescueInfoActivity.this,"rescue_info.txt");
        JSONObject jsonObject;
        String parsedData;
        for(String s:list){
            try{
                jsonObject=new JSONObject(s);
               // Log.d(TAG,jsonObject.toString());
                parsedData="From: "+jsonObject.get("fromID").toString()+"\n"+jsonObject.get("message");
                dialogueList.add(parsedData);
            }catch (JSONException e){
                e.getStackTrace();
            }

        }
        adapter.setList(dialogueList);
        adapter.notifyDataSetChanged();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.refresh_dialogue_rescue:
                getRescueList();
                break;
            case R.id.clean_rescue:
                try{
                    cacheUtils.writeJson(RescueInfoActivity.this,"","rescue_info.txt",false);
                    Toast.makeText(RescueInfoActivity.this,"The rescue information record is cleaned.",Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.getStackTrace();
                }
                break;

            default:
                break;
        }
    }

    public void onDestroy(){
        super.onDestroy();
		/*if(_socket!=null)
			try{
				_socket.close();
			}catch(IOException e){}*/
        Log.d(TAG,"RescueInfoActivity destroyed.");

    }
}
