package com.example.adminapplication.activities.users;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.adminapplication.R;
//import com.example.adminapplication.SQLdatabase.SQLEditor;
import com.example.adminapplication.activities.main.SearchActivity;
import com.example.adminapplication.adaptors.AllUsersAdaptor;
import com.example.adminapplication.api.APIService;
import com.example.adminapplication.model.Results;
import com.example.adminapplication.model.User;
import com.example.adminapplication.network.RetrofitClient;
import com.example.adminapplication.viewmodels.UsersVM;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllUsersActivity extends AppCompatActivity {
    private UsersVM usersVM;
    private RecyclerView recyclerView;
    private AllUsersAdaptor adaptor;
    private MaterialDialog mDialog;
    private TextView textView;
    Call<Results> getAllUsersCall;
    private APIService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);
        showLoadingDialog();
        //SQLEditor.newInstance(this);
        recyclerView=findViewById(R.id.allUsersRV);
        textView=findViewById(R.id.txtUsers);
        apiService= RetrofitClient.getInstance().getApiService();
        getAllUsersCall=apiService.getAllUsersCall();
        getAllUsersCall.enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                for (User user:response.body().getUsers()){
                    Log.e("names",user.getEmail());
                //    SQLEditor.addToUsers(user);
                }
                adaptor.setUsers(response.body().getUsers());
                //adaptor.setAdmins(SQLEditor.getAllUsers());
                int size=response.body().getUsers().size();

              textView.setText("عدد المستخدمين يساوي: "+size);
                //   adaptor.setAdmins(users);
                dismissLoadingDialog();
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {
                Log.e("onFailure",t.toString());
            }
        });

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adaptor=new AllUsersAdaptor(this);
        recyclerView.setAdapter(adaptor);
        usersVM= ViewModelProviders.of(this).get(UsersVM.class);
     /*   usersVM.getUnfinishedLiveData().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                Log.e("user count","user count: "+users.size());
                textView.setText("عدد المستخدمين يساوي: "+users.size());
                for (User user:users){
                    Log.e("names",user.getEmail());
                    SQLEditor.addToUsers(user);
                }
             //   adaptor.setAdmins(users);
                dismissLoadingDialog();
            }
        });
        adaptor.setAdmins(SQLEditor.getAllUsers());*/
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
            case R.id.action_search:{
                SearchActivity.actionStart(AllUsersActivity.this);

            }

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void showLoadingDialog() {
        mDialog = new MaterialDialog.Builder(AllUsersActivity.this)
                .title(R.string.app_name)
                .content(R.string.progress_wait)
                .progress(true, 0)
                .show();
    }
    public void dismissLoadingDialog() {
        mDialog.dismiss();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_home,menu);
        return true;
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