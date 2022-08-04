package com.example.questionsApp.utils

enum class Method {
    GET,
    POST
}

enum class ButtonStates {
    START, SUBMIT, SUBMITTED_SUCCESS,ALREADY_SUBMITTED
}

enum class BtnAction {
    RETRY, CLOSE
}

enum class SubmissionConfirmation {
    SUCCESS, FAIL
}

enum class QuestionNavBtnState {
    PREVIOUS, NEXT
}