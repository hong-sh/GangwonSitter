package com.example.yeah1.sitter.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.yeah1.sitter.R;
import com.example.yeah1.sitter.dto.ChattingMessage;
import com.example.yeah1.sitter.dto.ChattingRoom;
import com.example.yeah1.sitter.dto.UserInfo;
import com.example.yeah1.sitter.view.ChattingActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by hong on 2017. 11. 18..
 */

public class FirebaseBackgroundService extends Service implements ChildEventListener{

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReferenceChattingRoom;
    private Set<String> chattingRoomSet;

    private SitterApplication app;
    private HashMap<String, ArrayList<ChattingMessage>> chattingMessageList;
    private ArrayList<ChattingRoom> chattingRoomArrayList;
    private UserInfo userInfo;

    public static final String NEWCHATTINGMESSAGE_ACTION = "com.broadcast.newchattingmessage";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        app = (SitterApplication)getApplication();
        chattingMessageList = app.getInstanceOfChattingMessageList();
        chattingRoomArrayList = app.getInstanceOfChattingRoomArrayList();
        userInfo = app.getInstanceOfUserInfo();

        chattingRoomSet = new HashSet<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceChattingRoom = firebaseDatabase.getReference(userInfo.getUserName());
        Log.d("MyTag", "name = " +userInfo.getUserName());

        databaseReferenceChattingRoom.addChildEventListener(this);
        /*
        databaseReferenceChattingRoom.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator();

                while(child.hasNext())
                {
                    DataSnapshot dataSnapshotChattingRoom = child.next();


                    ChattingRoom chattingRoom = dataSnapshotChattingRoom.getValue(ChattingRoom.class);
                    final String chattingRoomName = chattingRoom.getChattingRoomName();

                    if(!chattingRoomSet.contains(chattingRoomName)) {
                        chattingRoomSet.add(chattingRoomName);
                        chattingRoomArrayList.add(chattingRoom);
                        //////get existing messages
                        chattingMessageList.put(chattingRoomName, new ArrayList<ChattingMessage>());
                        DatabaseReference databaseReferenceChatting =  firebaseDatabase.getReference(chattingRoomName);


                        databaseReferenceChatting.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Iterator<DataSnapshot> childChatting = dataSnapshot.getChildren().iterator();
                                while(childChatting.hasNext()) {
                                    DataSnapshot dataSnapshotMessage = childChatting.next();
                                    ChattingMessage chattingMessage = dataSnapshotMessage.getValue(ChattingMessage.class);

                                    Log.d("BackgroundService", "sender = " +chattingMessage.getSender());
                                    Log.d("BackgroundService", "receiver = " +chattingMessage.getReceiver());
                                    Log.d("BackgroundService", "content = " +chattingMessage.getContent());
                                    Log.d("BackgroundService", "date = " +chattingMessage.getDate());
                                    chattingMessageList.get(chattingRoomName).add(chattingMessage);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        ////////

                        //add new message listener
                        firebaseDatabase.getReference(chattingRoomName).addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                ChattingMessage chattingMessage = dataSnapshot.getValue(ChattingMessage.class);

                                Log.d("BackgroundService", "sender = " +chattingMessage.getSender());
                                Log.d("BackgroundService", "receiver = " +chattingMessage.getReceiver());
                                Log.d("BackgroundService", "content = " +chattingMessage.getContent());
                                Log.d("BackgroundService", "date = " +chattingMessage.getDate());
                                chattingMessageList.get(chattingRoomName).add(dataSnapshot.getValue(ChattingMessage.class));
                            }

                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onChildRemoved(DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        ///////
                    }


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        */

    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        ChattingRoom chattingRoom = dataSnapshot.getValue(ChattingRoom.class);
        final String chattingRoomName = chattingRoom.getChattingRoomName();
        Log.d("MyTag", "roomname = " +chattingRoomName);
        if(!chattingRoomSet.contains(chattingRoomName)) {
            chattingRoomSet.add(chattingRoomName);
            chattingRoomArrayList.add(chattingRoom);
            chattingMessageList.put(chattingRoomName, new ArrayList<ChattingMessage>());

            if(userInfo.isSitter()) {
                Intent notificationIntent = new Intent(this, ChattingActivity.class);
                notificationIntent.putExtra("chattingRoomName", chattingRoomName);
                notificationIntent.putExtra("receiverEmail", chattingRoom.getMomID());
                PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(this)
                                .setSmallIcon(R.drawable.appicon)
                                .setContentTitle("아가를 부탁해")
                                .setContentText(chattingRoom.getMomName() + "님이 온라인 미팅을 신청하였습니다.")
                                .setContentIntent(contentIntent)
                                .setAutoCancel(true)
                                .setWhen(System.currentTimeMillis())
                                .setDefaults(Notification.DEFAULT_ALL);

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    mBuilder.setCategory(Notification.CATEGORY_MESSAGE)
                            .setPriority(Notification.PRIORITY_HIGH)
                            .setVisibility(Notification.VISIBILITY_PUBLIC);
                }

                NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                nm.notify(1234, mBuilder.build());
            } else {
                Intent notificationIntent = new Intent(this, ChattingActivity.class);
                notificationIntent.putExtra("chattingRoomName", chattingRoomName);
                notificationIntent.putExtra("receiverEmail", chattingRoom.getSitterID());
                PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(this)
                                .setSmallIcon(R.drawable.appicon)
                                .setContentTitle("아가를 부탁해")
                                .setContentText(chattingRoom.getSitterName() + "님과 온라인 미팅을 위한 채팅방이 개설되었습니다.")
                                .setContentIntent(contentIntent)
                                .setAutoCancel(true)
                                .setWhen(System.currentTimeMillis())
                                .setDefaults(Notification.DEFAULT_ALL);

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    mBuilder.setCategory(Notification.CATEGORY_MESSAGE)
                            .setPriority(Notification.PRIORITY_HIGH)
                            .setVisibility(Notification.VISIBILITY_PUBLIC);
                }

                NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                nm.notify(1234, mBuilder.build());
            }

            firebaseDatabase.getReference(chattingRoomName).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    ChattingMessage chattingMessage = dataSnapshot.getValue(ChattingMessage.class);

                    Log.d("BackgroundService", "sender = " +chattingMessage.getSender());
                    Log.d("BackgroundService", "receiver = " +chattingMessage.getReceiver());
                    Log.d("BackgroundService", "content = " +chattingMessage.getContent());
                    Log.d("BackgroundService", "date = " +chattingMessage.getDate());
                    chattingMessageList.get(chattingRoomName).add(dataSnapshot.getValue(ChattingMessage.class));
                    Intent intent = new Intent(NEWCHATTINGMESSAGE_ACTION);
                    sendBroadcast(intent);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}


//ChattingRoom chattingRoom = new ChattingRoom("강원맘", "gangwon@mom.com", "강원시터", "gangwon@sitter.com", "chattingRoomTmp");
//databaseReferenceChattingRoom.push().setValue(chattingRoom);


//        ChattingMessage tmpChattingMessage = new ChattingMessage("gangwon@mom.com", "gangwon@sitter.com", "content", "date");
//        ChattingMessage tmpChattingMessage1 = new ChattingMessage("gangwon@mom.com", "gangwon@sitter.com", "content1", "date");
//        ChattingMessage tmpChattingMessage2 = new ChattingMessage("gangwon@sitter.com", "gangwon@mom.com", "content2", "date");
//        ChattingMessage tmpChattingMessage3 = new ChattingMessage("gangwon@sitter.com", "gangwon@mom.com", "content3", "date");
//        ChattingMessage tmpChattingMessage4 = new ChattingMessage("gangwon@sitter.com", "gangwon@mom.com", "content4", "date");
//        databaseReferenceChattingRoom.getRoot().child("chattingRoomTmp").push().setValue(tmpChattingMessage);
//        databaseReferenceChattingRoom.getRoot().child("chattingRoomTmp").push().setValue(tmpChattingMessage1);
//        databaseReferenceChattingRoom.getRoot().child("chattingRoomTmp").push().setValue(tmpChattingMessage2);
//        databaseReferenceChattingRoom.getRoot().child("chattingRoomTmp").push().setValue(tmpChattingMessage3);
//        databaseReferenceChattingRoom.getRoot().child("chattingRoomTmp").push().setValue(tmpChattingMessage4);
//databaseReferenceChattingRoom.child("chattingRoom1").setValue("senderID");