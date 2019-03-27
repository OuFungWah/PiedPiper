package com.crazywah.piedpiper.module.contact.util;

import com.crazywah.piedpiper.bean.User;

import java.util.Comparator;

public class RequestTimeComparator implements Comparator<User> {

    @Override
    public int compare(User o1, User o2) {
        if (o1.getRegisterTime() != null && o2.getRegisterTime() != null) {
            long requestTime1 = o1.getRequestTime().getTime();
            long requestTime2 = o1.getRequestTime().getTime();
            if (requestTime1 < requestTime2) {
                return -1;
            } else if (requestTime1 == requestTime2) {
                return 0;
            } else {
                return 1;
            }
        } else {
            return 0;
        }
    }

}
