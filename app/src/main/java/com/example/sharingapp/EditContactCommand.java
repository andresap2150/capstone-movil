package com.example.sharingapp;

import android.content.Context;

public class EditContactCommand extends Command {

    private ContactList contact_list;
    private Contact contact;
    private Contact updated_contact;
    private Context context;

    public EditContactCommand(ContactList contact_list, Contact contact, Contact updated_contact,Context context) {
        this.contact_list = contact_list;
        this.contact = contact;
        this.updated_contact = updated_contact;
        this.context = context;
    }

    @Override
    public void execute() {
        //delete the old contact
        new DeleteContactCommand(contact_list,contact,context).execute();
        //save the updated one
        new AddContactCommand(contact_list,updated_contact,context).execute();
    }
}
