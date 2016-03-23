package com.android.loginapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.android.database.DatabaseAdapter;
import com.ofix.barcode.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The main application activity which serves as a login page.
 *
 *
 */

public class login extends Activity {

	public static final String MY_PREFS = "SharedPreferences";
	private DatabaseAdapter dbHelper;
	private EditText theUsername;
	private EditText thePassword;
	private Button loginButton;
	private Button registerButton;
	private Button clearButton;
	private Button exitButton;
	private CheckBox rememberDetails;
	String outUser ="";
	String outPass = "";
	String Tblname ="";
	SQLiteDatabase db;
	StringBuilder stringBuilder;
	String[] jumbo;
	String[] num ;
	String[] sex ;
	String[] dob ;
	String[] name ;
	String[] status ;
	String[] breed ;
	String[] dam ;
	String[] sire ;
	String[] replacement;
	String[] replacement_maternal ;
	String[] terminal ;
	String[] replacement_maternal_prog ;
	String[] dairy ;
	String[] calving_diff;
	String[] trait_reliability ;
	/**
	 * Called when the activity is first created.
	 */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		SharedPreferences mySharedPreferences = getSharedPreferences(MY_PREFS, 0);
		Editor editor = mySharedPreferences.edit();
		editor.putLong("uid", 0);
		editor.commit();

