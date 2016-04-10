package ideal.com.utk.ideal;

/**
 * Created by Utkarsh on 11-04-2016 with the help of SWAG.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class LeaveStatusViewPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public LeaveStatusViewPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        LeaveProcessingFragment tab1 = new LeaveProcessingFragment();
        LeaveApprovedFragment tab2 = new LeaveApprovedFragment();
        LeaveRejectedFragment tab3 = new LeaveRejectedFragment();

        switch (position) {
            case 0:
                return tab1;
            case 1:
                return tab2;
            case 2:
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}