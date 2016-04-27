package ideal.com.utk.ideal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import ideal.com.utk.ideal.custom_datatypes.User_details;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{



    User_details userDetails;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_drawer_top);
        navigationView.setNavigationItemSelectedListener(this);


        userDetails = new User_details(this);
        if(userDetails.getUserType()!='U')navigationView.getMenu().setGroupVisible(R.id.lreq, true);

        View nav_header = navigationView.inflateHeaderView(R.layout.nav_header_main);
        ((TextView)nav_header.findViewById(R.id.nav_header_name)).setText(userDetails.getFname() +" "+ userDetails.getLname());
        ((TextView)nav_header.findViewById(R.id.nav_header_username)).setText(userDetails.getUsername());
        ((TextView)findViewById(R.id.drawer_leave_bl_ocl)).setText(userDetails.getOCL_balance());
        ((TextView)findViewById(R.id.drawer_leave_bl_cl)).setText(userDetails.getCL_balance());
        getSupportFragmentManager().beginTransaction().replace(R.id.main_activity_fragment,new HolidaysListFragment()).commit();//TODO:fix rotation fragment change by using saved bundle

        final Intent user_acc_activity = new Intent(this,UserAccountActivity.class);
        nav_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(user_acc_activity);
            }
        });

        findViewById(R.id.fab_cl).setOnClickListener(this);
        findViewById(R.id.fab_ocl).setOnClickListener(this);



    }


    private static long back_pressed;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (back_pressed + 2000 > System.currentTimeMillis()){
                super.onBackPressed();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
            else
                Toast.makeText(getBaseContext(), "Press again to exit !", Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment frag;
        FragmentTransaction fragTrans;
        if (id == R.id.nav_leave_history) {
            frag = new LeaveHistoryFragment();
            toolbar.setTitle("Leave History");
            fragTrans = getSupportFragmentManager().beginTransaction().replace(R.id.main_activity_fragment,frag);
            fragTrans.commit();
        } else if (id == R.id.nav_leave_processing) {

            frag = new LeaveProcessingFragment();
            toolbar.setTitle("Leaves Processing");
            fragTrans = getSupportFragmentManager().beginTransaction().replace(R.id.main_activity_fragment,frag);
            fragTrans.commit();
        } else if (id == R.id.nav_leave_requests) {
            frag = new LeaveRequestsFragment();
            toolbar.setTitle("Leave Requests");
            Bundle bundle = new Bundle();
            if(userDetails.getUserType()=='R')bundle.putString("api", "getRecommendations" );
            else if(userDetails.getUserType() == 'A')bundle.putString("api", "getApprovals" );//TODO: handle the admin user
            frag.setArguments(bundle);
            fragTrans = getSupportFragmentManager().beginTransaction().replace(R.id.main_activity_fragment,frag);
            fragTrans.commit();


        }/*else if (id == R.id.nav_help) {
            final Intent i = new Intent(this, LogInActivity.class);
            startActivity(i);
        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.fab_ocl:
                Intent i = new Intent(this, ApplicationFormOCLActivity.class);
                startActivity(i);
                break;
            case R.id.fab_cl:
                Intent j = new Intent(this, ApplicationFormCLActivity.class);
                startActivity(j);
                break;
        }
    }
}
