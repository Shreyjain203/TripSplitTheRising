package com.example.shreyjain.tripsplittherising;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

class user {

    public String name;
    public String email;
    public int money=0;


    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public user()
    {
    }

    public user(String name, String email,int money)
    {
        this.name = name;
        this.email = email;
        this.money=money;
    }
    //
//    public Map<String, Object> getAccounts() {
//        HashMap<String,Object> result=new HashMap<>();
//        result.put("name",name);
//        result.put("email",email);
//        result.put("qualification",qualification);
//        result.put("phoneno",phoneno);
//        return result;
//    }
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name",name);
        result.put("email",email);
        result.put("money",money);
        return result;
    }
}
public class Group extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String username,useremail,userphoto,userphone;
    int usermoney=0;
    DatabaseReference mDatabase,account;
    int i=0;
    int constant=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);


        username=getIntent().getExtras().getString("username");
        userphoto=getIntent().getExtras().getString("userphoto");
        useremail=getIntent().getExtras().getString("email");
        mDatabase= FirebaseDatabase.getInstance().getReference();
        account=mDatabase.child("Users");
        account.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    for(DataSnapshot d:dataSnapshot.getChildren())
                    {
                        i++;
                        user post=d.getValue(user.class);
                        if(post.email.equals(useremail))
                        {
                            constant = 1;
                            usermoney=post.money;
                            break;
                        }
                    }
                    if(constant==0)
                    {
                        String userId = account.push().getKey();
                        user user = new user(username, useremail, 0);
                        account.child(userId).setValue(user);
                    }
                    else
                    {
                        Toast.makeText(Group.this, "Welcome "+username, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mDatabase.keepSynced(true);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new CreateGroup();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content, fragment);
                fragmentTransaction.commit();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.group, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.creategroup)
        {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment=null;
        final Bundle bundle = new Bundle();
        bundle.putString("Username",username);
        bundle.putString("Useremail",useremail);
       if (id == R.id. creategroup)
       {
           fragment= new CreateGroup();
           final Dialog  dialog=new Dialog(Group.this);
           dialog.setContentView(R.layout.groupname);
           Button button=(Button) dialog.findViewById(R.id.button3);
           final Fragment finalFragment = fragment;
           button.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   dialog.dismiss();
                   fragmentlayout(finalFragment,bundle);
               }
           });
           dialog.show();

        }
        else if (id == R.id. selectgroup)
        {
            fragment= new selectGroup();

        }
        else if (id == R.id. dissolve)
        {
            fragment= new dissolve();

        }
        else if (id == R.id. logout)
        {
            final Dialog  dialog=new Dialog(Group.this);
            dialog.setContentView(R.layout.confirmlogout);


            Button button1 = (Button) dialog.findViewById(R.id.button4) ;
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Group.this,MainActivity.class);

                    startActivity(intent);
                }
            });
            Button button2 = (Button) dialog.findViewById(R.id.button5) ;
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Group.this,Group.class);
                    startActivity(intent);
                }
            });
            dialog.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void fragmentlayout(Fragment fragment,Bundle bundle)
    {

            if (fragment != null) {
                Toast.makeText(this, "Group Created", Toast.LENGTH_SHORT).show();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content, fragment);
                fragmentTransaction.commit();

        }
    }
}
