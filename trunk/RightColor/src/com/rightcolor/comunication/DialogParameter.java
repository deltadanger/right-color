package com.rightcolor.comunication;

public class DialogParameter {

    private String dialogTitle;
    private String dialogContent;
    private String okBtn;

    public DialogParameter(String dialogTitle, String dialogContent, String okBtn) {
        this.dialogTitle = dialogTitle;
        this.dialogContent = dialogContent;
        this.okBtn = okBtn;
    }

    public String getDialogTitle() {
        return dialogTitle;
    }

    public void setDialogTitle(String dialogTitle) {
        this.dialogTitle = dialogTitle;
    }

    public String getDialogContent() {
        return dialogContent;
    }

    public void setDialogContent(String dialogContent) {
        this.dialogContent = dialogContent;
    }

    public String getOkBtn() {
        return okBtn;
    }

    public void setOkBtn(String okBtn) {
        this.okBtn = okBtn;
    }
}
