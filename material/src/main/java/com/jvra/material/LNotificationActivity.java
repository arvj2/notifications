package com.jvra.material;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class LNotificationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        setTitle( R.string.title_lnotification_activity );

        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode( ActionBar.NAVIGATION_MODE_TABS );

        ActionBar.Tab tabHeadsUpNotification = actionBar.newTab().setText("Heads Up");
        ActionBar.Tab tabVisibilityMetadata = actionBar.newTab().setText("Visibility");
        ActionBar.Tab tabOtherMetadata = actionBar.newTab().setText("Others");
        tabHeadsUpNotification.setTabListener(new FragmentTabListener(HeadsUpNotificationFragment
                .newInstance()));
        tabVisibilityMetadata.setTabListener(new FragmentTabListener(VisibilityMetadataFragment
                .newInstance()));
        tabOtherMetadata.setTabListener(new FragmentTabListener(OtherMetadataFragment.newInstance
                ()));
        actionBar.addTab(tabHeadsUpNotification, 0);
        actionBar.addTab(tabVisibilityMetadata, 1);
        actionBar.addTab(tabOtherMetadata, 2);
    }


    private class FragmentTabListener extends ActionBar.TabListener{

        private Fragment fragment;

        public FragmentTabListener(Fragment fragment) {
            this.fragment = fragment;
        }

        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
            fragmentTransaction.replace( R.id.container,fragment );
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
            fragmentTransaction.remove( fragment );
        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

        }
    }
}
