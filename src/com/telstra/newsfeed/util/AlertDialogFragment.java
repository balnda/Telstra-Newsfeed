package com.telstra.newsfeed.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.InflateException;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.telstra.newsfeed.R;

/**
 * 
 * ClassName: AlertDialogFragment.java
 * 
 * Description: This class takes care for displaying android
 * default alert dialog as well as custom alert dialog
 * 
 */

public class AlertDialogFragment extends DialogFragment implements OnClickListener,OnKeyListener {
	
	private static final String TAG = "AlertDialogFragment:";
	
	
	private static boolean bCustomDlg = false;
	private static final String ERROR_MSG = "ErrorMsg";
	private static final String MSG_TITLE = "MsgTitle";
	private static final String Y_OFFSET = "YaxisOffset";
	private static final String LR_PADDING = "LRPadding";
	private static final String POSITIVE_BTN_TXT = "PositiveBtnTxt";
	private static final String NEGATIVE_BTN_TXT = "NegativeBtnTxt";
	private Button mOkBtn;
	private Button mCancelBtn;
	public View view;
	private AlertOnDismiss onDismiss;
	private static AlertDialogFragment currentDialog;
	/**
	 * It creates a new instance of AlertDialogFragment
	 * @param title: Title for the message box
	 * @param msg: User message 
	 * @param offsetY: offset along the Y-axis
	 * @param isCustomDlg: True for default alert dialog and false  for custom alert dialog
	 */
	
	
	public static AlertDialogFragment newInstance(String title,View view,  int offsetY,  String postiveBtnStr, String negativeBtnStr) {
		AlertDialogFragment frag = new AlertDialogFragment();
		frag.view=view;
		
		Bundle args = new Bundle();
        
	        args.putString(MSG_TITLE, title);
	        args.putInt(Y_OFFSET, offsetY);        
        	args.putString(POSITIVE_BTN_TXT, postiveBtnStr);
        	args.putString(NEGATIVE_BTN_TXT, negativeBtnStr);
        
        
        frag.setArguments(args);
        
        return frag;
    }
	
	/**
	 * Default callback method for dialog creation
	 * @param savedInstanceState: saved state
	 */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
    	AlertDialog alertDlg = null;
		PopupButtonsOnTouchColorChange iChangeColor = new PopupButtonsOnTouchColorChange();

    	String title = getArguments().getString(MSG_TITLE);
    	
    	String postivebuttontext=getArguments().getString(POSITIVE_BTN_TXT);
    	String negativebuttontext=getArguments().getString(NEGATIVE_BTN_TXT);
    	
    		View customFragView = null;
    	
    		int offsetY = getArguments().getInt(Y_OFFSET);
        	
        	// Get the layout inflater
            LayoutInflater inflater = getActivity().getLayoutInflater();

            try{
            		customFragView = inflater.inflate(R.layout.popup_msg, null);
            } catch(InflateException ex){
            
            	
            }
            
             TextView textView=(TextView) customFragView.findViewById(R.id.titleConfirmation);
             textView.setText(title);
             //textView.setTypeface(RobotoTypeface.sRobotoThin(getActivity()));
            
            RelativeLayout layout=(RelativeLayout) customFragView.findViewById(R.id.dynamicView);
            int lrPadding = getArguments().getInt(LR_PADDING);
            if(0 != lrPadding){
            	RelativeLayout.LayoutParams layoutParamView = (android.widget.RelativeLayout.LayoutParams) layout.getLayoutParams();
            	layoutParamView.setMargins(lrPadding, layoutParamView.topMargin, lrPadding, layoutParamView.bottomMargin);
            	layout.setLayoutParams(layoutParamView);
            }
            if(view!=null)
            {
            	layout.addView(view);
            }
            else
            {
            	
            }
            mOkBtn=(Button) customFragView.findViewById(R.id.btnUpdate);
            mCancelBtn=(Button) customFragView.findViewById(R.id.btnCancel);
            mOkBtn.setOnTouchListener(iChangeColor);
            //mOkBtn.setTypeface(RobotoTypeface.sRobotoLight(getActivity()));
            //mCancelBtn.setTypeface(RobotoTypeface.sRobotoLight(getActivity()));
            mCancelBtn.setOnTouchListener(iChangeColor);
            if(postivebuttontext==null){
            	mOkBtn.setVisibility(View.GONE);
                
            	
            }else{
            	
            	 mOkBtn.setText(postivebuttontext);
                 mOkBtn.setOnClickListener(AlertDialogFragment.this);
            	
            }
            
