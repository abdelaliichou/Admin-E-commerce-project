package com.example.admin_ecommerce.Model;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.admin_ecommerce.R;
import com.example.admin_ecommerce.View.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class Messaging_Service extends FirebaseMessagingService {

    NotificationManager notificationManager ;
    String noteTitle , noteMessage , noteType , imageUrl ;
    String CHANNEL_ID = "ID" ;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (message != null){

            // recevoire simple message
            noteTitle = message.getNotification().getTitle();
            noteMessage = message.getNotification().getBody();
            pushNotification(noteTitle,noteMessage);
        }
    }

    @Override
    public void onNewToken(@NonNull String token) {
        // required for group messaging
        super.onNewToken(token);
    }

    private void pushNotification(String title , String message) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            // here we must create our own channel

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID
                    ,"You are in the Settings" // this message will appear when we GO TO THE SETTING of our noti
                    , NotificationManager.IMPORTANCE_DEFAULT);  // this is the importance of our notification ( the ability of swiping this noti )
            channel.setDescription("My channel description "); // this is another message that will appear when we go to the settings of our message

            NotificationManager nm = getSystemService(NotificationManager.class);
            nm.createNotificationChannel(channel);

        }

        // now we will do the procedure that any phone have the SDK <= 26 also directly enter to it
        // also who have the SDK >= 26 will enter to it but after creating the precedent channel

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT); // this is the intent that when we click an icon in the notification it goes to this intent

        Uri Rington_Sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION); // sound of the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID); // means that if there is a notification and he needed to a channel so
        // he will connect it with the channel tht we have create in the top

        builder.setSmallIcon(R.drawable.avatar)
                .setContentTitle(title) // notification title
                .setContentText(message) // the text in the notification (description)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSound(Rington_Sound)
                .setColor(getResources().getColor(R.color.mainyellow))
                .setContentIntent(pi) // when we click in the notification , it will use this pending intent ( in this case , it will take us to the MainActivity )
               // .setStyle(new NotificationCompat.BigTextStyle().bigText("This is the style")) // this text is also a description text but it hase more styles and features (by writing this line , the first description will not appear , and this description will appear in his place )
                .addAction(R.drawable.ic_flesh,"Replay",pi); // this is the additional button that exists in the button of the noti it hase an icon , a text , and an action ( in this case , when we click in this replay button , it will take use to the MainActivity using the PI pending intent that we have create in the top )

        NotificationManagerCompat nmc = NotificationManagerCompat.from(this);
        nmc.notify(10,builder.build());
    }

}
