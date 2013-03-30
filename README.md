WizarDroid
==========

WizarDroid is an Android library for creating Wizard like Activities such as Installation Wizard, 
Step by Step processes, etc. It is built on top of Android's Fragments featuring Wizard state persistence 
and simple API for controling the flow of your application. 

Prereqiusits
------------

You need to make sure that your project is compatible with the following:

1.	Minimum SDK is API level 9
2.	Android's support library

Getting started
---------------

The basic Wizard scenario:

1.	Create an Activity that inherits from WizardActivity
2.	Create your Wizard's steps by inheriting from WizardStep
3.	Once you've got your Wizard's steps ready, override onSetup() in your WizardActivity and create a new WizardFlow
4.	Last but not least, override onWizardDone() in your WizardActivity and do whatever you want to do once the Wizard reached the last step (typically, here you will call the activity's finish() method or return a result, etc.)

**The following sample is available at [wizard-sample/](https://github.com/Nimrodda/WizarDroid/wizard-sample/)**

**1.	The Activity's Layout**

**Notice the FrameLayout which will act as our Step container.**

	<?xml version="1.0" encoding="utf-8"?>
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
                  android:orientation="vertical"
                  android:layout_width="fill_parent"
                  android:layout_height="fill_parent">

        <!--
            **********************************************************************
            **You MUST have this FrameLayout as the container for wizard's steps**
            **********************************************************************
        -->
        <FrameLayout
                android:id="@+id/step_container"
                android:layout_width="match_parent"
                android:layout_height="wrap_content" />

        <!-- Button for signalling the wizard to proceed to the next step -->
        <Button
                android:id="@+id/next_button"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_alignParentBottom="true"
                android:layout_alignParentRight="true"
                android:text="Next" />
    </LinearLayout>

**2.	The Activity**

	public class TutorialWizard extends WizardActivity {

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.wizard);
        }

        //You must override this method and create a wizard flow by
        //using WizardFlow.Builder as shown in this example
        @Override
        public void onSetup(WizardFlow flow) {
            flow = new WizardFlow.Builder()
                    .setActivity(this)                      //First, set the hosting activity for the wizard
                    .setContainerId(R.id.step_container)    //then set the layout container for the steps.
                    .addStep(new Step1())                   //Add your steps in the order you want them
                    .addStep(new Step2())                   //to appear and eventually call create()
                    .create();                              //to create the wizard flow.

            //Call the super method using the newly created flow
            super.onSetup(flow);
        }

        //Overriding this method is optional
        @Override
        public void onWizardDone() {
            //Do whatever you want to do once the Wizard is complete
            //in this case I just close the activity, which causes Android
            //to go back to the previous activity.
            finish();
        }
	}

**3.	Steps Layout**

**To make it simple, we will use the same layout for all the steps.**

**XML:**

	<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
                  android:orientation="vertical"
                  android:layout_width="match_parent"
                  android:layout_height="match_parent">

        <TextView
                android:layout_width="fill_parent"
                android:layout_height="wrap_content"
                android:id="@+id/textView"/>
    </LinearLayout>

**Code:**

**TutorialStep1**

	public class TutorialStep1 extends WizardStep implements View.OnClickListener {

        //You must have an empty constructor for every tutorial_step
        public TutorialStep1() {
        }

        //Set your layout here
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.tutorial_step, container, false);
            TextView tv = (TextView) v.findViewById(R.id.textView);
            tv.setText("This is an example of Step 1 in the wizard. Click the Next " +
                    "button to proceed to the next tutorial_step. Hit your phone's back button to go back to the calling activity.");

            //Set listener for 'Next' button click
            //Note that we are setting OnClickListener using getActivity() because
            //the 'Next' button is actually part of the hosting activity's layout and
            //not the tutorial_step's layout
            Button nextButton = (Button) getActivity().findViewById(R.id.next_button);
            nextButton.setOnClickListener(this);
            nextButton.setText("Next");

            return v;
        }

        @Override
        public void onClick(View view) {
            //Do some work
            //...

            //And call done() to signal that the tutorial_step is completed successfully
            done();
        }
    }

**TutorialStep2**

    public class TutorialStep2 extends WizardStep implements View.OnClickListener {

        //You must have an empty constructor for every tutorial_step
        public TutorialStep2() {
        }

        //Set your layout here
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.tutorial_step, container, false);
            TextView tv = (TextView) v.findViewById(R.id.textView);
            tv.setText("This is an example of Step 2 and also the last tutorial_step in this wizard. " +
                    "By clicking Next you will go back to the main activity. Hit your phone back button to go back to the previous tutorial_step");
            //Set listener for 'Next' button click
            //Note that we are setting OnClickListener using getActivity() because
            //the 'Next' button is actually part of the hosting activity's layout and
            //not the tutorial_step's layout
            Button nextButton = (Button) getActivity().findViewById(R.id.next_button);
            nextButton.setOnClickListener(this);
            nextButton.setText("Finish");

            return v;
        }

        @Override
        public void onClick(View view) {
            //Do some work
            //...

            //And call done() to signal that the tutorial_step is completed successfully
            done();
        }
    }

**That's it!** Run the application and see how it's working.
Press the next button to go to the next step and press your phone's back button to go back one step.

**This is just a simple example of what you can do with WizarDroid.
For more complex scenarios continue to the Advanced section.**

Installation
------------

1.	The easiest way is to add a Maven dependency like so:

    <dependency>
		<groupId>org.codepond.android.wizardroid</groupId>
		<artifactId>wizardroid-core</artifactId>
		<version>1.0.0-SNAPSHOT</version>
	</dependency>

2.	You can also download the latest release JAR and simply put it in your class path.

License
-------

You may use WizarDroid under the terms of MIT License.
