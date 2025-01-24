package com.example.myapplication;

public class faq_item {
    String ques;
    String ans;

    public faq_item() {
    }

    public faq_item(String ques, String ans) {
        this.ques = ques;
        this.ans = ans;
    }

    public String getQues() {
        return ques;
    }

    public void setQues(String ques) {
        this.ques = ques;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }
}
