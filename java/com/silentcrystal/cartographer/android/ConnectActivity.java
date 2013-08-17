package com.silentcrystal.cartographer.android;

import android.app.Activity;
import android.os.Bundle;
import android.os.AsyncTask;

import android.widget.Button;
import android.widget.EditText;

import android.view.View;
import android.view.View.OnClickListener;

import android.content.Intent;

import com.silentcrystal.cartographer.R;
import com.silentcrystal.cartographer.network.ServerConnection;

public class ConnectActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_connect);
		
		Button loginButton = (Button) findViewById(R.id.loginButton);
		final EditText serverAddress = (EditText) findViewById(R.id.serverAddress);
		final EditText userName = (EditText) findViewById(R.id.userName);
		final EditText userPassword = (EditText) findViewById(R.id.userPassword);
		
		loginButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				String  user     = userName.getText().toString(),
						password = userPassword.getText().toString(),
						server   = serverAddress.getText().toString();
				ServerConnection serverConnection = new ServerConnection(server, user, password);
				
				(new ConnectTask()).execute(serverConnection, view);
			}
		});
	}
	
	public class ConnectTask extends AsyncTask<Object, Void, Void> {
		public Void doInBackground(Object...params) {
			ServerConnection server = (ServerConnection) params[0];
			View view = (View) params[1];
			server.connect();
			
			if (server.getStatus() == ServerConnection.CONNECTED) {
				Intent mainActivity = new Intent(view.getContext(), MainActivity.class);
				mainActivity.putExtra("SeverConnection", server);
				startActivity(mainActivity);
			}
			
			return null;
		}
	}
}