		dbHelper = new DatabaseAdapter(this);
		dbHelper.open();
		String path = "ICBF";
		db = this.openOrCreateDatabase(path,MODE_PRIVATE, null);
		setContentView(R.layout.main);
		initControls();
	}

	private void initControls() {
		//Set the activity layout.

		theUsername = (EditText) findViewById(R.id.Username);
		thePassword = (EditText) findViewById(R.id.Password);
		loginButton = (Button) findViewById(R.id.Login);
		registerButton = (Button) findViewById(R.id.Register);
		clearButton = (Button) findViewById(R.id.Clear);
		exitButton = (Button) findViewById(R.id.Exit);
		rememberDetails = (CheckBox) findViewById(R.id.RememberMe);



		//Create touch listeners for all buttons.
		loginButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				LogMeIn(v);
			}
		});

		registerButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Register(v);
			}
		});

		clearButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				ClearForm();
			}
		});

		exitButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Exit();
			}
		});
		//Create remember password check box listener.
		rememberDetails.setOnClickListener(new CheckBox.OnClickListener() {
			public void onClick(View v) {
				RememberMe();
			}
		});

		//Handle remember password preferences.
		SharedPreferences prefs = getSharedPreferences(MY_PREFS, 0);
		String thisUsername = prefs.getString("username", "");
		String thisPassword = prefs.getString("password", "");
		boolean thisRemember = prefs.getBoolean("remember", false);
		if (thisRemember) {
			theUsername.setText(thisUsername);
			thePassword.setText(thisPassword);
			rememberDetails.setChecked(thisRemember);
		}

	}

	/**
	 * Deals with Exit option - exits the application.
	 */
	private void Exit() {
		finish();
	}

	/**
	 * Clears the login form.
	 */
	private void ClearForm() {
		saveLoggedInUId(0, "", "");
		theUsername.setText("");
		thePassword.setText("");
	}

	/**
	 * Handles the remember password option.
	 */
	private void RememberMe() {
		boolean thisRemember = rememberDetails.isChecked();
		SharedPreferences prefs = getSharedPreferences(MY_PREFS, 0);
		Editor editor = prefs.edit();
		editor.putBoolean("remember", thisRemember);
		editor.commit();
	}

	/**
	 * This method handles the user login process.
	 *
	 * @param v
	 */
	private void LogMeIn(View v) {
		//Get the username and password
		String thisUsername = theUsername.getText().toString();
		String thisPassword = thePassword.getText().toString();

		outUser = thisUsername;
		outPass = thisPassword;
		Tblname = thisUsername;
		JSONObject json = new JSONObject();
		try {
			json.put("username",outUser);
			json.put("password",outPass);

			String baseUrl = "http://192.168.1.4:8080/InputOutput";

			new HttpAsyncTask().execute(baseUrl, json.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}




		System.out.println("*******************"+ thisUsername + "*********************"+ thisPassword);
		System.out.println("Im after logging in .................@@@@@@@@@@@@");
		//Assign the hash to the password
		thisPassword = md5(thisPassword);                   //send down to encrypt username to be saved on db

		// Check the existing user name and password database
		Cursor theUser = dbHelper.fetchUser(thisUsername, thisPassword);
		if (theUser != null) {
			startManagingCursor(theUser);
			if (theUser.getCount() > 0) {
				//  saveLoggedInUId(theUser.getLong(theUser.getColumnIndex(DatabaseAdapter.COL_ID)), thisUsername, thePassword.getText().toString());
				saveLoggedInUId(theUser.getLong(theUser.getColumnIndex(DatabaseAdapter.COL_ID)), thisUsername, thisPassword);
				stopManagingCursor(theUser);
				theUser.close();
				Intent i = new Intent(v.getContext(), Helloworld.class);
				startActivity(i);
			}

			//Returns appropriate message if no match is made
			else {
				Toast.makeText(getApplicationContext(),
						"You have entered an incorrect username or password.",
						Toast.LENGTH_SHORT).show();
				saveLoggedInUId(0, "", "");
			}
			stopManagingCursor(theUser);
			theUser.close();
		} else {
			Toast.makeText(getApplicationContext(),
					"Database query error",
					Toast.LENGTH_SHORT).show();
		}
	}
	private class HttpAsyncTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {

			String jsonString = "";

			try {
				jsonString = HttpUtils.urlContentPost(urls[0], "login", urls[1]);
			} catch (IOException e) {
				e.printStackTrace();
			}

			System.out.println(jsonString);
			return jsonString;
		}//do in background
		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			JSONObject jsonResult = null;
			try {
				jsonResult = new JSONObject(result);
				JSONArray Jinjumbo = jsonResult.getJSONArray("jumbo");
				JSONArray Jinnum = jsonResult.getJSONArray("num");
				JSONArray Jinsex = jsonResult.getJSONArray("sex");
				JSONArray Jindob = jsonResult.getJSONArray("dob");
				JSONArray Jinname = jsonResult.getJSONArray("name");
				JSONArray Jinstatus = jsonResult.getJSONArray("status");
				JSONArray Jinbreed = jsonResult.getJSONArray("breed");
				JSONArray Jindam = jsonResult.getJSONArray("dam");
				JSONArray Jinsire = jsonResult.getJSONArray("sire");
				JSONArray Jinreplacement = jsonResult.getJSONArray("replacement");
				JSONArray Jinreplacement_maternal = jsonResult.getJSONArray("replacement_maternal");
				JSONArray Jinterminal = jsonResult.getJSONArray("terminal");
				JSONArray Jinreplacement_maternal_prog = jsonResult.getJSONArray("replacement_maternal_prog");
				JSONArray Jindairy = jsonResult.getJSONArray("dairy");
				JSONArray Jincalving_diff = jsonResult.getJSONArray("calving_diff");
				JSONArray Jintrait_reliability = jsonResult.getJSONArray("trait_reliability");

				jumbo = new String[Jinjumbo.length()];
				num = new String[Jinjumbo.length()];
				sex = new String[Jinjumbo.length()];
				dob = new String[Jinjumbo.length()];
				name = new String[Jinjumbo.length()];
				status = new String[Jinjumbo.length()];
				breed = new String[Jinjumbo.length()];
				dam = new String[Jinjumbo.length()];
				sire = new String[Jinjumbo.length()];
				replacement = new String[Jinjumbo.length()];
				replacement_maternal = new String[Jinjumbo.length()];
				terminal = new String[Jinjumbo.length()];
				replacement_maternal_prog = new String[Jinjumbo.length()];
				dairy = new String[Jinjumbo.length()];
				calving_diff = new String[Jinjumbo.length()];
				trait_reliability = new String[Jinjumbo.length()];

				for (int i = 0; i < Jinjumbo.length() ; i++) {

					jumbo[i] = Jinjumbo.getString(i);
					num[i] = Jinnum.getString(i);
					sex[i] = Jinsex.getString(i);
					dob[i] = Jindob.getString(i);
					name[i] = Jinname.getString(i);
					status[i] = Jinstatus.getString(i);
					breed[i] = Jinbreed.getString(i);
					dam [i] = Jindam.getString(i);
					sire[i] = Jinsire.getString(i);
					replacement[i] = Jinreplacement.getString(i);
					replacement_maternal[i] = Jinreplacement_maternal.getString(i);
					terminal[i] = Jinterminal.getString(i);
					replacement_maternal_prog[i] = Jinreplacement_maternal_prog.getString(i);
					dairy[i] = Jindairy.getString(i);
					calving_diff[i] = Jincalving_diff.getString(i);
					trait_reliability[i] = Jintrait_reliability.getString(i);

				}
				addDataBaseTable();
				insertData();

			} catch (JSONException e) {
				e.printStackTrace();
			}

			Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
		}//on post execute
	}//http async task
	/**
	 * Open the Registration activity.
	 *
	 * @param v
	 */
	private void Register(View v) {
		Intent i = new Intent(v.getContext(), Register.class);
		startActivity(i);
	}

	private void saveLoggedInUId(long id, String username, String password) {
		SharedPreferences settings = getSharedPreferences(MY_PREFS, 0);
		Editor myEditor = settings.edit();
		myEditor.putLong("uid", id);
		myEditor.putString("username", username);
		myEditor.putString("password", password);
		boolean rememberThis = rememberDetails.isChecked();
		myEditor.putBoolean("rememberThis", rememberThis);
		myEditor.commit();
	}

	/**
	 * Deals with the password encryption.
	 *
	 * @param s The password.
	 * @return
	 */
	private String md5(String s) {
		try {
			MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
			digest.update(s.getBytes());
			byte messageDigest[] = digest.digest();

			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++)
				hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
			Toast.makeText(getApplicationContext(),
					hexString.toString(),
					Toast.LENGTH_LONG).show();
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			return s;
		}
	}
	private void insertData() {

		db.beginTransaction();
		try {

			//insert rows
			for(int i = 0; i < jumbo.length;i++)
			{
				db.execSQL( "insert into "+Tblname+"(jumbo, num, sex, dob, name, status, breed, dam, sire, replacement, replacement_maternal, terminal, replacement_maternal_prog, dairy, calving_diff, trait_reliability) "
						+ " values ('"+jumbo[i]+
						         "' , '"+num[i]+
						         "' , '"+sex[i]+
						         "' , '"+dob[i]+
						         "' , '"+name[i]+
						         "' , '"+status[i]+
						         "' , '"+breed[i]+
						         "' , '"+dam[i]+
						         "' , '"+sire[i]+
						         "' , '"+replacement[i]+
						         "' , '"+replacement_maternal[i]+
						         "' , '"+terminal[i]+
						         "' , '"+replacement_maternal_prog[i]+
						         "' , '"+dairy[i]+
						         "' , '"+calving_diff[i]+
						         "' , '"+trait_reliability[i]+
						         "');" );
			}

			//commit your changes
			db.setTransactionSuccessful();

		}
		catch (SQLiteException e2) {
		}
		finally {
			db.endTransaction();
		}


	}
	private void addDataBaseTable() {

		db.beginTransaction();
		try {
			//create table

			db.execSQL("create table "+Tblname+"("
					+ " recID integer PRIMARY KEY autoincrement, "
					+ " jumbo  text, "
					+ " num  text, "
					+ " sex  text, "
					+ " dob  text, "
					+ " name  text, "
					+ " status  text, "
					+ " breed  text, "
					+ " dam  text, "
					+ " sire  text, "
					+ " replacement  text, "
					+ " replacement_maternal  text, "
					+ " terminal  text, "
					+ " replacement_maternal_prog  text, "
					+ " dairy  text, "
					+ " calving_diff  text, "
					+ " trait_reliability text );  ");


			//commit your changes
			db.setTransactionSuccessful();

		} catch (SQLException e1) {

		}
		finally {
			db.endTransaction();
		}


	}
}