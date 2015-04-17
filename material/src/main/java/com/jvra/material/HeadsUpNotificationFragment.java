package com.jvra.material;

import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

/**
 * Created by Jansel R. Abreu on 4/17/2015.
 */
public class HeadsUpNotificationFragment extends Fragment{


    private static final int NOTIFICATION_ID = 1;

    private NotificationManager nm;

    private Button mShowNotificationButton;

    private CheckBox mUseHeadsUpCheckbox;

    public static HeadsUpNotificationFragment newInstance(){
        HeadsUpNotificationFragment fragment = new HeadsUpNotificationFragment();
        fragment.setRetainInstance(true);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nm = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE );
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate( R.layout.fragment_heads_up_notification,container,false );
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mShowNotificationButton = (Button) view.findViewById(R.id.show_notification_button);
        mShowNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nm.notify(NOTIFICATION_ID, createNotification(
                        mUseHeadsUpCheckbox.isChecked()));
                Toast.makeText(getActivity(), "Show Notification clicked", Toast.LENGTH_SHORT).show();
            }
        });
        mUseHeadsUpCheckbox = (CheckBox) view.findViewById(R.id.use_heads_up_checkbox);
    }


    private Notification createNotification( boolean headup ){
        Notification.Builder builder = new Notification.Builder(getActivity());
        builder.setSmallIcon( R.drawable.ic_launcher_notification )
                .setPriority( Notification.PRIORITY_DEFAULT )
                .setCategory( Notification.CATEGORY_MESSAGE )
                .setContentTitle( "Sample Notification" )
                .setContentText( "This is the normal notification" );

        if (headup) {
            Intent push = new Intent();
            push.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
            push.setClass( getActivity(),LNotificationActivity.class );


            PendingIntent fullScreen = PendingIntent.getActivity(getActivity(),0,push,PendingIntent.FLAG_CANCEL_CURRENT );
            builder.setContentText( "Head-up Notification on Android L" );
            builder.setFullScreenIntent(fullScreen,true);
        }
        return builder.build();
    }
}
