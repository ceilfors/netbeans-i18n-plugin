package com.wyse.netbeans.i18n;

import org.openide.util.NbBundle;

/**
 *
 * @author Wyse
 */
public class Suppression {

    public static String SUPPRESSOR = NbBundle.getMessage(Suppression.class, "suppression.suppressor");
    public static String COMMENT = NbBundle.getMessage(Suppression.class, "suppression.comment");

    public static String getSuppressorComment() {
        return COMMENT + " " + SUPPRESSOR;
    }

    public static boolean isSuppressed(String line) {
//        int commentIndex = line.lastIndexOf(COMMENT, end);
//        int suppressionIndex = line.lastIndexOf(SUPPRESSION, end);
//        boolean noSuppression = true;
//        if (commentIndex != -1 && suppressionIndex != -1) {
//            // Found suppression text candidate
//            if (!line.substring(commentIndex + COMMENT.length(), suppressionIndex).trim().isEmpty()) {
//                // Not a suppression i.e. maybe: //fakeNOI18N
//                noSuppression = false;
//            }
//        } else {
//            noSuppression = false;
//        }
        return line.endsWith(Suppression.getSuppressorComment());
    }
}
