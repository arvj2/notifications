package com.jvra.custom;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RemoteViews;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void createNotification(View v ){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags( Intent.FLAG_ACTIVITY_SINGLE_TOP );

        PendingIntent pIntent = PendingIntent.getActivity(this,0, intent,PendingIntent.FLAG_UPDATE_CURRENT );
        builder.setContentIntent(pIntent);

        builder.setTicker( getString( R.string.custom_notification ) );
        builder.setSmallIcon( R.drawable.ic_stat_custom );
        builder.setAutoCancel( true );

        Notification not = builder.build();

        RemoteViews contentView = new RemoteViews(getPackageName(),R.layout.notification);

        final String time = DateFormat.getTimeInstance().format( new Date() ).toString();
        final String text = getString( R.string.collapsed, time );

        contentView.setTextViewText( R.id.textView,text );
        not.contentView = contentView;


        if(Build.VERSION.SDK_INT >= 16 ){
            RemoteViews bigView = new RemoteViews(getPackageName(),R.layout.notification_expanded);
            not.bigContentView = bigView;
        }

        NotificationManager nm  = ( NotificationManager ) getSystemService(Context.NOTIFICATION_SERVICE );
        nm.notify( 0, not );
    }


    public void showNotificationClicked(View v ){
        createNotification(v);
    }
}
