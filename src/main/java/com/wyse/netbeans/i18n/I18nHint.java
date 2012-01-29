package com.wyse.netbeans.i18n;

import com.sun.source.tree.Tree;
import com.sun.source.tree.Tree.Kind;
import com.sun.source.util.SourcePositions;
import com.sun.source.util.TreePath;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import org.netbeans.api.editor.EditorRegistry;
import org.netbeans.api.java.source.CompilationInfo;
import org.netbeans.modules.java.hints.spi.AbstractHint;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.ErrorDescriptionFactory;
import org.netbeans.spi.editor.hints.Fix;
import org.netbeans.spi.editor.hints.Severity;
import org.openide.util.NbBundle;

/**
 *
 * @author Wyse
 */
public class I18nHint extends AbstractHint {

    private static final Set<Tree.Kind> TREE_KINDS =
            EnumSet.<Tree.Kind>of(Tree.Kind.STRING_LITERAL);
    public static final String DISPLAY_NAME = NbBundle.getMessage(I18nHint.class, "i18nhint.display_name");
    public static final String ID = NbBundle.getMessage(I18nHint.class, "i18nhint.id");
    public static final String DESCRIPTION = NbBundle.getMessage(I18nHint.class, "i18nhint.description");
    public static final String ERROR_DESCRIPTION = NbBundle.getMessage(I18nHint.class, "i18nhint.error.description");

    public I18nHint() {
        super(true, true, AbstractHint.HintSeverity.WARNING);
    }

    @Override
    public Set<Kind> getTreeKinds() {
        return TREE_KINDS;
    }

    @Override
    public List<ErrorDescription> run(CompilationInfo info, TreePath treePath) {
        Tree t = treePath.getLeaf();
        JTextComponent editor = EditorRegistry.lastFocusedComponent();
        Document doc = editor.getDocument();

        SourcePositions sp = info.getTrees().getSourcePositions();
        int start = (int) sp.getStartPosition(info.getCompilationUnit(), t);
        int end = (int) sp.getEndPosition(info.getCompilationUnit(), t);
        int endLine = info.getText().indexOf("\n", start);
        String line = info.getText().substring(start, endLine).trim();

        if (!Suppression.isSuppressed(line)) {
            return Collections.<ErrorDescription>singletonList(
                    ErrorDescriptionFactory.createErrorDescription(
                    Severity.HINT, ERROR_DESCRIPTION, createFixes(
                    doc, start, endLine), info.getFileObject(), start, end));
        } else {
            return null;
        }
    }

    protected List<Fix> createFixes(Document doc, int start, int end) {
        return Collections.<Fix>singletonList(
                new SuppressI18nHintFix(doc, end, " " + Suppression.getSuppressorComment()));
    }

    @Override
    public void cancel() {
    }

    @Override
    public String getDisplayName() {
        return DISPLAY_NAME;
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
