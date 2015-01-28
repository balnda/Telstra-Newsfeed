package com.telstra.newsfeed.exception;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.telstra.newsfeed.R;
import com.telstra.newsfeed.constants.NewsFeedAppConstants;
import com.telstra.newsfeed.util.AlertDialogFragment;
import com.telstra.newsfeed.util.AlertDialogFragment.AlertDialogListener;

public class ErrorScreenActivity extends FragmentActivity implements
		AlertDialogListener {

	private AlertDialogFragment alertDlgFrg;
	
	private boolean isRunTimeException = false;
	private boolean is108Error = false;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		Intent newIntent = getIntent();
		isRunTimeException = newIntent.getBooleanExtra(NewsFeedAppConstants.IS_EXCEPTION, false);
		String errorMessage = newIntent.getStringExtra(NewsFeedAppConstants.ERROR_MESSAGE);
		is108Error = newIntent.getBooleanExtra(NewsFeedAppConstants.IS_108_EXCEPTION, false);
		String title = "";		
		
		View inflater = View.inflate(this, R.layout.alert, null);
		TextView textView=(TextView) inflater.findViewById(R.id.ExceptionMessage);
		//textView.setTypeface(RobotoTypeface.sRobotoRegular(this));
		if (isRunTimeException) {
			textView.setText(ErrorCodeAndMessage.UNCAUGHT_ERROR
					.getErrorMessage());
			title = getString(R.string.fatal_error);
		}
		else{
			textView.setText(errorMessage);
			if (!is108Error) {
				title = "Activity Unavailable";
			}
			else{
				title = "Data Mismatch";
			}
		}
		AlertDialogFragment alertDlgFrg = AlertDialogFragment
				.newInstance(title, inflater,0,getString(R.string.ok),null);
		alertDlgFrg.onClickAlertDialog(this);
		
		alertDlgFrg.makeNonCancallable(false, false);
		alertDlgFrg.mustShowAlert(getSupportFragmentManager(), "Error_Report");
	
	}


	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		
		if (isRunTimeException) {
			
				
				
				System.exit(0);//TBD ...what needs to be done on an app crash.
		}
		else{
				
			this.finish();
		}

	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
		
		if (isRunTimeException) {
			
		}
		else{
			
			
			}
		
	}

}
