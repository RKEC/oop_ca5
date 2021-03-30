package com.dkit.oopca5.client;

/* This class should contain static methods to verify input in the application
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * d00230925
 *Richard Collins
 */

public class RegexChecker
{
    public boolean checkRegister(String num, String ptrn){
        boolean result = false;
        try{
            String s = String.valueOf(num);
            Pattern pattern = Pattern.compile(ptrn);
            Matcher matcher = pattern.matcher(s);
            boolean matchFound = matcher.find();
            if(matchFound){
                result = true;
            }else{
                result = false;
            }
        }catch (PatternSyntaxException e){
            e.printStackTrace();
        }
        return result;
    }

    public boolean checkCourseId(String id){
        boolean result = false;
        try {
            Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{6,}$");
            Matcher matcher = pattern.matcher(id);
            boolean matchFound = matcher.find();
            if(matchFound){
                result = true;
            }else{
                result = false;
            }
        }catch (PatternSyntaxException e){
            e.printStackTrace();
        }
        return result;
    }

}
