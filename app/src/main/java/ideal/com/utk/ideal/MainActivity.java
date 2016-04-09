package ideal.com.utk.ideal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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
import android.widget.TextView;
import android.widget.Toast;

import ideal.com.utk.ideal.custom_datatypes.User_details;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{



    User_details userDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_drawer_top);
        navigationView.setNavigationItemSelectedListener(this);


        userDetails = new User_details(this);

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


        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Leave application form", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                startActivity(leave_app_activity);
            }
        });*/
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, as long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment frag;
        FragmentTransaction fragTrans;
        if (id == R.id.nav_leave_process) {
            frag = new LeaveProcessingFragment();
            fragTrans = getSupportFragmentManager().beginTransaction().replace(R.id.main_activity_fragment,frag);
            fragTrans.commit();
        } else if (id == R.id.nav_leave_approved) {

            frag = new LeaveApprovedFragment();
            fragTrans = getSupportFragmentManager().beginTransaction().replace(R.id.main_activity_fragment,frag);
            fragTrans.commit();
        } else if (id == R.id.nav_leave_rejected) {
            frag = new LeaveRejectedFragment();
            fragTrans = getSupportFragmentManager().beginTransaction().replace(R.id.main_activity_fragment,frag);
            fragTrans.commit();


        } /**else if (id == R.id.nav_debug) {
            final Intent i = new Intent(this, LogInActivity.class);
            startActivity(i);
        }**/

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
