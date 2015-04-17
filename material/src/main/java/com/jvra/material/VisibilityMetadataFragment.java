package com.jvra.material;

import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by Jansel R. Abreu on 4/17/2015.
 */
public class VisibilityMetadataFragment extends Fragment{

    private NotificationManager mNotificationManager;

    /**
     * {@link RadioGroup} that has Visibility RadioButton in its children.
     */
    private RadioGroup mRadioGroup;

    /**
     * Incremental Integer used for ID for notifications so that each notification will be
     * treated differently.
     */
    private Integer mIncrementalNotificationId = Integer.valueOf(0);

    private Button mShowNotificationButton;

    public static VisibilityMetadataFragment newInstance(){
        VisibilityMetadataFragment fragment = new VisibilityMetadataFragment();
        fragment.setRetainInstance(true);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNotificationManager = (NotificationManager) getActivity().getSystemService(Context
                .NOTIFICATION_SERVICE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_visibility_metadata_notification, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mShowNotificationButton = (Button) view.findViewById(R.id.show_notification_button);
        mShowNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationVisibility visibility = getVisibilityFromSelectedRadio(mRadioGroup);
                showNotificationClicked(visibility);
            }
        });
        mRadioGroup = (RadioGroup) view.findViewById(R.id.visibility_radio_group);
    }

    private void showNotificationClicked(NotificationVisibility visibility) {
        // Assigns a unique (incremented) notification ID in order to treat each notification as a
        // different one. This helps demonstrate how a notification with a different visibility
        // level differs on the lockscreen.
        mIncrementalNotificationId = new Integer(mIncrementalNotificationId + 1);
        mNotificationManager.notify(mIncrementalNotificationId, createNotification(visibility));
        Toast.makeText(getActivity(), "Show Notification clicked", Toast.LENGTH_SHORT).show();
    }

    Notification createNotification(NotificationVisibility visibility) {
        Notification.Builder notificationBuilder = new Notification.Builder(getActivity())
                .setContentTitle("Notification for Visibility metadata");

        notificationBuilder.setVisibility(visibility.getVisibility());
        notificationBuilder.setContentText(String.format("Visibility : %s",
                visibility.getDescription()));
        notificationBuilder.setSmallIcon(visibility.getNotificationIconId());

        return notificationBuilder.build();
    }

    private NotificationVisibility getVisibilityFromSelectedRadio(RadioGroup radiogroup) {
        switch (radiogroup.getCheckedRadioButtonId()) {
            case R.id.visibility_public_radio_button:
                return NotificationVisibility.PUBLIC;
            case R.id.visibility_private_radio_button:
                return NotificationVisibility.PRIVATE;
            case R.id.visibility_secret_radio_button:
                return NotificationVisibility.SECRET;
            default:
                //If not selected, returns PUBLIC as default.
                return NotificationVisibility.PUBLIC;
        }
    }

    static enum NotificationVisibility {
        PUBLIC(Notification.VISIBILITY_PUBLIC, "Public", R.drawable.ic_public_notification),
        PRIVATE(Notification.VISIBILITY_PRIVATE, "Private", R.drawable.ic_private_notification),
        SECRET(Notification.VISIBILITY_SECRET, "Secret", R.drawable.ic_secret_notification);

        /**
         * Visibility level of the notification.
         */
        private final int mVisibility;

        /**
         * String representation of the visibility.
         */
        private final String mDescription;

        /**
         * Id of an icon used for notifications created from the visibility.
         */
        private final int mNotificationIconId;

        NotificationVisibility(int visibility, String description, int notificationIconId) {
            mVisibility = visibility;
            mDescription = description;
            mNotificationIconId = notificationIconId;
        }

        public int getVisibility() {
            return mVisibility;
        }

        public String getDescription() {
            return mDescription;
        }

        public int getNotificationIconId() {
            return mNotificationIconId;
        }
    }
}
