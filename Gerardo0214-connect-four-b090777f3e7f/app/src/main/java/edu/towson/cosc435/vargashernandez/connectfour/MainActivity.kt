package edu.towson.cosc435.vargashernandez.connectfour

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import edu.towson.cosc435.vargashernandez.connectfour.ui.theme.ConnectFourTheme
import androidx.compose.material.*


class MainActivity : ComponentActivity() {
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "i.apps.notifications"
    private val description = "Connect Four Notification"

    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as
                NotificationManager
        setContent {
            ConnectFourTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    start()
                    NavigationDrawer()
                    Notificationfunc()
                }
            }
        }
    }

    fun Notificationfunc() {
        val intent = Intent(this, LauncherActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.lightColor
            notificationManager.createNotificationChannel(notificationChannel)
            builder = Notification.Builder(this, channelId)
                .setContentTitle("Welcome to Connect Four!")
                .setContentText("Let's Play Connect Four")
                    //change image to connect four logo
                .setSmallIcon(R.drawable.logo)
                .setContentIntent(pendingIntent)
        }
        notificationManager.notify(12345, builder.build())
    }
}

