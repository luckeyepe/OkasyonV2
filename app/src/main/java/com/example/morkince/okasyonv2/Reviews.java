package com.example.morkince.okasyonv2;

public class Reviews {

    String evaluatorName;
    int numStars;
    String reviewComment;

    public Reviews(){}

    public Reviews(String evaluatorName, int numStars, String reviewComment) {
        this.evaluatorName = evaluatorName;
        this.numStars = numStars;
        this.reviewComment = reviewComment;
    }

    public String getEvaluatorName() {
        return evaluatorName;
    }

    public void setEvaluatorName(String evaluatorName) {
        this.evaluatorName = evaluatorName;
    }

    public int getNumStars() {
        return numStars;
    }

    public void setNumStars(int numStars) {
        this.numStars = numStars;
    }

    public String getReviewComment() {
        return reviewComment;
    }

    public void setReviewComment(String reviewComment) {
        this.reviewComment = reviewComment;
    }
}
