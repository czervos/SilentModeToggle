package com.dummies.android.silentmodetoggle;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity
{
	private AudioManager mAudioManager;	// defines audiomanager variable which will manage phone volume
	private boolean mPhoneIsSilent;	// defines variable which will indicate if phone is silent

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState); // tells Activity to do the setup work for the MainActivity class - this starts the app
		setContentView(R.layout.activity_main); // shows the user interface on screen by retrieving the layout for activity_main
		
		mAudioManager = (AudioManager)getSystemService(AUDIO_SERVICE); 	// initializes audiomanager variable by returning as an object type (hence needing to cast)
																		// the type of service AUDIO_SERVICE is, which is the volume control
		checkIfPhoneIsSilent();		// method that initializes mPhoneIsSilent variable since the phone may or may not already be in silent mode
		
		setButtonClickListener();	// calls buttonclicklistener method defined below which sents up button click event handler
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void setButtonClickListener()
	{
		Button toggleButton = (Button)findViewById(R.id.toggleButton); 	// findViewById method (from Activity) allows you to find a view (widget) inside the
																		// the activities layout and returns a View class that you need to cast to the appropriate type
		/* Setting up event handler */
		toggleButton.setOnClickListener(new View.OnClickListener() { // creates a View.OnClickListener object and assigns it to togglebutton
			public void onClick(View v) { // you put what you want the button press to do in here
			
				if (mPhoneIsSilent) {
					// change back to normal mode
					mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
					mPhoneIsSilent = false;
				}
				else {
					// change to silent mode
					mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
					mPhoneIsSilent = true;
				}
				
				// toggles the UI image
				toggleUi();	
			} // alternate way of implementing button: http://developer.android.com/guide/topics/ui/controls/button.html
		});
	}
	
	/*
	 * Check to see if the phone is currently in Silent mode.
	 */
	private void checkIfPhoneIsSilent() {
		int ringerMode = mAudioManager.getRingerMode(); // gets the phones current ringer mode
		if (ringerMode == AudioManager.RINGER_MODE_SILENT) {
			mPhoneIsSilent = true;
		}
		else {
			mPhoneIsSilent = false;
		}
	}
	
	/*
	 * Toggles the UI image from silent to normal and vice versa
	 */
	private void toggleUi() {
		ImageView imageView = (ImageView) findViewById(R.id.phone_icon);
		Drawable newPhoneImage; // defines variable to store the image we want to change to
		
		if (mPhoneIsSilent) {
			newPhoneImage = getResources().getDrawable(R.drawable.phone_silent); // if phone is silent, get the phone_silent image
		}
		else {
			newPhoneImage = getResources().getDrawable(R.drawable.phone_on);
		}
		
		imageView.setImageDrawable(newPhoneImage); // sets the image to the new one
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		checkIfPhoneIsSilent(); // on resume, run these methods
		toggleUi();
	}
	
}
