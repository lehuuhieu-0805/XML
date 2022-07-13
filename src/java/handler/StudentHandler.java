/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handler;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author lehuuhieu
 */
public class StudentHandler extends DefaultHandler {

    private String username;
    private String password;
    private String currentTagName;
    private String fullname;

    private boolean foundBothUserPass;
    private boolean foundUsername;
    private boolean found;

    public StudentHandler() {
        this.foundUsername = false;
        this.foundBothUserPass = false;
        this.found = false;

        this.currentTagName = "";
    }

    public StudentHandler(String username, String password) {
        this.username = username;
        this.password = password;
        this.currentTagName = "";

        this.foundUsername = false;
        this.foundBothUserPass = false;
        this.found = false;
    }

    public boolean isFound() {
        return found;
    }

    public String getFullname() {
        return fullname;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (!this.found) {
            this.currentTagName = qName;
            if (qName.equals("student")) {
                String id = attributes.getValue("id");
                if (id.equals(this.username)) {
                    this.foundUsername = true;
                }//id match username
            }//cursor points to student tag name
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        this.currentTagName = "";
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (!this.found) {
            if (this.foundUsername) {
                String tmp = new String(ch, start, length);
                if (this.currentTagName.equals("lastname")) {
                    this.fullname = tmp.trim();
                } else if (this.currentTagName.equals("middlename")) {
                    this.fullname = this.fullname + " " + tmp.trim();
                } else if (this.currentTagName.equals("firstname")) {
                    this.fullname = this.fullname + " " + tmp.trim();
                } else if (this.currentTagName.equals("password")) {
                    this.foundUsername = false;
                    if (tmp.trim().equals(this.password)) {
                        this.foundBothUserPass = true;
                    }//paasword is matched 
                }//end cursor pointer to password tag Name
            }//matched username

            if (this.foundBothUserPass) {
                String tmp = new String(ch, start, length);
                if (this.currentTagName.equals("status")) {
                    this.foundBothUserPass = false;
                    if (!tmp.trim().equals("dropout")) {
                        this.found = true;
                    }//end status difference dropout
                }//end cursor pointed to status
            }//end matched both username and password
        }
    }

}
