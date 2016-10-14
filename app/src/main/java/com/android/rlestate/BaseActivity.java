package com.android.rlestate;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public abstract class BaseActivity extends AppCompatActivity
{

	public LayoutInflater inflater;
	public LinearLayout llMiddle,llTop1,llTop2;
	private AlertDialog.Builder alertDialog;
	private ProgressDialog progressDialog;
	public TextView tvHome,tvHeading,tvDone;
	
	@Override
	protected void onCreate(Bundle arg0) 
	{
		super.onCreate(arg0);
		setContentView(R.layout.base);
		initializeControls();
		initial();
	}

	public abstract void initial();

	private void initializeControls() 
	{
		inflater 				=  this.getLayoutInflater();
		llMiddle				=  (LinearLayout) findViewById(R.id.llMiddle);
		alertDialog             =  new AlertDialog.Builder(BaseActivity.this);
		progressDialog          =  new ProgressDialog(BaseActivity.this);
	}
	
	public void hideSoftKeyboard(Activity activity) 
	{
		
		InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
		View focus = activity.getCurrentFocus();
		if(focus != null)
			inputMethodManager.hideSoftInputFromWindow(focus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}

	public void showCoustomDialog(String message)
	{
		if (alertDialog != null) 
		{
			alertDialog.setTitle("Alert");
			alertDialog.setMessage(message);
			alertDialog.setPositiveButton("OK",new DialogInterface.OnClickListener() 
			{
				@Override
				public void onClick(DialogInterface dialog, int which) 
				{
					dialog.dismiss();
				}
			});
			alertDialog.show();
		}
	}
	
	
	public void showLoader(final String msg) 
	{
		runOnUiThread(new RunShowLoader(msg, ""));
	}

	public void hideLoader()
	{
		runOnUiThread(new Runnable() 
		{
			@Override
			public void run() 
			{
				try 
				{
					if (progressDialog != null && progressDialog.isShowing())
						progressDialog.dismiss();
					progressDialog = null;
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	class RunShowLoader implements Runnable 
	{
		private String strMsg;
		private String title;

		public RunShowLoader(String strMsg, String title) 
		{
			this.strMsg = strMsg;
			this.title = title;
		}

		@Override
		public void run() 
		{
			try {
				if (progressDialog == null|| (progressDialog != null && !progressDialog.isShowing())) 
				{
					progressDialog = ProgressDialog.show(BaseActivity.this,title, strMsg);
				} 
				else if (progressDialog == null|| (progressDialog != null && progressDialog.isShowing())) 
				{
					progressDialog.setMessage(strMsg);
				}
			} 
			catch (Exception e) 
			{
				progressDialog = null;
			}
		}
	}
}
