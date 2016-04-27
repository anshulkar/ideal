package ideal.com.utk.ideal;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class UserAccountActivity extends AppCompatActivity {

    LinearLayout passcontainer;
    LinearLayout recommcontainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final EditText oldpass = (EditText)findViewById(R.id.usersett_oldpassword);
        final EditText newpass = (EditText)findViewById(R.id.usersett_newpassword);
        final Button savepass = (Button)findViewById(R.id.usersett_pass_but);
        passcontainer = (LinearLayout)findViewById(R.id.user_set_showpassopt);

        recommcontainer = (LinearLayout)findViewById(R.id.user_set_showrecommopt);
        final View passcardview = findViewById(R.id.user_sett_passcard_view);
        passcardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passcontainer.getVisibility()== View.VISIBLE)passcontainer.setVisibility(View.GONE);
                else {
                    collapse_all_cards();
                    passcontainer.setVisibility(View.VISIBLE);
                }
            }
        });
        final View recommcardview = findViewById(R.id.user_sett_recommcard_view);
        recommcardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(recommcontainer.getVisibility()== View.VISIBLE)recommcontainer.setVisibility(View.GONE);
                else {
                    collapse_all_cards();
                    recommcontainer.setVisibility(View.VISIBLE);
                }
            }
        });



    }

    private void collapse_all_cards(){
        passcontainer.setVisibility(View.GONE);
    }

}
