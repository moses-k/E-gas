package com.moscom.egas.utilities;

import java.util.Random;

public class Utilities {

    public static synchronized String generateOrderNo(int length) throws Exception {
        int randomNumberLength = length ;	Random random = null;	StringBuilder builder = null;
        random = new Random(System.currentTimeMillis());
        builder = new StringBuilder("");
        try {
            for (int i = 0; i < randomNumberLength; i++) {
                int digit = random.nextInt(10);
                if(digit==0)
                    builder.append(digit+1);
                else
                    builder.append(digit);
            }
        } catch (Exception e) {
            throw new Exception("Can't generate generateOrderNo");
        }finally{
            random=null;
        }
        return builder.toString();
    }
}
