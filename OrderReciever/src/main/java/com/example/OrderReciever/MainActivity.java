package com.example.OrderReciever;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Sidie88 on 9/15/2014.
 */
public class MainActivity extends Activity {
    TextView textView1;
    EditText editIP;
    boolean mBounded;
    ClientService mServer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        textView1 = (TextView) findViewById(R.id.textViewLog);
        editIP = (EditText) findViewById(R.id.editTextIp);

    }

    public void onClickCon(View v) {
        try {
            String ip = editIP.getText().toString();

            Intent service = new Intent(this, ClientService.class);
            service.putExtra("IP_ADDRESS", ip);
            startService(service);

            bindService(service, mConnection, BIND_AUTO_CREATE);

        } catch (Exception e) {
            Toast.makeText(getBaseContext(),
                    e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    public void onClickSend(View v) {

        mServer.onClickSend("Feedback");
    }

    ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(getBaseContext(), "Service is disconnected", Toast.LENGTH_LONG).show();
            mBounded = false;
            mServer = null;
        }

        public void onServiceConnected(ComponentName name, IBinder service) {
            Toast.makeText(getBaseContext(), "Service is connected", Toast.LENGTH_LONG).show();
            mBounded = true;
            ClientService.LocalBinder mLocalBinder = (ClientService.LocalBinder) service;
            mServer = mLocalBinder.getClientServiceInstance();
        }
    };

//    public class ClientThread implements Runnable {
//
//        private String ipAddress;
//
//        public ClientThread(String ipAddress) {
//            this.ipAddress = ipAddress;
//        }
//
//        public void run() {
//            try {
//                InetAddress serverAddr = InetAddress.getByName(ipAddress);
//
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        textView1.setText(textView1.getText()
//                                + "Connecting to the server");
//                    }
//                });
//
//                socket = new Socket(serverAddr, SERVER_PORT);
//                try {
//                    printWriter = new PrintWriter(new BufferedWriter(
//                            new OutputStreamWriter(socket.getOutputStream())),
//                            true);
//
//                    //---get an InputStream object to read from the server---
//                    BufferedReader br = new BufferedReader(
//                            new InputStreamReader(socket.getInputStream()));
//
//                    try {
//                        //---read all incoming data terminated with a \n
//                        // char---
//                        String line = null;
//                        while ((line = br.readLine()) != null) {
//                            final String strReceived = line;
//
//                            handler.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    textView1.setText(textView1.getText()
//                                            + "\n" + strReceived);
//                                    if(strReceived.startsWith("ORDER")){
//                                        String[] split = strReceived.split("#");
////                                        createNotify(split[1].toString(), split[2].toString(), split[3].toString());
//                                    }
//                                }
//                            });
//                        }
//
//                        //---disconnected from the server---
//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                textView1.setText(textView1.getText()
//                                        + "\n" + "Client disconnected");
//                            }
//                        });
//
//                    } catch (Exception e) {
//                        final String error = e.getLocalizedMessage();
//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                textView1.setText(textView1.getText() + "\n" + error);
//                            }
//                        });
//                    }
//
//                } catch (Exception e) {
//                    final String error = e.getLocalizedMessage();
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            textView1.setText(textView1.getText() + "\n" + error);
//                        }
//                    });
//                }
//
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        textView1.setText(textView1.getText()
//                                + "\n" + "Connection closed.");
//                    }
//                });
//
//            } catch (Exception e) {
//                final String error = e.getLocalizedMessage();
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        textView1.setText(textView1.getText() + "\n" + error);
//                    }
//                });
//            }
//        }
//    }

//	@Override
//	protected void onStart() {
//		super.onStart();
//		Thread clientThread = new Thread(new ClientThread());
//		clientThread.start();
//	}

//    @Override
//    protected void onStop() {
//        super.onStop();
//        try {
//            if(socket != null){
//                socket.shutdownInput();
//                socket.close();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
