/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.io.Serializable;

/**
 *
 * @author lehuuhieu
 */
public class StudentDTO implements Serializable{
    private String id;
    private String sClass;
    private String fullName;
    private String address;
    private String sex;
    private String status;

    public StudentDTO() {
    }

    public StudentDTO(String id, String sClass, String fullName, String address, String sex, String status) {
        this.id = id;
        this.sClass = sClass;
        this.fullName = fullName;
        this.address = address;
        this.sex = sex;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getsClass() {
        return sClass;
    }

    public void setsClass(String sClass) {
        this.sClass = sClass;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
