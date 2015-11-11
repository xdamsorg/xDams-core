package org.xdams.utility.text.findreplace;
import java.util.LinkedList;


/**
 * @author Daniel Camarda
 */
public class MemoryTransactionalReplacer extends ATransactionalReplacer {
    LinkedList<IReplacer> checkPoints = new LinkedList<IReplacer>();

    public MemoryTransactionalReplacer(IReplacer replacer){
        this.replacer = replacer;
    }

    public MemoryTransactionalReplacer(){
        replacer = new Replacer();
    }

    public MemoryTransactionalReplacer(CharSequence text, String pattern, Options... options){
        replacer = new Replacer(text, pattern, options);
    }

    public MemoryTransactionalReplacer(CharSequence text, String pattern){
        replacer = new Replacer(text, pattern);
    }

    public MemoryTransactionalReplacer(String pattern){
        replacer = new Replacer(pattern);
    }

    public void saveCheckPoint() {
        try {
            checkPoints.add(replacer.clone());
        } catch (CloneNotSupportedException e) {
            //non ci arriva mai
            e.printStackTrace();
        }
    }

    public void restoreCheckPoint(int checkPointNum) {
        replacer = checkPoints.get(checkPointNum);
    }

    public void restoreLastCheckPoint() {
        replacer = checkPoints.getLast();
    }

    public void removeCheckPoint(int checkPointNum) {
        checkPoints.remove(checkPointNum);
    }

    public void removeAllCheckPoints() {
        checkPoints.clear();
    }

    public IReplacer clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}
