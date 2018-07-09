package com.example.sharingapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.Observable;
import java.util.Observer;

/**
 * Editing a pre-existing contact consists of deleting the old contact and adding a new contact with the old
 * contact's id.
 * Note: You will not be able contacts which are "active" borrowers
 */
public class EditContactActivity extends AppCompatActivity implements Observer{

    private ContactListController contact_list_controller = new ContactListController(new ContactList());
    private ContactController​ contactController​;
    private EditText email;
    private EditText username;
    private Context context;
    private boolean on_create_update = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        context = getApplicationContext();
        contact_list_controller.loadContacts(context);

        Intent intent = getIntent();
        int pos = intent.getIntExtra("position", 0);

        contactController​ = new ContactController​(contact_list_controller.getContact(pos));

        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);

        username.setText(contactController​.getUsername());
        email.setText(contactController​.getEmail());

        on_create_update = true;
        contact_list_controller.addObserver(this);

        on_create_update = false;
    }

    public void saveContact(View view) {

        String email_str = email.getText().toString();

        if (email_str.equals("")) {
            email.setError("Empty field!");
            return;
        }

        if (!email_str.contains("@")){
            email.setError("Must be an email address!");
            return;
        }

        String username_str = username.getText().toString();
        String id = contactController​.getId(); // Reuse the contact id

        // Check that username is unique AND username is changed (Note: if username was not changed
        // then this should be fine, because it was already unique.)
        if (!contact_list_controller.isUsernameAvailable(username_str) && !(contactController​.getUsername().equals(username_str))) {
            username.setError("Username already taken!");
            return;
        }

        ContactController​ update_contact_ctr = new ContactController​(new Contact(username_str, email_str, id));
        //call the edit command
        contact_list_controller.addContact(contactController​.getContact(),context);

        // End EditContactActivity
        finish();
    }

    public void deleteContact(View view) {

        //delete contact
        contact_list_controller.deleteContact(contactController​.getContact(),context);
        contact_list_controller.removeObserver(this);
        // End EditContactActivity
        finish();
    }

    @Override
    public void update(Observable observable, Object o) {
        if(on_create_update){
            //update view
        }
    }
}
