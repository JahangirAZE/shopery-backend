package az.shopery.utils.common;

import az.shopery.utils.enums.UserRole;

public class EnumUtils {
    public static String formatRole(UserRole userRole){
        return userRole.toString().charAt(0) +
                userRole.toString().substring(1).toLowerCase();
    }
}
