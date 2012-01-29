package com.wyse.netbeans.i18n;

import javax.swing.text.Document;
import org.netbeans.spi.editor.hints.ChangeInfo;
import org.netbeans.spi.editor.hints.EnhancedFix;
import org.openide.util.NbBundle;

/**
 * Fix implementation that will add a suppression string at the end of the line.
 * When a line has a suppression, the hint won't be shown.
 *
 * @author Wyse
 */
public class SuppressI18nHintFix implements EnhancedFix {

    public static final String TEXT = NbBundle.getMessage(SuppressI18nHintFix.class, "suppressi18nhintfix.text");
    private Document doc;
    private int start;
    private String suppression;

    public SuppressI18nHintFix(final Document doc, final int start, final String suppression) {
        this.doc = doc;
        this.start = start;
        this.suppression = suppression;
    }

    @Override
    public CharSequence getSortText() {
        return this.getClass().getSimpleName();
    }

    @Override
    public String getText() {
        return TEXT;
    }

    @Override
    public ChangeInfo implement() throws Exception {
        doc.insertString(start, suppression, null);
        return null;
    }
}
