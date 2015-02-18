package com.example.aaron.fragmentlecture;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.widget.Toast;


public class Fragmentmanageractivity extends FragmentActivity implements MyFragment.GetFragNumber {
	public static final String FIRST_FRAGMENT_TAG = "first";
	public static final String SECOND_FRAGMENT_TAG = "second";
    public static final String ALL_FRAGMENT_TAG = "fragment ";
	private Fragment visible;
	private Fragment mVisibleCached;
	private MyFragment myFrag;
	private MyFragment myFragTwo;
	private int currentfragnumber = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manager_activity);
		setupFragments();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}
	
	public void setupFragments(){
		final FragmentManager fm = getSupportFragmentManager();
		final FragmentTransaction ft = fm.beginTransaction();
		myFrag = ((MyFragment) fm.findFragmentByTag(FIRST_FRAGMENT_TAG));
		if (myFrag == null) {
			myFrag = MyFragment.newInstance(currentfragnumber, false);
			ft.add(R.id.fragment_container, myFrag, FIRST_FRAGMENT_TAG);
		}
		visible = myFrag;
		ft.commit();
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		visible = mVisibleCached;

	}
	
	public void addFragment(boolean infinite) {
		final FragmentManager fm = getSupportFragmentManager();
		final FragmentTransaction ft = fm.beginTransaction();
		ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
		
		if(infinite) {
            currentfragnumber++;
            myFragTwo = MyFragment.newInstance(currentfragnumber, infinite);
            ft.replace(R.id.fragment_container,  myFragTwo, ALL_FRAGMENT_TAG);
            ft.addToBackStack(null);
            visible = myFragTwo;
        }
        else {

            if (visible.getTag() == FIRST_FRAGMENT_TAG) {
                myFragTwo = ((MyFragment) fm.findFragmentByTag(SECOND_FRAGMENT_TAG));
                if (myFragTwo == null) {
                    myFragTwo = MyFragment.newInstance(currentfragnumber, infinite);
                }
                ft.replace(R.id.fragment_container, myFragTwo, SECOND_FRAGMENT_TAG);
                ft.addToBackStack(null);
                currentfragnumber = 1;
                visible = myFragTwo;
            } else {
                myFrag = ((MyFragment) fm.findFragmentByTag(FIRST_FRAGMENT_TAG));
                if (myFrag == null) {
                    myFrag = MyFragment.newInstance(currentfragnumber, infinite);
                }

                ft.replace(R.id.fragment_container, myFrag, FIRST_FRAGMENT_TAG);
                ft.addToBackStack(null);
                currentfragnumber = 0;
                visible = myFrag;
            }
        }
		ft.commit();
		
	}

	@Override
	public int getData() {
		return currentfragnumber;
	}
}
