package com.jvra.material;

import android.app.Activity;
import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

/**
 * Created by Jansel R. Abreu on 4/17/2015.
 */
public class OtherMetadataFragment extends Fragment{


    /**
     * Request code used for picking a contact.
     */
    public static final int REQUEST_CODE_PICK_CONTACT = 1;

    /**
     * Incremental Integer used for ID for notifications so that each notification will be
     * treated differently.
     */
    private Integer mIncrementalNotificationId = Integer.valueOf(0);

    private NotificationManager mNotificationManager;

    /**
     * Button to show a notification.
     */
    private Button mShowNotificationButton;

    /**
     *  Spinner that holds possible categories used for a notification as
     *  {@link Notification.Builder#setCategory(String)}.
     */
    private Spinner mCategorySpinner;

    /**
     * Spinner that holds possible priorities used for a notification as
     * {@link Notification.Builder#setPriority(int)}.
     */
    private Spinner mPrioritySpinner;

    /**
     * Holds a URI for the person to be attached to the notification.
     */
    //@VisibleForTesting
    Uri mContactUri;

    public static OtherMetadataFragment newInstance(){
        OtherMetadataFragment fragment = new OtherMetadataFragment();
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
        return inflater.inflate(R.layout.fragment_other_metadata, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mShowNotificationButton = (Button) view.findViewById(R.id.show_notification_button);
        mShowNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Priority selectedPriority = (Priority) mPrioritySpinner.getSelectedItem();
                Category selectedCategory = (Category) mCategorySpinner.getSelectedItem();
                showNotificationClicked(selectedPriority, selectedCategory, mContactUri);
            }
        });

        mCategorySpinner = (Spinner) view.findViewById(R.id.category_spinner);
        ArrayAdapter<Category> categoryArrayAdapter = new ArrayAdapter<Category>(getActivity(),
                android.R.layout.simple_spinner_item, Category.values());
        categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCategorySpinner.setAdapter(categoryArrayAdapter);

        mPrioritySpinner = (Spinner) view.findViewById(R.id.priority_spinner);
        ArrayAdapter<Priority> priorityArrayAdapter = new ArrayAdapter<Priority>(getActivity(),
                android.R.layout.simple_spinner_item, Priority.values());
        priorityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPrioritySpinner.setAdapter(priorityArrayAdapter);

        view.findViewById(R.id.attach_person).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findContact();
            }
        });

        view.findViewById(R.id.contact_entry).setVisibility(View.GONE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_PICK_CONTACT:
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactUri = data.getData();
                    mContactUri = contactUri;
                    updateContactEntryFromUri(contactUri);
                }
                break;
        }
    }

    private void findContact() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_PICK_CONTACT);
    }




    /**
     * Enum indicating possible categories in {@link Notification} used from
     * {@link #mCategorySpinner}.
     */
    //@VisibleForTesting
    static enum Category {
        ALARM("alarm"),
        CALL("call"),
        EMAIL("email"),
        ERROR("err"),
        EVENT("event"),
        MESSAGE("msg"),
        PROGRESS("progress"),
        PROMO("promo"),
        RECOMMENDATION("recommendation"),
        SERVICE("service"),
        SOCIAL("social"),
        STATUS("status"),
        SYSTEM("sys"),
        TRANSPORT("transport");

        private final String value;

        Category(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    /**
     * Enum indicating possible priorities in {@link Notification} used from
     * {@link #mPrioritySpinner}.
     */
    //@VisibleForTesting
    static enum Priority {
        DEFAULT(Notification.PRIORITY_DEFAULT),
        MAX(Notification.PRIORITY_MAX),
        HIGH(Notification.PRIORITY_HIGH),
        LOW(Notification.PRIORITY_LOW),
        MIN(Notification.PRIORITY_MIN);

        private final int value;

        Priority(int value) {
            this.value = value;
        }
    }
}
