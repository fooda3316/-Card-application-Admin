package com.example.adminapplication.activities.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.adminapplication.R;
import com.example.adminapplication.SQLdatabase.SQLEditor;
import com.example.adminapplication.activities.users.ShowUserActivity;
import com.example.adminapplication.adaptors.MyListViewAdapter;
import com.example.adminapplication.model.User;
import com.example.adminapplication.utils.Utility;
import com.example.adminapplication.viewmodels.UsersVM;


import java.util.ArrayList;
import java.util.List;

/**
 * Dialog search interface واجهة البحث
 * Created by leosunzh on 2015/12/23.
 */
public class SearchActivity extends AppCompatActivity implements TextWatcher,
        AdapterView.OnItemClickListener, View.OnClickListener{
    //search field
    EditText editText;
    //Search suggestion listView
    ListView listView;
    MyListViewAdapter myListViewAdapter;
    private MaterialDialog mDialog;
    private UsersVM usersVM;
    List<User> list;
    //private List<User> users=new ArrayList<>();


  //  PinYinAndNumSearch pinYinAndNumSearch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_layout);
        showLoadingDialog();
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        //Set the size and transparency of the search window
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = layoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.TOP;
//        layoutParams.alpha = 0.5f;
        window.setAttributes(layoutParams);
        //back button
        findViewById(R.id.back_imb_dia).setOnClickListener(this);
        findViewById(R.id.clear_imb_dia).setOnClickListener(this);
        editText =  findViewById(R.id.search_ed_dia);
        editText.addTextChangedListener(this);
        listView =  findViewById(R.id.result_lv_dia);
        listView.setOnItemClickListener(this);
        SQLEditor.newInstance(this);
        list = new ArrayList<>();
        usersVM= ViewModelProviders.of(SearchActivity.this).get(UsersVM.class);
        usersVM.getUnfinishedLiveData().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> data) {
                for (User user:data){
                    SQLEditor.addToUsers(user);
                }
               // users=data;
               dismissLoadingDialog();
            }
        });

        myListViewAdapter = new MyListViewAdapter(this,R.layout.list_item_layout,list);
        listView.setAdapter(myListViewAdapter);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}
    @Override
    public void afterTextChanged(Editable s) {
            list.clear();
            list.addAll(Utility.searchForPerson(SQLEditor.getAllUsers(),s));
            myListViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        User user = (User) listView.getItemAtPosition(position);
        ShowUserActivity.actionStart(this,user.getName(),user.getEmail(),user.getPassword(),user.getImage(),user.getBalance()+"");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_imb_dia:
                finish();break;
            case R.id.clear_imb_dia:
                editText.setText(null);break;
        }
    }

    /**
     * come back to SearchActivity
     */
    public static void actionStart(Context context){
        Intent intent = new Intent(context,SearchActivity.class);
        context.startActivity(intent);
    }
    public void showLoadingDialog() {
        mDialog = new MaterialDialog.Builder(SearchActivity.this)
                .title(R.string.app_name)
                .content(R.string.progress_wait)
                .progress(true, 0)
                .show();
    }


    public void dismissLoadingDialog() {
        mDialog.dismiss();
    }
    @Override
    protected void onStop() {
        super.onStop();

        if (mDialog!=null){
            dismissLoadingDialog();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDialog!=null){
            dismissLoadingDialog();
        }
    }
}
