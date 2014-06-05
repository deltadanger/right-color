package com.rightcolor.comunication;

public class ConfirmParameter extends DialogParameter {

    private String cancelBtn;
    public ConfirmParameter(String dialogTitle, String dialogContent, String okBtn, String cancelBtn) {
        super(dialogTitle, dialogContent, okBtn);
        this.cancelBtn = cancelBtn;
    }
    public String getCancelBtn() {
        return cancelBtn;
    }
    public void setCancelBtn(String cancelBtn) {
        this.cancelBtn = cancelBtn;
    }
}
