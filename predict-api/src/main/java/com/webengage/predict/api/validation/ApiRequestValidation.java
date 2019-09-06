package com.webengage.predict.api.validation;

import com.webengage.predict.api.exception.PredictApiException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ApiRequestValidation {

    public void doesGetRequestContainsId(Integer id) {
        if (id == null) PredictApiException.BAD_REQUEST.throwException("Please provide a valid id");
    }

    public void checkIfAllUrlsArePresentAndValid(String url1, String url2, String url3) {
        String regex = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        String message = "";
        boolean everythingMatching = true;
        if (!isMatching(url1, regex)) {
            message += " URL1";
            everythingMatching = false;
        }
        if (!isMatching(url2, regex)) {
            message += " URL2";
            everythingMatching = false;
        }
        if (!isMatching(url3, regex)) {
            message += " URL3";
            everythingMatching = false;
        }

        if (!everythingMatching) PredictApiException.BAD_REQUEST.throwException("Please provide valid" + message);
    }

    public boolean isMatching(String url, String pattern) {
        if (url != null) {
            Pattern patt = Pattern.compile(pattern);
            Matcher matcher = patt.matcher(url);
            return matcher.matches();
        }
        return false;
    }
}
