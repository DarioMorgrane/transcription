package dariomorgrane.transcription.model;

public class RolesInformation {

    private String interviewerDesignation = "Интервьюер: ";
    private String respondentDesignation = "Респондент: ";
    private String firstSpeaker;
    private Boolean speakersDesignationsIsBold;

    public String getInterviewerDesignation() {
        return interviewerDesignation;
    }

    public void setInterviewerDesignation(String interviewerDesignation) {
        this.interviewerDesignation = interviewerDesignation;
    }

    public String getRespondentDesignation() {
        return respondentDesignation;
    }

    public void setRespondentDesignation(String respondentDesignation) {
        this.respondentDesignation = respondentDesignation;
    }

    public String getFirstSpeaker() {
        return firstSpeaker;
    }

    public void setFirstSpeaker(String firstSpeaker) {
        this.firstSpeaker = firstSpeaker;
    }

    public Boolean getSpeakersDesignationsIsBold() {
        return speakersDesignationsIsBold;
    }

    public void setSpeakersDesignationsIsBold(String speakersDesignationsIsBold) {
        this.speakersDesignationsIsBold = Boolean.valueOf(speakersDesignationsIsBold);
    }
}
