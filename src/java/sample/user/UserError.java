/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.user;

/**
 *
 * @author Lc_Tn
 */
public class UserError {
    private String idError;
    private String nameError;
    private String confirmError;
    private String mail;
    private String quantityError;
    private String phoneError;
    
    public UserError() {
    }

    public UserError(String idError, String nameError, String confirmError, String mail, String quantityError, String phoneError) {
        this.idError = idError;
        this.nameError = nameError;
        this.confirmError = confirmError;
        this.mail = mail;
        this.quantityError = quantityError;
        this.phoneError = phoneError;
    }

    public String getIdError() {
        return idError;
    }

    public void setIdError(String idError) {
        this.idError = idError;
    }

    public String getNameError() {
        return nameError;
    }

    public void setNameError(String nameError) {
        this.nameError = nameError;
    }

    public String getConfirmError() {
        return confirmError;
    }

    public void setConfirmError(String confirmError) {
        this.confirmError = confirmError;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getQuantityError() {
        return quantityError;
    }

    public void setQuantityError(String quantityError) {
        this.quantityError = quantityError;
    }

    public String getPhoneError() {
        return phoneError;
    }

    public void setPhoneError(String phoneError) {
        this.phoneError = phoneError;
    }    
}
