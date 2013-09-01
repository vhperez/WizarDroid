package org.codepond.android.wizardroid.sample.steps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import org.codepond.android.wizardroid.persistence.ContextVariable;
import org.codepond.android.wizardroid.R;
import org.codepond.android.wizardroid.WizardStep;

public class FormStep1 extends WizardStep {

    //Tell WizarDroid to persist these fields for next steps
    @ContextVariable
    private String firstname;
    @ContextVariable
    private String lastname;

    //You must have an empty constructor for every step
    public FormStep1() {
    }

    //Set your layout here
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.step_form, container, false);

        return v;
    }

    //Called whenever the wizard proceeds to the next step or goes back to the previous step
    @Override
    public void onExit(int exitCode) {
        switch (exitCode) {
            case WizardStep.EXIT_NEXT:
                bindDataFields();
                break;
            case WizardStep.EXIT_PREVIOUS:
                //Do nothing...
                break;
        }
    }

    private void bindDataFields() {
        //Do some work
        //...
        EditText firstnameEt = (EditText) getActivity().findViewById(R.id.firstnameField);
        EditText lastnameEt = (EditText) getActivity().findViewById(R.id.lastnameField);

        //The values of these fields will be automatically stored in the wizard context
        //and will be populated in the next steps only if the same field names are used.
        firstname = firstnameEt.getText().toString();
        lastname = lastnameEt.getText().toString();
    }
}
