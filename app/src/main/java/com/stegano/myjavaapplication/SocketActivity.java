package com.stegano.myjavaapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketActivity extends AppCompatActivity {
    private static final String TAG = "SocketActivity";

    EditText editText;
    TextView textView;
    TextView textView2;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);

        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String data = editText.getText().toString();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        send(data);
                    }
                }).start();
            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        startServer();
                    }
                }).start();
            }
        });
    }

    public void printClientLog(final String data) {
        Log.d(TAG, "data : " + data);

        handler.post(new Runnable() {
            @Override
            public void run() {
                textView.append(data + "\n");
            }
        });
    }

    public void printServerLog(final String data) {
        Log.d(TAG, "data : " + data);

        handler.post(new Runnable() {
            @Override
            public void run() {
                textView2.append(data + "\n");
            }
        });
    }

    public void send(String data) {
        try {
            int portNumber = 5001;
            Socket socket = new Socket("localhost", portNumber);  // 소캣 객체 만들기
            printClientLog("소켓 연결함");

            // ObjectInputStream, ObjectOutputStream은 자바 이외의 언어에서 송수신이 비정상적이어서 잘 사용하지 않는다. -> writeObject(), readObject()를 써야함
//            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
//            outputStream.writeObject(data);

            // 주로 DataOutputStream, DataInputStream을 사용함 -> read, write를 readUTF(), writeUTF()를 써야함
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            outputStream.writeUTF(data);
            outputStream.flush();  // 다음 메시지 전송을 위해 버퍼를 지워줌
            printClientLog("데이터 전송함");

            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            printClientLog("서버로부터 받음 : " + inputStream.readUTF());

            socket.close();  // 주고 받은 다음 소켓 닫기

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void startServer() {
        try {
            int portNumber = 5001;
            ServerSocket server = new ServerSocket(portNumber);  // 소캣 객체 만들기
            printServerLog("서버 시작, port : " + portNumber);

            while (true) {
                Socket socket = server.accept();
                InetAddress clientHost = socket.getLocalAddress();
                int clientPort = socket.getPort();
                printServerLog("클라이언트 연결됨 : " + clientHost + " : " + clientPort);

                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                String obj = inputStream.readUTF();
                printServerLog("데이터 받음 : " + obj);

                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                outputStream.writeUTF(obj + " from Server");
                outputStream.flush();
                printServerLog("데이터 보냄");

                socket.close();  // 주고 받은 다음 소켓 닫기
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}