            if(negativebuttontext==null){
            	
            	mCancelBtn.setVisibility(View.GONE);
            	
            }
            else{
            	 mCancelBtn=(Button) customFragView.findViewById(R.id.btnCancel);
                 mCancelBtn.setText(negativebuttontext); 
            	
            }

            mOkBtn.setOnClickListener(AlertDialogFragment.this);
            
            mCancelBtn.setOnClickListener(AlertDialogFragment.this);
                      
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setView(customFragView);
            alertDlg = builder.create();
             
           
    	
    	return alertDlg;
    }
    
    
    
    @Override
    public void onResume() {
    	if(onResume!=null)
    		onResume.run();
    	super.onResume();
    }
    
    
    AlertDialogListener mListener;
	private Runnable onResume;
	private boolean backBtn=true;
	
	/**
	 * method to instantiate the AlertDialogListener
	 * @param listner: alert dialog on click listener interface
	 */
	public void onClickAlertDialog(AlertDialogListener listener){
		try{
			mListener = listener;
		} catch(ClassCastException ex){
			
		}
	}

    /**
   	 * callback method for onClick
   	 * @param view: view which receive the onClick event
   	 */
	@Override
	public void onClick(View view) {
		
		if(view.getId() == mOkBtn.getId()){
			
			 mListener.onDialogPositiveClick(AlertDialogFragment.this);
		} else if(view.getId() == mCancelBtn.getId()){
			
			 mListener.onDialogNegativeClick(AlertDialogFragment.this); 
		}
	}
	
	@Override
	public void onDismiss(DialogInterface dialog) {

		if (onDismiss == null)
			super.onDismiss(dialog);
		else
			onDismiss.onDialogDismiss(this);
	}
	
	public AlertOnDismiss getOnDismiss() {
		return onDismiss;
	}


	public void setOnDismiss(AlertOnDismiss onDismiss) {
		this.onDismiss = onDismiss;
	}

	/**
	 * interface in order to receive event callbacks
	 */
	public interface AlertDialogListener {
		public void onDialogPositiveClick(DialogFragment dialog);
		public void onDialogNegativeClick(DialogFragment dialog);
	}
	
	public interface AlertOnDismiss
	{
		public void onDialogDismiss(DialogFragment dialog);
	}
	
	
	
	
	@Override
	public int show(FragmentTransaction transaction, String tag) {
		if(currentDialog == null){
			currentDialog = this;
			return super.show(transaction, tag);
		}
		else{			
			
		}
		return super.show(transaction, tag);
	}
	
	@Override
	public void show(FragmentManager manager, String tag) {
		
		if(currentDialog == null){			
			currentDialog = this;
			super.show(manager, tag);	
		}
		else{	
			
		}
		
	}
	
	@Override
	public void onDestroyView() {
		
		if (currentDialog == this) {
			currentDialog = null;
		}
		super.onDestroyView();
	}
	
	@Override
	public void onDestroy() {
		
		super.onDestroy();
	}

	@Override
	public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
		if(!this.backBtn)
			return true;
		
		if (keyCode == KeyEvent.KEYCODE_BACK) {            
			if (currentDialog == this) {
				currentDialog = null;
			}
            dialog.dismiss();
        }
		return false;
	}
	
	public void makeNonCancallable(final boolean backBtn,final boolean touchOutside)
	{		
		this.backBtn=backBtn;
		onResume=new Runnable() {			
			@Override
			public void run() {
				AlertDialogFragment.this.getDialog().setCancelable(backBtn);
				AlertDialogFragment.this.getDialog().setCanceledOnTouchOutside(touchOutside);				
			}
		};
	}
	
	public void mustShowAlert(FragmentManager manager, String tag)
	{
		currentDialog=null;
		super.show(manager, tag);
	}
	
	
}

	
	