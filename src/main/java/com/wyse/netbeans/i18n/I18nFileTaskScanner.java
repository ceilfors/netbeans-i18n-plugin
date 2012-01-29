package com.wyse.netbeans.i18n;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.Comment;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.LineComment;
import japa.parser.ast.expr.StringLiteralExpr;
import japa.parser.ast.visitor.VoidVisitorAdapter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.netbeans.spi.tasklist.FileTaskScanner;
import org.netbeans.spi.tasklist.Task;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.NbBundle;

/**
 *
 * @author Wyse
 */
public class I18nFileTaskScanner extends FileTaskScanner {

    public static final String DISPLAY_NAME = NbBundle.getMessage(I18nFileTaskScanner.class, "i18nfiletaskscanner.display_name");
    public static final String DESCRIPTION = NbBundle.getMessage(I18nFileTaskScanner.class, "i18nfiletaskscanner.description");
    public static final String TASK_DESCRIPTION = NbBundle.getMessage(I18nFileTaskScanner.class, "i18nfiletaskscanner.task.description");
    private static final Logger LOGGER = Logger.getLogger(I18nFileTaskScanner.class.getName());
    
    public I18nFileTaskScanner() {
        super(DISPLAY_NAME, DESCRIPTION, null);
    }

    @Override
    public List<? extends Task> scan(FileObject fileObject) {
        // Filter to only java files
        if (fileObject == null || !"java".equalsIgnoreCase(fileObject.getExt())) {
            return null;
        }

        File file = FileUtil.toFile(fileObject);
        if (file == null) { // occurs for libraries for example
            return null;
        }

        // Parse java files and creates tasks
        try {
            CompilationUnit cu = JavaParser.parse(file);
            StringWithoutSuppressionVisitor visitor = new StringWithoutSuppressionVisitor(filterAndConvertToLineCommentMap(cu.getComments()));
            visitor.visit(cu, null);

            List<Task> tasks = new ArrayList<Task>();
            for (Integer lineNumber : visitor.getNotSuppressedLineNumbers()) {
                tasks.add(Task.create(fileObject, "nb-tasklist-warning", TASK_DESCRIPTION, lineNumber));
            }

            return tasks;
        } catch (ParseException ex) {
            LOGGER.log(Level.SEVERE, "source code can't be parsed", ex);
            return null;
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
    }

    @Override
    public void notifyPrepare() {
        super.notifyPrepare();
    }

    private Map<Integer, LineComment> filterAndConvertToLineCommentMap(List<Comment> commentList) {
        Map<Integer, LineComment> commentMap = new HashMap<Integer, LineComment>();
        for (Comment comment : commentList) {
            if (comment instanceof LineComment) {
                commentMap.put(comment.getBeginLine(), (LineComment) comment);
            }
        }
        return commentMap;
    }

    @Override
    public void attach(Callback callback) {
    }

    private static class StringWithoutSuppressionVisitor extends VoidVisitorAdapter {

        private Map<Integer, LineComment> comments;
        private List<Integer> notSuppressedLineNumbers;

        public StringWithoutSuppressionVisitor(Map<Integer, LineComment> comments) {
            this.comments = comments;
            this.notSuppressedLineNumbers = new ArrayList<Integer>();
        }

        @Override
        public void visit(final StringLiteralExpr n, final Object arg) {
            final int lineNumber = n.getBeginLine();
            LineComment lineComment = comments.get(lineNumber);
            if (lineComment == null
                    || !Suppression.isSuppressed(lineComment.toString().trim())) {// Calling toString() will add "//", calling getContent() won't.
                notSuppressedLineNumbers.add(lineNumber);
            }
        }

        public List<Integer> getNotSuppressedLineNumbers() {
            return notSuppressedLineNumbers;
        }
    }
}
